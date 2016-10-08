package agi.gameoflife;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created by sebastian.mahr on 08.10.16.
 */
public interface Area {


    boolean[][] getMatix();


    void unSetPoint(Point2D point);
    void unSetPoint(List<Point2D> point);

    void setPoint(Point2D point);
    void setPoint(List<Point2D> point);

    void performIteration();
}
