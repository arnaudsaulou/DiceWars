package diceWars.exceptions;

public class NonNeighboringTerritoryException extends Exception {

    public NonNeighboringTerritoryException() {
        super("Attaque impossible : territoire non voisin !");
    }
}
