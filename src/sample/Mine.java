package sample;

import javafx.scene.image.Image;

public class Mine extends Sprite {

    public Mine(double x, double y){
        id = 2;
        this.x = x;
        this.y = y;
        Image img = new Image("./textures/buildings/mine_128.png");
        this.image = img;
        initialize();

        maxHP = 1100;
        hp = 1100;

        constructHealthBar();
        updateHealthBar();


    }
}
