package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.controllers.dto.LoginRequestDto;
import nl.han.ica.oose.dea.dewihu.controllers.dto.LoginResponseDto;
import nl.han.ica.oose.dea.dewihu.datasources.LoginDAO;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {
    private LoginDAO loginDAO;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequestDto request) {

        AccountModel login = loginDAO.login(request.getUser(), request.getPassword());

        if (login.getToken() == null) {
            return Response.status(403).build();
        }

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(login.getToken());
        response.setUser(login.getName());

        return Response.ok().entity(response).build();
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

}
