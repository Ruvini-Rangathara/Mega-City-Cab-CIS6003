<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.CustomerDTO" %>
<%@ page import="com.project.megacitycab.dto.VehicleDriverDTO" %>
<%@ page import="com.project.megacitycab.dto.BookingDTO" %>
<%@ page import="com.project.megacitycab.service.custom.impl.CustomerServiceImpl" %>
<%@ page import="com.project.megacitycab.service.custom.impl.VehicleDriverServiceImpl" %>
<%@ page import="com.project.megacitycab.service.custom.impl.BookingServiceImpl" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reports - Mega City Cab Service</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #fca311;
            --secondary-color: #6c757d;
            --background-color: #f8f9fa;
            --success-color: #28a745;
            --danger-color: #dc3545;
            --text-color: #212529;
        }

        body {
            background-color: var(--background-color);
            min-height: 100vh;
            padding-top: 60px;
            margin-left: 250px;
            font-family: 'Open Sans', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            color: var(--text-color);
        }

        /* Sidebar Styles */
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 250px;
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

        /* Report Section Styles */
        .top-section {
            margin-top: 0.7rem;
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
            color: var(--text-color);
            font-weight: 600;
            font-size: 1.25rem;
            margin: 0;
        }

        /* Tab Navigation Styling */
        .nav-tabs {
            display: flex;
            border-bottom: 1px solid #dee2e6;
            margin-bottom: 1.5rem;
        }

        .nav-tabs .nav-item {
            margin-right: 10px;
        }

        .nav-tabs .nav-link {
            color: var(--secondary-color);
            background-color: transparent;
            border: none;
            padding: 0.5rem 1rem;
            font-weight: 500;
            cursor: pointer;
            border-radius: 0.25rem 0.25rem 0 0;
            transition: all 0.3s ease;
        }

        .nav-tabs .nav-link:hover {
            background-color: rgba(252, 163, 17, 0.1);
            color: var(--primary-color);
        }

        .nav-tabs .nav-link.active {
            color: var(--primary-color);
            background-color: rgba(252, 163, 17, 0.2);
            border-bottom: 2px solid var(--primary-color);
        }

        .tab-content {
            padding: 1rem;
            background-color: white;
            /*border-radius: 1rem;*/
            /*box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);*/
        }

        .tab-pane {
            display: none;
        }

        .tab-pane.active {
            display: block;
        }

        /* Filter Form Styling */
        .filter-form {
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-bottom: 1.5rem;
        }

        .filter-form label {
            font-weight: 500;
            color: var(--text-color);
        }

        .filter-form select, .filter-form input {
            padding: 0.5rem;
            border: 1px solid #ced4da;
            border-radius: 0.25rem;
            font-family: inherit;
            color: var(--text-color);
            background-color: white;
            -webkit-appearance: auto;
            -moz-appearance: auto;
            appearance: auto;
        }

        .btn-primary {
            background-color: var(--primary-color);
            border: none;
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 0.25rem;
            cursor: pointer;
            font-family: inherit;
            font-weight: 500;
        }

        .btn-primary:hover {
            background-color: #e59100;
        }

        .btn-secondary {
            background-color: #6c757d;
            border: none;
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 0.25rem;
            cursor: pointer;
            font-family: inherit;
            font-weight: 500;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }

        /* Table Styling */
        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
            margin-bottom: 1.5rem;
        }

        .card-body {
            padding: 1rem;
        }

        .table-responsive {
            border-radius: 0.5rem;
        }

        .table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 0;
        }

        .table thead th {
            background-color: #f8f9fa;
            border-bottom: 2px solid #dee2e6;
            font-weight: 600;
            color: var(--text-color);
        }

        .table td, .table th {
            padding: 0.75rem;
            text-align: left;
            border-bottom: 1px solid #dee2e6;
            font-size: 0.9rem;
        }

        .table td {
            color: var(--text-color);
        }

        /* Print Button Styling */
        .print-btn {
            background-color: var(--success-color);
            border: none;
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 0.25rem;
            cursor: pointer;
            font-family: inherit;
            font-weight: 500;
        }

        .print-btn:hover {
            background-color: #218838;
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
            <a href="${pageContext.request.contextPath}/views/reports.jsp">
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
                %>
                <%= currentUser.substring(0, 1).toUpperCase() %>
            </div>
            <div class="user-details">
                <div class="user-name"><%= currentUser %></div>
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
                <button class="nav-link active" id="customer-tab" data-bs-toggle="tab" data-bs-target="#customerReports" type="button" role="tab" aria-controls="customerReports" aria-selected="true">Customer Reports</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="vehicleDriver-tab" data-bs-toggle="tab" data-bs-target="#vehicleDriverReports" type="button" role="tab" aria-controls="vehicleDriverReports" aria-selected="false">Vehicle Driver Reports</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="booking-tab" data-bs-toggle="tab" data-bs-target="#bookingReports" type="button" role="tab" aria-controls="bookingReports" aria-selected="false">Booking Reports</button>
            </li>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="reportTabsContent">
            <!-- Customer Reports -->
            <div class="tab-pane fade show active" id="customerReports" role="tabpanel" aria-labelledby="customer-tab">
                <div class="filter-form">
                    <label for="customerDuration">Duration:</label>
                    <select id="customerDuration" name="customerDuration" onchange="toggleCustomDates('customerDuration', 'customerStartDate', 'customerEndDate')">
                        <option value="all">All Time</option>
                        <option value="last7days">Last 7 Days</option>
                        <option value="last30days">Last 30 Days</option>
                    </select>
                    <input type="date" id="customerStartDate" name="customerStartDate" style="display: none;">
                    <input type="date" id="customerEndDate" name="customerEndDate" style="display: none;">
                    <button class="btn-primary" onclick="filterCustomerReports()">Generate Report</button>
                </div>
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover" id="customerReportsTable">
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
                                    CustomerServiceImpl customerService = new CustomerServiceImpl();
                                    Map<String, String> params = new HashMap<>();
                                    List<CustomerDTO> customers = customerService.getAll(params);
                                    if (customers != null && !customers.isEmpty()) {
                                        for (CustomerDTO customer : customers) {
                                %>
                                <tr>
                                    <td><%= customer.getRegistrationNo() %></td>
                                    <td><%= customer.getName() %></td>
                                    <td><%= customer.getEmail() %></td>
                                    <td><%= customer.getMobileNo() %></td>
                                    <td><%= customer.getCreatedAt() %></td>
                                    <td><%= customer.getUpdatedAt() %></td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="4" class="text-center">No customer reports found</td>
                                </tr>
                                <%
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <button class="print-btn" onclick="printReport('customerReportsTable')">Print Report</button>
            </div>

            <!-- Vehicle Driver Reports -->
            <div class="tab-pane fade" id="vehicleDriverReports" role="tabpanel" aria-labelledby="vehicleDriver-tab">
                <div class="filter-form">
                    <label for="vehicleDriverDuration">Duration:</label>
                    <select id="vehicleDriverDuration" name="vehicleDriverDuration" onchange="toggleCustomDates('vehicleDriverDuration', 'vehicleDriverStartDate', 'vehicleDriverEndDate')">
                        <option value="all">All Time</option>
                        <option value="last7days">Last 7 Days</option>
                        <option value="last30days">Last 30 Days</option>
                    </select>
                    <input type="date" id="vehicleDriverStartDate" name="vehicleDriverStartDate" style="display: none;">
                    <input type="date" id="vehicleDriverEndDate" name="vehicleDriverEndDate" style="display: none;">
                    <button class="btn-primary" onclick="filterVehicleDriverReports()">Generate Report</button>
                </div>
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover" id="vehicleDriverReportsTable">
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
                                    VehicleDriverServiceImpl vehicleDriverService = new VehicleDriverServiceImpl();
                                    List<VehicleDriverDTO> vehicleDrivers = vehicleDriverService.getAll(new HashMap<>());
                                    if (vehicleDrivers != null && !vehicleDrivers.isEmpty()) {
                                        for (VehicleDriverDTO vd : vehicleDrivers) {
                                %>
                                <tr>
                                    <td><%= vd.getVehicle().getLicensePlate() %></td>
                                    <td><%= vd.getDriver().getName() %></td>
                                    <td><%= vd.getDriver().getLicenseNo() %></td>
                                    <td>
                                        <%= vd.getVehicle().getBrand() %> <%= vd.getVehicle().getModel() %><br>
                                        <small class="text-muted">
                                            Color: <%= vd.getVehicle().getColor() %>,
                                            Capacity: <%= vd.getVehicle().getCapacity() %>
                                        </small>
                                    </td>
                                    <td>Rs. <%= vd.getVehicle().getPricePerKm() %></td>
                                    <td>
                                        <%= vd.getDriver().getMobileNo() %><br>
                                        <small class="text-muted"><%= vd.getDriver().getEmail() %></small>
                                    </td>
                                    <td>
                            <span class="status-<%= vd.getVehicle().getStatus().toString().toLowerCase() %>">
                                <%= vd.getVehicle().getStatus() %>
                            </span>
                                    </td>
                                    <td><%= vd.getVehicle().getCreatedAt() != null ? vd.getVehicle().getCreatedAt() : "N/A" %></td>
                                    <td><%= vd.getVehicle().getUpdatedAt() != null ? vd.getVehicle().getUpdatedAt() : "N/A" %></td>
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
                <button class="print-btn" onclick="printReport('vehicleDriverReportsTable')">Print Report</button>
            </div>

            <!-- Booking Reports -->
            <div class="tab-pane fade" id="bookingReports" role="tabpanel" aria-labelledby="booking-tab">
                <div class="filter-form">
                    <label for="bookingDuration">Duration:</label>
                    <select id="bookingDuration" name="bookingDuration" onchange="toggleCustomDates('bookingDuration', 'bookingStartDate', 'bookingEndDate')">
                        <option value="all">All Time</option>
                        <option value="last7days">Last 7 Days</option>
                        <option value="last30days">Last 30 Days</option>
                    </select>
                    <input type="date" id="bookingStartDate" name="bookingStartDate" style="display: none;">
                    <input type="date" id="bookingEndDate" name="bookingEndDate" style="display: none;">
                    <button class="btn-primary" onclick="filterBookingReports()">Generate Report</button>
                </div>
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover" id="bookingReportsTable">
                                <thead>
                                <tr>
                                    <th>Booking ID</th>
                                    <th>Vehicle ID</th>
                                    <th>Pickup Location</th>
                                    <th>Destination</th>
                                    <th>Booking Date</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    BookingServiceImpl bookingService = new BookingServiceImpl();
                                    List<BookingDTO> bookings = bookingService.getAll(new HashMap<>());
                                    if (bookings != null && !bookings.isEmpty()) {
                                        for (BookingDTO booking : bookings) {
                                %>
                                <tr>
                                    <td><%= booking.getId() != null ? booking.getId() : "N/A" %></td>
                                    <td><%= booking.getVehicleId() != null ? booking.getVehicleId() : "N/A" %></td>
                                    <td><%= booking.getPickupLocation() != null ? booking.getPickupLocation() : "N/A" %></td>
                                    <td><%= booking.getDestination() != null ? booking.getDestination() : "N/A" %></td>
                                    <td><%= booking.getBookingDate() != null ? booking.getBookingDate() : "N/A" %></td>
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
                <button class="print-btn" onclick="printReport('bookingReportsTable')">Print Report</button>
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
        // Add server-side filtering logic here (e.g., submit form to servlet)
        alert('Filtering customer reports... (Implement server-side logic)');
    }

    function filterVehicleDriverReports() {
        toggleCustomDates('vehicleDriverDuration', 'vehicleDriverStartDate', 'vehicleDriverEndDate');
        // Add server-side filtering logic here
        alert('Filtering vehicle driver reports... (Implement server-side logic)');
    }

    function filterBookingReports() {
        toggleCustomDates('bookingDuration', 'bookingStartDate', 'bookingEndDate');
        // Add server-side filtering logic here
        alert('Filtering booking reports... (Implement server-side logic)');
    }

    // Print function
    function printReport(tableId) {
        const printWindow = window.open('', '', 'height=600,width=800');
        printWindow.document.write('<html><head><title>Print Report</title>');
        printWindow.document.write('<style>table { border-collapse: collapse; width: 100%; font-family: "Open Sans", sans-serif; } th, td { border: 1px solid #ddd; padding: 8px; text-align: left; } th { background-color: #f2f2f2; }</style>');
        const table = document.querySelector(`#${tableId} table`);
        printWindow.document.write('</head><body>');
        printWindow.document.write(table.outerHTML);
        printWindow.document.write('</body></html>');
        printWindow.document.close();
        printWindow.print();
    }
</script>
</body>
</html>





