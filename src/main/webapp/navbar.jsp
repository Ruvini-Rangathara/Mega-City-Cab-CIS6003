<%@ page contentType="text/html;charset=UTF-8" %>
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
      padding-top: 60px;
      margin-left: 250px;
    }

    /* Side Navigation Bar */
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
      padding: 1rem;
      font-size: 1.5rem;
      font-weight: bold;
      color: var(--primary-color);
      border-bottom: 2px solid rgba(252, 163, 17, 0.3);
    }

    .sidebar-nav {
      list-style: none;
      padding: 0;
      margin: 0;
    }

    .sidebar-nav li {
      padding: 0.75rem 1rem;
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
      border-top: 1px solid rgba(0, 0, 0, 0.1); /* Divider color */
      background-color: white; /* Footer background */
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
      color: white; /* Text color for avatar */
    }

    .user-details {
      margin-left: 10px;
    }

    .user-name {
      font-size: 14px;
      font-weight: 500;
      color: var(--secondary-color); /* User name color */
    }

    .user-role {
      font-size: 12px;
      color: var(--secondary-color); /* User role color */
    }

    .sidebar-divider {
      height: 1px;
      background-color: rgba(0, 0, 0, 0.1); /* Divider color */
      margin: 15px 0;
    }
  </style>
</head>
<body>
<!-- Side Navigation Bar -->
<aside class="sidebar">
  <div class="sidebar-brand">MEGA CITY CAB</div>
  <ul class="sidebar-nav">
    <li class="active">
      <a href="${pageContext.request.contextPath}/">
        <i class="bi bi-house-door"></i>
        Dashboard
      </a>
    </li>
    <li>
      <a href="${pageContext.request.contextPath}/bookings">
        <i class="bi bi-calendar-check"></i>
        Bookings
      </a>
    </li>
    <li>
      <a href="${pageContext.request.contextPath}/vehicles">
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
    <li>
      <a href="${pageContext.request.contextPath}/customers">
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

<!-- Rest of your existing content -->
<div class="container">
  <div class="row">
    <!-- Booking Form -->
    <div class="col-lg-8">
      <!-- Existing booking form content -->
    </div>

    <!-- Summary Section -->
    <div class="col-lg-4">
      <!-- Existing summary content -->
    </div>
  </div>
</div>

<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0-rc.0/js/select2.min.js"></script>
</body>
</html>
