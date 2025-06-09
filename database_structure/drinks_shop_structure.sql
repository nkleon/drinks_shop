-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2025 at 07:33 PM
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

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
CREATE TABLE IF NOT EXISTS `admins` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(10) NOT NULL,
  `admin_password` varchar(10) NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `admin_name_uq` (`admin_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `branches`
--

DROP TABLE IF EXISTS `branches`;
CREATE TABLE IF NOT EXISTS `branches` (
  `branch_id` int(11) NOT NULL AUTO_INCREMENT,
  `branch_name` varchar(50) NOT NULL,
  PRIMARY KEY (`branch_id`),
  UNIQUE KEY `branch_name` (`branch_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_email` varchar(30) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `customer_phone` varchar(10) NOT NULL,
  `customer_password` varchar(8) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `client_email` (`customer_email`),
  UNIQUE KEY `client_phone` (`customer_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `drinks`
--

DROP TABLE IF EXISTS `drinks`;
CREATE TABLE IF NOT EXISTS `drinks` (
  `drink_id` int(11) NOT NULL AUTO_INCREMENT,
  `drink_name` varchar(30) NOT NULL,
  `drink_price` int(11) NOT NULL,
  PRIMARY KEY (`drink_id`),
  UNIQUE KEY `drink_name` (`drink_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` date NOT NULL,
  `branch_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `drink_id` int(11) NOT NULL,
  `order_quantity` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `orders_to_branches_fk` (`branch_id`),
  KEY `orders_to_customers_fk` (`customer_id`),
  KEY `orders_to_drinks_fk` (`drink_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `drink_id` int(11) NOT NULL,
  `branch_id` int(11) NOT NULL,
  `drink_stock` int(11) NOT NULL,
  PRIMARY KEY (`drink_id`,`branch_id`),
  KEY `stock_to_branches_fk` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_to_branches_fk` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`),
  ADD CONSTRAINT `orders_to_customers_fk` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  ADD CONSTRAINT `orders_to_drinks_fk` FOREIGN KEY (`drink_id`) REFERENCES `drinks` (`drink_id`);

--
-- Constraints for table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `stock_to_branches_fk` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`),
  ADD CONSTRAINT `stock_to_drinks_fk` FOREIGN KEY (`drink_id`) REFERENCES `drinks` (`drink_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
