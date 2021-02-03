package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Screen;

public class Main extends Application {
    Scene scene;

    HUD hud;

    private final int tileSize = 32 ;
    private final int numTilesVert = 100 ;
    private final int numTilesHoriz = 100 ;

    private int scene_height = (int) Math.round(0.85*Screen.getPrimary().getBounds().getHeight());
    private int scene_width = (int) Math.round(0.7*Screen.getPrimary().getBounds().getWidth());

    private int hud_height = 180;
    private int hud_width = scene_width;

    private final int speed = 400 ; //nb de pixel par seconde
    private boolean up ;
    private boolean down ;
    private boolean left ;
    private boolean right ;
    private boolean camUnlocked;
    private boolean escape;
    private boolean map;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("APOKALYPS");

        //Initialisation de la bande son :
        Music music = new Music();

        //root et scène du jeu
        AnchorPane anchorPane = new AnchorPane();
        scene = new Scene(anchorPane, scene_width, scene_height);

        //Création de la carte (le sol etc)
        Pane background = createBackground();

        //Création du joueur
        Player player = new Player(numTilesHoriz*tileSize/2, numTilesVert*tileSize/2);
        background.getChildren().add(player.getPane());

        //Création de l'HUD
        hud = new HUD(scene,anchorPane, background,player);
        hud.toFront();

        //Le personnage s'oriente en fonction de la position du curseur
        background.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.rotate(event.getX(),event.getY());
            }
        });

        background.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.rotate(event.getX(),event.getY());
            }
        });

        //création de la caméra
        Rectangle camera = new Rectangle(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 20, 20);
        camera.setVisible(false);
        background.getChildren().add(camera);

        //Les mouvements de caméra sont fait à partir de l'exemple de James_D ici : https://stackoverflow.com/a/47916374
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(scene.widthProperty());
        clip.heightProperty().bind(scene.heightProperty());

        hud.prefWidthProperty().bind(scene.widthProperty());

        clip.xProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(camera.getX() - scene.getWidth() / 2, 0, background.getWidth() - scene.getWidth()),
                camera.xProperty(), scene.widthProperty()));
        clip.yProperty().bind(Bindings.createDoubleBinding(
                () -> clampRange(camera.getY() + hud_height /2  - scene.getHeight() / 2, 0, background.getHeight() + hud_height - 2 - scene.getHeight()),
                camera.yProperty(), scene.heightProperty()));

        background.setClip(clip);
        background.translateXProperty().bind(clip.xProperty().multiply(-1));
        background.translateYProperty().bind(clip.yProperty().multiply(-1));

        MainMenu mainMenu = new MainMenu(primaryStage,scene,music);
        mainMenu.player = player;
        EscMenu escMenu = new EscMenu(music,primaryStage,scene, mainMenu.getMenuScene());

        scene.setOnKeyPressed(e -> processKey(e.getCode(), true, mainMenu));
        scene.setOnKeyReleased(e -> processKey(e.getCode(), false,mainMenu));

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(background);

        anchorPane.setBottomAnchor(hud,-1.0);
        anchorPane.setTopAnchor(borderPane,0.0);
        anchorPane.getChildren().addAll(borderPane, hud,hud.getPlot().pane);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = -1 ;
            @Override
            public void handle(long now) {
                long elapsedNanos = now - lastUpdate ;
                if (lastUpdate < 0) {
                    lastUpdate = now ;
                    return ;
                }
                double elapsedSeconds = elapsedNanos / 1_000_000_000.0 ;
                double deltaX = 0 ;
                double deltaY = 0 ;
                if (right) deltaX += speed ;
                if (left) deltaX -= speed ;
                if (down) deltaY += speed ;
                if (up) deltaY -= speed ;
                if (escape) {
                    primaryStage.setResizable(false);
                    primaryStage.setScene(escMenu.getEscScene());
                }
                if (map) {
                    hud.getPlot().showPlot();
                }

                music.soundtrack.play();

                if (!camUnlocked){

                    //Déplacement du personnage et si il y aura intersection avec un batiment,
                    //alors on annule le mouvement en cours (selon x ou y) !

                    player.setX(clampRange(player.getX() + deltaX * elapsedSeconds, 0, background.getWidth() - player.getWidth()));
                    if(player.intersectWith(hud.getAl_Buildings())){
                        deltaX = -deltaX;
                        player.setX(clampRange(player.getX() + deltaX * elapsedSeconds, 0, background.getWidth() - player.getWidth()));
                    }
                    player.setY(clampRange(player.getY() + deltaY * elapsedSeconds, 0, background.getHeight() - player.getHeight()));
                    if(player.intersectWith(hud.getAl_Buildings())){
                        deltaY = -deltaY;
                        player.setY(clampRange(player.getY() + deltaY * elapsedSeconds, 0, background.getHeight() - player.getHeight()));
                    }

                    camera.setX(player.getX()+player.getWidth()/2);
                    camera.setY(player.getY()+player.getHeight()/2);;
                } else {
                    camera.setX(clampRange(camera.getX() + deltaX * elapsedSeconds, 0, background.getWidth() - player.getWidth()));
                    camera.setY(clampRange(camera.getY() + deltaY * elapsedSeconds, 0, background.getHeight() - player.getHeight()));

                }

                player.gc.clearRect(0,0, player.getWidth(), player.getHeight());
                player.render();

                hud.update();
                hud.generate(elapsedSeconds);
                lastUpdate = now ;
            }
        };

        /*
        On affiche la scène du menu principal
        */
        primaryStage.setScene(mainMenu.getMenuScene());
        primaryStage.show();

        timer.start();
    }

    //permets de ne pas dépasser la carte
    private double clampRange(double value, double min, double max) {
        if (value < min) return min ;
        if (value > max) return max ;
        return value ;
    }

    //On utilise un if-else si on veut pouvoir CHANGER les controles car les
    private void processKey(KeyCode code, boolean on,MainMenu mainMenu) {
        if (code == mainMenu.getkLeft()) {
            left = on;
        } else if (code == mainMenu.getkRight()) {
            right = on;
        } else if (code == mainMenu.getkUp()) {
            up = on;
        } else if (code == mainMenu.getkDown()) {
            down = on;
        } else if (code == KeyCode.SHIFT) {
            camUnlocked = on;
        } else if (code == KeyCode.ESCAPE) {
            escape = on;
        } else if (code == KeyCode.M) {
            map = on;
        }
    }

    private Pane createBackground() {

        List<Integer> filledTiles = sampleWithoutReplacement(numTilesHoriz * numTilesVert);

        Canvas canvas = new Canvas(numTilesHoriz * tileSize, numTilesVert * tileSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane pane = new Pane(canvas);


        pane.setMinSize(numTilesHoriz * tileSize, numTilesVert * tileSize);
        pane.setPrefSize(numTilesHoriz * tileSize, numTilesVert * tileSize);
        pane.setMaxSize(numTilesHoriz * tileSize, numTilesVert * tileSize);

        Image grass = new Image("./textures/map/grass.png");
        Image grassDark = new Image("./textures/map/grass_dark.png");
        Image grassLight = new Image("./textures/map/grass_light.png");
        ArrayList<Image> groundType = new ArrayList<>();
        groundType.add(grass);
        groundType.add(grassLight);
        groundType.add(grassDark);

        Random rng = new Random();

        for (Integer tile : filledTiles) {
            int x = (tile % numTilesHoriz) * tileSize;
            int y = (tile / numTilesHoriz) * tileSize;
            gc.drawImage(groundType.get(rng.nextInt(3)), x,y);
        }

        return pane ;
    }

    private List<Integer> sampleWithoutReplacement(int populationSize) {

        List<Integer> population = new ArrayList<>();
        for (int i = 0 ; i < populationSize; i++)
            population.add(i);

        return population;
    }

    public static void main(String[] args) {
        launch(args);
    }
}