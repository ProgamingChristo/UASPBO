-- CreateTable
CREATE TABLE `pembelian` (
    `pembelianID` INTEGER NOT NULL AUTO_INCREMENT,
    `Tanggalpembelian` DATETIME(3) NOT NULL,
    `TotalHarga` DECIMAL(10, 2) NOT NULL,
    `PelangganID` INTEGER NOT NULL,

    INDEX `pembelian_PelangganID_fkey`(`PelangganID`),
    PRIMARY KEY (`pembelianID`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `detailpembelian` (
    `DetailID` INTEGER NOT NULL AUTO_INCREMENT,
    `pembelianID` INTEGER NOT NULL,
    `ProdukID` INTEGER NOT NULL,
    `JumlahProduk` INTEGER NOT NULL,
    `Subtotal` DECIMAL(10, 2) NOT NULL,

    INDEX `DetailPembelian_PembelianID_fkey`(`pembelianID`),
    INDEX `DetailPembelian_ProdukID_fkey`(`ProdukID`),
    PRIMARY KEY (`DetailID`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AddForeignKey
ALTER TABLE `pembelian` ADD CONSTRAINT `pembelian_PelangganID_fkey` FOREIGN KEY (`PelangganID`) REFERENCES `pelanggan`(`PelangganID`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `detailpembelian` ADD CONSTRAINT `Detailpembelian_pembelianID_fkey` FOREIGN KEY (`pembelianID`) REFERENCES `pembelian`(`pembelianID`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `detailpembelian` ADD CONSTRAINT `Detailpembelian_ProdukID_fkey` FOREIGN KEY (`ProdukID`) REFERENCES `produk`(`ProdukID`) ON DELETE RESTRICT ON UPDATE CASCADE;
