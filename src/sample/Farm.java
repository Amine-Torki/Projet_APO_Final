package sample;

import javafx.scene.image.Image;

public class Farm extends Sprite {

    public Farm(double x, double y){
        id =1;
        this.x = x;
        this.y = y;
        Image img = new Image("./textures/buildings/farm_128.png");
        this.image = img;
        maxHP = 1000;
        hp = 1000;

        initialize();
        constructHealthBar();
        updateHealthBar();

    }

}
