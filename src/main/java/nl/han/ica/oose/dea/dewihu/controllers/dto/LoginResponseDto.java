package nl.han.ica.oose.dea.dewihu.controllers.dto;

import javax.ws.rs.Path;

@Path("login")
public class LoginResponseDto {
    String token;
    String user;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
