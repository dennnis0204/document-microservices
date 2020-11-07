-- -----------------------------------------------------
-- Schema document-app
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `document-app`;

CREATE SCHEMA `document-app`;
USE `document-app` ;

-- -----------------------------------------------------
-- Table `document-app`.`document`
-- -----------------------------------------------------

CREATE TABLE `document-app`.`document` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(36) NULL,
  `file_name` TEXT NULL,
  `digest_string` VARCHAR(64) NULL,
  `date_and_time` TIMESTAMP NULL,
  `path` TEXT NULL,
  `user` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)
ENGINE=InnoDB
AUTO_INCREMENT = 1;






