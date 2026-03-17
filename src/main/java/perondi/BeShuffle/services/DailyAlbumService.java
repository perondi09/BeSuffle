package perondi.BeShuffle.services;

import org.springframework.stereotype.Service;
import perondi.BeShuffle.dtos.album.Album;
import perondi.BeShuffle.entities.DailyAlbum;
import perondi.BeShuffle.repositories.DailyAlbumRepository;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DailyAlbumService {

    private final DailyAlbumRepository dailyAlbumRepository;
    private final AlbumService albumService;
    private final ObjectMapper objectMapper;

    public DailyAlbumService(
            DailyAlbumRepository dailyAlbumRepository,
            AlbumService albumService,
            ObjectMapper objectMapper
    ) {
        this.dailyAlbumRepository = dailyAlbumRepository;
        this.albumService = albumService;
        this.objectMapper = objectMapper;
    }

    /**
     * Retorna o álbum do dia atual, ou null se não existir
     */
    public DailyAlbum getTodayAlbum() {
        return dailyAlbumRepository.findByDisplayDate(LocalDate.now())
                .orElse(null);
    }

    /**
     * Registra um novo álbum como o álbum do dia
     */
    public DailyAlbum setDailyAlbum(String spotifyAlbumId) {
        // Verifica se o álbum já foi exibido
        Optional<DailyAlbum> existingAlbum =
                dailyAlbumRepository.findBySpotifyAlbumId(spotifyAlbumId);

        if (existingAlbum.isPresent()) {
            throw new IllegalArgumentException("Este álbum já foi exibido!");
        }

        // Busca o álbum no Spotify
        Album spotifyAlbum = albumService.getAlbumById(spotifyAlbumId);

        // Cria e salva o registro
        DailyAlbum dailyAlbum = new DailyAlbum();
        dailyAlbum.setSpotifyAlbumId(spotifyAlbum.getId());
        dailyAlbum.setAlbumName(spotifyAlbum.getName());
        dailyAlbum.setArtistName(spotifyAlbum.getArtists().get(0).getName());
        dailyAlbum.setImageUrl(spotifyAlbum.getImages().get(0).getUrl()); // Maior imagem
        dailyAlbum.setAlbumUrl(spotifyAlbum.getUri());
        dailyAlbum.setReleaseDate(spotifyAlbum.getReleaseDate());
        dailyAlbum.setDisplayDate(LocalDate.now());

        try {
            dailyAlbum.setFullAlbumJson(objectMapper.writeValueAsString(spotifyAlbum));
        } catch (Exception e) {
            // Log error if needed
        }

        return dailyAlbumRepository.save(dailyAlbum);
    }
}
