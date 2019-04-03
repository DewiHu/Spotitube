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

    @Path("tracks")
    @GET
    @Produces("application/json")
    public Response tracks(@QueryParam("forPlaylist") int forPlaylist, @QueryParam("Token") String token) {
        ArrayList<TrackModel> tracks = trackDAO.availableTracks(forPlaylist);

        if (tracks.isEmpty()) {
            return Response.status(403).build();
        }

        TrackResponseDto response = new TrackResponseDto();
        response.setTracks(tracks);

        return Response.ok().entity(response).build();
    }

    @GET
    @Path("playlists/{pId}/tracks")
    @Produces("application/json")
    public Response tracksOfPlaylist(@PathParam ("pId") int pId, @QueryParam("token") String token) {
        ArrayList<TrackModel> tracks = trackDAO.tracks(pId);

        TrackResponseDto response = new TrackResponseDto();
        response.setTracks(tracks);

        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("playlists/{pId}/tracks/{tId}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response delTrack (@PathParam("pId") int pId, @PathParam("tId") int tId, @QueryParam("token") String token) {
        trackDAO.dTrack(pId, tId);
        return tracksOfPlaylist(pId, token);
    }

    @POST
    @Path("playlists/{pId}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response postTrack (@PathParam("pId") int pId, @QueryParam("token") String token, TrackRequestDto request) {
        trackDAO.cTrack(pId, request.getId(), request.isOfflineAvailable());

        return tracksOfPlaylist(pId, token);
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}