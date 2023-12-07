package dictionary.gui.control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabGameController {
    List<Image> imageList = new ArrayList<>();
    List<String> words = new ArrayList<>();
    public void initialize() {
        check.setDisable(true);
        answer.setDisable(true);
        words.add("football");
        words.add("hotdog");
        words.add("earring");
        words.add("horseshoe");
        words.add("keyboard");
        words.add("cupcake");
        words.add("icebox");
        words.add("handshake");
        words.add("suitcase");
        words.add("beanbag");
        words.add("candycane");
        words.add("menhole");
        words.add("limestone");
        words.add("carpet");
        words.add("railroad");
        words.add("seesaw");
        words.add("snowglobe");
        words.add("hairnet");
        words.add("milkshake");
        words.add("spyglass");
        words.add("doorway");
        words.add("cannot");
        words.add("lightbulb");
        words.add("necklace");
        words.add("lawsuit");
        words.add("peanuts");
        words.add("kneecap");
        words.add("footprint");
        words.add("icecream");
        words.add("forklift");
        words.add("payroll");
        words.add("heartburn");
        for (int i = 1; i < 34; i++) {
            String imagePath = "/Pic/" + i + ".PNG";

            InputStream stream = this.getClass().getResourceAsStream(imagePath);


            if (stream != null) {
                imageList.add(new Image(stream));
            } else {
                System.err.println("Image not found: " + imagePath);
            }
        }
    }
    static int Score = 0;
    static int Life = 3;
    @FXML
    private ImageView Picture;
    @FXML
    private Label life;
    @FXML
    private Label score;
    @FXML
    private TextField answer;
    @FXML
    private Button check;
    @FXML
    private void checkWord() {
        if (answer.getText().toLowerCase().equals(words.get(imageList.indexOf(Picture.getImage())))) {
            Score++;
            score.setText(Score + "");
            life.setText(Life + "");
            answer.clear();
            Random ran = new Random();
            Picture.setImage(imageList.get(ran.nextInt(imageList.size())));
        } else {
            Life--;
            if (Life == 0) {
                Picture.setImage(null);
                answer.clear();
                check.setDisable(true);
                answer.setDisable(true);
            }
            life.setText(Life + "");
        }
    }
    @FXML
    public void startGame() {
        answer.setDisable(false);
        check.setDisable(false);
        Life = 3;
        Score = 0;
        score.setText(Score + "");
        life.setText(Life + "");
        Random ran = new Random();
        Picture.setImage(imageList.get(ran.nextInt(imageList.size())));
    }
}
