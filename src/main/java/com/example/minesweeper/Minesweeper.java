package com.example.minesweeper;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Minesweeper {

    private Grid grid;

    public static Minesweeper create(int rows, int cols, int mines) {
        Grid grid = new Grid(rows, cols);
        List<Cell> options = grid.toList();
        Random random = new Random();
        IntStream.range(0, mines)
                .mapToObj(i -> options.remove(random.nextInt(options.size())))
                .forEach(grid::markMine);
        return new Minesweeper(grid);
    }

    private Minesweeper(Grid grid) {
        this.grid = grid;
    }

    public void reveal(int x, int y) {
        this.grid.reveal(x, y);
    }

    public void draw(Drawable drawer) {
        drawer.draw(grid);
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
