-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 23, 2018 at 02:44 PM
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
-- Table structure for table `times`
--

CREATE TABLE `times` (
  `Country` varchar(100) COLLATE utf8_bin NOT NULL,
  `City` varchar(100) COLLATE utf8_bin NOT NULL,
  `yyyy` int(11) NOT NULL,
  `mm` int(11) NOT NULL,
  `dd` int(11) NOT NULL,
  `Day` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `Imsak` varchar(7) COLLATE utf8_bin NOT NULL,
  `Sobh` varchar(7) COLLATE utf8_bin NOT NULL,
  `Tolo` varchar(7) COLLATE utf8_bin NOT NULL,
  `Zohr` varchar(7) COLLATE utf8_bin NOT NULL,
  `Asr` varchar(7) COLLATE utf8_bin NOT NULL,
  `Ghorob` varchar(7) COLLATE utf8_bin NOT NULL,
  `Maghreb` varchar(7) COLLATE utf8_bin NOT NULL,
  `Esha` varchar(7) COLLATE utf8_bin NOT NULL,
  `Midnight` varchar(7) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `times`
--
ALTER TABLE `times`
  ADD PRIMARY KEY (`Country`,`City`,`yyyy`,`mm`,`dd`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
