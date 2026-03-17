package perondi.BeShuffle.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import perondi.BeShuffle.dtos.album.Album;

@FeignClient(
        name = "AlbumSpotifyClient",
        url = "https://api.spotify.com"
)
public interface AlbumSpotifyClient {

    @GetMapping(value = "/v1/albums/{id}")
    Album getAlbum(@RequestHeader("Authorization") String authorization, @PathVariable("id") String id);
}
