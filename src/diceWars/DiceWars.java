package diceWars;

import diceWars.controllers.ApplicationController;
import diceWars.views.ModeSelectorView;

public class DiceWars {

    public static ApplicationController applicationController;

    public static void main(String[] args) {
        applicationController = new ApplicationController(args[0]);
        applicationController.changeView(new ModeSelectorView(applicationController));
    }
}
