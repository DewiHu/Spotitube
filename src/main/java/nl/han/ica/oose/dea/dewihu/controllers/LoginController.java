package nl.han.ica.oose.dea.dewihu.controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class LoginController {
    @GET
    public String stuurText() {
        return "Hello world!";
    }

/*    @Path("login")
    @POST
    @Produces("application/json")
    public Response loginFormaat(@QueryParam("user") String user, @QueryParam("password") String password) {
    }*/
}
