public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
        System.out.println("Nice! I've marked this task as done:");
    }

    public void unmarkAsNotDone() {
        this.isDone = false;
        System.out.println("OK, I've marked this task as not done yet:");
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
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
        private String day;
        public Deadline(String description, String day) {
            super(description);
            this.day = day;
        }
        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + day + ")";
        }
    }

    static class Event extends Task {
        private String start;
        private String end;
        public Event(String description, String start, String end) {
            super(description);
            this.start = start;
            this.end = end;
        }
        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + start + "to: " + end + ")";
        }
    }
}
