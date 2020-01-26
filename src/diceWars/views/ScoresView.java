package diceWars.views;

import javax.swing.*;
import java.awt.*;

public class ScoresView extends JDialog {

    //region Constantes

    private static final int DISPLAY_TIME = 500;
    private static final String ATTACKER_STRING = "Attaquant :";
    private static final String DEFENDER_STRING = "Defenseur :";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 25;

    //endregion

    //region Constructor

    public ScoresView(int[] scores) {

        // Make sure the pop-up isn't resizable
        this.setResizable(false);
        this.setUndecorated(true);

        //Start timer to dispose the dialog after DISPLAY_TIME
        new Timer(DISPLAY_TIME, actionEvent -> dispose()).start();

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.setBorder(BorderFactory.createBevelBorder(0));

        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.LINE_AXIS));

        // Create components
        Font font = new Font("ARIAL", Font.BOLD, 30);

        JLabel attackerLabel = new JLabel(ATTACKER_STRING);
        attackerLabel.setFont(font);
        attackerLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 10));

        JLabel attackerScore = new JLabel(String.valueOf(scores[0]));
        attackerScore.setFont(font);
        attackerScore.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel divider = new JLabel("/");
        divider.setFont(font);
        divider.setBorder(BorderFactory.createEmptyBorder(0, 35, 0, 35));

        JLabel defenderLabel = new JLabel(DEFENDER_STRING);
        defenderLabel.setFont(font);
        defenderLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JLabel defenderScore = new JLabel(String.valueOf(scores[1]));
        defenderScore.setFont(font);
        defenderScore.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 20));

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Add all of our components to the panel:
        xPanel.add(attackerLabel);
        xPanel.add(attackerScore);
        xPanel.add(divider);
        xPanel.add(defenderLabel);
        xPanel.add(defenderScore);

        // Finally add our panel that contains all our components:
        borderPanel.add(topPanel, BorderLayout.NORTH);
        borderPanel.add(xPanel, BorderLayout.CENTER);
        borderPanel.add(bottomPanel, BorderLayout.SOUTH);
        this.add(borderPanel);

        // Prep the popup to be shown
        this.pack();
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    //endregion

}
