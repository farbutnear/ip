package farquaad.farquaadexception;

public class InvalidIndexException extends FarquaadException {
    public InvalidIndexException() {
        super("the farquaad.task number is invalid.");
    }
}