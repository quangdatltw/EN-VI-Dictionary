package dictionary.gui.control;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TabRelaxingCornerController extends AppController {

    private MediaPlayer playingMusic = null;
    private static List<String> songList;

    public void initialize() {

        songList = new ArrayList<>();
        songList.add("1. Theme of Violet Evergarden.mp3");
        songList.add("2. A Doll's Beginning.mp3");
        songList.add("3. One Last Message.mp3");
        songList.add("4. Unspoken Words.mp3");
        songList.add("5. A Simple Mission.mp3");
        songList.add("6. Another Sunny Day.mp3");
        songList.add("7. The Voice In My Heart.mp3");
        songList.add("8. Rust.mp3");
        songList.add("9. In Remembrance.mp3");
        songList.add("10. Ink to Paper.mp3");
        songList.add("11. The Birth of a Legend.mp3");
        songList.add("12. To the Ends of Our World.mp3");
        songList.add("13. Back in Business.mp3");
        songList.add("14. A Place to Call Home.mp3");
        songList.add("15. An Admirable Doll.mp3");
        songList.add("16. Those Words you Spoke to Me.mp3");
        songList.add("17. Strangeling.mp3");
        songList.add("18. A Bit of Sass.mp3");
        songList.add("19. Each Memory A Message.mp3");
        songList.add("20. The Long Night.mp3");
        songList.add("21. Violet Snow for Orchestra.mp3");
        list.setItems(FXCollections.observableArrayList(songList));
    }


    @FXML
    private Button pauseMusic;
    @FXML
    private ListView<String> list;
    @FXML
    private void pause_continueMusic() {
        switchMediaStage(playingMusic, pauseMusic);
    }

    @FXML
    private void getSong() {
        if (playingMusic != null) {
            playingMusic.stop();
            playingMusic.dispose();
        } else if (list.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        String song = list.getSelectionModel().getSelectedItem();
        URL resourceUrl = getClass().getResource("music/Part1/" + song);
        assert resourceUrl != null;
        System.out.println(resourceUrl.toString());
        Media songMedia = new Media(resourceUrl.toString());
        playingMusic = new MediaPlayer(songMedia);
        playingMusic.setOnEndOfMedia(() -> {

            int selectedIndex = list.getSelectionModel().getSelectedIndex();

            int nextIndex = selectedIndex + 1;

            if (nextIndex < songList.size()) {

                String nextItem = songList.get(nextIndex);
                URL nextResourceUrl =  getClass().getResource("music/Part1/" + nextItem);
                assert nextResourceUrl != null;
                Media songMediaNext = new Media(nextResourceUrl.toString());
                playingMusic = new MediaPlayer(songMediaNext);
                playingMusic.play();
            } else {
                System.out.println("No next item");
            }
        });
        playingMusic.play();
    }

}
