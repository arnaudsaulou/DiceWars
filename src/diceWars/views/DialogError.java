package diceWars.views;

import javax.swing.*;
import java.awt.*;

public class DialogError extends JDialog {

    //region Constructor

    public DialogError(String errorMsg) {
        this.setUndecorated(true);
        this.add(this.constructDialogPanel(errorMsg));
        this.pack();
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    //endregion

    //region Utils

    /**
     * Construct a JPanel displaying the error message that occurred
     *
     * @param errorMsg The error message
     * @return The JPanel ready to use
     */
    private JPanel constructDialogPanel(String errorMsg) {
        JLabel title = new JLabel("Attaque impossible");
        title.setFont(new Font("ARIAL", Font.BOLD, 25));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));

        JLabel msg = new JLabel(errorMsg);
        msg.setFont(new Font("ARIAL", Font.PLAIN, 20));
        msg.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));

        JButton ok = new JButton("OK");
        ok.addActionListener(actionEvent -> dispose());

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(title, BorderLayout.NORTH);
        borderPanel.add(msg, BorderLayout.CENTER);
        borderPanel.add(ok, BorderLayout.SOUTH);

        return borderPanel;
    }

    //endregion
}
