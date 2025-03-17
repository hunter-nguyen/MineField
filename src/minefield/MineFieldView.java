package minefield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;


import mvc.Model;
import mvc.View;

public class MineFieldView extends View {
    
    private int TILE_SIZE = 12;

    @Override
    public void setModel(Model newModel) {
        super.setModel(newModel);
        //initView();
        repaint();
    }

    public MineFieldView(MineField model) {
        super(model);
        int rows = model.getField().length;
        int cols = model.getField()[0].length;
        setPreferredSize(new Dimension(cols * TILE_SIZE, rows * TILE_SIZE));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        MineField mineField = (MineField) model;
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