package diceWars.views;

import diceWars.DiceWars;
import diceWars.models.Partie;

import javax.swing.*;
import java.awt.*;

import static diceWars.models.Partie.COLOR;

public class ConfigDiceWars extends JDialog {

    //region Constants

    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private static final int VERTICAL_OFFSET = 25;
    private static final int WIDTH_LABEL = 400;
    private static final int HEIGHT_LABEL = 50;

    //endregion

    //region Variables

    private int nbJoueur;

    //endregion

    //region Constructor

    public ConfigDiceWars() {
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setLocation(
                (DiceWars.applicationController.getApplicationView().getWidth() / 2) - HEIGHT,
                (DiceWars.applicationController.getApplicationView().getHeight() / 2) - VERTICAL_OFFSET);
        this.setTitle("Configuration Dicewars");
        this.setUndecorated(true);
        this.setModal(true);

        JPanel configDiceWarsPanel = this.constructConfigDiceWarsPanel();

        this.getContentPane().add(configDiceWarsPanel);
        this.setVisible(true);
    }

    //endregion

    //region Utils

    /**
     * Construct a JPanel to configure the number of player
     *
     * @return The JPanel ready to be displayed
     */
    private JPanel constructConfigDiceWarsPanel() {

        JPanel constructedPanel = new JPanel();

        constructedPanel.setLayout(new BorderLayout());

        JLabel jLabelTitre = new JLabel("Configuration");
        jLabelTitre.setFont(new Font("Arial Black", Font.BOLD, 20));
        jLabelTitre.setHorizontalAlignment(JLabel.CENTER);
        jLabelTitre.setPreferredSize(new Dimension(WIDTH_LABEL, HEIGHT_LABEL));

        JPanel jPanelOption = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        jPanelOption.setLayout(new GridBagLayout());

        gbc.gridx = 0;  //position sur la grille en x
        gbc.gridy = 0;  //position sur la grille en y
        JLabel jLabelNbJoueur = new JLabel("Nombre de joueur : ");
        jPanelOption.add(jLabelNbJoueur, gbc);

        gbc.gridx = 1;  //position sur la grille en x
        gbc.gridy = 0;  //position sur la grille en y
        JTextField jTextFieldNbJoueur = new JTextField(10);
        jTextFieldNbJoueur.setPreferredSize(new Dimension(30, 20));
        jPanelOption.add(jTextFieldNbJoueur, gbc);

        JLabel jLabelError = new JLabel();
        gbc.gridx = 1;  //position sur la grille en x
        gbc.gridy = 1;  //position sur la grille en y
        jPanelOption.add(jLabelError, gbc);

        JPanel jPanelButtons = new JPanel();

        JButton jButtonCancel = new JButton("ANNULER");
        jButtonCancel.addActionListener(e -> ConfigDiceWars.this.dispose());

        JButton jButtonValidate = new JButton("VALIDER");
        jButtonValidate.requestFocus();
        jButtonValidate.addActionListener(e -> {
            nbJoueur = Integer.parseInt(jTextFieldNbJoueur.getText());
            if (nbJoueur > 1 && nbJoueur <= COLOR.length) {
                try {
                    DiceWars.applicationController.lunchPartie(Partie.MODE.MULTI, nbJoueur);
                    ConfigDiceWars.this.dispose();
                } catch (IllegalStateException exception) {
                    jLabelError.setText(exception.getMessage());
                }

            } else {
                jLabelError.setText("Veuillez saisir un nombre de joueur supérieur à 1 et inférieur à " + (COLOR.length + 1));
            }
        });

        jPanelButtons.add(jButtonCancel);
        jPanelButtons.add(jButtonValidate);

        constructedPanel.add(jLabelTitre, BorderLayout.NORTH);
        constructedPanel.add(jPanelOption, BorderLayout.CENTER);
        constructedPanel.add(jPanelButtons, BorderLayout.SOUTH);

        return constructedPanel;
    }

    //endregion

}
