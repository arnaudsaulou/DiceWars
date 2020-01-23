package diceWars.views;

import javax.swing.*;
import java.awt.*;

public class JPanelWithBackground extends JPanel {

    private Image image;
    private boolean backgroundDisplayed;

    public JPanelWithBackground(Image background) {
        this.image = background;
        this.backgroundDisplayed = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!this.backgroundDisplayed) {
            this.image = this.image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            this.backgroundDisplayed = true;
        }
        g.drawImage(this.image, 0, 0, null);

    }
}
