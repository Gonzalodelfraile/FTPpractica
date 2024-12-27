package edu.ucam.logic.options.settings;

import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

public class ListSettings implements Option {
    private User user;

    public ListSettings(User user) {
        this.user = user;
    }
    @Override
    public void execute() {
        Log.getInstance().debug("Listando configuraciones...");
        System.out.println(user.listConfigs());

    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Listar configuraciones";
    }
}
