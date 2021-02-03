package sample;

import javafx.scene.image.Image;

public class Wall extends Sprite {
    Plot plot;
    String typeWall;

    public Wall(double x, double y, Plot plot){
        id = 5;
        this.plot = plot;
        this.x = x;
        this.y = y;

        update();

        initialize();

        maxHP = 300;
        hp = 300;

        constructHealthBar();
        updateHealthBar();
    }


    public void update(){
        Sprite[] neighbors = plot.neighbors((int)x/32,(int)y/32);
        String typeWall = "";
        for(int i=0;i<4;i++){
            if(neighbors[i] == null){
                typeWall += "0";
            } else if(neighbors[i].id != 5) {
                typeWall += "0";
            } else {
                typeWall += "1";
                switch (i) {
                    case 0:
                        ((Wall)neighbors[i]).typeWall = ((Wall)neighbors[i]).typeWall.substring(0,2)
                                + "1" + ((Wall)neighbors[i]).typeWall.substring(3);
                        System.out.println(((Wall)neighbors[i]).typeWall);
                        break;
                    case 1:
                        ((Wall)neighbors[i]).typeWall = ((Wall)neighbors[i]).typeWall.substring(0,3) + "1";
                        System.out.println(((Wall)neighbors[i]).typeWall);
                        break;
                    case 2:
                        ((Wall)neighbors[i]).typeWall = "1"+((Wall)neighbors[i]).typeWall.substring(1);
                        System.out.println(((Wall)neighbors[i]).typeWall);
                        break;
                    case 3:
                        ((Wall)neighbors[i]).typeWall = ((Wall)neighbors[i]).typeWall.substring(0,1)
                                + "1" + ((Wall)neighbors[i]).typeWall.substring(2);
                        System.out.println(((Wall)neighbors[i]).typeWall);
                        break;
                }
                Image img = new Image("./textures/buildings/wall_"+((Wall)neighbors[i]).typeWall+".png");
                neighbors[i].setImage(img);
                neighbors[i].render();
            }
        }
        System.out.println("typewall="+typeWall);

        Image img = new Image("./textures/buildings/wall_"+typeWall+".png");
        this.image = img;
        render();
        this.typeWall = typeWall;
    }

}
