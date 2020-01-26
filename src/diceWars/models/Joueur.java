package diceWars.models;

import diceWars.models.exceptions.BelongingTerritoryException;
import diceWars.models.exceptions.NonNeighboringTerritoryException;
import diceWars.models.exceptions.NotBelongingTerritoryException;
import diceWars.models.exceptions.OneDiceTerritoryException;

import java.awt.*;
import java.util.Random;

public class Joueur {

    //region Constants

    private static int ID_REGISTER = 0;

    //endregion

    //region Variables

    private final int id;
    private int nbTerritoiresOwned;
    private final Color colorPlayer;
    protected Jeux jeux;

    //endregion

    //region Constructor

    public Joueur() {
        //Set the id of the new player and increment the id for the next one
        this.id = ID_REGISTER;
        ID_REGISTER++;
        this.nbTerritoiresOwned = 0;

        this.colorPlayer = this.chooseColor();
    }

    /**
     * Associate a color from the list of colors available to the player
     *
     * @return The choosen color
     */
    private Color chooseColor() {
        Random rand = new Random();
        Color color;
        boolean retry;

        do {
            color = Partie.COLOR[rand.nextInt(Partie.COLOR.length)];

            if (Partie.COLOR_USED.contains(color)) {
                retry = true;
            } else {
                Partie.COLOR_USED.add(color);
                retry = false;
            }
        } while (retry);

        return color;
    }

    //endregion

    //region Utils


    /**
     * Procedure to attack a territory from another territory
     *
     * @param coordinatesTerritoireAttaquant The attacking territory
     * @param coordinatesTerritoireAttaque   The attacked territory
     */
    public int[] attaquer(Coordinates coordinatesTerritoireAttaquant, Coordinates coordinatesTerritoireAttaque)
            throws BelongingTerritoryException, NonNeighboringTerritoryException,
            OneDiceTerritoryException, NotBelongingTerritoryException {

        int nbDiceOnTerritoireAttaquant = this.jeux.getCarte().getNbDiceOnTerritoire(coordinatesTerritoireAttaquant);
        int nbDiceOnTerritoireAttaque = this.jeux.getCarte().getNbDiceOnTerritoire(coordinatesTerritoireAttaque);
        int[] scores;

        if (this.jeux.getCarte().getOwnerTerritoire(coordinatesTerritoireAttaquant) == this) {
            if (nbDiceOnTerritoireAttaquant != 1) {
                if (this.jeux.getCarte().getOwnerTerritoire(coordinatesTerritoireAttaque) != this) {
                    if (this.jeux.getCarte().getNeighbors(coordinatesTerritoireAttaquant, true).contains(coordinatesTerritoireAttaque)) {

                        scores = this.jeux.determineWinner(
                                coordinatesTerritoireAttaquant,
                                coordinatesTerritoireAttaque,
                                nbDiceOnTerritoireAttaquant,
                                nbDiceOnTerritoireAttaque,
                                this
                        );
                    } else
                        throw new NonNeighboringTerritoryException(coordinatesTerritoireAttaquant, coordinatesTerritoireAttaque);
                } else
                    throw new BelongingTerritoryException(coordinatesTerritoireAttaquant, coordinatesTerritoireAttaque);
            } else
                throw new OneDiceTerritoryException(coordinatesTerritoireAttaquant, coordinatesTerritoireAttaque);
        } else
            throw new NotBelongingTerritoryException(coordinatesTerritoireAttaquant, coordinatesTerritoireAttaque);

        return scores;
    }

    /**
     * Add one to the number of territories owned
     */
    public void incremetNbTerritoiresOwned() {
        this.nbTerritoiresOwned++;
    }

    /**
     * Remove one to the number of territories owned
     */
    public void decremetNbTerritoiresOwned() {
        this.nbTerritoiresOwned--;
    }

    //endregion

    //region Getter

    public int getId() {
        return this.id;
    }

    public int getNbTerritoiresOwned() {
        return this.nbTerritoiresOwned;
    }

    public Color getColorPlayer() {
        return this.colorPlayer;
    }

    //endregion

    //region Setter

    protected void setJeux(Jeux jeux) {
        this.jeux = jeux;
    }

    //endregion

}
