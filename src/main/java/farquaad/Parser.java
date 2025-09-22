package farquaad;

import farquaad.command.Command;
import farquaad.command.DeadlineCommand;
import farquaad.command.DeleteCommand;
import farquaad.command.EventCommand;
import farquaad.command.ExitCommand;
import farquaad.command.ListCommand;
import farquaad.command.MarkCommand;
import farquaad.command.ToDoCommand;
import farquaad.command.UnmarkCommand;
import farquaad.command.FindCommand;
import farquaad.farquaadexception.FarquaadException;
import farquaad.farquaadexception.UnknownCommandException;

/**
 * Parses user input into commands for execution.
 */
public class Parser {

    /**
     * Converts the given user input string into a {@code Command}.
     *
     * @param input Input string entered by the user.
     * @return The {@code Command} corresponding to the input.
     * @throws FarquaadException If the input does not match any known command.
     */
    public static Command parse(String input) throws FarquaadException {
        if (input.isEmpty()) {
            throw new UnknownCommandException();
        }
        assert input != null : "Input string should not be null";

        String[] parts = input.split(" ", 2);
        assert parts.length > 0 : "Parsed words should contain at least the command word";
        String commandWord = parts[0].toLowerCase();
        assert !commandWord.isEmpty() : "Command word should not be empty";
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
        case "find":
            return new FindCommand(remaining);
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
