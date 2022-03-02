package cash.db.dao;

import cash.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    List<User> findUserByRole(String role);
    boolean update(User user);
    User findEntityByLogin(String login);


}
