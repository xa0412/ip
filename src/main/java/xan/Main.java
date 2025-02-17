package xan;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A launcher class to workaround classpath issues.
 */
public class Main extends Application {
    private final XanChatBot xan = new XanChatBot();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("XanChatBot");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setXan(xan);
            fxmlLoader.<MainWindow>getController().sendWelcomeMessage();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
