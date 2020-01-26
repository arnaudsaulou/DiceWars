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


    public ApplicationController() {
        this.applicationView = new ApplicationView();
        this.menuController = new MenuController(null, new MenuView());
    }

    private void setUpPartie(int nbPlayer, Partie.MODE mode) throws IllegalStateException {

        Partie partie = new Partie(nbPlayer, mode);

        this.mapController = new MapController(
                partie.getJeux().getCarte(),
                new MapView(partie.getJeux().getCarte())
        );

        this.gameController = new GameController(
                partie.getJeux(),
                new GameView((MapView) this.mapController.getView())
        );

        this.rankingController = new RankingController(
                new Ranking(new Comparator<>() {
                    @Override
                    public int compare(Joueur joueur, Joueur otherJoueur) {
                        return joueur.getNbTerritoiresOwned() - otherJoueur.getNbTerritoiresOwned();
                    }
                }, partie.getJoueurs()),
                new RankingView()
        );
    }

    public void lunchPartie(Partie.MODE mode, int nbPlayer) throws IllegalStateException {
        this.setUpPartie(nbPlayer, mode);
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
