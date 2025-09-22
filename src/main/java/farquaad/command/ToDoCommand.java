package farquaad.command;

import farquaad.farquaadexception.*;
import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.ui.Ui;
import java.io.IOException;

public class ToDoCommand extends Command {
    private String arguments;

    public ToDoCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
        if (arguments.trim().isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }

        Task todo = new Task.ToDo(arguments.trim());
        tasks.add(todo);
        storage.save(tasks.getTasks());
        return ui.displayTaskAdded(todo, tasks.size());

    }
}