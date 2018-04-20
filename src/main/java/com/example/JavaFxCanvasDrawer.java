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
                        drawMine(grid, cell);
                    } else {
                        drawCell(grid, cell);
                    }
                } else if (cell.isFlagged()) {
                    drawFlag(grid, cell);
                }
            }
        }
    }

    private void drawCell(Grid grid, Cell cell) {
        double width = gc.getCanvas().getWidth() / grid.cols;
        double height = gc.getCanvas().getHeight() / grid.rows;
        gc.setStroke(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        double centerX = cell.x * width + width / 2;
        double centerY = (cell.y + 0.75) * height;
        gc.strokeText(String.valueOf(grid.neighbors(cell)), centerX, centerY);
    }

    private void drawFlag(Grid grid, Cell cell) {
        double width = gc.getCanvas().getWidth() / grid.cols;
        double height = gc.getCanvas().getHeight() / grid.rows;
        gc.setFill(Color.RED);

        double topX = cell.x * width + 0.5 * width;
        double topY = cell.y * height + 0.25 * height;

        double bottomX = cell.x * width + 0.5 * width;
        double bottomY = cell.y * height + (0.25 + 0.4) * height;

        double leftX = cell.x * width + (0.5 - 0.2) * width;
        double leftY = cell.y * height + (0.25 + 0.2) * height;

        gc.fillPolygon(new double[] {topX, bottomX, leftX}, new double[] {topY, bottomY, leftY}, 3);

        gc.setFill(Color.BLACK);

        double rectX = cell.x * width + (0.5 - 0.3) * width;
        double rectY = cell.y * height + (0.25 + 0.4) * height;
        gc.fillRect(rectX, rectY, 0.5 * width, 0.2 * height);
    }

    private void drawMine(Grid grid, Cell cell) {
        double width = gc.getCanvas().getWidth() / grid.cols;
        double height = gc.getCanvas().getHeight() / grid.rows;
        gc.setFill(Color.GREY);
        gc.fillOval(cell.x * width, cell.y * height, width, height);
    }

    protected Color toColor(Drawable.PaintColor paintColor) {
        return Color.valueOf(paintColor.name());
    }
}
