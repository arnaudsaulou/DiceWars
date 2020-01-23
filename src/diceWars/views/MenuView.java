package diceWars.views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class MenuView extends AbstractView {

    private final JLabel jLabelJouerSolo;
    private final JLabel jLabelJouerMulti;
    private final JLabel jLabelQuitter;
    private Image imgBackground;

    public MenuView() {

        try {
            BufferedImage splashscreen = ImageIO.read(new FileInputStream("res/images/backgroundMenu.jpg"));
            ImageIcon icon = new ImageIcon(splashscreen);
            this.imgBackground = icon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanelWithBackground menuViewPanel = new JPanelWithBackground(this.imgBackground);
        //JPanel menuViewPanel = new JPanel();

        JPanel jPanel_titre = new JPanel();
        jPanel_titre.setPreferredSize(new Dimension(menuViewPanel.getWidth(), 500));
        jPanel_titre.setLayout(new BoxLayout(jPanel_titre, BoxLayout.PAGE_AXIS));
        jPanel_titre.setOpaque(false);

        JPanel jPanel_blanc = new JPanel();
        jPanel_blanc.setPreferredSize(new Dimension(menuViewPanel.getWidth(), 350));
        jPanel_blanc.setOpaque(false);

        //Titre de la vue
        JLabel jLabel_titre = new JLabel("Nouvelle Partie");
        jLabel_titre.setForeground(Color.WHITE);
        jLabel_titre.setFont(new Font("Arial Black", Font.BOLD, 50));
        jLabel_titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel_titre.setPreferredSize(new Dimension(menuViewPanel.getWidth(), 150));

        jPanel_titre.add(jPanel_blanc);
        jPanel_titre.add(jLabel_titre);

        JPanel jPanel_menu = new JPanel();
        jPanel_menu.setLayout(new BoxLayout(jPanel_menu, BoxLayout.PAGE_AXIS));

        //Boutton pour jouer en solo le jeu
        this.jLabelJouerSolo = new JLabel("SOLO");
        this.jLabelJouerSolo.setForeground(Color.WHITE);
        this.jLabelJouerSolo.setFont(new Font("Arial Black", Font.BOLD, 30));
        this.jLabelJouerSolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel_menu.add(this.jLabelJouerSolo);


        //Boutton pour jouer en multi le jeu
        this.jLabelJouerMulti = new JLabel("MULTI");
        this.jLabelJouerMulti.setForeground(Color.WHITE);
        this.jLabelJouerMulti.setFont(new Font("Arial Black", Font.BOLD, 30));
        this.jLabelJouerMulti.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel_menu.add(this.jLabelJouerMulti);


        //Boutton pour quitter le jeu
        this.jLabelQuitter = new JLabel("QUITTER");
        this.jLabelQuitter.setForeground(Color.WHITE);
        this.jLabelQuitter.setFont(new Font("Arial Black", Font.BOLD, 30));
        this.jLabelQuitter.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel_menu.add(this.jLabelQuitter);

        jPanel_menu.setOpaque(false);
        jPanel_menu.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuViewPanel.setLayout(new BorderLayout());
        menuViewPanel.add(jPanel_titre, BorderLayout.NORTH);
        menuViewPanel.add(jPanel_menu, BorderLayout.CENTER);

        this.setViewPanel(menuViewPanel);
    }

    //region Getter

    public JLabel getJLabelJouerSolo() {
        return jLabelJouerSolo;
    }

    public JLabel getJLabelJouerMulti() {
        return jLabelJouerMulti;
    }

    public JLabel getJLabelQuitter() {
        return jLabelQuitter;
    }

    //endregion
}

