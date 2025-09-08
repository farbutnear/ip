package farquaad.farquaadexception;

public class InvalidDateFormatException extends FarquaadException {
    public InvalidDateFormatException(String command) {
        super("oi provide a valid date format for " + command + ". " +
                "Examples: 2024-12-25, 25/12/2024, Dec 25 2024");
    }
}