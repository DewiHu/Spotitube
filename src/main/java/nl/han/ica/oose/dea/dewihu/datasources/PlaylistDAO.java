package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseProperties;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistDAO{
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;

    @Inject
    public PlaylistDAO(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        tryLoadJdbcDriver(databaseProperties);
    }
    public ArrayList<PlaylistModel> playlists(String token) {
        String sqlPlaylist = "SELECT * FROM PLAYLIST";

        ArrayList<PlaylistModel> playlists = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString(), databaseProperties.connectionUser(), databaseProperties.connectionPassword());
            PreparedStatement statement = connection.prepareStatement(sqlPlaylist);
            setPlaylists(token, playlists, statement);
            statement.close();
            connection.close();
        }catch (SQLException e) {
            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
        }
        return playlists;
    }

    private void setPlaylists(String token, ArrayList<PlaylistModel> playlists, PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            PlaylistModel playlist = new PlaylistModel();
            playlist.setId(resultSet.getInt("ID"));
            playlist.setName(resultSet.getString("NAME"));
            playlist.setOwner(token.equals(resultSet.getString("TOKEN")));
            playlist.setTracks(new ArrayList<>());
            playlists.add(playlist);
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
