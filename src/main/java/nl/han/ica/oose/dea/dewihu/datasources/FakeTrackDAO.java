package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseConnection;
import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseProperties;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class FakeTrackDAO extends DatabaseConnection implements TrackDAO{
    @Inject
    public FakeTrackDAO(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    //READ AVAILABLE TRACKS
    public ArrayList<TrackModel> availableTracks(int id) {
        String sql = "SELECT DISTINCT T.ID, T.TITLE, T.PERFORMER, T.DURATION, T.ALBUM, T.PLAYCOUNT, T.PUBLICATIONDATE, T.DESCRIPTION, TP.OFFLINEAVAILABLE " +
                "FROM TRACK T LEFT OUTER JOIN TRACK_IN_PLAYLIST TP " +
                "ON T.ID = TP.TRACK_ID " +
                "WHERE TP.PLAYLIST_ID != " + id + " OR TP.PLAYLIST_ID IS NULL";
        ArrayList<TrackModel> tracks = new ArrayList<>();
        setTracks(tracks, sql);
        return tracks;
    }

    //READ TRACKS IN PLAYLIST
    public ArrayList<TrackModel> tracks(int id) {
        String sql = "SELECT T.ID, T.TITLE, T.PERFORMER, T.DURATION, T.ALBUM, T.PLAYCOUNT, T.PUBLICATIONDATE, T.DESCRIPTION, TP.OFFLINEAVAILABLE " +
                "FROM TRACK T LEFT OUTER JOIN TRACK_IN_PLAYLIST TP ON T.ID = TP.TRACK_ID WHERE TP.PLAYLIST_ID = " + id;
        ArrayList<TrackModel> tracks = new ArrayList<>();
        setTracks(tracks, sql);
        return tracks;
    }

    //DELETE TRACK IN PLAYLIST
    public void dTrack(int pId, int tId) {
        String sql = "DELETE FROM TRACK_IN_PLAYLIST WHERE PLAYLIST_ID = " + pId + " AND TRACK_ID = " + tId;
        executeStatement(sql);
    }

    //ADD TRACK IN PLAYLIST
    public void cTrack(int pId, int tId, boolean oA) {
        int oABit = 0;
        if (oA) oABit = 1;
        String sql = "INSERT INTO TRACK_IN_PLAYLIST (TRACK_ID, PLAYLIST_ID, OFFLINEAVAILABLE) " +
                "VALUES (" + tId + ", " + pId + ", " + oABit + ")";
        executeStatement(sql);
    }

    private void setTracks(ArrayList<TrackModel> tracks, String sql) {
        try {
            Connection conn = DriverManager.getConnection(getDatabaseProperties().connectionString(),
                    getDatabaseProperties().connectionUser(), getDatabaseProperties().connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            getTracks(tracks, st);
            st.close();
            conn.close();
        } catch (SQLException  e) {
            getLogger().log(Level.SEVERE, "Error communicating with database " + getDatabaseProperties().connectionString(), e);
        }
    }

    private void getTracks(ArrayList<TrackModel> tracks, PreparedStatement st) throws SQLException {
        ResultSet rS = st.executeQuery();
        while(rS.next()) {
            TrackModel track = new TrackModel();
            track.setId(rS.getInt("ID"));
            track.setTitle(rS.getString("TITLE"));
            track.setPerformer(rS.getString("PERFORMER"));
            track.setDuration(rS.getInt("DURATION"));
            track.setAlbum(rS.getString("ALBUM"));
            track.setPlaycount(rS.getInt("PLAYCOUNT"));
            track.setPublicationDate(rS.getString("PUBLICATIONDATE"));
            track.setDescription(rS.getString("DESCRIPTION"));
            track.setOfflineAvailable(rS.getBoolean("OFFLINEAVAILABLE"));
            tracks.add(track);
        }
    }
}
