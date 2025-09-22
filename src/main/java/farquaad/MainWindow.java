package farquaad;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Farquaad farquaad;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/donk.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/farquaad.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setFarquaad(Farquaad f) {
        this.farquaad = f;

        // Show welcome
        String welcome = farquaad.getWelcomeMessage();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(welcome, dukeImage)
        );

        // Show startup reminders, if any
        String reminders = farquaad.getStartupRemindersMessage();
        if (!reminders.isBlank()) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(reminders, dukeImage)
            );
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = farquaad.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
