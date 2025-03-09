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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0-rc.0/css/select2.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #fca311;
            --secondary-color: #6c757d;
            --background-color: #f8f9fa;
            --success-color: #28a745;
            --danger-color: #dc3545;
        }

        body {
            background-color: var(--background-color);
            min-height: 100vh;
            padding-top: 40px;
            margin-left: 250px;
        }

        /* Sidebar Styles */
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 300px;
            height: 100vh;
            background-color: white;
            box-shadow: 2px 0 4px rgba(0, 0, 0, 0.1);
            z-index: 1000;
            padding-top: 20px;
        }

        .sidebar-brand {
            padding: 1rem 1.5rem;
            font-size: 1.5rem;
            font-weight: bold;
            color: var(--primary-color);
            border-bottom: 2px solid rgba(252, 163, 17, 0.3);
        }

        .sidebar-nav {
            list-style: none;
            padding: 0;
            margin-top: 20px;
        }

        .sidebar-nav li {
            padding: 0.75rem 1.5rem;
            transition: all 0.3s ease;
        }

        .sidebar-nav li:hover {
            background-color: rgba(252, 163, 17, 0.1);
        }

        .sidebar-nav li a {
            color: var(--secondary-color);
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }

        .sidebar-nav li a i {
            font-size: 1.25rem;
        }

        .sidebar-nav li.active {
            background-color: rgba(252, 163, 17, 0.2);
        }

        .sidebar-nav li.active a {
            color: var(--primary-color);
        }

        .sidebar-footer {
            position: absolute;
            bottom: 0;
            width: 100%;
            padding: 15px 20px;
            border-top: 1px solid rgba(0, 0, 0, 0.1);
            background-color: white;
        }

        .user-info {
            display: flex;
            align-items: center;
        }

        .user-avatar {
            width: 32px;
            height: 32px;
            background-color: var(--primary-color);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            color: white;
        }

        .user-details {
            margin-left: 10px;
        }

        .user-name {
            font-size: 14px;
            font-weight: 500;
            color: var(--secondary-color);
        }

        .user-role {
            font-size: 12px;
            color: var(--secondary-color);
        }

        .sidebar-divider {
            height: 1px;
            background-color: rgba(0, 0, 0, 0.1);
            margin: 30px 0;
        }

        /* Booking Management Styles */
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

        #successMessage, #errorMessage {
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
<!-- Side Navigation Bar -->
<aside class="sidebar">
    <div class="sidebar-brand">MEGA CITY CAB</div>
    <ul class="sidebar-nav">
        <li>
            <a href="${pageContext.request.contextPath}/">
                <i class="bi bi-house-door"></i>
                Dashboard
            </a>
        </li>
        <li class="active">
            <a href="${pageContext.request.contextPath}/booking-servlet">
                <i class="bi bi-calendar-check"></i>
                Bookings
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/vehicle-driver-servlet">
                <i class="bi bi-car-front"></i>
                Vehicles
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/driver-servlet">
                <i class="bi bi-person-badge"></i>
                Drivers
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/customer-servlet">
                <i class="bi bi-people"></i>
                Customers
            </a>
        </li>
        <div class="sidebar-divider"></div>
        <li>
            <a href="${pageContext.request.contextPath}/reports">
                <i class="bi bi-bar-chart"></i>
                Reports
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/settings">
                <i class="bi bi-gear"></i>
                Settings
            </a>
        </li>
    </ul>

    <!-- Sidebar Footer -->
    <div class="sidebar-footer">
        <div class="user-info">
            <div class="user-avatar">
                <% String currentUser = (String) session.getAttribute("username");
                    if (currentUser == null) currentUser = "User";
//                    out.print(currentUser.substring(0, 1).toUpperCase());
                %>
            </div>
            <div class="user-details">
                <div class="user-name"><%=currentUser%></div>
                <div class="user-role">Administrator</div>
            </div>
        </div>
    </div>
</aside>

<!-- Main Content -->
<div class="container">
    <!-- Alert Messages -->
    <%
        String success = request.getParameter("success");
        String error = request.getParameter("error");
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>
    <% if (success != null && !success.isEmpty()) { %>
    <div id="successMessage" class="alert alert-success alert-dismissible fade show" role="alert">
        <%= success %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% } %>
    <% if (error != null && !error.isEmpty()) { %>
    <div id="errorMessage" class="alert alert-danger alert-dismissible fade show" role="alert">
        <%= error %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% } else if (errorMessage != null && !errorMessage.isEmpty()) { %>
    <div id="errorMessage" class="alert alert-danger alert-dismissible fade show" role="alert">
        <%= errorMessage %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% } %>

    <!-- Top Section -->
    <div class="top-section">
        <div class="section-header">
            <h2 class="section-title">
                <i class="bi bi-calendar-check-fill me-2"></i>Booking Management
            </h2>
            <a href="${pageContext.request.contextPath}/booking-servlet?action=newForm" class="btn btn-primary">
                <i class="bi bi-plus-circle me-2"></i>New Booking
            </a>
        </div>

        <!-- Search Form -->
        <form id="searchForm" action="${pageContext.request.contextPath}/booking-servlet" method="get">
            <div class="row g-3">
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="date" class="form-control" id="searchDate" name="searchDate"
                               value="${searchDate}">
                        <label for="searchDate">Booking Date</label>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchCustomer" name="searchCustomer"
                               value="${searchCustomer}" placeholder="Customer Name or Mobile">
                        <label for="searchCustomer">Customer Name or Mobile</label>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-floating">
                        <select class="form-select" id="searchStatus" name="searchStatus">
                            <option value="">All Status</option>
                            <option value="pending" ${searchStatus eq 'pending' ? 'selected' : ''}>Pending</option>
                            <option value="confirmed" ${searchStatus eq 'confirmed' ? 'selected' : ''}>Confirmed</option>
                            <option value="cancelled" ${searchStatus eq 'cancelled' ? 'selected' : ''}>Cancelled</option>
                            <option value="completed" ${searchStatus eq 'completed' ? 'selected' : ''}>Completed</option>
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
                                CustomerDTO customer = customerMap != null ? customerMap.get(booking.getId()) : null;
                                VehicleDTO vehicle = vehicleMap != null ? vehicleMap.get(booking.getId()) : null;
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
                                <a href="${pageContext.request.contextPath}/booking-servlet?action=view&id=<%= booking.getId() %>"
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0-rc.0/js/select2.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        const successMessage = document.getElementById("successMessage");
        const errorMessage = document.getElementById("errorMessage");

        if (successMessage) {
            successMessage.classList.remove("d-none");
            setTimeout(() => {
                const bsAlert = new bootstrap.Alert(successMessage);
                bsAlert.close();
            }, 5000);
        }

        if (errorMessage) {
            errorMessage.classList.remove("d-none");
            setTimeout(() => {
                const bsAlert = new bootstrap.Alert(errorMessage);
                bsAlert.close();
            }, 5000);
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
