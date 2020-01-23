package diceWars.models.exceptions;

import diceWars.models.Coordinates;

public class NotBelongingTerritoryException extends Exception {

    public NotBelongingTerritoryException
            (Coordinates coordinatesTerritoireAttaquant, Coordinates coordinatesTerritoireAttaque) {
        super("Le territoire attaquant ne vous appartient pas !\n " +
                coordinatesTerritoireAttaquant + " -> " + coordinatesTerritoireAttaque);
    }
}
