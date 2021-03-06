package nl.han.ica.oose.dea.dewihu.datasources.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("/database.properties"));
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Can't access property file database.properties", e);
        }
    }

    public String driver() {
        return properties.getProperty("driver");
    }

    public String connectionString() {
        return properties.getProperty("connectionString");
    }

    public String connectionUser() {
        return properties.getProperty("user");
    }

    public String connectionPassword() {
        return properties.getProperty("password");
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
