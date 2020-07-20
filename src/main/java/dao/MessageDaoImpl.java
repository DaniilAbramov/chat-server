package dao;

import lombok.SneakyThrows;
import model.Message;
import model.User;

import java.sql.*;

public class MessageDaoImpl implements MessageDao {


    @SneakyThrows
    @Override
    public Message findByMessage(Message message) {
        ResultSet resultSet = getStatement().executeQuery("SELECT text, password from message");
        while (resultSet.next()) {
            if (message.getText().equalsIgnoreCase(resultSet.getString("text"))) {
                return new Message(message.getId(), message.getText(), resultSet.getShort("text"));
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void createMessage(Message message) {
        getStatement().executeUpdate("INSERT INTO message(id,text,idUser) values ('" + message.getId() + ',' + message.getText() + "','" + message.getIdUser() + "');");
    }

    @SneakyThrows
    @Override
    public void setUserMessage(String text, Integer idUser) {
        getStatement().executeUpdate("UPDATE message SET text ='" + text + "' where '" + idUser + "'");

    }

    @SneakyThrows
    @Override
    public Message deleteMessage(Integer id, String text) {
        getStatement().executeUpdate("DELETE from message WHERE '" + id + "'and '" + text + "'");
        System.out.println("deleted successful");
        return null;
    }


    private Statement getStatement() throws SQLException {
        String DB_URL = "jdbc:MySQL://localhost:3306/my_schema?serverTimezone=UTC";
        String DB_USER = "root";
        String DB_PASSWORD = "root";
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        return connection.createStatement();
    }
}
