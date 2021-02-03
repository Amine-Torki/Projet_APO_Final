package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.Arrays;

public class HUD extends Pane {

    private double wheat = 1500;
    private double iron = 1500;
    private double wood = 1500;

    private final int hud_height = 180;
    private int hud_width = (int) Math.round(0.7*Screen.getPrimary().getBounds().getWidth());

    Canvas canvas = new Canvas(hud_width, hud_height);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    //Les ressources du joueur [wheat,iron,wood]
    private ArrayList<Double> resources = new ArrayList();

    //Les coûts des bâtiments [wheat,iron,wood]
    public final ArrayList<Double> costFarm = new ArrayList(Arrays.asList(500.,300.,400.0));
    public final ArrayList<Double> costMine = new ArrayList(Arrays.asList(200.0,500.0,300.0));
    public final ArrayList<Double> costLumberjack = new ArrayList(Arrays.asList(200.0,200.0,300.0));
    public final ArrayList<Double> costBarracks = new ArrayList(Arrays.asList(500.0,500.0,300.0));
    public final ArrayList<Double> costWall = new ArrayList(Arrays.asList(25.0,25.0,50.0));
    public final ArrayList<Double> costTower = new ArrayList(Arrays.asList(200.0,200.0,200.0));


    //Listes contenant les bâtiments possédés
    private ArrayList<Farm> al_Farm = new ArrayList();
    private ArrayList<Mine> al_Mine = new ArrayList();
    private ArrayList<Lumberjack> al_Lumberjack = new ArrayList();
    private ArrayList<Barracks> al_Barracks = new ArrayList();
    private ArrayList<Wall> al_Wall = new ArrayList();
    private ArrayList<Tower> al_Tower= new ArrayList();
    private ArrayList<Castle> al_Castle= new ArrayList();

    private ArrayList<ArrayList> al_Buildings = new ArrayList();

    //Listes contenant les unités :
    private ArrayList<Soldier> al_Soldier = new ArrayList();
    private ArrayList<Archer> al_Archer = new ArrayList();

    private Plot plot = new Plot(100,100);

    Label lMenuBuildings = new Label();

    MyButton btnFarm = new MyButton(0,0.25* hud_height,"./textures/gui/btn_blank.png","FARM");
    MyButton btnMine = new MyButton(0,0.5* hud_height,"./textures/gui/btn_blank.png","MINE");
    MyButton btnLumberjack = new MyButton(0,0.75* hud_height,"./textures/gui/btn_blank.png","LUMBERJACK");

    MyButton btnBarracks = new MyButton(0.025* hud_width +212,0.25* hud_height,"./textures/gui/btn_blank.png","BARRACKS");
    MyButton btnWall = new MyButton(0.025* hud_width +212,0.5* hud_height,"./textures/gui/btn_blank.png","WALL");
    MyButton btnTower = new MyButton(0.025* hud_width +212,0.75* hud_height,"./textures/gui/btn_blank.png","TOWER");

    Label lMenuRessources = new Label();

    MyButton btnWheat = new MyButton(0.45* hud_width,0.25* hud_height,"./textures/gui/btn_blank.png","WHEAT : "+ wheat);
    MyButton btnIron = new MyButton(0.45* hud_width,0.5* hud_height,"./textures/gui/btn_blank.png","IRON : "+iron);
    MyButton btnWood = new MyButton(0.45* hud_width,0.75* hud_height,"./textures/gui/btn_blank.png","WOOD : "+wood);

    Label lMenuUnits = new Label();
    Label lMenuMaxUnits = new Label();
    Label lMenuArchers = new Label();
    Label lMenuSoldiers = new Label();



    public HUD(Scene sc, AnchorPane anchorPane, Pane background, Player player){

        plot.zero();

        System.out.println(plot);

        al_Buildings.add(al_Farm);
        al_Buildings.add(al_Mine);
        al_Buildings.add(al_Lumberjack);
        al_Buildings.add(al_Barracks);
        al_Buildings.add(al_Wall);
        al_Buildings.add(al_Tower);
        al_Buildings.add(al_Castle);
        Castle mainCastle = new Castle();
        al_Castle.add(mainCastle);
        background.getChildren().add(mainCastle.getPane());
        plot.add(mainCastle);

        resources.add(wheat);
        resources.add(iron);
        resources.add(wood);

        canvas.widthProperty().bind(sc.widthProperty());

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0, hud_width +1, hud_height +1);

        this.getChildren().add(canvas);

        lMenuBuildings.setText("BUILDINGS :");
        lMenuBuildings.setFont(Font.font("Imprint MT Shadow", FontWeight.EXTRA_BOLD, Math.round(hud_height /8) ));
        lMenuBuildings.setTextFill(Color.BLACK);
        lMenuBuildings.relocate(0,0.05* hud_height);


        btnFarm.getPane().setOnMouseClicked(event -> {
            if(affordable(resources,costFarm)){
                GhostBuilding ghostBuilding = new GhostBuilding(background,"./textures/buildings/farm_128.png","FARM", player,this,sc,anchorPane);
                background.getChildren().add(ghostBuilding.getPane());
            } else {
                PopUpMessage popUpMessage = new PopUpMessage(sc.getWidth()/2-256,sc.getHeight()-canvas.getHeight()-50,"You need more resources to do that !");
                anchorPane.getChildren().add(popUpMessage.getPane());
                popUpMessage.showMessage();
                popUpMessage=null;
            }
        });
        btnFarm.centerText();


        btnMine.getPane().setOnMouseClicked(event -> {
            if(affordable(resources,costMine)){
                GhostBuilding ghostBuilding = new GhostBuilding(background,"./textures/buildings/mine_128.png","MINE", player,this,sc,anchorPane);
                background.getChildren().add(ghostBuilding.getPane());
            } else {
                PopUpMessage popUpMessage = new PopUpMessage(sc.getWidth()/2-256,sc.getHeight()-canvas.getHeight()-50,"You need more resources to do that !");
                anchorPane.getChildren().add(popUpMessage.getPane());
                popUpMessage.showMessage();
                popUpMessage=null;
            }
        });
        btnMine.centerText();

        btnLumberjack.getPane().setOnMouseClicked(event -> {
            if(affordable(resources,costLumberjack)){
                GhostBuilding ghostBuilding = new GhostBuilding(background,"./textures/buildings/lumberjack_128.png","LUMBERJACK",player,this,sc,anchorPane);
                background.getChildren().add(ghostBuilding.getPane());
            } else {
                PopUpMessage popUpMessage = new PopUpMessage(sc.getWidth()/2-256,sc.getHeight()-canvas.getHeight()-50,"You need more resources to do that !");
                anchorPane.getChildren().add(popUpMessage.getPane());
                popUpMessage.showMessage();
                popUpMessage=null;
            }
        });
        btnLumberjack.centerText();


        btnBarracks.getPane().setOnMouseClicked(event -> {
            if(affordable(resources,costBarracks)){
                GhostBuilding ghostBuilding = new GhostBuilding(background,"./textures/buildings/barracks_128.png","BARRACKS",player,this,sc,anchorPane);
                background.getChildren().add(ghostBuilding.getPane());
            } else {
                PopUpMessage popUpMessage = new PopUpMessage(sc.getWidth()/2-256,sc.getHeight()-canvas.getHeight()-50,"You need more resources to do that !");
                anchorPane.getChildren().add(popUpMessage.getPane());
                popUpMessage.showMessage();
                popUpMessage=null;
            }
        });
        btnBarracks.centerText();


        btnWall.getPane().setOnMouseClicked(event -> {
            if(affordable(resources,costWall)){
                GhostBuilding ghostBuilding = new GhostBuilding(background,"./textures/buildings/wall_0000.png","WALL", player,this,sc,anchorPane);
                background.getChildren().add(ghostBuilding.getPane());
            } else {
                PopUpMessage popUpMessage = new PopUpMessage(sc.getWidth()/2-256,sc.getHeight()-canvas.getHeight()-50,"You need more resources to do that !");
                anchorPane.getChildren().add(popUpMessage.getPane());
                popUpMessage.showMessage();
                popUpMessage=null;
            }
        });
        btnWall.centerText();


        btnTower.getPane().setOnMouseClicked(event -> {
            System.out.println("slt");
        });
        btnTower.centerText();

        lMenuRessources.setText("RESOURCES :");
        lMenuRessources.setFont(Font.font("Imprint MT Shadow", FontWeight.EXTRA_BOLD, Math.round(hud_height /8) ));
        lMenuRessources.setTextFill(Color.BLACK);
        lMenuRessources.relocate(0.45* hud_width,0.05* hud_height);

        //Cliquer sur les boutons ne donnera pas de ressource dans le jeu, c'est juste pour aider à la conception
        btnWheat.getPane().setOnMouseClicked(event -> {
            System.out.println("+1000 wheat");
            resources.set(0,resources.get(0)+1000.0);
            btnWheat.setName("WHEAT : "+ resources.get(0));
            System.out.println(resources);
        });

        btnWheat.centerText();


        btnIron.getPane().setOnMouseClicked(event -> {
            System.out.println("+1000 iron");
            resources.set(1,resources.get(1)+1000.0);
            btnIron.setName("IRON : " + resources.get(1));
        });
        btnIron.centerText();


        btnWood.getPane().setOnMouseClicked(event -> {
            System.out.println("+1000 wood");
            resources.set(2,resources.get(2)+1000.0);
            btnWood.setName("WOOD : " + resources.get(2));
        });
        btnWood.centerText();

        lMenuUnits.setText("UNITS :");
        lMenuUnits.setFont(Font.font("Imprint MT Shadow", FontWeight.EXTRA_BOLD, Math.round(hud_height /8) ));
        lMenuUnits.setTextFill(Color.BLACK);
        lMenuUnits.relocate(0.8* hud_width,0.05* hud_height);

        lMenuMaxUnits.setText("MAX AMOUNT : "+al_Barracks.size()*10);
        lMenuMaxUnits.setFont(Font.font("Imprint MT Shadow", FontWeight.EXTRA_BOLD, Math.round(hud_height /8) ));
        lMenuMaxUnits.setTextFill(Color.BLACK);
        lMenuMaxUnits.relocate(0.8* hud_width,0.25* hud_height);

        lMenuSoldiers.setText("Soldiers : "+al_Soldier.size());
        lMenuSoldiers.setFont(Font.font("Imprint MT Shadow", FontWeight.EXTRA_BOLD, Math.round(hud_height /8) ));
        lMenuSoldiers.setTextFill(Color.BLACK);
        lMenuSoldiers.relocate(0.8* hud_width,0.5* hud_height);


        lMenuArchers.setText("Archers : "+al_Archer.size());
        lMenuArchers.setFont(Font.font("Imprint MT Shadow", FontWeight.EXTRA_BOLD, Math.round(hud_height /8) ));
        lMenuArchers.setTextFill(Color.BLACK);
        lMenuArchers.relocate(0.8* hud_width,0.75* hud_height);


        this.getChildren().addAll(lMenuBuildings,lMenuRessources,lMenuUnits);
        this.getChildren().addAll(btnFarm.getPane(),btnMine.getPane(),btnLumberjack.getPane(),
                btnBarracks.getPane(), btnWall.getPane(), btnTower.getPane(),
                btnWheat.getPane(),btnIron.getPane(),btnWood.getPane(),
                lMenuSoldiers,lMenuArchers,lMenuMaxUnits);

        //CODE A RAJOUTER AU MENU ECHAP
        //Bouton qui apelle le Garbage Collector pour supprimer les objets inutiles (null) donc optimiser la ram

        MyButton optRam = new MyButton(30,30,"./textures/gui/btn_96x32.png","CLEAR RAM");
        optRam.getPane().setOnMouseClicked(event -> {
            System.gc();
        });
        optRam.getLName().setFont(Font.font("Felix Titling", FontWeight.BOLD, 14 ));
        optRam.centerText();
        anchorPane.getChildren().add(optRam.getPane());

    }

    /* ------------------------------------------------------------------------------------
    Getters et setters :
     */

    public ArrayList<Double> getResources() {
        return resources;
    }

    public ArrayList<ArrayList> getAl_Buildings() {
        return al_Buildings;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setResources(ArrayList<Double> resources) {
        this.resources = resources;
    }

    public Plot getPlot() {
        return plot;
    }

    /* ------------------------------------------------------------------------------------
    methodes :
     */

    private boolean affordable(ArrayList<Double> resources, ArrayList<Double> cost){
        for(int i =0; i<resources.size();i++){
            if(resources.get(i) < cost.get(i)){
                return false;
            }

        }
        return true;
    }


    public void update(){
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        lMenuBuildings.relocate(0.5* canvas.getWidth()-512,0.05* hud_height);

        btnFarm.setX(0.5* canvas.getWidth()-512);
        btnMine.setX(0.5* canvas.getWidth()-512);
        btnLumberjack.setX(0.5* canvas.getWidth()-512);

        btnBarracks.setX(0.5* canvas.getWidth()-300);
        btnWall.setX(0.5* canvas.getWidth()-300);
        btnTower.setX(0.5* canvas.getWidth()-300);

        lMenuRessources.relocate(0.5* canvas.getWidth()-50,0.05* hud_height);

        btnWheat.setX(0.5* canvas.getWidth()-50);
        btnIron.setX(0.5* canvas.getWidth()-50);
        btnWood.setX(0.5* canvas.getWidth()-50);

        lMenuUnits.relocate(0.5* canvas.getWidth()+212,0.05* hud_height);
        lMenuMaxUnits.relocate(0.5* canvas.getWidth()+212,0.25* hud_height);
        lMenuSoldiers.relocate(0.5* canvas.getWidth()+212,0.5* hud_height);
        lMenuArchers.relocate(0.5* canvas.getWidth()+212,0.75* hud_height);

        lMenuMaxUnits.setText("MAX AMOUNT : "+al_Barracks.size()*10);
        lMenuSoldiers.setText("Soldiers : "+al_Soldier.size());
        lMenuArchers.setText("Archers : "+al_Archer.size());

        btnWheat.setName("WHEAT : "+ resources.get(0).intValue());
        btnIron.setName("IRON : " + resources.get(1).intValue());
        btnWood.setName("WOOD : " + resources.get(2).intValue());

        if((plot.getWindowHeight() != canvas.getHeight() || plot.getWindowWidth() != canvas.getWidth())
        && plot.getPane().isVisible()){
            plot.setWindowHeight(canvas.getHeight());
            plot.setWindowWidth(canvas.getWidth());
            plot.closePlot();
            plot.showPlot();
        }


    }

    public void generate(double elapsedSeconds){
        resources.set(0,resources.get(0)+1*elapsedSeconds*al_Farm.size());
        resources.set(1,resources.get(1)+1*elapsedSeconds*al_Mine.size());
        resources.set(2,resources.get(2)+1*elapsedSeconds*al_Lumberjack.size());
    }

    public void pay(ArrayList<Double> resources, ArrayList<Double> cost){
        for(int i=0;i<3;i++){
            resources.set(i,resources.get(i)-cost.get(i));
        }
    }

}
