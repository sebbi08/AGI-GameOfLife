package agi.gameoflife.gui;

import agi.gameoflife.GameArea;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Created by sebastian.mahr on 08.10.16.
 */
public class GameGui extends JFrame {
    private JButton startButton;
    private JButton stopButton;
    private JButton singleStepButton;
    private JPanel main;
    private JTextArea textArea1;
    private JButton newButton;
    private GameArea gameArea;
    public Thread runner;
    public AutoRunner autoRunnter;

    public GameGui() {
        setTitle("Simple example");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(main);

        gameArea = new GameArea(10, 10, true, new ArrayList<>());
        autoRunnter = new AutoRunner(gameArea, textArea1);

        textArea1.setText(gameArea.toString());

        singleStepButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.performIteration();
                textArea1.setText(gameArea.toString());
            }
        });

        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runner = new Thread(autoRunnter);
                runner.start();
            }
        });

        stopButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runner.stop();
                textArea1.setText(gameArea.toString());
            }
        });

        newButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewGameDialog gameDialog = new NewGameDialog(this);
                gameDialog.pack();
                gameDialog.setVisible(true);
            }
        });

    }

    class AutoRunner implements Runnable {

        private final GameArea gameArea;
        private final JTextArea textArea1;
        private boolean isRunning = false;

        public AutoRunner(GameArea gameArea, JTextArea textArea1) {

            this.gameArea = gameArea;
            this.textArea1 = textArea1;
        }

        @Override
        public void run() {
            while (true) {
                gameArea.performIteration();
                textArea1.setText(gameArea.toString());
            }
        }
    }
}
