package agi.gameoflife;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by sebastian.mahr on 08.10.16.
 */
public interface Area {


    boolean[][] getMatix();


    void unSetPoint(Point2D point); // Im Spielfeld einen Punkt löschen
    void unSetPoint(List<Point2D> point); // Im Spielfeld mehrere Punkte löschen

    void setPoint(Point2D point); // Spielfeld befüllen mit einem Point
    void setPoint(List<Point2D> point); // Spielfeld befüllen mit einer Liste

    void performIteration(); // Schritt im Spiel
}
