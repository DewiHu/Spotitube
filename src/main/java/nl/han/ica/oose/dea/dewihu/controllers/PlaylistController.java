package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistRequestDto;
import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistResponseDto;
import nl.han.ica.oose.dea.dewihu.datasources.PlaylistDAO;
import nl.han.ica.oose.dea.dewihu.datasources.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;
import nl.han.ica.oose.dea.dewihu.services.PlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/")
public class PlaylistController {
    private PlaylistDAO playlistDAO;
    private PlaylistService playlistService;
    private TrackDAO trackDAO;

    @GET
    @Path("playlists")
    @Produces ("application/json")
    public Response playlists(@QueryParam("token") String token) {
        ArrayList<PlaylistModel> playlists = playlistDAO.rAllPlaylists(token);

        if (playlists.isEmpty()) {
            return Response.status(400).build();
        }

        for (PlaylistModel p : playlists) {
            p.setTracks(trackDAO.availableTracks(p.getId()));
        }

        PlaylistResponseDto response = new PlaylistResponseDto();
        response.setPlaylists(playlists);
        response.setLength(playlistService.length(playlists));

        return Response.ok().entity(response).build();
    }


    @DELETE
    @Path("playlists/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response delPlaylist (@QueryParam("token") String token, @PathParam("id") int id) {
        playlistDAO.dPlaylist(id);
        return playlists(token);
    }

    @POST
    @Path("playlists")
    @Produces("application/json")
    @Consumes("application/json")
    public Response postPlaylist (@QueryParam("token") String token, PlaylistRequestDto request) {
        playlistDAO.cPlaylist(request.getName(), token);
        return playlists(token);
    }

    @PUT
    @Path("playlists/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response putPlaylist (@QueryParam("token") String token, @PathParam("id") int id, PlaylistRequestDto request) {
        playlistDAO.uPlaylist(id, request.getName());
        return playlists(token);
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
