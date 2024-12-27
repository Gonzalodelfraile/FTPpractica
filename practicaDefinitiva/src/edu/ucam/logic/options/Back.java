package edu.ucam.logic.options;

import edu.ucam.models.Option;
import edu.ucam.utils.Log;

public class Back implements Option {
    @Override
    public void execute() {
        Log.getInstance().debug("Volviendo al menú anterior...");
        //isMenuDisplayed() es false por lo que volverá al menú anterior
    }

    @Override
    public boolean isMenuDisplayed() {
        return false;
    }

    @Override
    public String toString() {
        return "Volver";
    }
}
