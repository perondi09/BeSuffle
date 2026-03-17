package perondi.BeShuffle.services;

import org.springframework.stereotype.Service;
import perondi.BeShuffle.client.AlbumSpotifyClient;
import perondi.BeShuffle.client.AuthSpotifyClient;
import perondi.BeShuffle.dtos.album.Album;

@Service
public class AlbumService {

    private final AlbumSpotifyClient albumSpotifyClient;
    private final AuthService authService;

    public AlbumService(AlbumSpotifyClient albumSpotifyClient, AuthService authService) {
        this.albumSpotifyClient = albumSpotifyClient;
        this.authService = authService;
    }

    public Album getAlbumById(String albumId) {
        String token = authService.getAccessToken();
        return albumSpotifyClient.getAlbum("Bearer " + token, albumId);
    }
}