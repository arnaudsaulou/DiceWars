package diceWars.models.exceptions;

import diceWars.models.Coordinates;

public class NonNeighboringTerritoryException extends Exception {

    public NonNeighboringTerritoryException
            (Coordinates coordinatesTerritoireAttaquant, Coordinates coordinatesTerritoireAttaque) {
        super("Territoire non voisin !\n " +
                coordinatesTerritoireAttaquant + " -> " + coordinatesTerritoireAttaque);
    }
}
