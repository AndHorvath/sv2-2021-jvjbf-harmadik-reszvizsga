package vehiclerental;

import java.time.LocalTime;

public class Car implements Rentable {

    @Override
    public int calculateSumPrice(long minutes) {
        return 0;
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