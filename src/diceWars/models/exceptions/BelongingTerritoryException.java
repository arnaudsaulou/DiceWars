package diceWars.models.exceptions;

import diceWars.models.Coordinates;

public class BelongingTerritoryException extends Exception {

    public BelongingTerritoryException
            (Coordinates coordinatesTerritoireAttaquant, Coordinates coordinatesTerritoireAttaque) {
        super("Le territoire attaqué vous appartient !\n " +
                coordinatesTerritoireAttaquant + " -> " + coordinatesTerritoireAttaque);
    }
}
