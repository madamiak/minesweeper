package com.example;

import com.example.minesweeper.Drawable;
import com.example.minesweeper.Minesweeper;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

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
        primaryStage.getIcons().add(IconFactory.minesweeper());
        newGame(primaryStage);
        primaryStage.show();
    }

    private void newGame(Stage primaryStage) {
        VBox root = new VBox();
        HBox gameStatusPanel = new HBox();
        gameStatusPanel.setSpacing(20);
        gameStatusPanel.setAlignment(Pos.CENTER);
        Text passingTime = new Text(50, 20, "0");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                passingTime.setText(String.valueOf(Integer.valueOf(passingTime.getText()) + 1));
            }
        }, 1000, 1000);
        ImageView statusIcon = new ImageView();
        statusIcon.setPreserveRatio(true);
        statusIcon.setFitHeight(20);
        statusIcon.setImage(IconFactory.smilingFace());
        statusIcon.setOnMouseClicked(e -> newGame(primaryStage));
        Text flagsLeft = new Text(50, 20, String.valueOf(MINES));
        gameStatusPanel.getChildren().addAll(passingTime, statusIcon, flagsLeft);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().addAll(gameStatusPanel, canvas);
        primaryStage.setScene(new Scene(root));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Drawable javaFxCanvasDrawer = new JavaFxCanvasDrawer(gc);
        Minesweeper minesweeper = Minesweeper.create(ROWS, COLS, MINES);
        minesweeper.draw(javaFxCanvasDrawer);
        root.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> statusIcon.setImage(IconFactory.openMouthFace()));
        root.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> statusIcon.setImage(IconFactory.smilingFace()));
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
                timer.cancel();
                minesweeper.draw(javaFxCanvasDrawer);
                if (minesweeper.won()) {
                    dialog(primaryStage, "You win!");
                } else {
                    dialog(primaryStage, "You loose!");
                }
            }
            minesweeper.draw(javaFxCanvasDrawer);
        });
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> flagsLeft.setText(String.valueOf(minesweeper.flagsLeft())));
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
        root.getChildren().addAll(new VBox(new Text(WIDTH, HEIGHT, text), playAgainButton));
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
