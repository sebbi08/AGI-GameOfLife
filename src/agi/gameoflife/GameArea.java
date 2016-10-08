package agi.gameoflife;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by peterkrieger on 08.10.16.
 */
public class GameArea implements Area{

    int breite, hoehe;
    private boolean [][]matrix;
    private Random rand = new Random();
    private List<Point2D> randomList = new ArrayList<>();

    public GameArea(int breite, int hoehe, boolean random, List<Point2D> liste){
        this.breite = breite;
        this.hoehe = hoehe;
        matrix = new boolean[breite][hoehe];
        // Felder werde zufällig befühlt
        if(random){
            befuehleFelderRandom();
        }
        else
            befuehleFelderMitBenutzerEingaben(liste);
    }

    private void befuehleFelderMitBenutzerEingaben(List<Point2D> liste) {

    }

    private void befuehleFelderRandom() {

        Random anzahlFelder = new Random();
        int felder = anzahlFelder.nextInt(this.breite*this.hoehe)+1;
        int schleifenDurchlauf = felder;
        while (schleifenDurchlauf > 0 ) {
            //zufällige Punkt wird erstellt
            Point2D randomPoint = new Point(rand.nextInt(this.breite),rand.nextInt(this.hoehe));
            randomList.add(randomPoint);
            schleifenDurchlauf--;
        }

        //Matrize wird befühlt
        Point2D temp;
        for (int b = 0; b < this.breite; b++) {
            for (int h = 0; h < this.hoehe; h++) {

                if (b*h < randomList.size() && randomList.size() > 0){
                    temp = randomList.get(b*h);
                    matrix[(int)temp.getX()][(int)temp.getY()] = true;
                }
                else
                    matrix[b][h] = false;
            }
        }
    }


    @Override
    public boolean[][] getMatix() {
        return  matrix;
    }

    @Override
    public void unSetPoint(Point2D point) {

    }

    @Override
    public void unSetPoint(List<Point2D> point) {

    }

    @Override
    public void setPoint(Point2D point) {

    }

    @Override
    public void setPoint(List<Point2D> point) {

    }

    @Override
    public void performIteration() {

    }

    private int countNeighbours(Point2D point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int curX = x + i;
                int curY = x + j;

                if (!(i == 0 && j == 0)) {
                    if (matrix[curX][curY]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        String returnValue = "";
        for (int i = 0; i < this.matrix.length; i++) {
            for (int y = 0; y < this.matrix[i].length; y++) {
                returnValue += matrix[i][y] ? "1" : "0";
            }
            returnValue += "\n";
        }
        return returnValue;
    }
}
