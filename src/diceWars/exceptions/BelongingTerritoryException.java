package diceWars.exceptions;

public class BelongingTerritoryException extends Exception {

    public BelongingTerritoryException() {
        super("Attaque impossible : le territoire attaqué vous appartient !");
    }
}
