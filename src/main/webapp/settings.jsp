<%--
  Created by IntelliJ IDEA.
  User: ruvini
  Date: 2025-03-11
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Settings - Mega City Cab Service</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.11.1/font/bootstrap-icons.min.css" rel="stylesheet">
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

        .settings-container {
            margin: 1rem auto;
            max-width: 1400px;
            padding: 0 20px;
        }

        .settings-header {
            text-align: center;
            margin-bottom: 1rem;
        }

        .settings-title {
            font-size: 2rem;
            font-weight: 600;
            color: #212529;
            position: relative;
            display: inline-block;
            padding-bottom: 10px;
        }

        .settings-title:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 3px;
            background-color: var(--primary-color);
        }

        .settings-row {
            display: flex;
            flex-wrap: wrap;
            margin: 0 -15px;
        }

        .settings-column {
            flex: 0 0 50%;
            max-width: 50%;
            padding: 0 15px;
        }

        .settings-card {
            background-color: white;
            border-radius: 1rem;
            padding: 2rem;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
            margin-bottom: 2rem;
            height: 100%;
        }

        .password-change-section h3, .guide-section h3 {
            font-size: 1.5rem;
            font-weight: 600;
            color: #333;
            margin-bottom: 1.25rem;
            text-align: center;
            position: relative;
            padding-bottom: 0.75rem;
        }

        .password-change-section {
            margin-left: 20px;
        }

        .password-change-section h3:after, .guide-section h3:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 3px;
            background-color: var(--primary-color);
        }

        .guide-feature {
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 0.5rem;
            transition: all 0.3s ease;
            border-left: 4px solid var(--primary-color);
            background-color: rgba(252, 163, 17, 0.05);
        }

        .guide-feature:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .guide-feature-title {
            display: flex;
            align-items: center;
            font-size: 1.1rem;
            font-weight: 600;
            color: #333;
            margin-bottom: 0.5rem;
        }

        .guide-feature-title i {
            color: var(--primary-color);
            font-size: 1.3rem;
            margin-right: 0.75rem;
        }

        .guide-feature-description {
            padding-left: 2.05rem;
            font-size: 0.9rem;
            color: #666;
            line-height: 1.5;
        }

        .tips-section {
            margin-top: 1.5rem;
            padding: 1.25rem;
            background-color: #f0f8ff;
            border-radius: 0.5rem;
            border-left: 4px solid #4299e1;
        }

        .tips-title {
            font-size: 1.1rem;
            color: #2b6cb0;
            font-weight: 600;
            margin-bottom: 1rem;
            display: flex;
            align-items: center;
        }

        .tips-title i {
            margin-right: 0.75rem;
            font-size: 1.3rem;
        }

        .tips-list {
            padding-left: 1.5rem;
            margin-bottom: 0;
        }

        .tips-list li {
            margin-bottom: 0.75rem;
            position: relative;
            font-size: 0.9rem;
        }

        .tips-list li:last-child {
            margin-bottom: 0;
        }

        .tips-list li:before {
            content: '•';
            color: #4299e1;
            font-weight: bold;
            position: absolute;
            left: -1.25rem;
        }

        .form-label {
            font-weight: 500;
            color: #495057;
        }

        .form-control:focus {
            border-color: #fca311;
            box-shadow: 0 0 0 0.25rem rgba(252, 163, 17, 0.25);
        }

        .btn-primary {
            background-color: #fca311;
            border-color: #fca311;
        }

        .btn-primary:hover {
            background-color: #e59100;
            border-color: #e59100;
        }

        .alert {
            border-radius: 0.5rem;
        }

        @media (max-width: 992px) {
            .settings-column {
                flex: 0 0 100%;
                max-width: 100%;
            }

            body {
                margin-left: 0;
                padding-top: 20px;
            }

            .sidebar {
                transform: translateX(-100%);
                transition: transform 0.3s ease;
            }

            .sidebar.show {
                transform: translateX(0);
            }
        }

        .is-invalid {
            border-color: var(--danger-color);
        }

        .invalid-feedback {
            display: none;
            color: var(--danger-color);
            font-size: 0.875rem;
            margin-top: 0.25rem;
        }

        .is-invalid ~ .invalid-feedback {
            display: block;
        }
    </style>
</head>
<body>
<!-- Side Navigation Bar -->
<aside class="sidebar">
    <div class="sidebar-brand">MEGA CITY CAB</div>
    <ul class="sidebar-nav">
        <li><a href="${pageContext.request.contextPath}/dashboard"><i class="bi bi-house-door"></i>Dashboard</a></li>
        <li><a href="${pageContext.request.contextPath}/booking-servlet"><i class="bi bi-calendar-check"></i>Bookings</a></li>
        <li><a href="${pageContext.request.contextPath}/vehicle-driver-servlet"><i class="bi bi-car-front"></i>Vehicles</a></li>
        <li><a href="${pageContext.request.contextPath}/driver-servlet"><i class="bi bi-person-badge"></i>Drivers</a></li>
        <li><a href="${pageContext.request.contextPath}/customer-servlet"><i class="bi bi-people"></i>Customers</a></li>
        <div class="sidebar-divider"></div>
        <li><a href="${pageContext.request.contextPath}/views/reports.jsp"><i class="bi bi-bar-chart"></i>Reports</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/settings.jsp"><i class="bi bi-gear"></i>Settings</a></li>
    </ul>
    <div class="sidebar-footer">
        <div class="user-info" data-bs-toggle="modal" data-bs-target="#logoutModal" style="cursor: pointer;">
            <div class="user-avatar">
                <% String currentUser = (String) session.getAttribute("username");
                    if (currentUser == null) currentUser = "User"; %>
            </div>
            <div class="user-details">
                <div class="user-name">Logout</div>
                <div class="user-role">Administrator</div>
            </div>
        </div>
    </div>
</aside>

<!-- Main Content -->
<div class="settings-container">
    <!-- Alert Messages -->
    <% String success = (String) session.getAttribute("success");
        String error = (String) session.getAttribute("error");
        if (success != null) { %>
    <div id="successMessage" class="alert alert-success alert-dismissible fade show" role="alert">
        <%= success %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% session.removeAttribute("success"); %>
    <% } %>
    <% if (error != null) { %>
    <div id="errorMessage" class="alert alert-danger alert-dismissible fade show" role="alert">
        <%= error %>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% session.removeAttribute("error"); %>
    <% } %>

    <div class="settings-header">
        <h2 class="settings-title">System Settings</h2>
    </div>

    <div class="settings-row">
        <!-- Left Column - Password Change -->
        <div class="settings-column">
            <div class="settings-card password-change-section">
                <h3>Change Password</h3>
                <form id="passwordChangeForm" action="${pageContext.request.contextPath}/user-servlet?action=update" method="post">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="currentPassword" class="form-label">Current Password</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                            <button type="button" id="toggleCurrentPassword" style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); background: none; border: none; cursor: pointer;">Show</button>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="newPassword" class="form-label">New Password</label>

                        <div class="input-group">
                            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                            <button type="button" id="toggleNewPassword" style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); background: none; border: none; cursor: pointer;">Show</button>
                        </div>
                        <div class="invalid-feedback">Passwords do not match.</div>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">Confirm New Password</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                            <button type="button" id="toggleConfirmPassword" style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); background: none; border: none; cursor: pointer;">Show</button>
                        </div>
                        <div class="invalid-feedback">Passwords do not match.</div>
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="bi bi-key me-2"></i>Change Password</button>
                </form>

                <div class="tips-section mt-4">
                    <div class="tips-title"><i class="bi bi-lightbulb"></i>Pro Tips for System Usage</div>
                    <ul class="tips-list">
                        <li><strong>Keyboard Shortcuts:</strong> Press <code>Alt+D</code> for Dashboard, <code>Alt+B</code> for Bookings, and <code>Alt+R</code> for Reports.</li>
                        <li><strong>Bulk Operations:</strong> Use checkboxes for batch actions like assigning vehicles.</li>
                        <li><strong>Real-time Updates:</strong> Dashboard refreshes every 5 minutes; use refresh for instant updates.</li>
                        <li><strong>Data Export:</strong> Export tables to CSV, Excel, or PDF from the top-right corner.</li>
                        <li><strong>Data Filtering:</strong> Use advanced filters to find specific data quickly.</li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Right Column - User Guidelines -->
        <div class="settings-column">
            <div class="settings-card guide-section">
                <h3>User Guidelines</h3>
                <p class="text-center mb-4">Welcome to the Mega City Cab Service Management System.</p>
                <div class="guide-feature">
                    <div class="guide-feature-title"><i class="bi bi-house-door"></i>Dashboard</div>
                    <div class="guide-feature-description">Monitor active bookings, vehicles, and drivers in real-time.</div>
                </div>
                <div class="guide-feature">
                    <div class="guide-feature-title"><i class="bi bi-calendar-check"></i>Bookings</div>
                    <div class="guide-feature-description">Manage bookings from creation to completion.</div>
                </div>
                <div class="guide-feature">
                    <div class="guide-feature-title"><i class="bi bi-car-front"></i>Vehicles</div>
                    <div class="guide-feature-description">Add and track vehicle details and maintenance.</div>
                </div>
                <div class="guide-feature">
                    <div class="guide-feature-title"><i class="bi bi-person-badge"></i>Drivers</div>
                    <div class="guide-feature-description">Update driver profiles and assignments.</div>
                </div>
                <div class="guide-feature">
                    <div class="guide-feature-title"><i class="bi bi-people"></i>Customers</div>
                    <div class="guide-feature-description">Manage customer profiles and history.</div>
                </div>
                <div class="guide-feature">
                    <div class="guide-feature-title"><i class="bi bi-bar-chart"></i>Reports</div>
                    <div class="guide-feature-description">Generate and export business reports.</div>
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
            <div class="modal-body">Are you sure you want to logout?</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><i class="bi bi-x-circle me-2"></i>Cancel</button>
                <form action="${pageContext.request.contextPath}/auth/logout" method="post" class="d-inline">
                    <button type="submit" class="btn btn-danger"><i class="bi bi-box-arrow-right me-2"></i>Logout</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
    // Toggle password visibility
    function togglePassword(fieldId, toggleId) {
        const field = document.getElementById(fieldId);
        const toggleBtn = document.getElementById(toggleId);
        toggleBtn.addEventListener('click', () => {
            field.type = field.type === 'password' ? 'text' : 'password';
            toggleBtn.textContent = field.type === 'password' ? 'Show' : 'Hide';
        });
    }

    togglePassword('currentPassword', 'toggleCurrentPassword');
    togglePassword('newPassword', 'toggleNewPassword');
    togglePassword('confirmPassword', 'toggleConfirmPassword');

    // Form validation
    document.getElementById('passwordChangeForm').addEventListener('submit', function (event) {
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const newPasswordField = document.getElementById('newPassword');
        const confirmPasswordField = document.getElementById('confirmPassword');

        newPasswordField.classList.remove('is-invalid');
        confirmPasswordField.classList.remove('is-invalid');

        if (newPassword !== confirmPassword) {
            event.preventDefault();
            newPasswordField.classList.add('is-invalid');
            confirmPasswordField.classList.add('is-invalid');
            return false;
        }
    });

    // Alert handling
    document.addEventListener("DOMContentLoaded", function () {
        const successMessage = document.getElementById("successMessage");
        const errorMessage = document.getElementById("errorMessage");

        function hideAlert(alertElement) {
            if (alertElement) {
                setTimeout(() => {
                    alertElement.classList.remove("show");
                    setTimeout(() => alertElement.style.display = "none", 500);
                }, 5000);
            }
        }

        if (successMessage) {
            successMessage.style.display = "block";
            hideAlert(successMessage);
        }
        if (errorMessage) {
            errorMessage.style.display = "block";
            hideAlert(errorMessage);
        }

        document.querySelectorAll(".alert .btn-close").forEach(button => {
            button.addEventListener("click", function () {
                const alert = this.closest(".alert");
                alert.classList.remove("show");
                setTimeout(() => alert.style.display = "none", 500);
            });
        });
    });
</script>
</body>
</html>
