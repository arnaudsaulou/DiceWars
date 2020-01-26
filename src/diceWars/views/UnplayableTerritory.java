package diceWars.views;

import javax.swing.*;
import java.awt.*;
public class UnplayableTerritory extends JPanel {

    //region Constants

    private static final Color UNPLAYABLE_COLOR = new Color(0, 0, 0, 55);

    //endregion

    //region Constructor

    public UnplayableTerritory() {
        this.setBackground(UNPLAYABLE_COLOR);
    }

    //endregion

}
