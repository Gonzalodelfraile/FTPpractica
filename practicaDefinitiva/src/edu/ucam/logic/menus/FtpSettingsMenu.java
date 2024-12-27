package edu.ucam.logic.menus;

import edu.ucam.logic.options.Back;
import edu.ucam.logic.options.Exit;
import edu.ucam.logic.options.settings.ListSettings;
import edu.ucam.logic.options.settings.NewSetting;
import edu.ucam.logic.options.settings.RemoveSetting;
import edu.ucam.logic.options.settings.UseSetting;
import edu.ucam.models.Menu;

public class FtpSettingsMenu extends Menu {

    public FtpSettingsMenu() {
        super("Configuración FTP");

        addOption(1, new NewSetting());
        addOption(2, new RemoveSetting());
        addOption(3, new ListSettings());
        addOption(4, new UseSetting());
        addOption(5, new Back());
        addOption(6, new Exit());
    }
}

