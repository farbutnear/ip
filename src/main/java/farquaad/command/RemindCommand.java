package farquaad.command;

import farquaad.TaskList;
import farquaad.storage.Storage;
import farquaad.task.Task;
import farquaad.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RemindCommand extends Command {
    private static final int DAYS_AHEAD = 3; // deadlines due N DAYS_AHEAD from today

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        String msg = buildUpcomingDeadlinesMessage(tasks, DAYS_AHEAD);
        if (msg.isEmpty()) {
            return ui.displayMessage("No upcoming deadlines in the next " + DAYS_AHEAD + " days.");
        }
        return ui.displayMessage(msg);
    }

    private static String buildUpcomingDeadlinesMessage(TaskList tasks, int daysAhead) {
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
        String[] patterns = {
                "yyyy-MM-dd",        // 2025-09-24
                "d MMM yyyy",        // 24 Sep 2025
                "d MMMM yyyy",       // 24 September 2025
                "EEEE"               // Wednesday (next occurrence)
        };

        for (String p : patterns) {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern(p);
                return LocalDate.parse(s, fmt);
            } catch (Exception ignored) {
            }
        }

        return null; // unparseable → skip this task
    }
}
