package com.example;

import com.example.minesweeper.Drawable;
import com.example.minesweeper.Minesweeper;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");
        newGame(primaryStage);
        primaryStage.show();
    }

    private void newGame(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Drawable javaFxCanvasDrawer = new JavaFxCanvasDrawer(gc);
        Minesweeper minesweeper = Minesweeper.create(ROWS, COLS, MINES);
        minesweeper.draw(javaFxCanvasDrawer);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            int x = (int) (e.getX() / COLS);
            int y = (int) (e.getY() / ROWS);
            if ((e.getButton().equals(MouseButton.PRIMARY) && e.isSecondaryButtonDown())
                    || (e.getButton().equals(MouseButton.SECONDARY) && e.isPrimaryButtonDown())) {
                minesweeper.revealNeighbors(x, y);
            } else if (e.getButton().equals(MouseButton.PRIMARY)) {
                minesweeper.reveal(x, y);
            } else if (e.getButton().equals(MouseButton.SECONDARY)) {
                minesweeper.flag(x, y);
            }
            if (minesweeper.finished()) {
                minesweeper.gameOver();
                if (minesweeper.won()) {
                    dialog(primaryStage, "You win!");
                } else {
                    dialog(primaryStage, "You loose!");
                }
            }
            minesweeper.draw(javaFxCanvasDrawer);
        });
    }

    private void dialog(Stage primaryStage, String text) {
        Stage dialog = new Stage();
        dialog.initOwner(primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        Button playAgainButton = new Button("Play again");
        playAgainButton.setOnAction(e -> {
            newGame(primaryStage);
            dialog.close();
        });
        root.getChildren().addAll(new VBox(new Text(WIDTH, HEIGHT, text),playAgainButton));
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
