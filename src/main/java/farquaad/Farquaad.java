package farquaad;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import farquaad.command.Command;
import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.ui.Ui;
import farquaad.farquaadexception.FarquaadException;

/**
 * The main entry point of the Farquaad program.
 * Initializes the storage, task list, and UI components,
 * and starts the program execution loop.
 */
public class Farquaad {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Creates a new {@code Farquaad} instance using the given save file path.
     *
     * @param filePath Path to the file used for saving and loading tasks.
     */
    public Farquaad(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.displayLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        new Farquaad("data/farquaad.txt").run();
    }

    /**
     * Starts the main execution loop of the program.
     * Reads user input, parses it into commands, and executes them until exit.
     */
    public void run() {
        ui.displayWelcome();

        String startupReminders = getStartupRemindersMessage();
        if (!startupReminders.isEmpty()) {
            ui.displayMessage(startupReminders);
        }

        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command command = Parser.parse(input);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (FarquaadException e) {
                ui.displayError(e.getMessage());
            } catch (IOException e) {
                ui.displayError("Cannot save file! "
                        + e.getMessage());
            }
        }
    }

    /**
     * Returns the chatbot's response to the given input string.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.execute(tasks, ui, storage);
        } catch (FarquaadException e) {
            return e.getMessage();
        } catch (IOException e) {
            return "File error: " + e.getMessage();
        }
    }

    /**
     * Returns the welcome message (and prints it via Ui).
     */
    public String getWelcomeMessage() {
        return ui.displayWelcome();
    }

    /**
     * Builds and returns startup reminders (<= 3 days ahead).
     * Returns "" if there are no upcoming deadlines.
     */
    public String getStartupRemindersMessage() {
        return buildUpcomingDeadlinesMessage(3);
    }

    private String buildUpcomingDeadlinesMessage(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(daysAhead);

        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (Task t : tasks.getTasks()) {
            if (t instanceof Task.Deadline) {
                LocalDate due = parseDate(((Task.Deadline) t).getIsoDay());
                if (due != null && !due.isBefore(today) && !due.isAfter(limit)) {
                    if (count == 0) {
                        sb.append("Here are your upcoming deadlines (next ")
                                .append(daysAhead).append(" days):\n");
                    }
                    count++;
                    sb.append(count).append(". ").append(t).append("\n");
                }
            }
        }
        return count == 0 ? "" : sb.toString().trim();
    }

    private static LocalDate parseDate(String s) {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}