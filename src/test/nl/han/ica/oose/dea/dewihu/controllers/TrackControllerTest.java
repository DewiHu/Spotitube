package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.controllers.dto.TrackRequestDto;
import nl.han.ica.oose.dea.dewihu.datasources.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

class TrackControllerTest {
    private static final String TOKEN = "1234-1234-1234";
    private TrackModel track;
    private ArrayList<TrackModel> trackModels;
    private TrackDAO trackDAOMock;
    private TrackController trackController;

    @BeforeEach
    void setup() {
        trackDAOMock = Mockito.mock(TrackDAO.class);

        track = new TrackModel();
        trackModels = new ArrayList<>();
        trackModels.add(track);

        trackController = new TrackController();
        trackController.setTrackDAO(trackDAOMock);
    }

    @Test
    void doesEndPointDelegateToDAO() {
        //Assemble
        Mockito.when(trackDAOMock.availableTracks(Mockito.isA(Integer.class))).thenReturn(trackModels);

        //Act
        Response response = trackController.tracks(Mockito.isA(Integer.class), TOKEN);


        //Assert
        Mockito.verify(trackDAOMock).availableTracks(Mockito.isA(Integer.class));
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointDelegateRequestToDAO() {
        //Assemble
        var dto = new TrackRequestDto();
        dto.setId(1);
        dto.setOfflineAvailable(true);
        PlaylistModel playlistModelMock = Mockito.mock(PlaylistModel.class);
        playlistModelMock.setId(Mockito.isA(Integer.class));

        //Act
        Response response = trackController.postTrack(playlistModelMock.getId(), TOKEN, dto);

        //Assert
        Mockito.verify(trackDAOMock).cTrack(playlistModelMock.getId(), dto.getId(), dto.isOfflineAvailable());
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointReturnCorrectStatusWithEmptyTracks() {
        //Assemble
        Mockito.when(trackDAOMock.availableTracks(Mockito.isA(Integer.class))).thenReturn(new ArrayList<>());

        //Act
        Response response = trackController.tracks(Mockito.isA(Integer.class), TOKEN);


        //Assert
        Mockito.verify(trackDAOMock).availableTracks(Mockito.isA(Integer.class));
        Assertions.assertEquals(403, response.getStatus());
    }

}