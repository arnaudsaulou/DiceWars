package diceWars.views;

import javax.swing.*;
import java.awt.*;

public class GameView extends AbstractView {

    private JPanel gameViewPanel;
    private JPanel mapViewPanel;
    private JButton endRoundButton;
    private JTextArea actionsHistoryTextArea;
    private JPanel playersColor;
    private JLabel title_idPlayer;

    public GameView(MapView mapView) {
        this.setViewPanel(this.gameViewPanel);

        this.mapViewPanel.add(mapView.getDisplayableViewPanel(), BorderLayout.CENTER);
    }

    public void freezeView() {
        this.setEnableRec(this.gameViewPanel, false);
    }

    public void unfreezeView() {
        this.setEnableRec(this.gameViewPanel, true);
    }

    private void setEnableRec(Component container, boolean enable) {
        container.setEnabled(enable);

        try {
            Component[] components = ((Container) container).getComponents();
            for (Component component : components) {
                setEnableRec(component, enable);
            }
        } catch (ClassCastException ignored) {

        }
    }

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
}
