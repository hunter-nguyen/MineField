package minefield;

import mvc.AppFactory;
import mvc.Command;
import mvc.Model;
import mvc.View;

public class MineFieldFactory implements AppFactory {

    public Model makeModel() {
        return null;
    }

    public View makeView(Model model) {
        return null;
    }

    public String[] getEditCommands() {
        return new String[0];
    }

    public Command makeEditCommand(Model model, String type, Object source) {
        return null;
    }

    public String getTitle() {
        return "";
    }

    public String[] getHelp() {
        return new String[0];
    }

    public String about() {
        return "";
    }
}
