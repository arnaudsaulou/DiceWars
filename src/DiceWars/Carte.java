package DiceWars;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Carte {

    //region Variables

    private int height;
    private int widht;
    private boolean[][] territoires;
    private HashMap<Coordinates, Integer> nbDiceByTerritoire;   //Coordinates <> NbDice
    private HashMap<Coordinates, Joueur> ownerTerritoire;       //Coordinates <> Joueur

    //endregion

    //region Constructor

    public Carte(int height, int widht) {
        this.height = height;
        this.widht = widht;
        this.territoires = new boolean[this.height][this.widht];
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

        try {
            Path path = Paths.get(carteToImportPath);
            long lineCount = Files.lines(path).count();
            Scanner scanner = new Scanner(path);
            System.out.println("\nImportation en cours !");

            int lineNumber = 1;
            while (scanner.hasNext()) {
                System.out.println("Importation : [ " + lineNumber + " / " + lineCount + " ]");
                String[] territoireArray = scanner.nextLine().split(";");

                //Set a territoires
                this.territoires[Integer.parseInt(territoireArray[0])][Integer.parseInt(territoireArray[1])] = true;

                lineNumber++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println();
        } catch (InputMismatchException e) {
            System.err.println("Format de carte invalide");
        } catch (IOException e) {
            System.err.println("Fichier introuvable");
        }
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
        this.nbDiceByTerritoire.put(coordinatesTerritoire, nbDice);
    }

    /**
     * Set a Joueur as the owner of the territory
     *
     * @param coordinatesTerritoire Coordinates of the territory
     * @param owner                 The owner to set
     */
    protected void setOwnerTerritoire(Coordinates coordinatesTerritoire, Joueur owner) {
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
    protected ArrayList<Coordinates> getNeighbors(Coordinates coordinatesTerritoire) {
        ArrayList<Coordinates> neighbours = new ArrayList<>();

        int row = coordinatesTerritoire.getX();
        int column = coordinatesTerritoire.getY();

        //Search upper, at the same level and lower
        for (int x = Math.max(0, row - 1); x <= Math.min(this.height - 1, row + 1); x++) {

            //Search left, in the middle and right
            for (int y = Math.max(0, column - 1); y <= Math.min(this.widht - 1, column + 1); y++) {

                //If it's not the origin territory
                if (x != row || y != column) {
                    neighbours.add(new Coordinates(x, y));
                }
            }
        }

        return neighbours;
    }

    /**
     * Get the number of dices on a territory
     *
     * @param coordinatesTerritoire Coordinates of the territory
     * @return The number of dice present
     */
    protected int getNbDiceOnTerritoire(Coordinates coordinatesTerritoire) {
        return this.nbDiceByTerritoire.get(coordinatesTerritoire);
    }

    /**
     * Get the owner of a territory
     *
     * @param coordinatesTerritoire Coordinates of the territory
     * @return The owner (type Joueur)
     */
    protected Joueur getOwnerTerritoire(Coordinates coordinatesTerritoire) {
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

        for (int x = 0; x < this.widht; x++) {
            for (int y = 0; y < this.height; y++) {
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