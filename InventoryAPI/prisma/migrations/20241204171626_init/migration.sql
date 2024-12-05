-- AlterTable
ALTER TABLE `user` ADD COLUMN `role` ENUM('user', 'kasir', 'admin') NOT NULL DEFAULT 'user';
