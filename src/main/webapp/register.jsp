<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Mega City Cab Service</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
        }

        body {
            background-color: #ffffff;
            height: 100vh;
        }

        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1.5rem 4rem;
        }

        .logo {
            font-size: 1.5rem;
            font-weight: bold;
            color: #fca311;
            text-decoration: none;
        }

        .nav-links a {
            text-decoration: none;
            color: #333;
            font-weight: 500;
        }

        .hero {
            display: flex;
            padding: 4rem;
            gap: 4rem;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
            height: 80vh;
        }

        .register-content {
            flex: 1;
            max-width: 420px;
            margin: 0 auto;
        }

        h1 {
            font-size: 3.5rem;
            margin-bottom: 1.5rem;
            line-height: 1.2;
            color: #1a1a1a;
        }

        .subtitle {
            color: #666;
            font-size: 1.1rem;
            line-height: 1.6;
            margin-bottom: 2rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #333;
            font-weight: 500;
        }

        .form-group input {
            width: 100%;
            padding: 0.8rem 1.5rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
        }

        .form-group input:focus {
            outline: none;
            border-color: #fca311;
        }

        .register-button {
            width: 100%;
            background-color: #fca311;
            color: white;
            padding: 0.8rem 1.5rem;
            border: none;
            border-radius: 25px;
            font-size: 1rem;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .register-button:hover {
            background-color: #e59100;
        }

        .error-message {
            color: #ff3333;
            font-size: 0.9rem;
            margin-top: 1rem;
            padding: 0.8rem;
            background-color: #fff5f5;
            border-radius: 8px;
        }

        .success-message {
            color: #22c55e;
            font-size: 0.9rem;
            margin-top: 1rem;
            padding: 0.8rem;
            background-color: #f0fdf4;
            border-radius: 8px;
        }

        .login-link {
            display: block;
            text-align: center;
            margin-top: 1.5rem;
            color: #333;
            text-decoration: none;
            font-weight: 500;
        }

        .login-link:hover {
            color: #fca311;
        }

        footer {
            font-size: 0.9rem;
            color: #666;
            text-align: center;
            padding: 1.5rem;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/" class="logo">MEGA CITY CAB</a>
</nav>

<div class="hero">
    <div class="register-content">
        <h1>Join us today</h1>
        <p class="subtitle">Create your Mega City Cab account and start riding</p>

        <form action="${pageContext.request.contextPath}/auth/register" method="post">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <div style="position: relative;">
                    <input type="password" id="password" name="password" required>
                    <button type="button" id="togglePassword" style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); background: none; border: none; cursor: pointer;">
                        Show
                    </button>
                </div>
            </div>

            <button type="submit" class="register-button">Create Account</button>
        </form>

        <c:if test="${not empty param.error}">
            <p class="error-message">
                Registration failed. Please try again.
                <span>${param.error}</span>
            </p>
        </c:if>

        <c:if test="${not empty param.success}">
            <p class="success-message">Registration successful! Please log in.</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/auth/login" class="login-link">
            Already have an account? Log in
        </a>
    </div>
</div>

<footer>&copy; <%= new java.util.Date().getYear() + 1900 %> MegaCity Cab Service. All rights reserved.</footer>

<script>
    document.getElementById('togglePassword').addEventListener('click', function() {
        const passwordInput = document.getElementById('password');
        const toggleBtn = document.getElementById('togglePassword');

        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            toggleBtn.textContent = 'Hide';
        } else {
            passwordInput.type = 'password';
            toggleBtn.textContent = 'Show';
        }
    });
</script>
</body>
</html>
