package vehiclerental;

import java.time.LocalTime;
import java.util.Objects;

public class Car implements Rentable {

    // --- attributes ---------------------------------------------------------

    private String id;
    private int pricePerMinute;
    private LocalTime rentingTime;

    // --- constructors -------------------------------------------------------

    public Car(String id, int pricePerMinute) {
        this.id = id;
        this.pricePerMinute = pricePerMinute;
    }

    // --- getters and setters ------------------------------------------------

    public String getId() { return id; }
    public int getPricePerMinute() { return pricePerMinute; }

    @Override
    public LocalTime getRentingTime() { return rentingTime; }

    // --- public methods -----------------------------------------------------

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) (pricePerMinute * minutes);
    }

    @Override
    public void rent(LocalTime time) {
        rentingTime = time;
    }

    @Override
    public void closeRent() {
        rentingTime = null;
    }
}