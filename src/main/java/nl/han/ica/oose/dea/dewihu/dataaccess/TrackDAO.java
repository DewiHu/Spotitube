package nl.han.ica.oose.dea.dewihu.dataaccess;

import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import java.util.ArrayList;

public interface TrackDAO {
    ArrayList<TrackModel> tracks(int id);

    ArrayList<TrackModel> availableTracks(int forPlaylist);

    void dTrack(int pId, int tId);

    void cTrack(int pId, int tId, boolean offlineAvailable);
}
