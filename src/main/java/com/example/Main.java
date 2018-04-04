package com.example;

import com.example.minesweeper.Drawable;
import com.example.minesweeper.Minesweeper;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double WIDTH = 400;

    private static final double HEIGHT = 400;

    private static final int ROWS = 20;

    private static final int COLS = 20;

    private static final int MINES = 15;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Minesweeper");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Drawable javaFxCanvasDrawer = new JavaFxCanvasDrawer(gc);
        Minesweeper minesweeper = Minesweeper.create(ROWS, COLS, MINES);
        minesweeper.draw(javaFxCanvasDrawer);
        System.out.println(minesweeper);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int x = (int) (e.getX() / COLS);
            int y = (int) (e.getY() / ROWS);
            minesweeper.reveal(x, y);
            minesweeper.draw(javaFxCanvasDrawer);
        });

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
