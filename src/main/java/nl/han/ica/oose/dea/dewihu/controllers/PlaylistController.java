package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistResponseDto;
import nl.han.ica.oose.dea.dewihu.datasources.PlaylistDAO;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("playlists")
public class PlaylistController {
    private PlaylistDAO playlistDAO;

    @GET
    @Produces ("application/json")
    public Response playlists(@QueryParam("token") String token) {

        ArrayList<PlaylistModel> playlists = playlistDAO.playlists(token);

        if (playlists.get(0) == null) {
            return Response.status(403).build();
        }

        PlaylistResponseDto response = new PlaylistResponseDto();
        response.setPlaylists(playlists);
        response.setLength(1234);

        return Response.ok().entity(response).build();
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }
}
