package agi.gameoflife.gui;

import agi.gameoflife.GameArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

public class NewGameDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton userInputRadioButton;
    private JRadioButton randomizedRadioButton;
    private JSpinner hightSpinner;
    private JSpinner widthSpinner;
    private JTextArea textArea1;
    private GameArea gameArea;

    public NewGameDialog(GameArea gameArea) {
        this.gameArea = gameArea;
        setSize(500,500);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        ButtonGroup group = new ButtonGroup();
        group.add(userInputRadioButton);
        group.add(randomizedRadioButton);

        SpinnerNumberModel model = new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1);
        SpinnerNumberModel model2 = new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1);
        hightSpinner.setModel(model);
        widthSpinner.setModel(model2);

        userInputRadioButton.addActionListener(e -> {
            textArea1.setEnabled(true);
            textArea1.setText("x1,y1\nx2,y2");
        });

        randomizedRadioButton.addActionListener(e -> {
            textArea1.setEnabled(false);
            textArea1.setText("Disabled");
        });
    }

    public NewGameDialog(AbstractAction abstractAction) {

    }

    private void onOK() {
        if(randomizedRadioButton.isSelected()){
            gameArea.create((Integer) hightSpinner.getValue(),(Integer) widthSpinner.getValue(),true,new ArrayList<>());
        }else{
            String valuePairs = textArea1.getText();
            List<Point2D> list = new ArrayList<>();
            try {
                for (String s : valuePairs.split("\n")) {
                    String[] values = s.split(",");
                    int x = Math.abs(Integer.parseInt(values[1]));
                    int y = Math.abs(Integer.parseInt(values[0]));

                    if(x > (Integer) hightSpinner.getValue()){
                        JOptionPane.showMessageDialog(this, "Wertepaar : " + x + "," + y + " ist aserhalb der reichweite");
                        return;
                    }else if(y > (Integer) widthSpinner.getValue()){
                        JOptionPane.showMessageDialog(this, "Wertepaar : " + x + "," + y + " ist aserhalb der reichweite");
                        return;
                    }

                    x--;
                    y--;

                    list.add(new Point(x,y));
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Flasche formatirung der werte paare x,y mit einer leerzeile getrennt");
                return;
            }

            gameArea.create((Integer) hightSpinner.getValue(),(Integer) widthSpinner.getValue(),false,list);
        }
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
