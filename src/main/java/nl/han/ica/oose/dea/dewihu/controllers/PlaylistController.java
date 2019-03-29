package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistRequestDto;
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

@Path("/")
public class PlaylistController {
    private PlaylistDAO playlistDAO;

    @GET
    @Path("playlists")
    @Produces ("application/json")
    public Response playlists(@QueryParam("token") String token) {

        PlaylistRequestDto request = new PlaylistRequestDto(token);
        ArrayList<PlaylistModel> playlists = playlistDAO.playlists(request.getToken());

        if (playlists.isEmpty()) {
            return Response.status(403).build();
        }

        for (PlaylistModel p : playlists) {
            //p.setTracks(TrackDAO.tracks(p.getId()));
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
