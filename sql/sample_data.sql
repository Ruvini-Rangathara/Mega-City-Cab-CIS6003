INSERT INTO users (id, email, password_hash, role)
VALUES (UUID(), 'admin@megacitycab.com', SHA2('admin123', 256), 'admin'), -- Admin user
       (UUID(), 'john.doe@example.com', SHA2('john123', 256), 'user'),    -- Regular user
       (UUID(), 'jane.smith@example.com', SHA2('jane123', 256), 'user'); -- Regular user


INSERT INTO customers (id,registrationNo, name, address, nic, dob, mobileNo, email)
VALUES (UUID(), 'C-001','John Doe', '123 Main St, Colombo', '123456789V', '1990-05-15', '0771234567', 'john.doe@example.com'),
       (UUID(), 'C-002','Jane Smith', '456 Park Ave, Kandy', '987654321V', '1985-10-20', '0777654321',
        'jane.smith@example.com'),
       (UUID(), 'C-003', 'Alice Johnson', '789 Galle Rd, Galle', '456789123V', '1995-03-25', '0779876543',
        'alice.johnson@example.com');

INSERT INTO drivers (id, name, licenseNumber, mobileNo, status, experience, email)
VALUES (UUID(), 'Robert Brown', 'D1234567', '0771111111', 'available', 5, 'robert.brown@example.com'),
       (UUID(), 'Michael Green', 'D7654321', '0772222222', 'available', 3, 'michael.green@example.com'),
       (UUID(), 'David White', 'D9876543', '0773333333', 'unavailable', 7, 'david.white@example.com');

INSERT INTO vehicles (id, licensePlate, model, brand, capacity, color, status)
VALUES (UUID(), 'CAB-1234', 'Corolla', 'Toyota', 4.0, 'White', 'available'),
       (UUID(), 'CAB-5678', 'Civic', 'Honda', 4.0, 'Black', 'available'),
       (UUID(), 'CAB-9101', 'Sunny', 'Nissan', 4.0, 'Blue', 'unavailable');

-- Get IDs for customers, drivers, vehicles, and users
SET @customerId1 = (SELECT id
                    FROM customers
                    WHERE email = 'john.doe@example.com');
SET @customerId2 = (SELECT id
                    FROM customers
                    WHERE email = 'jane.smith@example.com');
SET @driverId1 = (SELECT id
                  FROM drivers
                  WHERE email = 'robert.brown@example.com');
SET @driverId2 = (SELECT id
                  FROM drivers
                  WHERE email = 'michael.green@example.com');
SET @vehicleId1 = (SELECT id
                   FROM vehicles
                   WHERE licensePlate = 'CAB-1234');
SET @vehicleId2 = (SELECT id
                   FROM vehicles
                   WHERE licensePlate = 'CAB-5678');
SET @userId1 = (SELECT id
                FROM users
                WHERE email = 'john.doe@example.com');
SET @userId2 = (SELECT id
                FROM users
                WHERE email = 'jane.smith@example.com');

-- Insert bookings
INSERT INTO bookings (id, customerId, bookingDate, pickupLocation, destination, pickupTime, driverID, vehicleID, status,
                      distance, fare, discount, tax, userId)
VALUES (UUID(), @customerId1, NOW(), 'Colombo Fort', 'Kandy', '10:00:00', @driverId1, @vehicleId1, 'confirmed', 120.5,
        2500.00, 100.00, 200.00, @userId1),
       (UUID(), @customerId2, NOW(), 'Galle Face', 'Negombo', '12:30:00', @driverId2, @vehicleId2, 'pending', 30.0,
        800.00, 50.00, 80.00, @userId2);


-- Get IDs for bookings and users
SET @bookingId1 = (SELECT id
                   FROM bookings
                   WHERE customerId = @customerId1);
SET @bookingId2 = (SELECT id
                   FROM bookings
                   WHERE customerId = @customerId2);
