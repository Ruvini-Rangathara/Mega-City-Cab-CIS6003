package com.project.megacitycab.servlet;

import com.project.megacitycab.constant.Role;
import com.project.megacitycab.dto.UserDTO;
import com.project.megacitycab.service.custom.UserService;
import com.project.megacitycab.service.custom.impl.UserServiceImpl;
import com.project.megacitycab.util.exception.MegaCityCabException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "userServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {
    private UserService userService;

    public void init() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            return;
        }

        try {
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
                    sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (MegaCityCabException e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getExceptionType());
        } catch (Exception e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            return;
        }

        try {
            switch (action) {
                case "search":
                    searchUser(request, response);
                    break;
                case "getAll":
                    getAllUsers(request, response);
                    break;
                default:
                    sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (MegaCityCabException e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getExceptionType());
        } catch (Exception e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getParameter("email") == null || request.getParameter("password") == null) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Email and password are required");
                return;
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(request.getParameter("email"));
            userDTO.setPassword(request.getParameter("password"));
            userDTO.setRole(request.getParameter("role") != null ? Role.valueOf(request.getParameter("role")) : Role.USER);

            boolean isAdded = userService.add(userDTO);
            if (isAdded) {
                sendResponse(response, HttpServletResponse.SC_CREATED, "User added successfully");
            }
        } catch (MegaCityCabException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getParameter("email") == null || request.getParameter("password") == null) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Email and password are required");
                return;
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setId(request.getParameter("id"));
            userDTO.setEmail(request.getParameter("email"));
            userDTO.setPassword(request.getParameter("password"));
            userDTO.setRole(request.getParameter("role") != null ? Role.valueOf(request.getParameter("role")) : Role.USER);

            boolean isUpdated = userService.update(userDTO);

            if (isUpdated) {
                sendResponse(response, HttpServletResponse.SC_OK, "User updated successfully");
                return;  // Stop execution after successful update
            }

            // Only reach this if update failed
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update user");

        } catch (MegaCityCabException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String email = request.getParameter("id");

            if (email == null) {
                sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
                return;
            }

            boolean isDeleted = userService.delete(email);
            if (isDeleted) {
                sendResponse(response, HttpServletResponse.SC_OK, "User deleted successfully");
            } else {
                sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete user");
            }
        } catch (MegaCityCabException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            UserDTO userDTO = userService.searchById(request.getParameter("id"));
            if (userDTO != null) {
                sendResponse(response, HttpServletResponse.SC_OK, "" + userDTO);
            } else {
                sendResponse(response, HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (MegaCityCabException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<UserDTO> users = userService.getAll();

            if (!users.isEmpty()) {
                sendResponse(response, HttpServletResponse.SC_OK, "" + users);
            } else {
                sendResponse(response, HttpServletResponse.SC_NOT_FOUND, "No users found");
            }
        } catch (MegaCityCabException e) {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            sendResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void sendResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(code);
        response.setContentType("application/json");

        // Manually construct JSON error response
        String jsonResponse = String.format("{\"status\": %d, \"message\": \"%s\"}", code, message);

        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
        writer.flush();
    }
}
