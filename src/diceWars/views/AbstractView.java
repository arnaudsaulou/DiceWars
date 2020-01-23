package diceWars.views;

import diceWars.interfaces.Displayable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AbstractView implements Displayable {

    private JPanel viewPanel;

    public AbstractView() {
    }

    public void setViewPanel(JPanel viewPanel) {
        this.viewPanel = viewPanel;
    }

    @Override
    public JPanel getDisplayableViewPanel() {
        return this.viewPanel;
    }
}
