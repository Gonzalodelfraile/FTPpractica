package edu.ucam.ui;

public class ViewFactory {
    private static String viewType = "console";

    public static View getView() {
        return switch (viewType) {
            case "console" -> ConsoleView.getInstance();
            case "gui" -> {
                ConsoleView.getInstance().displayError("Vista GUI no implementada");
                yield ConsoleView.getInstance();
            }
            default -> {
                ConsoleView.getInstance().displayError("Vista no implementada");
                yield ConsoleView.getInstance();
            }
        };
    }

    public static void setViewType(String viewType) {
        ViewFactory.viewType = viewType;
    }
}
