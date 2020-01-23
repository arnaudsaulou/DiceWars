package diceWars.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ApplicationView extends JFrame {

    public ApplicationView(String title) throws HeadlessException {
        super(title);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //this.setUndecorated(true);
        this.setupEscapeKeyPressedDetection();
        this.setPreferredSize(new Dimension(500, 500));
        this.setVisible(true);
    }

    private void setupEscapeKeyPressedDetection() {
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        JRootPane rootPane = new JRootPane();
        rootPane.registerKeyboardAction(action, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.setRootPane(rootPane);
    }

    public void changeContentPane(Container contentPane) {
        this.getContentPane().removeAll();
        this.getContentPane().add(contentPane);
        this.getContentPane().repaint();
        this.getContentPane().validate();
    }
}
