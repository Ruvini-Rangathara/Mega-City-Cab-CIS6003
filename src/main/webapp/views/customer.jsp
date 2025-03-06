<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.CustomerDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Management - Mega City Cab Service</title>
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

        /* Customer Management Styles */
        .top-section {
            margin-top: 0.7rem;
            background-color: white;
            border-radius: 1rem;
            padding: 1.5rem;
            margin-bottom: 2rem;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
        }

        #successMessage, #errorMessage {
            margin-top: 2rem;
        }

        .card {
            border: none;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            border-radius: 1rem;
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

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }

        .btn-primary:hover {
            background-color: #e59100;
            border-color: #e59100;
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

        .btn-edit {
            background-color: #ffeb99;
            border-color: #ffeb99;
            color: #000;
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
        }

        .btn-view:hover {
            background-color: #99ccff;
            border-color: #99ccff;
            color: #000;
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
            <a href="${pageContext.request.contextPath}/drivers">
                <i class="bi bi-person-badge"></i>
                Drivers
            </a>
        </li>
        <li class="active">
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
    <div id="successMessage" class="alert alert-success alert-dismissible fade show d-none" role="alert">
        ${param.success}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <div id="errorMessage" class="alert alert-danger alert-dismissible fade show d-none" role="alert">
        ${param.error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>

    <!-- Top Section with New Button and Search -->
    <div class="top-section">
        <div class="section-header">
            <h2 class="section-title">
                <i class="bi bi-people-fill me-2"></i>Customer Management
            </h2>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#customerModal">
                <i class="bi bi-plus-circle me-2"></i>New Customer
            </button>
        </div>

        <!-- Search Form -->
        <form id="searchForm" action="${pageContext.request.contextPath}/customer-servlet" method="get">
            <div class="row g-3">
                <div class="col-md-3">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchRegNo" name="searchRegNo"
                               value="${param.searchRegNo}" placeholder="Registration No">
                        <label for="searchRegNo">Registration No</label>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchName" name="searchName"
                               value="${param.searchName}" placeholder="Name">
                        <label for="searchName">Name</label>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchEmail" name="searchEmail"
                               value="${param.searchEmail}" placeholder="Email">
                        <label for="searchEmail">Email</label>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="searchMobile" name="searchMobile"
                               value="${param.searchMobile}" placeholder="Mobile">
                        <label for="searchMobile">Mobile</label>
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

    <!-- Customer Table -->
    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Reg. No</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Email</th>
                        <th>NIC</th>
                        <th>Mobile</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<CustomerDTO> customers = (List<CustomerDTO>) request.getAttribute("customers");
                        if (customers != null && !customers.isEmpty()) {
                            for (CustomerDTO customer : customers) {
                    %>
                    <tr>
                        <td><%= customer.getRegistrationNo() %></td>
                        <td><%= customer.getName() %></td>
                        <td><%= customer.getAddress() %></td>
                        <td><%= customer.getEmail() %></td>
                        <td><%= customer.getNic() %></td>
                        <td><%= customer.getMobileNo() %></td>
                        <td>
                            <div class="d-flex gap-2">
                                <button class="btn btn-view btn-sm" onclick="viewCustomer(
                                        '<%= customer.getId() %>',
                                        '<%= customer.getRegistrationNo() %>',
                                        '<%= customer.getName() %>',
                                        '<%= customer.getEmail() %>',
                                        '<%= customer.getAddress() %>',
                                        '<%= customer.getNic() %>',
                                        '<%= customer.getMobileNo() %>',
                                        '<%= customer.getDob() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(customer.getDob()) : "" %>'
                                        )">
                                    <i class="bi bi-eye-fill"></i>
                                </button>
                                <button class="btn btn-edit btn-sm" onclick="editCustomer(
                                        '<%= customer.getId() %>',
                                        '<%= customer.getRegistrationNo() %>',
                                        '<%= customer.getName() %>',
                                        '<%= customer.getEmail() %>',
                                        '<%= customer.getAddress() %>',
                                        '<%= customer.getNic() %>',
                                        '<%= customer.getMobileNo() %>',
                                        '<%= customer.getDob() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(customer.getDob()) : "" %>'
                                        )">
                                    <i class="bi bi-pencil-fill"></i>
                                </button>
                                <form action="${pageContext.request.contextPath}/customer-servlet" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= customer.getId() %>">
                                    <button type="submit" class="btn btn-delete btn-sm"
                                            onclick="return confirm('Are you sure you want to delete this customer?')">
                                        <i class="bi bi-trash-fill"></i>
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-center">No customers found</td>
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

<!-- Customer Modal -->
<div class="modal fade" id="customerModal" tabindex="-1" aria-labelledby="customerModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="customerModalLabel">Manage Customer</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="customerForm" action="${pageContext.request.contextPath}/customer-servlet" method="post">
                    <input type="hidden" id="id" name="id">
                    <input type="hidden" id="action" name="action" value="add">

                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="registrationNo" name="registrationNo"
                                       placeholder="Registration No" required>
                                <label for="registrationNo">Registration No</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="Name" required>
                                <label for="name">Name</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="Email" required pattern="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$">
                                <label for="email">Email</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="address" name="address"
                                       placeholder="Address" required>
                                <label for="address">Address</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="nic" name="nic"
                                       placeholder="NIC" required pattern="^(?:\d{9}[VvXx]|\d{12})$">
                                <label for="nic">NIC</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="mobileNo" name="mobileNo"
                                       placeholder="Mobile Number" required pattern="^(?:\+94|0)7\d{8}$">
                                <label for="mobileNo">Mobile Number</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="date" class="form-control" id="dob" name="dob" required>
                                <label for="dob">Date of Birth</label>
                            </div>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end gap-2 mt-4">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="cancelButton">
                            <i class="bi bi-x-circle me-2"></i>Cancel
                        </button>
                        <button type="submit" class="btn btn-primary" id="submitButton">
                            <i class="bi bi-person-plus-fill me-2"></i>Add Customer
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

        // Form validation
        const customerForm = document.getElementById("customerForm");
        customerForm.addEventListener("submit", function(e) {
            const email = document.getElementById("email").value;
            const nic = document.getElementById("nic").value;
            const mobile = document.getElementById("mobileNo").value;

            const emailPattern = /^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$/;
            const nicPattern = /^(?:\d{9}[VvXx]|\d{12})$/;
            const mobilePattern = /^(?:\+94|0)7\d{8}$/;

            if (!emailPattern.test(email)) {
                e.preventDefault();
                alert("Please enter a valid email address");
                return;
            }

            if (!nicPattern.test(nic)) {
                e.preventDefault();
                alert("Please enter a valid NIC number");
                return;
            }

            if (!mobilePattern.test(mobile)) {
                e.preventDefault();
                alert("Please enter a valid mobile number");
                return;
            }
        });

        // Reset form when modal is closed
        const customerModal = document.getElementById('customerModal');
        customerModal.addEventListener('hidden.bs.modal', function () {
            customerForm.reset();
            document.getElementById("action").value = "add";
            document.getElementById("submitButton").innerHTML = '<i class="bi bi-person-plus-fill me-2"></i>Add Customer';
            document.getElementById("submitButton").style.display = "block";
            document.getElementById("registrationNo").readOnly = false;
            enableFormInputs(false);
        });
    });

    function clearSearch() {
        document.getElementById('searchRegNo').value = '';
        document.getElementById('searchName').value = '';
        document.getElementById('searchEmail').value = '';
        document.getElementById('searchMobile').value = '';
        document.getElementById('searchForm').submit();
    }

    function editCustomer(id, regNo, name, email, address, nic, mobile, dob) {
        document.getElementById("id").value = id;
        document.getElementById("registrationNo").value = regNo;
        document.getElementById("name").value = name;
        document.getElementById("email").value = email;
        document.getElementById("address").value = address;
        document.getElementById("nic").value = nic;
        document.getElementById("mobileNo").value = mobile;
        document.getElementById("dob").value = dob;

        document.getElementById("action").value = "update";
        document.getElementById("submitButton").innerHTML = '<i class="bi bi-check-lg me-2"></i>Update Customer';
        document.getElementById("submitButton").style.display = "block";
        document.getElementById("customerModalLabel").innerText = "Edit Customer";
        document.getElementById("registrationNo").readOnly = true;
        enableFormInputs(false);

        new bootstrap.Modal(document.getElementById('customerModal')).show();
    }

    function viewCustomer(id, regNo, name, email, address, nic, mobile, dob) {
        document.getElementById("id").value = id;
        document.getElementById("registrationNo").value = regNo;
        document.getElementById("name").value = name;
        document.getElementById("email").value = email;
        document.getElementById("address").value = address;
        document.getElementById("nic").value = nic;
        document.getElementById("mobileNo").value = mobile;
        document.getElementById("dob").value = dob;

        document.getElementById("action").value = "view"; // No form submission for view
        document.getElementById("submitButton").style.display = "none";
        document.getElementById("customerModalLabel").innerText = "View Customer Details";
        document.getElementById("registrationNo").readOnly = true;
        enableFormInputs(true);

        new bootstrap.Modal(document.getElementById('customerModal')).show();
    }

    function enableFormInputs(disable) {
        const inputs = document.querySelectorAll('#customerForm input');
        inputs.forEach(input => {
            input.disabled = disable;
        });
    }
</script>
</body>
</html>
