package com.project.megacitycab.servlet;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.dto.UserDTO;
import com.project.megacitycab.service.ServiceFactory;
import com.project.megacitycab.service.ServiceType;
import com.project.megacitycab.service.custom.UserService;
import com.project.megacitycab.util.EmailUtil;
import com.project.megacitycab.util.security.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private UserService userService;

    @Override
    public void init() {
        userService = ServiceFactory.getInstance().getService(ServiceType.USER_SERVICE_IMPL);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Handling POST request for authentication");
        String pathInfo = request.getPathInfo();

        switch (pathInfo) {
            case "/login":
                login(request, response);
                break;
            case "/register":
                register(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            default:
                logger.log(Level.SEVERE, "Error : Invalid Action");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Action");
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        switch (pathInfo) {
            case "/login":
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;
            case "/register":
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            default:
                logger.log(Level.SEVERE, "Error : Invalid Action");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Action");
        }
    }


    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.log(Level.INFO, "Logging in user");

            // Get form parameters from the login form
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Validate input
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/auth/login?error=invalid_input");
                return;
            }

            // Fetch user from the database
            UserDTO user = userService.searchByEmail(email);
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
            response.sendRedirect(request.getContextPath() + "/dashboard");
//            response.sendRedirect(request.getContextPath() + "/views/customer.jsp");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during login: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/login?error=system_error");
        }
    }


    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.log(Level.INFO, "Registering user");

            // Check if the user already exists
            UserDTO user = userService.searchByEmail(request.getParameter("email"));
            if (user != null) {
                response.sendRedirect(request.getContextPath() + "/auth/register?error=User already exists for " + request.getParameter("email"));
                return;
            }

            UserDTO userDTO = new UserDTO.UserDTOBuilder().name(request.getParameter("name")).email(request.getParameter("email")).password(request.getParameter("password")).role(request.getParameter("role") != null ? Role.valueOf(request.getParameter("role")) : Role.user).build();

            userService.add(userDTO);

            // Send generic email
            String recipientEmail = userDTO.getEmail();
            String recipientName = userDTO.getName();
            String emailBody = "Welcome to Mega City Cab!\n\n" + "Your account has been successfully registered with the following details:\n" + "  Name:  " + userDTO.getName() + "\n" + "  Email: " + userDTO.getEmail() + "\n" + "You can now log in using your email and password.\n";
            EmailUtil.printGenericEmail(recipientEmail, "Welcome to Mega City Cab", emailBody, recipientName);

            // Redirect to the login page after successful registration
            response.sendRedirect(request.getContextPath() + "/auth/register?success=true");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during registration: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/register?error=" + e.getMessage());
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.log(Level.INFO, "Initiating user logout");

            // Get the current session, if it exists, without creating a new one
            HttpSession session = request.getSession(false);
            if (session != null) {
                // Optionally log user details before invalidation (for auditing)
                String userEmail = (String) session.getAttribute("userEmail");
                if (userEmail != null) {
                    logger.log(Level.INFO, "Logging out user with email: {0}", userEmail);
                } else {
                    logger.log(Level.INFO, "Logging out an unidentified session");
                }

                // Invalidate the session to remove all attributes
                session.invalidate();
                logger.log(Level.INFO, "Session invalidated successfully");
            } else {
                logger.log(Level.WARNING, "No active session found for logout request");
            }

            // Set a success status before redirecting (optional)
            response.setStatus(HttpServletResponse.SC_OK);

            // Redirect to the login page with a success message
            response.sendRedirect(request.getContextPath() + "/auth/login?success=logged_out");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "IO Error during logout: {0}", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/login?error=logout_failed");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error during logout: {0}", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/auth/login?error=system_error");
        }
    }

}
