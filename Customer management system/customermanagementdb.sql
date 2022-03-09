-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 23, 2021 at 11:36 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `customermanagementdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `ID` int(150) NOT NULL,
  `First_Name` varchar(150) NOT NULL,
  `Last_Name` varchar(150) NOT NULL,
  `Middle_Name` varchar(150) NOT NULL,
  `Email` varchar(150) NOT NULL,
  `Date_of_Birth` date NOT NULL,
  `Address` varchar(150) NOT NULL,
  `Phone_Number` varchar(150) NOT NULL,
  `Gender` varchar(150) NOT NULL,
  `Country` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`ID`, `First_Name`, `Last_Name`, `Middle_Name`, `Email`, `Date_of_Birth`, `Address`, `Phone_Number`, `Gender`, `Country`) VALUES
(1, 'Manwell', 'Hanna', 'Mich', 'manwell.hanna@bhcc.edu', '1997-05-09', 'Boston MA', '7818569750', 'Male', 'Lebanon'),
(3, 'michel', 'hanna', 'manso', 'michel@hotmail.com', '2021-12-01', 'beirut', '70908148', 'male', 'lebanon');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Email` (`Email`),
  ADD UNIQUE KEY `Phone Number` (`Phone_Number`);
ALTER TABLE `customers` ADD FULLTEXT KEY `Address` (`Address`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `ID` int(150) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
