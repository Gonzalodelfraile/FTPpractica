package edu.ucam.models.menus;

import edu.ucam.logic.LoginManager;
import edu.ucam.models.User;
import edu.ucam.ui.MenuUI;
import edu.ucam.utils.Log;

public class AdminMenu {
    private String[] options = {
            "New user",
            "Delete user",
            "List users",
            "Log out",
            "Exit" };
    private User user;

    public AdminMenu(User user) {
        this.user = user;
    }

    public void showMenu() {
        int option;
        do{
            option = MenuUI.showMenu("Admin Menu", options);
            switch (option) {
                case -1:
                    Log.getInstance().error("Formato de opción incorrecto");
                    break;
                case 1:
                    System.out.println("New user");
                    break;
                case 2:
                    System.out.println("Delete user");
                    break;
                case 3:
                    System.out.println("List users");
                    break;
                case 4:
                    System.out.println("Log out");
                    LoginManager.login();
                    break;
                case 5:
                    System.out.println("Exit");
                    break;
            }
        } while (option != 5 && option != 4);

    }
}
