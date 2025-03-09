<%--
  Created by IntelliJ IDEA.
  User: ruvini
  Date: 2025-03-08
  Time: 23.19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.DriverDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Driver Management - Mega City Cab Service</title>
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
      padding-top: 60px;
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

    .sidebar-nav li.active {
      background-color: rgba(252, 163, 17, 0.2);
    }

    .sidebar-nav li.active a {
      color: var(--primary-color);
    }

    .sidebar-divider {
      height: 1px;
      background-color: rgba(0, 0, 0, 0.1);
      margin: 30px 0;
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


    .top-section {
      margin-top: 0.7rem;
      background-color: white;
      border-radius: 1rem;
      padding: 1.5rem;
      margin-bottom: 2rem;
      box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
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

    .table-responsive {
      border-radius: 0.5rem;
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
    <li>
      <a href="${pageContext.request.contextPath}/vehicle-driver-servlet">
        <i class="bi bi-car-front"></i>
        Vehicles
      </a>
    </li>
    <li class="active">
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
  <!-- Error Message -->
  <div id="errorMessage" class="alert alert-danger alert-dismissible fade show d-none" role="alert">
    ${requestScope.error}
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>

  <!-- Top Section with Search -->
  <div class="top-section">
    <div class="section-header">
      <h2 class="section-title">
        <i class="bi bi-person-badge-fill me-2"></i>Driver Management
      </h2>
    </div>

    <!-- Search Form -->
    <form id="searchForm" action="${pageContext.request.contextPath}/driver-servlet" method="get">
      <div class="row g-3">
        <div class="col-md-3">
          <div class="form-floating">
            <input type="text" class="form-control" id="searchLicenseNo" name="searchLicenseNo"
                   value="${param.searchLicenseNo}" placeholder="License No">
            <label for="searchLicenseNo">License No</label>
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

  <!-- Driver Table -->
  <div class="card">
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>License No</th>
            <th>Name</th>
            <th>Email</th>
            <th>Experience</th>
            <th>Mobile</th>
          </tr>
          </thead>
          <tbody>
          <%
            List<DriverDTO> drivers = (List<DriverDTO>) request.getAttribute("drivers");
            if (drivers != null && !drivers.isEmpty()) {
              for (DriverDTO driver : drivers) {
          %>
          <tr>
            <td><%= driver.getLicenseNo() %></td>
            <td><%= driver.getName() %></td>
            <td><%= driver.getEmail() %></td>
            <td><%= driver.getExperience()+" years" %></td>
            <td><%= driver.getMobileNo() %></td>
          </tr>
          <%
            }
          } else {
          %>
          <tr>
            <td colspan="5" class="text-center">No drivers found</td>
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

<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
  document.addEventListener("DOMContentLoaded", function() {
    // Show error message if it exists
    const errorMessage = document.getElementById("errorMessage");
    if (errorMessage.textContent.trim()) {
      errorMessage.classList.remove("d-none");
      setTimeout(() => errorMessage.classList.add("d-none"), 5000);
    }
  });

  function clearSearch() {
    document.getElementById('searchLicenseNo').value = '';
    document.getElementById('searchName').value = '';
    document.getElementById('searchEmail').value = '';
    document.getElementById('searchMobile').value = '';
    document.getElementById('searchForm').submit();
  }
</script>
</body>
</html>
