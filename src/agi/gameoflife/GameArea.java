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
            Point2D randomPoint = new Point2D() {

                @Override
                public double getX() {
                    return rand.nextInt(felder) + 1;
                }

                @Override
                public double getY() {
                    return rand.nextInt(felder) + 1;
                }

                //brauchen nicht?
                @Override
                public void setLocation(double x, double y) {

                }
            };
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

    }
}
