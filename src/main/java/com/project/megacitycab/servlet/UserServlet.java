package com.project.megacitycab.servlet;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.dto.UserDTO;
import com.project.megacitycab.service.custom.UserService;
import com.project.megacitycab.service.custom.impl.UserServiceImpl;
import com.project.megacitycab.util.SendResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private UserService userService;

    public void init() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                logger.log(Level.SEVERE, "Error : Invalid Action");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Action");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "search":
                searchUser(request, response);
                break;
            case "getAll":
                getAllUsers(request, response);
                break;
            default:
                logger.log(Level.SEVERE, "Error : Invalid Action");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Action");
        }

    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Updating user");
        try {
            if (request.getParameter("email") == null || request.getParameter("password") == null) {
                SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Email and password are required");
                return;
            }

            UserDTO userDTO = new UserDTO.UserDTOBuilder().id(request.getParameter("id")).name(request.getParameter("name")).email(request.getParameter("email")).password(request.getParameter("password")).role(request.getParameter("role") != null ? Role.valueOf(request.getParameter("role")) : Role.user).build();

            boolean isUpdated = userService.update(userDTO);

            if (isUpdated) {
                SendResponse.send(response, HttpServletResponse.SC_OK, "User updated successfully");
                return;  // Stop execution after successful update
            }

            // Only reach this if update failed
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update user");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating user: " + e.getMessage());
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Deleting user");
        try {
            String id = request.getParameter("id");

            if (id == null) {
                SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
                return;
            }

            boolean isDeleted = userService.delete(id);
            if (isDeleted) {
                SendResponse.send(response, HttpServletResponse.SC_OK, "User deleted successfully");
            } else {
                SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete user");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting user: " + e.getMessage());
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Searching user");
        try {
            UserDTO userDTO = userService.searchById(request.getParameter("id"));
            if (userDTO != null) {
                SendResponse.send(response, HttpServletResponse.SC_OK, "" + userDTO);
            } else {
                SendResponse.send(response, HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching user: " + e.getMessage());
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Getting all users");
        try {
            Map<String, String> searchParams = new HashMap<>();
            List<UserDTO> users = userService.getAll(searchParams);

            if (!users.isEmpty()) {
                SendResponse.send(response, HttpServletResponse.SC_OK, "" + users);
            } else {
                SendResponse.send(response, HttpServletResponse.SC_NOT_FOUND, "No users found");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching users: " + e.getMessage());
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
