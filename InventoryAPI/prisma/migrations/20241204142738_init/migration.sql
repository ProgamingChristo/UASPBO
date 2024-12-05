-- CreateTable
CREATE TABLE `Pelanggan` (
    `PelangganID` INTEGER NOT NULL AUTO_INCREMENT,
    `NamaPelanggan` VARCHAR(255) NOT NULL,
    `Alamat` TEXT NOT NULL,
    `NomorTelepon` VARCHAR(15) NOT NULL,

    PRIMARY KEY (`PelangganID`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `Penjualan` (
    `PenjualanID` INTEGER NOT NULL AUTO_INCREMENT,
    `TanggalPenjualan` DATETIME(3) NOT NULL,
    `TotalHarga` DECIMAL(10, 2) NOT NULL,
    `PelangganID` INTEGER NOT NULL,

    PRIMARY KEY (`PenjualanID`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `DetailPenjualan` (
    `DetailID` INTEGER NOT NULL AUTO_INCREMENT,
    `PenjualanID` INTEGER NOT NULL,
    `ProdukID` INTEGER NOT NULL,
    `JumlahProduk` INTEGER NOT NULL,
    `Subtotal` DECIMAL(10, 2) NOT NULL,

    PRIMARY KEY (`DetailID`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `Produk` (
    `ProdukID` INTEGER NOT NULL AUTO_INCREMENT,
    `NamaProduk` VARCHAR(255) NOT NULL,
    `Harga` DECIMAL(10, 2) NOT NULL,
    `Stok` INTEGER NOT NULL,

    PRIMARY KEY (`ProdukID`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AddForeignKey
ALTER TABLE `Penjualan` ADD CONSTRAINT `Penjualan_PelangganID_fkey` FOREIGN KEY (`PelangganID`) REFERENCES `Pelanggan`(`PelangganID`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `DetailPenjualan` ADD CONSTRAINT `DetailPenjualan_PenjualanID_fkey` FOREIGN KEY (`PenjualanID`) REFERENCES `Penjualan`(`PenjualanID`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `DetailPenjualan` ADD CONSTRAINT `DetailPenjualan_ProdukID_fkey` FOREIGN KEY (`ProdukID`) REFERENCES `Produk`(`ProdukID`) ON DELETE RESTRICT ON UPDATE CASCADE;
