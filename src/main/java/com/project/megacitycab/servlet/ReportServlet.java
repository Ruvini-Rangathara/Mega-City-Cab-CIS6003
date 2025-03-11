package com.project.megacitycab.servlet;

import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.dto.DriverDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.dto.VehicleDriverDTO;
import com.project.megacitycab.service.ServiceFactory;
import com.project.megacitycab.service.ServiceType;
import com.project.megacitycab.service.custom.BookingService;
import com.project.megacitycab.service.custom.CustomerService;
import com.project.megacitycab.service.custom.VehicleDriverService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "reportServlet", value = "/report-servlet")
public class ReportServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ReportServlet.class.getName());
    private BookingService bookingService;
    private CustomerService customerService;
    private VehicleDriverService vehicleDriverService;

    public void init() {
        bookingService = ServiceFactory.getInstance().getService(ServiceType.BOOKING_SERVICE_IMPL);
        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER_SERVICE_IMPL);
        vehicleDriverService = ServiceFactory.getInstance().getService(ServiceType.VEHICLE_DRIVER_SERVICE_IMPL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("type");
        String timeFilter = request.getParameter("timeFilter");

        if (timeFilter == null) {
            timeFilter = "all"; // Default to "all time" if not specified
        }

        try {
            if ("customer".equals(reportType)) {
                generateCustomerReport(request, response, timeFilter);
            } else if ("vehicleDriver".equals(reportType)) {
                generateVehicleDriverReport(request, response, timeFilter);
            } else if ("booking".equals(reportType)) {
                generateBookingReport(request, response, timeFilter);
            } else {
                generateCustomerReport(request, response, timeFilter);
            }
        } catch (SQLException ex) {
            LOGGER.severe("Database error in ReportServlet: " + ex.getMessage());
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/reports.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            LOGGER.severe("Unexpected error in ReportServlet: " + ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/report-servlet?error=Unexpected error: " + ex.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reportType = request.getParameter("reportType");
        String timeFilter = request.getParameter("timeFilter");

        if (timeFilter == null) {
            timeFilter = "all";
        }

        response.sendRedirect(request.getContextPath() + "/report-servlet?type=" + reportType + "&timeFilter=" + timeFilter);
    }

    private void generateCustomerReport(HttpServletRequest request, HttpServletResponse response, String timeFilter)
            throws SQLException, ServletException, IOException {

        List<CustomerDTO> customers = customerService.getAll(null);

        // Filter logic based on time filter if needed
        List<CustomerDTO> filteredCustomers = filterCustomersByDate(customers, timeFilter);

        request.setAttribute("customers", filteredCustomers);
        request.setAttribute("activeTab", "customer");
        request.setAttribute("timeFilter", timeFilter);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/reports.jsp");
        dispatcher.forward(request, response);
    }

    private void generateVehicleDriverReport(HttpServletRequest request, HttpServletResponse response, String timeFilter)
            throws SQLException, ServletException, IOException {
        // Get all vehicles and drivers
        List<VehicleDriverDTO> vehicleDrivers = vehicleDriverService.getVehicleDriver();

        // Filter logic based on time filter
        List<VehicleDriverDTO> filteredVehicleDrivers = filterVehicleDriversByDate(vehicleDrivers, timeFilter);

        request.setAttribute("vehicleDrivers", filteredVehicleDrivers);
        request.setAttribute("activeTab", "vehicleDriver");
        request.setAttribute("timeFilter", timeFilter);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/reports.jsp");
        dispatcher.forward(request, response);
    }

    private void generateBookingReport(HttpServletRequest request, HttpServletResponse response, String timeFilter)
            throws SQLException, ServletException, IOException, ClassNotFoundException {

        Map<String, String> searchParams = new HashMap<>();
        List<BookingDTO> bookings = bookingService.getAll(searchParams);

        // Filter bookings based on time period
        List<BookingDTO> filteredBookings = filterBookingsByDate(bookings, timeFilter);

        // Get customer and vehicle information for the bookings
        Map<String, CustomerDTO> customerMap = new HashMap<>();
        Map<String, VehicleDTO> vehicleMap = new HashMap<>();
        Map<String, DriverDTO> driverMap = new HashMap<>();

        for (BookingDTO booking : filteredBookings) {
            if (booking.getCustomerId() != null) {
                CustomerDTO customer = customerService.searchById(booking.getCustomerId());
                if (customer != null) customerMap.put(booking.getId(), customer);
            }

            if (booking.getVehicleId() != null) {
                VehicleDTO vehicle = vehicleDriverService.findVehicleByVehicleId(booking.getVehicleId());
                if (vehicle != null) {
                    vehicleMap.put(booking.getId(), vehicle);

                    // Get the driver for this vehicle
                    DriverDTO driver = vehicleDriverService.findDriverByVehicleId(vehicle.getId());
                    if (driver != null) driverMap.put(booking.getId(), driver);
                }
            }
        }

        request.setAttribute("bookings", filteredBookings);
        request.setAttribute("customerMap", customerMap);
        request.setAttribute("vehicleMap", vehicleMap);
        request.setAttribute("driverMap", driverMap);
        request.setAttribute("activeTab", "booking");
        request.setAttribute("timeFilter", timeFilter);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/reports.jsp");
        dispatcher.forward(request, response);
    }

    private List<BookingDTO> filterBookingsByDate(List<BookingDTO> bookings, String timeFilter) {
        LocalDate today = LocalDate.now();

        if ("all".equals(timeFilter)) {
            return bookings; // Return all bookings
        } else if ("7days".equals(timeFilter)) {
            LocalDate sevenDaysAgo = today.minusDays(7);
            return bookings.stream()
                    .filter(booking -> !booking.getBookingDate().isBefore(sevenDaysAgo))
                    .collect(Collectors.toList());
        } else if ("30days".equals(timeFilter)) {
            LocalDate thirtyDaysAgo = today.minusDays(30);
            return bookings.stream()
                    .filter(booking -> !booking.getBookingDate().isBefore(thirtyDaysAgo))
                    .collect(Collectors.toList());
        } else {
            return bookings; // Default to all bookings
        }
    }

    private List<CustomerDTO> filterCustomersByDate(List<CustomerDTO> customers, String timeFilter) {
        // For simplicity, we'll need to get all bookings first to filter customers
        // based on booking activity in the given time period
        try {
            Map<String, String> searchParams = new HashMap<>();
            List<BookingDTO> bookings = bookingService.getAll(searchParams);

            if ("all".equals(timeFilter)) {
                return customers; // Return all customers
            } else {
                LocalDate today = LocalDate.now();
                LocalDate cutoffDate = today;

                if ("7days".equals(timeFilter)) {
                    cutoffDate = today.minusDays(7);
                } else if ("30days".equals(timeFilter)) {
                    cutoffDate = today.minusDays(30);
                }

                final LocalDate filterDate = cutoffDate;

                // Get customer IDs that have bookings in the selected time period
                List<String> customerIdsWithRecentBookings = bookings.stream()
                        .filter(booking -> !booking.getBookingDate().isBefore(filterDate))
                        .map(BookingDTO::getCustomerId)
                        .distinct()
                        .toList();

                // Filter customers based on their booking activity
                return customers.stream()
                        .filter(customer -> customerIdsWithRecentBookings.contains(customer.getId()))
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            LOGGER.severe("Error filtering customers by date: " + ex.getMessage());
            return customers; // Return all customers if there's an error
        }
    }

    private List<VehicleDriverDTO> filterVehicleDriversByDate(List<VehicleDriverDTO> vehicleDrivers, String timeFilter) {
        // If time filter is "all", return all vehicle drivers
        if ("all".equals(timeFilter)) {
            return vehicleDrivers;
        }

        try {
            LocalDate today = LocalDate.now();
            LocalDate cutoffDate = today;

            if ("7days".equals(timeFilter)) {
                cutoffDate = today.minusDays(7);
            } else if ("30days".equals(timeFilter)) {
                cutoffDate = today.minusDays(30);
            }

            final LocalDate filterDate = cutoffDate;

            // Assuming VehicleDriverDTO has getCreatedDate() or similar method for filtering
            // If such a method doesn't exist, you might need to modify this filtering logic
            return vehicleDrivers.stream()
                    .filter(vd -> {
                        // Replace this with the appropriate date field from VehicleDriverDTO
                        // For example: vd.getVehicle().getRegistrationDate()
                        LocalDate dateToCheck = LocalDate.parse(vd.getVehicle().getCreatedAt());
                        return !dateToCheck.isBefore(filterDate);
                    })
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.severe("Error filtering vehicle drivers by date: " + ex.getMessage());
            return vehicleDrivers; // Return all vehicle drivers if there's an error
        }
    }

}
