package com.example.minesweeper;

public interface Drawable {

    void setPaintColor(PaintColor paintColor);

    void draw(Grid grid);

    enum PaintColor {}
}
