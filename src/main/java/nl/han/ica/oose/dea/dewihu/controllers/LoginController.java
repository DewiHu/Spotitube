package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.datasources.*;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginController {
    private LoginDAO loginDAO;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequestDto request) {

        UserModel login = loginDAO.login(request.getUser(), request.getPassword());

        if (login.getToken() == null) {
            return Response.status(403).build();
        }

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(login.getToken());
        response.setUser(login.getFullName());

        return Response.ok().entity(response).build();
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

}
