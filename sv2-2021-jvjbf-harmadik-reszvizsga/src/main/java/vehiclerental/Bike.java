package vehiclerental;

import java.time.LocalTime;

public class Bike implements Rentable {

    // --- attributes ---------------------------------------------------------

    private static final int PRICE_PER_MINUTE = 15;

    private String id;
    private LocalTime rentingTime;

    // --- constructors -------------------------------------------------------

    public Bike(String id) {
        this.id = id;
    }

    // --- getters and setters ------------------------------------------------

    public String getId() { return id; }

    @Override
    public LocalTime getRentingTime() { return rentingTime; }

    // --- public methods -----------------------------------------------------

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) (PRICE_PER_MINUTE * minutes);
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