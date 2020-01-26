package diceWars.views;

import diceWars.DiceWars;
import diceWars.interfaces.Displayable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class SplashscreenView extends JPanel implements Displayable {

    //region Constants

    private static final int SPLASHSCREEN_DELAY = 3000;

    //endregion

    //region Variables

    private Image imgBackground;

    //endregion

    //region Constructor

    public SplashscreenView() {

        //Try to get the splashscreen image
        try {
            BufferedImage splashscreen = ImageIO.read(new FileInputStream("res/images/splashscreen.jpg"));
            ImageIcon icon = new ImageIcon(splashscreen);
            this.imgBackground = icon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Remove splashscreen after SPLASHSCREEN_DELAY
        Timer timer = new Timer(SPLASHSCREEN_DELAY,
                arg0 -> DiceWars.applicationController.changeView(
                        DiceWars.applicationController.getMenuController().getView())
        );

        timer.setRepeats(false);
        timer.start();

        this.setVisible(true);
    }

    //endregion

    //region Override

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.imgBackground, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public JPanel getDisplayableViewPanel() {
        return this;
    }

    //endregion
}
