package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseProperties;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackDAO {
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    @Inject
    public TrackDAO (DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        tryLoadJdbcDriver(databaseProperties);
    }

    public ArrayList<TrackModel> tracks(int id) {
        String sql = "SELECT * FROM TRACK_IN_PLAYLIST TP INNER JOIN TRACK T ON TP.TRACK_ID = T.ID WHERE PLAYLIST_ID = " + id;

        ArrayList<TrackModel> tracks = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(databaseProperties.connectionString(), databaseProperties.connectionUser(), databaseProperties.connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            setTracks(tracks, st);
            st.close();
            conn.close();
        } catch (SQLException  e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }

        return tracks;
    }

    private void setTracks(ArrayList<TrackModel> tracks, PreparedStatement statement) throws SQLException{
        ResultSet rS = statement.executeQuery();
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

    private void tryLoadJdbcDriver(DatabaseProperties databaseProperties) {
        try{
            Class.forName(databaseProperties.driver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Can't load JDBC Driver " + databaseProperties.driver(), e);
        }
    }
}
