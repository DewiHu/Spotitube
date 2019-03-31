package nl.han.ica.oose.dea.dewihu.services;

import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import java.util.ArrayList;

public class PlaylistService {
    public int length(ArrayList<PlaylistModel> playlists) {
        int length = 0;

        for (PlaylistModel p : playlists) {
            if(!p.getTracks().isEmpty()) {
                for (TrackModel t : p.getTracks()) {
                    length += t.getDuration();
                }
            }
        }

        return length;
    }
}
