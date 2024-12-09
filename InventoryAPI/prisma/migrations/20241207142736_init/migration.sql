-- AlterTable
ALTER TABLE `pembelian` ADD COLUMN `status` ENUM('pending', 'accepted', 'rejected') NOT NULL DEFAULT 'pending';
