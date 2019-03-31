package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseConnection;
import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseProperties;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.logging.Level;

public class LoginDAO extends DatabaseConnection {

    @Inject
    public LoginDAO(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    public AccountModel login(String user, String pw) {
        String sql = "SELECT FULLNAME, TOKEN FROM ACCOUNT WHERE USERNAME LIKE '" + user + "' AND PASSWORD LIKE '" + pw + "'";

        var acc = new AccountModel();

        try {
            Connection conn = DriverManager.getConnection(getDatabaseProperties().connectionString(),
                    getDatabaseProperties().connectionUser(), getDatabaseProperties().connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            setAccountLogin(acc, st);
            st.close();
            conn.close();
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, "Error communicating with database " + getDatabaseProperties().connectionString(), e);
        }

        return acc;
    }

    private void setAccountLogin(AccountModel acc, PreparedStatement st) throws SQLException {
        ResultSet rS = st.executeQuery();
        while(rS.next()) {
            acc.setName(rS.getString("FULLNAME"));
            acc.setToken(rS.getString("TOKEN"));
        }
    }
}