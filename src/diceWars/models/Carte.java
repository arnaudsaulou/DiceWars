package diceWars.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Carte extends AbstractModel {

    //region Variables

    private final int height;
    private final int widht;
    private final boolean[][] territoires;
    private final HashMap<Coordinates, Integer> nbDiceByTerritoire;   //Coordinates <> NbDice
    private final HashMap<Coordinates, Joueur> ownerTerritoire;       //Coordinates <> Joueur

    //endregion

    //region Constructor

    public Carte(int widht, int height) {
        this.height = height;
        this.widht = widht;
        this.territoires = new boolean[this.widht][this.height];
        this.nbDiceByTerritoire = new HashMap<>();
        this.ownerTerritoire = new HashMap<>();
    }

    //endregion

    //region Utils

    /**
     * Import a carte from a CSV file.
     *
     * @param carteToImportPath The path of the file to import
     */
    protected void importCarteFromCSV(String carteToImportPath) {

        Scanner scanner = null;

        try {
            Path path = Paths.get(carteToImportPath);
            long lineCount = Files.lines(path).count();
            scanner = new Scanner(path);

            System.out.println("\nImportation en cours !");

            int lineNumber = 1;
            while (scanner.hasNext()) {
                this.extractDataFromLineRead(scanner, lineCount, lineNumber);
                lineNumber++;
            }
            System.out.println("Importation réussie !");

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println();
        } catch (InputMismatchException e) {
            System.err.println("Format de carte invalide");
        } catch (IOException e) {
            System.err.println("Fichier introuvable");
        } finally {
            assert scanner != null;
            scanner.close();
        }
    }

    /**
     * Extract data from the read line and make the corresponding territory playable (territoires[x][y] = True)
     *
     * @param scanner    The scanner object that read the CSV file
     * @param lineCount  The current line read
     * @param lineNumber The total number of line to read
     */
    private void extractDataFromLineRead(Scanner scanner, long lineCount, int lineNumber) {
        System.out.println("Importation : [ " + lineNumber + " / " + lineCount + " ]");
        String[] territoireArray = scanner.nextLine().split(";");

        //Set a territoires
        this.territoires[Integer.parseInt(territoireArray[0])][Integer.parseInt(territoireArray[1])] = true;
    }

    /**
     * Check if a coordinates are valid : in the dimensions of the map and on a playable territory
     *
     * @param coordinates Coordinates to check
     * @return A boolean, true if coordinates ok, false else
     */
    public boolean isCoordinatesValid(Coordinates coordinates) {
        return coordinates.getX() >= 0 &&
                coordinates.getX() < this.widht &&
                coordinates.getY() >= 0 &&
                coordinates.getY() < this.height &&
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

    /**
     * Get coordinates of neighbors territories
     *
     * @param coordinatesTerritoire Coordinates of the origin territory
     * @return ArrayList containing all coordinates of neighbors territories
     */
    protected HashSet<Coordinates> getNeighbors(Coordinates coordinatesTerritoire) {
        HashSet<Coordinates> neighbours = new HashSet<>();

        int row = coordinatesTerritoire.getY();
        int column = coordinatesTerritoire.getX();

        //Search upper, at the same level and lower
        for (int y = Math.max(0, row - 1); y <= Math.min(this.height - 1, row + 1); y++) {

            //Search left, in the middle and right
            for (int x = Math.max(0, column - 1); x <= Math.min(this.widht - 1, column + 1); x++) {

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
    private int computeContiguousTerritories(Coordinates coordinatesTerritoire, HashSet<Coordinates> territoiresVisited) {

        int maxContiguousTerritories = 0;

        HashSet<Coordinates> neighbors = this.getNeighbors(coordinatesTerritoire);

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
     * Get total height of the map
     *
     * @return The height of the map
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get total width of the map
     *
     * @return The width of the map
     */
    public int getWidht() {
        return widht;
    }

    //endregion

    //region Override

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.widht; x++) {
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
