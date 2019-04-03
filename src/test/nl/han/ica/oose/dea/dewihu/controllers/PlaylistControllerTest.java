package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistRequestDto;
import nl.han.ica.oose.dea.dewihu.datasources.PlaylistDAO;
import nl.han.ica.oose.dea.dewihu.datasources.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

class PlaylistControllerTest {
    private static final String TOKEN = "1234-1234-1234";
    private PlaylistModel playlist;
    private ArrayList<PlaylistModel> playlistModels = new ArrayList<>();
    private PlaylistDAO playlistDAOMock;
    private PlaylistController playlistController;

    @BeforeEach
    void setup() {
        playlistDAOMock = Mockito.mock(PlaylistDAO.class);
        TrackDAO trackDAOMock = Mockito.mock(TrackDAO.class);

        playlist = new PlaylistModel();
        playlistModels.add(playlist);

        playlistController = new PlaylistController();
        playlistController.setPlaylistDAO(playlistDAOMock);
        playlistController.setTrackDAO(trackDAOMock);
    }

    @Test
    void doesEndPointDelegateTokenToDAO() {
        //Assemble
        Mockito.when(playlistDAOMock.playlists(TOKEN)).thenReturn(playlistModels);

        //Act
        Response response = playlistController.getPlaylists(TOKEN);

        //Assert
        Mockito.verify(playlistDAOMock).playlists(TOKEN);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointDelegateRequestToDAO() {
        //Assemble
        var dto = new PlaylistRequestDto();
        dto.setName("Test");

        //Act
        Response response = playlistController.postPlaylists(TOKEN, dto);

        //Assert
        Mockito.verify(playlistDAOMock).cPlaylist(dto.getName(), TOKEN);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointReturnCorrectStatusWithEmptyPlaylist() {
        //Assemble
        String invalidToken = "Test Token";
        Mockito.when(playlistDAOMock.playlists(invalidToken)).thenReturn(playlistModels);

        //Act
        Response response = playlistController.getPlaylists(TOKEN);

        //Assert
        Mockito.verify(playlistDAOMock).playlists(TOKEN);
        Assertions.assertEquals(403, response.getStatus());
    }
}