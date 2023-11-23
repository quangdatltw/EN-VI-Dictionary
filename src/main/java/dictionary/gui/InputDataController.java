package dictionary.gui;

import dictionary.db.DictionaryDatabase;
import dictionary.db.InterfaceInputHandle;
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
    public void rootPaneClick() {
            filePath.getParent().requestFocus();
    }

    @FXML
    public void InternalDB() {
        DictionaryDatabase.loadLocalDictionary();
        try {
            switchToApp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ExternalDB() {
        if (InterfaceInputHandle.dictionaryInsertFromFile(filePath.getText())) {
            try {
                switchToApp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorText.setText("File path is incorrect");
        }
    }

    private void switchToApp() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/App.fxml")));
        Stage secondaryStage = (Stage) filePath.getScene().getWindow();
        Scene app = new Scene(root);
        secondaryStage.setScene(app);
        secondaryStage.centerOnScreen();
        secondaryStage.show();
    }


}