package nl.han.ica.oose.dea.dewihu.controllers.dto;

import javax.ws.rs.Path;

@Path("login")
public class LoginRequestDto {
    String user;
    String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
