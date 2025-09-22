package farquaad.command;

import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.ui.Ui;

public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        String message = ui.displayTaskList(tasks);
        return message;
    }
}