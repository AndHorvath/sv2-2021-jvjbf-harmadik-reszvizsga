package vehiclerental;

import java.time.LocalTime;
import java.util.*;

public class RentService {

    // --- attributes ---------------------------------------------------------

    private static final int MAXIMAL_RENTING_TIME = 180;

    private Set<Rentable> rentables;
    private Set<User> users;
    private Map<Rentable, User> actualRenting;

    // --- constructors -------------------------------------------------------

    public RentService() {
        rentables = new HashSet<>();
        users = new HashSet<>();
        actualRenting = new TreeMap<>();
    }

    // --- getters and setters ------------------------------------------------

    public Set<Rentable> getRentables() { return rentables; }
    public Set<User> getUsers() { return users; }
    public Map<Rentable, User> getActualRenting() { return actualRenting; }

    // --- public methods -----------------------------------------------------

    public void registerUser(User user) {
        addUserOrHandleDuplication(user);
    }

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public void rent(User user, Rentable rentable, LocalTime rentingTime) {
        validateParameters(user, rentable, rentingTime);

        rentable.rent(rentingTime);
        actualRenting.put(rentable, user);
    }

    public void closeRent(Rentable rentable, int minutes) {
        validateParameters(rentable, minutes);

        User user = actualRenting.remove(rentable);
        user.minusBalance(rentable.calculateSumPrice(minutes));
        rentable.closeRent();
    }

    // --- private methods ----------------------------------------------------

    private void addUserOrHandleDuplication(User user) {
        if (!users.add(user)) {
            throw new UserNameIsAlreadyTakenException();
        }
    }

    private void validateParameters(User user, Rentable rentable, LocalTime rentingTime) {
        validateUser(user);
        validateRentable(rentable);
        validateRentingConditions(user, rentable);
        validateFinancialConditions(user, rentable);
    }

    private void validateParameters(Rentable rentable, int minutes) {
        validateRentedVehicle(rentable);
        validateRentingTime(minutes);
    }

    private void validateUser(User user) {
        if (!users.contains(user)) {
            throw new IllegalArgumentException(
                "Specified user has not yet been registered.");
        }
    }

    private void validateRentable(Rentable rentable) {
        if (!rentables.contains(rentable)) {
            throw new IllegalArgumentException(
                "Specified vehicle has not yet been included in the fleet.");
        }
    }

    private void validateRentingConditions(User user, Rentable rentable) {
        if (rentable.getRentingTime() != null) {
            throw new IllegalStateException("Specified vehicle is currently rented.");
        } else if (actualRenting.containsValue(user)) {
            throw new IllegalStateException("Specified user is currently renting a vehicle.");
        }
    }

    private void validateFinancialConditions(User user, Rentable rentable) {
        if (user.getBalance() < rentable.calculateSumPrice(MAXIMAL_RENTING_TIME)) {
            throw new IllegalStateException("Insufficient purchasing power.");
        }
    }

    private void validateRentedVehicle(Rentable rentable) {
        if (!actualRenting.containsKey(rentable)) {
            throw new IllegalStateException("Specified vehicle is not rented");
        }
    }

    private void validateRentingTime(int minutes) {
        if (minutes > MAXIMAL_RENTING_TIME) {
            throw new IllegalStateException("Renting time must not be longer than 3 hours.");
        }
    }
}