package DiceWars;

import java.util.Scanner;

public class Joueur {

    private static int ID_REGISTER = 0;

    private int Id;
    private Scanner keyboard;

    public Joueur(Scanner keyboard) {
        //Set the id of the new player and increment the id for the next one
        this.Id = ID_REGISTER;
        ID_REGISTER++;
    }

    protected void jouer() {
        System.out.println("- q pour terminer le tour");
        this.keyboard.nextLine();
        //TODO
    }
}
