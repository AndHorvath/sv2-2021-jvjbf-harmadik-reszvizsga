package streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SongService {

    // --- attributes ---------------------------------------------------------

    private List<Song> songs;

    // --- constructors -------------------------------------------------------

    public SongService() {
        songs = new ArrayList<>();
    }

    // --- getters and setters ------------------------------------------------

    public List<Song> getSongs() { return songs; }

    // --- public methods -----------------------------------------------------

    public void addSong(Song song) {
        songs.add(song);
    }

    public Optional<Song> shortestSong() {
        return songs.stream().min(Comparator.comparingInt(Song::getLength));
    }

    public List<Song> findSongByTitle(String title) {
        return songs.stream().filter(song -> song.getTitle().equals(title)).toList();
    }

    public boolean isPerformerInSong(Song song, String performer) {
        return song.getPerformers().stream().anyMatch(p -> p.equals(performer));
    }

    public List<String> titlesBeforeDate(LocalDate date) {
        return songs
            .stream()
            .filter(song -> song.getRelease().isBefore(date))
            .map(Song::getTitle)
            .toList();
    }
}