package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.controllers.dto.TrackRequestDto;
import nl.han.ica.oose.dea.dewihu.controllers.dto.TrackResponseDto;
import nl.han.ica.oose.dea.dewihu.datasources.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/")
public class TrackController {
    private TrackDAO trackDAO;

    @GET
    @Path("playlists/{id}/tracks")
    @Produces("application/json")
    public Response tracks(@PathParam ("id") int id, @QueryParam("token") String token) {

        TrackRequestDto request = new TrackRequestDto();
        request.setId(id);
        request.setToken(token);
        ArrayList<TrackModel> tracks = trackDAO.tracks(request.getId());

        if (tracks.isEmpty()) {
            return Response.status(403).build();
        }

        TrackResponseDto response = new TrackResponseDto();
        response.setTracks(tracks);

        return Response.ok().entity(response).build();
    }

    @Path("tracks")
    @GET
    @Produces("application/json")
    public Response tracks() {

        var tracks = trackDAO.tracks(1);

        if (tracks.isEmpty()) {
            return Response.status(403).build();
        }

        TrackResponseDto response = new TrackResponseDto();
        response.setTracks(tracks);

        return Response.ok().entity(response).build();
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}