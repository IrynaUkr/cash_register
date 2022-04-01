package cash.db;

import cash.db.dao.impl.UserDaoImpl;
import cash.db.manager.DBManager;
import cash.entity.Product;
import cash.entity.Role;
import cash.entity.User;
import cash.exceptions.DBException;
import org.junit.Ignore;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static cash.db.ConstantQueryDB.COUNT_PRODUCT;
import static org.junit.jupiter.api.Assertions.*;
//"INSERT INTO employee (login, password, role, surname, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)"

public class UserDaoTest {
    private static Connection con;
    UserDaoImpl userDao;
    private static final String CREATE_USERS_TABLE = "CREATE TABLE  employee ("
            + "	id_user INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
            + "	login VARCHAR(210),"
            + "	password VARCHAR(250) ,"
            + "	role VARCHAR(210) ,"
            + "	surname VARCHAR(210) ,"
            + "	address VARCHAR(210),"
            + "	phone VARCHAR(210) ,"
            + "	email VARCHAR(255)"
            + ")";
    private static final String DROP_USERS_TABLE = "DROP TABLE employee";
    private DBManager dbm;

    @BeforeAll
    static void globalSetUp() throws SQLException, IOException {
        con = DriverManager.getConnection("jdbc:derby:memory:cashier;create=true");
        System.out.println("con-> " + con);
    }

    @AfterAll
    static void globalTearDown() throws SQLException, IOException {
        con.close();
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            System.err.println("Derby shutdown");
        }

    }

    @BeforeEach
    void setUp() throws SQLException {
        con.createStatement().executeUpdate(CREATE_USERS_TABLE);
    }

    @AfterEach
    void tearDown() throws SQLException {
        con.createStatement().executeUpdate(DROP_USERS_TABLE);
        System.out.println("tear down drop table");
    }

    @Test
    void createUserWithConTest() {
        User expected = new User("login", "pass", "surname", Role.ADMIN);
        userDao = UserDaoImpl.getInstance();
        userDao.createWithCon(expected, con);
        User actual = userDao.findEntityByLoginWithCon("login", con);
        assertEquals(expected, actual);
    }

    @Test
    void finsAllWithConTest() {
        User bender = new User("Ostap", "pass", "Bender", Role.ADMIN);
        User vorobjaninov = new User("Kisa", "pass", "Vorobjaninov", Role.ADMIN);
        userDao = UserDaoImpl.getInstance();
        userDao.createWithCon(bender, con);
        userDao.createWithCon(vorobjaninov, con);
        List<User> actual = userDao.findAllWithCon(con);
        List<User> expected = new ArrayList<>();
        expected.add(bender);
        expected.add(vorobjaninov);
        assertEquals(expected, actual);
    }

    @Test
    public void findEntityByLoginWithConTest() {
        User bender = new User("Ostap", "passOstap", "Bender", Role.ADMIN);
        User expected = new User("login", "pass", "surname", Role.ADMIN);
        userDao = UserDaoImpl.getInstance();
        userDao.createWithCon(expected, con);
        User actual = userDao.findEntityByLoginWithCon("login", con);
        assertEquals(expected, actual);
        assertNotEquals(bender, actual);
    }

    @Test
    public void deleteWithConnectionTest() {
        User bender = new User("Ostap", "pass", "Bender", Role.ADMIN);
        User vorobjaninov = new User("Kisa", "pass", "Vorobjaninov", Role.ADMIN);
        userDao = UserDaoImpl.getInstance();
        userDao.createWithCon(bender, con);

        boolean isDeleted = userDao.deleteWithConnection(bender.getLogin(), con);
        boolean isNotDeleted = userDao.deleteWithConnection(vorobjaninov.getLogin(), con);
        assertTrue(isDeleted);
        assertFalse(isNotDeleted);
        User actual = userDao.findEntityByLoginWithCon(bender.getLogin(), con);
        assertNull(actual);
    }

    @Test
    public void findEntityByIdWithConTest() {
        User bender = new User("Ostap", "pass", "Bender", Role.ADMIN);
        userDao = UserDaoImpl.getInstance();
        userDao.createWithCon(bender, con);
        User benderInDBbyLogin = userDao.findEntityByLoginWithCon(bender.getLogin(), con);
        User benderInDbById = userDao.findEntityByIdWithCon(benderInDBbyLogin.getId(), con);
        assertEquals(benderInDBbyLogin, benderInDbById);
    }


}
