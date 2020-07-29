import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import services.MyServer;
import utils.Props;

import java.io.IOException;
import java.util.Properties;

public class MyApp {

    public static void main(String[] args) {
        new MyServer().start();

        UserDao dao = new UserDaoImpl();

        dao.createUser(new User("Max","12345"));
        dao.setUserName(new User("Daniil","12345"),"daniil");
        dao.setUserPassword(new User("daniil","12345"),"54321");
        dao.deleteUser("Max","12345");

//        Properties props = new Properties();

//        try {
//            props.load(MyApp.class.getResourceAsStream("application.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        System.out.println("props.getProperty(\"user\") = " + Props.getValue("db.user"));

//        UserDao dao = new UserDaoImpl();
//        dao.findByName("");
    }
}
