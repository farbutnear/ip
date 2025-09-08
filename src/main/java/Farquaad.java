import java.io.IOException;

class FarquaadException extends Exception {
    FarquaadException(String msg) {
        super(msg);
    }
}

class UnknownCommandException extends FarquaadException {
    UnknownCommandException() {
        super("I apologise, but I don't know what that means LOL");
    }
}

class EmptyDescriptionException extends FarquaadException {
    EmptyDescriptionException(String task) {
        super("lmao the description of a " + task + " cannot be empty.");
    }
}

class InvalidIndexException extends FarquaadException {
    InvalidIndexException() {
        super("the task number is invalid.");
    }
}

class InvalidDateFormatException extends FarquaadException {
    InvalidDateFormatException(String command) {
        super("oi provide a valid date format for " + command + ". " +
                "Examples: 2024-12-25, 25/12/2024, Dec 25 2024");
    }
}

class MissingDateTimeException extends FarquaadException {
    MissingDateTimeException(String command) {
        super("can you provide the required date/time information for " + command + "...");
    }
}

public class Farquaad {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public void run() {
        ui.displayWelcome();

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
                ui.displayError("Cannot save file! " + e.getMessage());
            }
        }
    }
}