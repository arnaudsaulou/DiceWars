package diceWars.models;

import java.awt.*;
import java.util.*;

public class Partie {

    //region Constants

    public enum MODE {SOLO, MULTI}

    ;
    public static Color[] COLOR = {Color.BLUE,Color.GREEN,Color.RED,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.YELLOW};
    public static Set<Color> COLOR_USED = new HashSet<>();

    //endregion

    //region Variables

    private MODE mode;
    private Jeux jeux;
    private ArrayList<Joueur> joueurs;

    //endregion

    public Partie(String args) throws IllegalStateException {

        Carte carte = new Carte(40 , 40);

        this.joueurs = initializeJoueurs(args);

        //TODO
        //carte.importCarteFromCSV(askUserCarteToImportPath());
        carte.importCarteFromCSV("C:\\Users\\Arnaud\\Documents\\Cours\\L3\\Java2\\DiceWars\\res\\Carte1.csv");

        if (carte.getNbTerritoiresPlayable() < joueurs.size()) {
            throw new IllegalStateException("Plus de joueur que de territoires");
        } else if (carte.getNbTerritoiresPlayable() % joueurs.size() != 0) {
            throw new IllegalStateException("Le nombre de territoires n'est pas un multiple du nombre de joueurs");
        }

        this.jeux = new Jeux(carte, joueurs);
    }

    //region Utils

    public void lunchPartie(MODE mode) {
        this.mode = mode;
    }

    private ArrayList<Joueur> initializeJoueurs(String arg) throws IllegalStateException {

        ArrayList<Joueur> listJoueur = new ArrayList<>();

        try {
            int nbJoueur = Integer.parseInt(arg);

            if (nbJoueur <= COLOR.length) {
                for (int i = 0; i < nbJoueur; i++) {
                    listJoueur.add(new Joueur(this.jeux));
                }
            } else {
                throw new IllegalStateException("Trop de joueur (max : " + COLOR.length + ")");
            }

        } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Nombre de joueur invalide, veuillez réessayer");
            System.exit(151);
        }

        return listJoueur;
    }


    //endregion

    public Jeux getJeux() {
        return this.jeux;
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }
}
