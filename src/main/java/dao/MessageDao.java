package dao;

import model.Message;

public interface MessageDao {

    Message findByMessage(Message message);

    void createMessage(Message message);

    void setUserMessage(String text, Integer idUser);

    Message deleteMessage(Integer id, String text);
}
