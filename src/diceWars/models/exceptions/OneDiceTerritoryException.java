package diceWars.models.exceptions;

import diceWars.models.Coordinates;

public class OneDiceTerritoryException extends Exception {

    public OneDiceTerritoryException
            (Coordinates coordinatesTerritoireAttaquant, Coordinates coordinatesTerritoireAttaque) {
        super("Territoire contenant un seul dé !\n " +
                coordinatesTerritoireAttaquant + " -> " + coordinatesTerritoireAttaque);
    }
}
