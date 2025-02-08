DROP
DATABASE IF EXISTS `mega_city_cab`;
CREATE
DATABASE IF NOT EXISTS `mega_city_cab`;

USE
`mega_city_cab`;


CREATE TABLE users
(
    id            CHAR(36)     NOT NULL PRIMARY KEY,              -- UUID for user ID
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,                          -- Hashed password
    role          ENUM ('admin', 'user') NOT NULL DEFAULT 'user', -- User role
    createdAt     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt     DATETIME NULL
);



CREATE TABLE customers
(
    id        CHAR(36)     NOT NULL PRIMARY KEY, -- UUID for customer ID
    registrationNo VARCHAR(255) NOT NULL UNIQUE,
    name      VARCHAR(255) NOT NULL,
    address   VARCHAR(255) NOT NULL,
    nic       VARCHAR(20)  NOT NULL UNIQUE,
    dob       DATE         NOT NULL,
    mobileNo  VARCHAR(15)  NOT NULL UNIQUE,
    email     VARCHAR(255) NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt DATETIME NULL
);



CREATE TABLE drivers
(
    id            CHAR(36)     NOT NULL PRIMARY KEY, -- UUID for driver ID
    name          VARCHAR(255) NOT NULL,
    licenseNumber VARCHAR(255) NOT NULL UNIQUE,
    mobileNo      VARCHAR(15)  NOT NULL UNIQUE,
    status        ENUM ('available', 'unavailable') NOT NULL DEFAULT 'available',
    experience    INT          NOT NULL,
    email         VARCHAR(255) NOT NULL,
    createdAt     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt     DATETIME NULL
);



CREATE TABLE vehicles
(
    id           CHAR(36)       NOT NULL PRIMARY KEY, -- UUID for car ID
    licensePlate VARCHAR(15)    NOT NULL UNIQUE,
    model        VARCHAR(255)   NOT NULL,
    brand        VARCHAR(255)   NOT NULL,
    capacity     DECIMAL(10, 2) NOT NULL,
    color        VARCHAR(255)   NOT NULL,
    status       ENUM ('available', 'unavailable') NOT NULL DEFAULT 'available',
    createdAt    DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt    DATETIME NULL
);



CREATE TABLE bookings
(
    id             CHAR(36)       NOT NULL PRIMARY KEY,                                                          -- UUID for booking ID
    customerId     CHAR(36)       NOT NULL,                                                                      -- Reference to Customer
    bookingDate    DATETIME       NOT NULL,                                                                      -- Date and time of booking
    pickupLocation VARCHAR(255)   NOT NULL,                                                                      -- Pickup location
    destination    VARCHAR(255)   NOT NULL,                                                                      -- Destination
    pickupTime     TIME           NOT NULL,                                                                      -- Pickup time
    driverID       CHAR(36)       NOT NULL,                                                                      -- Reference to Driver
    vehicleID      CHAR(36)       NOT NULL,                                                                      -- Reference to Car
    status         ENUM ('pending', 'confirmed', 'cancelled', 'completed', 'failed') NOT NULL DEFAULT 'pending', -- Booking status
    distance       DECIMAL(10, 2) NOT NULL,                                                                      -- Distance
    fare           DECIMAL(10, 2) NOT NULL,                                                                      -- Fare for the ride
    discount       DECIMAL(10, 2) NOT NULL,                                                                      -- Discount
    tax            DECIMAL(10, 2) NOT NULL,                                                                      -- Tax
    userId         CHAR(36)       NOT NULL,                                                                      -- Reference to User
    createdAt      DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE,
    FOREIGN KEY (driverID) REFERENCES drivers (id) ON DELETE CASCADE,
    FOREIGN KEY (vehicleID) REFERENCES vehicles (id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);
