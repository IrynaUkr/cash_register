DROP database IF EXISTS cashier;
CREATE SCHEMA IF NOT EXISTS `cashier`;
USE `cashier`;

DROP database IF EXISTS cashier;
CREATE SCHEMA IF NOT EXISTS `cashier`;
USE `cashier`;


CREATE TABLE IF NOT EXISTS `cashier`.`employee`
(
    `id_user`  INT           NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(16)   NULL DEFAULT NULL,
    `password` VARCHAR(1000) NULL DEFAULT NULL,
    `surname`  VARCHAR(255)  NULL DEFAULT NULL,
    `address`  VARCHAR(32)   NULL DEFAULT NULL,
    `phone`    VARCHAR(18)   NULL DEFAULT NULL,
    `email`    VARCHAR(45)   NULL DEFAULT NULL,
    `role`     VARCHAR(150),
    PRIMARY KEY (`id_user`)
)
;


CREATE TABLE IF NOT EXISTS `cashier`.`payment`
(
    `id_payment`  INT           NOT NULL AUTO_INCREMENT,
    `value`       DECIMAL(8, 2) NOT NULL DEFAULT '0.00',
    `id_user`     INT           NULL     DEFAULT NULL,
    `date`        DATE          NOT NULL DEFAULT (CURRENT_DATE),
    `status`      VARCHAR(45)   NULL,
    `type`        VARCHAR(50)   NULL,
    `description` VARCHAR(350)  NULL,
    PRIMARY KEY (`id_payment`),
    CONSTRAINT `fk_payment_user`
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
    `price`       DECIMAL(8, 2) NOT NULL DEFAULT '0.00',
    `amount`      DECIMAL(8, 2) NOT NULL DEFAULT '0',
    `uom`         VARCHAR(45)   NULL     DEFAULT NULL,
    PRIMARY KEY (`id_product`)
)
;

CREATE TABLE IF NOT EXISTS `cashier`.`receipt`
(
    `id_receipt` INT          NOT NULL AUTO_INCREMENT,
    `number`     VARCHAR(255) NULL     DEFAULT NULL,
    `date`       DATE         NOT NULL DEFAULT (CURRENT_DATE),
    `id_user`    INT          NULL     DEFAULT NULL,
    `status`     VARCHAR(45)  NULL,
    `type`       VARCHAR(45)  NULL,
    PRIMARY KEY (`id_receipt`),
    INDEX `fk_receipt_user` (`id_user` ASC) VISIBLE,
    CONSTRAINT `fk_receipt_user`
        FOREIGN KEY (`id_user`)
            REFERENCES `user` (`id_user`)
)
;
CREATE TABLE IF NOT EXISTS `product_has_receipt`
(
    id                   INT NOT NULL AUTO_INCREMENT,
    `product_id_product` INT NOT NULL,
    `receipt_id_receipt` INT NOT NULL,
    `amount`             INT,
    `price`              DOUBLE,
    PRIMARY KEY (id),
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
CREATE TABLE IF NOT EXISTS `cashier`.`language`
(
    `id_language` INT         NOT NULL AUTO_INCREMENT,
    `short_name`  VARCHAR(50) NULL DEFAULT NULL,
    `full_name`   VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (`id_language`)
)
;
CREATE TABLE IF NOT EXISTS `cashier`.`translate`
(
    `id_language`    INT          NOT NULL,
    `id_product`     INT          NOT NULL,
    `name_tr`        VARCHAR(145) NULL DEFAULT NULL,
    `description_tr` VARCHAR(145) NULL DEFAULT NULL,
    PRIMARY KEY (`id_language`, `id_product`),
    CONSTRAINT `fk_language_translate`
        FOREIGN KEY (`id_language`)
            REFERENCES `cashier`.`language` (`id_language`),
    CONSTRAINT `fk_product_translate`
        FOREIGN KEY (`id_product`)
            REFERENCES `cashier`.`product` (`id_product`)
            ON DELETE CASCADE
);

INSERT INTO employee (login, password, role, surname)
VALUES ('admin', '1', 'ADMIN', 'Mercury');
INSERT INTO employee (login, password, role, surname)
VALUES ('merch', '1', 'MERCHANDISER', 'Jupiter');
INSERT INTO employee (login, password, role, surname)
VALUES ('chief', '1', 'CHIEF_CASHIER', 'Saturn');
INSERT INTO employee (login, password, role, surname)
VALUES ('cashier', '1', 'CASHIER', 'Mars');

INSERT INTO language (id_lang, short_name, full_name)
VALUES ('en', 'English');
INSERT INTO language (id_lang, short_name, full_name)
VALUES ('ua', 'Ukrainian');
INSERT INTO language (id_lang, short_name, full_name)
VALUES ('ru', 'Russian');
USE cashier;