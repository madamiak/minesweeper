package com.example;

import javafx.scene.image.Image;

public class IconFactory {
    private static Image SMILING_FACE;
    private static Image OPEN_MOUTH_FACE;
    private static Image MINESWEEPER;

    public static Image smilingFace() {
        if (SMILING_FACE == null) {
            SMILING_FACE = new Image("slightly-smiling-face_1f642.png");
        }
        return SMILING_FACE;
    }

    public static Image openMouthFace() {
        if (OPEN_MOUTH_FACE == null) {
            OPEN_MOUTH_FACE = new Image("face-with-open-mouth_1f62e.png");
        }
        return OPEN_MOUTH_FACE;
    }

    public static Image minesweeper() {
        if (MINESWEEPER == null) {
            MINESWEEPER = new Image("bomb_1f4a3.png");
        }
        return MINESWEEPER;
    }
}
