package com.project.megacitycab.service.custom.impl;

import com.project.megacitycab.dao.custom.UserDAO;
import com.project.megacitycab.dao.custom.impl.UserDaoImpl;
import com.project.megacitycab.entity.User;
import com.project.megacitycab.service.custom.AuthService;
import com.project.megacitycab.util.security.PasswordUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Base64;
import java.util.logging.Logger;

public class AuthServiceImpl implements AuthService {

    private final UserDAO userDao;
    private final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    public AuthServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.info("Attempting to log in user");

            // Get form parameters from the login form
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Validate input
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=invalid_input");
                return;
            }

            // Fetch user from the database
            User user = userDao.findByEmail(email);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=user_not_found");
                return;
            }

            // Verify password using PasswordUtils
            if (!PasswordUtils.verifyPassword(password, user.getPassword(), user.getSalt())) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=invalid_credentials");
                return;
            }

            // Create session and store user details
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());

            // Redirect to the dashboard after successful login
            response.sendRedirect(request.getContextPath() + "/");

        } catch (Exception e) {
            logger.severe("Error during login: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/login?error=system_error");
        }
    }

    @Override
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.info("Registering new user");

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Check if the user already exists
            User user = userDao.findByEmail(email);
            if (user != null) {
                response.sendRedirect(request.getContextPath() + "/auth/register?error=User already exists for " + email);
                return;
            }

            // Generate a random salt
            byte[] salt = PasswordUtils.generateSalt();

            // Hash the password with the salt
            String hashedPassword = PasswordUtils.hashPassword(password, salt);

            // Create a new user with the hashed password and salt
            User newUser = new User.UserBuilder().email(email).password(hashedPassword).salt(Base64.getEncoder().encodeToString(salt)) // Store salt as a Base64 encoded string
                    .build();

            // Save the user to the database
            userDao.add(newUser);

            // Redirect to the login page after successful registration
            response.sendRedirect(request.getContextPath() + "/auth/register?success=true");

        } catch (Exception e) {
            logger.severe("Error during registration: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/register?error=" + e.getMessage());
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false); // Get the current session (if exists)
            if (session != null) {
                session.invalidate(); // Invalidate session to log the user out
            }
            response.sendRedirect(request.getContextPath() + "/auth/login?logout=true");
        } catch (IOException e) {
            logger.severe("Error during logout: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/login?error=logout_error");
        }
    }
}
