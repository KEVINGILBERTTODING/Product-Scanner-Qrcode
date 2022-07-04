-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2022 at 01:58 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qrcode`
--

-- --------------------------------------------------------

--
-- Table structure for table `data_barang`
--

CREATE TABLE `data_barang` (
  `kode` varchar(50) NOT NULL,
  `nama_barang` varchar(80) NOT NULL,
  `harga` int(10) NOT NULL,
  `jumlah` int(10) NOT NULL,
  `satuan` varchar(50) NOT NULL,
  `image` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_barang`
--

INSERT INTO `data_barang` (`kode`, `nama_barang`, `harga`, `jumlah`, `satuan`, `image`) VALUES
('32h23sda', 'sepeda motor', 23000000, 2, 'unit', '32h23sda.png'),
('b0909', 'speaker', 500000, 67, 'unit', 'b0909.png'),
('B23921', 'AirPods Pro', 3500000, 74, 'Unit', 'B23921.png'),
('B343990', 'Apple Watch S7 Black', 7000000, 85, 'Unit', 'B343990.png'),
('b732932', 'charger', 320000, 87, 'unit', 'b732932.png'),
('B7823', 'MacBook Pro 14', 19000000, 44, 'Unit', 'B7823.png'),
('b8023911', 'iPhone 13 Pro Max', 18000000, 23, 'Unit', 'b8023911.png'),
('B93321', 'AirPods Pro Max', 5200000, 58, 'Unit', 'B93321.png'),
('BA82212', 'Apple Watch S7 Beige', 7000000, 45, 'Unit', 'BA82212.png'),
('bh6327', 'Jacket', 2000000, 23, 'Unit', ''),
('sadasd', 'air minum', 30000, 23, 'unit', 'sadasd.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `data_barang`
--
ALTER TABLE `data_barang`
  ADD PRIMARY KEY (`kode`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
