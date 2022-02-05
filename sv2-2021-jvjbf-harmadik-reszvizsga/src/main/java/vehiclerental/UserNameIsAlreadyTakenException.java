package vehiclerental;

public class UserNameIsAlreadyTakenException extends RuntimeException {

    // --- attributes ---------------------------------------------------------

    private static final String DEFAULT_MESSAGE = "Username is taken!";

    // --- constructors -------------------------------------------------------

    public UserNameIsAlreadyTakenException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNameIsAlreadyTakenException(String message) {
        super(message);
    }

    public UserNameIsAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameIsAlreadyTakenException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}