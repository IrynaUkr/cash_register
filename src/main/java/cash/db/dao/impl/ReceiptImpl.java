package cash.db.dao.impl;

import cash.db.dao.ReceiptDao;
import cash.db.manager.DBManager;
import cash.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReceiptImpl implements ReceiptDao {

    public static final String SELECT_RECEIPT_BY_ID = "SELECT * FROM receipt  WHERE id_receipt = ?";
    public static final String SELECT_RECEIPT_BY_TYPE = "SELECT * FROM receipt  WHERE type = ?";
    public static final String SELECT_RECEIPT_BY_STATUS = "SELECT * FROM receipt  WHERE status = ?";
    public static final String SELECT_RECEIPT_BY_NUMBER = "SELECT * FROM receipt  WHERE number = ?";
    public static final String SELECT_RECEIPT_BY_DATE = "SELECT * FROM receipt  WHERE date = ?";
    public static final String SELECT_ALL_RECEIPT = "SELECT * FROM receipt";
    public static final String SET_RECEIPT = "UPDATE receipt SET status=?  WHERE number = ?";
    public static final String DELETE_RECEIPT_BY_ID = "DELETE FROM receipt WHERE id_receipt = ?";


    @Override
    public List<Receipt> findAll() {
        List<Receipt> receipts = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_ALL_RECEIPT);
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
            stmt.close();
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
        receipt.setTotal(rs.getDouble("total"));
        return receipt;
    }

    @Override
    public Receipt findEntityById(Integer id) {
        Receipt receipt = new Receipt();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_RECEIPT_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                receipt = extractReceipt(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            PreparedStatement pstmt = null;
            Connection con = null;
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(DELETE_RECEIPT_BY_ID);
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return executeUpdate > 0;
        }
    }

    @Override
    public boolean delete(Integer id) {
        int executeUpdate = 0;
        PreparedStatement pstmt = null;
        Connection con = null;
        if (findEntityById(id) == null) {
            return false;
        } else {
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(DELETE_RECEIPT_BY_ID);
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
                pstmt.close();
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

    @Override
    public boolean update(Receipt receipt) {
        return false;
    }
//update  Status by Number

    public boolean updateStatus(OperationStatus status,Receipt receipt) {
        if (receipt == null) {
            throw new IllegalArgumentException();
        }
        receipt.getId();
        if (findEntityById(receipt.getId())!=null) {
            PreparedStatement pstmt = null;
            Connection con = null;
            int result = 0;
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(SET_RECEIPT);
                pstmt.setString(1, String.valueOf(status));
                pstmt.setString(2, receipt.getNumber());
                result = pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result > 0;
        } else {
            return false;
        }
    }


    private   void mapReceipt(Receipt receipt, PreparedStatement pstmt) throws SQLException {
        int k = 0;
        pstmt.setString(1, receipt.getNumber());
        pstmt.setString(2, String.valueOf(receipt.getStatus()));
        pstmt.setString(3, String.valueOf(receipt.getType()));
    }

    @Override
    public List<Receipt> findEntityByType(OperationType type) {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_RECEIPT_BY_TYPE);
            pstmt.setString(1, String.valueOf(type));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return receipts;
    }
    public List<Receipt> findEntityByStatus(OperationStatus status) {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_RECEIPT_BY_STATUS);
            pstmt.setString(1, String.valueOf(status));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return receipts;
    }
    public Receipt findReceiptByNumber(String number) {
        Receipt receipt= new Receipt();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_RECEIPT_BY_NUMBER);
            pstmt.setString(1,number);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                receipt = extractReceipt(rs);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println();
        return receipt;
    }
    public static String escapeForLike(String param) {
        return param
                .replace("!", "!!")
                .replace("%", "%!")
                .replace("_", "!_")
                .replace("[", "![")
                .replace("]", "!]");
    }


    @Override
    public List<Receipt> findEntityByDate(Date date) {
        List<Receipt> receipts = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_RECEIPT_BY_DATE);
            pstmt.setString(1, String.valueOf(date));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return receipts;
    }


}