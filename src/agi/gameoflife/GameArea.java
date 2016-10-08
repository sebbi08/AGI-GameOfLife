package agi.gameoflife;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by peterkrieger on 08.10.16.
 */
public class GameArea implements Area {

    private boolean[][] matrix;


    public GameArea(int breite, int hoehe, boolean random, List<Point2D> liste) {
        matrix = new boolean[breite][hoehe];
        if (random) {
            Point2D randomPoint = new Point2D() {
                @Override
                public double getX() {
                    return 0;
                }

                @Override
                public double getY() {
                    return 0;
                }

                @Override
                public void setLocation(double x, double y) {

                }
            };
            List<Point2D> randomList;
            //randomList.add(0, null);
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
