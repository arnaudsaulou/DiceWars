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
    private static Carte carte;
    private static Random random;

    //endregion

    //region Constructor

    public Jeux(ArrayList<Joueur> joueurs, Carte carte) {
        this.joueurs = joueurs;
        this.nbJoueurs = this.joueurs.size();
        Jeux.carte = carte;
        random = new Random();
        this.allocateTerritoires();
    }

    //endregion

    //region Allocation territoires

    protected static int throwDices(int nbDice) {
        int result = 0;

        for (int diceNumber = 0; diceNumber < nbDice; diceNumber++)
            result += aleatoire();

        return result;
    }

    private static int aleatoire() {
        return random.nextInt(5) + 1;
    }

    public static void distributeSupportDices(Joueur joueur, int supportDices) {

        ArrayList<Coordinates> territoiresOfPlayer = carte.getTerritoiresOfPlayer(joueur);

        while (supportDices > 0) {
            Coordinates choosenTerritoire = territoiresOfPlayer.get(random.nextInt(territoiresOfPlayer.size()));

            if (carte.getNbDiceOnTerritoire(choosenTerritoire) + 1 <= NB_MAX_DICE_PER_TERRITOIRES) {
                carte.setNbDiceOnTerritoire(choosenTerritoire, carte.getNbDiceOnTerritoire(choosenTerritoire) + 1);
                supportDices--;
            }
        }
    }

    /**
     * Compute the number of territory to allocate per joueur,
     * choose a number of dice to allocate per joueur and
     * setup territories foreach Joueur
     */
    private void allocateTerritoires() {

        //Compute the number of territory to allocate per joueur
        int nbTerritoiresPerJoueur = carte.getNbTerritoiresPlayable() / this.nbJoueurs;

        //Choose a number of dice to allocate per joueur
        int nbDicePerJoueur = Math.max(random.nextInt(carte.getNbTerritoiresPlayable() * NB_MAX_DICE_PER_TERRITOIRES)
                / this.joueurs.size(), carte.getNbTerritoiresPlayable());

        setupTerritoireForeachJoueur(nbTerritoiresPerJoueur, nbDicePerJoueur);

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

                //Browse in height
                for (int y = 0; y < carte.getHeight() && joueur.getNbTerritoiresOwned() < nbTerritoiresPerJoueur; y++) {

                    //Browse in width
                    for (int x = 0; x < carte.getWidht() && joueur.getNbTerritoiresOwned() < nbTerritoiresPerJoueur; x++) {
                        associateTerritoireWithJoueurOrNot(joueur, diceByTerritoire, x, y);
                    }
                }
            }
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
        if (carte.getTerritoires()[x][y] &&
                random.nextBoolean() &&
                carte.getOwnerTerritoire(new Coordinates(x, y)) == null) {

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
        carte.setOwnerTerritoire(new Coordinates(x, y), joueur);

        //Choose a index in the random distribution sequence
        int index = random.nextInt(diceByTerritoire.size());

        //Get the value at the specified index
        int nbDice = diceByTerritoire.get(index);

        //Set the value as the number of dice present on the territory
        carte.setNbDiceOnTerritoire(new Coordinates(x, y), nbDice);

        //Make +1 on the number of territory own by the player
        joueur.incremetNbTerritoiresOwned();

        //Remove the value in the random distribution sequence
        diceByTerritoire.remove(index);
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
            int rand = Math.min(Math.max(random.nextInt(nbDiceJoueur), 1), NB_MAX_DICE_PER_TERRITOIRES);

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

    //endregion

}
