USE
    `mega_city_cab`;

-- Insert Users (10 records)
INSERT INTO users (name, email, passwordHash, salt, role, createdAt)
VALUES ('John Doe', 'john.doe@megacitycab.com', SHA2(CONCAT('admin123', 'salt1'), 256), 'salt1', 'admin',
        '2025-02-10 09:00:00'),
       ('Alice Johnson', 'alice.j@megacitycab.com', SHA2(CONCAT('user123', 'salt2'), 256), 'salt2', 'user',
        '2025-02-12 10:00:00'),
       ('Bob Smith', 'bob.s@megacitycab.com', SHA2(CONCAT('user123', 'salt3'), 256), 'salt3', 'user',
        '2025-02-15 11:00:00'),
       ('Charlie Brown', 'charlie.b@megacitycab.com', SHA2(CONCAT('user123', 'salt4'), 256), 'salt4', 'user',
        '2025-02-20 12:00:00'),
       ('David Lee', 'david.l@megacitycab.com', SHA2(CONCAT('user123', 'salt5'), 256), 'salt5', 'user',
        '2025-02-25 13:00:00'),
       ('Emma Williams', 'emma.w@megacitycab.com', SHA2(CONCAT('user123', 'salt6'), 256), 'salt6', 'user',
        '2025-03-01 14:00:00'),
       ('Frank Miller', 'frank.m@megacitycab.com', SHA2(CONCAT('user123', 'salt7'), 256), 'salt7', 'user',
        '2025-03-05 15:00:00'),
       ('Grace Davis', 'grace.d@megacitycab.com', SHA2(CONCAT('user123', 'salt8'), 256), 'salt8', 'user',
        '2025-03-07 16:00:00'),
       ('Hannah Wilson', 'hannah.w@megacitycab.com', SHA2(CONCAT('user123', 'salt9'), 256), 'salt9', 'user',
        '2025-03-09 17:00:00'),
       ('Isaac Moore', 'isaac.m@megacitycab.com', SHA2(CONCAT('user123', 'salt10'), 256), 'salt10', 'user',
        '2025-03-11 18:00:00');

-- Insert Customers (50 records)
INSERT INTO customers (registrationNo, name, address, nic, dob, mobileNo, email, createdAt)
VALUES ('REG001', 'John Smith', '123 Main St', '901234567V', '1990-01-15', '0711234561', 'john.s@email.com',
        '2025-02-10 09:00:00'),
       ('REG002', 'Mary Johnson', '456 Oak Ave', '851234567V', '1985-03-22', '0721234562', 'mary.j@email.com',
        '2025-02-11 10:00:00'),
       ('REG003', 'David Wilson', '789 Pine Rd', '921234567V', '1992-07-08', '0751234563', 'david.w@email.com',
        '2025-02-12 11:00:00'),
       ('REG004', 'Sarah Brown', '321 Elm St', '881234567V', '1988-11-30', '0761234564', 'sarah.b@email.com',
        '2025-02-13 12:00:00'),
       ('REG005', 'Michael Lee', '654 Maple Dr', '951234567V', '1995-04-17', '0771234565', 'michael.l@email.com',
        '2025-02-14 13:00:00'),
       ('REG006', 'Lisa Anderson', '987 Cedar Ln', '831234567V', '1983-09-25', '0781234566', 'lisa.a@email.com',
        '2025-02-15 14:00:00'),
       ('REG007', 'James Taylor', '147 Birch St', '911234567V', '1991-12-03', '0791234567', 'james.t@email.com',
        '2025-02-16 15:00:00'),
       ('REG008', 'Emma Davis', '258 Walnut Ave', '861234567V', '1987-06-20', '0752234568', 'emma.d@email.com',
        '2025-02-17 16:00:00'),
       ('REG009', 'Robert Martin', '369 Pine St', '941234567V', '1993-02-14', '0762234569', 'robert.m@email.com',
        '2025-02-18 17:00:00'),
       ('REG010', 'Patricia White', '741 Oak Rd', '871234567V', '1986-08-09', '0772234570', 'patricia.w@email.com',
        '2025-02-19 18:00:00'),
       ('REG011', 'Tom Clark', '852 Elm St', '891234568V', '1990-05-10', '0712234571', 'tom.c@email.com',
        '2025-02-20 09:00:00'),
       ('REG012', 'Jane Doe', '963 Pine Rd', '931234568V', '1988-07-15', '0722234572', 'jane.d@email.com',
        '2025-02-21 10:00:00'),
       ('REG013', 'Sam Evans', '147 Oak Ave', '841234568V', '1992-09-20', '0732234573', 'sam.e@email.com',
        '2025-02-22 11:00:00'),
       ('REG014', 'Lily Brown', '258 Cedar Ln', '961234568V', '1987-11-25', '0742234574', 'lily.b@email.com',
        '2025-02-23 12:00:00'),
       ('REG015', 'Max Turner', '369 Birch St', '971234568V', '1995-01-30', '0752234575', 'max.t@email.com',
        '2025-02-24 13:00:00'),
       ('REG016', 'Zoe Quinn', '741 Maple Dr', '881234569V', '1984-03-05', '0762234576', 'zoe.q@email.com',
        '2025-02-25 14:00:00'),
       ('REG017', 'Alex King', '852 Walnut Ave', '991234569V', '1991-05-10', '0772234577', 'alex.k@email.com',
        '2025-02-26 15:00:00'),
       ('REG018', 'Ella Rose', '963 Elm St', '901234570V', '1989-07-15', '0782234578', 'ella.r@email.com',
        '2025-02-27 16:00:00'),
       ('REG019', 'Noah Hill', '147 Pine Rd', '911234571V', '1993-09-20', '0792234579', 'noah.h@email.com',
        '2025-02-28 17:00:00'),
       ('REG020', 'Sophia Gray', '258 Oak Ave', '921234572V', '1986-11-25', '0713234580', 'sophia.g@email.com',
        '2025-03-01 18:00:00'),
       ('REG021', 'Liam Fox', '369 Cedar Ln', '931234573V', '1994-01-30', '0723234581', 'liam.f@email.com',
        '2025-03-02 09:00:00'),
       ('REG022', 'Olivia Stone', '741 Birch St', '941234574V', '1985-03-05', '0733234582', 'olivia.s@email.com',
        '2025-03-03 10:00:00'),
       ('REG023', 'Ethan Wood', '852 Maple Dr', '951234575V', '1992-05-10', '0743234583', 'ethan.w@email.com',
        '2025-03-04 11:00:00'),
       ('REG024', 'Ava Brooks', '963 Walnut Ave', '961234576V', '1988-07-15', '0753234584', 'ava.b@email.com',
        '2025-03-05 12:00:00'),
       ('REG025', 'Mason Cole', '147 Elm St', '971234577V', '1990-09-20', '0763234585', 'mason.c@email.com',
        '2025-03-06 13:00:00'),
       ('REG026', 'Isabella Reed', '258 Pine Rd', '981234578V', '1987-11-25', '0773234586', 'isabella.r@email.com',
        '2025-03-07 14:00:00'),
       ('REG027', 'Logan Bell', '369 Oak Ave', '991234579V', '1995-01-30', '0783234587', 'logan.b@email.com',
        '2025-03-08 15:00:00'),
       ('REG028', 'Mia Ward', '741 Cedar Ln', '901234580V', '1984-03-05', '0793234588', 'mia.w@email.com',
        '2025-03-09 16:00:00'),
       ('REG029', 'Lucas Hayes', '852 Birch St', '911234581V', '1991-05-10', '0714234589', 'lucas.h@email.com',
        '2025-03-10 17:00:00'),
       ('REG030', 'Charlotte Perry', '963 Maple Dr', '921234582V', '1989-07-15', '0724234590', 'charlotte.p@email.com',
        '2025-03-11 18:00:00'),
       ('REG031', 'Henry Cook', '147 Walnut Ave', '931234583V', '1993-09-20', '0734234591', 'henry.c@email.com',
        '2025-02-10 09:00:00'),
       ('REG032', 'Amelia Price', '258 Elm St', '941234584V', '1986-11-25', '0744234592', 'amelia.p@email.com',
        '2025-02-11 10:00:00'),
       ('REG033', 'Jack Ross', '369 Pine Rd', '951234585V', '1994-01-30', '0754234593', 'jack.r@email.com',
        '2025-02-12 11:00:00'),
       ('REG034', 'Harper Long', '741 Oak Ave', '961234586V', '1985-03-05', '0764234594', 'harper.l@email.com',
        '2025-02-13 12:00:00'),
       ('REG035', 'Elijah Scott', '852 Cedar Ln', '971234587V', '1992-05-10', '0774234595', 'elijah.s@email.com',
        '2025-02-14 13:00:00'),
       ('REG036', 'Scarlett Hart', '963 Birch St', '981234588V', '1988-07-15', '0784234596', 'scarlett.h@email.com',
        '2025-02-15 14:00:00'),
       ('REG037', 'Carter Lane', '147 Maple Dr', '991234589V', '1990-09-20', '0794234597', 'carter.l@email.com',
        '2025-02-16 15:00:00'),
       ('REG038', 'Violet Dean', '258 Walnut Ave', '901234590V', '1987-11-25', '0715234598', 'violet.d@email.com',
        '2025-02-17 16:00:00'),
       ('REG039', 'Owen Ford', '369 Elm St', '911234591V', '1995-01-30', '0725234599', 'owen.f@email.com',
        '2025-02-18 17:00:00'),
       ('REG040', 'Layla Page', '741 Pine Rd', '921234592V', '1984-03-05', '0735234600', 'layla.p@email.com',
        '2025-02-19 18:00:00'),
       ('REG041', 'Gabriel Hunt', '852 Oak Ave', '931234593V', '1991-05-10', '0745234601', 'gabriel.h@email.com',
        '2025-02-20 09:00:00'),
       ('REG042', 'Chloe Wells', '963 Cedar Ln', '941234594V', '1989-07-15', '0755234602', 'chloe.w@email.com',
        '2025-02-21 10:00:00'),
       ('REG043', 'Wyatt Boyd', '147 Birch St', '951234595V', '1993-09-20', '0765234603', 'wyatt.b@email.com',
        '2025-02-22 11:00:00'),
       ('REG044', 'Aria Dunn', '258 Maple Dr', '961234596V', '1986-11-25', '0775234604', 'aria.d@email.com',
        '2025-02-23 12:00:00'),
       ('REG045', 'Levi Reid', '369 Walnut Ave', '971234597V', '1994-01-30', '0785234605', 'levi.r@email.com',
        '2025-02-24 13:00:00'),
       ('REG046', 'Stella Moss', '741 Elm St', '981234598V', '1985-03-05', '0795234606', 'stella.m@email.com',
        '2025-02-25 14:00:00'),
       ('REG047', 'Isaac Ray', '852 Pine Rd', '991234599V', '1992-05-10', '0716234607', 'isaac.r@email.com',
        '2025-02-26 15:00:00'),
       ('REG048', 'Luna Hale', '963 Oak Ave', '901234600V', '1988-07-15', '0726234608', 'luna.h@email.com',
        '2025-02-27 16:00:00'),
       ('REG049', 'Eli Grant', '147 Cedar Ln', '911234601V', '1990-09-20', '0736234609', 'eli.g@email.com',
        '2025-02-28 17:00:00'),
       ('REG050', 'Nora Cruz', '258 Birch St', '921234602V', '1987-11-25', '0746234610', 'nora.c@email.com',
        '2025-03-01 18:00:00');

-- Insert Drivers (50 records)
INSERT INTO drivers (name, licenseNo, mobileNo, experience, email, createdAt)
VALUES ('Tom Wilson', 'LIC001', '+94711234561', 5, 'tom.w@driver.com', '2025-02-10 09:00:00'),
       ('Jane Cooper', 'LIC002', '+94721234562', 3, 'jane.c@driver.com', '2025-02-11 10:00:00'),
       ('Steve Rogers', 'LIC003', '+94731234563', 7, 'steve.r@driver.com', '2025-02-12 11:00:00'),
       ('Diana Prince', 'LIC004', '+94741234564', 4, 'diana.p@driver.com', '2025-02-13 12:00:00'),
       ('Bruce Wayne', 'LIC005', '+94751234565', 6, 'bruce.w@driver.com', '2025-02-14 13:00:00'),
       ('Peter Parker', 'LIC006', '+94761234566', 2, 'peter.p@driver.com', '2025-02-15 14:00:00'),
       ('Tony Stark', 'LIC007', '+94771234567', 8, 'tony.s@driver.com', '2025-02-16 15:00:00'),
       ('Clark Kent', 'LIC008', '+94781234568', 5, 'clark.k@driver.com', '2025-02-17 16:00:00'),
       ('Wade Wilson', 'LIC009', '+94791234569', 3, 'wade.w@driver.com', '2025-02-18 17:00:00'),
       ('Logan Howlett', 'LIC010', '+94701234570', 6, 'logan.h@driver.com', '2025-02-19 18:00:00'),
       ('Sam Evans', 'LIC011', '+94711234571', 4, 'sam.e@driver.com', '2025-02-20 09:00:00'),
       ('Lily Brown', 'LIC012', '+94721234572', 5, 'lily.b@driver.com', '2025-02-21 10:00:00'),
       ('Max Turner', 'LIC013', '+94731234573', 7, 'max.t@driver.com', '2025-02-22 11:00:00'),
       ('Zoe Quinn', 'LIC014', '+94741234574', 3, 'zoe.q@driver.com', '2025-02-23 12:00:00'),
       ('Alex King', 'LIC015', '+94751234575', 6, 'alex.k@driver.com', '2025-02-24 13:00:00'),
       ('Ella Rose', 'LIC016', '+94761234576', 4, 'ella.r@driver.com', '2025-02-25 14:00:00'),
       ('Noah Hill', 'LIC017', '+94771234577', 5, 'noah.h@driver.com', '2025-02-26 15:00:00'),
       ('Sophia Gray', 'LIC018', '+94781234578', 2, 'sophia.g@driver.com', '2025-02-27 16:00:00'),
       ('Liam Fox', 'LIC019', '+94791234579', 8, 'liam.f@driver.com', '2025-02-28 17:00:00'),
       ('Olivia Stone', 'LIC020', '+94701234580', 3, 'olivia.s@driver.com', '2025-03-01 18:00:00'),
       ('Ethan Wood', 'LIC021', '+94711234581', 6, 'ethan.w@driver.com', '2025-03-02 09:00:00'),
       ('Ava Brooks', 'LIC022', '+94721234582', 4, 'ava.b@driver.com', '2025-03-03 10:00:00'),
       ('Mason Cole', 'LIC023', '+94731234583', 5, 'mason.c@driver.com', '2025-03-04 11:00:00'),
       ('Isabella Reed', 'LIC024', '+94741234584', 7, 'isabella.r@driver.com', '2025-03-05 12:00:00'),
       ('Logan Bell', 'LIC025', '+94751234585', 3, 'logan.b@driver.com', '2025-03-06 13:00:00'),
       ('Mia Ward', 'LIC026', '+94761234586', 6, 'mia.w@driver.com', '2025-03-07 14:00:00'),
       ('Lucas Hayes', 'LIC027', '+94771234587', 4, 'lucas.h@driver.com', '2025-03-08 15:00:00'),
       ('Charlotte Perry', 'LIC028', '+94781234588', 5, 'charlotte.p@driver.com', '2025-03-09 16:00:00'),
       ('Henry Cook', 'LIC029', '+94791234589', 2, 'henry.c@driver.com', '2025-03-10 17:00:00'),
       ('Amelia Price', 'LIC030', '+94701234590', 8, 'amelia.p@driver.com', '2025-03-11 18:00:00'),
       ('Jack Ross', 'LIC031', '+94711234591', 3, 'jack.r@driver.com', '2025-02-10 09:00:00'),
       ('Harper Long', 'LIC032', '+94721234592', 6, 'harper.l@driver.com', '2025-02-11 10:00:00'),
       ('Elijah Scott', 'LIC033', '+94731234593', 4, 'elijah.s@driver.com', '2025-02-12 11:00:00'),
       ('Scarlett Hart', 'LIC034', '+94741234594', 5, 'scarlett.h@driver.com', '2025-02-13 12:00:00'),
       ('Carter Lane', 'LIC035', '+94751234595', 7, 'carter.l@driver.com', '2025-02-14 13:00:00'),
       ('Violet Dean', 'LIC036', '+94761234596', 3, 'violet.d@driver.com', '2025-02-15 14:00:00'),
       ('Owen Ford', 'LIC037', '+94771234597', 6, 'owen.f@driver.com', '2025-02-16 15:00:00'),
       ('Layla Page', 'LIC038', '+94781234598', 4, 'layla.p@driver.com', '2025-02-17 16:00:00'),
       ('Gabriel Hunt', 'LIC039', '+94791234599', 5, 'gabriel.h@driver.com', '2025-02-18 17:00:00'),
       ('Chloe Wells', 'LIC040', '+94701234600', 2, 'chloe.w@driver.com', '2025-02-19 18:00:00'),
       ('Wyatt Boyd', 'LIC041', '+94711234601', 8, 'wyatt.b@driver.com', '2025-02-20 09:00:00'),
       ('Aria Dunn', 'LIC042', '+94721234602', 3, 'aria.d@driver.com', '2025-02-21 10:00:00'),
       ('Levi Reid', 'LIC043', '+94731234603', 6, 'levi.r@driver.com', '2025-02-22 11:00:00'),
       ('Stella Moss', 'LIC044', '+94741234604', 4, 'stella.m@driver.com', '2025-02-23 12:00:00'),
       ('Isaac Ray', 'LIC045', '+94751234605', 5, 'isaac.r@driver.com', '2025-02-24 13:00:00'),
       ('Luna Hale', 'LIC046', '+94761234606', 7, 'luna.h@driver.com', '2025-02-25 14:00:00'),
       ('Eli Grant', 'LIC047', '+94771234607', 3, 'eli.g@driver.com', '2025-02-26 15:00:00'),
       ('Nora Cruz', 'LIC048', '+94781234608', 6, 'nora.c@driver.com', '2025-02-27 16:00:00'),
       ('Ben Miles', 'LIC049', '+94791234609', 4, 'ben.m@driver.com', '2025-02-28 17:00:00'),
       ('Tara Lane', 'LIC050', '+94701234610', 5, 'tara.l@driver.com', '2025-03-01 18:00:00');

-- Insert Vehicles (50 records)
INSERT INTO vehicles (licensePlate, driverId, model, brand, capacity, color, pricePerKm, status, createdAt)
VALUES ('ABC-1234', (SELECT id FROM drivers WHERE licenseNo = 'LIC001'), 'Camry', 'Toyota', 4, 'Silver', 200.00,
        'available', '2025-02-10 09:00:00'),
       ('DEF-5678', (SELECT id FROM drivers WHERE licenseNo = 'LIC002'), 'Civic', 'Honda', 4, 'Black', 300.00,
        'available', '2025-02-11 10:00:00'),
       ('GHI-9012', (SELECT id FROM drivers WHERE licenseNo = 'LIC003'), 'Corolla', 'Toyota', 4, 'White', 400.00,
        'unavailable', '2025-02-12 11:00:00'),
       ('JKL-3456', (SELECT id FROM drivers WHERE licenseNo = 'LIC004'), 'Accord', 'Honda', 4, 'Blue', 500.00,
        'available', '2025-02-13 12:00:00'),
       ('MNO-7890', (SELECT id FROM drivers WHERE licenseNo = 'LIC005'), 'Prius', 'Toyota', 4, 'Green', 600.00,
        'available', '2025-02-14 13:00:00'),
       ('PQR-1234', (SELECT id FROM drivers WHERE licenseNo = 'LIC006'), 'City', 'Honda', 4, 'Red', 250.00,
        'unavailable', '2025-02-15 14:00:00'),
       ('STU-5678', (SELECT id FROM drivers WHERE licenseNo = 'LIC007'), 'Avalon', 'Toyota', 4, 'Black', 350.00,
        'available', '2025-02-16 15:00:00'),
       ('VWX-9012', (SELECT id FROM drivers WHERE licenseNo = 'LIC008'), 'Insight', 'Honda', 4, 'Silver', 450.00,
        'available', '2025-02-17 16:00:00'),
       ('YZA-3456', (SELECT id FROM drivers WHERE licenseNo = 'LIC009'), 'Camry', 'Toyota', 4, 'White', 550.00,
        'unavailable', '2025-02-18 17:00:00'),
       ('BCD-7890', (SELECT id FROM drivers WHERE licenseNo = 'LIC010'), 'Civic', 'Honda', 4, 'Blue', 350.00,
        'available', '2025-02-19 18:00:00'),
       ('EFG-1235', (SELECT id FROM drivers WHERE licenseNo = 'LIC011'), 'Altima', 'Nissan', 4, 'Gray', 300.00,
        'available', '2025-02-20 09:00:00'),
       ('HIJ-5679', (SELECT id FROM drivers WHERE licenseNo = 'LIC012'), 'Sentra', 'Nissan', 4, 'Black', 400.00,
        'available', '2025-02-21 10:00:00'),
       ('KLM-9013', (SELECT id FROM drivers WHERE licenseNo = 'LIC013'), 'CR-V', 'Honda', 4, 'Red', 500.00,
        'unavailable', '2025-02-22 11:00:00'),
       ('NOP-3457', (SELECT id FROM drivers WHERE licenseNo = 'LIC014'), 'RAV4', 'Toyota', 4, 'White', 450.00,
        'available', '2025-02-23 12:00:00'),
       ('QRS-7891', (SELECT id FROM drivers WHERE licenseNo = 'LIC015'), 'Fit', 'Honda', 4, 'Silver', 250.00,
        'available', '2025-02-24 13:00:00'),
       ('TUV-1235', (SELECT id FROM drivers WHERE licenseNo = 'LIC016'), 'Highlander', 'Toyota', 4, 'Blue', 600.00,
        'unavailable', '2025-02-25 14:00:00'),
       ('WXY-5679', (SELECT id FROM drivers WHERE licenseNo = 'LIC017'), 'Pilot', 'Honda', 4, 'Green', 350.00,
        'available', '2025-02-26 15:00:00'),
       ('ZAB-9013', (SELECT id FROM drivers WHERE licenseNo = 'LIC018'), 'Maxima', 'Nissan', 4, 'Black', 400.00,
        'available', '2025-02-27 16:00:00'),
       ('CDE-3457', (SELECT id FROM drivers WHERE licenseNo = 'LIC019'), 'Corolla', 'Toyota', 4, 'White', 300.00,
        'unavailable', '2025-02-28 17:00:00'),
       ('FGH-7891', (SELECT id FROM drivers WHERE licenseNo = 'LIC020'), 'Civic', 'Honda', 4, 'Red', 450.00,
        'available', '2025-03-01 18:00:00'),
       ('IJK-1236', (SELECT id FROM drivers WHERE licenseNo = 'LIC021'), 'Camry', 'Toyota', 4, 'Silver', 500.00,
        'available', '2025-03-02 09:00:00'),
       ('LMN-5670', (SELECT id FROM drivers WHERE licenseNo = 'LIC022'), 'Accord', 'Honda', 4, 'Blue', 350.00,
        'unavailable', '2025-03-03 10:00:00'),
       ('OPQ-9014', (SELECT id FROM drivers WHERE licenseNo = 'LIC023'), 'Prius', 'Toyota', 4, 'Green', 400.00,
        'available', '2025-03-04 11:00:00'),
       ('RST-3458', (SELECT id FROM drivers WHERE licenseNo = 'LIC024'), 'City', 'Honda', 4, 'Black', 300.00,
        'available', '2025-03-05 12:00:00'),
       ('UVW-7892', (SELECT id FROM drivers WHERE licenseNo = 'LIC025'), 'Avalon', 'Toyota', 4, 'White', 550.00,
        'unavailable', '2025-03-06 13:00:00'),
       ('XYZ-1236', (SELECT id FROM drivers WHERE licenseNo = 'LIC026'), 'Insight', 'Honda', 4, 'Red', 450.00,
        'available', '2025-03-07 14:00:00'),
       ('ABC-5670', (SELECT id FROM drivers WHERE licenseNo = 'LIC027'), 'Altima', 'Nissan', 4, 'Silver', 350.00,
        'available', '2025-03-08 15:00:00'),
       ('DEF-9014', (SELECT id FROM drivers WHERE licenseNo = 'LIC028'), 'Sentra', 'Nissan', 4, 'Blue', 400.00,
        'unavailable', '2025-03-09 16:00:00'),
       ('GHI-3458', (SELECT id FROM drivers WHERE licenseNo = 'LIC029'), 'CR-V', 'Honda', 4, 'Green', 500.00,
        'available', '2025-03-10 17:00:00'),
       ('JKL-7892', (SELECT id FROM drivers WHERE licenseNo = 'LIC030'), 'RAV4', 'Toyota', 4, 'Black', 300.00,
        'available', '2025-03-11 18:00:00'),
       ('MNO-1237', (SELECT id FROM drivers WHERE licenseNo = 'LIC031'), 'Fit', 'Honda', 4, 'White', 450.00,
        'unavailable', '2025-02-10 09:00:00'),
       ('PQR-5671', (SELECT id FROM drivers WHERE licenseNo = 'LIC032'), 'Highlander', 'Toyota', 4, 'Red', 350.00,
        'available', '2025-02-11 10:00:00'),
       ('STU-9015', (SELECT id FROM drivers WHERE licenseNo = 'LIC033'), 'Pilot', 'Honda', 4, 'Silver', 400.00,
        'available', '2025-02-12 11:00:00'),
       ('VWX-3459', (SELECT id FROM drivers WHERE licenseNo = 'LIC034'), 'Maxima', 'Nissan', 4, 'Blue', 500.00,
        'unavailable', '2025-02-13 12:00:00'),
       ('YZA-7893', (SELECT id FROM drivers WHERE licenseNo = 'LIC035'), 'Corolla', 'Toyota', 4, 'Green', 300.00,
        'available', '2025-02-14 13:00:00'),
       ('BCD-1237', (SELECT id FROM drivers WHERE licenseNo = 'LIC036'), 'Civic', 'Honda', 4, 'Black', 450.00,
        'available', '2025-02-15 14:00:00'),
       ('EFG-5671', (SELECT id FROM drivers WHERE licenseNo = 'LIC037'), 'Camry', 'Toyota', 4, 'White', 350.00,
        'unavailable', '2025-02-16 15:00:00'),
       ('HIJ-9015', (SELECT id FROM drivers WHERE licenseNo = 'LIC038'), 'Accord', 'Honda', 4, 'Red', 400.00,
        'available', '2025-02-17 16:00:00'),
       ('KLM-3459', (SELECT id FROM drivers WHERE licenseNo = 'LIC039'), 'Prius', 'Toyota', 4, 'Silver', 500.00,
        'available', '2025-02-18 17:00:00'),
       ('NOP-7893', (SELECT id FROM drivers WHERE licenseNo = 'LIC040'), 'City', 'Honda', 4, 'Blue', 300.00,
        'unavailable', '2025-02-19 18:00:00'),
       ('QRS-1238', (SELECT id FROM drivers WHERE licenseNo = 'LIC041'), 'Avalon', 'Toyota', 4, 'Green', 450.00,
        'available', '2025-02-20 09:00:00'),
       ('TUV-5672', (SELECT id FROM drivers WHERE licenseNo = 'LIC042'), 'Insight', 'Honda', 4, 'Black', 350.00,
        'available', '2025-02-21 10:00:00'),
       ('WXY-9016', (SELECT id FROM drivers WHERE licenseNo = 'LIC043'), 'Altima', 'Nissan', 4, 'White', 400.00,
        'unavailable', '2025-02-22 11:00:00'),
       ('ZAB-3450', (SELECT id FROM drivers WHERE licenseNo = 'LIC044'), 'Sentra', 'Nissan', 4, 'Red', 500.00,
        'available', '2025-02-23 12:00:00'),
       ('CDE-7894', (SELECT id FROM drivers WHERE licenseNo = 'LIC045'), 'CR-V', 'Honda', 4, 'Silver', 300.00,
        'available', '2025-02-24 13:00:00'),
       ('FGH-1238', (SELECT id FROM drivers WHERE licenseNo = 'LIC046'), 'RAV4', 'Toyota', 4, 'Blue', 450.00,
        'unavailable', '2025-02-25 14:00:00'),
       ('IJK-5672', (SELECT id FROM drivers WHERE licenseNo = 'LIC047'), 'Fit', 'Honda', 4, 'Green', 350.00,
        'available', '2025-02-26 15:00:00'),
       ('LMN-9016', (SELECT id FROM drivers WHERE licenseNo = 'LIC048'), 'Highlander', 'Toyota', 4, 'Black', 400.00,
        'available', '2025-02-27 16:00:00'),
       ('OPQ-3450', (SELECT id FROM drivers WHERE licenseNo = 'LIC049'), 'Pilot', 'Honda', 4, 'White', 500.00,
        'unavailable', '2025-02-28 17:00:00'),
       ('RST-7894', (SELECT id FROM drivers WHERE licenseNo = 'LIC050'), 'Maxima', 'Nissan', 4, 'Red', 300.00,
        'available', '2025-03-01 18:00:00');

-- Insert Bookings (50 records)
INSERT INTO bookings (customerId, bookingDate, pickupLocation, destination, pickupTime, releaseTime, vehicleId, status,
                      distance, fare, discount, tax, netTotal, userId, createdAt)
VALUES
-- Past bookings (February 10 - March 10)
((SELECT id FROM customers WHERE registrationNo = 'REG001'), '2025-02-10 10:00:00', 'Airport', 'Hotel', '10:30:00',
 '11:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'ABC-1234'), 'completed', 15.50, 3100.00, 0.00, 250.00,
 3350.00, (SELECT id FROM users WHERE email = 'alice.j@megacitycab.com'), '2025-02-10 09:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG002'), '2025-02-11 11:30:00', 'Hotel', 'Mall', '12:00:00',
 '12:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'DEF-5678'), 'cancelled', 8.75, 2625.00, 100.00, 150.00,
 2675.00, (SELECT id FROM users WHERE email = 'bob.s@megacitycab.com'), '2025-02-11 10:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG003'), '2025-02-12 13:00:00', 'Office', 'Restaurant', '13:30:00',
 '14:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'JKL-3456'), 'completed', 5.25, 2100.00, 50.00, 100.00,
 2150.00, (SELECT id FROM users WHERE email = 'charlie.b@megacitycab.com'), '2025-02-12 11:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG004'), '2025-02-13 14:30:00', 'University', 'Library', '15:00:00',
 '15:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'MNO-7890'), 'completed', 3.80, 1900.00, 0.00, 80.00,
 1980.00, (SELECT id FROM users WHERE email = 'david.l@megacitycab.com'), '2025-02-13 12:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG005'), '2025-02-14 16:00:00', 'Station', 'Home', '16:30:00',
 '17:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'STU-5678'), 'cancelled', 12.30, 7380.00, 200.00, 200.00,
 7380.00, (SELECT id FROM users WHERE email = 'emma.w@megacitycab.com'), '2025-02-14 13:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG006'), '2025-02-15 17:30:00', 'Mall', 'Airport', '18:00:00',
 '18:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'VWX-9012'), 'completed', 16.20, 3240.00, 100.00, 260.00,
 3400.00, (SELECT id FROM users WHERE email = 'frank.m@megacitycab.com'), '2025-02-15 14:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG007'), '2025-02-16 19:00:00', 'Restaurant', 'Hotel', '19:30:00',
 '20:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'BCD-7890'), 'completed', 7.50, 2250.00, 50.00, 130.00,
 2330.00, (SELECT id FROM users WHERE email = 'grace.d@megacitycab.com'), '2025-02-16 15:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG008'), '2025-02-17 20:30:00', 'Cinema', 'Office', '21:00:00',
 '21:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'EFG-1235'), 'cancelled', 4.90, 1960.00, 0.00, 95.00,
 2055.00, (SELECT id FROM users WHERE email = 'hannah.w@megacitycab.com'), '2025-02-17 16:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG009'), '2025-02-18 22:00:00', 'Club', 'University', '22:30:00',
 '23:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'HIJ-5679'), 'completed', 6.70, 3350.00, 100.00, 120.00,
 3370.00, (SELECT id FROM users WHERE email = 'isaac.m@megacitycab.com'), '2025-02-18 17:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG010'), '2025-02-19 09:00:00', 'Hotel', 'Station', '09:30:00',
 '10:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'NOP-3457'), 'completed', 9.40, 5640.00, 150.00, 170.00,
 5660.00, (SELECT id FROM users WHERE email = 'alice.j@megacitycab.com'), '2025-02-19 18:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG011'), '2025-02-20 10:30:00', 'Airport', 'Mall', '11:00:00',
 '11:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'QRS-7891'), 'completed', 14.20, 2840.00, 0.00, 200.00,
 3040.00, (SELECT id FROM users WHERE email = 'bob.s@megacitycab.com'), '2025-02-20 09:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG012'), '2025-02-21 12:00:00', 'Hotel', 'Office', '12:30:00',
 '13:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'TUV-1235'), 'cancelled', 7.80, 2340.00, 50.00, 140.00,
 2430.00,
 (SELECT id FROM users WHERE email = 'charlie.b@megacitycab.com'), '2025-02-21 10:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG013'), '2025-02-22 13:30:00', 'Station', 'Restaurant', '14:00:00',
 '14:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'WXY-5679'), 'completed', 5.60, 2240.00, 0.00, 110.00,
 2350.00, (SELECT id FROM users WHERE email = 'david.l@megacitycab.com'), '2025-02-22 11:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG014'), '2025-02-23 15:00:00', 'Mall', 'Home', '15:30:00',
 '16:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'ZAB-9013'), 'completed', 11.30, 6780.00, 200.00, 180.00,
 6760.00, (SELECT id FROM users WHERE email = 'emma.w@megacitycab.com'), '2025-02-23 12:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG015'), '2025-02-24 16:30:00', 'Office', 'Airport', '17:00:00',
 '17:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'CDE-3457'), 'cancelled', 13.40, 2680.00, 100.00, 220.00,
 2800.00, (SELECT id FROM users WHERE email = 'frank.m@megacitycab.com'), '2025-02-24 13:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG016'), '2025-02-25 18:00:00', 'Hotel', 'Club', '18:30:00',
 '19:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'FGH-7891'), 'completed', 6.90, 3450.00, 50.00, 130.00,
 3530.00, (SELECT id FROM users WHERE email = 'grace.d@megacitycab.com'), '2025-02-25 14:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG017'), '2025-02-26 19:30:00', 'Restaurant', 'Station', '20:00:00',
 '20:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'IJK-1236'), 'completed', 8.20, 2460.00, 0.00, 150.00,
 2610.00, (SELECT id FROM users WHERE email = 'hannah.w@megacitycab.com'), '2025-02-26 15:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG018'), '2025-02-27 21:00:00', 'Mall', 'Hotel', '21:30:00',
 '22:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'LMN-5670'), 'completed', 10.50, 6300.00, 200.00, 160.00,
 6260.00, (SELECT id FROM users WHERE email = 'isaac.m@megacitycab.com'), '2025-02-27 16:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG019'), '2025-02-28 09:00:00', 'Office', 'Home', '09:30:00',
 '10:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'OPQ-9014'), 'completed', 7.10, 2130.00, 50.00, 120.00,
 2200.00, (SELECT id FROM users WHERE email = 'alice.j@megacitycab.com'), '2025-02-28 17:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG020'), '2025-03-01 10:30:00', 'Station', 'Mall', '11:00:00',
 '11:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'RST-3458'), 'completed', 9.80, 5880.00, 100.00, 170.00,
 5950.00, (SELECT id FROM users WHERE email = 'bob.s@megacitycab.com'), '2025-03-01 18:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG021'), '2025-03-02 12:00:00', 'Hotel', 'Airport', '12:30:00',
 '13:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'UVW-7892'), 'completed', 14.70, 2940.00, 0.00, 210.00,
 3150.00, (SELECT id FROM users WHERE email = 'charlie.b@megacitycab.com'), '2025-03-02 09:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG022'), '2025-03-03 13:30:00', 'Mall', 'Office', '14:00:00',
 '14:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'XYZ-1236'), 'completed', 6.40, 3200.00, 50.00, 140.00,
 3290.00, (SELECT id FROM users WHERE email = 'david.l@megacitycab.com'), '2025-03-03 10:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG023'), '2025-03-04 15:00:00', 'Restaurant', 'Home', '15:30:00',
 '16:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'ABC-5670'), 'completed', 11.90, 7140.00, 200.00, 180.00,
 7120.00, (SELECT id FROM users WHERE email = 'emma.w@megacitycab.com'), '2025-03-04 11:00:00'),
-- Last 7 days (March 5 - March 11)
((SELECT id FROM customers WHERE registrationNo = 'REG024'), '2025-03-05 16:30:00', 'Station', 'Hotel', '17:00:00',
 '17:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'DEF-9014'), 'completed', 8.30, 2490.00, 0.00, 150.00,
 2640.00, (SELECT id FROM users WHERE email = 'frank.m@megacitycab.com'), '2025-03-05 12:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG025'), '2025-03-06 18:00:00', 'Mall', 'Club', '18:30:00',
 '19:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'GHI-3458'), 'completed', 7.60, 3800.00, 100.00, 130.00,
 3830.00, (SELECT id FROM users WHERE email = 'grace.d@megacitycab.com'), '2025-03-06 13:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG026'), '2025-03-07 19:30:00', 'Office', 'Airport', '20:00:00',
 '20:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'JKL-7892'), 'completed', 12.40, 2480.00, 50.00, 200.00,
 2630.00, (SELECT id FROM users WHERE email = 'hannah.w@megacitycab.com'), '2025-03-07 14:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG027'), '2025-03-08 21:00:00', 'Hotel', 'Station', '21:30:00',
 '22:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'MNO-1237'), 'completed', 9.10, 5460.00, 150.00, 160.00,
 5470.00, (SELECT id FROM users WHERE email = 'isaac.m@megacitycab.com'), '2025-03-08 15:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG028'), '2025-03-09 09:00:00', 'Mall', 'Home', '09:30:00',
 '10:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'PQR-5671'), 'completed', 10.20, 5100.00, 0.00, 170.00,
 5270.00, (SELECT id FROM users WHERE email = 'alice.j@megacitycab.com'), '2025-03-09 16:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG029'), '2025-03-10 10:30:00', 'Office', 'Restaurant', '11:00:00',
 '11:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'STU-9015'), 'completed', 6.80, 2040.00, 50.00, 120.00,
 2110.00, (SELECT id FROM users WHERE email = 'bob.s@megacitycab.com'), '2025-03-10 17:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG030'), '2025-03-11 12:00:00', 'Hotel', 'Mall', '12:30:00',
 '13:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'VWX-3459'), 'confirmed', 8.90, 2670.00, 100.00, 140.00,
 2710.00, (SELECT id FROM users WHERE email = 'charlie.b@megacitycab.com'), '2025-03-11 18:00:00'),
-- Future bookings (March 12 - March 15)
((SELECT id FROM customers WHERE registrationNo = 'REG031'), '2025-03-12 13:30:00', 'Station', 'Airport', '14:00:00',
 '14:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'YZA-7893'), 'pending', 13.50, 2700.00, 0.00, 210.00,
 2910.00, (SELECT id FROM users WHERE email = 'david.l@megacitycab.com'), '2025-03-11 09:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG032'), '2025-03-13 15:00:00', 'Mall', 'Hotel', '15:30:00',
 '16:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'BCD-1237'), 'confirmed', 7.20, 3600.00, 50.00, 130.00,
 3680.00, (SELECT id FROM users WHERE email = 'emma.w@megacitycab.com'), '2025-03-11 10:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG033'), '2025-03-14 16:30:00', 'Office', 'Home', '17:00:00',
 '17:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'EFG-5671'), 'pending', 10.60, 6360.00, 200.00, 180.00,
 6340.00, (SELECT id FROM users WHERE email = 'frank.m@megacitycab.com'), '2025-03-11 11:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG034'), '2025-03-15 18:00:00', 'Restaurant', 'Station', '18:30:00',
 '19:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'HIJ-9015'), 'confirmed', 8.40, 2520.00, 0.00, 150.00,
 2670.00, (SELECT id FROM users WHERE email = 'grace.d@megacitycab.com'), '2025-03-11 12:00:00'),
-- Additional past bookings to reach 50
((SELECT id FROM customers WHERE registrationNo = 'REG035'), '2025-02-10 19:30:00', 'Hotel', 'Mall', '20:00:00',
 '20:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'KLM-3459'), 'completed', 9.70, 5820.00, 100.00, 160.00,
 5880.00, (SELECT id FROM users WHERE email = 'hannah.w@megacitycab.com'), '2025-02-10 13:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG036'), '2025-02-11 21:00:00', 'Office', 'Airport', '21:30:00',
 '22:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'NOP-7893'), 'completed', 12.80, 2560.00, 50.00, 200.00,
 2710.00, (SELECT id FROM users WHERE email = 'isaac.m@megacitycab.com'), '2025-02-11 14:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG037'), '2025-02-12 09:00:00', 'Station', 'Home', '09:30:00',
 '10:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'QRS-1238'), 'completed', 7.90, 4740.00, 0.00, 140.00,
 4880.00, (SELECT id FROM users WHERE email = 'alice.j@megacitycab.com'), '2025-02-12 15:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG038'), '2025-02-13 10:30:00', 'Mall', 'Hotel', '11:00:00',
 '11:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'TUV-5672'), 'cancelled', 10.10, 6060.00, 200.00, 170.00,
 6030.00, (SELECT id FROM users WHERE email = 'bob.s@megacitycab.com'), '2025-02-13 16:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG039'), '2025-02-14 12:00:00', 'Office', 'Restaurant', '12:30:00',
 '13:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'WXY-9016'), 'completed', 6.50, 1950.00, 50.00, 120.00,
 2020.00, (SELECT id FROM users WHERE email = 'charlie.b@megacitycab.com'), '2025-02-14 17:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG040'), '2025-02-15 13:30:00', 'Hotel', 'Station', '14:00:00',
 '14:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'ZAB-3450'), 'completed', 8.60, 2580.00, 0.00, 150.00,
 2730.00, (SELECT id FROM users WHERE email = 'david.l@megacitycab.com'), '2025-02-15 18:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG041'), '2025-02-16 15:00:00', 'Mall', 'Home', '15:30:00',
 '16:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'CDE-7894'), 'completed', 11.40, 6840.00, 100.00, 180.00,
 6920.00, (SELECT id FROM users WHERE email = 'emma.w@megacitycab.com'), '2025-02-16 09:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG042'), '2025-02-17 16:30:00', 'Office', 'Airport', '17:00:00',
 '17:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'FGH-1238'), 'cancelled', 13.20, 2640.00, 50.00, 200.00,
 2790.00, (SELECT id FROM users WHERE email = 'frank.m@megacitycab.com'), '2025-02-17 10:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG043'), '2025-02-18 18:00:00', 'Hotel', 'Mall', '18:30:00',
 '19:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'IJK-5672'), 'completed', 7.70, 3850.00, 0.00, 130.00,
 3980.00, (SELECT id FROM users WHERE email = 'grace.d@megacitycab.com'), '2025-02-18 11:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG044'), '2025-02-19 19:30:00', 'Station', 'Home', '20:00:00',
 '20:30:00', (SELECT id FROM vehicles WHERE licensePlate = 'LMN-9016'), 'completed', 9.90, 5940.00, 200.00, 160.00,
 5900.00, (SELECT id FROM users WHERE email = 'hannah.w@megacitycab.com'), '2025-02-19 12:00:00'),
((SELECT id FROM customers WHERE registrationNo = 'REG045'), '2025-02-20 21:00:00', 'Mall', 'Hotel', '21:30:00',
 '22:00:00', (SELECT id FROM vehicles WHERE licensePlate = 'OPQ-3450'), 'completed', 10.30, 6180.00, 50.00, 170.00,
 6300.00, (SELECT id FROM users WHERE email = 'isaac.m@megacitycab.com'), '2025-02-20 13:00:00');
