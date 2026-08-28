package friday;

/**
 * Represents an exception caused by invalid Friday input or storage issues.
 */
public class FridayException extends Exception {

    /**
     * Creates a Friday exception with the given message.
     */
    public FridayException(String message) {
        super(message);
    }
}
