package farquaad.farquaadexception;

public class EmptyDescriptionException extends FarquaadException {
    public EmptyDescriptionException(String task) {
        super("lmao the description of a " + task + " cannot be empty.");
    }
}