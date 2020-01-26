package diceWars.views;

import javax.swing.*;
import java.awt.*;

public class JPanelWithBackground extends JPanel {

    //region Variables

    private Image image;
    private boolean backgroundDisplayed;

    //endregion

    //region Constructor

    public JPanelWithBackground(Image background) {
        this.image = background;
        this.backgroundDisplayed = false;
    }

    //endregion

    //region Override

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //If the background as never been displayed, scaled it
        if (!this.backgroundDisplayed) {
            this.image = this.image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            this.backgroundDisplayed = true;
        }

        g.drawImage(this.image, 0, 0, null);

    }

    //endregion
}
