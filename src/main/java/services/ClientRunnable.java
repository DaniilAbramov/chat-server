package services;

import dao.MessageDao;
import dao.MessageDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.User;

import java.io.PrintWriter;
import java.net.Socket;

@RequiredArgsConstructor
public class ClientRunnable implements Runnable, Observer {
    private final Socket clientSocket;
    private final MyServer server;
    private User client;
    private final UserDao dao = new UserDaoImpl();
    private final MessageDao messageDao = new MessageDaoImpl();

    @SneakyThrows
    @Override
    public void run() {
        server.addObserver(this);
//        BufferedReader readerFromUser = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        ServerReceiver serverReceiver = new ServerReceiver(clientSocket.getInputStream());
        String messageFromUser = "";
        while (!clientSocket.isClosed() && (messageFromUser = serverReceiver.readMessage()) != null) {
            if (messageFromUser.contains("Registration ")) {
                registration(messageFromUser);
            } else if (messageFromUser.contains("Authorization ")) {
                authorization(messageFromUser);
            } else {
                break;
            }
        }
        if (clientSocket.isConnected()) {
            do {
                System.out.println(messageFromUser);
                server.notifyObservers(messageFromUser);
            } while ((messageFromUser = serverReceiver.readMessage()) != null);
        }
    }

    @SneakyThrows
    private void authorization(String messageFromUser) {
        String loginFromClient = messageFromUser.split(" ")[1];
        String passwordFromClient = messageFromUser.split(" ")[2];

        User userFromDao;
        if ((userFromDao = dao.findByName(loginFromClient)) != null) {
            if (userFromDao.getPassword().equals(passwordFromClient)) {
                client = userFromDao;
                notifyObserver("Authorization: successfully");
            } else {
                notifyObserver("Authorization failed: wrong password");
                server.deletedObserver(this);
                clientSocket.close();
                System.exit(0);
            }
        } else {
            notifyObserver("Authorization failed: wrong name");
            server.deletedObserver(this);
            clientSocket.close();
        }
    }

    private void registration(String messageFromUser) {
        System.out.println("Rega");
        client = new User(messageFromUser.split(" ")[1],
                messageFromUser.split(" ")[2]);
        dao.createUser(client);
        System.out.println("Rega for " + client.getName() + " success");
        notifyObserver("Registration successfully");
    }


    @SneakyThrows
    @Override
    public void notifyObserver(String message) {
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
//        if (client != null) {
        printWriter.println(message);
        printWriter.flush();
    }
}
//}
