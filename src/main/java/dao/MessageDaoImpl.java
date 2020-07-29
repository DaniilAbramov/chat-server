package dao;

import lombok.SneakyThrows;
import model.Message;
import model.User;
import utils.Props;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {


    @SneakyThrows
    @Override
    public List<Message> findAllByUser(User user) {
        ResultSet resultSet = getStatement().executeQuery("SELECT login, text from message, user WHERE message.user_id = user.id");
        List<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            if (user.getName().equals(resultSet.getString("login"))) {
                messages.add(new Message(resultSet.getString("text"), user));
            }
        }
        return messages;
    }

    @SneakyThrows
    @Override
    public void createMessage(Message message, User user) {
        ResultSet resultSet = getStatement().executeQuery("SELECT id FROM user WHERE login = '" + user.getName() + "'");
        int userId = resultSet.getInt("id");
        getStatement().executeUpdate("INSERT INTO message(id,text,idUser) values ('" +
                message.getText() + "','" + userId + "');");
    }

    private Statement getStatement() throws SQLException {
        String DB_URL = Props.getValue("db.url");
        String DB_USER = Props.getValue("db.user");
        String DB_PASSWORD = Props.getValue("db.password");
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection.createStatement();
    }
}
