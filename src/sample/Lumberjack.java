package sample;

import javafx.scene.image.Image;

public class Lumberjack extends Sprite {

    public Lumberjack(double x, double y){
        id=3;
        this.x = x;
        this.y = y;
        Image img = new Image("./textures/buildings/lumberjack_128.png");
        this.image = img;
        initialize();

        maxHP = 900;
        hp = 900;

        constructHealthBar();
        updateHealthBar();

    }
}
