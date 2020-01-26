package diceWars;

import diceWars.controllers.ApplicationController;

public class DiceWars {

    public static ApplicationController applicationController;
    private static int size;
    private static float density;

    public static void main(String[] args) {

        try {
            size = Integer.parseInt(args[0]);
            density = Float.parseFloat(args[1]);
            if (size > 2 && density > 0)
                applicationController = new ApplicationController();
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Lancement impossible : Format des paramètres invalide");
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.err.println("Lancement impossible : Il manque un ou des paramètres");
        }

    }

    public static int getSize() {
        return size;
    }

    public static float getDensity() {
        return density;
    }
}
