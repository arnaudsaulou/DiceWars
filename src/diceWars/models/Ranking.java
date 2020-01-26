package diceWars.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Ranking extends AbstractModel {

    //region Variables

    private final ArrayList<Joueur> joueurList;

    //endregion

    public Ranking(Comparator<Joueur> order, Collection<Joueur> collection) {
        this.joueurList = new ArrayList<>();
        this.joueurList.addAll(collection);
        this.joueurList.sort(order);
    }

    public ArrayList<Joueur> getJoueurList() {
        return joueurList;
    }

}
