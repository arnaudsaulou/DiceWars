package DiceWars;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Partie {

    //region Variables

    private static ArrayList<Joueur> joueurs;
    private static Carte carte;
    private static Scanner keyboard;

    //endregion

    public static void main(String[] args) {

        keyboard = new Scanner(System.in);

        carte = new Carte(4, 4);

        initializeJoueurs(args[0]);

        //TODO
        //carte.importCarteFromCSV(askUserCarteToImportPath());
        carte.importCarteFromCSV("C:\\Users\\Arnaud\\Documents\\Cours\\L3\\Java2\\DiceWars\\res\\Carte1.csv");

        if (carte.getNbTerritoiresPlayable() < joueurs.size()) {
            throw new IllegalStateException("Plus de joueur que de territoires");
        } else if (carte.getNbTerritoiresPlayable() % joueurs.size() != 0) {
            throw new IllegalStateException("Le nombre de territoires n'est pas un multiple du nombre de joueurs");
        }

        Jeux jeux = new Jeux(joueurs, carte);

        lunchPartie();
    }

    //region Utils

    private static void lunchPartie() {
        int indexNextJoueur = 0;
        Joueur nextJoueur = joueurs.get(indexNextJoueur);

        System.out.println("\n" + carte);

        while (true) {

            System.out.println(carte.getMaxContiguousTerritories(nextJoueur));

            nextJoueur.jouer();
            System.out.println("\n" + carte);

            indexNextJoueur++;
            nextJoueur = joueurs.get(indexNextJoueur % joueurs.size());
        }
    }

    private static void initializeJoueurs(String arg) {
        try {
            int nbJoueur = Integer.parseInt(arg);
            joueurs = new ArrayList<>(nbJoueur);
            for (int i = 0; i < nbJoueur; i++) {
                joueurs.add(new Joueur(keyboard, carte));
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
