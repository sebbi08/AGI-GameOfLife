package agi.gameoflife;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by peterkrieger on 08.10.16.
 */
public class GameArea implements Area{

    private boolean [][]matrix;
    Random rand = new Random();

    public GameArea(int breite, int hoehe, boolean random, List<Point2D> liste){
        matrix = new boolean[breite][hoehe];

        // Felder werde zufällig befühlt
        if(random){
            befuehleFelderRandom(breite, hoehe);
        }
        else
            befuehleFelderMitBenutzerEingaben(liste);
    }

    private void befuehleFelderMitBenutzerEingaben(List<Point2D> liste) {

    }

    private void befuehleFelderRandom(int breite, int hoehe) {

        List<Point2D> randomList = new ArrayList<>();
        Random anzahlFelder = new Random();
        int felder = anzahlFelder.nextInt(breite*hoehe)+1;
        int schleifenDurchlauf = felder;
        while (schleifenDurchlauf > 0 ) {
            //zufällige Punkt wird erstellt
            Point2D randomPoint = new Point(rand.nextInt(felder) + 1,rand.nextInt(felder) + 1);
            randomList.add(randomPoint);
            schleifenDurchlauf--;
        }
    }


    @Override
    public boolean[][] getMatix() {
        return new boolean[0][];
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
        List<Point2D> deleteList = new ArrayList<>();
        List<Point2D> setList = new ArrayList<>();

        for (int i = 0; i < this.matrix.length; i++) {
            for (int y = 0; y < this.matrix[i].length; y++) {
                int neighbours = countNeighbours(new Point(i,y));
                Point curentPoint = new Point(i,y);
                if(neighbours < 2){
                    deleteList.add(curentPoint);
                }else if(neighbours == 3){
                    setList.add(curentPoint);
                }else if(neighbours > 3){
                    deleteList.add(curentPoint);
                }
            }
        }

        setPoint(setList);
        unSetPoint(deleteList);
    }

    private int countNeighbours(Point2D point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int curX = x + i;
                int curY = x + j;

                if(curX == -1) curX = matrix.length -1;
                if(curY == -1) curY = matrix[curX].length -1;
                if(curX >= matrix.length) curX = 0;
                if(curY >= matrix[curX].length) curY = 0;

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
                returnValue += (matrix[i][y] ? "1" : "0") + (y < this.matrix[i].length-1 ? "||" : "");
            }
            returnValue += "\n";
        }
        return returnValue;
    }
}
