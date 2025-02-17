-- Users table data
INSERT INTO users (email, passwordHash, role)
VALUES ('admin@megacitycab.com', SHA2('admin123', 256), 'ADMIN'),
       ('user1@megacitycab.com', SHA2('user1123', 256), 'USER'),
       ('user2@megacitycab.com', SHA2('user2123', 256), 'USER'),
       ('user3@megacitycab.com', SHA2('user3123', 256), 'USER'),
       ('user4@megacitycab.com', SHA2('user4123', 256), 'USER'),
       ('user5@megacitycab.com', SHA2('user5123', 256), 'USER'),
       ('user6@megacitycab.com', SHA2('user6123', 256), 'USER'),
       ('user7@megacitycab.com', SHA2('user7123', 256), 'USER'),
       ('user8@megacitycab.com', SHA2('user8123', 256), 'USER'),
       ('user9@megacitycab.com', SHA2('user9123', 256), 'USER');

-- Customers table data
INSERT INTO customers (registrationNo, name, address, nic, dob, mobileNo, email)
VALUES ('REG001', 'John Smith', '123 Main St, City', 'NIC001', '1990-01-15', '+94771234567', 'john@email.com'),
       ('REG002', 'Mary Johnson', '456 Oak Ave, Town', 'NIC002', '1985-03-22', '+94772234567', 'mary@email.com'),
       ('REG003', 'David Wilson', '789 Pine Rd, Village', 'NIC003', '1992-07-08', '+94773234567', 'david@email.com'),
       ('REG004', 'Sarah Brown', '321 Elm St, District', 'NIC004', '1988-11-30', '+94774234567', 'sarah@email.com'),
       ('REG005', 'Michael Lee', '654 Maple Dr, City', 'NIC005', '1995-04-17', '+94775234567', 'michael@email.com'),
       ('REG006', 'Lisa Anderson', '987 Cedar Ln, Town', 'NIC006', '1983-09-25', '+94776234567', 'lisa@email.com'),
       ('REG007', 'James Taylor', '147 Birch St, Village', 'NIC007', '1991-12-03', '+94777234567', 'james@email.com'),
       ('REG008', 'Emma Davis', '258 Walnut Ave, District', 'NIC008', '1987-06-20', '+94778234567', 'emma@email.com'),
       ('REG009', 'Robert Martin', '369 Pine St, City', 'NIC009', '1993-02-14', '+94779234567', 'robert@email.com'),
       ('REG010', 'Patricia White', '741 Oak Rd, Town', 'NIC010', '1986-08-09', '+94770234567', 'patricia@email.com');

-- Drivers table data
INSERT INTO drivers (name, licenseNumber, mobileNo, status, experience, email)
VALUES ('Tom Wilson', 'LIC001', '+94781234567', 'available', 5, 'tom@driver.com'),
       ('Jane Cooper', 'LIC002', '+94782234567', 'available', 3, 'jane@driver.com'),
       ('Steve Rogers', 'LIC003', '+94783234567', 'unavailable', 7, 'steve@driver.com'),
       ('Diana Prince', 'LIC004', '+94784234567', 'available', 4, 'diana@driver.com'),
       ('Bruce Wayne', 'LIC005', '+94785234567', 'available', 6, 'bruce@driver.com'),
       ('Peter Parker', 'LIC006', '+94786234567', 'unavailable', 2, 'peter@driver.com'),
       ('Tony Stark', 'LIC007', '+94787234567', 'available', 8, 'tony@driver.com'),
       ('Clark Kent', 'LIC008', '+94788234567', 'available', 5, 'clark@driver.com'),
       ('Wade Wilson', 'LIC009', '+94789234567', 'unavailable', 3, 'wade@driver.com'),
       ('Logan Howlett', 'LIC010', '+94780234567', 'available', 6, 'logan@driver.com');

-- Get driver IDs for vehicles
SET @driver1 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC001');
SET @driver2 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC002');
SET @driver3 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC003');
SET @driver4 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC004');
SET @driver5 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC005');
SET @driver6 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC006');
SET @driver7 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC007');
SET @driver8 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC008');
SET @driver9 = (SELECT id
                FROM drivers
                WHERE licenseNumber = 'LIC009');
SET @driver10 = (SELECT id
                 FROM drivers
                 WHERE licenseNumber = 'LIC010');

-- Vehicles table data
INSERT INTO vehicles (licensePlate, driverId, model, brand, capacity, color, status)
VALUES ('ABC-1234', @driver1, 'Camry', 'Toyota', 4.00, 'Silver', 'available'),
       ('DEF-5678', @driver2, 'Civic', 'Honda', 4.00, 'Black', 'available'),
       ('GHI-9012', @driver3, 'Corolla', 'Toyota', 4.00, 'White', 'unavailable'),
       ('JKL-3456', @driver4, 'Accord', 'Honda', 4.00, 'Blue', 'available'),
       ('MNO-7890', @driver5, 'Prius', 'Toyota', 4.00, 'Green', 'available'),
       ('PQR-1234', @driver6, 'City', 'Honda', 4.00, 'Red', 'unavailable'),
       ('STU-5678', @driver7, 'Avalon', 'Toyota', 4.00, 'Black', 'available'),
       ('VWX-9012', @driver8, 'Insight', 'Honda', 4.00, 'Silver', 'available'),
       ('YZA-3456', @driver9, 'Camry', 'Toyota', 4.00, 'White', 'unavailable'),
       ('BCD-7890', @driver10, 'Civic', 'Honda', 4.00, 'Blue', 'available');

-- Get IDs for bookings
SET @customer1 = (SELECT id
                  FROM customers
                  WHERE registrationNo = 'REG001');
SET @customer2 = (SELECT id
                  FROM customers
                  WHERE registrationNo = 'REG002');
SET @customer3 = (SELECT id
                  FROM customers
                  WHERE registrationNo = 'REG003');
SET @customer4 = (SELECT id
                  FROM customers
                  WHERE registrationNo = 'REG004');
SET @customer5 = (SELECT id
                  FROM customers
                  WHERE registrationNo = 'REG005');

SET @vehicle1 = (SELECT id
                 FROM vehicles
                 WHERE licensePlate = 'ABC-1234');
SET @vehicle2 = (SELECT id
                 FROM vehicles
                 WHERE licensePlate = 'DEF-5678');
SET @vehicle3 = (SELECT id
                 FROM vehicles
                 WHERE licensePlate = 'JKL-3456');
SET @vehicle4 = (SELECT id
                 FROM vehicles
                 WHERE licensePlate = 'MNO-7890');
SET @vehicle5 = (SELECT id
                 FROM vehicles
                 WHERE licensePlate = 'STU-5678');

SET @user1 = (SELECT id
              FROM users
              WHERE email = 'user1@megacitycab.com');
SET @user2 = (SELECT id
              FROM users
              WHERE email = 'user2@megacitycab.com');
SET @user3 = (SELECT id
              FROM users
              WHERE email = 'user3@megacitycab.com');
SET @user4 = (SELECT id
              FROM users
              WHERE email = 'user4@megacitycab.com');
SET @user5 = (SELECT id
              FROM users
              WHERE email = 'user5@megacitycab.com');

-- Bookings table data
INSERT INTO bookings (customerId, bookingDate, pickupLocation, destination, pickupTime, vehicleID, status, distance,
                      fare, discount, tax, userId)
VALUES (@customer1, '2024-02-17 10:00:00', 'Airport', 'Hotel Grand', '10:30:00', @vehicle1, 'confirmed', 15.50, 2500.00,
        0.00, 250.00, @user1),
       (@customer2, '2024-02-17 11:30:00', 'Hotel Plaza', 'Shopping Mall', '12:00:00', @vehicle2, 'completed', 8.75,
        1500.00, 100.00, 150.00, @user2),
       (@customer3, '2024-02-17 13:00:00', 'Office Complex', 'Restaurant Row', '13:30:00', @vehicle3, 'pending', 5.25,
        1000.00, 50.00, 100.00, @user3),
       (@customer4, '2024-02-17 14:30:00', 'University', 'Library', '15:00:00', @vehicle4, 'confirmed', 3.80, 800.00,
        0.00, 80.00, @user4),
       (@customer5, '2024-02-17 16:00:00', 'Train Station', 'Residential Area', '16:30:00', @vehicle5, 'cancelled',
        12.30, 2000.00, 200.00, 200.00, @user5),
       (@customer1, '2024-02-17 17:30:00', 'Shopping Mall', 'Airport', '18:00:00', @vehicle1, 'completed', 16.20,
        2600.00, 100.00, 260.00, @user1),
       (@customer2, '2024-02-17 19:00:00', 'Restaurant', 'Hotel Plaza', '19:30:00', @vehicle2, 'confirmed', 7.50,
        1300.00, 50.00, 130.00, @user2),
       (@customer3, '2024-02-17 20:30:00', 'Cinema', 'Office Complex', '21:00:00', @vehicle3, 'pending', 4.90, 950.00,
        0.00, 95.00, @user3),
       (@customer4, '2024-02-17 22:00:00', 'Night Club', 'University', '22:30:00', @vehicle4, 'confirmed', 6.70,
        1200.00, 100.00, 120.00, @user4),
       (@customer5, '2024-02-17 23:30:00', 'Hotel Grand', 'Train Station', '00:00:00', @vehicle5, 'pending', 9.40,
        1700.00, 150.00, 170.00, @user5);
