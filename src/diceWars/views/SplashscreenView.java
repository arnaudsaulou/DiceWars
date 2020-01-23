package diceWars.views;

import diceWars.DiceWars;
import diceWars.interfaces.Displayable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class SplashscreenView extends JPanel implements Displayable {

    private Image imgBackground;  //variable de l'image de fond  afficher

    public SplashscreenView() {

        Timer timer = new Timer(3000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                DiceWars.applicationController.changeView(DiceWars.applicationController.getMenuController().getView());
            }
        });

        timer.setRepeats(false);
        timer.start();

        try {
            BufferedImage splashscreen = ImageIO.read(new FileInputStream("res/images/splashscreen.jpg"));
            ImageIcon icon = new ImageIcon(splashscreen);
            this.imgBackground = icon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.imgBackground, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public JPanel getDisplayableViewPanel() {
        return this;
    }
}
