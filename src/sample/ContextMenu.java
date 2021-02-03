package sample;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ContextMenu extends Sprite {

    public ContextMenu(){
        image = new Image("./textures/gui/contextMenu_110x128.png");
        initialize();
        hide();

        MyButton btnExit = new MyButton(x+96,y+5,"./textures/gui/exit_11x11.png","");
        btnExit.getPane().setOnMouseClicked((MouseEvent mouseClicked)-> hide());

        MyButton btnSoldier = new MyButton(x+7, y+45,"./textures/gui/btn_96x32.png","Soldier");
        btnSoldier.centerText();

        MyButton btnArcher = new MyButton(x+7, y+85,"./textures/gui/btn_96x32.png","Archer");
        btnArcher.centerText();

        lName.setFont(Font.font("Felix Titling", FontWeight.BOLD, 17));
        lName.setTextFill(Color.WHITE);
        lName.setText("RECRUIT :");
        lName.relocate(12,18);

        pane.getChildren().addAll(btnExit.getPane(),btnArcher.getPane(),btnSoldier.getPane(), lName);
        pane.setOnMouseExited((MouseEvent mouseExited) -> hide());
    }

    public void show(MouseEvent mouseEvent, double barrX, double barrY){
        pane.relocate(mouseEvent.getX()+barrX-20, mouseEvent.getY()+barrY-20);
        System.out.println("show");
        pane.setVisible(true);
        pane.setMouseTransparent(false);
        pane.toFront();
    }

    public void hide(){
        pane.setVisible(false);
        pane.setMouseTransparent(true);
    }

}
