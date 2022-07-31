package ru.clevertec.gordievich.infrastructure.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();
    private static final String PATH_FILL = "application.properties";
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";

    static {
        loadProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getDbUrl() {
        return PROPERTIES.getProperty(URL_KEY);
    }

    public static String getDbUser() {
        return PROPERTIES.getProperty(USERNAME_KEY);
    }

    public static String getDbPassword() {
        return PROPERTIES.getProperty(PASSWORD_KEY);
    }

    static void loadProperties() {
        try (InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PATH_FILL)) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
