package farquaad;

import farquaad.command.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_validCommands_returnsCorrectCommandTypes() throws FarquaadException {
        // Test basic commands
        assertTrue(Parser.parse("list") instanceof ListCommand);
        assertTrue(Parser.parse("bye") instanceof ExitCommand);

        // Test commands with arguments
        assertTrue(Parser.parse("todo buy milk") instanceof TodoCommand);
        assertTrue(Parser.parse("mark 1") instanceof MarkCommand);
        assertTrue(Parser.parse("unmark 2") instanceof UnmarkCommand);
        assertTrue(Parser.parse("delete 3") instanceof DeleteCommand);

        // Test complex commands
        assertTrue(Parser.parse("deadline submit /by 2024-12-25") instanceof DeadlineCommand);
        assertTrue(Parser.parse("event meeting /from 2pm /to 4pm") instanceof EventCommand);
    }

    @Test
    public void parse_caseInsensitiveCommands_returnsCorrectTypes() throws FarquaadException {
        // Test case insensitivity
        assertTrue(Parser.parse("LIST") instanceof ListCommand);
        assertTrue(Parser.parse("Todo buy milk") instanceof TodoCommand);
        assertTrue(Parser.parse("BYE") instanceof ExitCommand);
        assertTrue(Parser.parse("MaRk 1") instanceof MarkCommand);
    }

    @Test
    public void parse_commandsWithExtraSpaces_handlesCorrectly() throws FarquaadException {
        // Test handling of extra spaces
        assertTrue(Parser.parse("  list  ") instanceof ListCommand);
        assertTrue(Parser.parse("todo    buy milk   ") instanceof TodoCommand);
        assertTrue(Parser.parse("mark    1") instanceof MarkCommand);
    }

    @Test
    public void parse_emptyInput_throwsUnknownCommandException() {
        // Test empty input
        assertThrows(UnknownCommandException.class, () -> {
            Parser.parse("");
        });
    }

    @Test
    public void parse_unknownCommand_throwsUnknownCommandException() {
        // Test unknown commands
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
        // Test commands that don't require arguments
        Command listCmd = Parser.parse("list");
        assertFalse(listCmd.isExit());

        Command exitCmd = Parser.parse("bye");
        assertTrue(exitCmd.isExit());
    }

    @Test
    public void parse_commandsWithArguments_preservesArguments() throws FarquaadException {
        // Test that arguments are properly passed to commands
        // Note: We can't directly test the arguments without accessing private fields,
        // but we can test that the right command type is created
        assertTrue(Parser.parse("todo buy groceries and cook dinner") instanceof TodoCommand);
        assertTrue(Parser.parse("deadline project submission /by tomorrow") instanceof DeadlineCommand);
        assertTrue(Parser.parse("event team meeting /from 2pm /to 4pm today") instanceof EventCommand);
    }
}