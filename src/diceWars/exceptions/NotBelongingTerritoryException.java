package diceWars.exceptions;

public class NotBelongingTerritoryException extends Exception {

    public NotBelongingTerritoryException() {
        super("Attaque imposible : le territoire attaquant ne vous appartient pas !");
    }
}
