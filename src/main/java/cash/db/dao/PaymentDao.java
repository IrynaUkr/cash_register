package cash.db.dao;

import cash.entity.Payment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface PaymentDao extends BaseDao<Payment> {

    List<Payment> findPaymentByDate(Date date);

    boolean setFiscalStatusPayment();

}
