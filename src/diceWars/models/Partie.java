package diceWars.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Partie extends AbstractModel {

    //region Constants

    public enum MODE {SOLO, MULTI}

    public static Color[] COLOR = {Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW};
    public static Set<Color> COLOR_USED = new HashSet<>();

    //endregion

    //region Variables

    private MODE mode;
    private Jeux jeux;
    private ArrayList<Joueur> joueurs;

    //endregion

    public Partie(int nbPlayer) throws IllegalStateException {

        Carte carte = new Carte(25, 25);

        this.joueurs = initializeJoueurs(nbPlayer);

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

    private ArrayList<Joueur> initializeJoueurs(int nbPlayer) throws IllegalStateException {

        ArrayList<Joueur> listJoueur = new ArrayList<>();

        if (nbPlayer <= COLOR.length) {
            for (int i = 0; i < nbPlayer; i++) {
                listJoueur.add(new Joueur(this.jeux));
            }
        } else {
            throw new IllegalStateException("Trop de joueur (max : " + COLOR.length + ")");
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
