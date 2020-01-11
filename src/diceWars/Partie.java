package diceWars;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Partie {

    //region Variables

    private static ArrayList<Joueur> joueurs;
    private static Scanner keyboard;
    private static Jeux jeux;

    //endregion

    public static void main(String[] args) {

        keyboard = new Scanner(System.in);

        Carte carte = new Carte(4, 4);

        joueurs = initializeJoueurs(args[0]);

        //TODO
        //carte.importCarteFromCSV(askUserCarteToImportPath());
        carte.importCarteFromCSV("C:\\Users\\Arnaud\\Documents\\Cours\\L3\\Java2\\DiceWars\\res\\Carte1.csv");

        if (carte.getNbTerritoiresPlayable() < joueurs.size()) {
            throw new IllegalStateException("Plus de joueur que de territoires");
        } else if (carte.getNbTerritoiresPlayable() % joueurs.size() != 0) {
            throw new IllegalStateException("Le nombre de territoires n'est pas un multiple du nombre de joueurs");
        }

        jeux = new Jeux(carte, joueurs);

        lunchPartie();
    }

    //region Utils

    private static void lunchPartie() {
        int indexNextJoueur = 0;
        Joueur nextJoueur = joueurs.get(indexNextJoueur);

        while (jeux.isGameNotOver()) {
            nextJoueur.jouer();
            indexNextJoueur++;
            nextJoueur = joueurs.get(indexNextJoueur % joueurs.size());
        }


    }

    private static ArrayList<Joueur> initializeJoueurs(String arg) {

        ArrayList<Joueur> listJoueur = new ArrayList<>();

        try {
            int nbJoueur = Integer.parseInt(arg);
            for (int i = 0; i < nbJoueur; i++) {
                listJoueur.add(new Joueur(jeux, keyboard));
            }

        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Nombre de joueur invalide, veuillez réessayer");
            System.exit(151);
        }

        return listJoueur;
    }

    private static String askUserCarteToImportPath() {
        System.out.print("Entrer le chemin d'accès da la carte à importer : ");
        return keyboard.nextLine();
    }

    //endregion
}
