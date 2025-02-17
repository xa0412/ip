package xan;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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

    private XanChatBot xan;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image xanImage = new Image(this.getClass().getResourceAsStream("/images/Xan.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setXan(XanChatBot x) {
        xan = x;
    }

    /**
     * Sends the welcome message to the user.
     */
    public void sendWelcomeMessage() {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(xan.getWelcomeMessage(), xanImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = xan.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, xanImage)
        );
        userInput.clear();
        // @@ author xa0412-reused
        // Reused https://github.com/nus-cs2103-AY2425S2/ip/pull/8/files
        // #diff-fa25c50aec4546ddcaa1a91569bb7a23856803a8ea90903105f4ae8c4c248aa9R71-R75
        // with minor modifications
        if (xan.isExit()) {
            PauseTransition wait = new PauseTransition(Duration.seconds(2));
            wait.setOnFinished(event -> Platform.exit());
            wait.play();
        }
        // @@ author
    }
}
