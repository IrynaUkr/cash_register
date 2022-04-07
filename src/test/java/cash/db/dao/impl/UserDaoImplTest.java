package cash.db.dao.impl;

import cash.db.manager.DBManager;
import cash.entity.Role;
import cash.entity.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static cash.db.Constant.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

    UserDaoImpl userDao = UserDaoImpl.getInstance();
    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);


    @Mock
    DBManager dbManager;

    public UserDaoImplTest() throws SQLException {
    }

    @org.junit.jupiter.api.Test
    public void connectionTest() throws SQLException {
        MockedStatic<DBManager> dbManagerMockedStatic = setUp();
        Connection testConnection = DBManager.getInstance().getConnection();
        assertNotNull(testConnection);
        dbManagerMockedStatic.close();
    }


    @org.junit.jupiter.api.Test
    public void findEntityByLoginTest() throws SQLException {
        MockedStatic<DBManager> dbManagerMockedStatic = setUp();
        List<User> userList = getUserList();
        User admin = new User("admin", "1", "Mercury", Role.ADMIN);
        User cashier = new User("cashier", "2", "Mars", Role.CASHIER);
        userList.add(admin);
        userList.add(cashier);
        User adminDB = userDao.findEntityByLogin("admin");
        assertEquals(admin, adminDB);
        dbManagerMockedStatic.close();
    }

    @org.junit.jupiter.api.Test
    public void findAllTest() throws SQLException {
        MockedStatic<DBManager> dbManagerMockedStatic = setUp();
        List<User> all = userDao.findAll();
        List<User> userList = getUserList();
        assertNotNull(all);
        assertEquals(all, userList);
        dbManagerMockedStatic.close();
    }

    @org.junit.jupiter.api.Test
    public void deleteTest() throws SQLException {
        MockedStatic<DBManager> dbManagerMockedStatic = setUp();
        User admin = new User();
        admin.setLogin("admin");
        assertTrue(userDao.delete(admin));
        dbManagerMockedStatic.close();
    }

    @org.junit.jupiter.api.Test
    public void createTest() throws SQLException {
        MockedStatic<DBManager> dbManagerMockedStatic = setUp();
        User merch = new User();
        merch.setLogin("merch");
        assertTrue(userDao.create(merch));
        dbManagerMockedStatic.close();
    }

    @org.junit.jupiter.api.Test
    public void updateTest() throws SQLException {
        MockedStatic<DBManager> dbManagerMockedStatic = setUp();
        User merch = new User("merch", "8", "UpdateSurname",  Role.MERCHANDISER);
        merch.setIdUser(1);
        assertTrue(userDao.update(merch));
        dbManagerMockedStatic.close();
    }
    @org.junit.jupiter.api.Test
    public void viewAllWithSortingTest() throws SQLException {
        MockedStatic<DBManager> dbManagerMockedStatic = setUp();
        List<User> users = userDao.viewAllWithSorting(1, 5, "role");
        System.out.println(users);
        dbManagerMockedStatic.close();

    }

    private MockedStatic<DBManager> setUp() throws SQLException {
        MockedStatic<DBManager> dbManagerMockedStatic = Mockito.mockStatic(DBManager.class);
        dbManagerMockedStatic.when((MockedStatic.Verification) DBManager.getInstance()).thenReturn(dbManager);
        Mockito.when(dbManager.getConnection()).thenReturn(connection);
        connection.createStatement().executeUpdate(DROP_TABLE_EMPLOYEE);
        connection.createStatement().executeUpdate(CREATE_TABLE_EMPLOYEE);
        connection.createStatement().executeUpdate(SET_EMPLOYEE);
        return dbManagerMockedStatic;
    }

    private List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        User admin = new User("admin", "1", "Mercury", Role.ADMIN);
        User cashier = new User("cashier", "2", "Mars", Role.CASHIER);
        userList.add(admin);
        userList.add(cashier);
        return userList;
    }

}
