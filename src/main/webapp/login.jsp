<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Mega City Cab Service</title>
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

        .login-content {
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

        .login-button {
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

        .login-button:hover {
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

        .register-link {
            display: block;
            text-align: center;
            margin-top: 1.5rem;
            color: #333;
            text-decoration: none;
            font-weight: 500;
        }

        .register-link:hover {
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
    <div class="login-content">
        <h1>Welcome back</h1>
        <p class="subtitle">Log in to access your Mega City Cab account</p>

        <form action="${pageContext.request.contextPath}/auth/login" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="text" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <button type="submit" class="login-button">Login</button>
        </form>

        <c:if test="${not empty param.error}">
            <c:choose>
                <c:when test="${param.error == 'invalid_input'}">
                    <p class="error-message">Please provide both email and password.</p>
                </c:when>
                <c:when test="${param.error == 'user_not_found'}">
                    <p class="error-message">User not found. Please check your email or register.</p>
                </c:when>
                <c:when test="${param.error == 'invalid_credentials'}">
                    <p class="error-message">Invalid email or password. Please try again.</p>
                </c:when>
                <c:when test="${param.error == 'system_error'}">
                    <p class="error-message">An unexpected error occurred. Please try again later.</p>
                </c:when>
                <c:otherwise>
                    <p class="error-message">Login failed. Please try again.</p>
                </c:otherwise>
            </c:choose>
        </c:if>

        <a href="${pageContext.request.contextPath}/auth/register" class="register-link">
            Don't have an account? Sign up now
        </a>
    </div>
</div>

<footer>&copy; <%= new java.util.Date().getYear() + 1900 %> MegaCity Cab Service. All rights reserved.</footer>
</body>
</html>
