package nl.han.ica.oose.dea.dewihu.datasources.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DatabaseConnection {

    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    public DatabaseConnection(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        tryLoadJdbcDriver(databaseProperties);
    }

    protected void executeStatement(String sql) {
        try {
            Connection conn = DriverManager.getConnection(getDatabaseProperties().connectionString(), getDatabaseProperties().connectionUser(), getDatabaseProperties().connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            if (st.execute()) {
                st.close();
                conn.close();
            }
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, "Error communicating with database " + getDatabaseProperties().connectionString(), e);
        }
    }

    private void tryLoadJdbcDriver(DatabaseProperties databaseProperties) {
        try{
            Class.forName(databaseProperties.driver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Can't load JDBC Driver " + databaseProperties.driver(), e);
        }
    }


    protected Logger getLogger() {
        return logger;
    }

    protected DatabaseProperties getDatabaseProperties() {
        return databaseProperties;
    }

}
