package sample;

import javafx.scene.image.Image;

public class Soldier extends Unit {

    public Soldier(double x, double y, HUD hud){
        this.x = x;
        this.y = y;
        image = new Image("./textures/entity/soldier.png");
        initialize();

    }

}
