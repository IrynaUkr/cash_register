package cash.db.dao.impl;

import cash.db.manager.DBManager;
import cash.entity.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static cash.db.Constant.*;
import static org.junit.jupiter.api.Assertions.*;

/* to integrate Mockito with the JUnit 5 extension model use @ExtendWith*/
@ExtendWith(MockitoExtension.class)
public class TransactionDaoImplTest {
    TransactionDAOImpl transactionDAO = TransactionDAOImpl.getInstance();
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();
    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

    public TransactionDaoImplTest() throws SQLException {
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
    public void saveReceiptToDBTest() throws SQLException {
        ArrayList<ReceiptProducts> receiptProducts = new ArrayList<>();
        receiptProducts.add(new ReceiptProducts(1, 10));
        receiptProducts.add(new ReceiptProducts(2, 20));
        Receipt expected = new Receipt("rec1", 2, OperationStatus.CLOSED, OperationType.SALE);
        expected.setReceiptProducts(receiptProducts);
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            setSql();
            boolean saveReceiptToDB = transactionDAO.saveReceiptToDB(expected);
            Mockito.verify(dbManager).getConnection();
            assertTrue(saveReceiptToDB);
        }
    }

    @org.junit.jupiter.api.Test
    public void updateAmountReceiptTest() throws SQLException {
        Receipt receipt = new Receipt("R1", 3, OperationStatus.CLOSED, OperationType.RETURN);
        receipt.setId(2);
        ReceiptProducts updateRecProd = new ReceiptProducts(1, 8888);
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            setSql();
            assertTrue(transactionDAO.updateAmountReceipt(receipt, updateRecProd));
        }
    }

    @org.junit.jupiter.api.Test
    public void deleteProductFromReceiptTest() throws SQLException {
        Receipt receipt = new Receipt("R1", 3, OperationStatus.CLOSED, OperationType.RETURN);
        receipt.setId(2);
        Product apple = new Product(1, "A123", "apple", 100.0, 5.0, "kg", "fruit");
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            setSql();
            assertTrue(transactionDAO.delProductFromReceipt(receipt, apple));
        }
    }

    @org.junit.jupiter.api.Test
    public void setUpdateProductAmountTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
            setSql();
            Double expectedAmount = 555.0;
            boolean isUpdate = transactionDAO.setUpdateProductAmount(connection, 2, 1, expectedAmount);
            transactionDAO.getAmountByIdProdByIdReceipt(connection, 2, 1);
            assertTrue(isUpdate);
            Double actualAmount = transactionDAO.getAmountByIdProdByIdReceipt(connection, 2, 1);
            assertEquals(expectedAmount, actualAmount);
        }
    }

    @org.junit.jupiter.api.Test
    public void delProductTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
            setSql();
            boolean isDelete = transactionDAO.delProd(connection, 2, 1);
            assertTrue(isDelete);
            Double actualAmount = transactionDAO.getAmountByIdProdByIdReceipt(connection, 2, 1);
            Double expectedAmount = 0.0;
            assertEquals(expectedAmount, actualAmount);
        }
    }

    @org.junit.jupiter.api.Test
    public void getAmountByIdProdByIdReceiptTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
            setSql();
            Double actualAmount = transactionDAO.getAmountByIdProdByIdReceipt(connection, 2, 1);
            Double expectedAmount = 33.0;
            assertEquals(expectedAmount, actualAmount);
        }
    }

    @org.junit.jupiter.api.Test
    public void addProductForReceiptTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
            setSql();
            boolean isUpdate = transactionDAO.addProductForReceipt(connection, 1, 1, 444.0, 333.0);
            assertTrue(isUpdate);
        }
    }

    @org.junit.jupiter.api.Test
    public void createProductWithTranslateTest() throws SQLException {
        Product cheese = new Product(4, "Cheese1", "cheese", 230.0, 5.0, "kg", "milk");
        HashMap<Integer, String> names = new HashMap<>();
        names.put(1, "cheese");
        names.put(2, "сир");
        names.put(3, "сыр");
        HashMap<Integer, String> descriptions = new HashMap<>();
        descriptions.put(1, "milk food");
        descriptions.put(2, "виріб з молока");
        descriptions.put(3, "продукт из молока");
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            setSql();
            boolean created = transactionDAO.createProductWithTranslate(cheese, names, descriptions);
            assertTrue(created);
        }
    }

    @org.junit.jupiter.api.Test
    public void addTpDataBaseTest() throws SQLException {
        Product cheese = new Product(4, "Cheese1", "cheese", 230.0, 5.0, "kg", "milk");
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
            setSql();
            boolean added = transactionDAO.addToDataBase(cheese, connection);
            assertTrue(added);
        }
    }

    private void setUp(MockedStatic<DBManager> dbManagerMockedStatic) {
        dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
        Mockito.when(dbManager.getConnection()).thenReturn(connection);
    }

    private void setSql() throws SQLException {
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

