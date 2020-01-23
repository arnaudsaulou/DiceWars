package diceWars.controllers;

import diceWars.interfaces.Displayable;
import diceWars.models.*;
import diceWars.views.ApplicationView;
import diceWars.views.GameView;
import diceWars.views.MapView;
import diceWars.views.RankingView;

import java.util.Comparator;

public class ApplicationController {

    private final ApplicationView applicationView;
    private final GameController gameController;
    private final MapController mapController;
    private final RankingController rankingController;
    private final String nbJoueur;

    private Partie partie;

    public ApplicationController(String nbJoueur) {
        this.nbJoueur = nbJoueur;

        this.applicationView = new ApplicationView("DiceWars");

        try {
            this.partie = new Partie(this.nbJoueur);
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            System.exit(152);
        }

        this.mapController = new MapController(
                this.partie.getJeux().getCarte(),
                new MapView(this.partie.getJeux().getCarte())
        );

        this.gameController = new GameController(
                this.partie.getJeux(),
                new GameView((MapView) this.mapController.getView())
        );

        this.rankingController = new RankingController(
                new Ranking(new Comparator<Joueur>() {
                    @Override
                    public int compare(Joueur joueur, Joueur otherJoueur) {
                        return joueur.getNbTerritoiresOwned() - otherJoueur.getNbTerritoiresOwned();
                    }
                }, this.partie.getJoueurs()),
                new RankingView()
        );
    }


    public void resetPartie() {
        this.mapController.reset();
        this.gameController.reset();
        this.mapController.updateMap();
    }

    public void lunchPartie(Partie.MODE mode) {

        if (mode.equals(Partie.MODE.SOLO)) {
            this.changeView(this.gameController.getView());
        } else {
            //TODO
        }
        this.partie.lunchPartie(mode);


    }

    public void changeView(Displayable displayable) {
        this.applicationView.changeContentPane(displayable.getDisplayableViewPanel());
    }

    public void quit() {
        System.exit(1000);
    }

    public GameController getGameController() {
        return gameController;
    }

    public MapController getMapController() {
        return mapController;
    }

    public RankingController getRankingController() {
        return rankingController;
    }
}
