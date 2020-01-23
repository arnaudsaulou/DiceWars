package diceWars.views;

import diceWars.models.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TerritoryView extends JButton {

    //region Constants

    private static final int ADDITIONAL_POINTS_FONT_SIZE = 3;

    //endregion

    //region Variables

    private final Coordinates coordinates;
    private String text;

    //endregion

    //region Constructor

    public TerritoryView(Coordinates coordinates, String pString) {
        super(pString);

        this.coordinates = coordinates;

        //Remove margin of buttons
        this.setMargin(new Insets(0, 0, 0, 0));
    }

    //endregion

    //region Getter

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    //endregion

    //region Override

    @Override
    protected void paintComponent(Graphics g) {
        //Adapt font size depending on button dimensions
        this.setFont(
                new Font("Arial", Font.PLAIN, this.getHeight() / 2 + ADDITIONAL_POINTS_FONT_SIZE
                ));
        super.paintComponent(g);
    }

    //endregion
}