package cash.db.dao.impl;

import cash.db.dao.PaymentDao;
import cash.db.manager.DBManager;
import cash.entity.OperationStatus;
import cash.entity.OperationType;
import cash.entity.Payment;
import cash.exceptions.DBException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static cash.db.ConstantQueryDB.*;

public class PaymentDaoImpl implements PaymentDao {
    private static final Logger logger = LogManager.getLogger(PaymentDaoImpl.class);



    public PaymentDaoImpl() {
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        logger.info("query: find all payments");
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_FROM_PAYMENT)) {
            while (rs.next()) {
                payments.add(extractPayment(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("cannot find payments", ex);
            throw new DBException("cannot find payments", ex);
        }
        return payments;
    }


    @Override
    public Payment findEntityById(Integer id) {
        Payment payment = null;
        logger.info("query: find payment by id");
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_PAYMENT_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                payment = extractPayment(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("payment by id was not found", ex);
            throw new DBException("payment by id was not found", ex);
        }
        return payment;
    }

    @Override
    public List<Payment> findPaymentByDate(Date date) {
        List<Payment> payments = new ArrayList<>();
        logger.info("query: find payment by date");
        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement pst = con.prepareStatement(SELECT_PAYMENT_BY_DATE)) {
            pst.setString(1, String.valueOf(date));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                payments.add(extractPayment(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("payment by date was not found", ex);
            throw new DBException("payment by date was not found", ex);
        }
        return payments;
    }

    @Override
    public boolean delete(Payment payment) {
        logger.info("query: delete paymnet");
        if (payment == null) {
            return false;
        } else {
            int id = payment.getId();
            int executeUpdate = 0;
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pst = con.prepareStatement(DELETE_PAYMENT_BY_ID)) {
                pst.setInt(1, id);
                executeUpdate = pst.executeUpdate();
            } catch (SQLException ex) {
                logger.error("payment was not delete");
                throw new DBException("payment was not delete", ex);
            }
            return executeUpdate > 0;
        }
    }

    @Override
    public boolean create(Payment payment) {
        logger.info("query: create payment");
        if (payment == null)
            throw new IllegalArgumentException();
        int result;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {
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
        } catch (SQLException ex) {
            logger.error("payment  was not create", ex);
            throw new DBException("payment  was not create", ex);
        }
        return result > 0;
    }

    @Override
    public boolean setFiscalStatusPayment() {
        logger.info("query:set fiscal status payment");
        int result = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(PAYMENT_SET_FISCALISED)) {
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("fiscal status payment  was not set", ex);
            throw new DBException(" fiscal status payment  was set", ex);
        }
        return result > 0;
    }

    private void mapPayment(Payment payment, PreparedStatement pst) throws SQLException {
        int k = 0;
        pst.setDouble(++k, payment.getValue());
        pst.setInt(++k, (payment.getIdUser()));
        pst.setString(++k, String.valueOf(payment.getType()));
        pst.setString(++k, payment.getDescription());
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
}

