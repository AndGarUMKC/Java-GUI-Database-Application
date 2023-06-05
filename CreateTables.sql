CREATE TABLE `customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `current_address` VARCHAR(255) NOT NULL,
  `energy_tariff` VARCHAR(255) NOT NULL,
  `meter_type` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `meter_reading` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `reading_date` DATE NOT NULL,
  `reading_value` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);

CREATE TABLE `invoice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `invoice_date` DATE NOT NULL,
  `total_amount` DOUBLE NOT NULL,
  `paid` BOOLEAN NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);
