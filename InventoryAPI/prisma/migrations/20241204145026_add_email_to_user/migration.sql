-- CreateTable
CREATE TABLE `user` (
    `user_id` INTEGER NOT NULL AUTO_INCREMENT,
    `username` CHAR(25) NOT NULL,
    `email` CHAR(25) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `refreshtoken` TEXT NOT NULL,

    UNIQUE INDEX `uniq email`(`email`),
    PRIMARY KEY (`user_id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
