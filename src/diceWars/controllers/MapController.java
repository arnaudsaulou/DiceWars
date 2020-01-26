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

public class MapController extends AbstractController implements ActionListener {

    private final Carte carte;
    private final MapView mapView;

    public MapController(AbstractModel carte, AbstractView mapView) {
        super(carte, mapView);
        this.carte = (Carte) this.getModel();
        this.mapView = (MapView) this.getView();
        this.setUpMapView();
    }

    private void setUpMapView() {
        for (int y = -1; y < this.carte.getSize(); y++) {
            for (int x = -1; x < this.carte.getSize(); x++) {

                if (y == -1) {
                    if (x != -1) {
                        JLabel header = new JLabel(String.valueOf(x + 1));
                        header.setHorizontalAlignment(SwingConstants.CENTER);
                        header.setBackground(Color.WHITE);
                        header.setOpaque(true);
                        this.mapView.getDisplayableViewPanel().add(header);
                    } else {
                        this.mapView.getDisplayableViewPanel().add(new JPanel());
                    }
                } else {
                    if (x == -1) {
                        JLabel header = new JLabel(String.valueOf(y + 1));
                        header.setHorizontalAlignment(SwingConstants.CENTER);
                        header.setBackground(Color.WHITE);
                        header.setOpaque(true);
                        this.mapView.getDisplayableViewPanel().add(header);
                    } else {

                        if (!this.carte.getTerritoires()[y][x]) {
                            UnplayableTerritory unplayableTerritory = new UnplayableTerritory();
                            this.mapView.getDisplayableViewPanel().add(unplayableTerritory);
                        } else {
                            Coordinates coordinates = new Coordinates(x, y);
                            TerritoryView territoryView =
                                    new TerritoryView(
                                            coordinates,
                                            String.valueOf(this.carte.getNbDiceOnTerritoire(coordinates))
                                    );
                            territoryView.addActionListener(this);
                            territoryView.setBackground(this.carte.getOwnerTerritoire(coordinates).getColorPlayer());
                            this.mapView.getDisplayableViewPanel().add(territoryView);
                        }
                    }
                }
            }
        }
    }

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //Define what to do if clicked
        if (actionEvent.getSource() instanceof TerritoryView) {
            TerritoryView source = (TerritoryView) actionEvent.getSource();
            DiceWars.applicationController.getGameController().registerCoordinates(source.getCoordinates());
        }
    }
}
