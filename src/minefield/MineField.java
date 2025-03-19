package minefield;

import mvc.Model;
import mvc.Utilities;

public class MineField extends Model {
    public static final int FIELD_LENGTH = 20;
    private int playerX = 0;
    private int playerY = 0;
    private boolean gameOver = false;

    private Plot[][] field;
    private int bombs;

    public MineField() {
        super();
        initializeField(FIELD_LENGTH, FIELD_LENGTH);
        placeBombs(field);
    }

    public void move(int deltaX, int deltaY) throws Exception {
        if (gameOver) {
            throw new Exception("Game is over! Cannot move.");
        }

        int newX = playerX + deltaX;
        int newY = playerY + deltaY;

        if (isValid(newX, newY)) {
            playerX = newX;
            playerY = newY;
            field[playerY][playerX].setIsVisited();

            if (field[newY][newX].hasBomb()) {
                field[newY][newX].setIsVisited();
                gameOver = true;
                changed();
                throw new MineHitException("Game Over! You stepped on a mine.");
            }

            if (field[newY][newX].isGoal()) {
                field[newY][newX].setIsVisited();
                gameOver = true;
                changed();
                throw new GoalReachedException("Congratulations! You've reached the goal!");
            }

            changed();
        } else {
            throw new Exception("Move out of bounds");
        }
    }

    public void initializeField(int rows, int cols) {
        field = new Plot[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                field[i][j] = new Plot();
            }
        }

        field[0][0].setIsVisited();
        field[rows - 1][cols - 1].setIsGoal();
    }

    public void placeBombs(Plot[][] field) {
        int totalTiles = field.length * field[0].length;
        bombs = (int) ((totalTiles / 100.0) * 20);

        while (bombs > 0) {
            int row = Utilities.rng.nextInt(FIELD_LENGTH);
            int col = Utilities.rng.nextInt(FIELD_LENGTH);

            if (!field[row][col].hasBomb() && !(row == 0 && col == 0)
                    && !(row == FIELD_LENGTH - 1 && col == FIELD_LENGTH - 1)) {
                field[row][col].setHasBomb();
                bombs--;
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (isValid(i, j) && !(i == row && j == col)) {
                            field[i][j].addAdjacentBomb();
                        }
                    }
                }
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < field.length && col >= 0 && col < field[0].length;
    }

    public Plot[][] getField() {
        return field;
    }

    public int xCoord() {
        return playerX;
    }

    public int yCoord() {
        return playerY;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
