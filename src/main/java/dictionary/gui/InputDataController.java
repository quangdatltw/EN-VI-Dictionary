package dictionary.gui;

import dictionary.db.DictionaryDatabase;
import dictionary.db.InterfaceInputHandle;
import dictionary.db.WordHistory;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InputDataController {
    @FXML
    public Label errorText;
    @FXML
    private TextField filePath;

    public void initialize() {
    }

    @FXML
    public void clickRootPane() {
            filePath.getParent().requestFocus();
    }

    @FXML
    public void importInternalDB() {
        runTask(DictionaryDatabase::loadLocalDictionary);
    }

    @FXML
    public void importExternalDB() {
        if (InterfaceInputHandle.insertDictionaryFromFile(filePath.getText())) {
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

    private void switchToApp() {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/App.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage secondaryStage = (Stage) filePath.getScene().getWindow();
        Scene app = new Scene(root);
        secondaryStage.setScene(app);
        secondaryStage.setResizable(true);
        secondaryStage.setMinWidth(400);
        secondaryStage.setMinHeight(350);
        secondaryStage.centerOnScreen();
        secondaryStage.show();
        secondaryStage.setOnCloseRequest(event -> WordHistory.exportHistoryToFile());
        WordHistory.loadFromFile();
    }

}