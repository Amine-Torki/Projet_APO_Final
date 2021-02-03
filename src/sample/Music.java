package sample;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class Music {
    int volume = 5;
    int nbMusic = 1;
    File file = new File("./src/music/music_bg_1.mp3");
    MediaPlayer soundtrack =new MediaPlayer(new Media(file.toURI().toString()));

    public Music(){
        soundtrack.setOnEndOfMedia(() -> soundtrack.seek(Duration.ZERO));
        soundtrack.setVolume((double)volume/10);
    }

    public void changeVol(int volume){
        soundtrack.setVolume((double)volume/10);
        this.volume = volume;
    }
    public void changeMus(int nbMusic){
        this.nbMusic = nbMusic;
        file = new File("./src/music/music_bg_"+nbMusic+".mp3");
        soundtrack.stop();
        soundtrack = new MediaPlayer(new Media(file.toURI().toString()));
        soundtrack.setVolume((double)volume/10);
    }

    public int getVolume() {
        return volume;
    }

    public int getNbMusic() {
        return nbMusic;
    }

}
