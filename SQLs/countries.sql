-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 23, 2018 at 02:43 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `azan`
--

-- --------------------------------------------------------

--
-- Table structure for table `countries`
--

CREATE TABLE `countries` (
  `Name` varchar(100) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `countries`
--

INSERT INTO `countries` (`Name`) VALUES
('Albania'),
('Austria'),
('Belarus'),
('Belgium'),
('Bosnia and Herzegovina'),
('Bulgaria'),
('Croatia'),
('Czech Republic'),
('Denmark'),
('Estonia'),
('Finland'),
('France'),
('Germany'),
('Greece'),
('Hungary'),
('Ireland'),
('Italy'),
('Latvia'),
('Liechtenstein'),
('Lithuania'),
('Luxembourg'),
('Macedonia (Former Yugoslav Republic of Macedonia)'),
('Malta'),
('Montenegro'),
('Netherlands'),
('Norway'),
('Poland'),
('Romania'),
('Russia'),
('Serbia'),
('Slovakia'),
('Slovenia'),
('Spain'),
('Sweden'),
('Switzerland'),
('Ukraine');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `countries`
--
ALTER TABLE `countries`
  ADD PRIMARY KEY (`Name`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
