package DiceWars;

import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Carte {

    private int height;
    private int widht;
    private boolean[][] territoires;
    private HashMap<Pair<Integer, Integer>, Integer> nbDiceByTerritoire; //IdTerritoire <> NbDice
    private HashMap<Pair<Integer, Integer>, Integer> ownerTerritoire; //IdTerritoire <> IdJoueur

    public Carte(int height, int widht) {
        this.height = height;
        this.widht = widht;
        this.territoires = new boolean[this.height][this.widht];
        this.nbDiceByTerritoire = new HashMap<>();
        this.ownerTerritoire = new HashMap<>();
    }

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
            System.err.println("");
        } catch (InputMismatchException e) {
            System.err.println("Format de carte invalide");
        } catch (IOException e) {
            System.err.println("Fichier introuvable");
        }
    }


    protected ArrayList<Pair<Integer, Integer>> getNeighbours(Pair<Integer, Integer> idTerritoire) {
        ArrayList<Pair<Integer, Integer>> neighbours = new ArrayList<>();

        int row = idTerritoire.getKey();
        int column = idTerritoire.getValue();

        for (int y = Math.max(0, row - 1); y <= Math.min(this.height - 1, row + 1); y++) {
            for (int x = Math.max(0, column - 1); x <= Math.min(this.widht - 1, column + 1); x++) {
                if (x != column || y != row) {
                    neighbours.add(new Pair<>(y, x));
                }
            }
        }

        return neighbours;
    }

    protected int getNbDiceOnTerritoire(Pair<Integer, Integer> idTerritoire) {
        return this.nbDiceByTerritoire.get(idTerritoire);
    }

    protected void setNbDiceOnTerritoire(Pair<Integer, Integer> idTerritoire, int nbDice) {
        this.nbDiceByTerritoire.put(idTerritoire, nbDice);
    }

    protected int getOwnerTerritoire(Pair<Integer, Integer> idTerritoire) {
        return this.ownerTerritoire.get(idTerritoire);
    }

    protected void setOwnerTerritoire(Pair<Integer, Integer> idTerritoire, int idOwner) {
        this.ownerTerritoire.put(idTerritoire, idOwner);
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (boolean[] rowTerritoires : this.territoires) {
            for (Boolean territoire : rowTerritoires) {
                stringBuilder.append(territoire);
                stringBuilder.append(" | ");
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
