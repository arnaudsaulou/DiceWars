package diceWars.controllers;

import diceWars.interfaces.Displayable;
import diceWars.models.Joueur;
import diceWars.models.Partie;
import diceWars.models.Ranking;
import diceWars.views.*;

import java.util.Comparator;

/**
 * Controller of all controllers
 */
public class ApplicationController {

    //region Variables

    private final ApplicationView applicationView;
    private final MenuController menuController;
    private GameController gameController;
    private MapController mapController;
    private RankingController rankingController;

    //endregion

    //region Constructor

    public ApplicationController() {
        this.applicationView = new ApplicationView();
        this.menuController = new MenuController(null, new MenuView());
    }

    //endregion

    //region Utils

    /**
     * Setting up a new partie with associated controllers
     *
     * @param nbPlayer The number of player who wants to play
     * @param mode     The chosen mode (SOLO or MULTI)
     */
    private void setUpPartie(int nbPlayer, Partie.MODE mode) {

        //Create the new partie
        Partie partie = new Partie(nbPlayer, mode);

        //Setting up a corresponding MapController
        this.mapController = new MapController(
                partie.getJeux().getCarte(),
                new MapView(partie.getJeux().getCarte())
        );

        //Setting up a corresponding GameController
        this.gameController = new GameController(
                partie.getJeux(),
                new GameView((MapView) this.mapController.getView())
        );

        //Setting up a corresponding RankingController
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

    /**
     * Setting up a new partie and display it
     *
     * @param nbPlayer The number of player who wants to play
     * @param mode     The chosen mode (SOLO or MULTI)
     */
    public void lunchPartie(Partie.MODE mode, int nbPlayer) {
        this.setUpPartie(nbPlayer, mode);
        this.changeView(this.gameController.getView());
    }

    /**
     * Change content pane by the displayable pass in parameter
     *
     * @param displayable The displayable to display
     */
    public void changeView(Displayable displayable) {
        this.applicationView.changeContentPane(displayable.getDisplayableViewPanel());
    }

    /**
     * To quit the game
     */
    public void quit() {
        System.exit(1000);
    }

    //endregion

    //region Getter

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

    //endregion
}
