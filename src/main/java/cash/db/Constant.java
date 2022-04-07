package cash.db;

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
}
