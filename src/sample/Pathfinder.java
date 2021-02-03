package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Pathfinder {
    double[][] path;
    HUD hud;
    double[][] gCost = new double[100][100];
    double[][] fCost = new double[100][100];
    int[] start = new int[2];
    int[] goal = new int[2];
    PriorityQueue<int[]> openList = new PriorityQueue<>(this::compare);
    ArrayList<int[]> closedList = new ArrayList();

    public Pathfinder(HUD hud){
        this.hud = hud;
    }


    /* ALGORITHME DE RECHERCHE DE CHEMIN : (PAS FINI !)
Adapté du pseudo code sur la page wikipédia https://fr.wikipedia.org/wiki/Algorithme_A*

Ici openList contient des tuples [x,y], ce sont des cases qui restent à explorer
Avec x,y les coordonnées du point (divisées par 32)

gCost est le coût déja dépensé/parcouru pour arriver au point
hCost est le coût heuristique pour arriver à l'objectif (distance à vol d'oiseau)
fCost = g+h est le coût total estimé pour atteindre l'objectif

closedList sont les cases déjà explorées, on n'y reviendra pas
 */
    public void findPath(){
        openList.clear();
        closedList.clear();
        Arrays.fill(gCost,10000); //On initialise gCost avec des valeurs "infinies"
        Arrays.fill(fCost,10000); //On initialise fCost avec des valeurs "infinies"
        int[][][] parent = new int[100][100][2]; // va servir à retracer le chemin
        gCost[start[0]][start[1]]=0;
        openList.add(start);


        while (!openList.isEmpty()) {
            int[] current = openList.remove();
            if(current[0] == goal[0] && current[1] == goal[1]){
                //path = reconstructPath(current);
            }
            ArrayList<int[]> neighbors = grassNeighbors(goal[0],goal[1]);
            for (int[] neighbor:neighbors) {
                if(!closedList.contains(neighbor)){
                    double neighHCost = hCost(neighbor,goal);
                    double neighGCost = gCost[current[0]][current[1]] + 1;
                    if(isDiag(current,neighbor)){
                        neighGCost += Math.sqrt(2)-1;
                    }
                    //Si neighbor n'est pas dans openList avec un coût inférieur,
                    // alors on l'ajoute avec le coût trouvé juste au dessus
                    if((!openList.contains(neighbor))){
                        parent[neighbor[0]][neighbor[1]]= current;
                        gCost[neighbor[0]][neighbor[1]] = neighGCost;
                        fCost[neighbor[0]][neighbor[1]] = neighGCost + neighHCost;
                        openList.add(neighbor);
                    }
                    else if (openList.contains(neighbor) && neighGCost<gCost[neighbor[0]][neighbor[1]]){
                        parent[neighbor[0]][neighbor[1]]= current;
                        gCost[neighbor[0]][neighbor[1]] = neighGCost;
                        fCost[neighbor[0]][neighbor[1]] = neighGCost + neighHCost;
                    }

                }
            }



        }

    }

    private int compare(int[] v1, int[] v2){
        if(fCost[v1[0]][v1[1]] < fCost[v2[0]][v2[1]]) {
            return 1;
        } else if (fCost[v1[0]][v1[1]] == fCost[v2[0]][v2[1]]) {
            return 0;
        } else {
            return -1;
        }
    }

//private double[][] reconstructPath(double [] current){

//}

//plot[x][y-1], plot[x+1][y], plot[x][y+1], plot[x-1][y]
// plot[x+1][y-1], plot[x+1][y+1], plot[x-1][y+1], plot[x-1][y-1]

    private ArrayList<int[]> grassNeighbors(int x,int y){ //Incluant les voisins diagonaux
        ArrayList<int[]> arrayNeighbors = new ArrayList();
        Sprite[][] arrayPlot = hud.getPlot().getArray();
        //Nord
        if(arrayPlot[x][y-1]==null){
            arrayNeighbors.add(new int[] {x,y-1});
        }
        //Est
        if(arrayPlot[x+1][y]==null){
            arrayNeighbors.add(new int[] {x+1,y});
        }
        //Sud
        if(arrayPlot[x][y+1]==null){
            arrayNeighbors.add(new int[] {x,y+1});
        }
        //Ouest
        if(arrayPlot[x-1][y]==null){
            arrayNeighbors.add(new int[] {x-1,y});
        }
        //Nord-Est
        if(arrayPlot[x+1][y-1]==null){
            arrayNeighbors.add(new int[] {x+1,y-1});
        }
        //Sud-Est
        if(arrayPlot[x+1][y-1]==null){
            arrayNeighbors.add(new int[] {x+1,y+1});
        }
        //Sud-Ouest
        if(arrayPlot[x+1][y-1]==null){
            arrayNeighbors.add(new int[] {x-1,y+1});
        }
        //Nord-Ouest
        if(arrayPlot[x+1][y-1]==null){
            arrayNeighbors.add(new int[] {x-1,y-1});
        }
        return arrayNeighbors;
    }

    /*
    Calcule le coût heuristique (la distance à vol d'oiseau) pour aller au point B en partant de A
     */
    private double hCost(int[] pointA, int[] pointB){
        return Math.sqrt(Math.abs(pointB[0]-pointA[0])*Math.abs(pointB[0]-pointA[0])+
                Math.abs(pointB[1]-pointA[1])*Math.abs(pointB[1]-pointA[1]));
    }
    /*
    Retourne True si les points A et B sont des voisins diagonaux, sinon False.
     */
    private boolean isDiag(int[] pointA, int[] pointB){
        return (Math.abs(pointA[0]-pointB[0])+Math.abs(pointA[1]-pointB[1]) ==2);
    }
}
