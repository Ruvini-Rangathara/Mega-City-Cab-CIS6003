<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.BookingDTO" %>
<%@ page import="com.project.megacitycab.constant.BookingStatus" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - Mega City Cab Service</title>
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

    /* Dashboard Styles */
    .dashboard-container {
      padding: 1.5rem;
    }

    .dashboard-card {
      background-color: white;
      border-radius: 1rem;
      padding: 1.5rem;
      box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.08);
      margin-bottom: 1.5rem;
    }

    .account-balance {
      font-size: 1.5rem;
      font-weight: 600;
      color: #212529;
    }

    .account-actions a {
      margin-right: 1rem;
      text-decoration: none;
      color: var(--primary-color);
    }

    .account-actions a:hover {
      color: #e59100;
    }

    .linked-accounts .card {
      min-height: 120px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1.1rem;
      font-weight: 500;
      color: #212529;
    }

    .transactions-table th, .transactions-table td {
      vertical-align: middle;
    }

    .expenses-chart {
      text-align: center;
    }

    .expenses-chart .progress {
      height: 200px;
      width: 200px;
      margin: 0 auto;
      position: relative;
    }

    .expenses-chart .progress-value {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      font-size: 1.25rem;
      font-weight: 600;
    }

    .standing-orders {
      background-color: #28a745;
      color: white;
      border-radius: 1rem;
      padding: 1rem;
      text-align: center;
    }

    .standing-orders a {
      color: white;
      text-decoration: underline;
    }

    .standing-orders a:hover {
      color: #e6ffe6;
    }
  </style>
</head>
<body>
<!-- Side Navigation Bar -->
<aside class="sidebar">
  <div class="sidebar-brand">MEGA CITY CAB</div>
  <ul class="sidebar-nav">
    <li class="active">
      <a href="${pageContext.request.contextPath}/dashboard">
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
<div class="container dashboard-container">
  <!-- Alert Messages -->
  <% String success = request.getParameter("success"); %>
  <% String error = request.getParameter("error"); %>
  <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
  <% if (success != null && !success.isEmpty()) { %>
  <div id="successMessage" class="alert alert-success alert-dismissible fade show" role="alert">
    <%= success %>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
  <% } %>
  <% if (error != null && !error.isEmpty() || (errorMessage != null && !errorMessage.isEmpty())) { %>
  <div id="errorMessage" class="alert alert-danger alert-dismissible fade show" role="alert">
    <%= error != null ? error : errorMessage %>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
  <% } %>

  <!-- Dashboard Content -->
  <div class="dashboard-card">
    <div class="row">
      <div class="col-md-6">
        <h2 class="section-title">Main Account</h2>
        <div class="mb-3">
          <strong>Mega City Cab Savings Account</strong><br>
          <small>BB: 1234 5678 9012 3456</small>
        </div>
        <div class="account-balance">
          LKR <%= request.getAttribute("totalRevenue") != null ? String.format("%.2f", (Double) request.getAttribute("totalRevenue")) : "0.00" %>
        </div>
        <div class="account-actions mt-3">
          <a href="#">Transfer Money</a>
          <a href="#">Link Accounts</a>
        </div>

        <form action="${pageContext.request.contextPath}/dashboard" method="post" class="mt-3">
          <input type="hidden" name="action" value="sendEmail">
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-envelope"></i> Send Email
          </button>
        </form>

      </div>
      <div class="col-md-6">
        <div class="standing-orders">
          <h4>Define Standing Orders</h4>
          <p>We help you define recurring payments and bill dates. Care of your regular transfers.</p>
          <a href="${pageContext.request.contextPath}/settings">Define standing order</a>
        </div>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-3">
      <div class="card linked-accounts">
        <div class="card-body">
          <i class="bi bi-car-front"></i> Vehicle Earnings<br>
          LKR <%= request.getAttribute("totalVehicleEarnings") != null ? String.format("%.2f", (Double) request.getAttribute("totalVehicleEarnings")) : "0.00" %>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card linked-accounts">
        <div class="card-body">
          <i class="bi bi-person-badge"></i> Driver Earnings<br>
          LKR <%= request.getAttribute("totalDriverEarnings") != null ? String.format("%.2f", (Double) request.getAttribute("totalDriverEarnings")) : "0.00" %>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card linked-accounts">
        <div class="card-body">
          <i class="bi bi-people"></i> Customers<br>
          <%= request.getAttribute("totalCustomers") != null ? request.getAttribute("totalCustomers") : "0" %>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card linked-accounts">
        <div class="card-body">
          <i class="bi bi-clock"></i> Bookings<br>
          <%= request.getAttribute("totalBookings") != null ? request.getAttribute("totalBookings") : "0" %>
        </div>
      </div>
    </div>
  </div>

  <div class="row mt-2">
    <div class="col-md-6">
      <div class="dashboard-card">
        <h2 class="section-title">Latest Transactions</h2>
        <table class="table transactions-table">
          <thead>
          <tr>
            <th>Time</th>
            <th>Description</th>
            <th>Category</th>
            <th>Amount</th>
          </tr>
          </thead>
          <tbody>
          <%
            List<BookingDTO> recentBookings = (List<BookingDTO>) request.getAttribute("recentBookings");
            if (recentBookings != null && !recentBookings.isEmpty()) {
              for (BookingDTO booking : recentBookings) {
                // Fetch additional details from BookingDTO
                String pickupLocation = booking.getPickupLocation();
                String destination = booking.getDestination();
                BookingStatus status = booking.getStatus();
                double netTotal = booking.getNetTotal();
                LocalDate bookingDate = booking.getBookingDate();
                LocalTime pickupTime = booking.getPickupTime();
          %>
          <tr>
            <td>
              <strong><%= bookingDate %></strong><br>
              <small><%= pickupTime %></small>
            </td>
            <td>
              <strong>From: <%= pickupLocation %></strong><br>
              <small>To: <%= destination %></small>
            </td>
            <td>
<%--              <span class="badge bg-primary"><%= vehicleId %></span><br>--%>
              <span class="badge <%= status.equals(BookingStatus.completed) ? "bg-success" :
                            status.equals(BookingStatus.confirmed) ? "bg-info" :
                            status.equals(BookingStatus.pending) ? "bg-warning" :
                            status.equals(BookingStatus.cancelled) ? "bg-danger" : "bg-secondary" %>">
        <%= status %>
      </span>
            </td>
            <td>
              <strong>- LKR <%= String.format("%.2f", netTotal) %></strong><br>
              <small>Distance: <%= String.format("%.2f km", booking.getDistance()) %></small><br>
              <small>Fare: LKR <%= String.format("%.2f", booking.getFare()) %></small>
            </td>
          </tr>
          <%   }
          } else { %>
          <tr>
            <td colspan="4" class="text-center">No recent transactions found.</td>
          </tr>
          <% } %>
          </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/booking-servlet" class="btn btn-primary mt-3">See More</a>
      </div>
    </div>

    <div class="col-md-6">
      <div class="dashboard-card">
        <h2 class="section-title">All Expenses</h2>
        <div class="expenses-chart">
          <div class="progress">
            <div class="progress-value">
              LKR <%= request.getAttribute("totalExpenses") != null ? String.format("%.2f", (Double) request.getAttribute("totalExpenses")) : "0.00" %>
            </div>
          </div>
          <% Double totalExpenses = request.getAttribute("totalExpenses") != null ? (Double) request.getAttribute("totalExpenses") : 0.0; %>
          <small>Daily: LKR <%= String.format("%.2f", totalExpenses / 30) %> |
            Weekly: LKR <%= String.format("%.2f", totalExpenses / 4) %> |
            Monthly: LKR <%= String.format("%.2f", totalExpenses) %></small>
          <div class="mt-2">
            <span class="badge bg-success">Fuel</span>
            <span class="badge bg-warning">Maintenance</span>
            <span class="badge bg-info">Salaries</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Scripts -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.2/js/bootstrap.bundle.min.js"></script>
<script>
  document.addEventListener("DOMContentLoaded", function() {
    const successMessage = document.getElementById("successMessage");
    const errorMessage = document.getElementById("errorMessage");

    if (successMessage) {
      setTimeout(() => {
        const bsAlert = new bootstrap.Alert(successMessage);
        bsAlert.close();
      }, 5000);
    }

    if (errorMessage) {
      setTimeout(() => {
        const bsAlert = new bootstrap.Alert(errorMessage);
        bsAlert.close();
      }, 5000);
    }
  });

</script>
</body>
</html>


