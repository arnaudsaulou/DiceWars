package diceWars.models;

import java.util.ArrayList;
import java.util.HashSet;

public class IA extends Joueur {

    //region Variables

    private ArrayList<Coordinates> territoriesOwned;

    //endregion

    //region Constructor

    public IA() {
        super();
    }

    //endregion

    //region Utils

    /**
     * Choose a attacking territory from the list of territories owned
     *
     * @param territoriesOwned The list of territories owned
     * @return The attacking territory
     */
    public Coordinates chooseAttackingTerritory(ArrayList<Coordinates> territoriesOwned) {

        //Update territoriesOwned and sort them higher to lower nbDice
        this.territoriesOwned = territoriesOwned;
        this.territoriesOwned.sort((coordinates1, coordinates2) -> jeux.getCarte().getNbDiceOnTerritoire(coordinates2) - jeux.getCarte().getNbDiceOnTerritoire(coordinates1));

        int retry = 0;
        Coordinates attackingTerritory;
        HashSet<Coordinates> foreignNeighbors;

        //Choose the best attackers
        do {
            attackingTerritory = this.territoriesOwned.get(retry);
            foreignNeighbors = this.jeux.getCarte().getNeighbors(attackingTerritory, true);
            foreignNeighbors.removeAll(this.territoriesOwned);
            retry++;
        } while (foreignNeighbors.isEmpty() && retry < this.territoriesOwned.size());

        return attackingTerritory;
    }

    /**
     * Choose a attacking territory from the coordinates of the attacking territory
     *
     * @param attackingTerritory The attacking territory
     * @return The target territory of the attack
     */
    public Coordinates chooseTarget(Coordinates attackingTerritory) {

        Coordinates targetTerritory = null;
        int minNbDice = this.jeux.getCarte().getNbDiceOnTerritoire(attackingTerritory);
        int nbDiceOnTerritory;

        //If the IA still able to attack
        if (attackingTerritory != null) {
            HashSet<Coordinates> potentialTarget = this.jeux.getCarte().getNeighbors(attackingTerritory, true);

            //If the IA still able to attack
            if (potentialTarget != null) {
                potentialTarget.removeAll(this.territoriesOwned);

                //Decide the best target, lower point (minimize defeat risk)
                for (Coordinates chosenOne : potentialTarget) {
                    nbDiceOnTerritory = this.jeux.getCarte().getNbDiceOnTerritoire(chosenOne);
                    if (nbDiceOnTerritory <= minNbDice) {
                        minNbDice = nbDiceOnTerritory;
                        targetTerritory = chosenOne;
                    }
                }
            }
        }

        return targetTerritory;
    }

    //endregion

}
