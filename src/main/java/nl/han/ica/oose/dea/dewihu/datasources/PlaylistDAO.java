package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import java.util.ArrayList;

public interface PlaylistDAO {
    ArrayList<PlaylistModel> playlists(String token);

    void dPlaylist(int id, String token);

    void cPlaylist(String name, String token);

    void uPlaylist(int id, String name, String token);

    int length(ArrayList<PlaylistModel> playlists);
}
