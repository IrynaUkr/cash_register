package cash.db.dao.impl;

import cash.db.manager.DBManager;
import cash.entity.OperationType;
import cash.entity.Payment;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static cash.db.Constant.*;
import static org.junit.jupiter.api.Assertions.*;

/* to integrate Mockito with the JUnit 5 extension model use @ExtendWith*/
@ExtendWith(MockitoExtension.class)
public class PaymentDaoImplTest {
    PaymentDaoImpl paymentDao = PaymentDaoImpl.getInstance();
    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);


    /* use the @Mock annotation to inject a mock for an instance variable that we can use anywhere in the test class:*/
    @Mock
    DBManager dbManager;

    public PaymentDaoImplTest() throws SQLException {
    }

    @org.junit.jupiter.api.Test
    public void connectionTest() {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
            Mockito.when(dbManager.getConnection()).thenReturn(connection);
            Connection testConnection = DBManager.getInstance().getConnection();
            assertNotNull(testConnection);
        }
    }

    @org.junit.jupiter.api.Test
    public void findAllPaymentsTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            List<Payment> actual = paymentDao.findAll();
            actual.sort(Comparator.comparing(Payment::getValue));
            List<Payment> expected = getPayments();
            expected.sort(Comparator.comparing(Payment::getValue));
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findPaymentByIdTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Payment actual = paymentDao.findEntityById(1);
            Payment expected = new Payment(10000.0, 2, OperationType.SERVICE_CASH_INFLOW, "exchange");
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findPaymentByIdDate() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            List<Payment> paymentByDate = paymentDao.findPaymentByDate(Date.valueOf(LocalDate.now()));
            assertNotNull(paymentByDate);
        }
    }

    @org.junit.jupiter.api.Test
    public void deletePaymentTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Payment first = new Payment(10000.0, 2, OperationType.SERVICE_CASH_INFLOW, "exchange");
            first.setId(1);
            boolean delete = paymentDao.delete(first);
            assertTrue(delete);
        }
    }

    @org.junit.jupiter.api.Test
    public void createPaymentTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Payment third = new Payment(8888.0, 2, OperationType.SERVICE_CASH_INFLOW, "exchange");
            boolean create = paymentDao.create(third);
            assertTrue(create);
        }
    }
    @org.junit.jupiter.api.Test
    public void setFiscalStatusTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            assertTrue(paymentDao.setFiscalStatusPayment());
        }
    }


    private void setUp(MockedStatic<DBManager> dbManagerMockedStatic) throws SQLException {
        dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
        Mockito.when(dbManager.getConnection()).thenReturn(connection);
        connection.createStatement().executeUpdate(DROP_TABLE_PAYMENT);
        connection.createStatement().executeUpdate(CREATE_TABLE_PAYMENT);
        connection.createStatement().executeUpdate(SET_PAYMENT);
    }

    private List<Payment> getPayments() {
        List<Payment> allPayment = new ArrayList<>();
        allPayment.add(new Payment(10000.0, 2, OperationType.SERVICE_CASH_INFLOW, "exchange"));
        allPayment.add(new Payment(5000.0, 2, OperationType.SERVICE_CASH_OUTFLOW, "encashment"));
        return allPayment;
    }
}
