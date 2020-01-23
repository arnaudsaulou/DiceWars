package diceWars.controllers;

import diceWars.DiceWars;
import diceWars.models.AbstractModel;
import diceWars.models.Joueur;
import diceWars.models.Partie;
import diceWars.models.Ranking;
import diceWars.views.AbstractView;
import diceWars.views.RankingView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingController extends AbstractController {

    private final Ranking ranking;
    private final RankingView rankingView;

    public RankingController(AbstractModel ranking, AbstractView rankingView) {
        super(ranking, rankingView);

        this.ranking = (Ranking) ranking;
        this.rankingView = (RankingView) rankingView;

        String[] columnNames = {"Joueur", "Nombre de territoires"};

        JTable jTable = new JTable(this.constructRankinObject(), columnNames);
        jTable.setDefaultEditor(Object.class, null);
        jTable.setRowHeight(50);
        jTable.setFont(new Font("ARIAL", Font.PLAIN, 20));

        this.rankingView.getCenterPanel().add(jTable, BorderLayout.CENTER);

        this.rankingView.getQuit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DiceWars.applicationController.quit();
            }
        });

        this.rankingView.getRestart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DiceWars.applicationController.resetPartie();
                DiceWars.applicationController.lunchPartie(Partie.MODE.SOLO);
            }
        });
    }

    private String[][] constructRankinObject() {
        String[][] ranking = new String[this.ranking.getJoueurList().size()][2];

        for (int i = 0; i < this.ranking.getJoueurList().size(); i++) {
            ranking[i][0] = "Joueur " + this.ranking.getJoueurList().get(i).getId();
            ranking[i][1] = String.valueOf(this.ranking.getJoueurList().get(i).getNbTerritoiresOwned());
        }

        return ranking;

    }

}
