package farquaad;

import farquaad.command.*;
import farquaad.farquaadexception.*;
import farquaad.farquaadexception.UnknownCommandException;

public class Parser {
    public static Command parse(String input) throws FarquaadException {
        if (input.isEmpty()) {
            throw new UnknownCommandException();
        }

        String[] parts = input.split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String remaining = parts.length > 1 ? parts[1] : "";

        switch (commandWord) {
            case "list":
                return new ListCommand();
            case "delete":
                return new DeleteCommand(remaining);
            case "bye":
                return new ExitCommand();
            case "mark":
                return new MarkCommand(remaining);
            case "unmark":
                return new UnmarkCommand(remaining);
            case "todo":
                return new ToDoCommand(remaining);
            case "deadline":
                return new DeadlineCommand(remaining);
            case "event":
                return new EventCommand(remaining);
            default:
                throw new UnknownCommandException();
        }
    }
}
