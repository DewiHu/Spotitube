package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.models.AccountModel;

public interface LoginDAO {
    AccountModel login(String user, String password);
}
