DROP
DATABASE IF EXISTS `mega_city_cab`;
CREATE
DATABASE IF NOT EXISTS `mega_city_cab`;

USE `mega_city_cab`;


CREATE TABLE users
(
    id           CHAR(36)               NOT NULL PRIMARY KEY DEFAULT (UUID()),
    name         VARCHAR(255)           NOT NULL,
    email        VARCHAR(255)           NOT NULL,
    passwordHash VARCHAR(255)           NOT NULL,
    salt         VARCHAR(255)           NOT NULL,
    role         ENUM ('admin', 'user') NOT NULL             DEFAULT 'user', -- User role
    createdAt    DATETIME                                    DEFAULT CURRENT_TIMESTAMP,
    updatedAt    DATETIME                                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt    DATETIME               NULL
);



CREATE TABLE customers
(
    id             CHAR(36)     NOT NULL PRIMARY KEY DEFAULT (UUID()),
    registrationNo VARCHAR(255) NOT NULL UNIQUE,
    name           VARCHAR(255) NOT NULL,
    address        VARCHAR(255) NOT NULL,
    nic            VARCHAR(20)  NOT NULL UNIQUE,
    dob            DATE         NOT NULL,
    mobileNo       VARCHAR(15)  NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    createdAt      DATETIME                          DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME                          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt      DATETIME     NULL
);



CREATE TABLE drivers
(
    id         CHAR(36)     NOT NULL PRIMARY KEY DEFAULT (UUID()),
    name       VARCHAR(255) NOT NULL,
    licenseNo  VARCHAR(255) NOT NULL UNIQUE,
    mobileNo   VARCHAR(15)  NOT NULL UNIQUE,
    experience INT          NOT NULL,
    email      VARCHAR(255) NOT NULL,
    createdAt  DATETIME                          DEFAULT CURRENT_TIMESTAMP,
    updatedAt  DATETIME                          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt  DATETIME     NULL
);



CREATE TABLE vehicles
(
    id           CHAR(36)                          NOT NULL PRIMARY KEY DEFAULT (UUID()),
    licensePlate VARCHAR(15)                       NOT NULL UNIQUE,
    driverId     CHAR(36)                          NOT NULL,
    model        VARCHAR(255)                      NOT NULL,
    brand        VARCHAR(255)                      NOT NULL,
    capacity     INT                               NOT NULL,
    color        VARCHAR(255)                      NOT NULL,
    pricePerKm   DECIMAL(10, 2)                    NOT NULL,
    status       ENUM ('available', 'unavailable') NOT NULL             DEFAULT 'available',
    createdAt    DATETIME                                               DEFAULT CURRENT_TIMESTAMP,
    updatedAt    DATETIME                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt    DATETIME                          NULL,
    FOREIGN KEY (driverID) REFERENCES drivers (id) ON DELETE CASCADE
);



CREATE TABLE bookings
(
    id             CHAR(36)                                                          NOT NULL PRIMARY KEY DEFAULT (UUID()),
    customerId     CHAR(36)                                                          NOT NULL,
    bookingDate    DATETIME                                                          NOT NULL,
    pickupLocation VARCHAR(255)                                                      NOT NULL,
    destination    VARCHAR(255)                                                      NOT NULL,
    pickupTime     TIME                                                              NOT NULL,
    releaseTime    TIME                                                              NOT NULL,
    vehicleID      CHAR(36)                                                          NOT NULL,
    status         ENUM ('pending', 'confirmed', 'cancelled', 'completed', 'failed') NOT NULL             DEFAULT 'pending',
    distance       DECIMAL(10, 2)                                                    NOT NULL,
    fare           DECIMAL(10, 2)                                                    NOT NULL,
    discount       DECIMAL(10, 2)                                                    NOT NULL,
    tax            DECIMAL(10, 2)                                                    NOT NULL,
    netTotal       DECIMAL(10, 2)                                                    NOT NULL,
    userId         CHAR(36)                                                          NULL,
    createdAt      DATETIME                                                                               DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME                                                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE,
    FOREIGN KEY (vehicleID) REFERENCES vehicles (id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);



DELIMITER //
CREATE PROCEDURE GetAvailableVehicles(
    IN p_bookingDate DATETIME,
    IN p_pickupTime TIME,
    IN p_releaseTime TIME,
    OUT p_result BOOLEAN
)
BEGIN
    CREATE TEMPORARY TABLE IF NOT EXISTS temp_available_vehicles AS
SELECT
    v.id, v.licensePlate, v.driverId, v.model, v.brand, v.capacity, v.color, v.pricePerKm,
    d.name AS driverName, d.mobileNo AS driverMobile, d.licenseNo AS driverLicense
FROM vehicles v
         LEFT JOIN drivers d ON v.driverId = d.id
WHERE v.status = 'available'
  AND v.deletedAt IS NULL
  AND NOT EXISTS (
    SELECT 1 FROM bookings b
    WHERE b.vehicleId = v.id
      AND DATE(b.bookingDate) = DATE(p_bookingDate) -- Compare only date part
        AND b.status NOT IN ('cancelled', 'failed')
        AND (p_pickupTime < b.releaseTime AND p_releaseTime > b.pickupTime)
);

SELECT * FROM temp_available_vehicles;

SET p_result = TRUE;

    DROP TEMPORARY TABLE IF EXISTS temp_available_vehicles;
END //
DELIMITER ;
