package edu.ucam.models;

import edu.ucam.ui.View;
import edu.ucam.ui.ViewFactory;

public interface Option {
    View view = ViewFactory.getView();
    void execute();
    boolean isMenuDisplayed();
    String toString();
}
