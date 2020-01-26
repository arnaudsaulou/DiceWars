package diceWars.models;

import diceWars.DiceWars;

import java.util.*;

public class Carte extends AbstractModel {

    //region Variables

    private final int size;
    private final float density;
    private final boolean[][] territoires;
    private final HashMap<Coordinates, Integer> nbDiceByTerritoire;   //Coordinates <> NbDice
    private final HashMap<Coordinates, Joueur> ownerTerritoire;       //Coordinates <> Joueur
    private final Random random;

    //endregion

    //region Constructor

    public Carte(int nbPlayer) {
        this.size = DiceWars.getSize();
        this.density = DiceWars.getDensity();
        this.territoires = new boolean[this.size][this.size];
        this.nbDiceByTerritoire = new HashMap<>();
        this.ownerTerritoire = new HashMap<>();
        this.random = new Random();

        //Create a random map and verify it
        this.createMap(new Coordinates(this.random.nextInt(this.size), this.random.nextInt(this.size)));
        this.verifyMap(nbPlayer);
    }

    //endregion

    //region Utils

    private void verifyMap(int nbPlayer) {
        //Check if there is more territories on the map than players and
        //if the number of territories is a multiple of the number of players
        if (this.getNbTerritoiresPlayable() < nbPlayer || this.getNbTerritoiresPlayable() % nbPlayer != 0) {
            try {
                //Reset the map
                for (boolean[] territoire : this.territoires) {
                    Arrays.fill(territoire, false);
                }

                //Create a random map and verify it
                this.createMap(new Coordinates(this.random.nextInt(this.size), this.random.nextInt(this.size)));
                this.verifyMap(nbPlayer);
            } catch (StackOverflowError e) {

                //Force garbage collector (recursive)
                System.gc();
            }
        }
    }

    /**
     * Create recursively a random map depending on the density
     *
     * @param startingPoint The starting point where to start the procedure
     */
    private void createMap(Coordinates startingPoint) {

        //Get non playable neighbors
        ArrayList<Coordinates> nonPlayableNeighbors = new ArrayList<>(this.getNeighbors(startingPoint, false));

        //If exist non playable around the startingPoint
        if (!nonPlayableNeighbors.isEmpty()) {

            //Enable the startingPoint
            this.territoires[startingPoint.getY()][startingPoint.getX()] = true;

            //Foreach non playable neighbors
            for (Coordinates designatedTerritory : nonPlayableNeighbors) {

                //If number of playable neighbors < density * 10
                if (this.getNeighbors(designatedTerritory, true).size() < Math.min((this.density * 10), 9)) {
                    this.createMap(designatedTerritory);
                }
            }
        }
    }

    /**
     * Check if a coordinates are valid : in the dimensions of the map and on a playable territory
     *
     * @param coordinates Coordinates to check
     * @return A boolean, true if coordinates ok, false else
     */
    public boolean isCoordinatesValid(Coordinates coordinates) {
        return coordinates.getX() >= 0 &&
                coordinates.getX() < this.size &&
                coordinates.getY() >= 0 &&
                coordinates.getY() < this.size &&
                this.getTerritoires()[coordinates.getY()][coordinates.getX()];
    }

    //endregion

    //region Setter

    /**
     * Set a number of dices on a territory
     *
     * @param coordinatesTerritoire Coordinates of the territory
     * @param nbDice                Number of dices to set
     */
    protected void setNbDiceOnTerritoire(Coordinates coordinatesTerritoire, int nbDice) {
        //this.nbDiceByTerritoire.remove(coordinatesTerritoire);
        this.nbDiceByTerritoire.put(coordinatesTerritoire, nbDice);
    }

    /**
     * Set a Joueur as the owner of the territory
     *
     * @param coordinatesTerritoire Coordinates of the territory
     * @param owner                 The owner to set
     */
    protected void setOwnerTerritoire(Coordinates coordinatesTerritoire, Joueur owner) {
        this.ownerTerritoire.remove(coordinatesTerritoire);
        this.ownerTerritoire.put(coordinatesTerritoire, owner);
    }

    //endregion

    //region Getter


    /**
     * Get coordinates of neighbors territories, playable or not
     *
     * @param coordinatesTerritoire Coordinates of the origin territory
     * @param condition             True if wants playable neighbors, false if want non playable neighbors
     * @return ArrayList containing all coordinates of playable (or not) neighbors territories
     */
    protected HashSet<Coordinates> getNeighbors(Coordinates coordinatesTerritoire, boolean condition) {
        HashSet<Coordinates> neighbours = new HashSet<>();

        int row = coordinatesTerritoire.getY();
        int column = coordinatesTerritoire.getX();

        //Search upper, at the same level and lower
        for (int y = Math.max(0, row - 1); y <= Math.min(this.size - 1, row + 1); y++) {

            //Search left, in the middle and right
            for (int x = Math.max(0, column - 1); x <= Math.min(this.size - 1, column + 1); x++) {

                //If it's not the origin territory and the terittoires is playable or not
                if ((x != column || y != row) && (this.territoires[y][x] == condition)) {
                    neighbours.add(new Coordinates(x, y));
                }
            }
        }

        return neighbours;
    }

    /**
     * Get list of territories owned by a player
     *
     * @param joueur The player
     * @return The list of territories owned
     */
    public ArrayList<Coordinates> getTerritoiresOfPlayer(Joueur joueur) {

        ArrayList<Coordinates> territoiresOfPlayer = new ArrayList<>();

        for (Coordinates territoire : this.ownerTerritoire.keySet()) {
            if (this.ownerTerritoire.get(territoire) == joueur) {
                territoiresOfPlayer.add(territoire);
            }
        }

        return territoiresOfPlayer;
    }

    /**
     * Entire process to return the max number of contiguous territories owned by a player
     *
     * @param joueur The player
     * @return The number of contiguous territories max
     */
    protected int getMaxContiguousTerritories(Joueur joueur) {

        int maxContiguousTerritories = 0;
        HashSet<Coordinates> territoiresVisited = new HashSet<>();

        for (Coordinates territoire : this.getTerritoiresOfPlayer(joueur)) {
            maxContiguousTerritories = Math.max(maxContiguousTerritories,
                    this.computeContiguousTerritories(territoire, territoiresVisited));
            territoiresVisited.clear();
        }

        return maxContiguousTerritories;
    }

    /**
     * Compute recursively the number of contiguous territories starting from a specified territory
     *
     * @param coordinatesTerritoire The starting territory
     * @param territoiresVisited    The list of territories already visited
     * @return The number of contiguous territories around the starting territory
     */
    private int computeContiguousTerritories(
            Coordinates coordinatesTerritoire, HashSet<Coordinates> territoiresVisited) {

        int maxContiguousTerritories = 0;

        HashSet<Coordinates> neighbors = this.getNeighbors(coordinatesTerritoire, true);

        for (Coordinates neighbor : neighbors) {

            if (this.ownerTerritoire.get(coordinatesTerritoire) == this.ownerTerritoire.get(neighbor) &&
                    !territoiresVisited.contains(neighbor)) {
                territoiresVisited.add(neighbor);
                maxContiguousTerritories += this.computeContiguousTerritories(neighbor, territoiresVisited) + 1;
            } else {
                maxContiguousTerritories += 0;
            }
        }

        return maxContiguousTerritories;
    }

    /**
     * Get the number of dices on a territory
     *
     * @param coordinatesTerritoire Coordinates of the territory
     * @return The number of dice present
     */
    public int getNbDiceOnTerritoire(Coordinates coordinatesTerritoire) {
        return this.nbDiceByTerritoire.get(coordinatesTerritoire);
    }

    /**
     * Get the owner of a territory
     *
     * @param coordinatesTerritoire Coordinates of the territory
     * @return The owner (type Joueur)
     */
    public Joueur getOwnerTerritoire(Coordinates coordinatesTerritoire) {
        return this.ownerTerritoire.get(coordinatesTerritoire);
    }

    /**
     * Get the two dimensions array representing territories
     *
     * @return The two dimensions array
     */
    public boolean[][] getTerritoires() {
        return this.territoires;
    }

    /**
     * Get the number of territories player can play with
     *
     * @return The number of territories
     */
    public int getNbTerritoiresPlayable() {
        int nbTerritoires = 0;

        for (boolean[] territoiresRow : this.territoires) {
            for (boolean territoire : territoiresRow) {
                if (territoire)
                    nbTerritoires++;
            }
        }

        return nbTerritoires;
    }

    /**
     * Get total size of the map
     *
     * @return The size of the map
     */
    public int getSize() {
        return this.size;
    }

    //endregion

    //region Override

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                if (this.territoires[y][x]) {
                    //stringBuilder.append(this.ownerTerritoire.get(new Coordinates(x, y)).getId());
                    stringBuilder.append(" - ");
                    //stringBuilder.append(this.nbDiceByTerritoire.get(new Coordinates(x, y)));
                } else {
                    stringBuilder.append("  #  ");
                }

                stringBuilder.append(" | ");
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }

    //endregion
}
