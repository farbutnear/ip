package farquaad.command;

import farquaad.*;
import farquaad.farquaadexception.*;
import farquaad.storage.Storage;
import farquaad.ui.Ui;
import java.io.IOException;

/**
 * Represents a command that can be executed by the program.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks   The task list that the command operates on.
     * @param ui      The UI object for user interaction.
     * @param storage The storage object used for saving/loading tasks.
     * @throws Exception If the command cannot be executed successfully.
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException;

    /**
     * Returns whether this command signals the program to exit.
     *
     * @return {@code true} if the program should exit; {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }
}









