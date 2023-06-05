CREATE TABLE `customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `current_address` VARCHAR(255) NOT NULL,
  `energy_tariff` VARCHAR(255) NOT NULL,
  `meter_type` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `customer_consumption` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `days_used` DOUBLE NOT NULL,
  `reading_date` DATE NOT NULL,
  `reading_value` DOUBLE NOT NULL,
  `tariff_value` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);

CREATE TABLE `invoice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `invoice_date` DATE NOT NULL,
  `total_amount` DOUBLE NOT NULL,
  `paid` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);

CREATE TABLE kc_energycompany_admin (
id INT NOT NULL AUTO_INCREMENT,
username VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
PRIMARY KEY (id)
);

Select * From customer;
Select * From invoice;
Select * From meter_reading;
Select * From kc_energycompany_admin;


INSERT INTO kc_energycompany_admin (username, password, first_name, last_name)
VALUES ('KC_EnergyAdmin', 'Admin123', 'Andres', 'Garcia');




INSERT INTO customer (name, phone_number, current_address, energy_tariff, meter_type)
VALUES
('John Doe', '913-111-1111', '123 Main St', 'Standard', 'Analog'),
('Jane Smith', '816-222-2222', '456 Elm St', 'Solar', 'Digital'),
('Bob Johnson', '913-333-3333', '789 Oak St', 'Standard', 'Analog'),
('Sarah Davis', '816-444-4444', '321 Maple St', 'Wind', 'Digital'),
('Tom Wilson', '913-555-5555', '654 Pine St', 'Standard', 'Analog'),
('Emma Thompson', '816-666-6666', '987 Cedar St', 'Solar', 'Digital'),
('Mike Brown', '913-777-7777', '159 Walnut St', 'Standard', 'Analog'),
('Karen Garcia', '816-888-8888', '753 Birch St', 'Wind', 'Digital'),
('Steve Adams', '913-999-9999', '246 Spruce St', 'Standard', 'Analog'),
('Emily Scott', '816-111-1111', '369 Oak St', 'Solar', 'Digital'),
('Ryan Martinez', '913-222-2222', '582 Maple St', 'Standard', 'Analog'),
('Megan Taylor', '816-333-3333', '795 Pine St', 'Wind', 'Digital'),
('Jason Hernandez', '913-444-4444', '128 Cedar St', 'Standard', 'Analog'),
('Jennifer Lee', '816-555-5555', '357 Walnut St', 'Solar', 'Digital'),
('David Rodriguez', '913-666-6666', '864 Birch St', 'Standard', 'Analog'),
('Laura Clark', '816-777-7777', '951 Spruce St', 'Wind', 'Digital'),
('Kevin Davis', '913-888-8888', '246 Oak St', 'Standard', 'Analog'),
('Amy Perez', '816-999-9999', '579 Maple St', 'Solar', 'Digital'),
('Andrew Jackson', '913-111-1111', '803 Pine St', 'Standard', 'Analog'),
('Elizabeth Miller', '816-222-2222', '246 Cedar St', 'Wind', 'Digital'),
('Brandon Garcia', '913-333-3333', '579 Walnut St', 'Standard', 'Analog'),
('Lauren Martin', '816-444-4444', '802 Spruce St', 'Solar', 'Digital'),
('Brian Wilson', '913-555-5555', '135 Oak St', 'Standard', 'Analog'),
('Christine Thompson', '816-666-6666', '468 Maple St', 'Wind', 'Digital'),
('Alex Rodriguez', '913-777-7777', '791 Pine St', 'Standard', 'Analog'),
('Ashley Clark', '816-888-8888', '924 Cedar St', 'Solar', 'Digital'),
('Joshua Adams', '913-999-9999', '357 Walnut St', 'Standard', 'Analog'),
('Melissa Scott', '816-111-1111', '680 Birch St', 'Wind', 'Digital'),
('Matthew Hernandez', '913-222-2222', '913 Spruce St', 'Standard', 'Analog'),
('Emily Brown', '415-000-0000', '789 Oak Ave', 'Standard', 'Analog'),
('William Lee', '347-000-0000', '246 Maple Rd', 'Solar', 'Digital'),
('Lucy Garcia', '202-000-0000', '369 Pine St', 'Standard', 'Analog'),
('Daniel Hernandez', '631-000-0000', '258 Cedar Ave', 'Solar', 'Digital'),
('Sarah Thompson', '213-000-0000', '147 Walnut Ln', 'Standard', 'Analog'),
('David Johnson', '305-000-0000', '369 Cherry St', 'Solar', 'Digital'),
('Olivia Wilson', '617-000-0000', '789 Oak Ave', 'Standard', 'Analog'),
('Sofia Rodriguez', '415-000-0000', '456 Elm St', 'Solar', 'Digital'),
('Mia Anderson', '214-000-0000', '123 Main St', 'Standard', 'Analog'),
('Ethan Martin', '347-000-0000', '246 Maple Rd', 'Solar', 'Digital'),
('Avery Campbell', '408-000-0000', '369 Pine St', 'Standard', 'Analog'),
('James Parker', '202-000-0000', '258 Cedar Ave', 'Solar', 'Digital'),
('Liam Bailey', '631-000-0000', '147 Walnut Ln', 'Standard', 'Analog'),
('Madison Smith', '305-000-0000', '369 Cherry St', 'Solar', 'Digital'),
('Ava Brown', '617-000-0000', '789 Oak Ave', 'Standard', 'Analog'),
('Elijah Davis', '213-000-0000', '456 Elm St', 'Solar', 'Digital'),
('Emma Wilson', '214-000-0000', '123 Main St', 'Standard', 'Analog'),
('Noah Miller', '347-000-0000', '246 Maple Rd', 'Solar', 'Digital'),
('Abigail King', '408-000-0000', '369 Pine St', 'Standard', 'Analog'),
('Evelyn Wright', '202-000-0000', '258 Cedar Ave', 'Solar', 'Digital'),
('Mason Green', '631-000-0000', '147 Walnut Ln', 'Standard', 'Analog'),
('Sophia Taylor', '305-000-0000', '369 Cherry St', 'Solar', 'Digital'),
('Benjamin Adams', '617-000-0000', '789 Oak Ave', 'Standard', 'Analog'),
('Michael Scott', '213-000-0000', '456 Elm St', 'Solar', 'Digital'),
('Chloe Allen', '214-000-0000', '123 Main St', 'Standard', 'Analog'),
('Evelyn Davis', '347-000-0000', '246 Maple Rd', 'Solar', 'Digital'),
('Elizabeth Garcia', '408-000-0000', '369 Pine St', 'Standard', 'Analog'),
('Isabella Johnson', '202-000-0000', '258 Cedar Ave', 'Solar', 'Digital'),
('Alexander Taylor', '631-000-0000', '147 Walnut Ln', 'Standard', 'Analog'),
('Ella Miller', '305-000-0000', '369 Cherry St', 'Solar', 'Digital'),
('William Anderson', '617-000-0000', '789 Oak Ave', 'Standard', 'Analog'),
('Stephanie Lee', '816-333-3333', '146 Oak St', 'Solar', 'Digital');



INSERT INTO customer_consumption (customer_id, days_used, reading_date, reading_value, tariff_value)
VALUES
(1, 23.5, '2023-04-20', 935, 0.17),
(2, 28.2, '2023-03-12', 872, 0.1),
(3, 25.8, '2023-02-25', 938, 0.17),
(4, 21.1, '2023-01-17', 749, 0.15),
(5, 27.9, '2023-04-03', 1000, 0.17),
(6, 22.7, '2023-03-28', 1200, 0.1),
(7, 24.3, '2023-02-10', 700, 0.17),
(8, 29.6, '2023-01-22', 918, 0.15),
(9, 26.8, '2023-04-13', 825, 0.17),
(10, 20.5, '2023-03-04', 863, 0.1),
(11, 23.5, '2023-04-20', 735, 0.17),
(12, 28.2, '2023-03-12', 872, 0.1),
(13, 25.8, '2023-02-25', 938, 0.17),
(14, 21.1, '2023-01-17', 749, 0.15),
(15, 27.9, '2023-04-03', 1000, 0.17),
(16, 22.7, '2023-03-28', 1300, 0.1),
(17, 24.3, '2023-02-10', 700, 0.17),
(18, 29.6, '2023-01-22', 918, 0.15),
(19, 26.8, '2023-04-13', 825, 0.17),
(20, 20.5, '2023-03-04', 1163, 0.1),
(21, 23.5, '2023-04-20', 1235, 0.17),
(22, 28.2, '2023-03-12', 1872, 0.1),
(23, 25.8, '2023-02-25', 938, 0.17),
(24, 21.1, '2023-01-17', 1549, 0.15),
(25, 27.9, '2023-04-03', 1000, 0.17),
(26, 22.7, '2023-03-28', 1300, 0.1),
(27, 24.3, '2023-02-10', 700, 0.17),
(28, 29.6, '2023-01-22', 1918, 0.15),
(29, 26.8, '2023-04-13', 825, 0.17),
(30, 20.5, '2023-03-04', 1163, 0.1);

-- Generate mock data for the invoice table
INSERT INTO `invoice` (`customer_id`, `invoice_date`, `total_amount`, `paid`) VALUES
(1, '2023-04-25', 35, 'YES'),
(2, '2023-04-28', 20, 'NO'),
(3, '2023-04-19', 35, 'YES'),
(4, '2023-04-22', 20, 'YES'),
(5, '2023-04-26', 35, 'NO'),
(6, '2023-04-23', 20, 'NO'),
(7, '2023-04-27', 35, 'YES'),
(8, '2023-04-21', 20, 'NO'),
(9, '2023-04-24', 35, 'NO'),
(10, '2023-04-20', 20, 'YES'),
(11, '2023-04-25', 35, 'YES'),
(12, '2023-04-28', 20, 'NO'),
(13, '2023-04-19', 35, 'YES'),
(14, '2023-04-22', 20, 'YES'),
(15, '2023-04-26', 35, 'NO'),
(16, '2023-04-23', 20, 'NO'),
(17, '2023-04-27', 35, 'YES'),
(18, '2023-04-21', 20, 'NO'),
(19, '2023-04-24', 35, 'NO'),
(20, '2023-04-20', 20, 'YES'),
(21, '2023-04-25', 35, 'YES'),
(22, '2023-04-28', 20, 'NO'),
(23, '2023-04-19', 35, 'YES'),
(24, '2023-04-22', 20, 'YES'),
(25, '2023-04-26', 35, 'NO'),
(26, '2023-04-23', 20, 'NO'),
(27, '2023-04-27', 35, 'YES'),
(28, '2023-04-21', 20, 'NO'),
(29, '2023-04-24', 35, 'NO'),
(30, '2023-04-20', 20, 'YES');

