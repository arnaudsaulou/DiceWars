package diceWars.views;

import diceWars.models.Carte;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class MapView extends AbstractView {

    private final Carte carte;
    private final JPanel JPanel;

    public MapView(Carte carte) {
        this.carte = carte;
        this.JPanel = new JPanel();
        this.JPanel.setBorder(BorderFactory.createEtchedBorder());
        GridLayout grid = new GridLayout(this.carte.getHeight() + 1, this.carte.getWidht() + 1, 2, 2);
        this.JPanel.setLayout(grid);
        this.setViewPanel(this.JPanel);
    }

    private void paintBackgroundImage() {
        Graphics g = this.JPanel.getGraphics();
        try {
            BufferedImage grass = ImageIO.read(new FileInputStream("res/textures/warezone.jpg"));
            Image scaledImage = grass.getScaledInstance(this.JPanel.getWidth(), this.JPanel.getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(scaledImage, 0, 0, null);
            grass.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.JPanel.paintComponents(g);
    }
}
