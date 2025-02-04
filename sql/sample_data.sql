-- Sample Users
INSERT INTO users (id, username, password_hash, role)
VALUES (UUID(), 'admin_user', 'hashed_password_1', 'admin'),
       (UUID(), 'customer_user_1', 'hashed_password_2', 'user'),
       (UUID(), 'customer_user_2', 'hashed_password_3', 'user'),
       (UUID(), 'driver_user_1', 'hashed_password_4', 'user'),
       (UUID(), 'driver_user_2', 'hashed_password_5', 'user');

-- Sample Customers
INSERT INTO customers (id, name, address, nic, dob, mobileNo)
VALUES (UUID(), 'John Doe', '123 Main St, Cityville', '1234567890', '1985-12-15', '123-456-7890'),
       (UUID(), 'Jane Smith', '456 Oak St, Townsville', '2345678901', '1990-03-20', '234-567-8901');
-- Sample Drivers
INSERT INTO drivers (id, name, licenseNumber, mobile, status, experience)
VALUES (UUID(), 'Alice Driver', 'DL12345', '987-654-3210', 'available', 5),
       (UUID(), 'Bob Driver', 'DL67890', '876-543-2109', 'unavailable', 3),
       (UUID(), 'Charlie Driver', 'DL54321', '765-432-1098', 'available', 7),
       (UUID(), 'David Driver', 'DL11223', '654-321-0987', 'available', 6);

-- Sample Cars
INSERT INTO cars (id, licensePlate, model, brand, capacity, color, status)
VALUES (UUID(), 'XYZ123', 'ModelX', 'BrandX', 4.5, 'Red', 'available'),
       (UUID(), 'ABC456', 'ModelY', 'BrandY', 4.0, 'Blue', 'unavailable'),
       (UUID(), 'DEF789', 'ModelZ', 'BrandZ', 5.0, 'Green', 'available'),
       (UUID(), 'GHI012', 'ModelA', 'BrandA', 4.0, 'Black', 'available');
SELECT id
FROM users
WHERE username = 'admin_user';

-- Sample Bookings
INSERT INTO bookings (id, customerId, bookingDate, pickupLocation, destination, pickupTime, driverID, carID, status,
                      distance, fare, discount, tax, userId)
VALUES (UUID(), (SELECT id FROM customers WHERE name = 'John Doe'), NOW(), 'Location A', 'Location B', '12:30:00',
        (SELECT id FROM drivers WHERE name = 'Alice Driver'), (SELECT id FROM cars WHERE licensePlate = 'XYZ123'),
        'confirmed', 10.5, 100.0, 10.0, 5.0, (SELECT id FROM users WHERE username = 'admin_user')),
       (UUID(), (SELECT id FROM customers WHERE name = 'Jane Smith'), NOW(), 'Location C', 'Location D', '13:00:00',
        (SELECT id FROM drivers WHERE name = 'Bob Driver'), (SELECT id FROM cars WHERE licensePlate = 'ABC456'),
        'pending', 8.0, 80.0, 5.0, 4.0, (SELECT id FROM users WHERE username = 'admin_user')),
       (UUID(), (SELECT id FROM customers WHERE name = 'John Doe'), NOW(), 'Location E', 'Location F', '15:00:00',
        (SELECT id FROM drivers WHERE name = 'Charlie Driver'), (SELECT id FROM cars WHERE licensePlate = 'DEF789'),
        'cancelled', 0.0, 0.0, 0.0, 0.0, (SELECT id FROM users WHERE username = 'admin_user'));


-- Sample Bills
INSERT INTO bills (id, bookingId, fare, tax, discount, totalAmount, paymentStatus, userId)
VALUES (UUID(), (SELECT id
                 FROM bookings
                 WHERE customerId = (SELECT id FROM customers WHERE name = 'John Doe') LIMIT 1), 100.0, 5.0, 10.0, 95.0, 'completed',
       (SELECT id FROM users WHERE username = 'admin_user')),
       (UUID(),
        (SELECT id FROM bookings WHERE customerId = (SELECT id FROM customers WHERE name = 'Jane Smith') LIMIT 1), 80.0,
        4.0, 5.0, 79.0, 'pending', (SELECT id FROM users WHERE username = 'admin_user')),
       (UUID(), (SELECT id FROM bookings WHERE customerId = (SELECT id FROM customers WHERE name = 'John Doe') LIMIT 1),
        0.0, 0.0, 0.0, 0.0, 'failed', (SELECT id FROM users WHERE username = 'admin_user'));


