package vehiclerental;

import java.time.LocalTime;

public interface Rentable extends Comparable {

    int calculateSumPrice(long minutes);
    LocalTime getRentingTime();
    void rent(LocalTime time);
    void loseRent();

    @Override
    default int compareTo(Object o) {
        return this.getRentingTime().compareTo(((Rentable) o).getRentingTime());
    }
}