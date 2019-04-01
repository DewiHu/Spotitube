package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistRequestDto;
import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistResponseDto;
import nl.han.ica.oose.dea.dewihu.datasources.PlaylistDAO;
import nl.han.ica.oose.dea.dewihu.datasources.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/")
public class PlaylistController {
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;

    @GET
    @Path("playlists")
    @Produces ("application/json")
    public Response getPlaylists(@QueryParam("token") String token) {
        ArrayList<PlaylistModel> playlists = playlistDAO.playlists(token);

        if (playlists.isEmpty()) {
            return Response.status(403).build();
        }

        for (PlaylistModel p : playlists) {
            p.setTracks(trackDAO.tracks(p.getId()));
        }

        PlaylistResponseDto response = new PlaylistResponseDto();
        response.setPlaylists(playlists);
        response.setLength(playlistDAO.length(playlists));

        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("playlists/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response delPlaylists (@PathParam("id") int id, @QueryParam("token") String token) {
        playlistDAO.dPlaylist(id, token);

        return Response.ok().entity(getPlaylists(token)).build();
    }

    @POST
    @Path("playlists")
    @Produces("application/json")
    @Consumes("application/json")
    public Response postPlaylists (@QueryParam("token") String token, PlaylistRequestDto request) {
        playlistDAO.cPlaylist(request.getName(), token);

        return Response.ok().entity(getPlaylists(token)).build();
    }

    @PUT
    @Path("playlists/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response putPlaylists (@PathParam("id") int id, PlaylistRequestDto request, @QueryParam("token") String token) {
        playlistDAO.uPlaylist(id, request.getName(), token);

        return Response.ok().entity(getPlaylists(token)).build();
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
