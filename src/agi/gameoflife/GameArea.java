package agi.gameoflife;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
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

    //Konstruktor
    public GameArea(int breite, int hoehe, boolean random, List<Point2D> liste){
        create(breite,hoehe,random,liste);
    }

    public void create(int breite, int hoehe, boolean random, List<Point2D> liste){
        this.breite = breite;
        this.hoehe = hoehe;
        matrix = new boolean[breite][hoehe];
        randomList = new ArrayList<>();
        // Felder werde zufällig befühlt
        if(random){
            befuehleFelderRandom();
        }
        else
            setPoint(liste);
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
        befuellMatrize();
    }

    private void befuellMatrize() {
        //Matrize wird befühlt
        Point2D temp;
        for (int b = 0; b < this.breite; b++) {
            fuelleSpalte(b);
        }
    }

    private void fuelleSpalte(int b) {
        Point2D temp;
        for (int h = 0; h < this.hoehe; h++) {

            if (b*h < randomList.size() && randomList.size() > 0){
                temp = randomList.get(b*h);
                matrix[(int)temp.getX()][(int)temp.getY()] = true;
            }
            else
                matrix[b][h] = false;
        }
    }


    @Override
    public boolean[][] getMatix() {
        return  matrix;
    }

    @Override
    public void unSetPoint(Point2D point) {
        matrix[(int)point.getX()][(int)point.getY()] = false;
    }

    @Override
    public void unSetPoint(List<Point2D> point) {
        for(int i = 0; i < point.size(); i++){
            unSetPoint(point.get(i));
        }
    }

    @Override
    public void setPoint(Point2D point) { // 1,2;
            matrix[(int)point.getX()][(int)point.getY()] = true;
    }

    @Override
    public void setPoint(List<Point2D> point) {
        for (int i = 0; i < point.size(); i++) {
            setPoint(point.get(i));
        }
    }

    @Override
    public void performIteration() {
        List<Point2D> deleteList = new ArrayList<>();
        List<Point2D> setList = new ArrayList<>();

        for (int i = 0; i < this.matrix.length; i++) {
            for (int y = 0; y < this.matrix[i].length; y++) {
                int neighbours = countNeighbours(new Point(i,y));
                Point currentPoint = new Point(i,y);
                if(neighbours < 2){
                    deleteList.add(currentPoint);
                }else if(neighbours == 3){
                    setList.add(currentPoint);
                }else if(neighbours > 3){
                    deleteList.add(currentPoint);
                }
            }
        }

        setPoint(setList);
        unSetPoint(deleteList);
    }

    @Override
    public void saveGame() {

        Writer fw = null;

        try
        {
            fw = new FileWriter( "AktuellerStatus.txt" );
            fw.write(toString(1));
            fw.append( System.getProperty("line.separator") ); // e.g. "\n"
        }
        catch ( IOException ex ) {
            System.err.println( "Konnte Datei nicht erstellen" );
        }
        finally {
            if ( fw != null )
                try { fw.close(); } catch ( IOException ex ) { ex.printStackTrace(); }
        }
    }

    @Override
    public void loadGame() {
        String matrize;
        File file = new File("AktuellerStatus.txt");
        if (!file.canRead() || !file.isFile())
            System.exit(0);

        FileReader fr = null;
        int c;
        StringBuffer buff = new StringBuffer();
        try {
            fr = new FileReader(file);
            while ((c = fr.read()) != -1) {
                buff.append((char) c);
            }
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        matrize = buff.toString();
        String [] arrayMatrize = new String[breite*hoehe];
        arrayMatrize = matrize.split("\\s*[|]+");
        int zaeler = 0;
        for (int i = 0; i < this.breite; i++) {
            for (int j = 0; j < this.hoehe; j++) {
                    this.matrix[i][j] = Boolean.parseBoolean(arrayMatrize[zaeler+j]);
            }
            zaeler += 10;
        }
    }

    private int countNeighbours(Point2D point) {
        int x = (int) point.getX();
        int y = (int) point.getY();
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int curX = x + i;
                int curY = y + j;

                if(curX == -1) curX = matrix.length -1;
                if(curX >= matrix.length) curX = 0;
                if(curY == -1) curY = matrix[curX].length -1;
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
        for (int i = this.matrix.length-1; i > -1; i--) {
            for (int y = 0; y < this.matrix[i].length; y++) {
                returnValue += "|" +  (matrix[i][y] ? "X" : "O");
            }
            returnValue += "|\n";
        }
        return returnValue;
    }

    public String toString(int a ) {
        String returnValue = "";
        for (int i = 0; i < this.matrix.length; i++) {
            for (int y = 0; y < this.matrix[i].length; y++) {
                returnValue += this.matrix[i][y] + "|";
            }
        }
        return returnValue;
    }
}
