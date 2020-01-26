package diceWars.models;

import java.util.ArrayList;
import java.util.HashSet;

public class IA extends Joueur {

    private ArrayList<Coordinates> territoriesOwned;

    public IA() {
        super();
    }

    public Coordinates chooseAttackingTerritory(ArrayList<Coordinates> territoriesOwned) {

        this.territoriesOwned = territoriesOwned;

        int retry = 0;
        Coordinates attackingTerritory;
        HashSet<Coordinates> foreignNeighbors;

        this.territoriesOwned.sort((coordinates1, coordinates2) -> jeux.getCarte().getNbDiceOnTerritoire(coordinates2) - jeux.getCarte().getNbDiceOnTerritoire(coordinates1));

        do {
            attackingTerritory = this.territoriesOwned.get(retry);
            foreignNeighbors = this.jeux.getCarte().getNeighborsPlayable(attackingTerritory);
            foreignNeighbors.removeAll(this.territoriesOwned);
            retry++;
        } while (foreignNeighbors.isEmpty() && retry < this.territoriesOwned.size());

        return attackingTerritory;
    }

    public Coordinates chooseTarget(Coordinates attackingTerritory) {
        Coordinates targetTerritory = null;

        int minNbDice = this.jeux.getCarte().getNbDiceOnTerritoire(attackingTerritory);
        int nbDiceOnTerritory;

        if (attackingTerritory != null) {
            HashSet<Coordinates> potentialTarget = this.jeux.getCarte().getNeighborsPlayable(attackingTerritory);

            if (potentialTarget != null) {
                potentialTarget.removeAll(this.territoriesOwned);

                for (Coordinates chosenOne : potentialTarget) {
                    nbDiceOnTerritory = this.jeux.getCarte().getNbDiceOnTerritoire(chosenOne);
                    if (nbDiceOnTerritory < minNbDice) {
                        minNbDice = nbDiceOnTerritory;
                        targetTerritory = chosenOne;
                    }
                }
            }
        }

        return targetTerritory;
    }

}
