package farquaad.command;

import farquaad.Farquaad;
import farquaad.farquaadexception.*;
import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

        String description = splits[0].trim();
        String rawDate = splits[1].trim();

        LocalDate parsedDate = parseFlexibleDate(rawDate);

        String normalizedDate = parsedDate.toString();

        Task deadline = new Task.Deadline(description, normalizedDate);
        tasks.add(deadline);
        storage.save(tasks.getTasks());
        return ui.displayTaskAdded(deadline, tasks.size());
    }

    private LocalDate parseFlexibleDate(String input) throws FarquaadException {
        String[] patterns = { "yyyy-MM-dd", "d MMM yyyy", "d MMMM yyyy", "MMM d yyyy", "d-MMM-yyyy", "d-M-yyyy" };
        for (String p : patterns) {
            try {
                return LocalDate.parse(input, DateTimeFormatter.ofPattern(p));
            } catch (DateTimeParseException ignored) {}
        }
        throw new MissingDateTimeException("Unrecognised date format: " + input);
    }
}