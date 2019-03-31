package nl.han.ica.oose.dea.dewihu.datasources;

import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseConnection;
import nl.han.ica.oose.dea.dewihu.datasources.util.DatabaseProperties;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class PlaylistDAO extends DatabaseConnection {

    @Inject
    public PlaylistDAO(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    //READ ALL
    public ArrayList<PlaylistModel> rAllPlaylists(String token) {
        String sql = "SELECT * FROM PLAYLIST";

        ArrayList<PlaylistModel> playlists = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(getDatabaseProperties().connectionString(),
                    getDatabaseProperties().connectionUser(), getDatabaseProperties().connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            setAllPlaylists(playlists, token, st);
            st.close();
            conn.close();
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, "Error communicating with database " + getDatabaseProperties().connectionString(), e);
        }

        return playlists;
    }


    //DELETE
    public void dPlaylist(int id) {
        String sql = "DELETE FROM PLAYLIST WHERE ID = " + id;
        executeStatement(sql);
    }

    //CREATE
    public void cPlaylist(String name, String token) {
        String sql = "INSERT INTO PLAYLIST (NAME, TOKEN) VALUES ('" + name + "', '" + token + "')";
        executeStatement(sql);
    }

    //UPDATE
    public void uPlaylist(int id, String name) {
        String sql = "UPDATE PLAYLIST SET NAME = '" + name + "' WHERE ID = " + id;
        executeStatement(sql);
    }

    private void setAllPlaylists(ArrayList<PlaylistModel> playlists, String token, PreparedStatement st) throws SQLException {
        ResultSet rS = st.executeQuery();
        while(rS.next()) {
            var playlist = new PlaylistModel();
            playlist.setId(rS.getInt("ID"));
            playlist.setName(rS.getString("NAME"));
            playlist.setOwner(rS.getString("TOKEN").equals(token));
            playlist.setTracks(new ArrayList<>());
            playlists.add(playlist);
        }
    }
}
