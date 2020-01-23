package diceWars.controllers;

import diceWars.interfaces.Displayable;
import diceWars.models.Joueur;
import diceWars.models.Partie;
import diceWars.models.Ranking;
import diceWars.views.*;

import java.util.Comparator;

public class ApplicationController {

    private final ApplicationView applicationView;
    private final MenuController menuController;
    private GameController gameController;
    private MapController mapController;
    private RankingController rankingController;

    private Partie partie;

    public ApplicationController() {
        this.applicationView = new ApplicationView();
        this.menuController = new MenuController(null, new MenuView());
    }

    private void setUpPartie(int nbPlayer) throws IllegalStateException {

        this.partie = new Partie(nbPlayer);

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

    public void lunchPartie(Partie.MODE mode, int nbPlayer) throws IllegalStateException {
        if (nbPlayer == -1) {
            this.setUpPartie(this.partie.getJoueurs().size());
        } else {
            this.setUpPartie(nbPlayer);
        }
        this.changeView(this.gameController.getView());
    }

    public void changeView(Displayable displayable) {
        this.applicationView.changeContentPane(displayable.getDisplayableViewPanel());
    }

    public void quit() {
        System.exit(1000);
    }

    public GameController getGameController() {
        return this.gameController;
    }

    public MapController getMapController() {
        return this.mapController;
    }

    public RankingController getRankingController() {
        return this.rankingController;
    }

    public ApplicationView getApplicationView() {
        return this.applicationView;
    }

    public MenuController getMenuController() {
        return menuController;
    }
}
