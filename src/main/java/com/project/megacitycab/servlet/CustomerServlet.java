package com.project.megacitycab.servlet;

import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.service.custom.CustomerService;
import com.project.megacitycab.service.custom.impl.CustomerServiceImpl;
import com.project.megacitycab.util.SendResponse;
import com.project.megacitycab.util.exception.MegaCityCabException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "customerServlet", value = "/customer-servlet")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() {
        customerService = new CustomerServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action == null) {
            SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            return;
        }

        try {
            switch (action) {
                case "add":
                    addCustomer(request, response);
                    break;
                case "update":
                    updateCustomer(request, response);
                    break;
                case "delete":
                    deleteCustomer(request, response);
                    break;
                default:
                    SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (MegaCityCabException e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getExceptionType());
        } catch (Exception e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action == null) {
            SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            return;
        }

        try {
            switch (action) {
                case "search":
                    searchCustomer(request, response);
                    break;
                case "getAll":
                    getAllCustomers(request, response);
                    break;
                default:
                    SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (MegaCityCabException e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getExceptionType());
        } catch (Exception e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Validate all fields as required
            if (request.getParameter("name") == null || request.getParameter("email") == null || request.getParameter("registrationNo") == null || request.getParameter("address") == null || request.getParameter("nic") == null || request.getParameter("mobileNo") == null || request.getParameter("dob") == null) {
                SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Fill the required Fields");
                return;
            }

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setRegistrationNo(request.getParameter("registrationNo"));
            customerDTO.setName(request.getParameter("name"));
            customerDTO.setAddress(request.getParameter("address"));
            customerDTO.setNic(request.getParameter("nic"));

            // Parse date of birth if provided (expecting the format yyyy-MM-dd)
            String dobStr = request.getParameter("dob");
            if (dobStr != null && !dobStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dob = sdf.parse(dobStr);
                    customerDTO.setDob(dob);
                } catch (ParseException e) {
                    SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid date format for dob. Expected yyyy-MM-dd");
                    return;
                }
            }

            customerDTO.setMobileNo(request.getParameter("mobileNo"));
            customerDTO.setEmail(request.getParameter("email"));
            // Optionally, you can set createdAt or other audit fields if needed

            boolean isAdded = customerService.add(customerDTO);
            if (isAdded) {
                SendResponse.send(response, HttpServletResponse.SC_CREATED, "Customer added successfully");
            } else {
                SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add customer");
            }
        } catch (MegaCityCabException e) {
            SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getParameter("name") == null || request.getParameter("email") == null || request.getParameter("registrationNo") == null || request.getParameter("address") == null || request.getParameter("nic") == null || request.getParameter("mobileNo") == null || request.getParameter("dob") == null) {
                SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Fill the required Fields");
                return;
            }

            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(request.getParameter("id"));
            customerDTO.setRegistrationNo(request.getParameter("registrationNo"));
            customerDTO.setName(request.getParameter("name"));
            customerDTO.setAddress(request.getParameter("address"));
            customerDTO.setNic(request.getParameter("nic"));

            String dobStr = request.getParameter("dob");
            if (dobStr != null && !dobStr.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dob = sdf.parse(dobStr);
                    customerDTO.setDob(dob);
                } catch (ParseException e) {
                    SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid date format for dob. Expected yyyy-MM-dd");
                    return;
                }
            }

            customerDTO.setMobileNo(request.getParameter("mobileNo"));
            customerDTO.setEmail(request.getParameter("email"));
            // Optionally, update audit fields like updatedAt if needed

            boolean isUpdated = customerService.update(customerDTO);
            if (isUpdated) {
                SendResponse.send(response, HttpServletResponse.SC_OK, "Customer updated successfully");
            } else {
                SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update customer");
            }
        } catch (MegaCityCabException e) {
            SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // For deletion, the customer ID is required.
            String id = request.getParameter("id");
            if (id == null) {
                SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Customer ID is required");
                return;
            }
            if (customerService.delete(id)) {
                SendResponse.send(response, HttpServletResponse.SC_OK, "Customer deleted successfully");
            }
        } catch (MegaCityCabException e) {
            SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void searchCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = request.getParameter("id");
            if (id == null) {
                SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Customer ID is required for search");
                return;
            }

            CustomerDTO customerDTO = customerService.searchById(id);
            if (customerDTO != null) {
                SendResponse.send(response, HttpServletResponse.SC_OK, customerDTO.toString());
            } else {
                SendResponse.send(response, HttpServletResponse.SC_NOT_FOUND, "Customer not found");
            }
        } catch (MegaCityCabException e) {
            SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void getAllCustomers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<CustomerDTO> customers = customerService.getAll();
            if (customers != null && !customers.isEmpty()) {
                SendResponse.send(response, HttpServletResponse.SC_OK, customers.toString());
            } else {
                SendResponse.send(response, HttpServletResponse.SC_NOT_FOUND, "No customers found");
            }
        } catch (MegaCityCabException e) {
            SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, e.getExceptionType());
        } catch (Exception e) {
            SendResponse.send(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
