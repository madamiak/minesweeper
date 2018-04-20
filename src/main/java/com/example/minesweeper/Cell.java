package com.example.minesweeper;

public class Cell {

    public int x;

    public int y;

    private boolean isMine = false;

    private boolean revealed = false;

    private boolean flagged = false;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void mine() {
        isMine = true;
    }

    public boolean flag() {
        flagged = !flagged;
        return flagged;
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

    public boolean isFlagged() {
        return flagged;
    }
}
