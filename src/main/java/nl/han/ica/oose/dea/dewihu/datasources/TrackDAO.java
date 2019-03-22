package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import java.util.ArrayList;

public class TrackDAO {
    public ArrayList<TrackModel> tracks(int id, String token) {
        var tracks = new ArrayList<TrackModel>();

        tracks.add(new TrackModel());
        tracks.get(0).setId(1);
        tracks.get(0).setTitle("test titel");
        tracks.get(0).setPerformer("test performer");
        tracks.get(0).setDuration(123);
        tracks.get(0).setAlbum("test album");
        tracks.get(0).setPlaycount(0);
        tracks.get(0).setPublicationDate("01-01-2000");
        tracks.get(0).setDescription("test description");
        tracks.get(0).setOfflineAvailable(false);

        return tracks;
    }

    public ArrayList<TrackModel> tracks() {
        var tracks = new ArrayList<TrackModel>();

        tracks.add(new TrackModel());
        tracks.get(0).setId(1);
        tracks.get(0).setTitle("test titel");
        tracks.get(0).setPerformer("test performer");
        tracks.get(0).setDuration(123);
        tracks.get(0).setAlbum("test album");
        tracks.get(0).setPlaycount(0);
        tracks.get(0).setPublicationDate("01-01-2000");
        tracks.get(0).setDescription("test description");
        tracks.get(0).setOfflineAvailable(false);

        tracks.get(0).setId(2);
        tracks.get(0).setTitle("test titel");
        tracks.get(0).setPerformer("test performer");
        tracks.get(0).setDuration(123);
        tracks.get(0).setAlbum("test album");
        tracks.get(0).setPlaycount(0);
        tracks.get(0).setPublicationDate("01-01-2000");
        tracks.get(0).setDescription("test description");
        tracks.get(0).setOfflineAvailable(false);

        return tracks;
    }
}
