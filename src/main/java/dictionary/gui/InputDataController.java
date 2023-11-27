package dictionary.gui;

import dictionary.db.DatabaseRequestHandle;
import dictionary.db.InterfaceRequestDelegate;
import dictionary.db.WordHistory;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InputDataController {

    @FXML
    private Label errorText;
    @FXML
    private TextField filePath;

    /**
     * Initialize.
     */
    public void initialize() {
        WordHistory.loadFromFile();
    }

    /**
     * Release focusing text-field when click on root pane.
     */
    @FXML
    public void clickRootPane() {
            filePath.getParent().requestFocus();
    }

    /**
     * Import internal database.
     */
    @FXML
    public void importInternalDB() {
        runTask(DatabaseRequestHandle::loadLocalDictionary);
    }

    /**
     * Import external database.
     */
    @FXML
    public void importExternalDB() {
        if (InterfaceRequestDelegate.insertDictionaryFromFile(filePath.getText())) {
            runTask(WordHistory::loadFromFile);
        } else {
            errorText.setText("File path is incorrect");
        }
    }

    private void runTask(Runnable taskRunnable) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                taskRunnable.run();
                return null;
            }
        };
        task.setOnSucceeded(event -> switchToApp());
        new Thread(task).start();
    }

    /**
     * Switch to main app.
     */
    private void switchToApp() {
        ((Stage) filePath.getScene().getWindow()).close();

        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/App.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("EN-VI Dictionary");
        Scene app = new Scene(root);
        secondaryStage.getIcons().add(new Image("App_icon.png"));
        secondaryStage.setScene(app);
        secondaryStage.setMinWidth(400);
        secondaryStage.setMinHeight(350);
        secondaryStage.centerOnScreen();
        secondaryStage.show();
        secondaryStage.setOnCloseRequest(event -> WordHistory.exportToFile());
    }

}