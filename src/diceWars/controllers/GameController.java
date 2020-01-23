package diceWars.controllers;

import diceWars.DiceWars;
import diceWars.models.AbstractModel;
import diceWars.models.Coordinates;
import diceWars.models.Jeux;
import diceWars.models.exceptions.BelongingTerritoryException;
import diceWars.models.exceptions.NonNeighboringTerritoryException;
import diceWars.models.exceptions.NotBelongingTerritoryException;
import diceWars.models.exceptions.OneDiceTerritoryException;
import diceWars.views.AbstractView;
import diceWars.views.DialogError;
import diceWars.views.GameView;
import diceWars.views.ScoresView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController extends AbstractController {

    private static final String WINNER_MSG = "Joueur %d à gagné ! \n";
    private static final int PLAYER_ID_OFFSET = 1;

    private final Jeux jeux;
    private final GameView gameView;

    public GameController(AbstractModel jeux, AbstractView gameView) {
        super(jeux, gameView);
        this.jeux = (Jeux) jeux;
        this.gameView = (GameView) gameView;

        this.gameView.getPlayersColor().setOpaque(true);
        this.gameView.getPlayersColor().setBackground(
                GameController.this.jeux.getNextJoueur().getColorPlayer()
        );

        this.gameView.getTitle_idPlayer().setText(
                String.valueOf(GameController.this.jeux.getNextJoueur().getId() + PLAYER_ID_OFFSET)
        );

        this.gameView.getEndRoundButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GameController.this.jeux.endRound();

                if (GameController.this.jeux.isGameNotOver()) {
                    GameController.this.gameView.getPlayersColor().setBackground(
                            GameController.this.jeux.getNextJoueur().getColorPlayer()
                    );
                    GameController.this.gameView.getTitle_idPlayer().setText(
                            String.valueOf(GameController.this.jeux.getNextJoueur().getId() + PLAYER_ID_OFFSET)
                    );
                    DiceWars.applicationController.getMapController().updateMap();
                } else {
                    DiceWars.applicationController.getRankingController().updateRanking();
                    DiceWars.applicationController.changeView(
                            DiceWars.applicationController.getRankingController().getView()
                    );
                }
            }
        });
    }

    private void askNeededCoordinatesToAttack() {

        try {

            Coordinates coordinatesAttacking = this.jeux.getAttackingTerritory();
            Coordinates coordinatesAttacked = this.jeux.getAttackedTerritory();
            int[] scores;

            if (this.jeux.getCarte().isCoordinatesValid(coordinatesAttacking) &&
                    this.jeux.getCarte().isCoordinatesValid(coordinatesAttacked)) {
                scores = this.jeux.getNextJoueur().attaquer(coordinatesAttacking, coordinatesAttacked);

                //Display scores
                new ScoresView(scores);

                this.gameView.getActionsHistoryTextArea().insert(
                        String.format(WINNER_MSG, this.jeux.getLastWinner().getId() + PLAYER_ID_OFFSET), 0
                );
            } else {

                //Should not append
                new DialogError("Coordonnée invalide");
            }
        } catch (BelongingTerritoryException |
                OneDiceTerritoryException |
                NotBelongingTerritoryException |
                NonNeighboringTerritoryException e) {
            new DialogError(e.getMessage());
        }
    }

    public void registerCoordinates(Coordinates coordinates) {
        if (this.jeux.getAttackingTerritory() == null) {
            this.jeux.setAttackingTerritory(coordinates);
        } else {
            this.jeux.setAttackedTerritory(coordinates);
            this.askNeededCoordinatesToAttack();
            DiceWars.applicationController.getMapController().updateMap();

            //Prepare for next acquisition
            this.jeux.setAttackingTerritory(null);
        }
    }

    public void reset(){
        this.jeux.reset();
    }
}
