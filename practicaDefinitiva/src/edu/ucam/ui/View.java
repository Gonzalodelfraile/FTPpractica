package edu.ucam.ui;

import edu.ucam.models.FtpConfig;
import edu.ucam.models.Option;
import edu.ucam.models.User;

import java.util.Map;

public interface View {

    void display(String message);
    void displayError(String message);
    int displayMenu(String title, Map<Integer, Option> options);

    User getUserData();
    FtpConfig getFtpData();
    String getInput(String message);

    void update();
}
