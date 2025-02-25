<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.VehicleDriverDTO" %>
<%@ page import="com.project.megacitycab.dto.CustomerDTO" %>
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
            --info-color: #17a2b8;
            --warning-color: #ffc107;
        }

        body {
            background-color: var(--background-color);
            min-height: 100vh;
            padding-top: 60px;
        }

        .navbar {
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .navbar-brand {
            color: var(--primary-color);
            font-weight: bold;
            font-size: 1.5rem;
        }

        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
            margin-top: 2rem;
            padding-top: 10px;
        }

        .vehicle-card {
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .vehicle-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 0.75rem 1.5rem rgba(0, 0, 0, 0.12);
        }

        .vehicle-card.selected {
            border: 2px solid var(--primary-color);
        }

        .summary-card {
            position: sticky;
            margin-top: 2rem;
        }

        .customer-info {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 10px;
            margin-top: 10px;
            display: none;
        }

        .driver-info {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 10px;
            margin-top: 10px;
            display: none;
        }

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

        /* Vehicle section styling */
        .vehicle-section {
            background-color: white;
            border-radius: 1rem;
            padding: 1.5rem;
            margin-top: 1rem;
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

        .filter-controls {
            background: #f1f1f1;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 15px;
        }

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

        /* Enhanced customer selection dropdown styling */
        .select2-result-customer__name {
            font-weight: bold;
        }

        .select2-result-customer__details {
            color: #6c757d;
        }

        .vehicle-table {
            display: none;
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

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: #e59100;
            border-color: #e59100;
        }


        /* Enhanced summary card styling */
        .summary-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            /*margin-bottom: 1rem;*/
            padding: 0.5rem;
            border-radius: 0.5rem;
            transition: all 0.3s ease;
        }

        .summary-item:hover {
            background-color: rgba(252, 163, 17, 0.1);
        }

        .summary-item i {
            margin-right: 0.5rem;
            font-size: 1.2rem;
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

        .booking-details-summary {
            margin-top: 1.5rem;
        }

        .vehicle-details-summary {
            margin-top: 1.5rem;
        }

        .card-header {
            background-color: white;
            border-bottom: 2px solid rgba(252, 163, 17, 0.3);
        }

        .card-header h5 {
            color: var(--primary-color);
        }

        .icon-primary {
            color: var(--primary-color);
        }

        .icon-summary {
            color: var(--secondary-color);
        }

        .animated-icon {
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.1);
            }
            100% {
                transform: scale(1);
            }
        }
    </style>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">MEGA CITY CAB</a>
        <div class="ms-auto d-flex align-items-center">
            <%
                String currentUser = (String) session.getAttribute("username");
                if (currentUser == null) currentUser = "User";
            %>
            <span class="me-3"><i class="bi bi-person-circle me-2"></i><%= currentUser %></span>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <!-- Booking Form -->
        <div class="col-lg-8">

            <div class="card mb-2">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="bi bi-calendar2-event icon-primary me-2"></i>
                        Manage Booking</h5>
                </div>
                <div class="card-body">
                    <form id="bookingForm" action="${pageContext.request.contextPath}/booking-servlet" method="post"
                          onsubmit="return validateForm()">
                        <input type="hidden" id="action" name="action" value="add">
                        <input type="hidden" id="bookingId" name="bookingId">
                        <input type="hidden" id="selectedVehicleId" name="vehicleId">
                        <input type="hidden" id="userId" name="userId"
                               value="<%= session.getAttribute("userId") != null ? session.getAttribute("userId") : "" %>">
                        <input type="hidden" id="fare" name="fare">
                        <input type="hidden" id="netTotal" name="netTotal">

                        <!-- Customer Details -->
                        <h6 class="mb-3">Customer Information</h6>
                        <div class="row g-3">
                            <div class="col-md-12">
                                <label for="customerSelect" class="form-label">Select Customer</label>
                                <select class="form-select" id="customerSelect" name="customerId" required>
                                    <option value="">Select customer by NIC or name</option>
                                    <%
                                        List<CustomerDTO> customers = (List<CustomerDTO>) request.getAttribute("customers");
                                        if (customers != null) {
                                            for (CustomerDTO customer : customers) {
                                    %>
                                    <option value="<%= customer.getId() %>"
                                            data-nic="<%= customer.getNic() %>"
                                            data-name="<%= customer.getName() %>"
                                            data-phone="<%= customer.getMobileNo() %>"
                                            data-email="<%= customer.getEmail() %>">
                                        <%= customer.getNic() %> - <%= customer.getName() %>
                                    </option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <div class="col-md-12">
                                <div id="customerInfo" class="customer-info">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <p class="mb-1"><strong>Name:</strong> <span id="customerName"></span></p>
                                            <p class="mb-1"><strong>NIC:</strong> <span id="customerNIC"></span></p>
                                        </div>
                                        <div class="col-md-6">
                                            <p class="mb-1"><strong>Phone:</strong> <span id="customerPhone"></span></p>
                                            <p class="mb-1"><strong>Email:</strong> <span id="customerEmail"></span></p>
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
                                           required>
                                    <label>Booking Date</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="time" class="form-control" id="pickupTime" name="pickupTime" required>
                                    <label>Pickup Time</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="time" class="form-control" id="releaseTime" name="releaseTime"
                                           required>
                                    <label>Release Time</label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="pickupLocation" name="pickupLocation"
                                           required>
                                    <label>Pickup Location</label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="destination" name="destination"
                                           required>
                                    <label>Destination</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="distance" name="distance"
                                           required min="1" step="0.1" onchange="calculateFare()">
                                    <label>Distance (km)</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="discount" name="discount"
                                           value="0" min="0" step="0.01" onchange="calculateNetTotal()">
                                    <label>Discount (Rs.)</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="tax" name="tax"
                                           value="0" min="0" step="0.01" onchange="calculateNetTotal()">
                                    <label>Tax (Rs.)</label>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-floating">
                                    <select class="form-select" id="bookingStatus" name="status">
                                        <%
                                            for (com.project.megacitycab.constant.BookingStatus status : com.project.megacitycab.constant.BookingStatus.values()) {
                                        %>
                                        <option value="<%= status %>"><%= status.toString() %>
                                        </option>
                                        <% } %>
                                    </select>
                                    <label>Booking Status</label>
                                </div>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="d-flex justify-content-end gap-2 mt-4">
                            <a href="${pageContext.request.contextPath}/booking-list.jsp" class="btn btn-secondary">
                                <i class="bi bi-x-circle me-2"></i>Cancel
                            </a>
                            <button type="submit" class="btn btn-primary" id="submitButton">
                                <i class="bi bi-plus-circle me-2"></i>Create Booking
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- Vehicle Selection Section -->
            <div class="vehicle-section">
                <div class="card-header">
                    <h5 class="mb-2">
                        <i class="bi bi-car-front-fill me-2 icon-primary animated-icon"></i>
                        Vehicle Selection</h5>
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
                                        // Get unique brands from available vehicles
                                        List<VehicleDriverDTO> availableVehicles = (List<VehicleDriverDTO>) request.getAttribute("availableVehicles");
                                        if (availableVehicles != null && !availableVehicles.isEmpty()) {
                                            java.util.Set<String> uniqueBrands = new java.util.HashSet<>();
                                            for (VehicleDriverDTO vd : availableVehicles) {
                                                uniqueBrands.add(vd.getVehicle().getBrand());
                                            }
                                            for (String brand : uniqueBrands) {
                                    %>
                                    <option value="<%= brand %>"><%= brand %>
                                    </option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                                <label for="brandFilter">Brand</label>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-floating">
                                <select class="form-select" id="modelFilter">
                                    <option value="">All</option>
                                    <%
                                        // Get unique models from available vehicles
                                        if (availableVehicles != null && !availableVehicles.isEmpty()) {
                                            java.util.Set<String> uniqueModels = new java.util.HashSet<>();
                                            for (VehicleDriverDTO vd : availableVehicles) {
                                                uniqueModels.add(vd.getVehicle().getModel());
                                            }
                                            for (String model : uniqueModels) {
                                    %>
                                    <option value="<%= model %>"><%= model %>
                                    </option>
                                    <%
                                            }
                                        }
                                    %>
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
                <div id="vehicleTableContainer" class="vehicle-table mt-3">
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

                <!-- Driver Information -->
                <div id="driverInfo" class="driver-info mt-3">
                    <h6 class="mb-2">Driver Information</h6>
                    <div class="row">
                        <div class="col-md-4">
                            <p class="mb-1"><strong>Name:</strong> <span id="driverName"></span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1"><strong>Phone:</strong> <span id="driverPhone"></span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1"><strong>License:</strong> <span id="driverLicense"></span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="card summary-card">
                <div class="card-header">
                    <h5 class="mb-0">
                        <i class="bi bi-clipboard-check me-2 icon-primary animated-icon"></i>
                        Booking Summary</h5>
                </div>
                <div class="card-body">
                    <!-- Fare Summary Section -->
                    <div class="summary-title">
                        <i class="bi bi-currency-exchange me-2 icon-primary"></i>Fare Details
                    </div>
                    <div class="summary-item">
                        <div><i class="bi bi-signpost-2-fill icon-summary"></i> Distance:</div>
                        <div id="summaryDistance">0 km</div>
                    </div>
                    <div class="summary-item">
                        <div><i class="bi bi-cash-stack icon-summary"></i> Base Fare:</div>
                        <div id="summaryFare">Rs. 0.00</div>
                    </div>
                    <div class="summary-item">
                        <div><i class="bi bi-receipt icon-summary"></i> Tax:</div>
                        <div id="summaryTax">Rs. 0.00</div>
                    </div>
                    <div class="summary-item">
                        <div><i class="bi bi-tag-fill icon-summary"></i> Discount:</div>
                        <div id="summaryDiscount">Rs. 0.00</div>
                    </div>

                    <div class="summary-total d-flex justify-content-between">
                        <div><i class="bi bi-cash-coin icon-summary"></i> Net Total:</div>
                        <div id="summaryNetTotal">Rs. 0.00</div>
                    </div>

                    <!-- Booking Details Summary -->
                    <div class="booking-details-summary">
                        <div class="summary-title">
                            <i class="bi bi-info-circle me-2 icon-primary"></i>Booking Details
                        </div>
                        <div class="summary-item">
                            <div><i class="bi bi-calendar-date icon-summary"></i> Date:</div>
                            <div id="summaryDate">Not selected</div>
                        </div>
                        <div class="summary-item">
                            <div><i class="bi bi-clock icon-summary"></i> Time:</div>
                            <div id="summaryTime">Not selected</div>
                        </div>
                        <div class="summary-item">
                            <div><i class="bi bi-geo-alt-fill icon-summary"></i> From:</div>
                            <div id="summaryPickup">Not specified</div>
                        </div>
                        <div class="summary-item">
                            <div><i class="bi bi-geo-fill icon-summary"></i> To:</div>
                            <div id="summaryDestination">Not specified</div>
                        </div>
                    </div>

                    <!-- Vehicle Details Summary (will appear when vehicle is selected) -->
                    <div class="vehicle-details-summary" id="vehicleSummarySection" style="display: none;">
                        <div class="summary-title">
                            <i class="bi bi-car-front me-2 icon-primary"></i>Vehicle Details
                        </div>
                        <div class="summary-item">
                            <div><i class="bi bi-car-front-fill icon-summary"></i> Vehicle:</div>
                            <div id="summaryVehicle">Not selected</div>
                        </div>
                        <div class="summary-item">
                            <div><i class="bi bi-person-fill icon-summary"></i> Driver:</div>
                            <div id="summaryDriver">Not assigned</div>
                        </div>
                        <div class="summary-item">
                            <div><i class="bi bi-people-fill icon-summary"></i> Capacity:</div>
                            <div id="summaryCapacity">-</div>
                        </div>
                    </div>

                    <div class="mt-3">
                        <span>Status: </span>
                        <span id="summaryStatus" class="status-badge status-pending">Pending</span>
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
    let selectedPricePerKm = 0;

    // Store all available vehicles for filtering
    const availableVehicles = [
        <% if (availableVehicles != null && !availableVehicles.isEmpty()) {
            for (int i = 0; i < availableVehicles.size(); i++) {
                VehicleDriverDTO vd = availableVehicles.get(i);
        %>
        {
            vehicleId: '<%= vd.getVehicle().getId() %>',
            brand: '<%= vd.getVehicle().getBrand() %>',
            model: '<%= vd.getVehicle().getModel() %>',
            licensePlate: '<%= vd.getVehicle().getLicensePlate() %>',
            capacity: <%= vd.getVehicle().getCapacity() %>,
            pricePerKm: <%= vd.getVehicle().getPricePerKm() %>,
            driverName: '<%= vd.getDriver().getName() %>',
            driverPhone: '<%= vd.getDriver().getMobileNo() %>',
            driverLicense: '<%= vd.getDriver().getLicenseNo() %>'
        }<%= i < availableVehicles.size() - 1 ? "," : "" %>
        <% } } %>
    ];

    $(document).ready(function () {
        // Initialize Select2 for customer dropdown with custom templates
        $('#customerSelect').select2({
            placeholder: "Select customer by NIC or name",
            allowClear: true,
            templateResult: formatCustomerResult,
            templateSelection: formatCustomerSelection
        });

        // Initialize booking date with current date
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('bookingDate').value = today;
        document.getElementById('bookingDate').min = today;

        // Set default status to pending
        document.getElementById('bookingStatus').value = "pending";

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
    });

    // Format customer results in the dropdown for better visibility
    function formatCustomerResult(customer) {
        if (!customer.id) {
            return customer.text;
        }

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
        if (!customer.id) {
            return customer.text;
        }

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
        // Remove selection from all rows
        document.querySelectorAll('#vehicleTableBody tr').forEach(row => {
            row.classList.remove('selected');
        });

        // Add selection to the clicked row
        const selectedRow = document.querySelector(`#vehicleTableBody tr[data-vehicle-id="${vehicleId}"]`);
        if (selectedRow) {
            selectedRow.classList.add('selected');
        }

        // Set the selected vehicle ID
        document.getElementById('selectedVehicleId').value = vehicleId;
        selectedPricePerKm = pricePerKm;

        // Update driver info
        document.getElementById('driverName').textContent = driverName;
        document.getElementById('driverPhone').textContent = driverPhone;
        document.getElementById('driverLicense').textContent = driverLicense;
        document.getElementById('driverInfo').style.display = 'block';

        calculateFare();
    }

    function calculateFare() {
        const distance = parseFloat(document.getElementById('distance').value) || 0;
        const fare = distance * selectedPricePerKm;

        document.getElementById('fare').value = fare.toFixed(2);
        document.getElementById('summaryDistance').textContent = distance + ' km';
        document.getElementById('summaryFare').textContent = 'Rs. ' + fare.toFixed(2);

        calculateNetTotal();
    }

    function calculateNetTotal() {
        const fare = parseFloat(document.getElementById('fare').value) || 0;
        const tax = parseFloat(document.getElementById('tax').value) || 0;
        const discount = parseFloat(document.getElementById('discount').value) || 0;

        const netTotal = fare + tax - discount;

        document.getElementById('netTotal').value = netTotal.toFixed(2);
        document.getElementById('summaryTax').textContent = 'Rs. ' + tax.toFixed(2);
        document.getElementById('summaryDiscount').textContent = 'Rs. ' + discount.toFixed(2);
        document.getElementById('summaryNetTotal').textContent = 'Rs. ' + netTotal.toFixed(2);
    }

    function validateForm() {
        if (!document.getElementById('selectedVehicleId').value) {
            alert('Please select a vehicle');
            return false;
        }

        const bookingDate = new Date(document.getElementById('bookingDate').value);
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        if (bookingDate < today) {
            alert('Booking date cannot be in the past');
            return false;
        }

        const pickupTime = document.getElementById('pickupTime').value;
        const releaseTime = document.getElementById('releaseTime').value;

        if (pickupTime >= releaseTime) {
            alert('Release time must be after pickup time');
            return false;
        }

        const distance = parseFloat(document.getElementById('distance').value);
        if (distance <= 0) {
            alert('Distance must be greater than 0');
            return false;
        }

        const discount = parseFloat(document.getElementById('discount').value);
        const fare = parseFloat(document.getElementById('fare').value);

        if (discount > fare) {
            alert('Discount cannot be greater than fare');
            return false;
        }

        return true;
    }
</script>
</body>
</html>
