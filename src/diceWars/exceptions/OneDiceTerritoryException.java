package diceWars.exceptions;

public class OneDiceTerritoryException extends Exception {

    public OneDiceTerritoryException() {
        super("Attaque impossible : territoire contenant un seul dé !");
    }
}
