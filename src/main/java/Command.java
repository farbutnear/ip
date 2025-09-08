import java.io.IOException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException;

    public boolean isExit() {
        return false;
    }
}

class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.displayTaskList(tasks);
    }
}

class MarkCommand extends Command {
    private String arguments;

    public MarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
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
        task.markAsDone();
        ui.displayTaskMarked(task);
        storage.save(tasks.getTasks());
    }
}

class UnmarkCommand extends Command {
    private String arguments;

    public UnmarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
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
        ui.displayTaskUnmarked(task);
        storage.save(tasks.getTasks());
    }
}

class TodoCommand extends Command {
    private String arguments;

    public TodoCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
        if (arguments.trim().isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }

        Task todo = new Task.ToDo(arguments.trim());
        tasks.add(todo);
        ui.displayTaskAdded(todo, tasks.size());
        storage.save(tasks.getTasks());
    }
}

class DeadlineCommand extends Command {
    private String arguments;

    public DeadlineCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
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
        tasks.add(deadline);
        ui.displayTaskAdded(deadline, tasks.size());
        storage.save(tasks.getTasks());
    }
}

class EventCommand extends Command {
    private String arguments;

    public EventCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
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
        ui.displayTaskAdded(event, tasks.size());
        storage.save(tasks.getTasks());
    }
}

class DeleteCommand extends Command {
    private String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage)
            throws FarquaadException, IOException {
        if (arguments.trim().isEmpty()) {
            throw new EmptyDescriptionException("delete");
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

        Task removed = tasks.remove(taskNo);
        ui.displayTaskDeleted(removed, tasks.size());
        storage.save(tasks.getTasks());
    }
}

class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.displayGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}