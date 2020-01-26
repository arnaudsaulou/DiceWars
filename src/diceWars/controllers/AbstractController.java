package diceWars.controllers;

import diceWars.models.AbstractModel;
import diceWars.views.AbstractView;

/**
 * Mother class of all controllers
 */
public abstract class AbstractController {

    //region Variables

    private final AbstractModel abstractModel;
    private AbstractView abstractView;

    //endregion

    //region Constructor

    public AbstractController(AbstractModel abstractModel, AbstractView abstractView) {
        this.abstractModel = abstractModel;
        this.abstractView = abstractView;
    }

    //endregion

    //region Getter

    public AbstractModel getModel() {
        return this.abstractModel;
    }

    public AbstractView getView() {
        return this.abstractView;
    }

    //endregion

}
