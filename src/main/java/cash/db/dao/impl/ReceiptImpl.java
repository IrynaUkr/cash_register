package cash.db.dao.impl;

import cash.db.dao.ReceiptDao;
import cash.db.manager.DBManager;
import cash.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReceiptImpl implements ReceiptDao {

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


    @Override
    public List<Receipt> findAll() {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_RECEIPT);
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return receipts;
    }

    private Receipt extractReceipt(ResultSet rs) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(rs.getInt("id_receipt"));
        receipt.setNumber(rs.getString("number"));
        receipt.setDate(rs.getDate("date"));
        receipt.setStatus(OperationStatus.valueOf(rs.getString("status")));
        receipt.setOperationType(OperationType.valueOf(rs.getString("type")));
        receipt.setSum(rs.getDouble("total"));
        return receipt;
    }

    @Override
    public Receipt findEntityById(Integer id) {
        Receipt receipt = new Receipt();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_RECEIPT_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                receipt = extractReceipt(rs);
            }
            close(rs);
            close(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receipt;
    }


    @Override
    public boolean delete(Receipt receipt) {
        if (receipt == null) {
            return false;
        }
        int id = receipt.getId();
        if (findEntityById(id) == null) {
            return false;
        } else {
            int executeUpdate = 0;
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(DELETE_RECEIPT_BY_ID)) {
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return executeUpdate > 0;
        }
    }

    @Override
    public boolean create(Receipt receipt) {
        return false;
    }


//update  Status by Number

    public boolean updateStatus(OperationStatus status, Receipt receipt) {
        if (receipt == null) {
            return false;
        }
        if (findEntityById(receipt.getId()) != null) {
            int result = 0;
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(SET_RECEIPT)) {
                pstmt.setString(1, String.valueOf(status));
                pstmt.setString(2, receipt.getNumber());
                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result > 0;
        } else {
            return false;
        }
    }


    public List<Receipt> findEntityByStatus(OperationStatus status) {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_RECEIPT_BY_STATUS)) {
            pstmt.setString(1, String.valueOf(status));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return receipts;
    }

    public Receipt findReceiptByNumber(String number) {
        Receipt receipt = new Receipt();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_RECEIPT_BY_NUMBER)) {
            pstmt.setString(1, number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                receipt = extractReceipt(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return receipt;
    }


    public List<Receipt> findReceiptByDate(Date date) {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_RETURN_BY_DATE)) {
            pstmt.setString(1, String.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return receipts;
    }


    public ArrayList<ReceiptProducts> getListProductsByIdReceiptLANG(Integer idReceipt, int id_lang) {
        ArrayList<ReceiptProducts> products = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_JOIN_LANG)) {
            pstmt.setInt(1, idReceipt);
            pstmt.setInt(2, id_lang);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public boolean setFiscalStatusReceipt() {
        int result = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(RECEIPT_SET_FISCALISED)) {
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    private ReceiptProducts extractProductLang(ResultSet rs) throws SQLException {
        ReceiptProducts receiptProduct = new ReceiptProducts();
        receiptProduct.setCode(rs.getString("code"));
        receiptProduct.setName(rs.getString("name_tr"));
        receiptProduct.setPrice(rs.getDouble("price"));
        receiptProduct.setAmount(rs.getDouble("amount"));
        receiptProduct.setUom(rs.getString("uom"));
        return receiptProduct;
    }
}
