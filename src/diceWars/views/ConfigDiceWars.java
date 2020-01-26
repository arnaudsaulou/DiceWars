package diceWars.views;

import diceWars.DiceWars;
import diceWars.models.Partie;

import javax.swing.*;
import java.awt.*;

import static diceWars.models.Partie.COLOR;

public class ConfigDiceWars extends JDialog {

    private int nbJoueur;

    public ConfigDiceWars() {
        this.setSize(new Dimension(600, 300));
        this.setLocation(
                (DiceWars.applicationController.getApplicationView().getWidth() / 2) - 300,
                (DiceWars.applicationController.getApplicationView().getHeight() / 2) - 25);
        this.setTitle("Configuration Dicewars");
        this.setUndecorated(true);
        this.setModal(true);

        JPanel configDiceWarsPanel = this.constructConfigDiceWarsPanel();

        this.getContentPane().add(configDiceWarsPanel);
        this.setVisible(true);
    }

    private JPanel constructConfigDiceWarsPanel() {

        JPanel constructedPanel = new JPanel();

        constructedPanel.setLayout(new BorderLayout());

        JLabel jLabelTitre = new JLabel("Configuration");
        jLabelTitre.setFont(new Font("Arial Black", Font.BOLD, 20));
        jLabelTitre.setHorizontalAlignment(JLabel.CENTER);
        jLabelTitre.setPreferredSize(new Dimension(400, 50));

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
}
