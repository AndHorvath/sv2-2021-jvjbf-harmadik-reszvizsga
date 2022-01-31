package movietheatres;

import java.time.LocalTime;
import java.util.Objects;

public class Movie {

    // --- attributes ---------------------------------------------------------

    private String title;
    private LocalTime startTime;

    // --- constructors -------------------------------------------------------

    public Movie(String title, LocalTime startTime) {
        this.title = title;
        this.startTime = startTime;
    }

    // --- getters and setters ------------------------------------------------

    public String getTitle() { return title; }
    public LocalTime getStartTime() { return startTime; }

    // --- public methods -----------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}