package dictionary.gui.control;

import dictionary.gui.Animation;
import dictionary.gui.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
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
    private Tab Tab_RelaxingCorner;
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
            loadTab(Tab_RelaxingCorner, "fxml/Tab_RelaxingCorner.fxml");
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
        Stage stage = (Stage) (borderPane.getScene().getWindow());
        stage.close();
    }
    @FXML
    private void minimize() {
        Stage stage = (Stage) (borderPane.getScene().getWindow());
        stage.setIconified(true);
    }

    @FXML
    private void maximize() {
        Stage stage = (Stage) (borderPane.getScene().getWindow());
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
        Stage stage = (Stage) (borderPane.getScene().getWindow());
        stage.setY(event.getScreenY() - y);
        stage.setX(event.getScreenX() - x);
    }

    @FXML
    private void mouse_pressed(MouseEvent event) {
        x = event.getX();
        y = event.getY();
    }

    @FXML
    private void topBor(MouseEvent event) {
        Stage stage = (Stage) (borderPane.getScene().getWindow());
        double currentHeight = stage.getHeight();
        double currentY = stage.getY();
        double mouseY = event.getScreenY();
        double deltaY = currentY - mouseY;

        if (deltaY > 0) {
            double newHeight = currentHeight + deltaY;
            stage.setHeight(newHeight);
            stage.setY(mouseY);
        } else if (deltaY < 0) {
            double newHeight = currentHeight - Math.abs(deltaY);
            stage.setHeight(newHeight);
            stage.setY(mouseY);
        }
    }
    @FXML
    private void bottomBor(MouseEvent event) {
        Stage stage = (Stage) (borderPane.getScene().getWindow());
        double currentHeight = stage.getHeight();
        double mouseY = event.getScreenY();

        double deltaY = mouseY - (stage.getY() + currentHeight);

        if (deltaY > 0) {

            double newHeight = currentHeight + deltaY;
            stage.setHeight(newHeight);
        } else if (deltaY < 0) {

            double newHeight = currentHeight - Math.abs(deltaY);
            stage.setHeight(newHeight);
        }
    }

    @FXML
    private void rightBor(MouseEvent event) {
        Stage stage = (Stage) (borderPane.getScene().getWindow());
        double currentWidth = stage.getWidth();
        double mouseX = event.getScreenX();

        double deltaX = mouseX - (stage.getX() + currentWidth);

        if (deltaX > 0) {

            double newWidth = currentWidth + deltaX;
            stage.setWidth(newWidth);
        } else if (deltaX < 0) {

            double newWidth = currentWidth - Math.abs(deltaX);
            stage.setWidth(newWidth);
        }
    }

    @FXML
    private void leftBor(MouseEvent event) {
        Stage stage = (Stage) (borderPane.getScene().getWindow());
        double currentWidth = stage.getWidth();
        double currentX = stage.getX();
        double mouseX = event.getScreenX();

        double deltaX = currentX - mouseX;

        if (deltaX > 0) {

            double newWidth = currentWidth + deltaX;
            stage.setWidth(newWidth);
            stage.setX(mouseX);
        } else if (deltaX < 0) {

            double newWidth = currentWidth - Math.abs(deltaX);
            stage.setWidth(newWidth);
            stage.setX(mouseX);
        }
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

    /**
     * Sets button img.
     *
     * @param url    the url
     * @param button the button
     */
    protected void setButtonImg(String url, Button button) {
        Image Icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)));
        button.setGraphic(new ImageView(Icon));
    }

    /**
     * Switch media stage.
     *
     * @param mediaPlayer the media player
     * @param switchB     the switch b
     */
    protected void switchMediaStage(MediaPlayer mediaPlayer, Button switchB) {
        if (mediaPlayer == null) {
            return;
        }
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            setButtonImg("/icon/play.png", switchB );
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            setButtonImg("/icon/pause.png", switchB );
            mediaPlayer.play();
        }
    }

}