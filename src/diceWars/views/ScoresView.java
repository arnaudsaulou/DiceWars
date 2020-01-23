package diceWars.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoresView extends JDialog {

    private static final int DISPLAY_TIME = 3000;
    private static final String ATTACKER_STRING = "Attaquant :";
    private static final String DEFENDER_STRING = "Defenseur :";

    public ScoresView(int[] scores) {

        // Make sure the pop-up isn't resizable
        this.setResizable(false);
        this.setUndecorated(true);

        new Timer(DISPLAY_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        }).start();

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.setBorder(BorderFactory.createBevelBorder(0));

        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.LINE_AXIS));

        // Create the components:

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
        topPanel.setPreferredSize(new Dimension(500, 25));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(500, 25));

        // Add all of our components to the panel:
        xPanel.add(attackerLabel);
        xPanel.add(attackerScore);
        xPanel.add(divider);
        xPanel.add(defenderLabel);
        xPanel.add(defenderScore);
        //xPanel.setVisible(true);

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

}
