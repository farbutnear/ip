import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public Boolean getIsDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }


    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    static class ToDo extends Task {
        public ToDo(String description) {
            super(description);
        }
        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    static class Deadline extends Task {
        private LocalDate day;
        private String originalDay;

        public Deadline(String description, String day) {
            super(description);
            this.originalDay = day;
            this.day = parseDate(day);
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
                }
            }

            return null;
        }
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

    static class Event extends Task {
        private LocalDateTime start;
        private LocalDateTime end;
        private String originalStart;
        private String originalEnd;

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

        public String getStart() {
            if (start != null) {
                return start.format(DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a"));
            } else {
                return originalStart;
            }
        }

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
