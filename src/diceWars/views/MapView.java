package diceWars.views;

import diceWars.models.Carte;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class MapView extends AbstractView {

    private Image imgBackground;

    public MapView(Carte carte) {

        try {
            BufferedImage grass = ImageIO.read(new FileInputStream("res/textures/grass.jpg"));
            ImageIcon icon = new ImageIcon(grass);
            this.imgBackground = icon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanelWithBackground JPanel = new JPanelWithBackground(this.imgBackground);
        JPanel.setBorder(BorderFactory.createEtchedBorder());

        GridLayout grid = new GridLayout(carte.getHeight() + 1, carte.getWidht() + 1, 2, 2);
        JPanel.setLayout(grid);

        this.setViewPanel(JPanel);
    }

}
