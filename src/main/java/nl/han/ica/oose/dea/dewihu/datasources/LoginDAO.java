package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseProperties;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO{

    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    @Inject
    public LoginDAO(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        tryLoadJdbcDriver(databaseProperties);
    }

    public AccountModel login(String user, String password) {
        String sql = "SELECT FULLNAME, TOKEN FROM ACCOUNT WHERE USERNAME LIKE '" + user + "' AND PASSWORD LIKE '" + password + "'";

        var account = new AccountModel();

        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString(), databaseProperties.connectionUser(), databaseProperties.connectionPassword());
            PreparedStatement statement = connection.prepareStatement(sql);
            setAccountLogin(account, statement);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }

        return account;
    }

    private void setAccountLogin(AccountModel account, PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            account.setName(resultSet.getString("FULLNAME"));
            account.setToken(resultSet.getString("TOKEN"));
        }
    }

    private void tryLoadJdbcDriver(DatabaseProperties databaseProperties) {
        try{
            Class.forName(databaseProperties.driver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Can't load JDBC Driver " + databaseProperties.driver(), e);
        }
    }

}