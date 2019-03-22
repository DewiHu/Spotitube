package nl.han.ica.oose.dea.dewihu.controllers.dto;

public class PlaylistRequestDto {
    private String token;

    public PlaylistRequestDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
