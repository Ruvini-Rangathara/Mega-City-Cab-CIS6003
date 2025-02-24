package com.project.megacitycab.servlet;

import com.project.megacitycab.constant.BookingStatus;
import com.project.megacitycab.dto.BookingDTO;
import com.project.megacitycab.dto.CustomerDTO;
import com.project.megacitycab.dto.VehicleDTO;
import com.project.megacitycab.dto.VehicleDriverDTO;
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
import java.util.*;

@WebServlet(name = "bookingServlet", value = "/booking-servlet")
public class BookingServlet extends HttpServlet {
    private BookingService bookingService;
    private CustomerService customerService;
    private VehicleDriverService vehicleDriverService;
    public void init() {
        bookingService = new BookingServiceImpl();
        customerService = new CustomerServiceImpl();
        vehicleDriverService = new VehicleDriverServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("view".equals(action)) {
                viewBooking(request, response);
            } else {
                // Default to listing bookings with optional filters
                listBookings(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                createBooking(request, response);
            } else if ("update".equals(action)) {
                updateBooking(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/booking-list.jsp?error=Invalid action");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listBookings(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // Collect search parameters from request
        Map<String, String> searchParams = new HashMap<>();

        if (request.getParameter("searchDate") != null && !request.getParameter("searchDate").isEmpty()) {
            searchParams.put("searchDate", request.getParameter("searchDate"));
        }
        if (request.getParameter("searchCustomer") != null && !request.getParameter("searchCustomer").isEmpty()) {
            searchParams.put("searchCustomer", request.getParameter("searchCustomer"));
        }
        if (request.getParameter("searchStatus") != null && !request.getParameter("searchStatus").isEmpty()) {
            searchParams.put("searchStatus", request.getParameter("searchStatus").toUpperCase());
        }

        // Call getAll with search parameters
        List<BookingDTO> bookings = bookingService.getAll(searchParams);

        // Set attributes and forward request
        request.setAttribute("bookings", bookings);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/booking-list.jsp");
        dispatcher.forward(request, response);
    }

    private void viewBooking(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String bookingId = request.getParameter("id");
        BookingDTO booking = bookingService.searchById(bookingId);

        if (booking != null) {
            request.setAttribute("booking", booking);
            // Get customers for dropdown using builder pattern if applicable
            List<CustomerDTO> customers = customerService.getAll(null);
            request.setAttribute("customers", customers);

            // Get available vehicles for this booking time
            List<VehicleDriverDTO> availableVehicles = getAvailableVehicles(
                    booking.getBookingDate(),
                    booking.getPickupTime(),
                    booking.getReleaseTime());
            request.setAttribute("availableVehicles", availableVehicles);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/booking-form.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/booking-list.jsp?error=Booking not found");
        }
    }

    private void createBooking(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // Extract form data
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

        // Build BookingDTO using builder pattern
        BookingDTO booking = new BookingDTO.BookingDTOBuilder()
                .customerId(customerId)
                .vehicleId(vehicleId)
                .bookingDate(bookingDate)
                .pickupTime(pickupTime)
                .releaseTime(releaseTime)
                .pickupLocation(pickupLocation)
                .destination(destination)
                .distance(distance)
                .fare(fare)
                .discount(discount)
                .tax(tax)
                .netTotal(netTotal)
                .status(status)
                .build();

        boolean success = bookingService.add(booking);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/booking-list.jsp?success=Booking created successfully");
        } else {
            response.sendRedirect(request.getContextPath() + "/booking-form.jsp?error=Failed to create booking");
        }
    }

    private void updateBooking(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String bookingId = request.getParameter("bookingId");
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

        // Build updated BookingDTO using builder pattern
        BookingDTO booking = new BookingDTO.BookingDTOBuilder()
                .id(bookingId)
                .customerId(customerId)
                .vehicleId(vehicleId)
                .bookingDate(bookingDate)
                .pickupTime(pickupTime)
                .releaseTime(releaseTime)
                .pickupLocation(pickupLocation)
                .destination(destination)
                .distance(distance)
                .fare(fare)
                .discount(discount)
                .tax(tax)
                .netTotal(netTotal)
                .status(status)
                .build();

        boolean success = bookingService.update(booking);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/booking-list.jsp?success=Booking updated successfully");
        } else {
            response.sendRedirect(request.getContextPath() + "/booking-form.jsp?id=" + bookingId + "&error=Failed to update booking");
        }
    }

    /**
     * Prepare form data for creating a new booking
     */
    public void prepareNewBookingForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // Get current date for default booking date
        LocalDate today = LocalDate.now();

        // Get all customers for customer dropdown
        List<CustomerDTO> customers = customerService.getAll(null);
        request.setAttribute("customers", customers);

        // Get available vehicles for today (default)
        List<VehicleDriverDTO> availableVehicles = getAvailableVehicles(today, null, null);
        request.setAttribute("availableVehicles", availableVehicles);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/booking-form.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Get available vehicles for a specific date and time range.
     * This is the key logic for finding vehicles that are not already booked.
     */
    private List<VehicleDriverDTO> getAvailableVehicles(LocalDate date, LocalTime startTime, LocalTime endTime)
            throws SQLException {
        List<VehicleDriverDTO> availableVehicleDrivers = new ArrayList<>();

        // If no specific time is provided, use default business hours
        if (startTime == null) {
            startTime = LocalTime.of(8, 0); // 8 AM
        }
        if (endTime == null) {
            endTime = LocalTime.of(20, 0); // 8 PM
        }

        // Step 1: Get all vehicles
        List<VehicleDTO> allVehicles = vehicleDriverService.getAllVehicles(null);

        // Step 2: Get all bookings for the given date
        List<BookingDTO> bookingsOnDate = bookingService.findByDate(date);

        // Step 3: Filter out vehicles that are already booked during the requested time slot
        for (VehicleDTO vehicle : allVehicles) {
            boolean isAvailable = true;

            // Check if this vehicle is booked during the requested time
            for (BookingDTO booking : bookingsOnDate) {
                // Skip cancelled bookings
                if (booking.getStatus() == BookingStatus.cancelled) {
                    continue;
                }

                // Skip if it's not the same vehicle
                if (!booking.getVehicleId().equals(vehicle.getId())) {
                    continue;
                }

                // Check if there's a time overlap
                LocalTime bookingStart = booking.getPickupTime();
                LocalTime bookingEnd = booking.getReleaseTime();

                // Time overlap check: if the requested time intersects with an existing booking
                if (!(endTime.isBefore(LocalTime.from(bookingStart)) || startTime.isAfter(LocalTime.from(bookingEnd)))) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                // Build VehicleDriverDTO using builder pattern
                VehicleDriverDTO vehicleDriver = new VehicleDriverDTO.VehicleDriverDTOBuilder()
                        .vehicle(vehicle)
                        .driver(vehicleDriverService.findDriverByVehicleId(vehicle.getId()))
                        .build();
                availableVehicleDrivers.add(vehicleDriver);
            }
        }

        return availableVehicleDrivers;
    }
}
