package sample;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class PopUpMessage extends Sprite {

    public PopUpMessage(double x, double y, String message) {
        image = new Image("./textures/gui/popUpMessage.png");
        this.x = x;
        this.y = y;
        initialize();

        pane.getChildren().add(lName);

        lName.setFont(Font.font("Felix Titling", FontWeight.BOLD, 17));
        lName.setTextFill(Color.WHITE);
        lName.setText(message);
        //pane.setStyle("-fx-background-color: rgba(0,100,100,0.5); -fx-background-radius: 10;");
        pane.toFront();
        pane.setMouseTransparent(true);

        centerText();
    }

    public void showMessage(){
        PauseTransition stayOn = new PauseTransition(Duration.millis(1000));
        FadeTransition fader = new FadeTransition(Duration.seconds(0.8), pane);
        fader.setFromValue(0.85);
        fader.setToValue(0);

        SequentialTransition popUpFading = new SequentialTransition(
                pane,
                stayOn,
                fader
        );

        popUpFading.play();
    }
}