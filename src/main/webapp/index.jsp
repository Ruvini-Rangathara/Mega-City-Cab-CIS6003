<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Mega City Cab</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            background: linear-gradient(to right, #141e30, #243b55);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            padding: 20px;
        }

        .container {
            width: 90%;
            max-width: 500px;
            background: rgba(255, 255, 255, 0.1);
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            border-radius: 15px;
            text-align: center;
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        h1 {
            color: #fff;
            font-size: 26px;
            margin-bottom: 10px;
        }

        p {
            font-size: 16px;
            color: #ddd;
            margin-bottom: 20px;
        }

        .btn-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .btn {
            width: 100%;
            padding: 10px;
            background: #00c6ff;
            color: white;
            text-decoration: none;
            border-radius: 30px;
            text-align: center;
            font-weight: 600;
            transition: 0.3s;
            border: none;
            font-size: 16px;
            cursor: pointer;
        }

        .btn:hover {
            background: #0072ff;
            transform: scale(1.05);
        }

        /* Responsive styles */
        @media (min-width: 600px) {
            .btn-container {
                flex-direction: row;
                flex-wrap: wrap;
                justify-content: center;
            }

            .btn {
                flex: 1;
                margin: 5px;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <h1>🚖 Mega City Cab</h1>
    <p>Ride smarter, safer, and faster with us.</p>

    <div class="btn-container">
        <a href="register.jsp" class="btn">Sign Up</a>
        <a href="login.jsp" class="btn">Log In</a>
        <a href="bookRide.jsp" class="btn">Book a Ride</a>
    </div>
</div>

</body>
</html>
