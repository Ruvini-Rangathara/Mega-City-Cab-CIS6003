<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.CustomerDTO" %>
<%@ page import="com.project.megacitycab.dto.VehicleDriverDTO" %>
<%@ page import="com.project.megacitycab.dto.BookingDTO" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.project.megacitycab.dto.VehicleDTO" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reports - Mega City Cab Service</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css"
          rel="stylesheet">
    <style>

        ::-webkit-scrollbar {
            display: none;
        }

        * {
            scrollbar-width: none;
        }

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
            cursor: pointer;
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
            transition: transform 0.2s ease;
        }

        .user-avatar:hover {
            transform: scale(1.1);
        }

        .modal-header {
            background-color: var(--primary-color);
            color: white;
            border-top-left-radius: 1rem;
            border-top-right-radius: 1rem;
        }

        .modal-content {
            border-radius: 1rem;
            border: none;
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

        .export-btn {
            background-color: #17a2b8;
            border: none;
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 0.25rem;
            cursor: pointer;
            font-family: inherit;
            font-weight: 500;
            margin-left: 1rem;
        }

        .print-btn {
            background-color: #6c757d;
            border: none;
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 0.25rem;
            cursor: pointer;
            font-family: inherit;
            font-weight: 500;
            margin-left: 1rem;
        }


        .export-btn:hover {
            background-color: #138496;
        }

        .button-group {
            margin-top: 1rem;
            display: flex;
            gap: 1rem;
        }

        .filter-form {
            margin-top: 1rem;
            margin-bottom: 1rem;
            display: flex;
            gap: 1rem;
            align-items: center;
        }

        .filter-form label {
            font-weight: 500;
            color: #495057;
        }

        .filter-form select, .filter-form input[type="date"] {
            padding: 0.5rem;
            border-radius: 0.25rem;
            border: 1px solid #ced4da;
        }

        .filter-form button {
            padding: 0.5rem 1rem;
            border-radius: 0.25rem;
            background-color: var(--primary-color);
            border: none;
            color: white;
            cursor: pointer;
        }

        .filter-form button:hover {
            background-color: #e59100;
        }
    </style>
</head>
<body>
<!-- Side Navigation Bar -->
<aside class="sidebar">
    <div class="sidebar-brand">MEGA CITY CAB</div>
    <ul class="sidebar-nav">
        <li>
            <a href="${pageContext.request.contextPath}/dashboard">
                <i class="bi bi-house-door"></i>
                Dashboard
            </a>
        </li>
        <li>
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
        <li class="active">
            <a href="${pageContext.request.contextPath}/report-servlet">
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
        <div class="user-info" data-bs-toggle="modal" data-bs-target="#logoutModal">
            <div class="user-avatar">
                <% String currentUser = (String) session.getAttribute("username");
                    if (currentUser == null) currentUser = "User";
//                    out.print(currentUser.substring(0, 1).toUpperCase());
                %>
            </div>
            <div class="user-details">
                <div class="user-name">Logout
                </div>
                <div class="user-role">Administrator</div>
            </div>
        </div>
    </div>
</aside>

<!-- Main Content -->
<div class="container">
    <div class="top-section">
        <div class="section-header">
            <h2 class="section-title">
                <i class="bi bi-bar-chart-fill me-2"></i>Reports
            </h2>
        </div>

        <!-- Tab Navigation -->
        <ul class="nav nav-tabs" id="reportTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link <%= "customer".equals(request.getParameter("type")) ? "active" : "" %>"
                        id="customer-tab" data-bs-toggle="tab" data-bs-target="#customerReports"
                        type="button" role="tab" aria-controls="customerReports" aria-selected="true">
                    Customer Reports
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link <%= "vehicleDriver".equals(request.getParameter("type")) ? "active" : "" %>"
                        id="vehicleDriver-tab" data-bs-toggle="tab" data-bs-target="#vehicleDriverReports"
                        type="button" role="tab" aria-controls="vehicleDriverReports" aria-selected="false">
                    Vehicle Driver Reports
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link <%= "booking".equals(request.getParameter("type")) ? "active" : "" %>"
                        id="booking-tab" data-bs-toggle="tab" data-bs-target="#bookingReports"
                        type="button" role="tab" aria-controls="bookingReports" aria-selected="false">
                    Booking Reports
                </button>
            </li>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="reportTabsContent">
            <!-- Customer Reports -->
            <div class="tab-pane fade <%= "customer".equals(request.getParameter("type")) ? "show active" : "" %>"
                 id="customerReports" role="tabpanel" aria-labelledby="customer-tab">
                <div class="filter-form">
                    <label for="customerDuration">Duration:</label>
                    <select id="customerDuration" name="customerDuration">
                        <option value="all" <%= "all".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>All Time</option>
                        <option value="7days" <%= "7days".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>Last 7 Days</option>
                        <option value="30days" <%= "30days".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>Last 30 Days</option>
                    </select>
                    <button class="btn-primary" onclick="filterCustomerReports()">Generate Report</button>
                </div>
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover" id="customerTable">
                                <thead>
                                <tr>
                                    <th>Reg. No</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Mobile</th>
                                    <th>CreatedAt</th>
                                    <th>UpdatedAt</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    List<CustomerDTO> customers = (List<CustomerDTO>) request.getAttribute("customers");
                                    if (customers != null && !customers.isEmpty()) {
                                        for (CustomerDTO customer : customers) {
                                %>
                                <tr>
                                    <td><%= customer.getRegistrationNo() %>
                                    </td>
                                    <td><%= customer.getName() %>
                                    </td>
                                    <td><%= customer.getEmail() %>
                                    </td>
                                    <td><%= customer.getMobileNo() %>
                                    </td>
                                    <td><%= customer.getCreatedAt() %>
                                    </td>
                                    <td><%= customer.getUpdatedAt() %>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="6" class="text-center">No customer reports found</td>
                                </tr>
                                <%
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="button-group">
                    <button class="print-btn" onclick="printCustomerList()">Print Report</button>
                    <button class="export-btn" onclick="exportToCSV('customerTable')">Export to CSV</button>
                </div>
            </div>

            <!-- Vehicle Driver Reports -->
            <div class="tab-pane fade <%= "vehicleDriver".equals(request.getParameter("type")) ? "show active" : "" %>"
                 id="vehicleDriverReports" role="tabpanel" aria-labelledby="vehicleDriver-tab">
                <div class="filter-form">
                    <label for="vehicleDriverDuration">Duration:</label>
                    <select id="vehicleDriverDuration" name="vehicleDriverDuration">
                        <option value="all" <%= "all".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>All Time</option>
                        <option value="7days" <%= "7days".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>Last 7 Days</option>
                        <option value="30days" <%= "30days".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>Last 30 Days</option>
                    </select>
                    <button class="btn-primary" onclick="filterVehicleDriverReports()">Generate Report</button>
                </div>
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover" id="vehicleDriverTable">
                                <thead>
                                <tr>
                                    <th>License Plate</th>
                                    <th>Driver Name</th>
                                    <th>Driver License</th>
                                    <th>Vehicle Info</th>
                                    <th>Price/km</th>
                                    <th>Driver Contact</th>
                                    <th>Status</th>
                                    <th>Created At</th>
                                    <th>Updated At</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    List<VehicleDriverDTO> vehicleDrivers = (List<VehicleDriverDTO>) request.getAttribute("vehicleDrivers");
                                    if (vehicleDrivers != null && !vehicleDrivers.isEmpty()) {
                                        for (VehicleDriverDTO vd : vehicleDrivers) {
                                %>
                                <tr>
                                    <td><%= vd.getVehicle().getLicensePlate() %>
                                    </td>
                                    <td><%= vd.getDriver().getName() %>
                                    </td>
                                    <td><%= vd.getDriver().getLicenseNo() %>
                                    </td>
                                    <td>
                                        <%= vd.getVehicle().getBrand() %> <%= vd.getVehicle().getModel() %><br>
                                        <small class="text-muted">
                                            Color: <%= vd.getVehicle().getColor() %>,
                                            Capacity: <%= vd.getVehicle().getCapacity() %>
                                        </small>
                                    </td>
                                    <td>Rs. <%= vd.getVehicle().getPricePerKm() %>
                                    </td>
                                    <td>
                                        <%= vd.getDriver().getMobileNo() %><br>
                                        <small class="text-muted"><%= vd.getDriver().getEmail() %>
                                        </small>
                                    </td>
                                    <td>
                                        <span class="status-<%= vd.getVehicle().getStatus().toString().toLowerCase() %>">
                                            <%= vd.getVehicle().getStatus() %>
                                        </span>
                                    </td>
                                    <td><%= vd.getVehicle().getCreatedAt() != null ? vd.getVehicle().getCreatedAt() : "N/A" %>
                                    </td>
                                    <td><%= vd.getVehicle().getUpdatedAt() != null ? vd.getVehicle().getUpdatedAt() : "N/A" %>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="9" class="text-center">No vehicle-driver reports found</td>
                                </tr>
                                <%
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="button-group">
                    <button class="print-btn" onclick="printVehicleDriverList()">Print Report</button>
                    <button class="export-btn" onclick="exportToCSV('vehicleDriverTable')">Export to CSV</button>
                </div>
            </div>

            <!-- Booking Reports -->
            <div class="tab-pane fade <%= "booking".equals(request.getParameter("type")) ? "show active" : "" %>"
                 id="bookingReports" role="tabpanel" aria-labelledby="booking-tab">
                <div class="filter-form">
                    <label for="bookingDuration">Duration:</label>
                    <select id="bookingDuration" name="bookingDuration">
                        <option value="all" <%= "all".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>All Time</option>
                        <option value="7days" <%= "7days".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>Last 7 Days</option>
                        <option value="30days" <%= "30days".equals(request.getParameter("timeFilter")) ? "selected" : "" %>>Last 30 Days</option>
                    </select>
                    <button class="btn-primary" onclick="filterBookingReports()">Generate Report</button>
                </div>
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover" id="bookingTable">
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
                                    <td><%= count++ %>
                                    </td>
                                    <td>
                                        <div class="customer-info">
                                            <% if (customer != null) { %>
                                            <div class="customer-name"><%= customer.getName() %>
                                            </div>
                                            <div class="customer-mobile"><i
                                                    class="bi bi-telephone"></i> <%= customer.getMobileNo() %>
                                            </div>
                                            <% } else { %>
                                            <span class="text-muted">ID: <%= booking.getCustomerId() %></span>
                                            <% } %>
                                        </div>
                                    </td>
                                    <td><%= booking.getBookingDate() %>
                                    </td>
                                    <td>
                                        <div class="time-info">
                                            <span class="time-label"><i
                                                    class="bi bi-clock"></i> Pickup:</span> <%= booking.getPickupTime() %>
                                            <br>
                                            <span class="time-label"><i
                                                    class="bi bi-clock-history"></i> Release:</span> <%= booking.getReleaseTime() %>
                                        </div>
                                    </td>
                                    <td>
                                        From: <%= booking.getPickupLocation() %><br>
                                        To: <%= booking.getDestination() %>
                                    </td>
                                    <td>
                                        <div class="vehicle-info">
                                            <% if (vehicle != null) { %>
                                            <div class="vehicle-brand"><%= vehicle.getBrand() %>
                                            </div>
                                            <div class="vehicle-model"><%= vehicle.getModel() %>
                                            </div>
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
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="5" class="text-center">No booking reports found</td>
                                </tr>
                                <%
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="button-group">
                    <button class="print-btn" onclick="printBookingList()">Print Report</button>
                    <button class="export-btn" onclick="exportToCSV('bookingTable')">Export to CSV</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Logout Modal -->
<div class="modal fade" id="logoutModal" tabindex="-1" aria-labelledby="logoutModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="logoutModalLabel">Logout</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to logout?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    <i class="bi bi-x-circle me-2"></i>Cancel
                </button>
                <form action="${pageContext.request.contextPath}/auth/logout" method="post" class="d-inline">
                    <button type="submit" class="btn btn-danger">
                        <i class="bi bi-box-arrow-right me-2"></i>Logout
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    // Function to toggle custom date inputs
    function toggleCustomDates(selectId, startDateId, endDateId) {
        const select = document.getElementById(selectId);
        const startDate = document.getElementById(startDateId);
        const endDate = document.getElementById(endDateId);
        if (select.value === 'custom') {
            startDate.style.display = 'inline';
            endDate.style.display = 'inline';
        } else {
            startDate.style.display = 'none';
            endDate.style.display = 'none';
        }
    }

    // Filter functions (placeholder - to be implemented with server-side logic)
    function filterCustomerReports() {
        toggleCustomDates('customerDuration', 'customerStartDate', 'customerEndDate');
        alert('Filtering customer reports... (Implement server-side logic)');
    }

    function filterVehicleDriverReports() {
        toggleCustomDates('vehicleDriverDuration', 'vehicleDriverStartDate', 'vehicleDriverEndDate');
        alert('Filtering vehicle driver reports... (Implement server-side logic)');
    }

    function filterBookingReports() {
        toggleCustomDates('bookingDuration', 'bookingStartDate', 'bookingEndDate');
        alert('Filtering booking reports... (Implement server-side logic)');
    }

    // Print functions for each table
    function printCustomerList() {
        const printContents = document.getElementById('customerTable').outerHTML;
        const originalContents = document.body.innerHTML;
        document.body.innerHTML = printContents;
        window.print();
        document.body.innerHTML = originalContents;
        window.location.reload();
    }

    function printVehicleDriverList() {
        const printContents = document.getElementById('vehicleDriverTable').outerHTML;
        const originalContents = document.body.innerHTML;
        document.body.innerHTML = printContents;
        window.print();
        document.body.innerHTML = originalContents;
        window.location.reload();
    }

    function printBookingList() {
        const printContents = document.getElementById('bookingTable').outerHTML;
        const originalContents = document.body.innerHTML;
        document.body.innerHTML = printContents;
        window.print();
        document.body.innerHTML = originalContents;
        window.location.reload();
    }

    // Function to export table to CSV
    function exportToCSV(tableId) {
        const table = document.getElementById(tableId);
        const rows = table.querySelectorAll('tr');
        let csvContent = '';

        rows.forEach(row => {
            const rowData = [];
            row.querySelectorAll('th, td').forEach(cell => {
                if (!cell.querySelector('button')) { // Exclude action buttons
                    let text = cell.innerText.replace(/,/g, ''); // Remove commas to avoid CSV conflicts
                    rowData.push('"' + text + '"'); // Wrap in quotes to handle spaces
                }
            });
            csvContent += rowData.join(',') + '\n';
        });

        const blob = new Blob([csvContent], {type: 'text/csv;charset=utf-8;'});
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = tableId + '_report_' + new Date().toISOString().slice(0, 10) + '.csv';
        link.click();
    }
</script>
<script>
    // Function to handle tab clicks and redirect to the servlet with appropriate parameters
    document.getElementById('customer-tab').addEventListener('click', function () {
        const timeFilter = document.getElementById('customerDuration').value; // Get selected time filter
        window.location.href = "${pageContext.request.contextPath}/report-servlet?type=customer&timeFilter=" + timeFilter;
    });

    document.getElementById('vehicleDriver-tab').addEventListener('click', function () {
        const timeFilter = document.getElementById('vehicleDriverDuration').value; // Get selected time filter
        window.location.href = "${pageContext.request.contextPath}/report-servlet?type=vehicleDriver&timeFilter=" + timeFilter;
    });

    document.getElementById('booking-tab').addEventListener('click', function () {
        const timeFilter = document.getElementById('bookingDuration').value; // Get selected time filter
        window.location.href = "${pageContext.request.contextPath}/report-servlet?type=booking&timeFilter=" + timeFilter;
    });
</script>
</body>
</html>
