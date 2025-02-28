package com.project.megacitycab.servlet;

import com.project.megacitycab.constant.BookingStatus;
import com.project.megacitycab.dto.*;
import com.project.megacitycab.service.custom.BookingService;
import com.project.megacitycab.service.custom.CustomerService;
import com.project.megacitycab.service.custom.VehicleDriverService;
import com.project.megacitycab.service.custom.impl.BookingServiceImpl;
import com.project.megacitycab.service.custom.impl.CustomerServiceImpl;
import com.project.megacitycab.service.custom.impl.VehicleDriverServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(name = "bookingServlet", value = "/booking-servlet")
public class BookingServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BookingServlet.class.getName());
    private BookingService bookingService;
    private CustomerService customerService;
    private VehicleDriverService vehicleDriverService;

    public void init() {
        bookingService = new BookingServiceImpl();
        customerService = new CustomerServiceImpl();
        vehicleDriverService = new VehicleDriverServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("view".equals(action)) {
                viewBooking(request, response);
            } else if ("newForm".equals(action)) {
                prepareNewBookingForm(request, response);
            } else if ("searchVehicles".equals(action)) {
                searchAvailableVehicles(request, response);
            } else {
                listBookings(request, response);
            }
        } catch (SQLException ex) {
            LOGGER.severe("Database error in doGet: " + ex.getMessage());
            request.setAttribute("errorMessage", "Database error: " + ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/booking-list.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            LOGGER.severe("Unexpected error in doGet: " + ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/booking-servlet?error=Unexpected error: " + ex.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                createBooking(request, response);
            } else if ("update".equals(action)) {
                updateBooking(request, response);
            } else if ("search".equals(action)) {
                processSearchForm(request, response);
            } else if ("searchVehicles".equals(action)) {
                searchAvailableVehicles(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/booking-servlet?error=Invalid action");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Database error in doPost: " + ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/booking-servlet?error=" + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.severe("Unexpected error in doPost: " + ex.getMessage());
            response.sendRedirect(request.getContextPath() + "/booking-servlet?error=Unexpected error: " + ex.getMessage());
        }
    }

    private void processSearchForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchDate = request.getParameter("searchDate");
        String searchCustomer = request.getParameter("searchCustomer");
        String searchStatus = request.getParameter("searchStatus");

        StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/booking-servlet?");
        if (searchDate != null && !searchDate.isEmpty())
            redirectUrl.append("searchDate=").append(searchDate).append("&");
        if (searchCustomer != null && !searchCustomer.isEmpty())
            redirectUrl.append("searchCustomer=").append(searchCustomer).append("&");
        if (searchStatus != null && !searchStatus.isEmpty())
            redirectUrl.append("searchStatus=").append(searchStatus).append("&");

        response.sendRedirect(redirectUrl.toString());
    }

    private void listBookings(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Map<String, String> searchParams = new HashMap<>();
        String searchDate = request.getParameter("searchDate");
        String searchCustomer = request.getParameter("searchCustomer");
        String searchStatus = request.getParameter("searchStatus");

        if (searchDate != null && !searchDate.isEmpty()) {
            searchParams.put("searchDate", searchDate);
            request.setAttribute("searchDate", searchDate);
        }
        if (searchCustomer != null && !searchCustomer.isEmpty()) {
            searchParams.put("searchCustomer", searchCustomer);
            request.setAttribute("searchCustomer", searchCustomer);
        }
        if (searchStatus != null && !searchStatus.isEmpty()) {
            searchParams.put("searchStatus", searchStatus);
            request.setAttribute("searchStatus", searchStatus);
        }

        List<BookingDTO> bookings = bookingService.getAll(searchParams);
        Map<String, CustomerDTO> customerMap = new HashMap<>();
        Map<String, VehicleDTO> vehicleMap = new HashMap<>();

        for (BookingDTO booking : bookings) {
            if (booking.getCustomerId() != null) {
                CustomerDTO customer = customerService.searchById(booking.getCustomerId());
                if (customer != null) customerMap.put(booking.getId(), customer);
            }
            if (booking.getVehicleId() != null) {
                VehicleDTO vehicle = vehicleDriverService.findVehicleByVehicleId(booking.getVehicleId());
                if (vehicle != null) vehicleMap.put(booking.getId(), vehicle);
            }
        }

        request.setAttribute("bookings", bookings);
        request.setAttribute("customerMap", customerMap);
        request.setAttribute("vehicleMap", vehicleMap);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/booking-list.jsp");
        dispatcher.forward(request, response);
    }

    private void viewBooking(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String bookingId = request.getParameter("id");
        BookingDTO booking = bookingService.searchById(bookingId);

        if (booking != null) {
            request.setAttribute("booking", booking);
            CustomerDTO customer = customerService.searchById(booking.getCustomerId());
            request.setAttribute("customer", customer);
            VehicleDTO vehicle = vehicleDriverService.findVehicleByVehicleId(booking.getVehicleId());
            request.setAttribute("vehicle", vehicle);
            DriverDTO driver = vehicleDriverService.findDriverByVehicleId(vehicle.getId());
            request.setAttribute("driver", driver);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/booking-form.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/booking-servlet?error=Booking not found");
        }
    }

    private void createBooking(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            BookingDTO booking = buildBookingDTOFromRequest(request, null);
            boolean success = bookingService.add(booking);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/booking-servlet?success=Booking created successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/booking-servlet?action=newForm&error=Failed to create booking");
            }
        } catch (Exception e) {
            LOGGER.severe("Error in createBooking: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/booking-servlet?action=newForm&error=" + e.getMessage());
        }
    }

    private void updateBooking(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            String bookingId = request.getParameter("bookingId");
            BookingDTO booking = buildBookingDTOFromRequest(request, bookingId);
            boolean success = bookingService.update(booking);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/booking-servlet?success=Booking updated successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/booking-servlet?action=view&id=" + bookingId + "&error=Failed to update booking");
            }
        } catch (Exception e) {
            LOGGER.severe("Error in updateBooking: " + e.getMessage());
            String bookingId = request.getParameter("bookingId");
            response.sendRedirect(request.getContextPath() + "/booking-servlet?action=view&id=" + bookingId + "&error=" + e.getMessage());
        }
    }

    private void prepareNewBookingForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        LocalDate bookingDate = request.getParameter("bookingDate") != null ? LocalDate.parse(request.getParameter("bookingDate")) : LocalDate.now();
        LocalTime pickupTime = request.getParameter("pickupTime") != null ? LocalTime.parse(request.getParameter("pickupTime")) : LocalTime.of(8, 0);
        LocalTime releaseTime = request.getParameter("releaseTime") != null ? LocalTime.parse(request.getParameter("releaseTime")) : LocalTime.of(20, 0);

        List<CustomerDTO> customers = customerService.getAll(null);
        request.setAttribute("customers", customers);
        request.setAttribute("availableVehicles", null); // No vehicles initially
        request.setAttribute("bookingDate", bookingDate.toString());
        request.setAttribute("pickupTime", pickupTime.toString());
        request.setAttribute("releaseTime", releaseTime.toString());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/booking-form.jsp");
        dispatcher.forward(request, response);
    }

    private void searchAvailableVehicles(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        LocalDate bookingDate = LocalDate.parse(request.getParameter("bookingDate"));
        LocalTime pickupTime = LocalTime.parse(request.getParameter("pickupTime"));
        LocalTime releaseTime = LocalTime.parse(request.getParameter("releaseTime"));

        String customerId = request.getParameter("customerId");
        String pickupLocation = request.getParameter("pickupLocation");
        String destination = request.getParameter("destination");
        String distance = request.getParameter("distance");
        String discount = request.getParameter("discount");
        String tax = request.getParameter("tax");

        List<CustomerDTO> customers = customerService.getAll(null);
        List<VehicleDriverDTO> availableVehicles = vehicleDriverService.getAvailableVehicles(bookingDate, pickupTime, releaseTime);

        request.setAttribute("customers", customers);
        request.setAttribute("availableVehicles", availableVehicles);
        request.setAttribute("bookingDate", bookingDate.toString());
        request.setAttribute("pickupTime", pickupTime.toString());
        request.setAttribute("releaseTime", releaseTime.toString());

        if (customerId != null) request.setAttribute("customerId", customerId);
        if (pickupLocation != null) request.setAttribute("pickupLocation", pickupLocation);
        if (destination != null) request.setAttribute("destination", destination);
        if (distance != null) request.setAttribute("distance", distance);
        if (discount != null) request.setAttribute("discount", discount);
        if (tax != null) request.setAttribute("tax", tax);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/booking-form.jsp");
        dispatcher.forward(request, response);
    }

    private BookingDTO buildBookingDTOFromRequest(HttpServletRequest request, String bookingId) {
        String customerId = request.getParameter("customerId");
        String vehicleId = request.getParameter("vehicleId");
        LocalDate bookingDate = LocalDate.parse(request.getParameter("bookingDate"));
        LocalTime pickupTime = LocalTime.parse(request.getParameter("pickupTime"));
        LocalTime releaseTime = LocalTime.parse(request.getParameter("releaseTime"));
        String pickupLocation = request.getParameter("pickupLocation");
        String destination = request.getParameter("destination");
        double distance = Double.parseDouble(request.getParameter("distance"));
        double fare = Double.parseDouble(request.getParameter("fare"));
        double discount = Double.parseDouble(request.getParameter("discount"));
        double tax = Double.parseDouble(request.getParameter("tax"));
        double netTotal = Double.parseDouble(request.getParameter("netTotal"));
        BookingStatus status = BookingStatus.valueOf(request.getParameter("status").toUpperCase());

        return new BookingDTO.BookingDTOBuilder().id(bookingId).customerId(customerId).vehicleId(vehicleId).bookingDate(bookingDate).pickupTime(pickupTime).releaseTime(releaseTime).pickupLocation(pickupLocation).destination(destination).distance(distance).fare(fare).discount(discount).tax(tax).netTotal(netTotal).status(status).build();
    }
}
