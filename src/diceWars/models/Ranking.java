package diceWars.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Ranking extends AbstractModel {

    //region Variables

    private final ArrayList<Joueur> joueurList;

    //endregion

    //region Constructor

    public Ranking(Comparator<Joueur> order, Collection<Joueur> collection) {
        this.joueurList = new ArrayList<>();
        this.joueurList.addAll(collection);
        this.joueurList.sort(order);
    }

    //endregion

    //region Getter

    public ArrayList<Joueur> getJoueurList() {
        return joueurList;
    }

    //endregion

}
