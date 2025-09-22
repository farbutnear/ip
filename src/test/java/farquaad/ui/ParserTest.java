package farquaad.ui;

import farquaad.Parser;
import farquaad.command.*;
import farquaad.farquaadexception.FarquaadException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_validTodoCommand_returnsToDoCommand() throws FarquaadException {
        Command c = Parser.parse("todo read book");
        assertTrue(c instanceof ToDoCommand);
    }

    @Test
    public void parse_validDeadlineCommand_returnsDeadlineCommand() throws FarquaadException {
        Command c = Parser.parse("deadline submit report /by 2025-09-25");
        assertTrue(c instanceof DeadlineCommand);
    }

    @Test
    public void parse_validEventCommand_returnsEventCommand() throws FarquaadException {
        Command c = Parser.parse("event project meeting /from 2025-09-23 /to 2025-09-24");
        assertTrue(c instanceof EventCommand);
    }

    @Test
    public void parse_validListCommand_returnsListCommand() throws FarquaadException {
        Command c = Parser.parse("list");
        assertTrue(c instanceof ListCommand);
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        assertThrows(FarquaadException.class, () -> Parser.parse("blah blah"));
    }

    @Test
    public void parse_emptyInput_throwsException() {
        assertThrows(FarquaadException.class, () -> Parser.parse(""));
    }
}
