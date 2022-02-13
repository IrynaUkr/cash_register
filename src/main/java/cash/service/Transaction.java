package cash.service;

import cash.db.manager.DBManager;
import cash.entity.OperationType;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import java.sql.*;

public class Transaction {
    public static final String SET_RECEIPT_AMOUNT = "UPDATE product_has_receipt SET amount=?,  WHERE number = ?";
    public static final String SET_RECEIPT_TYPE = "UPDATE receipt SET type=?,  WHERE number = ?";
    public static final String SET_RECEIPT_STATUS = "UPDATE receipt SET status=?,  WHERE number = ?";
    public static final String SQL = "INSERT INTO receipt ( number,status,type ) values (?,?,?)";


    public void addNewReceipt(Receipt receipt, ReceiptProducts... products) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
                    addProductForReceipt(connection, receipt.getId(), rp.getProductId(), rp.getAmount());
                    reduceAmount(connection, rp.getProductId(), rp.getAmount());
                }
            } else {
                for (ReceiptProducts rp : products) {
                    addProductForReceipt(connection, receipt.getId(), rp.getProductId(), rp.getAmount());
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
            close(connection);
            close(pstmt);
        }
    }

    public void updateAmountReceipt(Receipt receipt, ReceiptProducts... products) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            for (ReceiptProducts rp : products) {
                Double oldAmount = getAmountByIdProdByIdReceipt(connection, receipt.getId(), rp.getProductId());
                Double newAmount = rp.getAmount();
                Double changeAm = oldAmount - newAmount;
                setUpdateProductAmount(connection, receipt.getId(), rp.getProductId(), newAmount);
                if (receipt.getType().equals(OperationType.SALE)) {
                    increaseAmount(connection, rp.getProductId(), changeAm);
                } else {
                    reduceAmount(connection, rp.getProductId(), changeAm);
                }
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
            close(pstmt);
        }
    }
    public void delProductFromReceipt(Receipt receipt, Product... products) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            for (Product p : products) {
                Double amountInReceipt = getAmountByIdProdByIdReceipt(connection, receipt.getId(), p.getId());
                delProd(connection, receipt.getId(), p.getId());
                if (receipt.getType().equals(OperationType.SALE)) {
                    increaseAmount(connection, p.getId(), amountInReceipt);
                } else {
                    reduceAmount(connection, p.getId(), amountInReceipt);
                }
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
            close(pstmt);
        }
    }
    public void setUpdateProductAmount(Connection con, Integer idReceipt, Integer idProduct, double newAmount) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement("UPDATE product_has_receipt SET amount=? WHERE receipt_id_receipt =? and product_id_product =?");
        pstmt.setDouble(1, newAmount);
        pstmt.setInt(2, idReceipt);
        pstmt.setInt(3, idProduct);
        pstmt.executeUpdate();
        pstmt.close();
    }
    public void delProd(Connection con, Integer idReceipt, Integer idProduct) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement("DELETE FROM product_has_receipt WHERE product_id_product =? AND receipt_id_receipt =?;");
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
            pstmt = con.prepareStatement("SELECT MAX(amount) FROM product_has_receipt WHERE receipt_id_receipt = ? and product_id_product =?");
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
    }

    public void addProductForReceipt(Connection con, Integer idReceipt, Integer idProduct, double amount) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO product_has_receipt (product_id_product, receipt_id_receipt,amount) VALUES (?,?,?)");
            pstmt.setInt(1, idProduct);
            pstmt.setInt(2, idReceipt);
            pstmt.setDouble(3, amount);
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
