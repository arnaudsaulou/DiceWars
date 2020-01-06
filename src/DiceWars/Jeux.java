package DiceWars;

import java.util.ArrayList;
import java.util.Random;

public class Jeux {

    //region Constants

    private static final int NB_MAX_DICE_PER_TERRITOIRES = 8;

    //endregion

    //region Variables

    private int nbJoueurs;
    private ArrayList<Joueur> joueurs;
    private Carte carte;
    private Random random;

    //endregion

    //region Constructor

    public Jeux(ArrayList<Joueur> joueurs, Carte carte) {
        this.joueurs = joueurs;
        this.nbJoueurs = this.joueurs.size();
        this.carte = carte;
        this.random = new Random();
        this.allocateTerritoires();
    }

    //endregion

    //region Allocation territoires

    /**
     * Compute the number of territory to allocate per joueur,
     * choose a number of dice to allocate per joueur and
     * setup territories foreach Joueur
     */
    private void allocateTerritoires() {

        //Compute the number of territory to allocate per joueur
        int nbTerritoiresPerJoueur = this.carte.getNbTerritoiresPlayable() / this.nbJoueurs;

        //Choose a number of dice to allocate per joueur
        int nbDicePerJoueur = Math.max(this.random.nextInt(this.carte.getNbTerritoiresPlayable() * NB_MAX_DICE_PER_TERRITOIRES)
                / this.joueurs.size(), this.carte.getNbTerritoiresPlayable());

        setupTerritoireForeachJoueur(nbTerritoiresPerJoueur, nbDicePerJoueur);

    }

    /**
     * Associate randomly a territory with a joueur and a random number of dice (between 1 and 8)
     *
     * @param nbTerritoiresPerJoueur The final number of territories that a joueur has to own
     * @param nbDicePerJoueur        The final number of dice that a joueur has to possess
     */
    private void setupTerritoireForeachJoueur(int nbTerritoiresPerJoueur, int nbDicePerJoueur) {

        //Foreach joueur
        for (Joueur joueur : this.joueurs) {

            //Construct a random distribution sequence
            ArrayList<Integer> diceByTerritoire = getRandomSequence(nbTerritoiresPerJoueur, nbDicePerJoueur);

            //While the player has to own more territories
            while (joueur.getNbTerritoiresOwned() < nbTerritoiresPerJoueur) {

                //Browse in width
                for (int x = 0; x < this.carte.getWidht() && joueur.getNbTerritoiresOwned() < nbTerritoiresPerJoueur; x++) {

                    //Browse in height
                    for (int y = 0; y < this.carte.getHeight() && joueur.getNbTerritoiresOwned() < nbTerritoiresPerJoueur; y++) {
                        associateTerritoireWithJoueurOrNot(joueur, diceByTerritoire, x, y);
                    }
                }
            }
        }
    }

    /**
     * Associate a territory with a player if possible and if the random is ok
     *
     * @param joueur           The player
     * @param diceByTerritoire The territory
     * @param x                The horizontal coordinate
     * @param y                The vertical coordinate
     */
    private void associateTerritoireWithJoueurOrNot(Joueur joueur, ArrayList<Integer> diceByTerritoire, int x, int y) {
        //If the territory can be played and the territory has no owner and the random is true
        if (this.carte.getTerritoires()[x][y] &&
                this.random.nextBoolean() &&
                this.carte.getOwnerTerritoire(new Coordinates(x, y)) == null) {

            //Associate the territory with the player
            setupTerritoire(joueur, diceByTerritoire, x, y);
        }
    }

    /**
     * The entire process to associate a territory with a player and associate a random number of dice that come with
     *
     * @param joueur           The player
     * @param diceByTerritoire The territory
     * @param x                The horizontal coordinate
     * @param y                The vertical coordinate
     */
    private void setupTerritoire(Joueur joueur, ArrayList<Integer> diceByTerritoire, int x, int y) {

        //Set the owner of the territory
        this.carte.setOwnerTerritoire(new Coordinates(x, y), joueur);

        //Choose a index in the random distribution sequence
        int index = this.random.nextInt(diceByTerritoire.size());

        //Get the value at the specified index
        int nbDice = diceByTerritoire.get(index);

        //Set the value as the number of dice present on the territory
        this.carte.setNbDiceOnTerritoire(new Coordinates(x, y), nbDice);

        //Make +1 on the number of territory own by the player
        joueur.incremetNbTerritoiresOwned();

        //Remove the value in the random distribution sequence
        diceByTerritoire.remove(index);
    }

    /**
     * Construct a random distribution sequence depending on the total number of territories and the total number of dices
     *
     * @param nbTerritoiresPerJoueur The total number of territories
     * @param nbDiceJoueur           The total number of dices
     * @return An arraylist containing the random distribution sequence
     */
    private ArrayList<Integer> getRandomSequence(int nbTerritoiresPerJoueur, int nbDiceJoueur) {
        ArrayList<Integer> diceByTerritoire = new ArrayList<>(nbTerritoiresPerJoueur);

        nbDiceJoueur = setMinimumDicePerTerritoire(nbTerritoiresPerJoueur, nbDiceJoueur, diceByTerritoire);

        distributeDiceOnTerritoires(nbTerritoiresPerJoueur, nbDiceJoueur, diceByTerritoire);

        return diceByTerritoire;
    }

    /**
     * Distribute randomly a fixed number of dice on a fixed-sized ArrayList
     *
     * @param nbTerritoiresPerJoueur The number of territories that has to own a player
     * @param nbDiceJoueur           The total number of dice to set
     * @param diceByTerritoire       The arrayList that will contains the random distribution sequence
     */
    private void distributeDiceOnTerritoires(int nbTerritoiresPerJoueur, int nbDiceJoueur, ArrayList<Integer> diceByTerritoire) {
        int indexTerritoire = 0;

        //While the player has dice to place
        while (nbDiceJoueur > 0) {

            //Choose a number of dices to place between 1 and 8
            int rand = Math.min(Math.max(this.random.nextInt(nbDiceJoueur), 1), NB_MAX_DICE_PER_TERRITOIRES);

            //Compute the index where to place these dices
            int index = indexTerritoire % nbTerritoiresPerJoueur;

            //If excess of dices
            if (diceByTerritoire.get(index) + rand > NB_MAX_DICE_PER_TERRITOIRES) {
                rand = NB_MAX_DICE_PER_TERRITOIRES - diceByTerritoire.get(index);
            }

            //Place dices on territory
            diceByTerritoire.set(index, diceByTerritoire.get(index) + rand);

            //Decrement the number of dice that the player has to place
            nbDiceJoueur -= rand;

            //Go to next index
            indexTerritoire++;
        }
    }

    /**
     * Put the minimum dice on each territories and return the number of dice left
     *
     * @param nbTerritoiresPerJoueur The number of territories that has to own a player
     * @param nbDiceJoueur           The total number of dice to set
     * @param diceByTerritoire       The arrayList that will contains the random distribution sequence
     * @return The number of dice left to set
     */
    private int setMinimumDicePerTerritoire(int nbTerritoiresPerJoueur, int nbDiceJoueur, ArrayList<Integer> diceByTerritoire) {
        for (int i = 0; i < nbTerritoiresPerJoueur; i++) {
            diceByTerritoire.add(i, 1);
            nbDiceJoueur--;
        }
        return nbDiceJoueur;
    }

    //endregion

    //region Trow dices

    protected int throwDices(int nbDice) {
        int result = 0;

        for (int diceNumber = 0; diceNumber < nbDice; diceNumber++)
            result += this.aleatoire();

        return result;
    }

    private int aleatoire() {
        return this.random.nextInt(5) + 1;
    }

    //endregion

}
