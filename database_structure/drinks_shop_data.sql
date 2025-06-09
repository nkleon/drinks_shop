-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2025 at 07:34 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `drinks_shop`
--
CREATE DATABASE IF NOT EXISTS `drinks_shop` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `drinks_shop`;

--
-- Dumping data for table `branches`
--

INSERT INTO `branches` (`branch_id`, `branch_name`) VALUES
(5, 'Eldoret'),
(3, 'KISUMU'),
(2, 'MOMBASA'),
(1, 'NAIROBI'),
(4, 'NAKURU');

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `customer_email`, `customer_name`, `customer_phone`, `customer_password`) VALUES
(1, 'john@email.com', 'John', '1234512345', '12345678'),
(2, 'jane@email.com', 'Jane', '6789067890', '87654321'),
(3, 'alice@email.com', 'Alice', '1357913579', '09876543'),
(4, 'alex@email.com', 'Alex', '2468024680', '34567890'),
(5, 'saul@email.com', 'Saul', '5432154321', '13579135'),
(6, 'sasha@email.com', 'Sasha', '0987609876', '24680246'),
(7, 'brandon@email.com', 'Brandon', '9753197531', '14703692'),
(8, 'brenda@email.com', 'Brenda', '0864208642', '25814703');

--
-- Dumping data for table `drinks`
--

INSERT INTO `drinks` (`drink_id`, `drink_name`, `drink_price`) VALUES
(1, 'Plain water', 50),
(2, 'Tonic water', 150),
(3, 'Orange Soda', 70),
(4, 'Pineapple soda', 70),
(5, 'Energy drink', 150),
(6, 'Apple juice', 100);

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `order_date`, `branch_id`, `customer_id`, `drink_id`, `order_quantity`) VALUES
(1, '2025-05-05', 1, 2, 3, 1),
(2, '2025-05-05', 2, 3, 4, 2),
(3, '2025-05-06', 3, 4, 5, 3),
(4, '2025-05-06', 4, 5, 6, 2),
(5, '2025-05-07', 1, 6, 1, 2),
(6, '2025-05-07', 2, 1, 2, 1),
(7, '2025-05-08', 1, 6, 1, 2),
(8, '2025-05-08', 4, 5, 6, 3);

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`drink_id`, `branch_id`, `drink_stock`) VALUES
(1, 1, 20),
(1, 2, 20),
(1, 3, 20),
(1, 4, 20),
(2, 1, 20),
(2, 2, 20),
(2, 3, 20),
(2, 4, 20),
(3, 1, 20),
(3, 2, 20),
(3, 3, 20),
(3, 4, 20),
(4, 1, 20),
(4, 2, 20),
(4, 3, 20),
(4, 4, 20);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
