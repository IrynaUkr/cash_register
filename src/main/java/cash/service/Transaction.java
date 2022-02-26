package cash.service;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.manager.DBManager;
import cash.entity.OperationType;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import cash.exceptions.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Transaction {
    ProductDaoImpl productDao = new ProductDaoImpl();

    public static final String INSERT_INTO_RECEIPT = "INSERT INTO receipt ( number,status,type,id_user ) values (?,?,?,?)";
    public static final String SET_AMOUNT = "UPDATE product_has_receipt SET amount=? WHERE receipt_id_receipt =? and product_id_product =?";
    public static final String DELETE_PRODUCT = "DELETE FROM product_has_receipt WHERE product_id_product =? AND receipt_id_receipt =?;";
    public static final String SELECT_AMOUNT_FROM_PRODUCT = "SELECT MAX(amount) FROM product_has_receipt WHERE receipt_id_receipt = ? and product_id_product =?";
    public static final String INSERT_PRODUCT_AMOUNT = "INSERT INTO product_has_receipt (product_id_product, receipt_id_receipt,amount, price) VALUES (?,?,?,?)";

    public void saveReceiptToDB(Receipt receipt) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ArrayList<ReceiptProducts> products = receipt.getReceiptProducts();
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(INSERT_INTO_RECEIPT, Statement.RETURN_GENERATED_KEYS);
            mapReceipt(receipt, pstmt);
            int count = pstmt.executeUpdate();
            if (count > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        receipt.setId(generatedKeys.getInt(1));
                    }
                }
            }
            if (receipt.getType().equals(OperationType.SALE)) {
                for (ReceiptProducts rp : products) {
                    addProductForReceipt(connection, receipt.getId(), rp.getProductId(), rp.getAmount(), rp.getPrice());
                    reduceAmount(connection, rp.getProductId(), rp.getAmount());
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
            close(pstmt);
            close(connection);
        }
    }





    public void updateAmountReceipt(Receipt receipt, ReceiptProducts addReceiptProduct) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            Double oldAmount = getAmountByIdProdByIdReceipt(connection, receipt.getId(), addReceiptProduct.getProductId());
            Double newAmount = addReceiptProduct.getAmount();
            Double changeAm = oldAmount - newAmount;
            setUpdateProductAmount(connection, receipt.getId(), addReceiptProduct.getProductId(), newAmount);
            if (receipt.getType().equals(OperationType.SALE)) {
                increaseAmount(connection, addReceiptProduct.getProductId(), changeAm);
            } else {
                reduceAmount(connection, addReceiptProduct.getProductId(), changeAm);
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
            close(pstmt);
            close(connection);
        }
    }

    public void delProductFromReceipt(Receipt receipt, Product p) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            Double amountInReceipt = getAmountByIdProdByIdReceipt(connection, receipt.getId(), p.getId());
            delProd(connection, receipt.getId(), p.getId());
            if (receipt.getType().equals(OperationType.SALE)) {
                increaseAmount(connection, p.getId(), amountInReceipt);
            } else {
                reduceAmount(connection, p.getId(), amountInReceipt);
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

    public void setUpdateProductAmount(Connection con, Integer idReceipt, Integer idProduct, double newAmount) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(SET_AMOUNT);
        pstmt.setDouble(1, newAmount);
        pstmt.setInt(2, idReceipt);
        pstmt.setInt(3, idProduct);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void delProd(Connection con, Integer idReceipt, Integer idProduct) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(DELETE_PRODUCT);
        pstmt.setInt(1, idProduct);
        pstmt.setInt(2, idReceipt);
        pstmt.executeUpdate();
        pstmt.close();
    }


    public Double getAmountByIdProdByIdReceipt(Connection con, Integer idReceipt, Integer idProduct) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double result = 0.0;
        try {
            pstmt = con.prepareStatement(SELECT_AMOUNT_FROM_PRODUCT);
            pstmt.setInt(1, idReceipt);
            pstmt.setInt(2, idProduct);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(" amount in receipt was" + result);
                result = rs.getDouble(1);
            }
        } finally {
            pstmt.close();

        }
        System.out.println("idReceipt" + idReceipt + "idProd " + idProduct + "amount " + result);
        return result;
    }


    private void mapReceipt(Receipt receipt, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, receipt.getNumber());
        pstmt.setString(2, String.valueOf(receipt.getStatus()));
        pstmt.setString(3, String.valueOf(receipt.getType()));
        pstmt.setInt(4, receipt.getIdUser());

    }

    public void addProductForReceipt(Connection con, Integer idReceipt, Integer idProduct, double amount, double price) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(INSERT_PRODUCT_AMOUNT);
            pstmt.setInt(1, idProduct);
            pstmt.setInt(2, idReceipt);
            pstmt.setDouble(3, amount);
            pstmt.setDouble(4, price);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    public void reduceAmount(Connection con, Integer idProduct, double amount) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(" UPDATE product SET amount =(product.amount-?) WHERE id_product = ?");
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, idProduct);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    public void increaseAmount(Connection con, Integer idProduct, double amount) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(" UPDATE product SET amount =(product.amount+?) WHERE id_product = ?");
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, idProduct);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
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
