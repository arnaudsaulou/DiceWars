package DiceWars;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Partie {

    //region Variables

    private static ArrayList<Joueur> joueurs;
    private static Scanner keyboard;

    //endregion

    public static void main(String[] args) {

        initializeJoueurs(args[0]);

        keyboard = new Scanner(System.in);

        Carte carte = new Carte(4, 4);

        //TODO
        //carte.importCarteFromCSV(askUserCarteToImportPath());
        carte.importCarteFromCSV("C:\\Users\\Arnaud\\Documents\\Cours\\L3\\Java2\\DiceWars\\res\\Carte1.csv");

        if (carte.getNbTerritoiresPlayable() < joueurs.size()) {
            throw new IllegalStateException("Plus de joueur que de territoires");
        } else if (carte.getNbTerritoiresPlayable() % joueurs.size() != 0) {
            throw new IllegalStateException("Le nombre de territoires n'est pas un multiple du nombre de joueurs");
        }

        //System.out.println(carte);

        //System.out.println(carte.getNeighbours(new Coordinates(1, 3)));

        new Jeux(joueurs, carte);

        System.out.println("\n" + carte);
    }

    //region Utils

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

    //endregion
}
