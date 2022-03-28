package cash.db;

public class ConstantQueryDB {
    public static final String COUNT_PRODUCT = "SELECT COUNT(DISTINCT id_product)  FROM product ";
    public static final String COUNT_RECEIPT = "SELECT COUNT(DISTINCT id_receipt)  FROM receipt ";
    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product  WHERE id_product = ?";
    public static final String SELECT_PRODUCT_BY_CODE = "SELECT * FROM product  WHERE code = ?";
    public static final String SELECT_PRODUCT_BY_CODE_LANG = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? and code=?";
    public static final String SELECT_PRODUCT_BY_NAME_LANG = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? and name_tr = ?";
    public static final String SELECT_FROM_PRODUCT = "SELECT * FROM product";
    public static final String SELECT_FROM_PRODUCT_BY_LANG = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=?";
    public static final String SELECT_FROM_PRODUCT_LIMIT = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? LIMIT ?, ?";
    public static final String SELECT_FROM_RECEIPT_LIMIT = "SELECT * FROM receipt  LIMIT ?, ?";
    public static final String INSERT_PRODUCT = "INSERT INTO product" +
            " (code, price, amount, uom) VALUES (?, ?, ?, ?)";
    public static final String SET_AMOUNT_PRODUCT = "UPDATE product SET  amount= ? WHERE id_product = ?";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM product WHERE id_product = ?";
    public static final String DELETE_PRODUCT_BY_CODE = "DELETE FROM product WHERE code = ?";
    public static final String GET_ID_BY_NAME_LANG = "select * from language where short_name= ? ";

    public static final String SELECT_RECEIPT_BY_ID = "SELECT * FROM receipt  WHERE id_receipt = ?";
    public static final String SELECT_RECEIPT_BY_STATUS = "SELECT * FROM receipt  WHERE status = ?";
    public static final String SELECT_RECEIPT_BY_NUMBER = "SELECT * FROM receipt  WHERE number = ?";
    public static final String SELECT_RETURN_BY_DATE = "SELECT receipt.id_receipt, receipt.type, receipt.status, receipt.number," +
            "sum(product_has_receipt.amount * product_has_receipt.price) as total, receipt.date FROM receipt JOIN product_has_receipt" +
            "   ON receipt.id_receipt=product_has_receipt.receipt_id_receipt WHERE  receipt.status ='closed' and date = ?" +
            "GROUP BY receipt.id_receipt";
    public static final String SELECT_ALL_RECEIPT = "SELECT * FROM receipt";
    public static final String SET_RECEIPT = "UPDATE receipt SET status=?  WHERE number = ?";
    public static final String DELETE_RECEIPT_BY_ID = "DELETE FROM receipt WHERE id_receipt = ?";
    public static final String SQL_JOIN_LANG = "SELECT product_has_receipt.amount, product_has_receipt.price," +
            " product.code, product.uom, translate.name_tr" +
            " FROM product_has_receipt" +
            " JOIN product" +
            " ON product_has_receipt.product_id_product = product.id_product" +
            " JOIN translate" +
            " ON translate.id_prod_tr=product.id_product" +
            " WHERE product_has_receipt.receipt_id_receipt=? and translate.id_lang_tr=?";
    public static final String RECEIPT_SET_FISCALISED = "UPDATE receipt SET status ='FISCALISED' WHERE receipt.status= 'CLOSED'";

    public static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payment  WHERE id_payment = ?";
    public static final String SELECT_PAYMENT_BY_DATE = "SELECT * FROM payment  WHERE payment.status = 'CLOSED' and payment.date = ?";
    public static final String SELECT_FROM_PAYMENT = "SELECT * FROM payment";
    public static final String INSERT_PAYMENT = "INSERT INTO payment (value, id_user, type,description) VALUES (?, ?, ?, ?)";
    public static final String DELETE_PAYMENT_BY_ID = "DELETE FROM payment WHERE id_payment = ?";
    public static final String PAYMENT_SET_FISCALISED = "UPDATE payment SET status ='FISCALISED' WHERE status= 'CLOSED'";


    public static final String INSERT_INTO_TRANSLATE = "INSERT INTO translate (id_prod_tr,id_lang_tr,name_tr,description_tr) VALUES(?, ?,?,?)";
    public static final String INSERT_INTO_RECEIPT = "INSERT INTO receipt ( number,status,type,id_user ) values (?,?,?,?)";
    public static final String SET_AMOUNT = "UPDATE product_has_receipt SET amount=? WHERE receipt_id_receipt =? and product_id_product =?";
    public static final String DELETE_PRODUCT = "DELETE FROM product_has_receipt WHERE product_id_product =? AND receipt_id_receipt =?;";
    public static final String SELECT_AMOUNT_FROM_PRODUCT = "SELECT MAX(amount) FROM product_has_receipt WHERE receipt_id_receipt = ? and product_id_product =?";
    public static final String INSERT_PRODUCT_AMOUNT = "INSERT INTO product_has_receipt (product_id_product, receipt_id_receipt,amount, price) VALUES (?,?,?,?)";
    public static final String INCREASE_AMOUNT = " UPDATE product SET amount =(product.amount+?) WHERE id_product = ?";
    public static final String DECREASE_AMOUNT = " UPDATE product SET amount =(product.amount-?) WHERE id_product = ?";

    public static final String SELECT_USER_BY_ID = "SELECT * FROM user  WHERE id_user = ?";
    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user  WHERE login = ?";
    public static final String SELECT_USER_BY_ROLE = "SELECT * FROM user  WHERE role = ?";
    public static final String SELECT_FROM_USER = "SELECT * FROM user";
    public static final String INSERT_USER = "INSERT INTO user (login, password, role, surname, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SET_USER = "UPDATE user SET login = ?, password = ?, role = ?, surname =? WHERE id_user =?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE id_user = ?";
}
