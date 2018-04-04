package com.example.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid {

    private final Cell[][] grid;

    public final int rows;

    public final int cols;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Cell[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public List<Cell> toList() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            cells.addAll(Arrays.asList(grid[i]));
        }
        return cells;
    }

    public void markMine(Cell cell) {
        cell(cell.x, cell.y).mine();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < cols; i++) {
            sb.append("--");
        }
        sb.append("\n");
        for (int i = 0; i < cols; i++) {

            for (int j = 0; j < rows; j++) {
                sb.append(cell(i,j).toString());
                sb.append("|");
            }
            sb.append("\n");
            for (int k = 0; k < cols; k++) {
                sb.append("--");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Cell cell(int col, int row) {
        return grid[col][row];
    }

    public int neighbors(Cell cell) {
        if (cell.isMine()) {
            return -1;
        }
        int neighbors = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = cell.x + i;
                int y = cell.y + j;
                if (x >= 0 && x < cols && y >= 0 && y < rows) {
                    neighbors += cell(x, y).isMine() ? 1 : 0;
                }
            }
        }
        return neighbors;
    }

    public void reveal(int col, int row) {
        Cell cell = cell(col, row);
        if (cell.isRevealed()) {
            return;
        }
        cell.reveal();
        if (neighbors(cell) == 0) {
            floodFill(cell);
        }
    }

    private void floodFill(Cell cell) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = cell.x + i;
                int y = cell.y + j;
                if (x >= 0 && x < cols && y >= 0 && y < rows) {
                    cell = cell(x,y);
                    if (!cell.isRevealed() && !cell.isMine()) {
                        reveal(x, y);
                    }
                }
            }
        }
    }
}
