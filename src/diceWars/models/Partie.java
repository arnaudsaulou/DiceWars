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

    private final Jeux jeux;
    private final ArrayList<Joueur> joueurs;

    //endregion

    //region Constructor

    public Partie(int nbPlayer, MODE mode) {

        this.joueurs = initializeJoueurs(nbPlayer);

        if (mode.equals(MODE.SOLO)) {
            nbPlayer += 1;
            //Add the ia to the list of joueurs
            this.joueurs.add(new IA());
        }

        this.jeux = new Jeux(new Carte(nbPlayer), joueurs);
    }

    //endregion

    //region Utils

    /**
     * Create the number of joueurs pass in parameters and put them into a list
     *
     * @param nbPlayer The number of players to create
     * @return The list of players created
     */
    private ArrayList<Joueur> initializeJoueurs(int nbPlayer) {

        ArrayList<Joueur> listJoueur = new ArrayList<>();

        for (int i = 0; i < nbPlayer; i++) {
            listJoueur.add(new Joueur());
        }

        return listJoueur;
    }

    //endregion

    //region Getter

    public Jeux getJeux() {
        return this.jeux;
    }

    public ArrayList<Joueur> getJoueurs() {
        return this.joueurs;
    }

    //endregion

}
