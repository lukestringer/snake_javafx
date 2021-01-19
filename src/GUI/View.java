package GUI;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;

public class View {
    private BorderPane root;
    private GridPane gameGrid;
    private HashMap<Model.Coordinates, Rectangle> gameSquares;
    private Model model;
    private Timeline timeline;
    private KeyEvent direction;
    private Scene scene;
    private Stage stage;

    private static final double GAME_WIDTH = 500;
    private static final double GAME_HEIGHT = 500;
    private static final double STAGE_WIDTH = 600;
    private static final double STAGE_HEIGHT = 600;
    private static final String TITLE = "Snake";

    private static final Color SNAKE_COLOUR = Color.LAWNGREEN;
    private static final Color EMPTY_COLOUR = Color.BLACK;
    private static final Color APPLE_COLOUR = Color.RED;
    private static final Color DEAD_HEAD_COLOUR = Color.DARKGREEN;
    private double squareWidth;
    private double squareHeight;

    //todo make look like actual snake (change rectangle shapes based on direction)


    public View(Model model, Stage stage) {
        this.model = model;
        this.stage = stage;
        squareWidth = GAME_WIDTH / model.maxColumns();
        squareHeight = GAME_HEIGHT / model.maxRows();

        setupView();
    }

    private void setupView() {
        root = new BorderPane();
        gameGrid = new GridPane();
        root.setCenter(gameGrid);

        setupSquares();
        setupSnake();
        setupApple();

        setupTimeline();
        setupScene();
        setupStage();


    }

    private void setupStage() {
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void setupScene() {
        scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);//, Color.GREY);
        scene.setOnKeyPressed(new DirectionActionHandler());
    }

    private void setupTimeline() {
        Duration duration = Duration.millis(model.getGameSpeed());
        KeyFrame keyFrame = new KeyFrame(duration, new MoveActionHandler());
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void setupApple() {
        gameSquares.get(model.getApple()).setFill(APPLE_COLOUR);
    }

    private void setupSnake() {
        for (Model.Coordinates snakeCoord : model.getSnake()) {
            gameSquares.get(snakeCoord).setFill(SNAKE_COLOUR);
        }
    }

    private void setupSquares() {
        gameSquares = new HashMap<>();
        for (byte column = 0; column < model.maxColumns(); column++) {
            for (byte row = 0; row < model.maxRows(); row++) {
                //get pixel position of the game square
                double x = squareWidth * column;
                double y = squareHeight * row;

                //make the square and add it to the map with matching coords
                Rectangle square = new Rectangle(x, y, squareWidth, squareHeight);
                gameSquares.put(new Model.Coordinates(row, column), square);

                //add it to the grid pane to make it visible
                gameGrid.add(square, column, row);
            }
        }
    }

    private void resetGame() {
        //set game empty
        for (Rectangle square : gameSquares.values()) {
            square.setFill(EMPTY_COLOUR);
        }
        setupSnake();
        setupApple();
    }

    public void startTimeline() {
        timeline.play();
    }

    private class MoveActionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //check if player has made a direction change
            if (direction != null) {
                switch (direction.getCode()) {
                    case RIGHT:
                        model.changeDirection(Model.Direction.RIGHT);
                        break;
                    case DOWN:
                        model.changeDirection(Model.Direction.DOWN);
                        break;
                    case LEFT:
                        model.changeDirection(Model.Direction.LEFT);
                        break;
                    case UP:
                        model.changeDirection(Model.Direction.UP);
                        break;
                }
                //done with the action, reset it to null
                direction = null;
            }
            //update model and view to move snake
            model.moveSnake();
            Model.Coordinates justRemoved = model.getJustRemoved();
            if (justRemoved != null) {
                gameSquares.get(justRemoved).setFill(EMPTY_COLOUR);
            }
            if (model.isJustEaten()) {
                gameSquares.get(model.getApple()).setFill(APPLE_COLOUR);
            }
            if (model.getCollision()) {
                gameSquares.get(model.getHead()).setFill(DEAD_HEAD_COLOUR);//
                timeline.stop();
            } else {
                gameSquares.get(model.getHead()).setFill(SNAKE_COLOUR);//
            }
        }
    }

    public class DirectionActionHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            direction = event;//race condition with move action?
        }
    }

}
