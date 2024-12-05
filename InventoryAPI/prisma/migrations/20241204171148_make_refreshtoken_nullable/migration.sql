/*
  Warnings:

  - Made the column `refreshtoken` on table `user` required. This step will fail if there are existing NULL values in that column.

*/
-- AlterTable
ALTER TABLE `user` MODIFY `refreshtoken` TEXT NOT NULL;
