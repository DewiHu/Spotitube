package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.models.AccountModel;

public class LoginDAO {

    public nl.han.ica.oose.dea.dewihu.models.AccountModel login(String user, String password) {

        var account = new AccountModel();

        if ("meron".equals(user) && "password".equals(password)) {
            account.setName("Meron Brouwer");
            account.setToken("dfkmfsd-qeeqw-8234nk");
        }
        return account;
    }
}