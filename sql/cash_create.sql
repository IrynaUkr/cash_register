DROP database IF EXISTS cashier;
CREATE SCHEMA IF NOT EXISTS `cashier`;
USE `cashier`;


CREATE TABLE IF NOT EXISTS `cashier`.`user`
(
    `id_user`         INT          NOT NULL AUTO_INCREMENT,
    `login`           VARCHAR(16)  NULL DEFAULT NULL,
    `password`        VARCHAR(32)  NULL DEFAULT NULL,
    `surname`         VARCHAR(255) NULL DEFAULT NULL,
    `adress`          VARCHAR(32)  NULL DEFAULT NULL,
    `phone_number`    VARCHAR(18)  NULL DEFAULT NULL,
    `email`           VARCHAR(45)  NULL DEFAULT NULL,
    `role`            VARCHAR(150),
    `user_details_id` INT,
    PRIMARY KEY (`id_user`)
)
;


CREATE TABLE IF NOT EXISTS `cashier`.`cash_flow`
(
    `id_cash_flow` INT           NOT NULL AUTO_INCREMENT,
    `value`        DECIMAL(5, 2) NOT NULL DEFAULT '0.00',
    `id_user`      INT           NULL     DEFAULT NULL,
    `date`         DATETIME      NULL,
    `type`         VARCHAR(50)   NULL,
    `cash_flowcol` VARCHAR(45)   NULL,
    PRIMARY KEY (`id_cash_flow`),
    INDEX `fk_cash_flow_user` (`id_user` ASC) VISIBLE,
    CONSTRAINT `fk_cash_flow_user`
        FOREIGN KEY (`id_user`)
            REFERENCES
                `user` (`id_user`)
)
;

CREATE TABLE IF NOT EXISTS `cashier`.`product`
(
    `id_product`  INT           NOT NULL AUTO_INCREMENT,
    `code`        VARCHAR(255)  NULL     DEFAULT NULL,
    `name`        VARCHAR(45)   NULL     DEFAULT NULL,
    `description` VARCHAR(250)  NULL     DEFAULT NULL,
    `price`       DECIMAL(5, 2) NOT NULL DEFAULT '0.00',
    `amount`      INT           NULL     DEFAULT '0',
    `uom`         VARCHAR(45)   NULL     DEFAULT NULL,
    PRIMARY KEY (`id_product`)
)
;

CREATE TABLE IF NOT EXISTS `cashier`.`receipt`
(
    `id_receipt` INT          NOT NULL AUTO_INCREMENT,
    `number`     VARCHAR(255) NULL DEFAULT NULL,
    `date`       DATETIME     NOT NULL,
    `id_user`    INT          NULL DEFAULT NULL,
    `check_type` VARCHAR(45)  NULL,
    PRIMARY KEY (`id_receipt`),
    INDEX `fk_recept_user` (`id_user` ASC) VISIBLE,
    CONSTRAINT `fk_recept_user`
        FOREIGN KEY (`id_user`)
            REFERENCES `user` (`id_user`)
)
;
CREATE TABLE IF NOT EXISTS `product_has_receipt`
(
    `product_id_product` INT NOT NULL,
    `receipt_id_receipt` INT NOT NULL,
    PRIMARY KEY (`product_id_product`, `receipt_id_receipt`),
    INDEX `fk_product_has_receipt_receipt1_idx` (`receipt_id_receipt` ASC) VISIBLE,
    INDEX `fk_product_has_receipt_product1_idx` (`product_id_product` ASC) VISIBLE,
    CONSTRAINT `fk_product_has_receipt_product1`
        FOREIGN KEY (`product_id_product`)
            REFERENCES `product` (`id_product`),
    CONSTRAINT `fk_product_has_receipt_receipt1`
        FOREIGN KEY (`receipt_id_receipt`)
            REFERENCES `receipt` (`id_receipt`)
            ON DELETE CASCADE
)
;
USE cashier;

