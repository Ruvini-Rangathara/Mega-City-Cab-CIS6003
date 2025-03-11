package com.project.megacitycab.servlet;

import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.service.ServiceFactory;
import com.project.megacitycab.service.ServiceType;
import com.project.megacitycab.service.custom.CustomerService;
import com.project.megacitycab.service.custom.impl.CustomerServiceImpl;
import com.project.megacitycab.util.SendResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "customerServlet", value = "/customer-servlet")
public class CustomerServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CustomerServlet.class.getName());
    private CustomerService customerService;

    @Override
    public void init() {
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER_SERVICE_IMPL);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

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
            case "search":
                searchCustomer(request, response);
                break;
            default:
                logger.log(Level.SEVERE, "Error : Invalid Action");
                response.sendRedirect(request.getContextPath() + "/customers?error=Invalid action");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Create search parameters map
            Map<String, String> searchParams = new HashMap<>();
            searchParams.put("registrationNo", request.getParameter("searchRegNo"));
            searchParams.put("name", request.getParameter("searchName"));
            searchParams.put("email", request.getParameter("searchEmail"));
            searchParams.put("mobileNo", request.getParameter("searchMobile"));

            // Get filtered list of customers
            List<CustomerDTO> customers = customerService.getAll(searchParams);
            request.setAttribute("customers", customers);

            // Forward to JSP page
            request.getRequestDispatcher("/views/customer.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error retrieving customers: " + e.getMessage());
            request.getRequestDispatcher("/views/customer.jsp")
                    .forward(request, response);
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Add customer request received, Customer Registration No : " + request.getParameter("registrationNo"));
        try {
            // Parse date of birth (expected format: yyyy-MM-dd)
            Date dob = null;
            String dobStr = request.getParameter("dob");
            if (dobStr != null && !dobStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dob = sdf.parse(dobStr);
            }

            boolean isAdded = customerService.add(new CustomerDTO.CustomerDTOBuilder().registrationNo(request.getParameter("registrationNo")).name(request.getParameter("name")).address(request.getParameter("address")).nic(request.getParameter("nic")).dob(dob).mobileNo(request.getParameter("mobileNo")).email(request.getParameter("email")).build());
            if (isAdded) {
                response.sendRedirect(request.getContextPath() + "/customer-servlet?success=Customer successfully saved");
            } else {
                response.sendRedirect(request.getContextPath() + "/customer-servlet?error=Failed to save customer");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding customer: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/customer-servlet?error=" + e.getMessage());
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Update customer request received, Customer ID : " + request.getParameter("id"));
        try {
            // Parse the date of birth (expected format: yyyy-MM-dd)
            Date dob = null;
            String dobStr = request.getParameter("dob");
            if (dobStr != null && !dobStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dob = sdf.parse(dobStr);
            }
            // Update the customer
            boolean isUpdated = customerService.update(new CustomerDTO.CustomerDTOBuilder().id(request.getParameter("id")).registrationNo(request.getParameter("registrationNo")).name(request.getParameter("name")).address(request.getParameter("address")).nic(request.getParameter("nic")).dob(dob).mobileNo(request.getParameter("mobileNo")).email(request.getParameter("email")).build());
            if (isUpdated) {
                response.sendRedirect(request.getContextPath() + "/customer-servlet?success=Customer successfully updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/customer-servlet?error=Failed to update customer");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating customer: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/customer-servlet?error=" + e.getMessage());
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Delete customer request received, Customer ID : " + request.getParameter("id"));
        try {
            boolean isDeleted = customerService.delete(request.getParameter("id"));

            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/customer-servlet?success=Customer successfully deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/customer-servlet?error=Failed to delete customer");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting customer: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/customer-servlet?error=" + e.getMessage());
        }
    }

    private void searchCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "Search customer request received, Customer ID : " + request.getParameter("id"));
        try {
            String id = request.getParameter("id");
            if (id == null) {
                SendResponse.send(response, HttpServletResponse.SC_BAD_REQUEST, "Customer ID is required for search");
                return;
            }

            CustomerDTO customerDTO = customerService.searchById(id);
            request.setAttribute("customer", customerDTO);
            request.getRequestDispatcher("/views/customer.jsp").forward(request, response);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching customer", e);
            response.sendRedirect(request.getContextPath() + "/error.jsp?message=Error fetching customer");
        }
    }
}
