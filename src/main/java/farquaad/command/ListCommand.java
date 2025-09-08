package farquaad.command;

import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.displayTaskList(tasks);
    }
}