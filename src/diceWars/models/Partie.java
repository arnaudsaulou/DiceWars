package diceWars.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Partie extends AbstractModel {

    //region Constants

    public enum MODE {SOLO, MULTI}

    public static final Color[] COLOR = {Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW};
    public static final Set<Color> COLOR_USED = new HashSet<>();

    //endregion

    //region Variables

    private final MODE mode;
    private final Jeux jeux;
    private final ArrayList<Joueur> joueurs;

    //endregion

    public Partie(int nbPlayer, MODE mode) throws IllegalStateException {
        this.mode = mode;

        this.joueurs = initializeJoueurs(nbPlayer);

        if (this.mode.equals(MODE.SOLO)) {
            nbPlayer += 1;
            this.joueurs.add(new IA());
        }

        this.jeux = new Jeux(new Carte(nbPlayer), joueurs);
    }

    //region Utils

    private ArrayList<Joueur> initializeJoueurs(int nbPlayer) throws IllegalStateException {

        ArrayList<Joueur> listJoueur = new ArrayList<>();

        if (nbPlayer <= COLOR.length) {
            for (int i = 0; i < nbPlayer; i++) {
                listJoueur.add(new Joueur());
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
        return this.joueurs;
    }

}
