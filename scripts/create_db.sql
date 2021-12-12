-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema lab_schema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema lab_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lab_schema` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `lab_schema` ;

-- -----------------------------------------------------
-- Table `lab_schema`.`apartments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lab_schema`.`apartments` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `rooms_number` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE,
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `lab_schema`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lab_schema`.`roles` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `lab_schema`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lab_schema`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `password_hash` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(15) NULL DEFAULT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `User_Role_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `User_Role`
    FOREIGN KEY (`role_id`)
    REFERENCES `lab_schema`.`roles` (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `lab_schema`.`userorders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lab_schema`.`userorders` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `apartment_id` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `start_time` TIMESTAMP NOT NULL,
  `end_time` TIMESTAMP NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `order_user_idx` (`user_id` ASC) VISIBLE,
  INDEX `order_apartment_idx` (`apartment_id` ASC) VISIBLE,
  CONSTRAINT `order_apartment`
    FOREIGN KEY (`apartment_id`)
    REFERENCES `lab_schema`.`apartments` (`Id`),
  CONSTRAINT `order_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `lab_schema`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
