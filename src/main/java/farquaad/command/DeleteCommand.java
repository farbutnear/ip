package farquaad.command;

import farquaad.farquaadexception.*;
import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.ui.Ui;
import java.io.IOException;

public class DeleteCommand extends Command {
    private String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
        if (arguments.trim().isEmpty()) {
            throw new EmptyDescriptionException("delete");
        }

        int taskNo;
        try {
            taskNo = Integer.parseInt(arguments.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidIndexException();
        }

        if (!tasks.isValidIndex(taskNo)) {
            throw new InvalidIndexException();
        }

        Task removed = tasks.remove(taskNo);
        ui.displayTaskDeleted(removed, tasks.size());
        storage.save(tasks.getTasks());
    }
}
