package diceWars.views;

import diceWars.interfaces.Displayable;

import javax.swing.*;

/**
 * Mother class of all views
 */
public abstract class AbstractView implements Displayable {

    //region Variables

    private JPanel viewPanel;

    //endregion

    //region Constructor

    public AbstractView() {
    }

    //endregion

    //region Setter

    public void setViewPanel(JPanel viewPanel) {
        this.viewPanel = viewPanel;
    }

    //endregion

    //region Override

    @Override
    public JPanel getDisplayableViewPanel() {
        return this.viewPanel;
    }

    //endregion
}
