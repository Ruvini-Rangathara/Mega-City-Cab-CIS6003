<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.*" %>
<%@ page import="com.project.megacitycab.constant.BookingStatus" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Booking - Mega City Cab Service</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css"
          rel="stylesheet">
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
            padding-top: 60px;
        }

        /* Navbar styles */
        .navbar {
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .navbar-brand {
            color: var(--primary-color);
            font-weight: bold;
            font-size: 1.5rem;
        }

        .vehicle-and-driver-section{
            border: none;
            background-color: white;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
            /*margin-top: 1rem;*/
            margin-bottom: 2rem;
            padding: 5px 1rem
        }

        /* Card styles */
        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
            margin-top: 2rem;
            padding-top: 1rem;
        }

        .card-header {
            background-color: white;
            border-bottom: 2px solid rgba(252, 163, 17, 0.3);
        }

        .card-header h5 {
            color: var(--primary-color);
        }

        .customer-info{
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 10px;
            margin-top: 10px;
            display: none;
        }

        /* Vehicle section - positioned below booking details */
        /*.vehicle-section {*/
        /*    background-color: white;*/
        /*    padding: 1.5rem;*/
        /*    border: none;*/
        /*    box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);*/
        /*    border-radius: 1rem;*/
        /*    margin-top: 1rem;*/
        /*}*/

        /* Vehicle table */
        .vehicle-table {
            display: none;
            margin-top: 1rem;
        }

        .vehicle-table table {
            width: 100%;
            border-collapse: collapse;
        }

        .vehicle-table tr.selected {
            background-color: rgba(252, 163, 17, 0.2);
        }

        .vehicle-table th {
            background-color: #f1f1f1;
            padding: 10px;
            text-align: left;
        }

        .vehicle-table td {
            padding: 10px;
            border-bottom: 1px solid #dee2e6;
        }

        /* Filter section */
        .filter-controls {
            background: #f1f1f1;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 15px;
        }

        /* Button styles */
        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: #e59100;
            border-color: #e59100;
        }

        /* Select2 styling */
        .select2-container {
            width: 100% !important;
        }

        .select2-container .select2-selection--single {
            height: 38px;
            border-radius: 0.375rem;
            border-color: #ced4da;
        }

        .select2-container--default .select2-selection--single .select2-selection__rendered {
            line-height: 38px;
            padding-left: 12px;
        }

        .select2-container--default .select2-selection--single .select2-selection__arrow {
            height: 36px;
        }

        /* Summary card */
        .summary-card {
            position: sticky;
            margin-top: 2rem;
        }

        .summary-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0.2rem;
            border-radius: 0.5rem;
            transition: all 0.3s ease;
        }

        .summary-item:hover {
            background-color: rgba(252, 163, 17, 0.1);
        }

        .summary-total {
            background-color: rgba(252, 163, 17, 0.2);
            padding: 1rem;
            border-radius: 0.5rem;
            margin-top: 1rem;
            font-weight: bold;
        }

        .summary-title {
            font-size: 1.1rem;
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 1rem;
            border-bottom: 2px solid var(--primary-color);
            padding-bottom: 0.5rem;
        }

        /* Status badges */
        .status-badge {
            display: inline-block;
            padding: 0.25em 0.6em;
            font-size: 75%;
            font-weight: 700;
            line-height: 1;
            text-align: center;
            white-space: nowrap;
            vertical-align: baseline;
            border-radius: 0.25rem;
        }

        .status-pending {
            background-color: #ffc107;
            color: #212529;
        }

        .status-confirmed {
            background-color: #198754;
            color: white;
        }

        .status-cancelled {
            background-color: #dc3545;
            color: white;
        }

        .status-completed {
            background-color: #0d6efd;
            color: white;
        }

        .status-failed {
            background-color: #6c757d;
            color: white;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">MEGA CITY CAB</a>
        <div class="ms-auto d-flex align-items-center">
            <% String currentUser = (String) session.getAttribute("username");
                if (currentUser == null) currentUser = "User"; %>
            <span class="me-3"><i class="bi bi-person-circle me-2"></i><%=currentUser%></span>
        </div>
    </div>
</nav>

<%
    BookingDTO booking = (BookingDTO) request.getAttribute("booking");
    CustomerDTO customer = (CustomerDTO) request.getAttribute("customer");
    VehicleDTO vehicle = (VehicleDTO) request.getAttribute("vehicle");
    DriverDTO driver = (DriverDTO) request.getAttribute("driver");
    boolean isEditMode = (booking != null);
    String bookingStatus = isEditMode ? booking.getStatus().toString() : "";
    boolean isReadOnlyStatus = isEditMode && !bookingStatus.equals("pending");
%>

<div class="container">
    <div class="row">
        <!-- Booking Form -->
        <div class="col-lg-8">
            <div class="card mb-2">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="bi bi-calendar2-event me-2"></i>
                        <%=isEditMode ? "View/Edit Booking" : "Create Booking"%>
                    </h5>
                </div>
                <div class="card-body">
                    <form id="bookingForm" action="${pageContext.request.contextPath}/booking-servlet" method="post"
                          onsubmit="return validateForm()">
                        <input type="hidden" id="action" name="action" value="<%=isEditMode ? "update" : "add"%>">
                        <input type="hidden" id="bookingId" name="bookingId"
                               value="<%=isEditMode ? booking.getId() : ""%>">
                        <input type="hidden" id="selectedVehicleId" name="vehicleId"
                               value="<%=isEditMode ? booking.getVehicleId() : ""%>">
                        <input type="hidden" id="userId" name="userId"
                               value="<%=session.getAttribute("userId") != null ? session.getAttribute("userId") : ""%>">
                        <input type="hidden" id="fare" name="fare" value="<%=isEditMode ? booking.getFare() : ""%>">
                        <input type="hidden" id="netTotal" name="netTotal"
                               value="<%=isEditMode ? booking.getNetTotal() : ""%>">

                        <!-- Customer Details -->
                        <h6 class="mb-3">Customer Information</h6>
                        <div class="row g-3">
                            <% if (!isReadOnlyStatus) { %>
                            <div class="col-md-12">
                                <label for="customerSelect" class="form-label">Select Customer</label>
                                <select class="form-select" id="customerSelect" name="customerId" required
                                        <%=isEditMode ? "disabled" : ""%>>
                                    <option value="">Select customer by NIC or name</option>
                                    <%
                                        List<CustomerDTO> customers = (List<CustomerDTO>) request.getAttribute("customers");
                                        if (customers != null) {
                                            for (CustomerDTO c : customers) {
                                                String selected = "";
                                                if (isEditMode && customer != null && c.getId().equals(customer.getId())) {
                                                    selected = "selected";
                                                }
                                    %>
                                    <option value="<%= c.getId()%>"
                                            data-nic="<%=c.getNic()%>"
                                            data-name="<%=c.getName()%>"
                                            data-phone="<%=c.getMobileNo()%>"
                                            data-email="<%=c.getEmail()%>"
                                            <%=selected%>>
                                        <%=c.getNic()%> - <%=c.getName()%>
                                    </option>
                                    <% }
                                    } %>
                                </select>
                                <% if (isEditMode) { %>
                                <input type="hidden" name="customerId" value="<%=customer.getId()%>">
                                <% } %>
                            </div>
                            <% } %>

                            <div class="col-md-12">
                                <div id="customerInfo" class="customer-info"
                                     style="<%=isEditMode ? "display: block;" : ""%>">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <p class="mb-1"><strong>Name:</strong> <span
                                                    id="customerName"><%=isEditMode ? customer.getName() : ""%></span>
                                            </p>
                                            <p class="mb-1"><strong>NIC:</strong> <span
                                                    id="customerNIC"><%=isEditMode ? customer.getNic() : ""%></span></p>
                                        </div>
                                        <div class="col-md-6">
                                            <p class="mb-1"><strong>Phone:</strong> <span
                                                    id="customerPhone"><%=isEditMode ? customer.getMobileNo() : ""%></span>
                                            </p>
                                            <p class="mb-1"><strong>Email:</strong> <span
                                                    id="customerEmail"><%=isEditMode ? customer.getEmail() : ""%></span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Booking Details -->
                        <h6 class="mb-3 mt-4">Booking Information</h6>
                        <div class="row g-3">
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="date" class="form-control" id="bookingDate" name="bookingDate"
                                           value="<%=isEditMode ? booking.getBookingDate() : ""%>"
                                           required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Booking Date</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="time" class="form-control" id="pickupTime" name="pickupTime"
                                           value="<%=isEditMode ? booking.getPickupTime() : ""%>"
                                           required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Pickup Time</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="time" class="form-control" id="releaseTime" name="releaseTime"
                                           value="<%=isEditMode ? booking.getReleaseTime() : ""%>"
                                           required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Release Time</label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="pickupLocation" name="pickupLocation"
                                           value="<%=isEditMode ? booking.getPickupLocation() : ""%>"
                                           required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Pickup Location</label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="destination" name="destination"
                                           value="<%=isEditMode ? booking.getDestination() : ""%>"
                                           required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Destination</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="distance" name="distance"
                                           value="<%=isEditMode ? booking.getDistance() : ""%>" required min="1"
                                           step="0.1"
                                           onchange="calculateFare()" <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Distance (km)</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="discount" name="discount"
                                           value="<%=isEditMode ? booking.getDiscount() : "0"%>" min="0" step="0.01"
                                           onchange="calculateNetTotal()" <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Discount (Rs.)</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="tax" name="tax"
                                           value="<%=isEditMode ? booking.getTax() : "0"%>" min="0" step="0.01"
                                           onchange="calculateNetTotal()" <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Tax (Rs.)</label>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-floating">
                                    <select class="form-select" id="bookingStatus"
                                            name="status" <%=isReadOnlyStatus ? "disabled" : ""%>>
                                        <% for (BookingStatus status : BookingStatus.values()) {
                                            String selected = "";
                                            if (isEditMode && booking.getStatus().toString().equals(status.toString())) {
                                                selected = "selected";
                                            }
                                        %>
                                        <option value="<%=status%>" <%=selected%>><%=status.toString()%>
                                        </option>
                                        <% } %>
                                    </select>
                                    <label>Booking Status</label>
                                </div>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="d-flex justify-content-end gap-2 mt-2">
                            <% if (isReadOnlyStatus) { %>
                            <a href="${pageContext.request.contextPath}/views/booking-list.jsp"
                               class="btn btn-secondary">
                                <i class="bi bi-arrow-left me-2"></i>Back
                            </a>
                            <% } else { %>
                            <a href="${pageContext.request.contextPath}/views/booking-list.jsp"
                               class="btn btn-secondary">
                                <i class="bi bi-x-circle me-2"></i>Cancel
                            </a>
                            <button type="submit" class="btn btn-primary" id="submitButton">
                                <i class="bi <%=isEditMode ? "bi-check-circle" : "bi-plus-circle"%> me-2"></i>
                                <%=isEditMode ? "Update Booking" : "Create Booking"%>
                            </button>
                            <% } %>
                        </div>
                    </form>
                </div>
            </div>

            <div class="vehicle-and-driver-section">
                <!-- Vehicle Selection Section -->
                <% List<VehicleDriverDTO> availableVehicles = null;
                    if (!isReadOnlyStatus) { %>
                <div class="vehicle-section">
                    <div class="card-header">
                        <h5 class="mb-2"><i class="bi bi-car-front-fill me-2"></i>Vehicle Selection</h5>
                    </div>

                    <!-- Vehicle Filters -->
                    <form id="vehicleSearchForm" class="mt-4">
                        <div class="row g-3">
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="capacityFilter" min="1"
                                           placeholder="Min. capacity">
                                    <label for="capacityFilter">Capacity</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <select class="form-select" id="brandFilter">
                                        <option value="">All</option>
                                        <%
                                            availableVehicles = (List<VehicleDriverDTO>) request.getAttribute("availableVehicles");
                                            if (availableVehicles != null && !availableVehicles.isEmpty()) {
                                                java.util.Set<String> uniqueBrands = new java.util.HashSet<>();
                                                for (VehicleDriverDTO vd : availableVehicles) {
                                                    uniqueBrands.add(vd.getVehicle().getBrand());
                                                }
                                                for (String brand : uniqueBrands) {
                                        %>
                                        <option value="<%=brand%>"><%=brand%>
                                        </option>
                                        <% }
                                        } %>
                                    </select>
                                    <label for="brandFilter">Brand</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <select class="form-select" id="modelFilter">
                                        <option value="">All</option>
                                        <%
                                            if (availableVehicles != null && !availableVehicles.isEmpty()) {
                                                java.util.Set<String> uniqueModels = new java.util.HashSet<>();
                                                for (VehicleDriverDTO vd : availableVehicles) {
                                                    uniqueModels.add(vd.getVehicle().getModel());
                                                }
                                                for (String model : uniqueModels) {
                                        %>
                                        <option value="<%=model%>"><%=model%>
                                        </option>
                                        <% }
                                        } %>
                                    </select>
                                    <label for="modelFilter">Model</label>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end gap-2 mt-3">
                            <button type="button" class="btn btn-secondary" onclick="clearVehicleSearch()">
                                <i class="bi bi-x-circle me-2"></i>Clear Filter
                            </button>
                            <button type="button" class="btn btn-primary" onclick="searchVehicles()">
                                <i class="bi bi-search me-2"></i>Search
                            </button>
                        </div>
                    </form>

                    <!-- Vehicle Table -->
                    <div id="vehicleTableContainer" class="vehicle-table mt-3" style="display: none;">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Vehicle</th>
                                <th>License Plate</th>
                                <th>Capacity</th>
                                <th>Price/km</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody id="vehicleTableBody">
                            <!-- Vehicle rows will be dynamically added here -->
                            </tbody>
                        </table>
                    </div>
                    <% } %>

                    <!-- Vehicle Information -->
                    <div id="vehicleInfo" class="vehicle-info mt-3"
                         style="<%=isEditMode && vehicle != null ? "display: block;" : "display: none;"%>">
                        <h6 class="mb-2">Vehicle Information</h6>
                        <div class="row">
                            <div class="col-md-4">
                                <p class="mb-1"><strong>Brand:</strong> <span
                                        id="vehicleBrand"><%=isEditMode && vehicle != null ? vehicle.getBrand() : ""%></span>
                                </p>
                            </div>
                            <div class="col-md-4">
                                <p class="mb-1"><strong>Model:</strong> <span
                                        id="vehicleModel"><%=isEditMode && vehicle != null ? vehicle.getModel() : ""%></span>
                                </p>
                            </div>
                            <div class="col-md-4">
                                <p class="mb-1"><strong>Price Per km:</strong> <span
                                        id="vehiclePricePerKm"><%=isEditMode && vehicle != null ? vehicle.getPricePerKm() : ""%></span>
                                </p>
                            </div>
                        </div>
                    </div>

                    <!-- Driver Information -->
                    <div id="driverInfo" class="driver-info mt-3"
                         style="<%=isEditMode && driver != null ? "display: block;" : "display: none;"%>">
                        <h6 class="mb-2">Driver Information</h6>
                        <div class="row">
                            <div class="col-md-4">
                                <p class="mb-1"><strong>Name:</strong> <span
                                        id="driverName"><%=isEditMode && driver != null ? driver.getName() : ""%></span>
                                </p>
                            </div>
                            <div class="col-md-4">
                                <p class="mb-1"><strong>Phone:</strong> <span
                                        id="driverPhone"><%=isEditMode && driver != null ? driver.getMobileNo() : ""%></span>
                                </p>
                            </div>
                            <div class="col-md-4">
                                <p class="mb-1"><strong>License:</strong> <span
                                        id="driverLicense"><%=isEditMode && driver != null ? driver.getLicenseNo() : ""%></span>
                                </p>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

            <!-- Summary Section -->
            <div class="col-lg-4">
                <div class="card summary-card">
                    <div class="card-header">
                        <h5 class="mb-0"><i class="bi bi-clipboard-check me-2"></i>Booking Summary</h5>
                    </div>
                    <div class="card-body">
                        <!-- Fare Summary Section -->
                        <div class="summary-title">
                            <i class="bi bi-currency-exchange me-2"></i>Fare Details
                        </div>
                        <div class="summary-item">
                            <div>Price Per km:</div>
                            <div id="summaryPricePerKm"><%=isEditMode ? "Rs. " + vehicle.getPricePerKm() : "Rs. 0"%>
                            </div>
                        </div>
                        <div class="summary-item">
                            <div>Distance:</div>
                            <div id="summaryDistance"><%=isEditMode ? booking.getDistance() + " km" : "0 km"%>
                            </div>
                        </div>
                        <div class="summary-item">
                            <div>Base Fare:</div>
                            <div id="summaryFare"><%=isEditMode ? "Rs. " + booking.getFare() : "Rs. 0.00"%>
                            </div>
                        </div>
                        <div class="summary-item">
                            <div>Tax:</div>
                            <div id="summaryTax"><%=isEditMode ? "Rs. " + booking.getTax() : "Rs. 0.00"%>
                            </div>
                        </div>
                        <div class="summary-item">
                            <div>Discount:</div>
                            <div id="summaryDiscount"><%=isEditMode ? "Rs. " + booking.getDiscount() : "Rs. 0.00"%>
                            </div>
                        </div>

                        <div class="summary-total d-flex justify-content-between">
                            <div><i class="bi bi-cash-coin"></i> Net Total:</div>
                            <div id="summaryNetTotal"><%=isEditMode ? "Rs. " + booking.getNetTotal() : "Rs. 0.00"%>
                            </div>
                        </div>

                        <!-- Booking Details Summary -->
                        <div class="booking-details-summary mt-3">
                            <div class="summary-title">
                                <i class="bi bi-info-circle me-2"></i>Booking Details
                            </div>
                            <div class="summary-item">
                                <div>Date:</div>
                                <div id="summaryDate"><%=isEditMode ? booking.getBookingDate() : "Not selected"%>
                                </div>
                            </div>
                            <div class="summary-item">
                                <div>Time:</div>
                                <div id="summaryTime"><%=isEditMode ? booking.getPickupTime() + " - " + booking.getReleaseTime() : "Not selected"%>
                                </div>
                            </div>
                            <div class="summary-item">
                                <div>From:</div>
                                <div id="summaryPickup"><%=isEditMode ? booking.getPickupLocation() : "Not specified"%>
                                </div>
                            </div>
                            <div class="summary-item">
                                <div> To:</div>
                                <div id="summaryDestination"><%=isEditMode ? booking.getDestination() : "Not specified"%>
                                </div>
                            </div>
                        </div>

                        <!-- Vehicle Details Summary -->
                        <div class="vehicle-details-summary mt-3" id="vehicleSummarySection"
                             style="<%=isEditMode ? "display: block;" : "display: none;"%>">
                            <div class="summary-title">
                                <i class="bi bi-car-front me-2"></i>Vehicle Details
                            </div>
                            <div class="summary-item">
                                <div>Vehicle:</div>
                                <div id="summaryVehicle"><%=isEditMode ? vehicle.getBrand() + " " + vehicle.getModel() : "Not selected"%>
                                </div>
                            </div>
                            <div class="summary-item">
                                <div>Driver:</div>
                                <div id="summaryDriver"><%=isEditMode && driver != null ? driver.getName() : "Not assigned"%>
                                </div>
                            </div>
                            <div class="summary-item">
                                <div>Capacity:</div>
                                <div id="summaryCapacity"><%=isEditMode ? vehicle.getCapacity() : "-"%>
                                </div>
                            </div>
                        </div>

                        <div class="mt-3">
                            <span>Status: </span>
                            <span id="summaryStatus"
                                  class="status-badge status-<%=isEditMode ? booking.getStatus().toString() : "pending"%>">
                            <%=isEditMode ? booking.getStatus().toString().substring(0, 1).toUpperCase() + booking.getStatus().toString().substring(1) : "Pending"%>
                        </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0-rc.0/js/select2.min.js"></script>
<script>
    let selectedPricePerKm = <%=isEditMode && vehicle != null ? vehicle.getPricePerKm() : 0%>;

    // Store all available vehicles for filtering
    const availableVehicles = [
        <% if (availableVehicles != null && !availableVehicles.isEmpty()) {
            for (int i = 0; i < availableVehicles.size(); i++) {
                VehicleDriverDTO vd = availableVehicles.get(i);
                %>
        {
            vehicleId: '<%=vd.getVehicle().getId()%>',
            brand: '<%=vd.getVehicle().getBrand()%>',
            model: '<%=vd.getVehicle().getModel()%>',
            licensePlate: '<%=vd.getVehicle().getLicensePlate()%>',
            capacity: <%=vd.getVehicle().getCapacity()%>,
            pricePerKm: <%=vd.getVehicle().getPricePerKm()%>,
            driverName: '<%=vd.getDriver().getName()%>',
            driverPhone: '<%=vd.getDriver().getMobileNo()%>',
            driverLicense: '<%=vd.getDriver().getLicenseNo()%>'
        }<%=i < availableVehicles.size() - 1 ? "," : ""%>
        <% }
} %>
    ];

    $(document).ready(function () {
        // Initialize Select2 for customer dropdown
        $('#customerSelect').select2({
            placeholder: "Select customer by NIC or name",
            allowClear: true,
            templateResult: formatCustomerResult,
            templateSelection: formatCustomerSelection
        });

        // Initialize booking date with current date if not in edit mode
        <% if (!isEditMode) { %>
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('bookingDate').value = today;
        document.getElementById('bookingDate').min = today;
        <% } %>

        // Set default status to pending for new bookings
        <% if (!isEditMode) { %>
        document.getElementById('bookingStatus').value = "pending";
        <% } %>

        // Customer selection change event
        $('#customerSelect').on('change', function () {
            const customerId = $(this).val();
            if (customerId) {
                const selectedOption = $(this).find('option:selected');
                const name = selectedOption.data('name');
                const nic = selectedOption.data('nic');
                const phone = selectedOption.data('phone');
                const email = selectedOption.data('email');

                $('#customerName').text(name);
                $('#customerNIC').text(nic);
                $('#customerPhone').text(phone);
                $('#customerEmail').text(email);

                $('#customerInfo').slideDown();
            } else {
                $('#customerInfo').slideUp();
            }
        });

        // Booking status change event
        $('#bookingStatus').on('change', function () {
            updateStatusBadge();
        });

        // Initial status badge
        updateStatusBadge();

        // If in edit mode, calculate fare and net total
        <% if (isEditMode) { %>
        calculateFare();
        <% } %>
    });

    // Format customer results in the dropdown for better visibility
    function formatCustomerResult(customer) {
        if (!customer.id) return customer.text;
        const $option = $(customer.element);
        const nic = $option.data('nic');
        const name = $option.data('name');
        const phone = $option.data('phone');
        return $('<div class="select2-result-customer">' +
            '<div class="select2-result-customer__name">' + name + '</div>' +
            '<div class="select2-result-customer__details">' +
            '<small>NIC: ' + nic + ' | Phone: ' + phone + '</small>' +
            '</div>' +
            '</div>');
    }

    // Format selected customer in the dropdown
    function formatCustomerSelection(customer) {
        if (!customer.id) return customer.text;
        const $option = $(customer.element);
        const nic = $option.data('nic');
        const name = $option.data('name');
        return name + ' (' + nic + ')';
    }

    function updateStatusBadge() {
        const status = document.getElementById('bookingStatus').value;
        const statusBadge = document.getElementById('summaryStatus');
        // Remove all status classes
        statusBadge.className = "status-badge";
        // Add specific status class
        statusBadge.classList.add("status-" + status);
        // Update text
        statusBadge.textContent = status.charAt(0).toUpperCase() + status.slice(1);
    }

    function clearVehicleSearch() {
        // Reset all filter fields
        document.getElementById('capacityFilter').value = '';
        document.getElementById('brandFilter').value = '';
        document.getElementById('modelFilter').value = '';
        // Hide the vehicle table if it's currently shown
        $('#vehicleTableContainer').hide();
    }

    function searchVehicles() {
        const capacityFilter = parseInt($('#capacityFilter').val()) || 0;
        const brandFilter = $('#brandFilter').val();
        const modelFilter = $('#modelFilter').val();

        // Filter vehicles based on criteria
        const filteredVehicles = availableVehicles.filter(vehicle => {
            if (capacityFilter > 0 && vehicle.capacity < capacityFilter) {
                return false;
            }
            if (brandFilter && vehicle.brand !== brandFilter) {
                return false;
            }
            if (modelFilter && vehicle.model !== modelFilter) {
                return false;
            }
            return true;
        });

        // Clear table body
        const tableBody = document.getElementById('vehicleTableBody');
        tableBody.innerHTML = '';

        // Show or hide vehicle table based on results
        if (filteredVehicles.length === 0) {
            $('#vehicleTableContainer').hide();
            alert('No vehicles match your search criteria. Please adjust your filters and try again.');
            return;
        }

        // Populate table with filtered vehicles
        filteredVehicles.forEach(vehicle => {
            const row = document.createElement('tr');
            row.dataset.vehicleId = vehicle.vehicleId;
            row.innerHTML = `
            <td>${vehicle.brand} ${vehicle.model}</td>
            <td>${vehicle.licensePlate}</td>
            <td>${vehicle.capacity} passengers</td>
            <td>Rs. ${vehicle.pricePerKm.toFixed(2)}</td>
            <td>
                <button class="btn btn-sm btn-primary select-vehicle-btn" type="button"
                        onclick="selectVehicleFromTable('${vehicle.vehicleId}', ${vehicle.pricePerKm}, '${vehicle.driverName}', '${vehicle.driverPhone}', '${vehicle.driverLicense}')">
                    Select
                </button>
            </td>
        `;
            tableBody.appendChild(row);
        });

        // Show the vehicle table
        $('#vehicleTableContainer').show();
    }

    function selectVehicleFromTable(vehicleId, pricePerKm, driverName, driverPhone, driverLicense) {
        // Set selected vehicle ID in the hidden field
        document.getElementById('selectedVehicleId').value = vehicleId;
        selectedPricePerKm = pricePerKm;

        // Find the vehicle from our data
        const selectedVehicle = availableVehicles.find(v => v.vehicleId === vehicleId);

        // Update vehicle info display
        if (selectedVehicle) {
            document.getElementById('vehicleBrand').textContent = selectedVehicle.brand;
            document.getElementById('vehicleModel').textContent = selectedVehicle.model;
            document.getElementById('vehiclePricePerKm').textContent = selectedVehicle.pricePerKm.toFixed(2);

            // Show vehicle info section
            document.getElementById('vehicleInfo').style.display = 'block';

            // Update summary section
            document.getElementById('summaryVehicle').textContent = selectedVehicle.brand + ' ' + selectedVehicle.model;
            document.getElementById('summaryCapacity').textContent = selectedVehicle.capacity;
            document.getElementById('vehicleSummarySection').style.display = 'block';
        }

        // Update driver info display
        document.getElementById('driverName').textContent = driverName;
        document.getElementById('driverPhone').textContent = driverPhone;
        document.getElementById('driverLicense').textContent = driverLicense;

        // Show driver info section
        document.getElementById('driverInfo').style.display = 'block';

        // Update summary for driver
        document.getElementById('summaryDriver').textContent = driverName;

        // Recalculate fare based on new price per km
        calculateFare();

        // Hide the vehicle table after selection
        document.getElementById('vehicleTableContainer').style.display = 'none';
    }

    function calculateFare() {
        const distance = parseFloat(document.getElementById('distance').value) || 0;

        if (distance > 0 && selectedPricePerKm > 0) {
            // Calculate the fare based on distance and price per km
            const fare = distance * selectedPricePerKm;

            // Update the fare in the form
            document.getElementById('fare').value = fare.toFixed(2);

            // Update the fare in the summary
            document.getElementById('summaryFare').textContent = 'Rs. ' + fare.toFixed(2);
            document.getElementById('summaryDistance').textContent = distance + ' km';

            // Calculate net total
            calculateNetTotal();
        }
    }

    function calculateNetTotal() {
        const fare = parseFloat(document.getElementById('fare').value) || 0;
        const discount = parseFloat(document.getElementById('discount').value) || 0;
        const tax = parseFloat(document.getElementById('tax').value) || 0;

        // Calculate net total
        const netTotal = fare + tax - discount;

        // Update net total in the form
        document.getElementById('netTotal').value = netTotal.toFixed(2);

        // Update summary
        document.getElementById('summaryDiscount').textContent = 'Rs. ' + discount.toFixed(2);
        document.getElementById('summaryTax').textContent = 'Rs. ' + tax.toFixed(2);
        document.getElementById('summaryNetTotal').textContent = 'Rs. ' + netTotal.toFixed(2);
    }

    function validateForm() {
        const form = document.getElementById('bookingForm');

        // Validate customer selection
        <% if (!isReadOnlyStatus && !isEditMode) { %>
        if (!form.customerId.value) {
            alert('Please select a customer');
            return false;
        }
        <% } %>

        // Validate date fields
        if (!form.bookingDate.value) {
            alert('Please select a booking date');
            return false;
        }

        // Validate time fields
        if (!form.pickupTime.value || !form.releaseTime.value) {
            alert('Please specify both pickup and release times');
            return false;
        }

        // Validate location fields
        if (!form.pickupLocation.value || !form.destination.value) {
            alert('Please specify both pickup location and destination');
            return false;
        }

        // Validate distance
        if (!form.distance.value || parseFloat(form.distance.value) <= 0) {
            alert('Please enter a valid distance');
            return false;
        }

        // Validate vehicle selection
        <% if (!isReadOnlyStatus && !isEditMode) { %>
        if (!form.vehicleId.value) {
            alert('Please select a vehicle');
            return false;
        }
        <% } %>

        // If all validations pass, update the summary before submitting
        updateSummary();

        return true;
    }

    function updateSummary() {
        // Update booking details in summary
        document.getElementById('summaryDate').textContent = document.getElementById('bookingDate').value;
        document.getElementById('summaryTime').textContent =
            document.getElementById('pickupTime').value + ' - ' +
            document.getElementById('releaseTime').value;
        document.getElementById('summaryPickup').textContent = document.getElementById('pickupLocation').value;
        document.getElementById('summaryDestination').textContent = document.getElementById('destination').value;
    }

    // Update summary when form fields change
    document.getElementById('bookingDate').addEventListener('change', function () {
        document.getElementById('summaryDate').textContent = this.value;
    });

    document.getElementById('pickupTime').addEventListener('change', function () {
        document.getElementById('summaryTime').textContent =
            this.value + ' - ' + document.getElementById('releaseTime').value;
    });

    document.getElementById('releaseTime').addEventListener('change', function () {
        document.getElementById('summaryTime').textContent =
            document.getElementById('pickupTime').value + ' - ' + this.value;
    });

    document.getElementById('pickupLocation').addEventListener('change', function () {
        document.getElementById('summaryPickup').textContent = this.value;
    });

    document.getElementById('destination').addEventListener('change', function () {
        document.getElementById('summaryDestination').textContent = this.value;
    });
</script>
</body>
</html>
