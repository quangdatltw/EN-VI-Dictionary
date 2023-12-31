package dictionary.gui.control;

import dictionary.db.WordHistory;
import dictionary.gui.App;
import dictionary.gui.request.LibraryTypeRequestDelegator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LibraryTypeController {

    @FXML
    private ImageView image;
    @FXML
    private Label errorText;
    @FXML
    private TextField filePath;
    @FXML
    private Button internalDB;
    @FXML
    private Button externalDB;


    /**
     * Initialize.
     */
    public void initialize() {
        WordHistory.loadFromFile();
        DropShadow shadow = new DropShadow(5, Color.valueOf("#509dea"));
        image.setEffect(shadow);
    }

    @FXML
    private void close() {
        Stage stage = (Stage) ( filePath.getScene().getWindow());
        stage.close();
    }
    @FXML
    private void minimize() {
        Stage stage = (Stage) ( filePath.getScene().getWindow());
        stage.setIconified(true);
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
        internalDB.setDisable(true);
        LibraryTypeRequestDelegator.insertDictionaryFromDatabase();
    }

    /**
     * Import external database.
     */
    @FXML
    public void importExternalDB() {
        if (!LibraryTypeRequestDelegator.insertDictionaryFromFile(filePath.getText())) {
            errorText.setText("File path is incorrect");
        } else {
            externalDB.setDisable(true);
        }

    }

    /**
     * Switch to main app.
     */
    public static void switchToApp() {
        try {
            App.getSecondStage().show();
        } catch (Exception ignore) {
            callAlert();
        }
    }

    private static void callAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Can not load interface.");
        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                Platform.exit();
            }
        });
    }

}