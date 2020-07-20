package dao;

import model.User;

public interface UserDao {
    User findByName(String name);

    void setUserPassword(String name, String password);

    void createUser(User user);

    void setUserName(User user, String name);

    User deleteUser(String name, String password);


}
