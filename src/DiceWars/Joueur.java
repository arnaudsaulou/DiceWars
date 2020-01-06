package DiceWars;

import java.util.Scanner;

public class Joueur {

    //region Constants

    private static int ID_REGISTER = 0;

    //endregion

    //region Variables

    private int id;
    private int nbTerritoiresOwned;
    private Scanner keyboard;

    //endregion

    //region Constructor

    public Joueur(Scanner keyboard) {
        //Set the id of the new player and increment the id for the next one
        this.id = ID_REGISTER;
        ID_REGISTER++;
        this.keyboard = keyboard;
        this.nbTerritoiresOwned = 0;
    }

    //endregion

    //region Utils
    protected void jouer() {
        System.out.println("- q pour terminer le tour");
        this.keyboard.nextLine();
        //TODO
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
