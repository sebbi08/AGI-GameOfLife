package agi.gameoflife;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sebastian.mahr on 08.10.16.
 */
public class UserInteraction {


    private BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
    private int width = 0;
    private int height = 0;
    private boolean random;
    private Area gameArea;
    private List<Point2D> startSeeds = new ArrayList<Point2D>();
    boolean singleStepped;
    boolean isRunning = true;

    public void run() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_SPACE){
                isRunning = !isRunning;
            }
            return false;
        });

        width = readNumberWithValidation("Wie breit soll das Spielfeld sein ?");
        height = readNumberWithValidation("Wie Hoch soll das Spielfeld sein ?");
        System.out.println("Willst du das Spielfeld randomisiert () oder selbst befüllen (X)");

        random = true;


        gameArea = new GameArea(width,height,random, startSeeds);
        System.out.println("Möchtest du den Fortschritt single stepped (X) oder per play/stop (leertaste) beobachten?");
        singleStepped = false;
        while(isRunning) {
            gameArea.performIteration();
            String gameAreaString = gameArea.toString();
            System.out.println(gameAreaString);
        }
    }

    private int readNumberWithValidation(String text){
        int inputNumber = 0;
        String inputString = "";
        boolean isInValide = true;
        System.out.println(text);
        while (isInValide){
            try {
                inputString = sc.readLine();
                inputNumber = Integer.parseInt(inputString);
                inputNumber = Math.abs(inputNumber);
                isInValide = false;
            }catch (NumberFormatException ex){
                System.out.println("Bitte gebe eine zahl ein");
                isInValide = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputNumber;
    }
}
