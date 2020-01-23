package diceWars.views;

import javax.swing.*;

public class RankingView extends AbstractView {
    private JPanel resultPanel;
    private JButton restart;
    private JButton quit;
    private JPanel centerPanel;


    public RankingView() {
        this.setViewPanel(this.resultPanel);
    }

    //region Getter

    public JButton getRestart() {
        return this.restart;
    }

    public JButton getQuit() {
        return this.quit;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    //endregion
}
