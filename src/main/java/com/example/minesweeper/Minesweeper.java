package com.example.minesweeper;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Minesweeper {

    private final Grid grid;

    private final int mines;

    private int availableFlags;

    private boolean finished = false;

    public static Minesweeper create(int rows, int cols, int mines) {
        Grid grid = new Grid(rows, cols);
        List<Cell> options = grid.toList();
        Random random = new Random();
        IntStream.range(0, mines)
                .mapToObj(i -> options.remove(random.nextInt(options.size())))
                .forEach(grid::markMine);
        return new Minesweeper(grid, mines);
    }

    private Minesweeper(Grid grid, int mines) {
        this.grid = grid;
        this.mines = mines;
        this.availableFlags = mines;
    }

    public void reveal(int x, int y) {
        finished = this.grid.reveal(x, y);
    }

    public void flag(int x, int y) {
        if (!this.grid.cell(x, y).isRevealed()) {
            boolean flag = this.grid.flag(x, y);
            if (availableFlags > 0 && flag) {
                this.availableFlags--;
            } else if (!flag) {
                this.availableFlags++;
            } else if (availableFlags == 0) {
                this.grid.flag(x, y);
            }
        }
        System.out.println("available mines:" + availableFlags);
    }

    public void draw(Drawable drawer) {
        drawer.draw(grid);
    }

    public boolean finished() {
        return finished || (grid.getNotRevealedCells() == mines && availableFlags == 0);
    }

    public void gameOver() {
        grid.revealAll();
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
