package minefield;

import mvc.Model;

public class MineField extends Model {

    public MineField() {
        super();
    }

    public void move(int deltaX, int deltaY) throws Exception {


        changed();
    }
}
