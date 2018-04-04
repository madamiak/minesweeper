package com.example;

import com.example.minesweeper.Cell;
import com.example.minesweeper.Drawable;
import com.example.minesweeper.Grid;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class JavaFxCanvasDrawer implements Drawable {

    private final GraphicsContext gc;

    private Drawable.PaintColor paintColor;

    public JavaFxCanvasDrawer(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void setPaintColor(Drawable.PaintColor paintColor) {
        this.paintColor = paintColor;
    }


    @Override
    public void draw(Grid grid) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        double width = canvasWidth / grid.cols;
        double height = canvasHeight / grid.rows;
        for (int i = 0; i < grid.cols; i++) {
            for (int j = 0; j < grid.rows; j++) {
                gc.strokeRect(i * width, j * height, width, height);
                Cell cell = grid.cell(i, j);
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        gc.setFill(Color.GREY);
                        gc.fillOval(i * width, j * height, width, height);
                    } else {
                        gc.setStroke(Color.BLACK);
                        gc.setTextAlign(TextAlignment.CENTER);
                        gc.strokeText(String.valueOf(grid.neighbors(cell)), i * width + width / 2, (j + 0.75) * height);
                    }
                }
            }
        }
    }

    protected Color toColor(Drawable.PaintColor paintColor) {
        return Color.valueOf(paintColor.name());
    }
}
