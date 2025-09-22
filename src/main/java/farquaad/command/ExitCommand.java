package farquaad.command;

import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return  ui.displayGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}