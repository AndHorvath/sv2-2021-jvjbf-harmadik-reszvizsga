package movietheatres;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        List<String> movieTheatres = new ArrayList<>();
        for (String movieTheatre : shows.keySet()) {
            updateMovieTheatres(movieTheatres, movieTheatre, title);
        }
        return movieTheatres;
    }

    public LocalTime findLatestShow(String title) {
        boolean isFound = false;
        LocalTime latestShow = LocalTime.of(0, 0);
        for (List<Movie> value : shows.values()) {
            for (Movie movie : value) {
                if (movie.getTitle().equals(title)) {
                    isFound = true;
                    if (movie.getStartTime().isAfter(latestShow)) {
                        latestShow = movie.getStartTime();
                    }
                }
            }
        }
        if (!isFound) {
            throw new IllegalArgumentException("Invalid title.");
        } else {
            return latestShow;
        }
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

    private void updateMovieTheatres(List<String> movieTheatres, String movieTheatre, String title) {
        for (Movie movie : shows.get(movieTheatre)) {
            if (movie.getTitle().equals(title)) {
                if (!movieTheatres.contains(movieTheatre)) {
                    movieTheatres.add(movieTheatre);
                }
            }
        }
    }
}