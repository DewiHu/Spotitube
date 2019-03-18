package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import java.util.ArrayList;

public class PlaylistDAO {

    public ArrayList<PlaylistModel> playlists(String token) {

        var playlists = new ArrayList<PlaylistModel>();

        if("dfkmfsd-qeeqw-8234nk".equals(token)) {
            playlists.add(new PlaylistModel());
            playlists.get(0).setId(1);
            playlists.get(0).setName("Heavy Metal");
            playlists.get(0).setOwner(true);

            playlists.add(new PlaylistModel());
            playlists.get(1).setId(2);
            playlists.get(1).setName("Pop");
            playlists.get(1).setOwner(false);
        }

        return playlists;
    }
}
