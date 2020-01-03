package DiceWars;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Partie {

    private static ArrayList<Joueur> joueurs;
    private static Carte carte;
    private static Scanner keyboard;

    public static void main(String[] args) {

        initializeJoueurs(args[0]);

        keyboard = new Scanner(System.in);

        carte = new Carte(5, 5);
        carte.importCarteFromCSV(askUserCarteToImportPath());

        System.out.println(carte);

        System.out.println(carte.getNeighbours(new Pair<>(1, 3)));
    }

    private static void initializeJoueurs(String arg) {
        try {
            int nbJoueur = Integer.parseInt(arg);
            joueurs = new ArrayList<>(nbJoueur);
            for (int i = 0; i < nbJoueur; i++) {
                joueurs.add(new Joueur(keyboard));
            }

        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Nombre de joueur invalide, veuillez réessayer");
            System.exit(151);
        }
    }

    private static String askUserCarteToImportPath() {
        System.out.print("Entrer le chemin d'accès da la carte à importer : ");
        return keyboard.nextLine();
    }
}
