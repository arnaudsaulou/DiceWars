package diceWars.controllers;

import diceWars.DiceWars;
import diceWars.models.AbstractModel;
import diceWars.models.Jeux;
import diceWars.models.Ranking;
import diceWars.views.AbstractView;
import diceWars.views.RankingView;

import javax.swing.*;
import java.awt.*;

/**
 * Controller of the model Ranking and RankingView view
 */
public class RankingController extends AbstractController {

    //region Variables

    private final Ranking ranking;
    private final RankingView rankingView;

    //endregion

    //region Constructor

    public RankingController(AbstractModel ranking, AbstractView rankingView) {
        super(ranking, rankingView);

        this.ranking = (Ranking) ranking;
        this.rankingView = (RankingView) rankingView;

        //Register listener on the quit button
        this.rankingView.getQuit().addActionListener(actionEvent -> DiceWars.applicationController.quit());
    }

    //endregion

    //region Utils

    /**
     * Construct 2D array representing the ranking ready to inject in a JTable
     *
     * @return The 2D array representing the ranking
     */
    private String[][] constructRankinObject() {
        String[][] ranking = new String[this.ranking.getJoueurList().size()][2];

        for (int i = 0; i < this.ranking.getJoueurList().size(); i++) {
            ranking[i][0] = "Joueur " + (this.ranking.getJoueurList().get(i).getId() + 1);
            ranking[i][1] = String.valueOf(this.ranking.getJoueurList().get(i).getNbTerritoiresOwned());
        }

        return ranking;
    }

    /**
     * Construct rankingView with a JScrollPane panel and a JTable
     */
    public void updateRanking() {

        JScrollPane scrollPane = new JScrollPane();

        String[] columnNames = {"Joueur", "Nombre de territoires"};

        //Fill table with the 2D array of constructRankinObject
        JTable jTable = new JTable(this.constructRankinObject(), columnNames);
        jTable.setDefaultEditor(Object.class, null);
        jTable.setRowHeight(50);
        jTable.setFont(new Font("ARIAL", Font.PLAIN, 20));
        scrollPane.setViewportView(jTable);

        //Insert the table
        this.rankingView.getCenterPanel().add(scrollPane, BorderLayout.CENTER);

        //Update name of the winner
        Jeux jeux = (Jeux) DiceWars.applicationController.getGameController().getModel();
        this.rankingView.getWinnerName().setText("Joueur " + (jeux.getLastWinner().getId() + 1));
    }

    //endregion
}
