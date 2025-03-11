package com.project.megacitycab.servlet;

import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.service.ServiceFactory;
import com.project.megacitycab.service.ServiceType;
import com.project.megacitycab.service.custom.BookingService;
import com.project.megacitycab.service.custom.CustomerService;
import com.project.megacitycab.util.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private BookingService bookingService;
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize services using ServiceFactory
        bookingService = ServiceFactory.getInstance().getService(ServiceType.BOOKING_SERVICE_IMPL);
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER_SERVICE_IMPL);
        if (bookingService == null || customerService == null) {
            throw new ServletException("Failed to initialize services: BookingService or CustomerService is null");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            // Fetch data from BookingService
            double totalRevenue = bookingService.getTotalRevenue();
            double totalVehicleEarnings = bookingService.getTotalVehicleEarnings();
            double totalDriverEarnings = bookingService.getTotalDriverEarnings();
            long totalCustomers = bookingService.getTotalCustomers();
            long totalBookings = bookingService.getTotalBookings();
            List<BookingDTO> recentBookings = bookingService.getRecentBookings(5);
            double totalExpenses = bookingService.getTotalExpenses();

            // Set attributes for JSP
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("totalVehicleEarnings", totalVehicleEarnings);
            request.setAttribute("totalDriverEarnings", totalDriverEarnings);
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("totalBookings", totalBookings);
            request.setAttribute("recentBookings", recentBookings);
            request.setAttribute("totalExpenses", totalExpenses);

            // Forward to dashboard.jsp
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("sendEmail".equals(action)) {
            try {
                // Fetch data again for email (or store in session if preferred)
                double totalRevenue = bookingService.getTotalRevenue();
                double totalVehicleEarnings = bookingService.getTotalVehicleEarnings();
                double totalDriverEarnings = bookingService.getTotalDriverEarnings();
                long totalCustomers = bookingService.getTotalCustomers();
                long totalBookings = bookingService.getTotalBookings();
                double totalExpenses = bookingService.getTotalExpenses();


                // Print demo email to terminal
                String recipientEmail = "admin@megacitycab.com";
                String recipientName = "Administrator";
                EmailUtil.printDashboardEmail(recipientEmail, recipientName, totalRevenue, totalVehicleEarnings,
                        totalDriverEarnings, totalCustomers, totalBookings, totalExpenses);

                // Set success message
                response.sendRedirect(request.getContextPath() + "/dashboard?success=Email+preview+sent+to+terminal");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/dashboard?error=Failed+to+send+email+preview");
            }
        } else {
            // If no specific action, treat as GET
            doGet(request, response);
        }
    }
}
