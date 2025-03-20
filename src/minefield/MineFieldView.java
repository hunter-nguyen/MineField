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
    setPreferredSize(new Dimension(MineField.FIELD_LENGTH * TILE_SIZE,
                                  MineField.FIELD_LENGTH * TILE_SIZE));
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        MineField mineField = (MineField) model;

        int rows = MineField.FIELD_LENGTH;
        int cols = MineField.FIELD_LENGTH;

        Dimension size = this.getSize();
        TILE_SIZE = (int) Math.min(size.getWidth() / rows, size.getHeight() / cols);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Plot plot = mineField.getPlot(col, row);
                drawTile(g, plot, row, col, mineField);
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

            if (plot.hasBomb()) {
                g.setColor(Color.RED);
                g.drawString("B", x + TILE_SIZE / 3, y + (2 * TILE_SIZE) / 3);
            } else {
                int bombCount = plot.getAdjacentBombs();
                if (col == mineField.xCoord() && row == mineField.yCoord()) {
                    g.setColor(Color.BLUE);
                    g.drawString(String.valueOf(bombCount), x + TILE_SIZE / 3, y + (2 * TILE_SIZE) / 3);
                } else {
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

        if (plot.isGoal()) {
            g.setColor(Color.GREEN);
            g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
        }
    }
}
