package minefield;

import mvc.Command;
import mvc.Model;

public class MoveCommand extends Command {
    private final Heading direction;

    public MoveCommand(Model model, Heading heading) {
        super(model);
        this.direction = heading;
    }

    @Override
    public void execute() throws Exception {
        MineField field = (MineField) model;

        int deltaX = 0;
        int deltaY = 0;

        switch (direction) {
            case NORTH -> {
                deltaY = -1;
            }
            case SOUTH -> {
                deltaY = 1;
            }
            case WEST -> {
                deltaX = -1;
            }
            case EAST -> {
                deltaX = 1;
            }
            case NORTH_WEST -> {
                deltaY = -1;
                deltaX = -1;
            }
            case NORTH_EAST -> {
                deltaY = -1;
                deltaX = 1;
            }
            case SOUTH_WEST -> {
                deltaY = 1;
                deltaX = -1;
            }
            case SOUTH_EAST -> {
                deltaY = 1;
                deltaX = 1;
            }
            default -> throw new Exception("Invalid direction");
        }

        field.move(deltaX, deltaY);
    }
}
