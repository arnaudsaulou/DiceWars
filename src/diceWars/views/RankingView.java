package diceWars.views;

import javax.swing.*;

public class RankingView extends AbstractView {
    private JPanel resultPanel;
    private JButton quit;
    private JPanel centerPanel;
    private JLabel winnerName;

    public RankingView() {
        this.setViewPanel(this.resultPanel);
    }

    //region Getter

    public JButton getQuit() {
        return this.quit;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JLabel getWinnerName() {
        return winnerName;
    }

    //endregion
}
