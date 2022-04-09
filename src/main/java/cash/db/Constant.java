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
    public static final String SET_PAYMENT =
            "INSERT INTO payment(value, type,description,id_user, status) VALUES" +
                    "(10000, 'SERVICE_CASH_INFLOW', 'exchange',2, 'CREATED')," +
                    "(5000, 'SERVICE_CASH_OUTFLOW', 'encashment',3, 'CLOSED')";

// Payment payment = new Payment();
//        payment.setId(rs.getInt("id_payment"));
//        payment.setDate(rs.getDate("date"));
//        payment.setType(OperationType.valueOf(rs.getString("type")));
//        payment.setValue(rs.getDouble("value"));
//        payment.setDescription(rs.getString("description"));
//        payment.setIdUser(rs.getInt("id_user"));
//        payment.setStatus(OperationStatus.valueOf(rs.getString("status")));
}
