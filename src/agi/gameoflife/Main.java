package agi.gameoflife;

import agi.gameoflife.gui.GameGui;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GameGui ex = new GameGui();
            ex.setVisible(true);
        });
    }

}
