package vehiclerental;

import java.util.Objects;

public class User {

    // --- attributes ---------------------------------------------------------

    private String userName;
    private String email;
    private int balance;

    // --- constructors -------------------------------------------------------

    public User(String userName, String email, int balance) {
        this.userName = userName;
        this.email = email;
        this.balance = balance;
    }

    // --- getters and setters ------------------------------------------------

    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public int getBalance() { return balance; }

    // --- public methods -----------------------------------------------------

    public void minusBalance(int amount) {
        balance -= amount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}