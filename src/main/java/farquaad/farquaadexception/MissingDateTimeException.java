package farquaad.farquaadexception;

public class MissingDateTimeException extends FarquaadException {
    public MissingDateTimeException(String command) {
        super("can you provide the required date/time information for " + command + "...");
    }
}