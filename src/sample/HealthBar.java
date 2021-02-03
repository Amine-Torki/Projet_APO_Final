package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class HealthBar {
    protected double x;
    protected double y;
    protected final double width = 100;
    protected final double height = 10;
    protected Canvas canvas = new Canvas(width,height);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    protected Pane pane = new Pane(canvas);
    private int maxHP;
    protected double healthPoints;

    public HealthBar(int maxHP){
        this.maxHP = maxHP;
        gc.setFill(Color.RED);
        gc.fill();

    }

    public Pane getPane() {return pane; }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }
    public int getMaxHP() {
        return maxHP;
    }

    public void takeDamage(int damage){
        healthPoints -= damage;
    }

    public void heal(double heal){
        if(healthPoints < maxHP) {
            healthPoints += heal;
        }
    }
}
