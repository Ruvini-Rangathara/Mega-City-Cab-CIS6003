<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.megacitycab.dto.CustomerDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Customer Management - Mega City Cab Service</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
    }

    body {
      background-color: #ffffff;
      min-height: 100vh;
    }

    .navbar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 1.5rem 4rem;
      background-color: #fff;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .logo {
      font-size: 1.5rem;
      font-weight: bold;
      color: #fca311;
      text-decoration: none;
    }

    .container {
      max-width: 1200px;
      margin: 2rem auto;
      padding: 0 2rem;
    }

    .card {
      background: #fff;
      border-radius: 10px;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
      margin-bottom: 2rem;
      padding: 2rem;
    }

    .section-title {
      font-size: 1.8rem;
      color: #1a1a1a;
      margin-bottom: 1.5rem;
    }

    .form-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 1.5rem;
      margin-bottom: 1.5rem;
    }

    .form-group {
      margin-bottom: 1rem;
    }

    .form-group label {
      display: block;
      margin-bottom: 0.5rem;
      color: #333;
      font-weight: 500;
    }

    .form-group input {
      width: 100%;
      padding: 0.8rem 1rem;
      border: 1px solid #ddd;
      border-radius: 8px;
      font-size: 1rem;
    }

    .form-group input:focus {
      outline: none;
      border-color: #fca311;
    }

    .button-group {
      display: flex;
      justify-content: flex-end;
      gap: 1rem;
      margin-top: 1rem;
    }

    .btn {
      padding: 0.8rem 1.5rem;
      border: none;
      border-radius: 25px;
      font-size: 1rem;
      font-weight: 500;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    .btn-primary {
      background-color: #fca311;
      color: white;
    }

    .btn-primary:hover {
      background-color: #e59100;
    }

    .btn-secondary {
      background-color: #6c757d;
      color: white;
    }

    .btn-secondary:hover {
      background-color: #5a6268;
    }

    .customer-table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 2rem;
    }

    .customer-table th,
    .customer-table td {
      padding: 1rem;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }

    .customer-table th {
      background-color: #f8f9fa;
      font-weight: 600;
    }

    .customer-table tr:hover {
      background-color: #f8f9fa;
    }

    .action-buttons {
      display: flex;
      gap: 0.5rem;
    }

    .btn-edit, .btn-delete {
      padding: 0.5rem 1rem;
      border-radius: 20px;
      font-size: 0.9rem;
    }

    .btn-edit {
      background-color: #fca311;
      color: white;
    }

    .btn-delete {
      background-color: #dc3545;
      color: white;
    }

    .message {
      display: none;
      padding: 1rem;
      border-radius: 8px;
      margin-bottom: 1rem;
    }

    .success {
      background-color: #d4edda;
      color: #155724;
    }

    .error {
      background-color: #f8d7da;
      color: #721c24;
    }
  </style>
</head>

<body>

<nav class="navbar">
  <a href="${pageContext.request.contextPath}/" class="logo">MEGA CITY CAB</a>
</nav>

<div class="container">
  <div id="successMessage" class="message success">${param.success}</div>
  <div id="errorMessage" class="message error">${param.error}</div>

  <div class="card">
    <h2 class="section-title">Manage Customers</h2>
    <form id="customerForm" action="${pageContext.request.contextPath}/customer-servlet" method="post">
      <input type="hidden" id="id" name="id">
      <input type="hidden" id="action" name="action" value="add">

      <div class="form-grid">
        <div class="form-group">
          <label for="registrationNo">Registration No</label>
          <input type="text" id="registrationNo" name="registrationNo" required>
        </div>

        <div class="form-group">
          <label for="name">Name</label>
          <input type="text" id="name" name="name" required>
        </div>

        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" required
                 pattern="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$">
        </div>

        <div class="form-group">
          <label for="address">Address</label>
          <input type="text" id="address" name="address" required>
        </div>

        <div class="form-group">
          <label for="nic">NIC</label>
          <input type="text" id="nic" name="nic" required
                 pattern="^(?:\d{9}[VvXx]|\d{12})$">
        </div>

        <div class="form-group">
          <label for="mobileNo">Mobile Number</label>
          <input type="text" id="mobileNo" name="mobileNo" required
                 pattern="^(?:\+94|0)7\d{8}$">
        </div>

        <div class="form-group">
          <label for="dob">Date of Birth</label>
          <input type="date" id="dob" name="dob" required>
        </div>
      </div>

      <div class="button-group">
        <button type="reset" class="btn btn-secondary">Reset</button>
        <button type="submit" class="btn btn-primary" id="submitButton">Add Customer</button>
      </div>
    </form>
  </div>

  <div class="card">
    <h2 class="section-title">Customer List</h2>
    <table class="customer-table">
      <thead>
      <tr>
        <th>Reg. No</th>
        <th>Name</th>
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
        <td><%= customer.getEmail() %></td>
        <td><%= customer.getNic() %></td>
        <td><%= customer.getMobileNo() %></td>
        <td class="action-buttons">
          <button class="btn btn-edit" onclick="editCustomer('<%= customer.getId() %>',
                  '<%= customer.getRegistrationNo() %>',
                  '<%= customer.getName() %>',
                  '<%= customer.getEmail() %>',
                  '<%= customer.getAddress() %>',
                  '<%= customer.getNic() %>',
                  '<%= customer.getMobileNo() %>',
                  '<%= customer.getDob() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(customer.getDob()) : "" %>')">Edit</button>
          <form action="${pageContext.request.contextPath}/customer-servlet" method="post" style="display: inline">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" value="<%= customer.getId() %>">
            <button type="submit" class="btn btn-delete"
                    onclick="return confirm('Are you sure you want to delete this customer?')">Delete</button>
          </form>
        </td>
      </tr>
      <%
        }
      } else {
      %>
      <tr>
        <td colspan="6" style="text-align: center;">No customers found</td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  </div>
</div>

<script>
  document.addEventListener("DOMContentLoaded", function() {
    const successMessage = document.getElementById("successMessage");
    const errorMessage = document.getElementById("errorMessage");
    const customerForm = document.getElementById("customerForm");

    // Show messages if they exist
    if (successMessage.textContent.trim()) successMessage.style.display = "block";
    if (errorMessage.textContent.trim()) errorMessage.style.display = "block";

    // Hide messages after 5 seconds
    setTimeout(() => {
      successMessage.style.display = "none";
      errorMessage.style.display = "none";
    }, 5000);

    // Form validation
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
  });

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
    document.getElementById("submitButton").textContent = "Update Customer";
    document.getElementById("registrationNo").readOnly = true;
  }
</script>
</body>

</html>
