package perondi.BeSuffle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perondi.BeSuffle.Entity.DailyAlbum;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyAlbumRepository extends JpaRepository<DailyAlbum, Long> {

    Optional<DailyAlbum> findByDate(LocalDate date);

}