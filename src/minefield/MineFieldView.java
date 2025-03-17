package minefield;

import mvc.Model;
import mvc.View;

import java.awt.*;

public class MineFieldView extends View {

    private int TILE_SIZE = 12;

    @Override
    public void setModel(Model newModel) {
        super.setModel(newModel);
        initView(newModel);
        repaint();
    }

    public MineFieldView(Model model) {
        super(model);
        initView(model);
    }

    private void initView(Model model) {
        MineField field = (MineField) model;
        int rows = field.getField().length;
        int cols = field.getField()[0].length;
        setPreferredSize(new Dimension(cols * TILE_SIZE, rows * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        MineField mineField = (MineField) model;

        Dimension size = this.getSize();
        TILE_SIZE = (int) Math.min(size.getWidth() / mineField.getField().length, size.getHeight() / mineField.getField()[0].length);

        Plot[][] field = mineField.getField();

        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[row].length; col++) {
                drawTile(g, field[row][col], row, col, mineField);
            }
        }
    }

    private void drawTile(Graphics g, Plot plot, int row, int col, MineField mineField) {
        int x = col * TILE_SIZE;
        int y = row * TILE_SIZE;

        g.setFont(new Font("Arial", Font.BOLD, 8));

        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, TILE_SIZE, TILE_SIZE);


        if (plot.isVisited()) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, TILE_SIZE, TILE_SIZE);

            int bombCount = plot.getAdjacentBombs();
            if(plot.hasBomb()){
                g.setColor(Color.RED);
                g.drawString("B",x + TILE_SIZE / 3, y + (2 * TILE_SIZE) / 3 );
            }
            else {
                if(col == mineField.xCoord() && row == mineField.yCoord()){
                    g.setColor(Color.BLUE);
                    g.drawString(String.valueOf(bombCount), x + TILE_SIZE / 3, y + (2 * TILE_SIZE) / 3);
                }
                else{
                    g.setColor(Color.WHITE);
                    g.drawString(String.valueOf(bombCount), x + TILE_SIZE / 3, y + (2 * TILE_SIZE) / 3);
                }
            }


        } else {
            g.setColor(Color.GRAY);
            g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, TILE_SIZE, TILE_SIZE);


            g.setColor(Color.BLACK);
            g.drawString("?", x + TILE_SIZE / 3, y + (2 * TILE_SIZE) / 3);
        }


        if(plot.isGoal()){
            g.setColor(Color.GREEN);
            g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
        }



    }
}
