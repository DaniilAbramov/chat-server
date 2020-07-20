package utils;

import java.io.IOException;
import java.util.Properties;

public class Props {

    private static Properties properties = new Properties();
    static {
        try {
            properties.load(Props.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return System.getenv("db.user");
    }
}
