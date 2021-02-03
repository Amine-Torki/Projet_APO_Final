package sample;

import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Player extends Sprite {

    Image image = new Image("/textures/entity/player.png", 90,90,true,true);
    
    public Player(double x, double y){
        this.x = x;
        this.y = y;

        this.height = image.getHeight();
        this.width = image.getWidth();
        canvas.setHeight(height);
        canvas.setWidth(width);
        render();
    }

    @Override
    public void render() {
        pane.relocate(x,y);
        gc.drawImage( image, 0, 0 );
    }

    /*Rotation du joueur en fonction des coordonnées de la souris
    * Adapté de : https://stackoverflow.com/a/18262938 - jewelsea
    */
    public void rotate(double mx, double my){
        double angle = Math.atan2((my-y),(mx-x))*180/Math.PI;
        Rotate r = new Rotate(angle,width/2,height/2);

        gc.clearRect(0,0,width,height);
        gc.setTransform(r.getMxx(),r.getMyx(),r.getMxy(),r.getMyy(),r.getTx(),r.getTy());


    }



}
