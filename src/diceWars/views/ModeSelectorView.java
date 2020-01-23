package diceWars.views;

import diceWars.controllers.ApplicationController;
import diceWars.interfaces.Displayable;
import diceWars.models.Partie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectorView implements Displayable {

    private JPanel modeSelectorViewPanel;
    private JButton soloButton;
    private JButton multiButton;

    private ApplicationController applicationController;

    public ModeSelectorView(ApplicationController applicationController) {
        this.applicationController = applicationController;

        this.soloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                applicationController.lunchPartie(Partie.MODE.SOLO);
            }
        });

        this.multiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                applicationController.lunchPartie(Partie.MODE.MULTI);
            }
        });

    }

    @Override
    public JPanel getDisplayableViewPanel() {
        return this.modeSelectorViewPanel;
    }
}
