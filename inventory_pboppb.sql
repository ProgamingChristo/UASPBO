-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 04, 2024 at 10:08 PM
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
-- Database: `inventory_pboppb`
--

-- --------------------------------------------------------

--
-- Table structure for table `detailpembelian`
--

CREATE TABLE `detailpembelian` (
  `DetailID` int(11) NOT NULL,
  `pembelianID` int(11) NOT NULL,
  `ProdukID` int(11) NOT NULL,
  `JumlahProduk` int(11) NOT NULL,
  `Subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `detailpembelian`
--

INSERT INTO `detailpembelian` (`DetailID`, `pembelianID`, `ProdukID`, `JumlahProduk`, `Subtotal`) VALUES
(1, 1, 1, 2, 200000.00),
(2, 1, 2, 3, 300000.00);

-- --------------------------------------------------------

--
-- Table structure for table `detailpenjualan`
--

CREATE TABLE `detailpenjualan` (
  `DetailID` int(11) NOT NULL,
  `PenjualanID` int(11) NOT NULL,
  `ProdukID` int(11) NOT NULL,
  `JumlahProduk` int(11) NOT NULL,
  `Subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `detailpenjualan`
--

INSERT INTO `detailpenjualan` (`DetailID`, `PenjualanID`, `ProdukID`, `JumlahProduk`, `Subtotal`) VALUES
(1, 3, 1, 2, 200000.00),
(2, 3, 2, 3, 300000.00);

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `PelangganID` int(11) NOT NULL,
  `NamaPelanggan` varchar(255) NOT NULL,
  `Alamat` text NOT NULL,
  `NomorTelepon` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`PelangganID`, `NamaPelanggan`, `Alamat`, `NomorTelepon`) VALUES
(1, 'John Doe', 'Jl. Mawar No. 5', '081234567890'),
(2, 'John Doe', 'Jl. Mawar No. 5', '081234567890');

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

CREATE TABLE `pembelian` (
  `pembelianID` int(11) NOT NULL,
  `Tanggalpembelian` datetime(3) NOT NULL,
  `TotalHarga` decimal(10,2) NOT NULL,
  `PelangganID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pembelian`
--

INSERT INTO `pembelian` (`pembelianID`, `Tanggalpembelian`, `TotalHarga`, `PelangganID`) VALUES
(1, '2024-12-04 17:22:31.119', 500000.00, 2);

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `PenjualanID` int(11) NOT NULL,
  `TanggalPenjualan` datetime(3) NOT NULL,
  `TotalHarga` decimal(10,2) NOT NULL,
  `PelangganID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `penjualan`
--

INSERT INTO `penjualan` (`PenjualanID`, `TanggalPenjualan`, `TotalHarga`, `PelangganID`) VALUES
(3, '2024-12-04 17:22:27.594', 500000.00, 2);

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `ProdukID` int(11) NOT NULL,
  `NamaProduk` varchar(255) NOT NULL,
  `Harga` decimal(10,2) NOT NULL,
  `Stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`ProdukID`, `NamaProduk`, `Harga`, `Stok`) VALUES
(1, 'Produk B', 10021.00, 52),
(2, 'Produk A', 100000.00, 50),
(7, 'Produk B', 100000.00, 50),
(9, 'Produk B', 100000.00, 50),
(10, 'Produk B', 100000.00, 50);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` char(25) NOT NULL,
  `email` char(25) NOT NULL,
  `password` varchar(255) NOT NULL,
  `refreshtoken` text NOT NULL,
  `role` enum('user','kasir','admin') NOT NULL DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `email`, `password`, `refreshtoken`, `role`) VALUES
(4, 'admin', 'admin', '$2a$10$M4h4glpO.x5JernhgKIPa.kTYYRIhnPzWvQN4gjdGQEAH5PP.efMm', '', 'admin'),
(5, 'admin', 'admins', '$2a$10$F.x87/RWLp2dDFLadx1XMuu0ix3tMCXeApmmLbI8vHO4x0KYBH8ta', '', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `_prisma_migrations`
--

CREATE TABLE `_prisma_migrations` (
  `id` varchar(36) NOT NULL,
  `checksum` varchar(64) NOT NULL,
  `finished_at` datetime(3) DEFAULT NULL,
  `migration_name` varchar(255) NOT NULL,
  `logs` text DEFAULT NULL,
  `rolled_back_at` datetime(3) DEFAULT NULL,
  `started_at` datetime(3) NOT NULL DEFAULT current_timestamp(3),
  `applied_steps_count` int(10) UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `_prisma_migrations`
--

INSERT INTO `_prisma_migrations` (`id`, `checksum`, `finished_at`, `migration_name`, `logs`, `rolled_back_at`, `started_at`, `applied_steps_count`) VALUES
('0ad2ab7a-ca9a-4ab0-a04f-6157d6ee5378', 'c7825d0cbcf461cdb7083cc95782658d585c8beb6542798817db7ef49c28ca91', '2024-12-04 17:16:25.132', '20241204161419_init', NULL, NULL, '2024-12-04 17:16:24.970', 1),
('4c906da6-8769-4533-a0e6-88125c942c5c', '68f080b6ae3530647410855f240e0ec302bf57f72720ad25bedc6685a76324c6', '2024-12-04 17:16:25.224', '20241204171148_make_refreshtoken_nullable', NULL, NULL, '2024-12-04 17:16:25.192', 1),
('943316ca-05a4-43c5-aedf-8630d0f6d20a', '33be8e9267750c9c7809225e625bb20ee99215c5f03e9a693d85d0f21eb3efaa', '2024-12-04 17:16:26.145', '20241204171626_init', NULL, NULL, '2024-12-04 17:16:26.137', 1),
('cf1aede1-411f-4066-90e9-9802a95d7468', '57a598e98e869c646aa7041da170920870f7975b9909d0b8d45410f9b831b665', '2024-12-04 17:16:24.924', '20241204142738_init', NULL, NULL, '2024-12-04 17:16:24.791', 1),
('e34879dc-6da8-4542-aacd-261da3d25ba3', '25420a157e7910ee49227a2fe6abe8aec807dda08d9f71b8dbc8f797790a941b', '2024-12-04 17:16:25.191', '20241204171058_make_refreshtoken_nullable', NULL, NULL, '2024-12-04 17:16:25.134', 1),
('ee9f4b84-f23d-43ee-b8b9-5889b5c2fea7', '850d7c02a568fe9d3ab7f341c48ca7ffa2fdacfacf634756848784948e158437', '2024-12-04 17:16:24.968', '20241204145026_add_email_to_user', NULL, NULL, '2024-12-04 17:16:24.925', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detailpembelian`
--
ALTER TABLE `detailpembelian`
  ADD PRIMARY KEY (`DetailID`),
  ADD KEY `DetailPembelian_PembelianID_fkey` (`pembelianID`),
  ADD KEY `DetailPembelian_ProdukID_fkey` (`ProdukID`);

--
-- Indexes for table `detailpenjualan`
--
ALTER TABLE `detailpenjualan`
  ADD PRIMARY KEY (`DetailID`),
  ADD KEY `DetailPenjualan_PenjualanID_fkey` (`PenjualanID`),
  ADD KEY `DetailPenjualan_ProdukID_fkey` (`ProdukID`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`PelangganID`);

--
-- Indexes for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD PRIMARY KEY (`pembelianID`),
  ADD KEY `pembelian_PelangganID_fkey` (`PelangganID`);

--
-- Indexes for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`PenjualanID`),
  ADD KEY `Penjualan_PelangganID_fkey` (`PelangganID`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`ProdukID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `uniq email` (`email`);

--
-- Indexes for table `_prisma_migrations`
--
ALTER TABLE `_prisma_migrations`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `detailpembelian`
--
ALTER TABLE `detailpembelian`
  MODIFY `DetailID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `detailpenjualan`
--
ALTER TABLE `detailpenjualan`
  MODIFY `DetailID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pelanggan`
--
ALTER TABLE `pelanggan`
  MODIFY `PelangganID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pembelian`
--
ALTER TABLE `pembelian`
  MODIFY `pembelianID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `penjualan`
--
ALTER TABLE `penjualan`
  MODIFY `PenjualanID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `ProdukID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detailpembelian`
--
ALTER TABLE `detailpembelian`
  ADD CONSTRAINT `Detailpembelian_ProdukID_fkey` FOREIGN KEY (`ProdukID`) REFERENCES `produk` (`ProdukID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `Detailpembelian_pembelianID_fkey` FOREIGN KEY (`pembelianID`) REFERENCES `pembelian` (`pembelianID`) ON UPDATE CASCADE;

--
-- Constraints for table `detailpenjualan`
--
ALTER TABLE `detailpenjualan`
  ADD CONSTRAINT `DetailPenjualan_PenjualanID_fkey` FOREIGN KEY (`PenjualanID`) REFERENCES `penjualan` (`PenjualanID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `DetailPenjualan_ProdukID_fkey` FOREIGN KEY (`ProdukID`) REFERENCES `produk` (`ProdukID`) ON UPDATE CASCADE;

--
-- Constraints for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD CONSTRAINT `pembelian_PelangganID_fkey` FOREIGN KEY (`PelangganID`) REFERENCES `pelanggan` (`PelangganID`) ON UPDATE CASCADE;

--
-- Constraints for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD CONSTRAINT `Penjualan_PelangganID_fkey` FOREIGN KEY (`PelangganID`) REFERENCES `pelanggan` (`PelangganID`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
