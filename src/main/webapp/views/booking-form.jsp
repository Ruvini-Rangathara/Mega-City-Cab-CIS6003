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
            /*padding-top: 60px;*/
        }

        .summary-card {
            position: sticky;
            top: 20px;
            margin-top: 2rem;
            width: 100%;
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

        .vehicle-and-driver-section {
            border: none;
            background-color: white;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
            margin-bottom: 2rem;
            padding: 5px 1rem;
        }

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

        .customer-info, .vehicle-info, .driver-info {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 10px;
            margin-top: 10px;
            display: none;
        }

        .vehicle-table {
            margin-top: 1rem;
            display: block;
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

        .filter-controls {
            background: #f1f1f1;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 15px;
        }

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: #e59100;
            border-color: #e59100;
        }

        .btn-success {
            background-color: var(--success-color);
            border-color: var(--success-color);
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
    </style>
</head>
<body>
<!-- Navbar -->
<%--<nav class="navbar navbar-expand-lg fixed-top">--%>
<%--    <div class="container">--%>
<%--        <a class="navbar-brand" href="${pageContext.request.contextPath}/">MEGA CITY CAB</a>--%>
<%--        <div class="ms-auto d-flex align-items-center">--%>
<%--            <% String currentUser = (String) session.getAttribute("username");--%>
<%--                if (currentUser == null) currentUser = "User"; %>--%>
<%--            <span class="me-3"><i class="bi bi-person-circle me-2"></i><%=currentUser%></span>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</nav>--%>

<%
    BookingDTO booking = (BookingDTO) request.getAttribute("booking");
    CustomerDTO customer = (CustomerDTO) request.getAttribute("customer");
    VehicleDTO vehicle = (VehicleDTO) request.getAttribute("vehicle");
    DriverDTO driver = (DriverDTO) request.getAttribute("driver");
    boolean isEditMode = (booking != null);
    String bookingStatus = isEditMode ? booking.getStatus().toString() : "";
    boolean isReadOnlyStatus = isEditMode && (bookingStatus.equals("completed") || bookingStatus.equals("cancelled"));

    // Preserve form data
    String bookingDate = request.getParameter("bookingDate") != null ? request.getParameter("bookingDate") : (isEditMode ? String.valueOf(booking.getBookingDate()) : "");
    String pickupTime = request.getParameter("pickupTime") != null ? request.getParameter("pickupTime") : (isEditMode ? String.valueOf(booking.getPickupTime()) : "");
    String releaseTime = request.getParameter("releaseTime") != null ? request.getParameter("releaseTime") : (isEditMode ? String.valueOf(booking.getReleaseTime()) : "");
    String customerId = request.getParameter("customerId") != null ? request.getParameter("customerId") : (isEditMode ? customer.getId() : "");
    String pickupLocation = request.getParameter("pickupLocation") != null ? request.getParameter("pickupLocation") : (isEditMode ? booking.getPickupLocation() : "");
    String destination = request.getParameter("destination") != null ? request.getParameter("destination") : (isEditMode ? booking.getDestination() : "");
    String distance = request.getParameter("distance") != null ? request.getParameter("distance") : (isEditMode ? String.valueOf(booking.getDistance()) : "");
    String discount = request.getParameter("discount") != null ? request.getParameter("discount") : (isEditMode ? String.valueOf(booking.getDiscount()) : "0");
    String tax = request.getParameter("tax") != null ? request.getParameter("tax") : (isEditMode ? String.valueOf(booking.getTax()) : "0");
    String fare = request.getParameter("fare") != null ? request.getParameter("fare") : (isEditMode ? String.valueOf(booking.getFare()) : "0");
    String vehicleId = request.getParameter("vehicleId") != null ? request.getParameter("vehicleId") : (isEditMode && vehicle != null ? vehicle.getId() : "");
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
                    <form id="bookingForm" action="${pageContext.request.contextPath}/booking-servlet" method="post" onsubmit="return validateForm()">
                        <input type="hidden" id="action" name="action" value="<%=isEditMode ? "update" : "add"%>">
                        <input type="hidden" id="bookingId" name="bookingId" value="<%=isEditMode ? booking.getId() : ""%>">
                        <input type="hidden" id="selectedVehicleId" name="vehicleId" value="<%=vehicleId%>">
                        <input type="hidden" id="userId" name="userId" value="<%=session.getAttribute("userId") != null ? session.getAttribute("userId") : ""%>">
                        <input type="hidden" id="fare" name="fare" value="<%=fare%>">
                        <input type="hidden" id="netTotal" name="netTotal" value="<%=isEditMode ? booking.getNetTotal() : "0"%>">

                        <!-- Customer Details -->
                        <h6 class="mb-3">Customer Information</h6>
                        <div class="row g-3">
                            <% if (!isEditMode) { %>
                            <div class="col-md-12">
                                <label for="customerSelect" class="form-label">Select Customer</label>
                                <select class="form-select" id="customerSelect" name="customerId" required>
                                    <option value="">Select customer by NIC or name</option>
                                    <%
                                        List<CustomerDTO> customers = (List<CustomerDTO>) request.getAttribute("customers");
                                        if (customers != null) {
                                            for (CustomerDTO c : customers) {
                                                String selected = c.getId().equals(customerId) ? "selected" : "";
                                    %>
                                    <option value="<%=c.getId()%>" <%=selected%> data-nic="<%=c.getNic()%>" data-name="<%=c.getName()%>" data-phone="<%=c.getMobileNo()%>" data-email="<%=c.getEmail()%>">
                                        <%=c.getNic()%> - <%=c.getName()%>
                                    </option>
                                    <% }
                                    } %>
                                </select>
                            </div>
                            <% } else { %>
                            <input type="hidden" name="customerId" value="<%=customer.getId()%>">
                            <% } %>

                            <div class="col-md-12">
                                <div id="customerInfo" class="customer-info" style="display: none;">
                                    <h6 class="mb-2">Customer Details</h6>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <p class="mb-1"><strong>Name:</strong> <span id="customerName"><%=isEditMode ? customer.getName() : ""%></span></p>
                                        </div>
                                        <div class="col-md-4">
                                            <p class="mb-1"><strong>NIC:</strong> <span id="customerNIC"><%=isEditMode ? customer.getNic() : ""%></span></p>
                                        </div>
                                        <div class="col-md-4">
                                            <p class="mb-1"><strong>Phone:</strong> <span id="customerPhone"><%=isEditMode ? customer.getMobileNo() : ""%></span></p>
                                        </div>
                                        <div class="col-md-4">
                                            <p class="mb-1"><strong>Email:</strong> <span id="customerEmail"><%=isEditMode ? customer.getEmail() : ""%></span></p>
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
                                    <input type="date" class="form-control" id="bookingDate" name="bookingDate" value="<%=bookingDate%>" required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Booking Date</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="time" class="form-control" id="pickupTime" name="pickupTime" value="<%=pickupTime%>" required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Pickup Time</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="time" class="form-control" id="releaseTime" name="releaseTime" value="<%=releaseTime%>" required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Release Time</label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="pickupLocation" name="pickupLocation" value="<%=pickupLocation%>" required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Pickup Location</label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" class="form-control" id="destination" name="destination" value="<%=destination%>" required <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Destination</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="distance" name="distance" value="<%=distance%>" required min="1" step="0.1" onchange="calculateFare()" <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Distance (km)</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="discount" name="discount" value="<%=discount%>" min="0" step="0.01" onchange="calculateNetTotal()" <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Discount (Rs.)</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="tax" name="tax" value="<%=tax%>" min="0" step="0.01" onchange="calculateNetTotal()" <%=isReadOnlyStatus ? "readonly" : ""%>>
                                    <label>Tax (Rs.)</label>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-floating">
                                    <select class="form-select" id="bookingStatus" name="status" <%=isReadOnlyStatus ? "disabled" : ""%>>
                                        <% for (BookingStatus status : BookingStatus.values()) {
                                            String selected = "";
                                            if (isEditMode && booking.getStatus().toString().equals(status.toString())) {
                                                selected = "selected";
                                            }
                                        %>
                                        <option value="<%=status%>" <%=selected%>><%=status.toString()%></option>
                                        <% } %>
                                    </select>
                                    <label>Booking Status</label>
                                </div>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="d-flex justify-content-end gap-2 mt-2">
                            <% if (isReadOnlyStatus) { %>
                            <a href="${pageContext.request.contextPath}/booking-servlet" class="btn btn-secondary">
                                <i class="bi bi-arrow-left me-2"></i>Back
                            </a>
                            <% } else { %>
                            <a href="${pageContext.request.contextPath}/booking-servlet" class="btn btn-secondary">
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

            <!-- Vehicle and Driver Section -->
            <div class="vehicle-and-driver-section py-2">
                <% if (!isEditMode || (isEditMode && bookingStatus.equals("pending"))) { %>
                <div class="vehicle-section pt-2 pb-2">
                    <div class="card-header">
                        <h5 class="mb-2"><i class="bi bi-car-front-fill me-2"></i>Vehicle Selection</h5>
                    </div>

                    <!-- Vehicle Filters -->
                    <form id="vehicleSearchForm" action="${pageContext.request.contextPath}/booking-servlet" method="post" onsubmit="return validateVehicleSearch()">
                        <input type="hidden" name="action" value="searchVehicles">
                        <input type="hidden" name="customerId" id="formCustomerId" value="<%=customerId%>">
                        <input type="hidden" name="bookingDate" id="formBookingDate" value="<%=bookingDate%>">
                        <input type="hidden" name="pickupTime" id="formPickupTime" value="<%=pickupTime%>">
                        <input type="hidden" name="releaseTime" id="formReleaseTime" value="<%=releaseTime%>">
                        <input type="hidden" name="pickupLocation" id="formPickupLocation" value="<%=pickupLocation%>">
                        <input type="hidden" name="destination" id="formDestination" value="<%=destination%>">
                        <input type="hidden" name="distance" id="formDistance" value="<%=distance%>">
                        <input type="hidden" name="discount" id="formDiscount" value="<%=discount%>">
                        <input type="hidden" name="tax" id="formTax" value="<%=tax%>">
                        <input type="hidden" name="fare" id="formFare" value="<%=fare%>">
                        <input type="hidden" name="vehicleId" id="formVehicleId" value="<%=vehicleId%>">
                        <div class="row g-3">
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <input type="number" class="form-control" id="capacityFilter" name="capacityFilter" min="1" placeholder="Min. capacity" value="<%=request.getParameter("capacityFilter") != null ? request.getParameter("capacityFilter") : ""%>">
                                    <label for="capacityFilter">Capacity</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <select class="form-select" id="brandFilter" name="brandFilter">
                                        <option value="">All</option>
                                    </select>
                                    <label for="brandFilter">Brand</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating">
                                    <select class="form-select" id="modelFilter" name="modelFilter">
                                        <option value="">All</option>
                                    </select>
                                    <label for="modelFilter">Model</label>
                                </div>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end gap-2 mt-3">
                            <button type="button" class="btn btn-secondary" onclick="clearVehicleSearch()">
                                <i class="bi bi-x-circle me-2"></i>Clear Filter
                            </button>
                            <button type="submit" class="btn btn-primary">
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
                            <%
                                List<VehicleDriverDTO> availableVehicles = (List<VehicleDriverDTO>) request.getAttribute("availableVehicles");
                                if (availableVehicles != null && !availableVehicles.isEmpty()) {
                                    String capacityFilter = request.getParameter("capacityFilter");
                                    String brandFilter = request.getParameter("brandFilter");
                                    String modelFilter = request.getParameter("modelFilter");
                                    for (VehicleDriverDTO vd : availableVehicles) {
                                        VehicleDTO v = vd.getVehicle();
                                        DriverDTO d = vd.getDriver();
                                        boolean capacityMatch = capacityFilter == null || capacityFilter.isEmpty() || v.getCapacity() >= Integer.parseInt(capacityFilter);
                                        boolean brandMatch = brandFilter == null || brandFilter.isEmpty() || v.getBrand().equals(brandFilter);
                                        boolean modelMatch = modelFilter == null || modelFilter.isEmpty() || v.getModel().equals(modelFilter);
                                        if (capacityMatch && brandMatch && modelMatch) {
                            %>
                            <tr data-vehicle-id="<%=v.getId()%>"
                                data-price-per-km="<%=v.getPricePerKm()%>"
                                data-brand="<%=v.getBrand()%>"
                                data-model="<%=v.getModel()%>"
                                data-license="<%=v.getLicensePlate()%>"
                                data-capacity="<%=v.getCapacity()%>"
                                data-driver-name="<%=d != null ? d.getName() : "Not Assigned"%>"
                                data-driver-phone="<%=d != null ? d.getMobileNo() : ""%>"
                                data-driver-license="<%=d != null ? d.getLicenseNo() : ""%>"
                                    <%=vehicleId.equals(v.getId()) ? "class='selected'" : ""%>>
                                <td><%=v.getBrand()%> <%=v.getModel()%></td>
                                <td><%=v.getLicensePlate()%></td>
                                <td><%=v.getCapacity()%></td>
                                <td>Rs. <%=v.getPricePerKm()%></td>
                                <td>
                                    <button class="btn btn-sm <%=vehicleId.equals(v.getId()) ? "btn-success" : "btn-primary"%> select-btn"
                                            onclick="selectVehicle('<%=v.getId()%>', <%=v.getPricePerKm()%>, this)">
                                        <i class="bi <%=vehicleId.equals(v.getId()) ? "bi-check2-all" : "bi-check"%>"></i>
                                        <%=vehicleId.equals(v.getId()) ? "Selected" : "Select"%>
                                    </button>
                                </td>
                            </tr>
                            <% }
                            }
                            } else { %>
                            <tr>
                                <td colspan="5">Fill booking details and click Search to see available vehicles.</td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <% } %>

                <!-- Vehicle Information -->
                <div id="vehicleInfo" class="vehicle-info mt-3" style="<%=isEditMode && vehicle != null ? "display: block;" : "display: none;"%>">
                    <h6 class="mb-2">Vehicle Information</h6>
                    <div class="row">
                        <div class="col-md-4">
                            <p class="mb-1"><strong>Brand:</strong> <span id="vehicleBrand"><%=isEditMode && vehicle != null ? vehicle.getBrand() : ""%></span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1"><strong>Model:</strong> <span id="vehicleModel"><%=isEditMode && vehicle != null ? vehicle.getModel() : ""%></span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1"><strong>Price Per km:</strong> <span id="vehiclePricePerKm"><%=isEditMode && vehicle != null ? vehicle.getPricePerKm() : ""%></span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1"><strong>License Plate:</strong> <span id="vehicleLicense"><%=isEditMode && vehicle != null ? vehicle.getLicensePlate() : ""%></span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1"><strong>Capacity:</strong> <span id="vehicleCapacity"><%=isEditMode && vehicle != null ? vehicle.getCapacity() : ""%></span></p>
                        </div>
                    </div>
                </div>

                <!-- Driver Information -->
                <div id="driverInfo" class="driver-info mt-3" style="<%=isEditMode && driver != null ? "display: block;" : "display: none;"%>">
                    <h6 class="mb-2">Driver Information</h6>
                    <div class="row">
                        <div class="col-md-4">
                            <p class="mb-1"><strong>Name:</strong> <span id="driverName"><%=isEditMode && driver != null ? driver.getName() : ""%></span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1"><strong>Phone:</strong> <span id="driverPhone"><%=isEditMode && driver != null ? driver.getMobileNo() : ""%></span></p>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1"><strong>License:</strong> <span id="driverLicense"><%=isEditMode && driver != null ? driver.getLicenseNo() : ""%></span></p>
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
                    <div class="summary-title">
                        <i class="bi bi-currency-exchange me-2"></i>Fare Details
                    </div>
                    <div class="summary-item">
                        <div>Price Per km:</div>
                        <div id="summaryPricePerKm"><%=isEditMode && vehicle != null ? "Rs. " + vehicle.getPricePerKm() : "Rs. 0"%></div>
                    </div>
                    <div class="summary-item">
                        <div>Distance:</div>
                        <div id="summaryDistance"><%=distance.isEmpty() ? "0 km" : distance + " km"%></div>
                    </div>
                    <div class="summary-item">
                        <div>Base Fare:</div>
                        <div id="summaryFare"><%=fare.equals("0") ? "Rs. 0.00" : "Rs. " + fare%></div>
                    </div>
                    <div class="summary-item">
                        <div>Tax:</div>
                        <div id="summaryTax"><%=tax.isEmpty() ? "Rs. 0.00" : "Rs. " + tax%></div>
                    </div>
                    <div class="summary-item">
                        <div>Discount:</div>
                        <div id="summaryDiscount"><%=discount.isEmpty() ? "Rs. 0.00" : "Rs. " + discount%></div>
                    </div>
                    <div class="summary-total d-flex justify-content-between">
                        <div><i class="bi bi-cash-coin"></i> Net Total:</div>
                        <div id="summaryNetTotal"><%=isEditMode ? "Rs. " + booking.getNetTotal() : "Rs. 0.00"%></div>
                    </div>

                    <div class="booking-details-summary mt-3">
                        <div class="summary-title">
                            <i class="bi bi-info-circle me-2"></i>Booking Details
                        </div>
                        <div class="summary-item">
                            <div>Date:</div>
                            <div id="summaryDate"><%=bookingDate.isEmpty() ? "Not selected" : bookingDate%></div>
                        </div>
                        <div class="summary-item">
                            <div>Time:</div>
                            <div id="summaryTime"><%=pickupTime.isEmpty() || releaseTime.isEmpty() ? "Not selected" : pickupTime + " - " + releaseTime%></div>
                        </div>
                        <div class="summary-item">
                            <div>From:</div>
                            <div id="summaryPickup"><%=pickupLocation.isEmpty() ? "Not specified" : pickupLocation%></div>
                        </div>
                        <div class="summary-item">
                            <div>To:</div>
                            <div id="summaryDestination"><%=destination.isEmpty() ? "Not specified" : destination%></div>
                        </div>
                    </div>

                    <div class="vehicle-details-summary mt-4" id="vehicleSummarySection" style="<%=isEditMode && vehicle != null ? "display: block;" : "display: none;"%>">
                        <div class="summary-title">
                            <i class="bi bi-car-front me-2"></i>Vehicle Details
                        </div>
                        <div class="summary-item">
                            <div>Vehicle:</div>
                            <div id="summaryVehicle"><%=isEditMode && vehicle != null ? vehicle.getBrand() + " " + vehicle.getModel() : "Not selected"%></div>
                        </div>
                        <div class="summary-item">
                            <div>Driver:</div>
                            <div id="summaryDriver"><%=isEditMode && driver != null ? driver.getName() : "Not assigned"%></div>
                        </div>
                        <div class="summary-item">
                            <div>Capacity:</div>
                            <div id="summaryCapacity"><%=isEditMode && vehicle != null ? vehicle.getCapacity() : "-"%></div>
                        </div>
                    </div>

                    <div class="mt-3">
                        <span>Status: </span>
                        <span id="summaryStatus" class="status-badge status-<%=isEditMode ? booking.getStatus().toString() : "pending"%>">
                            <%=isEditMode ? booking.getStatus().toString().substring(0, 1).toUpperCase() + booking.getStatus().toString().substring(1) : "Pending"%>
                        </span>
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
    let selectedVehicleId = <%=vehicleId.isEmpty() ? "null" : "'" + vehicleId + "'"%>;

    document.addEventListener('DOMContentLoaded', function () {
        const customerSelect = document.getElementById('customerSelect');
        if (customerSelect) {
            $(customerSelect).select2({
                placeholder: "Select customer by NIC or name",
                allowClear: true
            }).on('select2:select', function (e) {
                const selectedOption = e.params.data.element;
                updateCustomerInfo({
                    value: selectedOption.value,
                    dataset: {
                        name: selectedOption.getAttribute('data-name'),
                        nic: selectedOption.getAttribute('data-nic'),
                        phone: selectedOption.getAttribute('data-phone'),
                        email: selectedOption.getAttribute('data-email')
                    }
                });
            });
            // Trigger initial update for pre-selected customer
            const initialSelected = customerSelect.querySelector('option[selected]');
            if (initialSelected) {
                updateCustomerInfo({
                    value: initialSelected.value,
                    dataset: {
                        name: initialSelected.getAttribute('data-name'),
                        nic: initialSelected.getAttribute('data-nic'),
                        phone: initialSelected.getAttribute('data-phone'),
                        email: initialSelected.getAttribute('data-email')
                    }
                });
            }
        }

        <% if (!isEditMode && bookingDate.isEmpty()) { %>
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('bookingDate').value = today;
        document.getElementById('bookingDate').min = today;
        document.getElementById('bookingStatus').value = "pending";
        <% } %>

        document.getElementById('bookingStatus')?.addEventListener('change', updateStatusBadge);
        updateStatusBadge();

        ['pickupLocation', 'destination', 'bookingDate', 'pickupTime', 'releaseTime', 'distance', 'discount', 'tax'].forEach(id => {
            const element = document.getElementById(id);
            if (element) {
                element.addEventListener('input', updateSummaryDetails);
                if (id === 'distance' || id === 'discount' || id === 'tax') {
                    element.addEventListener('input', calculateFare);
                }
            }
        });

        // Restore selected vehicle state
        if (selectedVehicleId) {
            const selectedRow = document.querySelector(`tr[data-vehicle-id="${selectedVehicleId}"]`);
            if (selectedRow) {
                selectedRow.classList.add('selected');
                const button = selectedRow.querySelector('.select-btn');
                button.className = 'btn btn-sm btn-success select-btn';
                button.innerHTML = '<i class="bi bi-check2-all"></i> Selected';
                updateVehicleInfo(selectedRow);
            }
        }

        updateSummaryDetails();
    });

    function updateCustomerInfo(selectedOption) {
        document.getElementById('customerName').textContent = selectedOption.dataset?.name || '';
        document.getElementById('customerNIC').textContent = selectedOption.dataset?.nic || '';
        document.getElementById('customerPhone').textContent = selectedOption.dataset?.phone || '';
        document.getElementById('customerEmail').textContent = selectedOption.dataset?.email || '';
        document.getElementById('customerInfo').style.display = selectedOption.value ? 'block' : 'none';
    }

    function updateStatusBadge() {
        const status = document.getElementById('bookingStatus')?.value;
        const statusBadge = document.getElementById('summaryStatus');
        if (status && statusBadge) {
            statusBadge.className = "status-badge status-" + status;
            statusBadge.textContent = status.charAt(0).toUpperCase() + status.slice(1);
        }
    }

    function calculateFare() {
        const distance = parseFloat(document.getElementById('distance').value) || 0;
        if (distance > 0 && selectedPricePerKm > 0) {
            const fare = distance * selectedPricePerKm;
            document.getElementById('fare').value = fare.toFixed(2);
            document.getElementById('summaryFare').textContent = 'Rs. ' + fare.toFixed(2);
            document.getElementById('summaryDistance').textContent = distance + ' km';
            calculateNetTotal();
        } else {
            document.getElementById('fare').value = '0.00';
            document.getElementById('summaryFare').textContent = 'Rs. 0.00';
            document.getElementById('summaryDistance').textContent = distance + ' km';
            calculateNetTotal();
        }
    }

    function calculateNetTotal() {
        const fare = parseFloat(document.getElementById('fare').value) || 0;
        const discount = parseFloat(document.getElementById('discount').value) || 0;
        const tax = parseFloat(document.getElementById('tax').value) || 0;
        const netTotal = fare + tax - discount;
        document.getElementById('netTotal').value = netTotal.toFixed(2);
        document.getElementById('summaryNetTotal').textContent = 'Rs. ' + netTotal.toFixed(2);
        document.getElementById('summaryTax').textContent = 'Rs. ' + tax.toFixed(2);
        document.getElementById('summaryDiscount').textContent = 'Rs. ' + discount.toFixed(2);
    }

    function validateVehicleSearch() {
        const bookingDate = document.getElementById('bookingDate').value;
        const pickupTime = document.getElementById('pickupTime').value;
        const releaseTime = document.getElementById('releaseTime').value;

        if (!bookingDate || !pickupTime || !releaseTime) {
            alert('Please fill Booking Date, Pickup Time, and Release Time before searching for vehicles.');
            return false;
        }

        document.getElementById('formCustomerId').value = document.getElementById('customerSelect')?.value || '';
        document.getElementById('formBookingDate').value = bookingDate;
        document.getElementById('formPickupTime').value = pickupTime;
        document.getElementById('formReleaseTime').value = releaseTime;
        document.getElementById('formPickupLocation').value = document.getElementById('pickupLocation').value;
        document.getElementById('formDestination').value = document.getElementById('destination').value;
        document.getElementById('formDistance').value = document.getElementById('distance').value;
        document.getElementById('formDiscount').value = document.getElementById('discount').value;
        document.getElementById('formTax').value = document.getElementById('tax').value;
        document.getElementById('formFare').value = document.getElementById('fare').value;
        document.getElementById('formVehicleId').value = selectedVehicleId || '';

        return true;
    }

    function validateForm() {
        const vehicleId = document.getElementById('selectedVehicleId').value;
        const distance = parseFloat(document.getElementById('distance').value) || 0;

        if (!vehicleId) {
            alert('Please select a vehicle before submitting the booking.');
            return false;
        }

        if (distance <= 0) {
            alert('Please enter a valid distance greater than 0.');
            return false;
        }

        calculateFare();
        return true;
    }

    function clearVehicleSearch() {
        document.getElementById('capacityFilter').value = '';
        document.getElementById('brandFilter').value = '';
        document.getElementById('modelFilter').value = '';
        document.getElementById('vehicleSearchForm').submit();
    }

    function selectVehicle(vehicleId, pricePerKm, button) {
        const previouslySelected = document.querySelector('#vehicleTableBody tr.selected');
        if (previouslySelected) {
            previouslySelected.classList.remove('selected');
            const prevButton = previouslySelected.querySelector('.select-btn');
            prevButton.className = 'btn btn-sm btn-primary select-btn';
            prevButton.innerHTML = '<i class="bi bi-check"></i> Select';
        }

        selectedVehicleId = vehicleId;
        selectedPricePerKm = pricePerKm;
        document.getElementById('selectedVehicleId').value = vehicleId;

        const selectedRow = button.closest('tr');
        selectedRow.classList.add('selected');
        button.className = 'btn btn-sm btn-success select-btn';
        button.innerHTML = '<i class="bi bi-check2-all"></i> Selected';

        updateVehicleInfo(selectedRow);
        calculateFare();
    }

    function updateVehicleInfo(selectedRow) {
        document.getElementById('vehicleBrand').textContent = selectedRow.dataset.brand;
        document.getElementById('vehicleModel').textContent = selectedRow.dataset.model;
        document.getElementById('vehiclePricePerKm').textContent = selectedPricePerKm;
        document.getElementById('vehicleLicense').textContent = selectedRow.dataset.license;
        document.getElementById('vehicleCapacity').textContent = selectedRow.dataset.capacity;
        document.getElementById('vehicleInfo').style.display = 'block';

        document.getElementById('driverName').textContent = selectedRow.dataset.driverName;
        document.getElementById('driverPhone').textContent = selectedRow.dataset.driverPhone;
        document.getElementById('driverLicense').textContent = selectedRow.dataset.driverLicense;
        document.getElementById('driverInfo').style.display = selectedRow.dataset.driverName !== 'Not Assigned' ? 'block' : 'none';

        document.getElementById('summaryPricePerKm').textContent = 'Rs. ' + selectedPricePerKm;
        document.getElementById('summaryVehicle').textContent = selectedRow.dataset.brand + ' ' + selectedRow.dataset.model;
        document.getElementById('summaryDriver').textContent = selectedRow.dataset.driverName;
        document.getElementById('summaryCapacity').textContent = selectedRow.dataset.capacity;
        document.getElementById('vehicleSummarySection').style.display = 'block';
    }

    function updateSummaryDetails() {
        const bookingDate = document.getElementById('bookingDate').value;
        const pickupTime = document.getElementById('pickupTime').value;
        const releaseTime = document.getElementById('releaseTime').value;
        const pickupLocation = document.getElementById('pickupLocation').value;
        const destination = document.getElementById('destination').value;

        document.getElementById('summaryDate').textContent = bookingDate || 'Not selected';
        document.getElementById('summaryTime').textContent = pickupTime && releaseTime ? pickupTime + ' - ' + releaseTime : 'Not selected';
        document.getElementById('summaryPickup').textContent = pickupLocation || 'Not specified';
        document.getElementById('summaryDestination').textContent = destination || 'Not specified';
    }
</script>
</body>
</html>
