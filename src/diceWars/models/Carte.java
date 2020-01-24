package diceWars.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

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

    public Carte(int size, float density, int nbPlayer) {
        this.size = size;
        this.density = density;
        this.territoires = new boolean[this.size][this.size];
        this.nbDiceByTerritoire = new HashMap<>();
        this.ownerTerritoire = new HashMap<>();
        this.random = new Random();
        this.createMap(0, null);
        this.verifyMap(nbPlayer);
    }

    //endregion

    //region Utils

    private void verifyMap(int nbPlayer) {
        if (this.getNbTerritoiresPlayable() < nbPlayer) {
            throw new IllegalStateException("Plus de joueur que de territoires");
        } else if (this.getNbTerritoiresPlayable() % nbPlayer != 0) {
            throw new IllegalStateException("Pas multiple");
            //TODO Add or remove territories

        }
    }

    private void createMap(int pass, ArrayList<Coordinates> lastPassPlayableTerritories) {

        if (pass < this.size) {

            HashSet<Coordinates> surroundingTerritories = new HashSet<>();

            if (lastPassPlayableTerritories != null) {
                for (Coordinates territoire : lastPassPlayableTerritories) {
                    surroundingTerritories.addAll(getSurroundingTerritories(territoire));
                }
            }

            ArrayList<Coordinates> potentialPlayableTerritories =
                    this.potentialPlayableTerritories(new Coordinates(pass, pass), surroundingTerritories);

            ArrayList<Coordinates> playableTerritories =
                    this.distributePlayableTerritories(potentialPlayableTerritories);

            pass = pass + 1;
            this.createMap(pass, playableTerritories);
        }
    }

    private ArrayList<Coordinates> potentialPlayableTerritories(
            Coordinates startingPoint, HashSet<Coordinates> surroundingTerritories) {

        ArrayList<Coordinates> potentialPlayableTerritories = new ArrayList<>();
        potentialPlayableTerritories.add(startingPoint);

        int x = startingPoint.getX();
        int y = startingPoint.getY();

        while (x > 0) {
            x = x - 1;
            potentialPlayableTerritories.add(new Coordinates(x, startingPoint.getY()));
        }

        while (y > 0) {
            y = y - 1;
            potentialPlayableTerritories.add(new Coordinates(startingPoint.getX(), y));
        }


        if (surroundingTerritories.size() > 0) {
            HashSet<Coordinates> oneMustBePlayable = new HashSet<>(potentialPlayableTerritories);
            oneMustBePlayable.retainAll(surroundingTerritories);
            return new ArrayList<>(oneMustBePlayable);
        } else {
            return potentialPlayableTerritories;
        }


    }

    private ArrayList<Coordinates> distributePlayableTerritories(
            ArrayList<Coordinates> potentialPlayableTerritories) {

        ArrayList<Coordinates> playableTerritories = new ArrayList<>();

        boolean aSurroundingTerritoryIsAccessible = false;
        boolean createTerritory;
        for (Coordinates territory : potentialPlayableTerritories) {
            createTerritory = createTerritoryOrNot();

            if (createTerritory) {
                this.territoires[territory.getX()][territory.getY()] = true;
                aSurroundingTerritoryIsAccessible = true;
                playableTerritories.add(territory);
            }
        }

        if (!aSurroundingTerritoryIsAccessible) {
            Coordinates designatedTerritory =
                    potentialPlayableTerritories.get(this.random.nextInt(potentialPlayableTerritories.size()));

            this.territoires[designatedTerritory.getX()][designatedTerritory.getY()] = true;
            playableTerritories.add(designatedTerritory);
        }

        return playableTerritories;
    }

    private boolean createTerritoryOrNot() {
        return this.random.nextFloat() <= this.density;
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
                this.getTerritoires()[coordinates.getX()][coordinates.getY()];
    }

    public void reset() {
        this.nbDiceByTerritoire.clear();
        this.ownerTerritoire.clear();
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
        this.nbDiceByTerritoire.remove(coordinatesTerritoire);
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

    protected HashSet<Coordinates> getSurroundingTerritories(Coordinates coordinatesTerritoire) {
        HashSet<Coordinates> neighbours = new HashSet<>();

        int row = coordinatesTerritoire.getY();
        int column = coordinatesTerritoire.getX();

        //Search upper, at the same level and lower
        for (int y = Math.max(0, row - 1); y <= Math.min(this.size - 1, row + 1); y++) {

            //Search left, in the middle and right
            for (int x = Math.max(0, column - 1); x <= Math.min(this.size - 1, column + 1); x++) {

                //If it's not the origin territory and the terittoires is playable
                if ((x != column || y != row)) {
                    neighbours.add(new Coordinates(x, y));
                }
            }
        }

        return neighbours;
    }

    /**
     * Get coordinates of playable neighbors territories
     *
     * @param coordinatesTerritoire Coordinates of the origin territory
     * @return ArrayList containing all coordinates of playable neighbors territories
     */
    protected HashSet<Coordinates> getNeighborsPlayable(Coordinates coordinatesTerritoire) {
        HashSet<Coordinates> neighbours = new HashSet<>();

        int row = coordinatesTerritoire.getY();
        int column = coordinatesTerritoire.getX();

        //Search upper, at the same level and lower
        for (int y = Math.max(0, row - 1); y <= Math.min(this.size - 1, row + 1); y++) {

            //Search left, in the middle and right
            for (int x = Math.max(0, column - 1); x <= Math.min(this.size - 1, column + 1); x++) {

                //If it's not the origin territory and the terittoires is playable
                if ((x != column || y != row) && this.territoires[x][y]) {
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
    protected ArrayList<Coordinates> getTerritoiresOfPlayer(Joueur joueur) {

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
    private int computeContiguousTerritories(Coordinates
                                                     coordinatesTerritoire, HashSet<Coordinates> territoiresVisited) {

        int maxContiguousTerritories = 0;

        HashSet<Coordinates> neighbors = this.getNeighborsPlayable(coordinatesTerritoire);

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
                if (this.territoires[x][y]) {
                    stringBuilder.append(this.ownerTerritoire.get(new Coordinates(x, y)).getId());
                    stringBuilder.append(" - ");
                    stringBuilder.append(this.nbDiceByTerritoire.get(new Coordinates(x, y)));
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
