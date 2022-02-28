package cash.db.dao.impl;

import cash.db.manager.DBManager;
import cash.entity.OperationType;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionDAO {


    public static final String INSERT_INTO_TRANSLATE = "INSERT INTO translate (id_prod_tr,id_lang_tr,name_tr,description_tr) VALUES(?, ?,?,?)";
    public static final String INSERT_INTO_RECEIPT = "INSERT INTO receipt ( number,status,type,id_user ) values (?,?,?,?)";
    public static final String SET_AMOUNT = "UPDATE product_has_receipt SET amount=? WHERE receipt_id_receipt =? and product_id_product =?";
    public static final String DELETE_PRODUCT = "DELETE FROM product_has_receipt WHERE product_id_product =? AND receipt_id_receipt =?;";
    public static final String SELECT_AMOUNT_FROM_PRODUCT = "SELECT MAX(amount) FROM product_has_receipt WHERE receipt_id_receipt = ? and product_id_product =?";
    public static final String INSERT_PRODUCT_AMOUNT = "INSERT INTO product_has_receipt (product_id_product, receipt_id_receipt,amount, price) VALUES (?,?,?,?)";
    public static final String INSERT_PRODUCT = "INSERT INTO product" +
            " (code,  price, amount, uom) VALUES (?, ?, ?, ?)";
    public static final String INCREASE_AMOUNT = " UPDATE product SET amount =(product.amount+?) WHERE id_product = ?";
    public static final String DECREASE_AMOUNT = " UPDATE product SET amount =(product.amount-?) WHERE id_product = ?";
    //save receipt to db and get its ID, set product,product's amount in related table "product has receipt"

    public void saveReceiptToDB(Receipt receipt) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;
        ArrayList<ReceiptProducts> products = receipt.getReceiptProducts();
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement(INSERT_INTO_RECEIPT, Statement.RETURN_GENERATED_KEYS);
            mapReceipt(receipt, pst);
            int count = pst.executeUpdate();
            if (count > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        receipt.setId(generatedKeys.getInt(1));
                    }
                }
            }
            if (receipt.getType().equals(OperationType.SALE)) {
                for (ReceiptProducts rp : products) {
                    addProductForReceipt(connection, receipt.getId(), rp.getProductId(), rp.getAmount(), rp.getPrice());
                    decreaseAmount(connection, rp.getProductId(), rp.getAmount());
                }
            } else {
                for (ReceiptProducts rp : products) {
                    addProductForReceipt(connection, receipt.getId(), rp.getProductId(), rp.getAmount(), rp.getPrice());
                    increaseAmount(connection, rp.getProductId(), rp.getAmount());
                }
            }
            connection.commit();
        } catch (SQLException e) {
            //log
            e.printStackTrace();
            connection.rollback();
            throw e;
        } finally {
            close(pst);
            close(connection);
        }
    }


    public void updateAmountReceipt(Receipt receipt, ReceiptProducts addReceiptProduct) {
        Connection connection = null;

        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            Double oldAmount = getAmountByIdProdByIdReceipt(connection, receipt.getId(), addReceiptProduct.getProductId());
            Double newAmount = addReceiptProduct.getAmount();
            double changeAm = oldAmount - newAmount;
            setUpdateProductAmount(connection, receipt.getId(), addReceiptProduct.getProductId(), newAmount);
            if (receipt.getType().equals(OperationType.SALE)) {
                increaseAmount(connection, addReceiptProduct.getProductId(), changeAm);
            } else {
                decreaseAmount(connection, addReceiptProduct.getProductId(), changeAm);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {

            close(connection);
        }
    }

    //method deletes product from receipt and changes amount of product on the store
    public void delProductFromReceipt(Receipt receipt, Product p) {
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            Double amountInReceipt = getAmountByIdProdByIdReceipt(connection, receipt.getId(), p.getId());
            delProd(connection, receipt.getId(), p.getId());
            if (receipt.getType().equals(OperationType.SALE)) {
                increaseAmount(connection, p.getId(), amountInReceipt);
            } else {
                decreaseAmount(connection, p.getId(), amountInReceipt);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            close(connection);
        }
    }

    public void setUpdateProductAmount(Connection con,
                                       Integer idReceipt,
                                       Integer idProduct,
                                       double newAmount) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(SET_AMOUNT)) {
            pst.setDouble(1, newAmount);
            pst.setInt(2, idReceipt);
            pst.setInt(3, idProduct);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delProd(Connection con, Integer idReceipt, Integer idProduct) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(DELETE_PRODUCT)) {
            pst.setInt(1, idProduct);
            pst.setInt(2, idReceipt);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public Double getAmountByIdProdByIdReceipt(Connection con,
                                               Integer idReceipt,
                                               Integer idProduct) throws SQLException {

        double result = 0.0;
        try (PreparedStatement pst = con.prepareStatement(SELECT_AMOUNT_FROM_PRODUCT)) {
            pst.setInt(1, idReceipt);
            pst.setInt(2, idProduct);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
        }
        return result;
    }


    private void mapReceipt(Receipt receipt, PreparedStatement pst) throws SQLException {
        pst.setString(1, receipt.getNumber());
        pst.setString(2, String.valueOf(receipt.getStatus()));
        pst.setString(3, String.valueOf(receipt.getType()));
        pst.setInt(4, receipt.getIdUser());

    }

    public void addProductForReceipt(Connection con, Integer idReceipt, Integer idProduct,
                                     double amount, double price) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(INSERT_PRODUCT_AMOUNT)) {
            pst.setInt(1, idProduct);
            pst.setInt(2, idReceipt);
            pst.setDouble(3, amount);
            pst.setDouble(4, price);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //if we delete Product from SALE Receipt we have to decrease amount of product in the store
    public void decreaseAmount(Connection con, Integer idProduct, double amount) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(DECREASE_AMOUNT)) {
            pst.setDouble(1, amount);
            pst.setInt(2, idProduct);
            pst.executeUpdate();
        }
    }

    //if we delete Product from RETURN Receipt we have to increase amount of product on the store
    public void increaseAmount(Connection con, Integer idProduct, double amount) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(INCREASE_AMOUNT)) {
            pst.setDouble(1, amount);
            pst.setInt(2, idProduct);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createProductWithTranslate(Product product,
                                              HashMap<Integer, String> names,
                                              HashMap<Integer, String> descriptions) {
        boolean isCreateProduct = false;
        Connection connection = null;
        int amountLanguages = 3;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            isCreateProduct = addToDataBase(product, connection);
            for (int i = 1; i <= amountLanguages; i++) {
                System.out.println(product.getId() + i + names.get(i) + descriptions.get(i));
                isCreateProduct = setNameDescription(product.getId(), i, names.get(i), descriptions.get(i), connection);
            }
            connection.commit();

        } catch (SQLException e) {
            //log
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            close(connection);
        }
        return isCreateProduct;
    }

    public boolean addToDataBase(Product product, Connection con) throws SQLException {
        int result;
        try (PreparedStatement pst = con.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            new ProductDaoImpl().mapProduct(product, pst);
            result = pst.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setProductId(generatedKeys.getInt(1));
                    }
                }
            }
            return result > 0;
        }
    }

    public boolean setNameDescription(int id_product,
                                      int id_lang,
                                      String name,
                                      String description,
                                      Connection con) throws SQLException {
        boolean isSet = false;
        try (PreparedStatement pst = con.prepareStatement(INSERT_INTO_TRANSLATE)) {
            pst.setInt(1, id_product);
            pst.setInt(2, id_lang);
            pst.setString(3, name);
            pst.setString(4, description);
            isSet = pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSet;
    }


    void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

    void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            //log
        }
    }
}
