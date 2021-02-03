package sample;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Unit extends Sprite {

    double[][] path;
    int[][][] map = new int[100][100][6]; //Matrice de la map contenant en chaque point un vecteur V[id,x,y,f,g,h,xp,yp]
    HUD hud;

public Unit(){

}
/* ALGORITHME DE RECHERCHE DE CHEMIN :
Adapté du pseudo code sur la page wikipédia https://fr.wikipedia.org/wiki/Algorithme_A*

Ici openList contient des vecteurs [x,y,f,g,h], ce sont des cases qui restent à explorer
Avec x,y les coordonnées du point, f = g+h le coût total estimé pour atteindre l'objectif
g le coût déja parcouru pour arriver au point
h le coût heuristique pour arriver à l'objectif (estimation à vol d'oiseau)

closedList sont les cases déjà explorées, on n'y reviendra pas (sauf pour retrouver notre chemin à la fin)
 */
public void findPath(int x, int y){
    PriorityQueue<double[]> openList = new PriorityQueue<>(this::compare);
    ArrayList<double[]> closedList = new ArrayList();
    double heuristicCost = Math.sqrt(Math.abs(x-this.x)*Math.abs(x-this.x)+Math.abs(y-this.y)*Math.abs(y-this.y));
    double[] start = {this.x,this.y,heuristicCost,0,heuristicCost};
    openList.add(start);

    while (!openList.isEmpty()) {
        double[] current = openList.remove();
        if(current[0] == x && current[1] == y){
            //path = reconstructPath(current);
        }
        ArrayList<double[]> neighbors = grassNeighbors();


    }

}

private int compare(double[] v1, double[] v2){
    if(v1[2] < v2[2]){
        return 1;
    } else if (v1[2] == v2[2]) {
        return 0;
    } else {
        return -1;
    }
}

//private double[][] reconstructPath(double [] current){

//}

//plot[x][y-1], plot[x+1][y], plot[x][y+1], plot[x-1][y]
// plot[x+1][y-1], plot[x+1][y+1], plot[x-1][y+1], plot[x-1][y-1]

private ArrayList<double[]> grassNeighbors(){ //Incluant les voisins diagonaux
    ArrayList<double[]> arrayNeighbors = new ArrayList();
    Sprite[][] arrayPlot = hud.getPlot().getArray();
    //Nord
    if(arrayPlot[(int)x][(int)y-1]==null){
        arrayNeighbors.add(new double[] {x,y-1,0,0,0});
    }
    //Est
    if(arrayPlot[(int)x+1][(int)y]==null){
        arrayNeighbors.add(new double[] {x+1,y,0,0,0});
    }
    //Sud
    if(arrayPlot[(int)x][(int)y+1]==null){
        arrayNeighbors.add(new double[] {x,y+1,0,0,0});
    }
    //Ouest
    if(arrayPlot[(int)x-1][(int)y]==null){
        arrayNeighbors.add(new double[] {x-1,y,0,0,0});
    }

    if(arrayPlot[(int)x][(int)y-1]==null){
        arrayNeighbors.add(new double[] {x,y-1,0,0,0});
    }



    return arrayNeighbors;
}

}
