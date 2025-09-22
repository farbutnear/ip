package farquaad.command;

import farquaad.farquaadexception.*;
import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.ui.Ui;
import java.io.IOException;

public class EventCommand extends Command {
    private String arguments;

    public EventCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
        if (arguments.trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }

        if (!arguments.contains(" /from ") || !arguments.contains(" /to ")) {
            throw new MissingDateTimeException("use: event <description> /from <start> /to <end>");
        }

        String[] firstSplit = arguments.split(" /from ", 2);
        if (firstSplit.length != 2 || firstSplit[0].trim().isEmpty()) {
            throw new MissingDateTimeException("event");
        }

        String[] secondSplit = firstSplit[1].split(" /to ", 2);
        if (secondSplit.length != 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
            throw new MissingDateTimeException("event");
        }

        Task event = new Task.Event(firstSplit[0].trim(), secondSplit[0].trim(), secondSplit[1].trim());
        tasks.add(event);
        storage.save(tasks.getTasks());
        return ui.displayTaskAdded(event, tasks.size());
    }
}