package movietheatres;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {

    // --- attributes ---------------------------------------------------------

    private Map<String, List<Movie>> shows;

    // --- constructors -------------------------------------------------------

    public MovieTheatreService() {
        shows = new LinkedHashMap<>();
    }

    // --- getters and setters ------------------------------------------------

    public Map<String, List<Movie>> getShows() { return shows; }

    // --- public methods -----------------------------------------------------

    public void readFromFile(Path path) {
        List<String> dataLines = readDataLines(path);
        fillShowList(dataLines);
        sortShowList();
    }

    public List<String> findMovie(String title) {
        return shows.keySet().stream()
            .filter(movieTheatre ->
                shows.get(movieTheatre).stream().anyMatch(movie -> movie.getTitle().equals(title)))
            .toList();
    }

    public List<String> findMovieWithoutStreams(String title) {
        List<String> movieTheatresByTitle = new ArrayList<>();
        for (String movieTheatre : shows.keySet()) {
            updateMovieTheatresByTitle(movieTheatresByTitle, movieTheatre, title);
        }
        return movieTheatresByTitle;
    }

    public LocalTime findLatestShow(String title) {
        return shows.values().stream()
            .flatMap(Collection::stream)
            .filter(movie -> movie.getTitle().equals(title))
            .map(Movie::getStartTime)
            .max(LocalTime::compareTo)
            .orElseThrow(() -> new IllegalArgumentException("Invalid title."));
    }

    public LocalTime findLatestShowWithoutStreams(String title) {
        boolean isFound = false;
        Time latestShowByTitle = new Time(0);
        for (List<Movie> value : shows.values()) {
            isFound |= updateLatestShowByTitle(value, latestShowByTitle, title);
        }
        return getLatestShow(latestShowByTitle, isFound);
    }

    // --- private methods ----------------------------------------------------

    private List<String> readDataLines(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot read file.");
        }
    }

    private void fillShowList(List<String> dataLines) {
        for (String dataLine : dataLines) {
            String movieTheatre = dataLine.split("-")[0];
            String title = dataLine.split("-")[1].split(";")[0];
            LocalTime startTime = LocalTime.parse(dataLine.split("-")[1].split(";")[1]);
            if (!shows.containsKey(movieTheatre)) {
                shows.put(movieTheatre, new ArrayList<>());
            }
            shows.get(movieTheatre).add(new Movie(title, startTime));
        }
    }

    private void sortShowList() {
        for (String movieTheatre : shows.keySet()) {
            shows.get(movieTheatre).sort(Comparator.comparing(Movie::getStartTime));
        }
    }

    private void updateMovieTheatresByTitle(List<String> movieTheatres, String movieTheatre, String title) {
        for (Movie movie : shows.get(movieTheatre)) {
            if (movie.getTitle().equals(title)) {
                if (!movieTheatres.contains(movieTheatre)) {
                    movieTheatres.add(movieTheatre);
                }
            }
        }
    }

    private boolean updateLatestShowByTitle(List<Movie> movies, Time latestShowByTitle, String title) {
        boolean isFound = false;
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                isFound = true;
                if (latestShowByTitle != null &&
                    movie.getStartTime().isAfter(latestShowByTitle.toLocalTime())) {
                    latestShowByTitle.setTime(Time.valueOf(movie.getStartTime()).getTime());
                }
            }
        }
        return isFound;
    }

    private LocalTime getLatestShow(Time latestShowByTitle, boolean isFound) {
        if (isFound) {
            return latestShowByTitle.toLocalTime();
        }
        throw new IllegalArgumentException("Invalid title");
    }
}