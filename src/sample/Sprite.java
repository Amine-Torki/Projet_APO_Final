package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Sprite {
    public int id = 99;

    protected double x;
    protected double y;
    protected Image image;
    protected double width;
    protected double height;

    protected double hp;
    protected int maxHP;

    protected Canvas canvas = new Canvas();
    GraphicsContext gc = canvas.getGraphicsContext2D();
    protected Pane pane = new Pane(canvas);

    Label lName = new Label();

    public Sprite(){}

    public Sprite(double x, double y){
        this.x = x;
        this.y = y;

        pane.relocate(x,y);
    }

    public double getX() {
        return x;
    }

    public double getY() { return y; }

    public double getWidth() { return width; }

    public double getHeight() { return height; }

    public Pane getPane() {return pane; }

    public Label getLName(){return lName;}

    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
        pane.relocate(x,y);
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setName(String name){
        lName.setText(name);
    }

    public void render()
    {
        gc.drawImage( image, 0, 0 );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(x,y,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    @Override
    public String toString()
    {
        return " [x:" + x + ", y:" + y + ", w:" + width + ", h:" +height +"]";
    }

    public void initialize(){
        height = image.getHeight();
        width = image.getWidth();
        canvas.setHeight(height);
        canvas.setWidth(width);
        pane.relocate(x,y);
        gc.drawImage( image, 0, 0 );

    }

    public void centerText(){

        Text tName = new Text(lName.getText());
        tName.setFont(lName.getFont());
        double tw = tName.getBoundsInLocal().getWidth();
        double th = tName.getBoundsInLocal().getHeight();
        lName.relocate(width/2-tw/2,height/2-th/2);
    }


    /*
    Vérifie si il n'y a pas d'intersection avec un batiment
    */
    public boolean intersectWith(ArrayList<ArrayList> al_Buildings){
        for (ArrayList<Sprite> arraylist:al_Buildings) {
            for (Sprite sprite: arraylist) {
                if (intersects(sprite)){
                    return true;
                }
            }
        }
        return false;
    }

    public void update(){

    }

    public void takeDamage(int damage){
        hp -= damage;
        updateHealthBar();
    }

    public void heal(double heal){
        if(hp < maxHP) {
            hp += heal;
        }
    }

    public void constructHealthBar(){
        gc.setFill(Color.RED);
        gc.fillRect(0.2*width,0,0.6*width,10);
    }

    public void updateHealthBar(){
        gc.setFill(Color.LIMEGREEN);
        gc.fillRect(0.2*width,0,0.6*width*(hp /maxHP),10);
    }

}
