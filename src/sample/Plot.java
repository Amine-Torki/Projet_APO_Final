package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Plot {
    Canvas canvas = new Canvas(400, 400);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Pane pane = new Pane(canvas);
    Color[] colors = {Color.YELLOWGREEN,Color.YELLOW,Color.GRAY,Color.SADDLEBROWN, Color.LIGHTBLUE,Color.BLACK,Color.RED};

    private double windowHeight;
    private double windowWidth;

    private Sprite[][] plot;
    private int n;
    private int m;

    public Plot(int n, int m){
        plot = new Sprite[n][m]; //Longueur et largeur du terrain constructible
        this.n = n;
        this.m = m;
        pane.setVisible(false);
    }

    public Sprite[] neighbors(int x, int y){ //retourne les voisins sous la forme [N,E,S,O] nord est sud ouest
        System.out.println("x:"+x+"y:"+y);
        Sprite[] neighbors = new Sprite[] { plot[x][y-1], plot[x+1][y], plot[x][y+1], plot[x-1][y] };
        for(int i =0; i<4;i++){
            System.out.println(neighbors[i]);
        }
        return neighbors;
    }

    public Sprite[] diagonalNeighbors(int x, int y){ //Voisins diagonaux de la forme [NE, SE, SO, NO]
        Sprite[] diagNeighbors = new Sprite[] { plot[x+1][y-1], plot[x+1][y+1], plot[x-1][y+1], plot[x-1][y-1] };
        return diagNeighbors;
    }

    public void zero(){
        for(int i=0;i<n;i++){
            for(int j=0; j<m;j++){
                plot[i][j] = null;
            }
        }
    }

    public Sprite[][] getArray(){
        return plot;
    }

    public void add(Sprite sprite){
        int a =(int)sprite.getX()/32;
        int b = (int)sprite.getY()/32;
        int w = (int)sprite.getWidth()/32;
        int h = (int)sprite.getHeight()/32;
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                plot[a+i][b+j] = sprite;
            }
        }

    }
    /*Permet d'afficher une mini-map (une carte) de tout le terrain constructible, avec les batiments posés
    Code couleur :
    HERBE : Vert
    FERME : Jaune
    MINE : Gris
    CABANE DE BUCHERON : Marron
    CASERNE : Bleu
    MUR : Noir
    CHATEAU PRINCIPAL : Rouge
    */
    public void showPlot(){
        pane.relocate(0.5*windowWidth-200,0.5*windowHeight);
        for(int i = 0; i<m;i++){
            for(int j=0; j<n;j++){
                if(plot[j][i]==null){
                    gc.setFill(colors[0]);
                    gc.fillRect(j*4,i*4,4,4);
                    gc.fill();
                } else {
                    gc.setFill(colors[plot[j][i].id]);
                    gc.fillRect(j*4,i*4,4,4);
                    gc.fill();
                }
            }
        }
        pane.setVisible(true);
        pane.setMouseTransparent(false);
        pane.toFront();
        pane.setOnMouseClicked(e -> closePlot());
    }

    public void closePlot(){
        pane.setVisible(false);
        pane.setMouseTransparent(true);
    }

    @Override
    public String toString(){
        String string = "";
        for(int i = 0; i<m;i++){
            for(int j=0; j<n;j++){
                if(plot[j][i]==null){
                    string +="░ ";
                } else {
                    string +=plot[j][i].id+" ";
                }
            }
            string += "\n";
        }
        return string;
    }

    public void setWindowHeight(double windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void setWindowWidth(double windowWidth) {
        this.windowWidth = windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public Pane getPane() {
        return pane;
    }
}
