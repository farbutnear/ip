package farquaad;

import java.io.IOException;
import farquaad.command.*;
import farquaad.storage.Storage;
import farquaad.ui.Ui;
import farquaad.farquaadexception.FarquaadException;

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