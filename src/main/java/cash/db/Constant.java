package cash.db;

import cash.entity.OperationType;

public class Constant {
    public static final String URL = "jdbc:mysql://localhost:3306/cashier_test";
    public static final String USER = "root";
    public static final String PASSWORD = "garden+use1982";

    public static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE IF NOT EXISTS `employee`" +
            "(" +
            "    `id_user`  INT           NOT NULL AUTO_INCREMENT," +
            "    `login`    VARCHAR(16)   NULL DEFAULT NULL," +
            "    `password` VARCHAR(1000) NULL DEFAULT NULL," +
            "    `surname`  VARCHAR(255)  NULL DEFAULT NULL," +
            "    `address`  VARCHAR(32)   NULL DEFAULT NULL," +
            "    `phone`    VARCHAR(18)   NULL DEFAULT NULL," +
            "    `email`    VARCHAR(45)   NULL DEFAULT NULL," +
            "    `role`     VARCHAR(150)," +
            "    PRIMARY KEY (`id_user`)" +
            ")";
    public static final String SET_EMPLOYEE =
            "INSERT INTO employee (`login`, `password`, `role`, `surname`)VALUES" +
                    " ('admin', '1', 'ADMIN', 'Mercury')," +
                    " ('merch', '4', 'MERCHANDISER', 'Venus')," +
                    " ('chief', '8', 'CHIEF_CASHIER', 'Moon')," +
                    "('cashier', '2', 'CASHIER', 'Mars') ; ";
    public static final String DROP_TABLE_EMPLOYEE = "DROP TABLE if exists employee";

    public static final String CREATE_TABLE_PAYMENT = "CREATE TABLE IF NOT EXISTS payment (" +
            "    `id_payment`  INT           NOT NULL AUTO_INCREMENT," +
            "    `value`       DECIMAL(8, 2) NOT NULL DEFAULT '0.00'," +
            "    `id_user`     INT           NULL     DEFAULT NULL," +
            "    `date`        DATE          NOT NULL DEFAULT (CURRENT_DATE)," +
            "    `status`      VARCHAR(45)   NULL," +
            "    `type`        VARCHAR(50)   NULL," +
            "    `description` VARCHAR(350)  NULL," +
            "    PRIMARY KEY (`id_payment`)," +
            "    CONSTRAINT `fk_payment_user`" +
            "        FOREIGN KEY (`id_user`)" +
            "            REFERENCES" +
            "                `employee` (`id_user`)" +
            ");";
    public static final String DROP_TABLE_PAYMENT = "DROP TABLE if exists payment";
    public static final String DROP_TABLE_PRODUCT = "DROP TABLE if exists product";
    public static final String DROP_TABLE_LANGUAGE = "DROP TABLE if exists language";
    public static final String DROP_TABLE_TRANSLATE = "DROP TABLE if exists translate";
    public static final String SET_PAYMENT =
            "INSERT INTO payment(value, type,description,id_user, status) VALUES" +
                    "(10000, 'SERVICE_CASH_INFLOW', 'exchange',2, 'CREATED')," +
                    "(5000, 'SERVICE_CASH_OUTFLOW', 'encashment',3, 'CLOSED')";
    public static final String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS `product`" +
            "(" +
            "    `id_product`  INT           NOT NULL AUTO_INCREMENT," +
            "    `code`        VARCHAR(255)  NULL     DEFAULT NULL," +
            "    `price`       DECIMAL(8, 2) NOT NULL DEFAULT '0.00'," +
            "    `amount`      DECIMAL(8, 2) NOT NULL DEFAULT '0'," +
            "    `uom`         VARCHAR(45)   NULL     DEFAULT NULL," +
            "    PRIMARY KEY (`id_product`)" +
            ")" +
            ";";
    public static final String CREATE_TABLE_LANGUAGE = "CREATE TABLE IF NOT EXISTS `language`" +
            "(" +
            "    `id_lang` INT         NOT NULL AUTO_INCREMENT," +
            "    `short_name`  VARCHAR(50) NULL DEFAULT NULL," +
            "    `full_name`   VARCHAR(50) NULL DEFAULT NULL," +
            "    PRIMARY KEY (`id_lang`)" +
            ")" +
            ";";
    public static final String CREATE_TABLE_TRANSLATE = "CREATE TABLE IF NOT EXISTS `translate`" +
            "(" +
            "    `id_lang_tr`    INT          NOT NULL," +
            "    `id_prod_tr`     INT          NOT NULL," +
            "    `name_tr`        VARCHAR(145) NULL DEFAULT NULL," +
            "    `description_tr` VARCHAR(145) NULL DEFAULT NULL," +
            "    PRIMARY KEY (`id_lang_tr`, `id_prod_tr`)," +
            "    CONSTRAINT `fk_language_translate`" +
            "        FOREIGN KEY (`id_lang_tr`)" +
            "            REFERENCES `language` (`id_lang`)," +
            "    CONSTRAINT `fk_product_translate`" +
            "        FOREIGN KEY (`id_prod_tr`)" +
            "            REFERENCES `product` (`id_product`)" +
            "            ON DELETE CASCADE" +
            ");";
    public static final String SET_PRODUCT = "INSERT INTO product(code,price,amount,uom) VALUES" +
            "('A123', 100, 5, 'kg')," +
            "('B456',300, 10, 'kg');";
    public static final String SET_LANGUAGE = "INSERT INTO language (id_lang, short_name, full_name) VALUES" +
            "(1, 'en', 'English')," +
            "(2, 'ua', 'Ukrainian')," +
            "(3, 'ru', 'Russian');" ;
    public static final String SET_TRANSLATE ="INSERT INTO translate " +
            "(id_lang_tr, id_prod_tr, name_tr, description_tr) VALUES"+
            "(1, 1,'apple','fruit'),"+
           "(2, 1,'яблуко','фрукт'),"+
            "(1, 2,'cherry','berry'),"+
            "(2, 2,'вишні','ягоди');";



}