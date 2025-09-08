package farquaad.command;

import farquaad.*;
import farquaad.farquaadexception.*;
import farquaad.storage.Storage;
import farquaad.ui.Ui;
import java.io.IOException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException;

    public boolean isExit() {
        return false;
    }
}









