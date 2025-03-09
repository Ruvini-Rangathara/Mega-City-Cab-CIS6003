<%--
  Created by IntelliJ IDEA.
  User: ruvini
  Date: 2025-02-23
  Time: 12.38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.VehicleDriverDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vehicle-Driver Management - Mega City Cab Service</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0-rc.0/css/select2.min.css" rel="stylesheet">
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
            padding-top: 60px;
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

        /* Vehicle-Driver Management Styles */
        .top-section {
            margin-top: 1rem;
            background-color: white;
            border-radius: 1rem;
            padding: 1.5rem;
            margin-bottom: 1rem;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
        }

        #successMessage, #errorMessage {
            margin-top: 2rem;
        }

        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .section-title {
            color: #212529;
            font-weight: 600;
            font-size: 1.25rem;
            margin: 0;
        }

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: #e59100;
            border-color: #e59100;
        }

        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
        }

        .modal-content {
            border-radius: 1rem;
            border: none;
        }

        .modal-header {
            background-color: var(--primary-color);
            color: white;
            border-top-left-radius: 1rem;
            border-top-right-radius: 1rem;
        }

        .status-available {
            color: #198754;
            background-color: #d1e7dd;
            border-radius: 0.25rem;
            padding: 0.25rem 0.5rem;
        }

        .status-unavailable {
            color: #dc3545;
            background-color: #f8d7da;
            border-radius: 0.25rem;
            padding: 0.25rem 0.5rem;
        }

        .btn-edit {
            background-color: #ffeb99;
            border-color: #ffeb99;
            color: #000;
            margin-right: 5px;
        }

        .btn-edit:hover {
            background-color: #ffe066;
            border-color: #ffe066;
            color: #000;
        }

        .btn-delete {
            background-color: #ff9999;
            border-color: #ff9999;
            color: #000;
            margin-left: 5px;
        }

        .btn-delete:hover {
            background-color: #ff6666;
            border-color: #ff6666;
            color: #000;
        }

        .btn-view {
            background-color: #cce5ff;
            border-color: #cce5ff;
            color: #000;
            margin-right: 5px;
        }

        .btn-view:hover {
            background-color: #99ccff;
            border-color: #99ccff;
            color: #000;
        }

        .card-header {
            background-color: white;
            border-bottom: none;
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

        .alert {
            border-radius: 0.5rem;
        }
    </style>
</head>
<body>
<!-- Side Navigation Bar -->
<aside class="sidebar">
    <div class="sidebar-brand">MEGA CITY CAB</div>
    <ul class="sidebar-nav">
        <li>
            <a href="${pageContext.request.contextPath}/dashboard.jsp">
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
        <li class="active">
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
    <div id="successMessage" class="alert alert-success alert-dismissible fade show d-none" role="alert">
        ${param.success}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <div id="errorMessage" class="alert alert-danger alert-dismissible fade show d-none" role="alert">
        ${param.error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>

    <!-- Search Section -->
    <div class="top-section">
        <div class="card-header d-flex justify-content-between align-items-center mb-4">
            <div class="section-header">
                <h2 class="section-title">
                    <i class="bi bi-search me-2"></i>Search Vehicle-Driver
                </h2>
            </div>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#vehicleDriverModal">
                <i class="bi bi-plus-circle me-2"></i>New Vehicle & Driver
            </button>
        </div>

        <form id="searchForm" action="${pageContext.request.contextPath}/vehicle-driver-servlet" method="get">
            <div class="row g-3">
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchLicensePlate" name="searchLicensePlate"
                               value="${param.searchLicensePlate}" placeholder="License Plate">
                        <label for="searchLicensePlate">License Plate</label>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchDriverName" name="searchDriverName"
                               value="${param.searchDriverName}" placeholder="Driver Name">
                        <label for="searchDriverName">Driver Name</label>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchLicenseNo" name="searchLicenseNo"
                               value="${param.searchLicenseNo}" placeholder="License No">
                        <label for="searchLicenseNo">Driver License No</label>
                    </div>
                </div>
            </div>
            <div class="row g-3 mt-2">
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchBrand" name="searchBrand"
                               value="${param.searchBrand}" placeholder="Brand">
                        <label for="searchBrand">Vehicle Brand</label>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchModel" name="searchModel"
                               value="${param.searchModel}" placeholder="Model">
                        <label for="searchModel">Vehicle Model</label>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchDriverEmail" name="searchDriverEmail"
                               value="${param.searchDriverEmail}" placeholder="Driver Email">
                        <label for="searchDriverEmail">Driver Email</label>
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

    <!-- Vehicle-Driver List -->
    <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
            <h5 class="mb-1 mt-1 pt-2">Vehicle-Driver Details</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>License Plate</th>
                        <th>Driver Name</th>
                        <th>Driver License</th>
                        <th>Vehicle Info</th>
                        <th>Price/km</th>
                        <th>Driver Contact</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<VehicleDriverDTO> vehicleDrivers = (List<VehicleDriverDTO>) request.getAttribute("vehicleDrivers");
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
                        <td>
                            <div class="btn-group">
                                <button class="btn btn-view btn-sm" onclick="viewVehicleDriver(
                                        '<%= vd.getVehicle().getId() %>',
                                        '<%= vd.getDriver().getId() %>',
                                        '<%= vd.getVehicle().getLicensePlate() %>',
                                        '<%= vd.getDriver().getLicenseNo() %>',
                                        '<%= vd.getDriver().getName() %>',
                                        '<%= vd.getDriver().getMobileNo() %>',
                                        '<%= vd.getDriver().getEmail() %>',
                                        '<%= vd.getDriver().getExperience() %>',
                                        '<%= vd.getVehicle().getBrand() %>',
                                        '<%= vd.getVehicle().getModel() %>',
                                        '<%= vd.getVehicle().getColor() %>',
                                        '<%= vd.getVehicle().getCapacity() %>',
                                        '<%= vd.getVehicle().getPricePerKm() %>',
                                        '<%= vd.getVehicle().getStatus() %>'
                                        )">
                                    <i class="bi bi-eye-fill"></i>
                                </button>
                                <button class="btn btn-edit btn-sm" onclick="editVehicleDriver(
                                        '<%= vd.getVehicle().getId() %>',
                                        '<%= vd.getDriver().getId() %>',
                                        '<%= vd.getVehicle().getLicensePlate() %>',
                                        '<%= vd.getDriver().getLicenseNo() %>',
                                        '<%= vd.getDriver().getName() %>',
                                        '<%= vd.getDriver().getMobileNo() %>',
                                        '<%= vd.getDriver().getEmail() %>',
                                        '<%= vd.getDriver().getExperience() %>',
                                        '<%= vd.getVehicle().getBrand() %>',
                                        '<%= vd.getVehicle().getModel() %>',
                                        '<%= vd.getVehicle().getColor() %>',
                                        '<%= vd.getVehicle().getCapacity() %>',
                                        '<%= vd.getVehicle().getPricePerKm() %>',
                                        '<%= vd.getVehicle().getStatus() %>'
                                        )">
                                    <i class="bi bi-pencil-fill"></i>
                                </button>
                                <button class="btn btn-delete btn-sm" onclick="deleteVehicleDriver(
                                        '<%= vd.getVehicle().getId() %>',
                                        '<%= vd.getDriver().getId() %>'
                                        )">
                                    <i class="bi bi-trash-fill"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="8" class="text-center">No vehicle & driver found</td>
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

<!-- Vehicle-Driver Modal -->
<div class="modal fade" id="vehicleDriverModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="vehicleDriverModalLabel">Manage Vehicle & Driver</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form id="vehicleDriverForm" action="${pageContext.request.contextPath}/vehicle-driver-servlet" method="post" onsubmit="return validateForm()">
                    <input type="hidden" id="action" name="action" value="add">
                    <input type="hidden" id="vehicleId" name="vehicleId">
                    <input type="hidden" id="driverId" name="driverId">

                    <!-- Driver Information -->
                    <h6 class="mb-3">Driver Information</h6>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="licenseNo" name="licenseNo" required>
                                <label>License No</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="driverName" name="driverName" required>
                                <label>Name</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="tel" class="form-control" id="driverMobile" name="driverMobile"
                                       required pattern="^(?:\+94|0)7\d{8}$">
                                <label>Mobile Number</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="email" class="form-control" id="driverEmail" name="driverEmail" required>
                                <label>Email</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="number" class="form-control" id="experience" name="experience"
                                       required min="0" max="50">
                                <label>Experience (Years)</label>
                            </div>
                        </div>
                    </div>

                    <!-- Vehicle Information -->
                    <h6 class="mb-3 mt-4">Vehicle Information</h6>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="licensePlate" name="licensePlate" required>
                                <label>License Plate</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="brand" name="brand" required>
                                <label>Brand</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="model" name="model" required>
                                <label>Model</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="color" name="color" required>
                                <label>Color</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="number" class="form-control" id="capacity" name="capacity"
                                       required min="1" step="0.1">
                                <label>Capacity</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <select class="form-select" id="vehicleStatus" name="vehicleStatus" required>
                                    <option value="available">Available</option>
                                    <option value="unavailable">Unavailable</option>
                                </select>
                                <label>Vehicle Status</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="number" class="form-control" id="pricePerKm" name="pricePerKm"
                                       required min="0" step="0.01">
                                <label>Price per km (Rs.)</label>
                            </div>
                        </div>
                    </div>

                    <!-- Form Actions -->
                    <div class="d-flex justify-content-end gap-2 mt-4">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="cancelButton">
                            <i class="bi bi-x-circle me-2"></i>Cancel
                        </button>
                        <button type="submit" class="btn btn-primary" id="submitButton">
                            <i class="bi bi-plus-circle me-2"></i>Add
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0-rc.0/js/select2.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
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

        // Reset form when modal is closed
        const vehicleDriverModal = document.getElementById('vehicleDriverModal');
        vehicleDriverModal.addEventListener('hidden.bs.modal', function () {
            document.getElementById("vehicleDriverForm").reset();
            document.getElementById("action").value = "add";
            document.getElementById("submitButton").innerHTML = '<i class="bi bi-plus-circle me-2"></i>Add';
            document.getElementById("submitButton").style.display = "block";
            document.getElementById("licenseNo").readOnly = false;
            document.getElementById("licensePlate").readOnly = false;
            enableFormInputs(false);
            document.getElementById("vehicleDriverModalLabel").innerText = "Manage Vehicle & Driver";
        });
    });

    function clearSearch() {
        document.getElementById('searchLicensePlate').value = '';
        document.getElementById('searchDriverName').value = '';
        document.getElementById('searchLicenseNo').value = '';
        document.getElementById('searchBrand').value = '';
        document.getElementById('searchModel').value = '';
        document.getElementById('searchDriverEmail').value = '';
        document.getElementById('searchForm').submit();
    }

    function editVehicleDriver(vehicleId, driverId, licensePlate, licenseNo, driverName, driverMobile, driverEmail,
                               experience, brand, model, color, capacity, pricePerKm, vehicleStatus) {
        document.getElementById("vehicleId").value = vehicleId;
        document.getElementById("driverId").value = driverId;
        document.getElementById("licensePlate").value = licensePlate;
        document.getElementById("licenseNo").value = licenseNo;
        document.getElementById("driverName").value = driverName;
        document.getElementById("driverMobile").value = driverMobile;
        document.getElementById("driverEmail").value = driverEmail;
        document.getElementById("experience").value = experience;
        document.getElementById("brand").value = brand;
        document.getElementById("model").value = model;
        document.getElementById("color").value = color;
        document.getElementById("capacity").value = capacity;
        document.getElementById("pricePerKm").value = pricePerKm;
        document.getElementById("vehicleStatus").value = vehicleStatus.toLowerCase();

        document.getElementById("action").value = "update";
        document.getElementById("submitButton").innerHTML = '<i class="bi bi-check-lg me-2"></i>Update';
        document.getElementById("submitButton").style.display = "block";
        document.getElementById("licenseNo").readOnly = true;
        document.getElementById("licensePlate").readOnly = true;
        document.getElementById("vehicleDriverModalLabel").innerText = "Edit Vehicle & Driver";
        enableFormInputs(false);

        new bootstrap.Modal(document.getElementById('vehicleDriverModal')).show();
    }

    function viewVehicleDriver(vehicleId, driverId, licensePlate, licenseNo, driverName, driverMobile, driverEmail,
                               experience, brand, model, color, capacity, pricePerKm, vehicleStatus) {
        document.getElementById("vehicleId").value = vehicleId;
        document.getElementById("driverId").value = driverId;
        document.getElementById("licensePlate").value = licensePlate;
        document.getElementById("licenseNo").value = licenseNo;
        document.getElementById("driverName").value = driverName;
        document.getElementById("driverMobile").value = driverMobile;
        document.getElementById("driverEmail").value = driverEmail;
        document.getElementById("experience").value = experience;
        document.getElementById("brand").value = brand;
        document.getElementById("model").value = model;
        document.getElementById("color").value = color;
        document.getElementById("capacity").value = capacity;
        document.getElementById("pricePerKm").value = pricePerKm;
        document.getElementById("vehicleStatus").value = vehicleStatus.toLowerCase();

        document.getElementById("action").value = "view"; // No form submission for view
        document.getElementById("submitButton").style.display = "none";
        document.getElementById("licenseNo").readOnly = true;
        document.getElementById("licensePlate").readOnly = true;
        document.getElementById("vehicleDriverModalLabel").innerText = "View Vehicle & Driver Details";
        enableFormInputs(true);

        new bootstrap.Modal(document.getElementById('vehicleDriverModal')).show();
    }

    function deleteVehicleDriver(vehicleId, driverId) {
        if (confirm("Are you sure you want to delete this vehicle & driver?")) {
            const form = document.createElement("form");
            form.method = "post";
            form.action = "${pageContext.request.contextPath}/vehicle-driver-servlet";

            const actionInput = document.createElement("input");
            actionInput.type = "hidden";
            actionInput.name = "action";
            actionInput.value = "delete";
            form.appendChild(actionInput);

            const vehicleIdInput = document.createElement("input");
            vehicleIdInput.type = "hidden";
            vehicleIdInput.name = "vehicleId";
            vehicleIdInput.value = vehicleId;
            form.appendChild(vehicleIdInput);

            const driverIdInput = document.createElement("input");
            driverIdInput.type = "hidden";
            driverIdInput.name = "driverId";
            driverIdInput.value = driverId;
            form.appendChild(driverIdInput);

            document.body.appendChild(form);
            form.submit();
        }
    }

    function validateForm() {
        const mobile = document.getElementById("driverMobile").value;
        const email = document.getElementById("driverEmail").value;
        const experience = document.getElementById("experience").value;

        const mobilePattern = /^(?:\+94|0)7\d{8}$/;
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!mobilePattern.test(mobile)) {
            alert("Please enter a valid mobile number (e.g., 0771234567 or +94771234567)");
            return false;
        }

        if (!emailPattern.test(email)) {
            alert("Please enter a valid email address");
            return false;
        }

        if (experience < 0 || experience > 50) {
            alert("Experience must be between 0 and 50 years");
            return false;
        }

        return true;
    }

    function enableFormInputs(disable) {
        const inputs = document.querySelectorAll('#vehicleDriverForm input, #vehicleDriverForm select');
        inputs.forEach(input => {
            input.disabled = disable;
        });
    }
</script>
</body>
</html>
