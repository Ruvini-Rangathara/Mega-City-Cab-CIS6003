<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab Service</title>
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
        }

        .nav-links {
            display: flex;
            gap: 2rem;
            align-items: center;
        }

        .nav-links a {
            text-decoration: none;
            color: #333;
            font-weight: 500;
        }

        .sign-up {
            background-color: #fca311;
            color: white !important;
            padding: 0.5rem 1.5rem;
            border-radius: 25px;
            transition: background-color 0.3s;
        }

        .sign-up:hover {
            background-color: #e59100;
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

        .hero-content {
            flex: 1;
        }

        .hero-image {
            flex: 1;
            text-align: right;
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

        .app-buttons {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
        }

        .app-button {
            background: #1a1a1a;
            color: white;
            padding: 0.8rem 1.5rem;
            border-radius: 8px;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-size: 0.9rem;
        }

        footer {
            font-size: 0.9rem;
            color: #666;
            text-align: center;
            bottom: 0;
        }
    </style>
</head>
<body>
<nav class="navbar">
    <div class="logo">MEGA CITY CAB</div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/auth/login">Login</a>
        <a href="${pageContext.request.contextPath}/auth/register" class="sign-up">Sign Up</a>
    </div>
</nav>

<div class="hero">
    <div class="hero-content">
        <h1>Find your ride at the best price</h1>
        <p class="subtitle">With Mega City Cab, enjoy convenient rides with professional drivers and reliable vehicles.
            Experience comfort and safety at competitive rates.</p>
        <div class="app-buttons">
            <a href="#" class="app-button">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="white">
                    <path d="M17.05 20.28c-.98.95-2.05.8-3.08.35-1.08-.46-2.07-.48-3.2 0-1.44.62-2.2.44-3.1-.35C4.4 17.16 3.85 12.8 5.85 10.4c1.45-1.7 3.26-2.16 4.55-2.16.85.02 1.65.32 2.35.57.55.2 1.03.37 1.45.37.38 0 .85-.16 1.4-.35.8-.28 1.7-.6 2.65-.5 1.2.1 2.3.55 3.1 1.35-1.8 1.15-2.75 3-.25 5.15-.75 1.45-1.65 2.8-2.95 4.45zm-4-15.8c.15-1.75 1.6-3.15 3.15-3.45.35 1.8-.5 3.45-2 4.4-1.35.8-2.65.7-3.15.65.2-1.2.85-2.7 2-3.6z"></path>
                </svg>
                App Store
            </a>
            <a href="#" class="app-button">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="white">
                    <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73-3.522-3.356c-.33-.314-.16-.888.282-.95l4.898-.696L7.702 1.1c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
                </svg>
                Google Play
            </a>
        </div>
    </div>
    <div class="hero-image">
        <img src="${pageContext.request.contextPath}/assets/images/welcome.png" alt="Taxi service illustration"
             style="max-width: 100%; height: auto;"/>
    </div>
</div>
<footer>&copy; <%= new java.util.Date().getYear() + 1900 %> MegaCity Cab Service. All rights reserved.</footer>

</body>
</html>
