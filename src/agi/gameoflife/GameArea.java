package agi.gameoflife;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by peterkrieger on 08.10.16.
 */
public class GameArea {

    private boolean [][]matrix;


    public GameArea(int breite, int hoehe){
        matrix = new boolean[breite][hoehe];
    }

    public boolean matrix  (Point2D point){
        return true;
    }
}
