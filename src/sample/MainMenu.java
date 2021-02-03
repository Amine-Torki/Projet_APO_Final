package sample;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.input.KeyCode;

// La classe MainMenu qui permet la création et l'affichage du menu principal au lancement du jeu.

public class MainMenu {
    // attribut scene qui va servir à afficher le menu au lancement du jeu.
    static Scene menuScene;
    // attribut volume, un flottant qui sera compris entre 0 et 1 et qui servira à fixer le volume de la musique du jeu
    private Music music;
    private KeyCode kUp = KeyCode.Z;
    private KeyCode kLeft = KeyCode.Q;
    private KeyCode kDown = KeyCode.S;
    private KeyCode kRight = KeyCode.D;
    Player player;


    public MainMenu(Stage primaryStage, Scene primaryScene, Music music) {
        this.music = music;
        // Mise en place de l'image d'arrière plan
        Image im_background = new Image("./textures/gui/Menu2.png");
        Canvas canvas = new Canvas(im_background.getWidth(),im_background.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Lancement de la musique au volume initialisé précédemment

        gc.drawImage(im_background,0,0);
        gc.setFill(Color.GRAY);
        Pane pane = new Pane(canvas);
        menuScene = new Scene(pane);
        primaryStage.setScene(menuScene);
        primaryStage.show();

        // Création du bouton Start, devant servir à lancer le jeu
        MyButton btnStart = new MyButton(0.5*im_background.getWidth()-96/2, 0.25* im_background.getHeight()-32/2+100,
                "./textures/gui/btn_96x32.png","PLAY");
        btnStart.centerText();
        pane.getChildren().add(btnStart.getPane());

        // affichage du volume de la musique sur 10
        MyButton btnVolume = new MyButton(0.5*im_background.getWidth()-96/2-250, 0.6*im_background.getHeight()-32/2+195,
                "./textures/gui/btn_blank.png","VOLUME " + music.getVolume() +"/10");
        btnVolume.centerText();
        pane.getChildren().add(btnVolume.getPane());

        // Création du bouton servant à baisser le son
        MyButton btnLeftSound = new MyButton(0.51* im_background.getWidth()-96/2-300, 0.6* im_background.getHeight() - 32/2 + 195,
                "./textures/gui/btn_32x32.png", "<");
        btnLeftSound.centerText();
        pane.getChildren().add(btnLeftSound.getPane());

        // Création du bouton servant à augmenter le son
        MyButton btnRightSound = new MyButton(0.55* im_background.getWidth()-96/2 - 98, 0.6* im_background.getHeight() - 32/2 + 195,
                "./textures/gui/btn_32x32.png", ">");
        btnRightSound.centerText();
        pane.getChildren().add(btnRightSound.getPane());

        //Affichage de la piste actuelle
        MyButton btnNbMusic = new MyButton(0.5*im_background.getWidth()-96/2-250, 0.6*im_background.getHeight()-32/2+155,
                "./textures/gui/btn_blank.png","Music " + music.getNbMusic() +"/3");
        btnNbMusic.centerText();
        pane.getChildren().add(btnNbMusic.getPane());

        // Bouton qui passe à la piste precedente
        MyButton btnPrevMusic = new MyButton(0.51* im_background.getWidth()-96/2-300, 0.6* im_background.getHeight() - 32/2 + 155,
                "./textures/gui/btn_32x32.png", "<");
        btnPrevMusic.centerText();
        pane.getChildren().add(btnPrevMusic.getPane());

        // Bouton qui passe à la piste suivante
        MyButton btnNextMusic = new MyButton(0.55* im_background.getWidth()-96/2 - 98, 0.6* im_background.getHeight() - 32/2 + 155,
                "./textures/gui/btn_32x32.png", ">");
        btnNextMusic.centerText();
        pane.getChildren().add(btnNextMusic.getPane());

        // Création du bouton nommé "controls"
        MyButton btnControls = new MyButton(0.75*im_background.getWidth()-25, 0.25*im_background.getHeight()+32/2+153,
                "./textures/gui/btn_96x32.png", "CONTROLS" );
        btnControls.centerText();
        pane.getChildren().add(btnControls.getPane());

        // Création du bouton Exit, devant servir à fermer le jeu
        MyButton btnExit = new MyButton(0.5*im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+105,
                "./textures/gui/btn_96x32.png", "EXIT");
        btnExit.centerText();
        pane.getChildren().add(btnExit.getPane());

        // Création du bouton nommé "Forward" dans les contrôles
        MyButton btnForward = new MyButton(0.7* im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+200,
                "./textures/gui/btn_96x32.png", "FORWARD" );
        btnForward.centerText();
        pane.getChildren().add(btnForward.getPane());


        // Création du bouton nommé "Backwards" dans les contrôles
        MyButton btnBackwards = new MyButton(0.7* im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+240,
                "./textures/gui/btn_96x32.png", "BACKWARDS" );
        btnBackwards.getLName().setFont(Font.font("Felix Titling", FontWeight.BOLD, 14 ));
        btnBackwards.centerText();
        pane.getChildren().add(btnBackwards.getPane());

        // Création du bouton nommé "Left" dans les contrôles
        MyButton btnLeft = new MyButton(0.7* im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+280,
                "./textures/gui/btn_96x32.png", "LEFT" );
        btnLeft.centerText();
        pane.getChildren().add(btnLeft.getPane());

        // Création du bouton nommé "Right" dans les contrôles
        MyButton btnRight = new MyButton(0.7* im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+320,
                "./textures/gui/btn_96x32.png", "RIGHT" );
        btnRight.centerText();
        pane.getChildren().add(btnRight.getPane());

        // Création du bouton affichant la touche clavier servant à avancer dans le jeu (Z par défaut)
        MyButton btnSetKUp = new MyButton(0.85* im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+200,
                "./textures/gui/btn_96x32.png", "Z" );
        btnSetKUp.centerText();
        pane.getChildren().add(btnSetKUp.getPane());
        btnSetKUp.getPane().setOnMouseClicked(event ->{
            btnSetKUp.getLName().setText("?");
            menuScene.setOnKeyPressed(e1 -> {
                kUp = e1.getCode();
                btnSetKUp.getLName().setText(kUp.getName());
                btnSetKUp.centerText();
                menuScene.setOnKeyPressed(e2 -> {}); //On arrête la modification
            });
        });

        // Création du bouton affichant la touche clavier servant à reculer dans le jeu (S par défaut)
        MyButton btnSetKDown = new MyButton(0.85* im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+240,
                "./textures/gui/btn_96x32.png", "S" );
        btnSetKDown.centerText();
        pane.getChildren().add(btnSetKDown.getPane());
        btnSetKDown.getPane().setOnMouseClicked(event ->{
            btnSetKDown.getLName().setText("?");
            menuScene.setOnKeyPressed(e1 -> {
                kDown = e1.getCode();
                btnSetKDown.getLName().setText(kDown.getName());
                btnSetKDown.centerText();
                menuScene.setOnKeyPressed(e2 -> {});
            });
        });

        // Création du bouton affichant la touche clavier servant à aller à gauche dans le jeu (Q par défaut)
        MyButton btnSetKLeft = new MyButton(0.85* im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+280,
                "./textures/gui/btn_96x32.png", "Q" );
        btnSetKLeft.centerText();
        pane.getChildren().add(btnSetKLeft.getPane());
        btnSetKLeft.getPane().setOnMouseClicked(event ->{
            btnSetKLeft.getLName().setText("?");
            menuScene.setOnKeyPressed(e1 -> {
                kLeft = e1.getCode();
                btnSetKLeft.getLName().setText(kLeft.getName());
                btnSetKLeft.centerText();
                menuScene.setOnKeyPressed(e2 -> {});
            });
        });

        // Création du bouton affichant la touche clavier servant à aller à droite dans le jeu (D par défaut)
        MyButton btnSetKRight = new MyButton(0.85* im_background.getWidth()-96/2, 0.25*im_background.getHeight()+32/2+320,
                "./textures/gui/btn_96x32.png", "D" );
        btnSetKRight.centerText();
        pane.getChildren().add(btnSetKRight.getPane());
        btnSetKRight.getPane().setOnMouseClicked(event ->{
            btnSetKRight.getLName().setText("?");
            menuScene.setOnKeyPressed(e1 -> {
                kRight = e1.getCode();
                btnSetKRight.getLName().setText(kRight.getName());
                btnSetKRight.centerText();
                menuScene.setOnKeyPressed(e2 -> {});
            });
        });


        //Lancer le jeu :
        btnStart.getPane().setOnMouseClicked(event -> {
            primaryStage.setScene(primaryScene);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(1075);
            primaryStage.setMinHeight(735);
        });

        // Augmenter le volume :
        btnRightSound.getPane().setOnMouseClicked(event -> {
            if (music.getVolume() >= 0 && music.getVolume() < 10 ){
                music.changeVol(music.getVolume()+1);
                btnVolume.getLName().setText("VOLUME " + music.getVolume() + "/10");
            }
        });

        // Baisser le volume :
        btnLeftSound.getPane().setOnMouseClicked(event -> {
            if (music.getVolume() > 0 && music.getVolume() <= 10) {
                music.changeVol(music.getVolume()-1);
                btnVolume.getLName().setText("VOLUME " + music.getVolume() + "/10");
            }
        });

        // Piste suivante :
        btnNextMusic.getPane().setOnMouseClicked(event -> {
            if (music.getNbMusic() >= 1 && music.getNbMusic() < 3 ){
                music.changeMus(music.getNbMusic()+1);
                btnNbMusic.getLName().setText("Music " + music.getNbMusic() + "/3");
            }
        });

        // Piste precedente :
        btnPrevMusic.getPane().setOnMouseClicked(event -> {
            if (music.getNbMusic() > 1 && music.getNbMusic() <= 3) {
                music.changeMus(music.getNbMusic()-1);
                btnNbMusic.getLName().setText("Music " + music.getNbMusic() + "/3");
            }
        });

        // Méthode faisant en sorte que le bouton Exit ferme le jeu quand on clique dessus
        btnExit.getPane().setOnMouseClicked(event -> {
            primaryStage.close();
        });

        primaryStage.setWidth(950);
        primaryStage.setHeight(670);
        primaryStage.setResizable(false);
    }



    public Scene getMenuScene() {
        return menuScene;
    }

    private void changeControls(MyButton btn){
        btn.getPane().setOnMouseClicked(event ->{
            btn.getLName().setText("?");
            menuScene.setOnKeyPressed(e -> {
               kUp = e.getCode();
                btn.getLName().setText(e.toString());
            });
        });
    }

    public KeyCode getkUp() {
        return kUp;
    }

    public KeyCode getkLeft() {
        return kLeft;
    }

    public KeyCode getkDown() {
        return kDown;
    }

    public KeyCode getkRight() {
        return kRight;
    }
}
