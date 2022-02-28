package cash.db.dao.impl;


import cash.db.dao.UserDao;
import cash.db.manager.DBManager;
import cash.entity.Role;
import cash.entity.User;
import cash.exceptions.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public UserDaoImpl() {
    }

    public static final String SELECT_USER_BY_ID = "SELECT * FROM user  WHERE id_user = ?";
    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user  WHERE login = ?";
    public static final String SELECT_USER_BY_ROLE = "SELECT * FROM user  WHERE role = ?";
    public static final String SELECT_FROM_USER = "SELECT * FROM user";
    public static final String INSERT_USER = "INSERT INTO user (login, password, role, surname) VALUES (?, ?, ?, ?)";
    public static final String SET_USER = "UPDATE user SET login = ?, password = ?, role = ?, surname =? WHERE id_user =?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE id_user = ?";


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_FROM_USER)) {
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public User findEntityById(Integer id) {
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
            ex.printStackTrace();
        }
        return user;
    }

    public User findEntityByLogin(String login) {
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
            ex.printStackTrace();
        }
        return user;
    }


    @Override
    public boolean delete(User user) {
        if (user == null) {
            return false;
        }
        int id = user.getId();
        if (findEntityById(id) == null) {
            return false;
        } else {
            int executeUpdate = 0;
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(DELETE_USER_BY_ID)) {
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return executeUpdate > 0;
        }
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setIdUser(rs.getInt("id_user"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setRole(Role.valueOf(rs.getString("role")));
        user.setSurname(rs.getString("surname"));
        return user;
    }


    @Override
    public boolean create(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        int result;
        if (findEntityByLogin(user.getLogin()) != null) {
            return false;
        } else {
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
            } catch (SQLException e) {
                throw new DBException("insert user was failed", e);
            }
            return result > 0;
        }
    }

    private void mapUser(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getLogin());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, String.valueOf(user.getRole()));
        pstmt.setString(4, user.getSurname());
    }

    public boolean update(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        user.getId();
        if (findEntityById(user.getId()) != null) {
            int result = 0;
            try (
                    Connection con = DBManager.getInstance().getConnection();
                    PreparedStatement pstmt = con.prepareStatement(SET_USER)) {
                mapUser(user, pstmt);
                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result > 0;
        } else {
            return false;
        }
    }


    @Override
    public List<User> findUserByRole(String role) {
        List<User> users = new ArrayList<>();
        try (
                Connection con = DBManager.getInstance().getConnection();
                PreparedStatement pstmt = con.prepareStatement(SELECT_USER_BY_ROLE)) {
            pstmt.setString(1, role);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }
}

