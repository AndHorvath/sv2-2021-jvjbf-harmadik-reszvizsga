package vehiclerental;

import java.time.LocalTime;

public class Bike implements Rentable {

    private String id;
    private LocalTime rentingTime;

    public Bike(String id) {
        this.id = id;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) minutes * 15;
    }

    @Override
    public LocalTime getRentingTime() {
        return null;
    }

    @Override
    public void rent(LocalTime time) {

    }

    @Override
    public void loseRent() {

    }
}