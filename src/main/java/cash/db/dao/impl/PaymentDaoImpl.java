package cash.db.dao.impl;

import cash.db.dao.PaymentDao;
import cash.db.manager.DBManager;
import cash.entity.OperationStatus;
import cash.entity.OperationType;
import cash.entity.Payment;
import cash.exceptions.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {

    public static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payment  WHERE id_payment = ?";
    public static final String SELECT_PAYMENT_BY_DATE = "SELECT * FROM payment  WHERE payment.status = 'CLOSED' and payment.date = ?";
    public static final String SELECT_FROM_PAYMENT = "SELECT * FROM payment";
    public static final String INSERT_PAYMENT = "INSERT INTO payment (value, id_user, type,description) VALUES (?, ?, ?, ?)";
    public static final String DELETE_PAYMENT_BY_ID = "DELETE FROM payment WHERE id_payment = ?";

    public PaymentDaoImpl() {
    }


    @Override
    public List findAll() {
        List<Payment> payments = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_FROM_PAYMENT);
            while (rs.next()) {
                payments.add(extractPayment(rs));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return payments;
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id_payment"));
        payment.setDate(rs.getDate("date"));
        payment.setType(OperationType.valueOf(rs.getString("type")));
        payment.setValue(rs.getDouble("value"));
        payment.setDescription(rs.getString("description"));
        payment.setIdUser(rs.getInt("id_user"));
        payment.setStatus(OperationStatus.valueOf(rs.getString("status")));

        return payment;
    }

    @Override
    public Payment findEntityById(Integer id) {
        Payment payment = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_PAYMENT_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                payment = extractPayment(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return payment;
    }

    public List<Payment> findPaymentByDate(Date date) {
        List<Payment> payments = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement( SELECT_PAYMENT_BY_DATE);
            pstmt.setString(1, String.valueOf(date));
            rs = pstmt.executeQuery();
           while (rs.next()) {
                payments.add(extractPayment(rs));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return payments;
    }
    @Override
    public boolean delete(Payment payment) {
        if (payment == null) {
            return false;
        }
        int id = payment.getId();
        if (findEntityById(id) == null) {
            return false;
        } else {
            int executeUpdate = 0;
            PreparedStatement pstmt = null;
            Connection con = null;
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(DELETE_PAYMENT_BY_ID);
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return executeUpdate > 0;
        }
    }


    public boolean delete(Integer id) {
        return false;
    }


    @Override
    public boolean create(Payment payment) {
        if (payment == null)
            throw new IllegalArgumentException();
        int result;
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS);
            mapPayment(payment, pstmt);
            result = pstmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DBException("insert  was failed", e);
        }
        return result > 0;
    }
    private void mapPayment(Payment payment, PreparedStatement pstmt) throws SQLException {
        pstmt.setDouble(1, payment.getValue());
        pstmt.setInt(2, (payment.getIdUser()));
        pstmt.setString(3, String.valueOf(payment.getType()));
        pstmt.setString(4, payment.getDescription());
    }

    @Override
    public boolean update(Payment payment) {
        return false;
    }
}
