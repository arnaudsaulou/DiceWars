package diceWars.views;

import javax.swing.*;
import java.awt.*;

public class GameView extends AbstractView {

    //region Variables

    private JPanel gameViewPanel;
    private JPanel mapViewPanel;
    private JButton endRoundButton;
    private JTextArea actionsHistoryTextArea;
    private JPanel playersColor;
    private JLabel title_idPlayer;

    //endregion

    //region Constructor

    public GameView(MapView mapView) {
        this.setViewPanel(this.gameViewPanel);
        this.mapViewPanel.add(mapView.getDisplayableViewPanel(), BorderLayout.CENTER);
    }

    //endregion

    //region Utils

    /**
     * Disable all components of the gameViewPanel
     */
    public void freezeView() {
        this.setEnableRec(this.gameViewPanel, false);
    }

    /**
     * Enable all components of the gameViewPanel
     */
    public void unfreezeView() {
        this.setEnableRec(this.gameViewPanel, true);
    }

    /**
     * Enable or disable the container pass in parameters
     *
     * @param container The container to enable or not
     * @param enable    The action, true : enable , false : disable
     */
    private void setEnableRec(Component container, boolean enable) {

        container.setEnabled(enable);

        //Try to get children components
        try {
            Component[] components = ((Container) container).getComponents();
            for (Component component : components) {
                setEnableRec(component, enable);
            }
        } catch (ClassCastException ignored) {

        }
    }

    //endregion

    //region Getter

    public JButton getEndRoundButton() {
        return this.endRoundButton;
    }

    public JPanel getPlayersColor() {
        return this.playersColor;
    }

    public JTextArea getActionsHistoryTextArea() {
        return actionsHistoryTextArea;
    }

    public JLabel getTitle_idPlayer() {
        return title_idPlayer;
    }

    //endregion
}
