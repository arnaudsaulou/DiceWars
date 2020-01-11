package diceWars;

import diceWars.exceptions.BelongingTerritoryException;
import diceWars.exceptions.NonNeighboringTerritoryException;
import diceWars.exceptions.NotBelongingTerritoryException;
import diceWars.exceptions.OneDiceTerritoryException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Joueur {

    //region Constants

    private static int ID_REGISTER = 0;

    //endregion

    //region Variables

    private final int id;
    private int nbTerritoiresOwned;
    private final Scanner keyboard;
    private Jeux jeux;

    //endregion

    //region Constructor

    public Joueur(Jeux jeux, Scanner keyboard) {
        //Set the id of the new player and increment the id for the next one
        this.id = ID_REGISTER;
        ID_REGISTER++;
        this.jeux = jeux;
        this.keyboard = keyboard;
        this.nbTerritoiresOwned = 0;
    }

    //endregion

    //region Utils

    /**
     * Precess proposing the player action he can do
     */
    protected void jouer() {

        String answer;

        do {

            System.out.println("\n\n" + this.jeux.getCarte());

            System.out.println("/////////////////////////////////////////////");
            System.out.println("//////    Joueur " + (this.id + 1) + " a toi de jouer !    ////// ");
            System.out.println("/////////////////////////////////////////////\n");
            System.out.println("- a pour attaquer");
            System.out.println("- q pour terminer le tour");
            System.out.print("Choissisez votre action : ");

            answer = this.keyboard.nextLine();

            switch (answer) {
                case "a":
                    this.askNeededCoordinatesToAttack();
                    break;
                case "q":
                    this.terminateRound();
                    break;
                default:
                    System.out.println("Réponse invalide, veuillez réessayer");
                    System.out.print("Choissisez votre action : ");
                    break;
            }
        } while (!answer.equals("q") && this.jeux.isGameNotOver());
    }

    /**
     * Procedure to end the round
     */
    private void terminateRound() {
        System.out.println("Fin du tour.");
        int supportDices = this.jeux.getCarte().getMaxContiguousTerritories(this);
        this.jeux.distributeSupportDices(this, supportDices);
    }

    /**
     * Procedure to ask the player which coordinate attack and from which one
     */
    private void askNeededCoordinatesToAttack() {

        try {

            Coordinates coordinatesAttacking = this.askCoordinate("\nEntrer les coordonées du territoire attaquant : ");
            Coordinates coordinatesAttacked = this.askCoordinate("\nEntrer les coordonées du territoire à attaquer : ");

            //To get ride of the nextInt bug
            this.keyboard.nextLine();

            if (this.jeux.getCarte().isCoordinatesValid(coordinatesAttacking) &&
                    this.jeux.getCarte().isCoordinatesValid(coordinatesAttacked))
                this.attaquer(coordinatesAttacking, coordinatesAttacked);
            else
                System.err.println("Coordonnée invalide");

        } catch (InputMismatchException e) {
            System.err.println("Coordonnée invalide");
        } catch (BelongingTerritoryException |
                OneDiceTerritoryException |
                NotBelongingTerritoryException |
                NonNeighboringTerritoryException e) {
            System.err.println(e.getMessage());
        }
    }

    private Coordinates askCoordinate(String msg) {
        System.out.println(msg);
        System.out.print("X : ");
        int x = this.keyboard.nextInt();
        System.out.print("Y : ");
        int y = this.keyboard.nextInt();

        return new Coordinates(x, y);
    }


    /**
     * Procedure to attack a territory from another territory
     *
     * @param coordinatesTerritoireAttaquant The attacking territory
     * @param coordinatesTerritoireAttaque   The attacked territory
     */
    protected void attaquer(Coordinates coordinatesTerritoireAttaquant, Coordinates coordinatesTerritoireAttaque)
            throws BelongingTerritoryException, NonNeighboringTerritoryException, OneDiceTerritoryException, NotBelongingTerritoryException {

        int nbDiceOnTerritoireAttaquant = this.jeux.getCarte().getNbDiceOnTerritoire(coordinatesTerritoireAttaquant);
        int nbDiceOnTerritoireAttaque = this.jeux.getCarte().getNbDiceOnTerritoire(coordinatesTerritoireAttaque);

        if (this.jeux.getCarte().getOwnerTerritoire(coordinatesTerritoireAttaquant) == this) {
            if (nbDiceOnTerritoireAttaquant != 1) {
                if (this.jeux.getCarte().getNeighbors(coordinatesTerritoireAttaquant).contains(coordinatesTerritoireAttaque)) {
                    if (this.jeux.getCarte().getOwnerTerritoire(coordinatesTerritoireAttaque) != this) {
                        this.jeux.determineWinner(
                                coordinatesTerritoireAttaquant,
                                coordinatesTerritoireAttaque,
                                nbDiceOnTerritoireAttaquant,
                                nbDiceOnTerritoireAttaque,
                                this
                        );
                    } else
                        throw new BelongingTerritoryException();
                } else
                    throw new NonNeighboringTerritoryException();
            } else
                throw new OneDiceTerritoryException();
        } else
            throw new NotBelongingTerritoryException();
    }


    /**
     * Add one to the number of territories owned
     */
    public void incremetNbTerritoiresOwned() {
        this.nbTerritoiresOwned++;
    }

    //endregion

    //region Getter

    public int getId() {
        return this.id;
    }

    public int getNbTerritoiresOwned() {
        return this.nbTerritoiresOwned;
    }

    //endregion

    //region Setter

    protected void setJeux(Jeux jeux) {
        this.jeux = jeux;
    }

    //endregion

}
