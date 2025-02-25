<%--
  Created by IntelliJ IDEA.
  User: ruvini
  Date: 2025-02-24
  Time: 16.31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.project.megacitycab.dto.BookingDTO" %>
<%@ page import="com.project.megacitycab.dto.CustomerDTO" %>
<%@ page import="com.project.megacitycab.dto.VehicleDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Management - Mega City Cab Service</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #fca311;
            --secondary-color: #6c757d;
            --background-color: #f8f9fa;
        }

        body {
            background-color: var(--background-color);
            min-height: 100vh;
            padding-top: 60px;
        }

        .navbar {
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .navbar-brand {
            color: var(--primary-color);
            font-weight: bold;
            font-size: 1.5rem;
        }

        .top-section {
            margin-top: 2rem;
            background-color: white;
            border-radius: 1rem;
            padding: 1.5rem;
            margin-bottom: 2rem;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
        }

        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        .section-title {
            color: #212529;
            font-weight: 600;
            font-size: 1.25rem;
            margin: 0;
        }

        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
        }

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: #e59100;
            border-color: #e59100;
        }

        .status-pending {
            color: #fd7e14;
            background-color: #fff3cd;
            border-radius: 0.25rem;
            padding: 0.25rem 0.5rem;
        }

        .status-confirmed {
            color: #198754;
            background-color: #d1e7dd;
            border-radius: 0.25rem;
            padding: 0.25rem 0.5rem;
        }

        .status-cancelled {
            color: #dc3545;
            background-color: #f8d7da;
            border-radius: 0.25rem;
            padding: 0.25rem 0.5rem;
        }

        .status-completed {
            color: #0dcaf0;
            background-color: #cff4fc;
            border-radius: 0.25rem;
            padding: 0.25rem 0.5rem;
        }

        .btn-view {
            background-color: #ffeb99;
            border-color: #ffeb99;
            color: #000;
        }

        .btn-view:hover {
            background-color: #ffe066;
            border-color: #ffe066;
            color: #000;
        }

        .table-responsive {
            border-radius: 0.5rem;
        }

        .table {
            margin-bottom: 0;
        }

        .table thead th {
            background-color: #f8f9fa;
            border-bottom: 2px solid #dee2e6;
        }

        #successMessage, #errorMessage{
            margin-top: 2rem;
        }

        .alert {
            border-radius: 0.5rem;
        }

        .time-info {
            font-size: 0.85rem;
        }

        .time-label {
            font-weight: 500;
            color: #495057;
        }

        .customer-info {
            font-size: 0.9rem;
        }

        .customer-name {
            font-weight: 500;
        }

        .customer-mobile {
            color: #6c757d;
        }

        .vehicle-info {
            font-size: 0.9rem;
        }

        .vehicle-brand {
            font-weight: 500;
        }

        .vehicle-model {
            color: #6c757d;
        }
    </style>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">MEGA CITY CAB</a>
    </div>
</nav>

<div class="container">
    <!-- Alert Messages -->
    <div id="successMessage" class="alert alert-success alert-dismissible fade show d-none" role="alert">
        ${param.success}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <div id="errorMessage" class="alert alert-danger alert-dismissible fade show d-none" role="alert">
        ${param.error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>

    <!-- Top Section -->
    <div class="top-section">
        <div class="section-header">
            <h2 class="section-title">
                <i class="bi bi-calendar-check-fill me-2"></i>Booking Management
            </h2>
            <a href="${pageContext.request.contextPath}/views/booking-form.jsp" class="btn btn-primary">
                <i class="bi bi-plus-circle me-2"></i>New Booking
            </a>
        </div>

        <!-- Search Form -->
        <form id="searchForm" action="${pageContext.request.contextPath}/booking-servlet" method="get">
            <div class="row g-3">
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="date" class="form-control" id="searchDate" name="searchDate"
                               value="${param.searchDate}">
                        <label for="searchDate">Booking Date</label>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchCustomer" name="searchCustomer"
                               value="${param.searchCustomer}" placeholder="Customer Name or Mobile">
                        <label for="searchCustomer">Customer Name or Mobile</label>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-floating">
                        <select class="form-select" id="searchStatus" name="searchStatus">
                            <option value="">All Status</option>
                            <option value="PENDING">Pending</option>
                            <option value="CONFIRMED">Confirmed</option>
                            <option value="CANCELLED">Cancelled</option>
                            <option value="COMPLETED">Completed</option>
                        </select>
                        <label for="searchStatus">Status</label>
                    </div>
                </div>
            </div>
            <div class="d-flex justify-content-end gap-2 mt-3">
                <button type="button" class="btn btn-secondary" onclick="clearSearch()">
                    <i class="bi bi-x-circle me-2"></i>Clear Filter
                </button>
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search me-2"></i>Search
                </button>
            </div>
        </form>
    </div>

    <!-- Booking List -->
    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Customer</th>
                        <th>Date</th>
                        <th>Vehicle Schedule</th>
                        <th>Route</th>
                        <th>Vehicle</th>
                        <th>Amount</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<BookingDTO> bookings = (List<BookingDTO>) request.getAttribute("bookings");
                        Map<String, CustomerDTO> customerMap = (Map<String, CustomerDTO>) request.getAttribute("customerMap");
                        Map<String, VehicleDTO> vehicleMap = (Map<String, VehicleDTO>) request.getAttribute("vehicleMap");

                        if (bookings != null && !bookings.isEmpty()) {
                            int count = 1;
                            for (BookingDTO booking : bookings) {
                                CustomerDTO customer = customerMap.get(booking.getId());
                                VehicleDTO vehicle = vehicleMap.get(booking.getId());
                    %>
                    <tr>
                        <td><%= count++ %></td>
                        <td>
                            <div class="customer-info">
                                <% if (customer != null) { %>
                                <div class="customer-name"><%= customer.getName() %></div>
                                <div class="customer-mobile"><i class="bi bi-telephone"></i> <%= customer.getMobileNo() %></div>
                                <% } else { %>
                                <span class="text-muted">ID: <%= booking.getCustomerId() %></span>
                                <% } %>
                            </div>
                        </td>
                        <td><%= booking.getBookingDate() %></td>
                        <td>
                            <div class="time-info">
                                <span class="time-label"><i class="bi bi-clock"></i> Pickup:</span> <%= booking.getPickupTime() %><br>
                                <span class="time-label"><i class="bi bi-clock-history"></i> Release:</span> <%= booking.getReleaseTime() %>
                            </div>
                        </td>
                        <td>
                            From: <%= booking.getPickupLocation() %><br>
                            To: <%= booking.getDestination() %>
                        </td>
                        <td>
                            <div class="vehicle-info">
                                <% if (vehicle != null) { %>
                                <div class="vehicle-brand"><%= vehicle.getBrand() %></div>
                                <div class="vehicle-model"><%= vehicle.getModel() %></div>
                                <% } else { %>
                                <span class="text-muted">ID: <%= booking.getVehicleId() %></span>
                                <% } %>
                            </div>
                        </td>
                        <td>
                            Net Total: Rs. <%= booking.getNetTotal() %><br>
                            <small class="text-muted">Distance: <%= booking.getDistance() %> km</small>
                        </td>
                        <td>
                            <span class="status-<%= booking.getStatus().toString().toLowerCase() %>">
                                <%= booking.getStatus() %>
                            </span>
                        </td>
                        <td>
                            <div class="d-flex gap-2">
                                <a href="${pageContext.request.contextPath}/views/booking-form.jsp?id=<%= booking.getId() %>"
                                   class="btn btn-view btn-sm">
                                    <i class="bi bi-eye-fill"></i>
                                </a>
                            </div>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="9" class="text-center">No bookings found</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Show alert messages if they exist
        const successMessage = document.getElementById("successMessage");
        const errorMessage = document.getElementById("errorMessage");

        if (successMessage.textContent.trim()) {
            successMessage.classList.remove("d-none");
            setTimeout(() => successMessage.classList.add("d-none"), 5000);
        }
        if (errorMessage.textContent.trim()) {
            errorMessage.classList.remove("d-none");
            setTimeout(() => errorMessage.classList.add("d-none"), 5000);
        }

        // Set selected status in dropdown if it exists in URL
        const urlParams = new URLSearchParams(window.location.search);
        const statusParam = urlParams.get('searchStatus');
        if (statusParam) {
            document.getElementById('searchStatus').value = statusParam;
        }
    });

    function clearSearch() {
        document.getElementById('searchDate').value = '';
        document.getElementById('searchCustomer').value = '';
        document.getElementById('searchStatus').value = '';
        document.getElementById('searchForm').submit();
    }
</script>
</body>
</html>

