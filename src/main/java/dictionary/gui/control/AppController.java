package dictionary.gui.control;

import dictionary.gui.Animation;
import dictionary.gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppController {
    @FXML
    private Tab Tab_Find;
    @FXML
    private Tab Tab_GoogleTranslate;
    @FXML
    private Tab Tab_AddRemoveUpdate;
    @FXML
    private Tab Tab_Game;
    @FXML
    private TabPane TabPane;
    @FXML
    private Button maximizeB;
    @FXML
    private BorderPane borderPane;

    private double x = 0;
    private double y = 0;

    /**
     * Initialize, load Tabs fxml.
     */
    public void initialize() {
        try {
            loadTab(Tab_Find, "fxml/Tab_Find.fxml");
            loadTab(Tab_GoogleTranslate, "fxml/Tab_GoogleTranslate.fxml");
            loadTab(Tab_AddRemoveUpdate, "fxml/Tab_AddRemoveUpdate.fxml");
            loadTab(Tab_Game, "fxml/Tab_Game.fxml");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void loadTab(Tab tab, String fxml) throws IOException {
        AnchorPane tabAnchor = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml)));
        tab.setContent(tabAnchor);
        if (!fxml.equals("fxml/Tab_Find.fxml")) {
            tab.getContent().setTranslateX(TabPane.getWidth());
        }
        Animation.tabAnimation(TabPane);
    }
    @FXML
    private void close() {
        Stage stage = (Stage) (TabPane.getScene().getWindow());
        stage.close();
    }
    @FXML
    private void minimize() {
        Stage stage = (Stage) (TabPane.getScene().getWindow());
        stage.setIconified(true);
    }

    @FXML
    private void maximize() {
        Stage stage = (Stage) ( TabPane.getScene().getWindow());
        if (stage.isMaximized()) {
            stage.setMaximized(false);
            setButtonImg("/icon/maximize1.png", maximizeB);
        } else {
            stage.setMaximized(true);
            setButtonImg("/icon/maximize2.png", maximizeB);
        }
    }

    @FXML
    private void setTopPane_dragged(MouseEvent event) {
        Stage stage = (Stage) ( borderPane.getScene().getWindow());
        stage.setY(event.getScreenY() - y);
        stage.setX(event.getScreenX() - x);
    }

    @FXML
    private void topPane_pressed(MouseEvent event) {
        x = event.getX();
        y = event.getY();
    }

    // General method for Tabs //////////////////////////////////////////////////////////

    /**
     * Copy text.
     *
     * @param sentence the sentence
     */
    protected void copyText(String sentence) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(sentence);
        clipboard.setContent(content);
    }

    protected void setButtonImg(String url, Button button) {
        Image Icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)));
        button.setGraphic(new ImageView(Icon));
    }

}