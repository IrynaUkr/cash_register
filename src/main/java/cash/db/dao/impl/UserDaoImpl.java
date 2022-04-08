package cash.db.dao.impl;


import cash.db.dao.UserDao;
import cash.db.manager.DBManager;
import cash.entity.Role;
import cash.entity.User;
import cash.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static cash.db.ConstantQueryDB.*;


public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private UserDaoImpl() {
    }

    private static UserDaoImpl instance = null;

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            return new UserDaoImpl();
        }
        return instance;
    }

    private int totalAmountRecords;

    public int getTotalAmountRecords() {
        return totalAmountRecords;
    }

    @Override
    public List<User> findAll() {
        logger.info("query: find users");
        List<User> users = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_FROM_USER)) {
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException ex) {
            logger.error("users were  not found", ex);
            throw new DBException("users were  not found", ex);
        }
        return users;
    }

    @Override
    public User findEntityById(Integer id) {
        logger.info("query: find user");
        User user = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_USER_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("user was  not found", ex);
            throw new DBException("user was  not found", ex);
        }
        return user;
    }

    @Override
    public User findEntityByLogin(String login) {
        logger.info("query: find user by login");
        User user = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_USER_BY_LOGIN)) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("user by login was  not found", ex);
            throw new DBException("user by login was  not found", ex);
        }
        return user;
    }


    @Override
    public boolean delete(User user) {
        logger.info("query: delete user");
        if (user == null) {
            return false;
        }
        int executeUpdate;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_USER_BY_LOGIN)) {
            pstmt.setString(1, user.getLogin());
            executeUpdate = pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("user was not deleted", ex);
            throw new DBException("user was not deleted", ex);
        }
        return executeUpdate > 0;
    }

    @Override
    public boolean create(User user) {
        logger.info("query: create user");
        if (user == null) {
            throw new IllegalArgumentException();
        }
        int result;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            mapUser(user, pstmt);
            result = pstmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setIdUser(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("user was not created", ex);
            throw new DBException("user was not created", ex);
        }
        return result > 0;
    }

    private void mapUser(User user, PreparedStatement pstmt) throws SQLException {
        int k = 0;
        pstmt.setString(++k, user.getLogin());
        pstmt.setString(++k, user.getPassword());
        pstmt.setString(++k, String.valueOf(user.getRole()));
        pstmt.setString(++k, user.getSurname());
        pstmt.setString(++k, user.getAddress());
        pstmt.setString(++k, user.getPhoneNumber());
        pstmt.setString(++k, user.getEmail());
    }

    @Override
    public boolean update(User user) {
        logger.info("query: update user");
        if (user == null) {
            throw new IllegalArgumentException();
        }
        int result;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SET_USER_LOGIN)) {
            pstmt.setString(1, user.getLogin());
            pstmt.setInt(2, user.getId());
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("user was not updated", ex);
            throw new DBException("user was not updated", ex);
        }
        return result > 0;
    }

    @Override
    public List<User> viewAllWithSorting(int offset, int recordsOnPage, String sortingType) {
        logger.info("query: view All With Sorting user");
        List<User> users = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM employee ORDER by ");
        queryBuilder.append(sortingType);
        queryBuilder.append(" LIMIT ?, ?");
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(queryBuilder.toString())) {
            pst.setInt(1, offset);
            pst.setInt(2, recordsOnPage);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
            rs = pst.executeQuery(COUNT_EMPLOYEE);
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            logger.error("employees with sorting amount of lines and language were  not found", ex);
            throw new DBException("employees with sorting amount of lines and language were  not found", ex);
        }
        return users;
    }

    public List<User> findAllWithRestrict(int offset, int noOfRecords) {
        logger.info("query: find all products with restrict amount of lines");
        List<User> users = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_FROM_EMPLOYEE_LIMIT)) {
            int k = 0;
            pst.setInt(++k, offset);
            pst.setInt(++k, noOfRecords);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
            rs = pst.executeQuery(COUNT_EMPLOYEE);
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            logger.error("products with restrict amount of lines were  not found", ex);
            throw new DBException("products with restrict amount of lines were  not found", ex);
        }
        return users;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setIdUser(rs.getInt("id_user"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRole(Role.valueOf(rs.getString("role")));
        user.setSurname(rs.getString("surname"));
        user.setAddress(rs.getString("address"));
        user.setPhoneNumber(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}


