package sample;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;


public class GhostBuilding extends Sprite {
    Player player;
    String type;
    ArrayList<ArrayList> al_Buildings;


    public GhostBuilding(Pane background, String imURL,String type, Player player,HUD hud,Scene sc,AnchorPane anchorPane){
        setPosition(-200,-200);
        this.player = player;
        this.type = type;
        this.al_Buildings = hud.getAl_Buildings();
        image = new Image(imURL);
        initialize();
        background.setOnMouseMoved((MouseEvent event) -> follow(event));
        pane.setOnMouseClicked((MouseEvent event) -> build(event,type,background,player,hud,sc,anchorPane));
        background.setOnMouseExited((MouseEvent mouseExited) -> {
            setPosition(-200, -200);
            pane.setVisible(false);
            pane.setMouseTransparent(true);
            System.out.println(pane.getParent().getChildrenUnmodifiable());
        });


    }

    public void follow( MouseEvent event){
        player.rotate(event.getX(),event.getY());
        int gbX = (int)(event.getX()+16-pane.getWidth()/2);
        gbX = gbX/32; // division euclidienne pour afficher le batiment aligné avec les carrés de terrain
        gbX = gbX*32;
        int gbY = (int)(event.getY()+16-pane.getHeight()/2);
        gbY = gbY/32;
        gbY = gbY*32;
        setPosition(gbX,gbY);
        render();
    }



    @Override
    public void render() {
        gc.drawImage( image, 0, 0 );
    }

    public void build(MouseEvent event, String type, Pane background, Player player, HUD hud, Scene sc, AnchorPane anchorPane){
        if(!intersectWith(al_Buildings) && !intersects(player) && x>=0 && y>=0 && x<background.getWidth() && y<background.getHeight()){ //vérifie si on peut ou non poser le batiment ici

            background.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    player.rotate(event.getX(),event.getY());
                }
            });

            switch (type) {
                case "FARM":
                    Farm farm = new Farm(x,y);
                    pane.setVisible(false);
                    al_Buildings.get(0).add(0,farm);
                    background.getChildren().add(farm.getPane());
                    System.out.println(al_Buildings);
                    System.out.println(al_Buildings.get(0));

                    hud.getPlot().add(farm);
                    System.out.println(hud.getPlot());

                    hud.pay(hud.getResources(), hud.costFarm);

                    break ;
                case "MINE" :
                    Mine mine = new Mine(x,y);
                    pane.setVisible(false);
                    al_Buildings.get(1).add(0,mine);
                    background.getChildren().add(mine.getPane());
                    System.out.println(al_Buildings);
                    System.out.println(al_Buildings.get(1));

                    hud.getPlot().add(mine);
                    System.out.println(hud.getPlot());

                    hud.pay(hud.getResources(), hud.costMine);
                    break;
                case "LUMBERJACK" :
                    Lumberjack lumberjack = new Lumberjack(x,y);
                    pane.setVisible(false);
                    al_Buildings.get(2).add(0,lumberjack);
                    background.getChildren().add(lumberjack.getPane());
                    System.out.println(al_Buildings);
                    System.out.println(al_Buildings.get(2));

                    hud.getPlot().add(lumberjack);
                    System.out.println(hud.getPlot());

                    hud.pay(hud.getResources(), hud.costLumberjack);
                    break;
                case "BARRACKS" :
                    Barracks barracks = new Barracks(x,y);
                    pane.setVisible(false);
                    al_Buildings.get(3).add(0,barracks);
                    background.getChildren().add(barracks.getPane());
                    background.getChildren().add(barracks.getContextMenu().getPane());
                    background.getChildren().add(barracks.getSpawnArea().getPane());
                    System.out.println(al_Buildings);
                    System.out.println(al_Buildings.get(2));

                    hud.getPlot().add(barracks);
                    System.out.println(hud.getPlot());

                    hud.pay(hud.getResources(), hud.costBarracks);
                    break;
                case "WALL" :
                    Wall wall = new Wall(x,y, hud.getPlot());
                    pane.setVisible(false);
                    al_Buildings.get(4).add(0,wall);
                    background.getChildren().add(wall.getPane());
                    System.out.println(al_Buildings);
                    System.out.println(al_Buildings.get(2));

                    hud.getPlot().add(wall);
                    //System.out.println(hud.getPlot());

                    hud.pay(hud.getResources(), hud.costWall);
                    wall.update();
                    break;
                default:
                    break ;
            }
        } else {
            PopUpMessage popUpMessage = new PopUpMessage(sc.getWidth()/2-256,sc.getHeight()-hud.getCanvas().getHeight()-50,"You need more space to build that !");
            anchorPane.getChildren().add(popUpMessage.getPane());
            popUpMessage.showMessage();
            popUpMessage=null;
        }
    }

}
