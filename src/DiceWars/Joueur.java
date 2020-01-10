package DiceWars;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Joueur {

    //region Constants

    private static int ID_REGISTER = 0;

    //endregion

    //region Variables

    private int id;
    private int nbTerritoiresOwned;
    private Scanner keyboard;
    private Carte carte;

    //endregion

    //region Constructor

    public Joueur(Scanner keyboard, Carte carte) {
        //Set the id of the new player and increment the id for the next one
        this.id = ID_REGISTER;
        ID_REGISTER++;
        this.keyboard = keyboard;
        this.carte = carte;
        this.nbTerritoiresOwned = 0;
    }

    //endregion

    //region Utils
    protected void jouer() {

        String answer = null;

        do {

            System.out.println("\n/////////////////////////////////////////////");
            System.out.println("//////    Joueur " + (this.id + 1) + " a toi de jouer !    ////// ");
            System.out.println("/////////////////////////////////////////////\n");
            System.out.println("- a pour attaquer");
            System.out.println("- q pour terminer le tour");
            System.out.print("Choissisez votre action : ");

            answer = this.keyboard.nextLine();

            switch (answer) {
                case "a":
                    this.askCoordinateToAttack();
                    break;
                case "q":
                    this.terminateRound();
                    break;
                default:
                    System.out.println("Réponse invalide, veuillez réessayer");
                    System.out.print("Choissisez votre action : ");
                    break;
            }
        } while (!answer.equals("q"));
    }

    private void terminateRound() {
        System.out.println("Fin du tour.");
        int supportDices = this.carte.getMaxContiguousTerritories(this);
        Jeux.distributeSupportDices(this, supportDices);
    }

    private void askCoordinateToAttack() {

        try {
            System.out.println("\nEntrer les coordonées du territoire attaquant : ");
            System.out.print("X : ");
            int xAttaquant = this.keyboard.nextInt();
            System.out.print("Y : ");
            int yAttaquant = this.keyboard.nextInt();

            System.out.println("\nEntrer les coordonées du territoire à attaquer : ");
            System.out.print("X : ");
            int xAttaque = this.keyboard.nextInt();
            System.out.print("Y : ");
            int yAttaque = this.keyboard.nextInt();

            //To get ride of the nextInt bug
            this.keyboard.nextLine();

            if (xAttaquant < 0 || xAttaque < 0 || xAttaquant > this.carte.getWidht() - 1 || xAttaque > this.carte.getWidht() - 1 ||
                    yAttaquant < 0 || yAttaque < 0 || yAttaquant > this.carte.getWidht() - 1 || yAttaque > this.carte.getWidht() - 1 ||
                    !this.carte.getTerritoires()[xAttaquant][yAttaquant] || !this.carte.getTerritoires()[xAttaque][yAttaque])
                throw new IllegalArgumentException("Coordonnées invalide");

            this.attaquer(new Coordinates(xAttaquant, yAttaquant), new Coordinates(xAttaque, yAttaque));
        } catch (InputMismatchException e) {
            System.err.println("Coordonnée invalide");
        }


    }

    protected void attaquer(Coordinates coordinatesTerritoireAttaquant, Coordinates coordinatesTerritoireAttaque) {

        int nbDiceOnTerritoireAttaquant = this.carte.getNbDiceOnTerritoire(coordinatesTerritoireAttaquant);
        int nbDiceOnTerritoireAttaque = this.carte.getNbDiceOnTerritoire(coordinatesTerritoireAttaque);

        if (this.carte.getOwnerTerritoire(coordinatesTerritoireAttaquant) == this) {
            if (nbDiceOnTerritoireAttaquant != 1) {
                if (this.carte.getNeighbors(coordinatesTerritoireAttaquant).contains(coordinatesTerritoireAttaque)) {
                    if (this.carte.getOwnerTerritoire(coordinatesTerritoireAttaque) != this) {
                        this.determineWinner(coordinatesTerritoireAttaquant, coordinatesTerritoireAttaque, nbDiceOnTerritoireAttaquant, nbDiceOnTerritoireAttaque);
                    } else
                        System.out.println("Attaque impossible : le territoire attaqué vous appartient !");
                } else
                    System.out.println("Attaque impossible : territoire non voisin !");
            } else
                System.out.println("Attaque impossible : territoire contenant un seul dé !");
        } else
            System.out.println("Attaque imposible : le territoire attaquant ne vous appartient pas !");

    }


    /**
     * Throw dice for attacking and attacked territory and depending on the result, apply the rules of the games
     *
     * @param coordinatesTerritoireAttaquant Coordinates attacking territory
     * @param coordinatesTerritoireAttaque   Coordinates attacked territory
     * @param nbDiceOnTerritoireAttaquant    Number of dices on the attacking territory
     * @param nbDiceOnTerritoireAttaque      Number of dices on the attacked territory
     */
    private void determineWinner(Coordinates coordinatesTerritoireAttaquant, Coordinates coordinatesTerritoireAttaque, int nbDiceOnTerritoireAttaquant, int nbDiceOnTerritoireAttaque) {
        int scoreTerritoireAttaquant = Jeux.throwDices(nbDiceOnTerritoireAttaquant);
        int scoreTerritoireAttaque = Jeux.throwDices(nbDiceOnTerritoireAttaque);

        if (scoreTerritoireAttaquant > scoreTerritoireAttaque) {

            this.carte.setNbDiceOnTerritoire(coordinatesTerritoireAttaquant, 1);
            this.carte.setNbDiceOnTerritoire(coordinatesTerritoireAttaque, nbDiceOnTerritoireAttaque + nbDiceOnTerritoireAttaquant - 1);
            this.carte.setOwnerTerritoire(coordinatesTerritoireAttaque, this);

            System.out.println("L'attaquant à gagné !");
        } else {

            this.carte.setNbDiceOnTerritoire(coordinatesTerritoireAttaquant, 1);
            System.out.println("Le défenseur à gagné !");

        }
    }

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

}
