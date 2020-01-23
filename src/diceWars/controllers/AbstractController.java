package diceWars.controllers;

import diceWars.models.AbstractModel;
import diceWars.views.AbstractView;

public abstract class AbstractController {

    private AbstractModel abstractModel;
    private AbstractView abstractView;

    public AbstractController(AbstractModel abstractModel, AbstractView abstractView) {
        this.abstractModel = abstractModel;
        this.abstractView = abstractView;
    }

    public AbstractModel getModel() {
        return this.abstractModel;
    }

    public AbstractView getView() {
        return this.abstractView;
    }

    public void setView(AbstractView abstractView) {
        this.abstractView = abstractView;
    }
}
