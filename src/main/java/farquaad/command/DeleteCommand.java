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
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
        assert arguments != null : "Delete arguments should not be null";
        if (arguments.trim().isEmpty()) {
            throw new EmptyDescriptionException("delete");
        }

        int taskNo;
        try {
            taskNo = Integer.parseInt(arguments.trim()) - 1;
            assert tasks.isValidIndex(taskNo) : "Index should be valid";
        } catch (NumberFormatException e) {
            throw new InvalidIndexException();
        }

        if (!tasks.isValidIndex(taskNo)) {
            throw new InvalidIndexException();
        }

        Task removed = tasks.remove(taskNo);
        storage.save(tasks.getTasks());
        return ui.displayTaskDeleted(removed, tasks.size());
    }
}
