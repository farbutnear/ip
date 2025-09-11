package farquaad.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a generic task in the task list.
 * A {@code Task} contains a description and a completion status.
 */

public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates a new task with the given description.
     * The task is initially marked as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmarkAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns whether this task is done.
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the string representation of the task,
     * showing its status and description.
     *
     * @return A formatted string for display.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Represents a simple ToDo task without deadlines or time.
     */
    public static class ToDo extends Task {

        /**
         * Creates a new ToDo task.
         *
         * @param description The description of the task.
         */
        public ToDo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    /**
     * Represents a task with a deadline.
     */
    public static class Deadline extends Task {
        private LocalDate day;
        private String originalDay;

        /**
         * Creates a new Deadline task.
         *
         * @param description Description of the task.
         * @param day The deadline date in string format.
         */
        public Deadline(String description, String day) {
            super(description);
            this.originalDay = day;
            this.day = parseDate(day);
        }

        /**
         * Returns the parsed date of the deadline, if available.
         *
         * @return The parsed {@code LocalDate}, or {@code null} if parsing failed.
         */
        public LocalDate getParsedDate() {
            return this.day;
        }

        private LocalDate parseDate(String day) {
            DateTimeFormatter[] formatters = {
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                    DateTimeFormatter.ofPattern("d/M/yyyy"),
                    DateTimeFormatter.ofPattern("d MMM yyyy"),
                    DateTimeFormatter.ofPattern(("MMM d yyyy"))
            };

            for (DateTimeFormatter formatter : formatters) {
                try {
                    return LocalDate.parse(day.trim(), formatter);
                } catch (DateTimeParseException e) {
                    // Try next formatter
                }
            }

            return null;
        }

        /**
         * Returns the deadline date in a user-friendly format.
         *
         * @return The formatted deadline date, or the original string if parsing failed.
         */
        public String getDay() {
            if (day != null) {
                return day.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
            } else {
                return originalDay;
            }
        }

        public String getOriginalDay() {
            return originalDay;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + getDay() + ")";
        }
    }

    /**
     * Represents a task with a start and end time (event).
     */
    public static class Event extends Task {
        private LocalDateTime start;
        private LocalDateTime end;
        private String originalStart;
        private String originalEnd;

        /**
         * Creates a new Event task.
         *
         * @param description Description of the event.
         * @param start The start time string.
         * @param end The end time string.
         */
        public Event(String description, String start, String end) {
            super(description);
            this.originalStart = start;
            this.originalEnd = end;
            this.start = parseDateTime(start);
            this.end = parseDateTime(end);
        }

        private LocalDateTime parseDateTime(String dateTimeString) {
            // Try multiple datetime formats
            DateTimeFormatter[] formatters = {
                    DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                    DateTimeFormatter.ofPattern("d/M/yyyy HH:mm a"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a")
            };

            for (DateTimeFormatter formatter : formatters) {
                try {
                    return LocalDateTime.parse(dateTimeString.trim(), formatter);
                } catch (DateTimeParseException e) {
                    // Try next formatter
                }
            }

            return null;
        }

        /**
         * Returns the start date-time in a user-friendly format.
         *
         * @return The formatted start date-time, or the original string if parsing failed.
         */
        public String getStart() {
            if (start != null) {
                return start.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
            } else {
                return originalStart;
            }
        }

        /**
         * Returns the end date-time in a user-friendly format.
         *
         * @return The formatted end date-time, or the original string if parsing failed.
         */
        public String getEnd() {
            if (end != null) {
                return end.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
            } else {
                return originalEnd;
            }
        }

        public String getOriginalStart() {
            return originalStart;
        }

        public String getOriginalEnd() {
            return originalEnd;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + getStart() + " to: " + getEnd() + ")";
        }
    }
}
