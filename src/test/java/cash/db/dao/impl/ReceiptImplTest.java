package cash.db.dao.impl;

import cash.db.manager.DBManager;
import cash.entity.OperationStatus;
import cash.entity.OperationType;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import org.junit.BeforeClass;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/* to integrate Mockito with the JUnit 5 extension model use @ExtendWith*/
@ExtendWith(MockitoExtension.class)
public class ReceiptImplTest {
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();
    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

    public ReceiptImplTest() throws SQLException {
    }

    @Mock
    DBManager dbManager;

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
    public void findAllReceiptWithRestrictTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            int offset = 0;
            int expected = 2;
            List<Receipt> receipts = receiptDao.findAllReceiptWithRestrict(offset, expected);
            int actual = receipts.size();
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findAllReceiptWithSortingTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            List<Receipt> actual = receiptDao.viewAllReceiptsWithSorting(0, 3, "number");
            List<Receipt> expected = getReceipts();
            expected.sort(Comparator.comparing(Receipt::getNumber));
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findAllTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            List<Receipt> actual = receiptDao.findAll();
            actual.sort(Comparator.comparing(Receipt::getNumber));
            List<Receipt> expected = getReceipts();
            expected.sort(Comparator.comparing(Receipt::getNumber));
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findReceiptByIdTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Receipt expected = new Receipt("S1", 3, OperationStatus.CLOSED, OperationType.SALE);
            Receipt actual = receiptDao.findEntityById(1);
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void deleteTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Receipt fiscalasedReceipt = new Receipt("S1", 3, OperationStatus.FISCALISED, OperationType.SALE);
            Receipt closedReceipt = new Receipt("R1", 3, OperationStatus.CLOSED, OperationType.RETURN);
            closedReceipt.setId(2);
            assertFalse(receiptDao.delete(fiscalasedReceipt));
            assertTrue(receiptDao.delete(closedReceipt));
        }
    }

    @org.junit.jupiter.api.Test
    public void updateStatusTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Receipt closedReceipt = new Receipt("R1", 3, OperationStatus.CLOSED, OperationType.RETURN);
            assertTrue(receiptDao.updateStatus(OperationStatus.FISCALISED, closedReceipt));
        }
    }

    @org.junit.jupiter.api.Test
    public void findReceiptByStatusTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            List<Receipt> receipts = receiptDao.findEntityByStatus(OperationStatus.CLOSED);
            int actual = receipts.size();
            int expected = 2;
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findReceiptByNumberTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Receipt actual = receiptDao.findReceiptByNumber("S1");
            Receipt expected = new Receipt("S1", 3, OperationStatus.FISCALISED, OperationType.SALE);
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findReceiptByDateTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            List<Receipt> receiptByDate = receiptDao.findReceiptByDate(Date.valueOf(LocalDate.now()));
            assertNotNull(receiptByDate);
        }
    }

    @org.junit.jupiter.api.Test
    public void getListProductsByIdReceiptLANGTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            ArrayList<ReceiptProducts> listProductsByIdReceiptLANG = receiptDao.getListProductsByIdReceiptLANG(1, 1);
            int actual = listProductsByIdReceiptLANG.size();
            int expected = 2;
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void setFiscalStatusReceiptTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            assertTrue(receiptDao.setFiscalStatusReceipt());
        }
    }

    private List<Receipt> getReceipts() {
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(new Receipt("S1", 3, OperationStatus.FISCALISED, OperationType.SALE));
        receipts.add(new Receipt("R1", 3, OperationStatus.CLOSED, OperationType.RETURN));
        receipts.add(new Receipt("S2", 3, OperationStatus.CLOSED, OperationType.SALE));
        return receipts;
    }

    private void setUp(MockedStatic<DBManager> dbManagerMockedStatic) throws SQLException {
        dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
        Mockito.when(dbManager.getConnection()).thenReturn(connection);
        connection.createStatement().executeUpdate(DROP_TABLE_TRANSLATE);
        connection.createStatement().executeUpdate(DROP_TABLE_LANGUAGE);
        connection.createStatement().executeUpdate(DROP_TABLE_PRODUCT_HAS_RECEIPT);
        connection.createStatement().executeUpdate(DROP_TABLE_PRODUCT);
        connection.createStatement().executeUpdate(DROP_TABLE_RECEIPT);

        connection.createStatement().executeUpdate(CREATE_TABLE_PRODUCT);
        connection.createStatement().executeUpdate(CREATE_TABLE_LANGUAGE);
        connection.createStatement().executeUpdate(CREATE_TABLE_TRANSLATE);
        connection.createStatement().executeUpdate(CREATE_TABLE_RECEIPT);
        connection.createStatement().executeUpdate(CREATE_TABLE_PRODUCT_HAS_RECEIPT);

        connection.createStatement().executeUpdate(SET_PRODUCT);
        connection.createStatement().executeUpdate(SET_LANGUAGE);
        connection.createStatement().executeUpdate(SET_TRANSLATE);
        connection.createStatement().executeUpdate(SET_RECEIPT);
        connection.createStatement().executeUpdate(SET_RECEIPT_HAS_PRODUCT);
    }
}
