package cash.db.dao;

import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import java.sql.Connection;
import java.util.HashMap;


public interface TransactionDao {
    void saveReceiptToDB(Receipt receipt);

    void updateAmountReceipt(Receipt receipt, ReceiptProducts receiptProducts);

    void delProductFromReceipt(Receipt receipt, Product p);

    void setUpdateProductAmount(Connection con, Integer idReceipt,
                                Integer idProduct, double newAmount);

    void delProd(Connection con, Integer idReceipt, Integer idProduct);

    Double getAmountByIdProdByIdReceipt(Connection con,
                                        Integer idReceipt,
                                        Integer idProduct);

    void addProductForReceipt(Connection con, Integer idReceipt, Integer idProduct,
                              double amount, double price);

    void decreaseAmount(Connection con, Integer idProduct, double amount);

    void increaseAmount(Connection con, Integer idProduct, double amount);

    boolean createProductWithTranslate(Product product,
                                       HashMap<Integer, String> names,
                                       HashMap<Integer, String> descriptions);

    boolean addToDataBase(Product product, Connection con);

    boolean setNameDescription(int id_product, int id_lang, String name,
                               String description, Connection con);

}
