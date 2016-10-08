package agi.gameoflife.gui;

import agi.gameoflife.GameArea;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Created by sebastian.mahr on 08.10.16.
 */
public class GameGui extends JFrame {
    private JButton startButton;
    private JButton stopButton;
    private JButton singleStepButton;
    private JPanel main;
    private JTextArea textArea1;
    private JButton reload;
    private JPanel gamePanel;
    private GameArea gameArea;
    public Thread runner;
    public AutoRunner autoRunnter;
    private FieldUpdater updater;

    public GameGui() {
        setTitle("Game of Life");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(main);

        createOrUpdateGameArea(10, 10, true, new ArrayList<>());
        updater = new FieldUpdater(gameArea,gamePanel);
        autoRunnter = new AutoRunner(gameArea, updater);

        updateGrid();

        singleStepButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.performIteration();
                updateGrid();
            }
        });

        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(runner != null && runner.isAlive()) return;
                runner = new Thread(autoRunnter);
                runner.start();
            }
        });

        stopButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runner.interrupt();
                updateGrid();
            }
        });

        reload.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                NewGameDialog dialog = new NewGameDialog();
                dialog.setVisible(true);
                createOrUpdateGameArea(dialog.getNewWidth(),dialog.getNewHeight(),dialog.isRandom(),dialog.getList());
                updateGrid();
            }
        });

    }

    private void updateGrid(){
        updater.update();
    }

    private void createOrUpdateGameArea(int breite, int hoehe, boolean random, List<Point2D> liste){
        if(gameArea == null){
            gameArea = new GameArea(breite,hoehe,random,liste);
        }else {
            gameArea.create(breite,hoehe,random,liste);
        }
    }

    class FieldUpdater {

        private final GameArea gameArea;
        private final JPanel gamePanel;

        public FieldUpdater(GameArea gameArea, JPanel gamePanel) {

            this.gameArea = gameArea;
            this.gamePanel = gamePanel;
        }


        public void update() {
            gamePanel.removeAll();
            int height = gameArea.getMatix().length;
            int width = gameArea.getMatix()[0].length;
            GridLayout layout = new GridLayout(height,width);
            gamePanel.setLayout(layout);
            boolean[][] matrix = gameArea.getMatix();
            for (int i = 0; i < matrix.length; i++) {
                for (int y = 0; y < matrix[i].length; y++) {
                    JPanel newPanel = new JPanel();
                    Color color;
                    if(matrix[i][y]){
                        color = Color.green;
                    }else {
                        color = Color.white;
                    }
                    newPanel.setBackground(color);
                    int finalI = i;
                    int finalY = y;
                    newPanel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            if(e.getButton() == MouseEvent.BUTTON1){
                                if(matrix[finalI][finalY]){
                                    gameArea.unSetPoint(new Point(finalI,finalY));
                                    newPanel.setBackground(Color.white);
                                }else {
                                    gameArea.setPoint(new Point(finalI,finalY));
                                    newPanel.setBackground(Color.green);
                                }
                            }
                        }
                    });
                    newPanel.setBorder(new LineBorder(Color.BLACK));
                    gamePanel.add(newPanel);
                }
            }
            gamePanel.updateUI();
        }
    }

    class AutoRunner implements Runnable {

        private final GameArea gameArea;
        private final FieldUpdater fieldUpdater;

        public AutoRunner(GameArea gameArea, FieldUpdater fieldUpdater) {

            this.gameArea = gameArea;
            this.fieldUpdater = fieldUpdater;
        }

        @Override
        public void run() {
            while (true) {
                if(Thread.interrupted()) return;
                gameArea.performIteration();
                fieldUpdater.update();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
