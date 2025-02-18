DROP
DATABASE IF EXISTS `mega_city_cab`;
CREATE
DATABASE IF NOT EXISTS `mega_city_cab`;

USE
`mega_city_cab`;


CREATE TABLE users
(
    id            CHAR(36)     NOT NULL PRIMARY KEY DEFAULT (UUID()),
    name         VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    salt          VARCHAR(255) NOT NULL,
    role          ENUM ('ADMIN', 'USER') NOT NULL DEFAULT 'USER', -- User role
    createdAt     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt     DATETIME NULL
);



CREATE TABLE customers
(
    id        CHAR(36)     NOT NULL PRIMARY KEY DEFAULT (UUID()),
    registrationNo VARCHAR(255) NOT NULL UNIQUE,
    name      VARCHAR(255) NOT NULL,
    address   VARCHAR(255) NOT NULL,
    nic       VARCHAR(20)  NOT NULL UNIQUE,
    dob       DATE         NOT NULL,
    mobileNo  VARCHAR(15)  NOT NULL ,
    email     VARCHAR(255) NOT NULL UNIQUE,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt DATETIME NULL
);



CREATE TABLE drivers
(
    id            CHAR(36)     NOT NULL PRIMARY KEY DEFAULT (UUID()),
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
    id           CHAR(36)       NOT NULL PRIMARY KEY DEFAULT (UUID()),
    licensePlate VARCHAR(15)    NOT NULL UNIQUE,
    driverId    CHAR(36)       NOT NULL,
    model        VARCHAR(255)   NOT NULL,
    brand        VARCHAR(255)   NOT NULL,
    capacity     DECIMAL(10, 2) NOT NULL,
    color        VARCHAR(255)   NOT NULL,
    status       ENUM ('available', 'unavailable') NOT NULL DEFAULT 'available',
    createdAt    DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt    DATETIME NULL,
    FOREIGN KEY (driverID) REFERENCES drivers (id) ON DELETE CASCADE
);



CREATE TABLE bookings
(
    id             CHAR(36)       NOT NULL PRIMARY KEY DEFAULT (UUID()),
    customerId     CHAR(36)       NOT NULL,
    bookingDate    DATETIME       NOT NULL,
    pickupLocation VARCHAR(255)   NOT NULL,
    destination    VARCHAR(255)   NOT NULL,
    pickupTime     TIME           NOT NULL,
    vehicleID      CHAR(36)       NOT NULL,
    status         ENUM ('pending', 'confirmed', 'cancelled', 'completed', 'failed') NOT NULL DEFAULT 'pending',
    distance       DECIMAL(10, 2) NOT NULL,
    fare           DECIMAL(10, 2) NOT NULL,
    discount       DECIMAL(10, 2) NOT NULL,
    tax            DECIMAL(10, 2) NOT NULL,
    userId         CHAR(36)       NOT NULL,
    createdAt      DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE,
    FOREIGN KEY (vehicleID) REFERENCES vehicles (id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE
);

