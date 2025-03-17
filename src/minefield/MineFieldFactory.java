package minefield;

import mvc.AppFactory;
import mvc.Command;
import mvc.Model;
import mvc.View;

public class MineFieldFactory implements AppFactory {

    public Model makeModel() {
        return new MineField();
    }

    public View makeView(Model model) {
        return new MineFieldView((MineField) model);
    }

    public String[] getEditCommands() {
        return new String[] { "North", "South", "West", "East",
                "North-West", "North-East", "South-West", "South-East" };
    }

    public Command makeEditCommand(Model model, String type, Object source) {
        Heading heading = null;
        switch (type) {
            case "North":
                heading = Heading.NORTH;
                break;
            case "South":
                heading = Heading.SOUTH;
                break;
            case "East":
                heading = Heading.EAST;
                break;
            case "West":
                heading = Heading.WEST;
                break;
            case "North-East":
                heading = Heading.NORTH_EAST;
                break;
            case "North-West":
                heading = Heading.NORTH_WEST;
                break;
            case "South-East":
                heading = Heading.SOUTH_EAST;
                break;
            case "South-West":
                heading = Heading.SOUTH_WEST;
                break;
        }
        if (heading != null) {
            return new MoveCommand(model, heading);
        }
        return null;
    }

    public String getTitle() {
        return "Minefield";
    }

    public String[] getHelp() {
        return new String[] {
                "Use the Edit menu to move in different directions",
                "Try to avoid mines and reach the goal!"
        };
    }

    public String about() {
        return "Minefield Game v1.0\nCreated with MVC Framework";
    }
}
