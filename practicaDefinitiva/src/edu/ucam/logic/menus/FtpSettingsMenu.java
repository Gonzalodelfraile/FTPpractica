package edu.ucam.logic.menus;

import edu.ucam.logic.options.Back;
import edu.ucam.logic.options.Exit;
import edu.ucam.logic.options.settings.ListSettings;
import edu.ucam.logic.options.settings.NewSetting;
import edu.ucam.logic.options.settings.RemoveSetting;
import edu.ucam.logic.options.settings.UseSetting;
import edu.ucam.models.Menu;
import edu.ucam.models.User;

public class FtpSettingsMenu extends Menu {

    public FtpSettingsMenu(User user) {
        super("Configuración FTP");

        addOption(1, new NewSetting(user));
        addOption(2, new RemoveSetting(user));
        addOption(3, new ListSettings(user));
        addOption(4, new UseSetting(user));
        addOption(5, new Back());
        addOption(6, new Exit());
    }
}

