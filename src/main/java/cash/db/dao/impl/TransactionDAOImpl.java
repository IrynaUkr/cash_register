package cash.db.dao.impl;

import cash.db.dao.TransactionDao;
import cash.db.manager.DBManager;
import cash.entity.OperationType;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import cash.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static cash.db.ConstantQueryDB.*;

public class TransactionDAOImpl implements TransactionDao {
    private static final Logger logger = LogManager.getLogger(TransactionDAOImpl.class);
    private static TransactionDAOImpl instance;

    private TransactionDAOImpl() {
    }

    public static TransactionDAOImpl getInstance() {
        if (instance == null) {
            return new TransactionDAOImpl();
        }
        return instance;
    }


    //save receipt to db and get its ID, set product,product's amount in related table "product has receipt"
    @Override
    public void saveReceiptToDB(Receipt receipt) {
        logger.info("query: save receipt to data base with list of products");
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
        } catch (SQLException ex) {
            rollback(connection);
            logger.error("Cannot execute operation", ex);
            throw new DBException("Cannot execute operation", ex);
        } finally {
            close(pst);
            close(connection);
        }
    }

    @Override
    public void updateAmountReceipt(Receipt receipt, ReceiptProducts receiptProducts) {
        logger.info("query: update amount of product in receipt  and change amount of product on the store");
        Connection connection = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            Double oldAmount = getAmountByIdProdByIdReceipt(connection, receipt.getId(), receiptProducts.getProductId());
            Double newAmount = receiptProducts.getAmount();
            double changeAm = oldAmount - newAmount;
            setUpdateProductAmount(connection, receipt.getId(), receiptProducts.getProductId(), newAmount);
            if (receipt.getType().equals(OperationType.SALE)) {
                increaseAmount(connection, receiptProducts.getProductId(), changeAm);
            } else {
                decreaseAmount(connection, receiptProducts.getProductId(), changeAm);
            }
            connection.commit();
        } catch (SQLException ex) {
            rollback(connection);
            logger.error("Cannot execute operation", ex);
            throw new DBException("Cannot execute operation", ex);
        } finally {
            close(connection);
        }
    }

    @Override
    public void delProductFromReceipt(Receipt receipt, Product p) {
        logger.info("query: delete  product in receipt and change amount of product on the store ");
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
        } catch (SQLException ex) {
            rollback(connection);
            logger.error("Cannot execute operation", ex);
            throw new DBException("Cannot execute operation", ex);
        } finally {
            close(connection);
        }
    }

    @Override
    public void setUpdateProductAmount(Connection con, Integer idReceipt,
                                       Integer idProduct, double newAmount) {
        logger.info("query: set new amount of  product in receipt");
        try (PreparedStatement pst = con.prepareStatement(SET_AMOUNT)) {
            pst.setDouble(1, newAmount);
            pst.setInt(2, idReceipt);
            pst.setInt(3, idProduct);
            pst.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot set new amount of  product in receipt", ex);
            throw new DBException("Cannot set new amount of  product in receipt", ex);
        }
    }

    @Override
    public void delProd(Connection con, Integer idReceipt, Integer idProduct) {
        logger.info("query: delete product from receipt");
        try (PreparedStatement pst = con.prepareStatement(DELETE_PRODUCT)) {
            pst.setInt(1, idProduct);
            pst.setInt(2, idReceipt);
            pst.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot delete product from receipt", ex);
            throw new DBException("Cannot delete of  product from receipt", ex);
        }
    }

    @Override
    public Double getAmountByIdProdByIdReceipt(Connection con, Integer idReceipt, Integer idProduct) {
        logger.info("query: get amount of product from receipt");
        double result = 0.0;
        try (PreparedStatement pst = con.prepareStatement(SELECT_AMOUNT_FROM_PRODUCT)) {
            pst.setInt(1, idReceipt);
            pst.setInt(2, idProduct);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            logger.error("Cannot get amount of product from receipt", ex);
            throw new DBException("Cannot get amount of product from receipt", ex);
        }
        return result;
    }


    private void mapReceipt(Receipt receipt, PreparedStatement pst) throws SQLException {
        pst.setString(1, receipt.getNumber());
        pst.setString(2, String.valueOf(receipt.getStatus()));
        pst.setString(3, String.valueOf(receipt.getType()));
        pst.setInt(4, receipt.getIdUser());

    }

    @Override
    public void addProductForReceipt(Connection con, Integer idReceipt, Integer idProduct,
                                     double amount, double price) {
        logger.info("query: add product to receipt");
        try (PreparedStatement pst = con.prepareStatement(INSERT_PRODUCT_AMOUNT)) {
            pst.setInt(1, idProduct);
            pst.setInt(2, idReceipt);
            pst.setDouble(3, amount);
            pst.setDouble(4, price);
            pst.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot add product to receipt", ex);
            throw new DBException("Cannot add product to receipt", ex);
        }
    }


    //if we delete Product from SALE Receipt we have to decrease amount of product in the store
    @Override
    public void decreaseAmount(Connection con, Integer idProduct, double amount) {
        logger.info("query: decrease amount of product in receipt");
        try (PreparedStatement pst = con.prepareStatement(DECREASE_AMOUNT)) {
            pst.setDouble(1, amount);
            pst.setInt(2, idProduct);
            pst.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot decrease amount of product in receipt", ex);
            throw new DBException("Cannot decrease amount of product in receipt", ex);
        }
    }

    //if we delete Product from RETURN Receipt we have to increase amount of product on the store
    @Override
    public void increaseAmount(Connection con, Integer idProduct, double amount) {
        logger.info("query: increase amount of product in receipt");
        try (PreparedStatement pst = con.prepareStatement(INCREASE_AMOUNT)) {
            pst.setDouble(1, amount);
            pst.setInt(2, idProduct);
            pst.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Cannot increase amount of product in receipt", ex);
            throw new DBException("Cannot increase amount of product in receipt", ex);
        }
    }

    @Override
    public boolean createProductWithTranslate(Product product,
                                              HashMap<Integer, String> names,
                                              HashMap<Integer, String> descriptions) {
        logger.info("query: create product with translate");
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
        } catch (SQLException ex) {
            rollback(connection);
            logger.error("Cannot execute operation", ex);
            throw new DBException("Cannot execute operation", ex);
        } finally {
            close(connection);
        }
        return isCreateProduct;
    }

    @Override
    public boolean addToDataBase(Product product, Connection con) {
        logger.info("query: add product to data base");
        int result = 0;
        try (PreparedStatement pst = con.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            ProductDaoImpl.getInstance().mapProduct(product, pst);
            result = pst.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setProductId(generatedKeys.getInt(1));
                    }
                } catch (SQLException ex) {
                    logger.error("Cannot generate id product", ex);
                    throw new DBException("Cannot generate id product", ex);
                }
            }
        } catch (SQLException ex) {
            logger.error("Cannot add product to data base", ex);
            throw new DBException("Cannot add product to data base", ex);
        }
        return result > 0;
    }

    @Override
    public boolean setNameDescription(int id_product, int id_lang, String name,
                                      String description, Connection con) {
        logger.info("query: set name description  to data base");
        boolean isSet;
        try (PreparedStatement pst = con.prepareStatement(INSERT_INTO_TRANSLATE)) {
            pst.setInt(1, id_product);
            pst.setInt(2, id_lang);
            pst.setString(3, name);
            pst.setString(4, description);
            isSet = pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            logger.error("Cannot set name description to data base", ex);
            throw new DBException("Cannot set name description to data base", ex);
        }
        return isSet;
    }


    void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("Cannot close statement", e);
            throw new DBException("Cannot close statement", e);
        }
    }

    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Cannot close connection", e);
            throw new DBException("Cannot close connection", e);
        }
    }

    private static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                logger.error("Cannot rollback", ex);
                throw new DBException("Cannot rollback", ex);
            }
        }
    }
}
