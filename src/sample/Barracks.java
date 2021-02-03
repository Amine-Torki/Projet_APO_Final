package sample;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Barracks extends Sprite {
    ContextMenu contextMenu;
    Sprite spawnArea;



    public Barracks(double x, double y){
        id =4;
        this.x = x;
        this.y = y;
        image = new Image("./textures/buildings/barracks_128.png");
        initialize();

        spawnArea = new Sprite(x+128,y);
        spawnArea.setImage("./textures/buildings/spawn_area.png");
        spawnArea.initialize();
        spawnArea.getPane().toBack();

        contextMenu = new ContextMenu();
        pane.setOnMouseClicked((MouseEvent mouseEvent) -> contextMenu.show(mouseEvent,x,y));
        maxHP = 1000;
        hp = 1000;

        constructHealthBar();
        updateHealthBar();
    }

    public void recruitSoldier() {

    }
    public void recruitArcher() {

    }


    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public Sprite getSpawnArea() {
        return spawnArea;
    }


}
