package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


//Erreur : le menu s'affiche lorsque l'on appuie sur Escape mais il ne revient pas à la scene principale...
public class EscMenu {
    static Scene escScene;

    public EscMenu(Music music, Stage primaryStage, Scene primaryScene, Scene menuScene){
        Canvas canvas = new Canvas(primaryScene.getWidth(),primaryScene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GRAY);
        Pane pane = new Pane(canvas);
        escScene = new Scene(pane);

        MyButton btnResume = new MyButton(0.5*canvas.getWidth()-96,0.5*canvas.getHeight()+50,"./textures/gui/btn_blank.png","RESUME");
        btnResume.centerText();

        MyButton btnMenu = new MyButton(0.5*canvas.getWidth()-96,0.5*canvas.getHeight()+100,"./textures/gui/btn_blank.png","Main Menu");
        btnMenu.centerText();

        MyButton btnExit = new MyButton(0.5*canvas.getWidth()-96,0.5*canvas.getHeight()+150,"./textures/gui/btn_blank.png","Exit");
        btnExit.centerText();

        MyButton btnNbMusic = new MyButton(0.5*canvas.getWidth()-48, 0.5*canvas.getHeight()-100,"./textures/gui/btn_96x32.png","music : "+music.getNbMusic()+"/3");
        btnNbMusic.centerText();
        btnNbMusic.getLName().setFont(Font.font("Felix Titling", FontWeight.BOLD,15 ));

        MyButton btnVolume = new MyButton(0.5*canvas.getWidth()-96, 0.5*canvas.getHeight()-50,"./textures/gui/btn_blank.png","volume : "+music.getVolume()+"/10");
        btnVolume.centerText();
        btnVolume.getLName().setFont(Font.font("Felix Titling", FontWeight.BOLD,15 ));

        MyButton btnLeftSound = new MyButton(0.5*canvas.getWidth()-96-48,0.5*canvas.getHeight()-50,"./textures/gui/btn_32x32.png","<" );
        btnLeftSound.centerText();

        // Baisser le volume :
        btnLeftSound.getPane().setOnMouseClicked(event -> {
            if (music.getVolume() > 0 && music.getVolume() <= 10) {
                music.changeVol(music.getVolume()-1);
                btnVolume.getLName().setText("VOLUME " + music.getVolume() + "/10");
            }
        });

        MyButton btnRightSound = new MyButton(0.5*canvas.getWidth()+96+16, 0.5*canvas.getHeight()-50,"./textures/gui/btn_32x32.png",">");
        btnRightSound.centerText();

        // Augmenter le volume :
        btnRightSound.getPane().setOnMouseClicked(event -> {
            if (music.getVolume() >= 0 && music.getVolume() < 10 ){
                music.changeVol(music.getVolume()+1);
                btnVolume.getLName().setText("VOLUME " + music.getVolume() + "/10");
            }
        });

        MyButton btnPrevMusic = new MyButton(0.5*canvas.getWidth()-96,0.5*canvas.getHeight()-100,"./textures/gui/btn_32x32.png","<");
        btnPrevMusic.centerText();

        // Piste precedente :
        btnPrevMusic.getPane().setOnMouseClicked(event -> {
            if (music.getNbMusic() > 1 && music.getNbMusic() <= 3) {
                music.changeMus(music.getNbMusic()-1);
                btnNbMusic.getLName().setText("Music " + music.getNbMusic() + "/3");
            }
        });


        MyButton btnNextMusic = new MyButton(0.5*canvas.getWidth()+96-32,0.5*canvas.getHeight()-100,"./textures/gui/btn_32x32.png",">");
        btnNextMusic.centerText();

        // Piste suivante :
        btnNextMusic.getPane().setOnMouseClicked(event -> {
            if (music.getNbMusic() >= 1 && music.getNbMusic() < 3 ){
                music.changeMus(music.getNbMusic()+1);
                btnNbMusic.getLName().setText("Music " + music.getNbMusic() + "/3");
            }
        });

        //ERREUR, CE BOUTON NE FONCTIONNE PAS...
        btnResume.getPane().setOnMouseClicked(event -> {
            primaryStage.setResizable(true);
            primaryStage.setScene(primaryScene);
        });

        //ERREUR, CE BOUTON NE FONCTIONNE PAS...
        btnMenu.getPane().setOnMouseClicked(event -> {
            primaryStage.setScene(menuScene);
            primaryStage.show();
            System.out.println(primaryStage.getScene());
            System.out.println(menuScene);
            System.out.println(escScene);

        });

        //ERREUR, CE BOUTON NE FONCTIONNE PAS...
        escScene.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.ESCAPE) {
                primaryStage.setScene(primaryScene);
            }
        });


        btnExit.getPane().setOnMouseClicked(event -> {
            primaryStage.close();
        });

        MyButton optRam = new MyButton(30,30,"./textures/gui/btn_96x32.png","CLEAR RAM");
        optRam.getPane().setOnMouseClicked(event -> {
            System.gc();
            System.out.println("GARBAGE COLLECTOR CALLED");
        });
        optRam.getLName().setFont(Font.font("Felix Titling", FontWeight.BOLD, 14 ));
        optRam.centerText();

        pane.getChildren().addAll(btnResume.getPane(),btnExit.getPane(),btnMenu.getPane(),btnLeftSound.getPane(),
                btnRightSound.getPane(),btnPrevMusic.getPane(),btnNextMusic.getPane(),btnVolume.getPane(),
                btnNbMusic.getPane(), optRam.getPane());

    }

    public Scene getEscScene() {
        return escScene;
    }
}
