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

        this.createMap(new Coordinates(this.random.nextInt(this.size), this.random.nextInt(this.size)));
        this.verifyMap(nbPlayer);

    }

    //endregion

    //region Utils

    private void verifyMap(int nbPlayer) {
        if (this.getNbTerritoiresPlayable() < nbPlayer || this.getNbTerritoiresPlayable() % nbPlayer != 0) {
            try {
                for (boolean[] territoire : this.territoires) {
                    Arrays.fill(territoire, false);
                }
                this.createMap(new Coordinates(this.random.nextInt(this.size), this.random.nextInt(this.size)));
                this.verifyMap(nbPlayer);
            } catch (StackOverflowError e) {
                System.gc();
            }
        }
    }

    /*private void createMap() {

        int remove = 0;

        ArrayList<Coordinates> playableTerritories = new ArrayList<>();
        ArrayList<Coordinates> visitedTerritories = new ArrayList<>();

        for (int y = 0; y < this.territoires.length; y++) {
            for (int x = 0; x < this.territoires[y].length; x++) {
                this.territoires[y][x] = true;
                playableTerritories.add(new Coordinates(x, y));
            }
        }

        int totalNbTerritories = this.size * this.size;
        int nbTerritoriesToRemove = (int) (totalNbTerritories - (totalNbTerritories * this.density));

        System.out.println("totalNbTerritories : " + totalNbTerritories);
        System.out.println("nbTerritoriesToRemove : " + nbTerritoriesToRemove);

        while (nbTerritoriesToRemove > 0) {

            Coordinates designatedTerritory = playableTerritories.get(this.random.nextInt(playableTerritories.size()));
            visitedTerritories.add(designatedTerritory);
            HashSet<Coordinates> neighborsPlayable = this.getNeighborsPlayable(designatedTerritory);

            System.out.println(nbTerritoriesToRemove);


            for (Coordinates neighbor : neighborsPlayable) {
                HashSet<Coordinates> neighborsPlayableOfNeighborsPlayable = this.getNeighborsPlayable(neighbor);
                neighborsPlayableOfNeighborsPlayable.removeAll(visitedTerritories);

                if (neighborsPlayableOfNeighborsPlayable.size() > 2) {
                    remove++;
                    //System.out.println(remove);
                    this.territoires[designatedTerritory.getY()][designatedTerritory.getX()] = false;
                    playableTerritories.remove(designatedTerritory);
                    nbTerritoriesToRemove--;
                    break;
                }

            }
        }

        System.out.println(this);
    }*/


    private void createMap(Coordinates startingPoint) {

        ArrayList<Coordinates> surroundingTerritories = this.getSurroundingTerritories(startingPoint);
        surroundingTerritories.removeAll(this.getNeighborsPlayable(startingPoint));

        if (!surroundingTerritories.isEmpty()) {
            this.territoires[startingPoint.getY()][startingPoint.getX()] = true;

            int index = (this.random.nextInt(surroundingTerritories.size()));
            Coordinates nextTerritory = surroundingTerritories.get(index);

            for (Coordinates designatedTerritory : surroundingTerritories) {

                if (this.getNeighborsPlayable(designatedTerritory).size() < Math.min((this.density * 10), 9)) {
                    this.createMap(nextTerritory);
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

    protected ArrayList<Coordinates> getSurroundingTerritories(Coordinates coordinatesTerritoire) {
        ArrayList<Coordinates> surrounding = new ArrayList<>();

        int row = coordinatesTerritoire.getY();
        int column = coordinatesTerritoire.getX();

        //Search upper, at the same level and lower
        for (int y = Math.max(0, row - 1); y <= Math.min(this.size - 1, row + 1); y++) {

            //Search left, in the middle and right
            for (int x = Math.max(0, column - 1); x <= Math.min(this.size - 1, column + 1); x++) {

                //If it's not the origin territory
                if (x != column || y != row) {
                    surrounding.add(new Coordinates(x, y));
                }
            }
        }

        return surrounding;
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
                if ((x != column || y != row) && this.territoires[y][x]) {
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
