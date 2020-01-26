package diceWars.controllers;

import diceWars.DiceWars;
import diceWars.models.AbstractModel;
import diceWars.models.Partie;
import diceWars.views.AbstractView;
import diceWars.views.ConfigDiceWars;
import diceWars.views.MenuView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Controller of the view Menu
 */
public class MenuController extends AbstractController implements MouseListener {

    //region Variables

    private final MenuView menuView;

    //endregion

    //region Constructor

    public MenuController(AbstractModel abstractModel, AbstractView menuView) {
        super(abstractModel, menuView);

        this.menuView = (MenuView) menuView;

        //Register listener on menu's buttons
        this.menuView.getJLabelJouerSolo().addMouseListener(this);
        this.menuView.getJLabelJouerMulti().addMouseListener(this);
        this.menuView.getJLabelQuitter().addMouseListener(this);
    }

    //endregion

    //region Override

    @Override
    public void mouseClicked(MouseEvent e) {
        //Quiter le jeu
        if (e.getSource() == this.menuView.getJLabelQuitter()) {
            DiceWars.applicationController.quit();
        }

        //Lancer le jeu en solo
        if (e.getSource() == this.menuView.getJLabelJouerSolo()) {
            DiceWars.applicationController.lunchPartie(Partie.MODE.SOLO, 1);
        }

        //Lancer le jeu en duo
        if (e.getSource() == this.menuView.getJLabelJouerMulti()) {
            new ConfigDiceWars();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this.menuView.getJLabelJouerSolo()) {
            this.menuView.getJLabelJouerSolo().setForeground(Color.DARK_GRAY);
            this.menuView.getJLabelJouerSolo().setFont(new Font("Arial Black", Font.BOLD, 40));
        }
        if (e.getSource() == this.menuView.getJLabelJouerMulti()) {
            this.menuView.getJLabelJouerMulti().setForeground(Color.DARK_GRAY);
            this.menuView.getJLabelJouerMulti().setFont(new Font("Arial Black", Font.BOLD, 40));
        }
        if (e.getSource() == this.menuView.getJLabelQuitter()) {
            this.menuView.getJLabelQuitter().setForeground(Color.DARK_GRAY);
            this.menuView.getJLabelQuitter().setFont(new Font("Arial Black", Font.BOLD, 40));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == this.menuView.getJLabelJouerSolo()) {
            this.menuView.getJLabelJouerSolo().setForeground(Color.WHITE);
            this.menuView.getJLabelJouerSolo().setFont(new Font("Arial Black", Font.BOLD, 30));
        }
        if (e.getSource() == this.menuView.getJLabelJouerMulti()) {
            this.menuView.getJLabelJouerMulti().setForeground(Color.WHITE);
            this.menuView.getJLabelJouerMulti().setFont(new Font("Arial Black", Font.BOLD, 30));
        }
        if (e.getSource() == this.menuView.getJLabelQuitter()) {
            this.menuView.getJLabelQuitter().setForeground(Color.WHITE);
            this.menuView.getJLabelQuitter().setFont(new Font("Arial Black", Font.BOLD, 30));
        }
    }

    //endregion
}
