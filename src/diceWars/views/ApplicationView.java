package diceWars.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ApplicationView extends JFrame {

    //region Constants

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    //endregion

    //region Constructor

    public ApplicationView() throws HeadlessException {
        super("Dice Ware");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setupEscapeKeyPressedDetection();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        //Display the splashscreen on startup
        this.setContentPane(new SplashscreenView());

        this.setVisible(true);
    }

    //endregion

    //region Utils

    /**
     * Register keyboard listener on escape key to stop the game
     */
    private void setupEscapeKeyPressedDetection() {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        ActionListener action = e -> System.exit(0);

        JRootPane rootPane = new JRootPane();
        rootPane.registerKeyboardAction(action, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.setRootPane(rootPane);
    }

    /**
     * Change content pane for the new one pass in parameter
     *
     * @param contentPane The new content pane
     */
    public void changeContentPane(Container contentPane) {
        this.getContentPane().removeAll();
        this.setContentPane(contentPane);
        this.getContentPane().repaint();
        this.getContentPane().revalidate();
    }

    //endregion
}
