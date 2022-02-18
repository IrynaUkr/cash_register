package cash.db.dao;

import cash.entity.Entity;

import java.sql.*;
import java.util.List;

public interface BaseDao<T extends Entity> {
    List<T> findAll();

    T findEntityById(Integer id);

    boolean delete(T t);//чтобы избавиться от выброса исключения использ boolean

    boolean create(T t);

    boolean update(T t);


    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                //return connection to pull
                connection.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

}
