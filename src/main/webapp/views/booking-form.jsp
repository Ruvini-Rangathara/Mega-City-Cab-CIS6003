<%--
  Created by IntelliJ IDEA.
  User: ruvini
  Date: 2025-02-24
  Time: 16.32
  To change this template use File | Settings | File Templates.
--%>

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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0-rc.0/css/select2.min.css" rel="stylesheet">
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
            top: 80px;
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
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Create New Booking</h5>
                </div>
                <div class="card-body">
                    <form id="bookingForm" action="${pageContext.request.contextPath}/booking-servlet" method="post" onsubmit="return validateForm()">
                        <input type="hidden" id="action" name="action" value="add">
                        <input type="hidden" id="bookingId" name="bookingId">
                        <input type="hidden" id="selectedVehicleId" name="vehicleId">
                        <input type="hidden" id="userId" name="userId" value="<%= session.getAttribute("userId") != null ? session.getAttribute("userId") : "" %>">
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
                                    <input type="date" class="form-control" id="bookingDate" name="bookingDate" required>
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
                                    <input type="time" class="form-control" id="releaseTime" name="releaseTime" required>
                                    <label>Release Time</label>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="pickupLocation" name="pickupLocation" required>
                                    <label>Pickup Location</label>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="destination" name="destination" required>
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
                                        <option value="<%= status %>"><%= status.toString() %></option>
                                        <% } %>
                                    </select>
                                    <label>Booking Status</label>
                                </div>
                            </div>
                        </div>

                        <!-- Vehicle Selection -->
                        <h6 class="mb-3 mt-4">Select Vehicle</h6>

                        <!-- Vehicle Filters -->
                        <div class="filter-controls">
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label for="capacityFilter" class="form-label">Capacity</label>
                                    <select class="form-select" id="capacityFilter">
                                        <option value="">All</option>
                                        <option value="4">4 Passengers</option>
                                        <option value="6">6 Passengers</option>
                                        <option value="8">8 Passengers</option>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <label for="brandFilter" class="form-label">Brand</label>
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
                                        <option value="<%= brand %>"><%= brand %></option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <label for="modelFilter" class="form-label">Model</label>
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
                                        <option value="<%= model %>"><%= model %></option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="row g-3" id="vehicleList">
                            <%
                                if (availableVehicles != null && !availableVehicles.isEmpty()) {
                                    for (VehicleDriverDTO vd : availableVehicles) {
                            %>
                            <div class="col-md-6 vehicle-item"
                                 data-capacity="<%= vd.getVehicle().getCapacity() %>"
                                 data-brand="<%= vd.getVehicle().getBrand() %>"
                                 data-model="<%= vd.getVehicle().getModel() %>">
                                <div class="card vehicle-card" onclick="selectVehicle(this, '<%= vd.getVehicle().getId() %>', <%= vd.getVehicle().getPricePerKm() %>, '<%= vd.getDriver().getName() %>', '<%= vd.getDriver().getMobileNo() %>', '<%= vd.getDriver().getLicenseNo() %>')">
                                    <div class="card-body">
                                        <h6 class="card-title"><%= vd.getVehicle().getBrand() %> <%= vd.getVehicle().getModel() %></h6>
                                        <p class="card-text mb-1">
                                            <small class="text-muted">
                                                License Plate: <%= vd.getVehicle().getLicensePlate() %><br>
                                                Capacity: <%= vd.getVehicle().getCapacity() %> passengers<br>
                                                Price per km: Rs. <%= vd.getVehicle().getPricePerKm() %>
                                            </small>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            %>
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
        </div>

        <!-- Booking Summary -->
        <div class="col-lg-4">
            <div class="card summary-card">
                <div class="card-header">
                    <h5 class="mb-0">Booking Summary</h5>
                </div>
                <div class="card-body">
                    <div class="d-flex justify-content-between mb-3">
                        <span>Distance:</span>
                        <span id="summaryDistance">0 km</span>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <span>Fare:</span>
                        <span id="summaryFare">Rs. 0.00</span>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <span>Tax:</span>
                        <span id="summaryTax">Rs. 0.00</span>
                    </div>
                    <div class="d-flex justify-content-between mb-3">
                        <span>Discount:</span>
                        <span id="summaryDiscount">Rs. 0.00</span>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between">
                        <strong>Net Total:</strong>
                        <strong id="summaryNetTotal">Rs. 0.00</strong>
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

    $(document).ready(function() {
        // Initialize Select2 for customer dropdown
        $('#customerSelect').select2({
            placeholder: "Select customer by NIC or name",
            allowClear: true
        });

        // Initialize booking date with current date
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('bookingDate').value = today;
        document.getElementById('bookingDate').min = today;

        // Set default status to pending
        document.getElementById('bookingStatus').value = "pending";

        // Customer selection change event
        $('#customerSelect').on('change', function() {
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
        $('#bookingStatus').on('change', function() {
            updateStatusBadge();
        });

        // Setup vehicle filters
        $('#capacityFilter, #brandFilter, #modelFilter').on('change', function() {
            filterVehicles();
        });

        // Initial status badge
        updateStatusBadge();
    });

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

    function filterVehicles() {
        const capacityFilter = $('#capacityFilter').val();
        const brandFilter = $('#brandFilter').val();
        const modelFilter = $('#modelFilter').val();

        $('.vehicle-item').each(function() {
            const capacity = $(this).data('capacity');
            const brand = $(this).data('brand');
            const model = $(this).data('model');

            let showVehicle = true;

            if (capacityFilter && capacity != capacityFilter) {
                showVehicle = false;
            }

            if (brandFilter && brand !== brandFilter) {
                showVehicle = false;
            }

            if (modelFilter && model !== modelFilter) {
                showVehicle = false;
            }

            $(this).toggle(showVehicle);
        });
    }

    function selectVehicle(element, vehicleId, pricePerKm, driverName, driverPhone, driverLicense) {
        // Remove selection from all vehicles
        document.querySelectorAll('.vehicle-card').forEach(card => {
            card.classList.remove('selected');
        });

        // Add selection to clicked vehicle
        element.classList.add('selected');
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
