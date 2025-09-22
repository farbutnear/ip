package farquaad.command;

import farquaad.farquaadexception.*;
import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.ui.Ui;
import java.io.IOException;

public class DeadlineCommand extends Command {
    private String arguments;

    public DeadlineCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
        assert arguments != null : "Deadline arguments should not be null";

        if (arguments.trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }

        if (!arguments.contains(" /by ")) {
            throw new MissingDateTimeException("use: deadline <description> /by <date>");
        }

        String[] splits = arguments.split(" /by ", 2);
        if (splits.length != 2 || splits[0].trim().isEmpty() || splits[1].trim().isEmpty()) {
            throw new MissingDateTimeException("deadline");
        }

        Task deadline = new Task.Deadline(splits[0].trim(), splits[1].trim());
        assert deadline != null : "Deadline task should not be null";

        tasks.add(deadline);
        storage.save(tasks.getTasks());
        return ui.displayTaskAdded(deadline, tasks.size());
    }
}