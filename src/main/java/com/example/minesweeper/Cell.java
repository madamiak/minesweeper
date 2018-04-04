package com.example.minesweeper;

public class Cell {

    int x;

    int y;

    private boolean isMine = false;

    private boolean revealed = true;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mine() {
        isMine = true;
    }

    public void reveal() {
        revealed = true;
    }

    public boolean isRevealed() {
        return revealed;
    }

    @Override
    public String toString() {
        return isMine ? "M" : " ";
    }

    public boolean isMine() {
        return isMine;
    }
}
