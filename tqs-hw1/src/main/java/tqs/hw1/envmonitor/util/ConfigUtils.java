package tqs.hw1.envmonitor.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    static public String getProperty(String property) {

        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream("application.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }

            prop.load(input);

            return prop.getProperty(property);

        } catch (IOException ex) {
            throw new RuntimeException("Unable to get properties from application.properties");
        }
    }
}
