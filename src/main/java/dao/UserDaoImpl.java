package dao;

import lombok.SneakyThrows;
import model.User;
import utils.Props;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    private final String DB_URL = Props.getValue("db.url");
    private final String DB_USER = Props.getValue("db.user");
    private final String DB_PASSWORD = Props.getValue("db.password");

    @SneakyThrows
    @Override
    public User findByName(String name) {
        ResultSet resultSet = getStatement().executeQuery("SELECT login, password from USER");
        while (resultSet.next()) {
            if (name.equalsIgnoreCase(resultSet.getString("login"))) {
                return new User(name, resultSet.getString("password"));
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void createUser(User user) {
        getStatement().executeUpdate("INSERT INTO USER(login, password) VALUES ('"
                + user.getName() + "','" + user.getPassword() + "') ");
    }

    @SneakyThrows
    @Override
    public void setUserPassword(User user, String password) {
        getStatement().executeUpdate("UPDATE user SET password = '" + password + "' WHERE login = '" + user.getName() + "'");
        System.out.println("Password update");
    }

    @SneakyThrows
    @Override
    public void setUserName(User user, String name) {
        getStatement().executeUpdate("UPDATE user SET login = '" + name + "' WHERE login = '" + user.getName() + "'");
        System.out.println("Name update");

    }

    @SneakyThrows
    @Override
    public User deleteUser(String name, String password) {
        getStatement().executeUpdate("DELETE from user WHERE '" + name + "','" + password + "'");
        System.out.println("deleted successful");
        return null;
    }

    private Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection.createStatement();
    }
}
