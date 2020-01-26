package diceWars.controllers;

import diceWars.DiceWars;
import diceWars.models.AbstractModel;
import diceWars.models.Coordinates;
import diceWars.models.IA;
import diceWars.models.Jeux;
import diceWars.models.exceptions.BelongingTerritoryException;
import diceWars.models.exceptions.NonNeighboringTerritoryException;
import diceWars.models.exceptions.NotBelongingTerritoryException;
import diceWars.models.exceptions.OneDiceTerritoryException;
import diceWars.views.AbstractView;
import diceWars.views.DialogError;
import diceWars.views.GameView;
import diceWars.views.ScoresView;

import javax.swing.*;

/**
 * Controller of the model Jeux and view GameView
 */
public class GameController extends AbstractController {

    //region Contantes

    private static final String WINNER_MSG = "Joueur %d à gagné ! \n";
    private static final int PLAYER_ID_OFFSET = 1;
    private static final int THINKING_TIME_IA = 1000;

    //endregion

    //region Variables

    private final Jeux jeux;
    private final GameView gameView;

    //endregion

    //region Constructor

    public GameController(AbstractModel jeux, AbstractView gameView) {
        super(jeux, gameView);
        this.jeux = (Jeux) jeux;
        this.gameView = (GameView) gameView;
        this.gameView.getPlayersColor().setOpaque(true);
        this.displayTitle();

        //Register a listener on the "Fin du tour" button
        this.gameView.getEndRoundButton().addActionListener(actionEvent -> this.endRoundProcedure());
    }

    //endregion

    //region Utils

    /**
     * Display players infos on the title (ID + Color)
     */
    private void displayTitle() {

        //Display player's color
        this.gameView.getPlayersColor().setBackground(
                GameController.this.jeux.getNextJoueur().getColorPlayer()
        );

        //Display player's id
        this.gameView.getTitle_idPlayer().setText(
                String.valueOf(GameController.this.jeux.getNextJoueur().getId() + PLAYER_ID_OFFSET)
        );
    }

    /**
     * Procedure to end a round
     */
    private void endRoundProcedure() {
        GameController.this.jeux.endRound();

        //If the game is not over
        if (GameController.this.jeux.isGameNotOver()) {

            this.displayTitle();
            DiceWars.applicationController.getMapController().updateMap();

            //If the it's IA's turn to play
            if (this.jeux.getNextJoueur() instanceof IA) {
                IA ia = (IA) this.jeux.getNextJoueur();
                this.letsIaPlay(ia);
            }

        }
        //If the game is over
        else {
            //Compute ranking and display the ranking view
            DiceWars.applicationController.getRankingController().updateRanking();
            DiceWars.applicationController.changeView(
                    DiceWars.applicationController.getRankingController().getView()
            );
        }
    }

    /**
     * Get attacking and attacked territories, if coordinates valid attack, get result, save in history and display it
     */
    public void askNeededCoordinatesToAttack() {

        try {
            Coordinates coordinatesAttacking = this.jeux.getAttackingTerritory();
            Coordinates coordinatesAttacked = this.jeux.getAttackedTerritory();

            //Check if chooser coordinates are valid
            if (this.jeux.getCarte().isCoordinatesValid(coordinatesAttacking) &&
                    this.jeux.getCarte().isCoordinatesValid(coordinatesAttacked)) {
                this.executeAnAttack(coordinatesAttacking, coordinatesAttacked);
            }

            //Should not append with GUI
            else {
                new DialogError("Coordonnée invalide");
            }
        } catch (BelongingTerritoryException | OneDiceTerritoryException | NotBelongingTerritoryException |
                NonNeighboringTerritoryException e) {
            //Display a popup with the rule violated
            new DialogError(e.getMessage());
        }

    }

    /**
     * Entire procedure to proceed an attack
     *
     * @param coordinatesAttacking The territory attacking
     * @param coordinatesAttacked  The territory attacked
     * @throws BelongingTerritoryException      BelongingTerritoryException
     * @throws NonNeighboringTerritoryException NonNeighboringTerritoryException
     * @throws OneDiceTerritoryException        NonNeighboringTerritoryException
     * @throws NotBelongingTerritoryException   NotBelongingTerritoryException
     */
    private void executeAnAttack(Coordinates coordinatesAttacking, Coordinates coordinatesAttacked)
            throws BelongingTerritoryException, NonNeighboringTerritoryException, OneDiceTerritoryException,
            NotBelongingTerritoryException {

        //Attack and get result scores
        int[] scores = this.jeux.getNextJoueur().attaquer(coordinatesAttacking, coordinatesAttacked);

        //Display scores
        new ScoresView(scores);

        //Register action on history
        this.gameView.getActionsHistoryTextArea().insert(
                String.format(WINNER_MSG, this.jeux.getLastWinner().getId() + PLAYER_ID_OFFSET), 0
        );

    }

    /**
     * Save the coordinates pass in parameter, first time the procedure is call the attacking territory is set,
     * the second time, the attacked territory is set. If the attacked territory is set => Attack
     *
     * @param coordinates The selected territory
     */
    public void registerCoordinates(Coordinates coordinates) {
        if (this.jeux.getAttackingTerritory() == null) {
            this.jeux.setAttackingTerritory(coordinates);
        } else {
            this.jeux.setAttackedTerritory(coordinates);

            //Proceed an attack and update the map
            this.askNeededCoordinatesToAttack();
            DiceWars.applicationController.getMapController().updateMap();

            //Prepare for next acquisition
            this.jeux.setAttackedTerritory(null);
            this.jeux.setAttackingTerritory(null);
        }
    }

    //region IA

    /**
     * Simulate the registerCoordinates => Save an attacking and attacked territories
     *
     * @param ia The IA (Player)
     */
    private void chooseMoveIa(IA ia) {
        this.jeux.setAttackingTerritory(ia.chooseAttackingTerritory(this.jeux.getCarte().getTerritoiresOfPlayer(ia)));
        this.jeux.setAttackedTerritory(ia.chooseTarget(this.jeux.getAttackingTerritory()));
    }

    /**
     * @param ia The IA (Player)
     */
    public void letsIaPlay(IA ia) {

        //Disable interactions with the view
        this.gameView.freezeView();

        //Simulate a thinking time
        Timer timer = new Timer(THINKING_TIME_IA, arg0 -> {

            //Simulate the registerCoordinates
            this.chooseMoveIa(ia);

            //If the IA still wants to attack
            if (this.jeux.getAttackedTerritory() != null) {

                //Attack and update the view
                this.askNeededCoordinatesToAttack();
                DiceWars.applicationController.getMapController().updateMap();

                //Prepare for next acquisition
                this.jeux.setAttackedTerritory(null);
                this.jeux.setAttackingTerritory(null);

                this.letsIaPlay(ia);
            } else {
                this.jeux.setAttackedTerritory(null);
                this.jeux.setAttackingTerritory(null);

                //End round and re-enable interactions with the view
                this.endRoundProcedure();
                this.gameView.unfreezeView();
            }

        });

        //Start the thinking time
        timer.setRepeats(false);
        timer.start();
    }

    //endregion

    //endregion

}
