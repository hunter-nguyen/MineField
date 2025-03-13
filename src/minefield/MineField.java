package minefield;

import mvc.Model;
import java.util.*;

public class MineField extends Model {

    private Plot[][] field;
    private int bombs;

    public MineField() {
        super();
        initializeField(20, 20);
        placeBombs(field);
    }

    public void move(int deltaX, int deltaY) throws Exception {
        changed();
    }

    public void initializeField(int row, int col){
        field = new Plot[row][col];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Plot();
            }
        }

        field[0][0].setIsVisited();
        field[row-1][col-1].setIsGoal();
    }

    public void placeBombs(Plot[][]field){
        Random rand = new Random();
        int totalTiles = field.length * field[0].length;
        bombs = (totalTiles/100) * 20;

        while(bombs > 0){
            int row = rand.nextInt(20);
            int col = rand.nextInt(20);
            
            if(!field[row][col].hasBomb() && !(row == 0 && col == 0) && !(row == field.length && col == field[0].length)){
                field[row][col].setHasBomb();
                System.out.println("There is a bomb at row: " + row + " and col " +  col);
                bombs--;
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (isValid(i, j) && !(i == row && j == col)) {
                            field[i][j].addAdjacentBomb();
                            System.out.println("The amount of adjacent bombs at row: " + i + " and col " +  j + " are " + field[i][j].getAdjacentBombs());
                        }
                    }
                }
            }
        }   
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < field.length && col >= 0 && col < field[0].length;
    }

    public Plot[][] getField(){
        return field;
    }


    // public static void main(String[] args) {
    //     MineField mf = new MineField();
    // }

    
}
