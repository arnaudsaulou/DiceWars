package diceWars.controllers;

import diceWars.DiceWars;
import diceWars.models.AbstractModel;
import diceWars.models.Carte;
import diceWars.models.Coordinates;
import diceWars.views.AbstractView;
import diceWars.views.MapView;
import diceWars.views.TerritoryView;
import diceWars.views.UnplayableTerritory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller of the model Carte and view MapView
 */
public class MapController extends AbstractController implements ActionListener {

    //region Variables

    private final Carte carte;
    private final MapView mapView;

    //endregion

    //region Constructor

    public MapController(AbstractModel carte, AbstractView mapView) {
        super(carte, mapView);
        this.carte = (Carte) this.getModel();
        this.mapView = (MapView) this.getView();
        this.constructMapView();
    }

    //endregion

    //region Utils

    /**
     * Construct a map of territoryView based on the territoires 2D array
     */
    private void constructMapView() {
        for (int y = -1; y < this.carte.getSize(); y++) {
            for (int x = -1; x < this.carte.getSize(); x++) {
                if (y == -1) {
                    this.constructFirstRow(x);
                } else {
                    this.constructRow(y, x);
                }
            }
        }
    }

    /**
     * Construct the first row of the map view (Empty space followed by columns headers)
     *
     * @param x The columns number
     */
    private void constructFirstRow(int x) {
        if (x != -1) {
            this.addHeader(x);
        } else {
            //Insert a empty space in the top left corner
            this.mapView.getDisplayableViewPanel().add(new JPanel());
        }
    }

    /**
     * Construct the row number y of the map view (Row headers followed by territories view)
     *
     * @param x The columns number
     */
    private void constructRow(int y, int x) {
        if (x == -1) {
            this.addHeader(y);
        } else {
            //If the territory is playable add a button, add a empty panel else
            if (this.carte.getTerritoires()[y][x]) {
                this.addTerritory(y, x);
            } else {
                this.addUnplayableTerritory();
            }
        }
    }

    /**
     * Add an unplayable territory to the mapView
     */
    private void addUnplayableTerritory() {
        this.mapView.getDisplayableViewPanel().add(new UnplayableTerritory());
    }

    /**
     * Add a TerritoryView (button) at the specified coordinates
     *
     * @param y The vertical index
     * @param x The horizontal index
     */
    private void addTerritory(int y, int x) {

        //Construct the TerritoryView
        Coordinates coordinates = new Coordinates(x, y);
        TerritoryView territoryView =
                new TerritoryView(
                        coordinates,
                        String.valueOf(this.carte.getNbDiceOnTerritoire(coordinates))
                );

        //Register the listener, set the background color, and add the button to the mapView
        territoryView.addActionListener(this);
        territoryView.setBackground(this.carte.getOwnerTerritoire(coordinates).getColorPlayer());
        this.mapView.getDisplayableViewPanel().add(territoryView);
    }

    /**
     * Add a header to the mapView
     *
     * @param l the text to display
     */
    private void addHeader(int l) {
        JLabel header = new JLabel(String.valueOf(l + 1));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBackground(Color.WHITE);
        header.setOpaque(true);
        this.mapView.getDisplayableViewPanel().add(header);
    }

    /**
     * Updates all TerritoryView of the mapView to match the model
     */
    public void updateMap() {
        TerritoryView territoryView;
        Coordinates coordinates;

        for (Component component : this.mapView.getDisplayableViewPanel().getComponents()) {
            if (component instanceof TerritoryView) {
                territoryView = (TerritoryView) component;
                coordinates = territoryView.getCoordinates();
                territoryView.setText(String.valueOf(this.carte.getNbDiceOnTerritoire(coordinates)));
                territoryView.setBackground(this.carte.getOwnerTerritoire(coordinates).getColorPlayer());
            }
        }
    }

    //endregion

    //region Override

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //Define what to do if a TerritoryView clicked
        if (actionEvent.getSource() instanceof TerritoryView) {
            TerritoryView source = (TerritoryView) actionEvent.getSource();
            DiceWars.applicationController.getGameController().registerCoordinates(source.getCoordinates());
        }
    }

    //endregion
}
