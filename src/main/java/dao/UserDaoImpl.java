package dao;

import lombok.SneakyThrows;
import model.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    private final String DB_URL = "jdbc:MySQL://localhost:3306/my_schema?serverTimezone=UTC";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "root";

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
    public void setUserPassword(String name, String password) {
        getStatement().executeUpdate("UPDATE user SET password = '" + password + "' WHERE login = '" + name + "'");
        System.out.println("Password update");
    }

    @SneakyThrows
    @Override
    public void setUserName(User user, String name) {
        getStatement().executeUpdate("UPDATE user SET login = '" + user.getName() + "' WHERE login = '" + name + "'");
        System.out.println("Name update");

    }

    @SneakyThrows
    @Override
    public User deleteUser(String name, String password) {
        getStatement().executeUpdate("DELETE from user WHERE '" + name + "'" + ",'" + password + "'");
        System.out.println("deleted successful");

        return null;
    }

    private Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection.createStatement();
    }
}
