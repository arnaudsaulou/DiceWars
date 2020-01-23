package diceWars.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogError extends JDialog {

    public DialogError(String errorMsg) {

        this.setUndecorated(true);

        JLabel title = new JLabel("Attaque impossible");
        title.setFont(new Font("ARIAL", Font.BOLD, 25));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));

        JLabel msg = new JLabel(errorMsg);
        msg.setFont(new Font("ARIAL", Font.PLAIN, 20));
        msg.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));

        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(title, BorderLayout.NORTH);
        borderPanel.add(msg, BorderLayout.CENTER);
        borderPanel.add(ok, BorderLayout.SOUTH);

        this.add(borderPanel);
        this.pack();
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
