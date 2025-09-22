package farquaad.command;

import farquaad.farquaadexception.*;
import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.ui.Ui;

import java.io.IOException;

public class UnmarkCommand extends Command {
    private String arguments;

    public UnmarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
        if (arguments.isEmpty()) {
            throw new InvalidIndexException();
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

        Task task = tasks.get(taskNo);
        task.unmarkAsNotDone();
        storage.save(tasks.getTasks());
        return ui.displayTaskUnmarked(task);

    }
}
