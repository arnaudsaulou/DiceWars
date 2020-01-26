package diceWars.views;

import diceWars.models.Carte;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class MapView extends AbstractView {

    //region Variables

    private Image imgBackground;

    //endregion

    //region Constructor

    public MapView(Carte carte) {

        //Try to get the background image
        try {
            BufferedImage grass = ImageIO.read(new FileInputStream("res/textures/grass_2.png"));
            ImageIcon icon = new ImageIcon(grass);
            this.imgBackground = icon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Add elements background + grid that compose the view
        JPanelWithBackground JPanel = new JPanelWithBackground(this.imgBackground);
        JPanel.setBorder(BorderFactory.createEtchedBorder());
        GridLayout grid = new GridLayout(carte.getSize() + 1, carte.getSize() + 1, 2, 2);
        JPanel.setLayout(grid);

        this.setViewPanel(JPanel);
    }

    //endregion

}
