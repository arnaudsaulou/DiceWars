package diceWars.models;

import javafx.collections.transformation.SortedList;

import java.util.*;

public class Ranking extends AbstractModel implements Iterable<Joueur> {

    //region Variables

    private ArrayList<Joueur> joueurList;

    //endregion

    public Ranking(Comparator<Joueur> order, Collection<Joueur> collection) {
        this.joueurList = new ArrayList<Joueur>();
        this.joueurList.addAll(collection);
        this.joueurList.sort(order);
    }

    public ArrayList<Joueur> getJoueurList() {
        return joueurList;
    }

    @Override
    public Iterator<Joueur> iterator() {
        return null;
    }
}
