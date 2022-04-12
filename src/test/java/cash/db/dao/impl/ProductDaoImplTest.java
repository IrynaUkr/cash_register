package cash.db.dao.impl;

import cash.db.manager.DBManager;
import cash.entity.Product;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static cash.db.Constant.*;
import static org.junit.jupiter.api.Assertions.*;

/* to integrate Mockito with the JUnit 5 extension model use @ExtendWith*/
@ExtendWith(MockitoExtension.class)
public class ProductDaoImplTest {
    ProductDaoImpl productDao = ProductDaoImpl.getInstance();
    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

    public ProductDaoImplTest() throws SQLException {
    }

    /* use the @Mock annotation to inject a mock for an instance variable that we can use anywhere in the test class:*/
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
    public void findAllProductTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            List<Product> actual = productDao.findAll();
            List<Product> expected = getProducts();
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void viewAllWithRestrictTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            int expected = 2;
            List<Product> users = productDao.findAllWithRestrict(0, expected, 1);
            int actual = users.size();
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void viewAllWithSortingTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            int recordsOnPage = 2;
            List<Product> actual = productDao.viewAllWithSorting(0, recordsOnPage, "price", 1);
            List<Product> expected = getProducts();
            expected.sort(Comparator.comparing(Product::getPrice));
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findProductByLangTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            List<Product> actual = productDao.findAllByLang(1);
            int sizeActual = actual.size();
            int sizeExpected = 2;
            assertEquals(sizeExpected, sizeActual);
        }
    }

    @org.junit.jupiter.api.Test
    public void findProductByIdTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Product actual = productDao.findEntityById(1);
            Product expected = new Product(1, "A123", "apple", 100.0, 5.0, "kg", "fruit");
            assertEquals(expected, actual);
        }
    }

    @org.junit.jupiter.api.Test
    public void deleteTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Product apple = new Product(1, "A123", "apple", 100.0, 5.0, "kg", "fruit");
            boolean delete = productDao.delete(apple);
            assertTrue(delete);
        }
    }

    @org.junit.jupiter.api.Test
    public void deleteProductByCodeTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            boolean delete = productDao.deleteProductByCode("A123");
            assertTrue(delete);
        }
    }

    @org.junit.jupiter.api.Test
    public void createTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Product tomato = new Product(
                    3, "pp3", "tomato", 10.0, 50.0, "kg", "vegetables");
            boolean create = productDao.create(tomato);
            assertTrue(create);
        }
    }
    @org.junit.jupiter.api.Test
    public void updateAmountTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Product apple = new Product(1, "A123", "apple", 100.0, 5.0, "kg", "fruit");
            boolean update = productDao.updateAmount(apple,1000.0);
            assertTrue(update);
        }
    }
    @org.junit.jupiter.api.Test
    public void findProductByCodeTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Product expected = new Product(1, "A123", "apple", 100.0, 5.0, "kg", "fruit");
            Product actual = productDao.findProductByCode("A123");
            assertEquals(expected, actual);
        }
    }
    @org.junit.jupiter.api.Test
    public void findProductByCodeLangTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Product expected = new Product(1, "A123", "apple", 100.0, 5.0, "kg", "fruit");
            Product actual = productDao.findProductByCodeLang("A123",1);
            assertEquals(expected, actual);
        }
    }
    @org.junit.jupiter.api.Test
    public void findProductByNameLangTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            Product expected = new Product(1, "A123", "apple", 100.0, 5.0, "kg", "fruit");
            Product actual = productDao.findProductByNameLang("apple",1);
            assertEquals(expected, actual);
        }
    }
    @org.junit.jupiter.api.Test
    public void getId_langTest() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class)) {
            setUp(dbManagerMockedStatic);
            int expected = 2;
            int actual = productDao.getId_lang("ua");
            assertEquals(expected,actual);
        }
    }
    private List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "A123", "apple", 100.0, 5.0, "kg", "fruit"));
        productList.add(new Product(2, "B456", "cherry", 300.0, 10.0, "kg", "berry"));
        return productList;
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
    }
}
