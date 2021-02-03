package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyButton extends Sprite {

    public MyButton(double x, double y, String imURL, String text){
        this.x = x;
        this.y = y;
        Image img = new Image(imURL);
        this.image = img;
        initialize();

        lName.setFont(Font.font("Felix Titling", FontWeight.BOLD, 17 ));
        lName.setTextFill(Color.WHITE);
        lName.setText(text);

        lName.setWrapText(true);

        pane.getChildren().add(lName);
    }


    @Override
    public void setX(double x){
        this.x = x;
        pane.relocate(x,y);
    }

    public void setY(double y){
        this.y = y;
        pane.relocate(x,y);
    }

}
