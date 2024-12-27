package edu.ucam.logic.options.settings;

import edu.ucam.models.FtpConfig;
import edu.ucam.models.Option;
import edu.ucam.models.User;
import edu.ucam.utils.Log;

import java.util.Scanner;

public class RemoveSetting implements Option {
    private User user;

    public RemoveSetting(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        Log.getInstance().debug("Eliminando configuración...");
        String name = ftpDataInput();

        user.removeConfig(name);
    }

    private String ftpDataInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nombre de la configuración a eliminar: ");
        return scanner.nextLine();
    }

    @Override
    public boolean isMenuDisplayed() {
        return true;
    }

    @Override
    public String toString() {
        return "Eliminar configuración";
    }
}
