package sample;

import javafx.scene.image.Image;

public class Castle extends Sprite {

    public Castle(){
        id =6;
        this.x = 48*32;
        this.y = 48*32;
        image = new Image("./textures/buildings/castle.png");
        initialize();

        maxHP = 2000;
        hp = 2000;

        constructHealthBar();
        updateHealthBar();
    }
}
