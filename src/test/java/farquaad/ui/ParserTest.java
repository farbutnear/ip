package farquaad.ui;

import farquaad.Parser;
import farquaad.command.*;
import farquaad.farquaadexception.*;
import farquaad.command.ToDoCommand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_validCommands_returnsCorrectCommandTypes() throws FarquaadException {
        assertTrue(Parser.parse("list") instanceof ListCommand);
        assertTrue(Parser.parse("bye") instanceof ExitCommand);
        assertTrue(Parser.parse("todo read book") instanceof ToDoCommand);
        assertTrue(Parser.parse("mark 3") instanceof MarkCommand);
        assertTrue(Parser.parse("unmark 1") instanceof UnmarkCommand);
        assertTrue(Parser.parse("delete 2") instanceof DeleteCommand);

        assertTrue(Parser.parse("deadline submit /by 2014-10-14") instanceof DeadlineCommand);
        assertTrue(Parser.parse("event meeting /from 1am /to 3am") instanceof EventCommand);
    }

    @Test
    public void parse_emptyInput_throwsUnknownCommandException() {
        assertThrows(UnknownCommandException.class, () -> {
            Parser.parse("");
        });
    }

    @Test
    public void parse_unknownCommand_throwsUnknownCommandException() {
        assertThrows(UnknownCommandException.class, () -> {
            Parser.parse("unknown");
        });

        assertThrows(UnknownCommandException.class, () -> {
            Parser.parse("invalidcommand");
        });

        assertThrows(UnknownCommandException.class, () -> {
            Parser.parse("123");
        });
    }

    @Test
    public void parse_commandsWithNoArguments_handlesCorrectly() throws FarquaadException {
        Command listCmd = Parser.parse("list");
        assertFalse(listCmd.isExit());

        Command exitCmd = Parser.parse("bye");
        assertTrue(exitCmd.isExit());
    }

    @Test
    public void parse_commandsWithArguments_preservesArguments() throws FarquaadException {
        assertTrue(Parser.parse("todo buy books and cycle") instanceof ToDoCommand);
        assertTrue(Parser.parse("deadline peer eval /by Sunday") instanceof DeadlineCommand);
        assertTrue(Parser.parse("event tutorial /from 1pm /to 3pm") instanceof EventCommand);
    }
}