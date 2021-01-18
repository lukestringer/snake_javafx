package GUI;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

public class View {

    private final Model _model;
    private final Group root;
    Timeline timeline;//timeline for the game - allows for moving and key captures
    private LinkedList<Rectangle> snake;
    private Rectangle apple;
    private final double segmentHeight, segmentWidth; //size of snake segments
    private EventHandler<ActionEvent> _moveActionHandler;

    private static final Color snakeColor = Color.LAWNGREEN;
    private static final Color appleColor = Color.RED;
    private final Scene scene;

    private Button reset;

    public View(Model model, double gameStageWidth, double gameStageHeight) {

        _model = model;
        //get how big each grid segment will be from the game stage size and grid size
        segmentWidth = gameStageWidth / _model.getColumns();
        segmentHeight = gameStageHeight / _model.getRows();
        root = new Group();
        root.setAutoSizeChildren(false);////////////

        double resetHeight = 25;

        addSnake();
        addApple();
        addReset(resetHeight, gameStageHeight, gameStageWidth);

        //for some reason, stage.show() increases the scene size by 10 in both directions.
        // I think it may be because the root node is a group, but without going into that in depth
        // I can fix the problem by reducing the scene size by 10 preemptively.
        double totalHeight = gameStageHeight - 10 + resetHeight;
        scene = new Scene(root, gameStageWidth - 10, totalHeight, Color.BLACK);


    }

    private void addReset(double resetHeight, double gameStageHeight, double gameStageWidth) {
        Rectangle resetBackground = new Rectangle(0, gameStageHeight, gameStageWidth, resetHeight);
        resetBackground.setFill(Color.GREY);
        root.getChildren().add(resetBackground);

        Text label = new Text("r - reset");
        label.setFont(new Font(20));
        //label.setFill(Color.WHITE);
        //label.setBackground(new Background(new BackgroundFill(Paint.   )));
        label.setY(gameStageHeight + 18.5);
        label.setX(gameStageWidth / 2.0 - 50);
        root.getChildren().add(label);

        //reset = new Button("reset");
        //reset.setMinHeight(500);
        //reset.setMinWidth(500);


        //root.getChildren().add(reset);


    }

    private void addApple() {
        apple = addSegment(_model.getApple(), appleColor);
    }

    private void addSnake() {
        snake = new LinkedList<>();
        List<int[]> coords = _model.getSnake();
        for (int[] coord : coords) {
            snake.addLast(addSegment(coord, snakeColor));
        }
    }

    private Rectangle addSegment(int[] coords, Color color) {
        //resize from grid coordinates to stage coordinates
        double y = coords[0] * segmentHeight;
        double x = coords[1] * segmentWidth;

        Rectangle segment = new Rectangle(x, y, segmentWidth, segmentHeight);
        segment.setFill(color);
        root.getChildren().add(segment);
        return segment;
    }

    public void moveSnake(boolean appleEaten) {
        if (!appleEaten) {
            //remove tail
            Rectangle tailEnd = snake.removeLast();
            root.getChildren().remove(tailEnd);
        } else {
            //move apple to new position
            moveApple();
            //speed up game
            speedUp();
        }

        //add new head position
        Rectangle head = addSegment(_model.getHead(), snakeColor);
        snake.addFirst(head);
    }

    private void speedUp() {
        timeline.stop();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(_model.getDelay()), _moveActionHandler);
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void moveApple() {
        int[] appleCoords = _model.getApple();
        apple.setY(appleCoords[0] * segmentHeight);
        apple.setX(appleCoords[1] * segmentWidth);
    }

    /*public void updateButtonNumber(Integer newNumber) {
        button.setText("Button clicked: " + newNumber);
    }*/

    public void addMoveActionHandler(EventHandler<KeyEvent> actionHandler) {
        scene.setOnKeyPressed(actionHandler);
    }

    /* -------------------- GETTERS -------------------- */

    public Scene getScene() {
        return scene;
    }

    public void endGame() {
        timeline.stop();
        for (Rectangle segment : snake) {
            segment.setFill(Color.DARKGREEN);
        }
    }

    public void addTimeline(double delay, EventHandler<ActionEvent> moveActionHandler) {
        _moveActionHandler = moveActionHandler;//fixme this sucks
        KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), moveActionHandler);
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void reset() {
        for (Rectangle seg : snake) {
            root.getChildren().remove(seg);
        }
        addSnake();
        root.getChildren().remove(apple);
        addApple();
        addTimeline(_model.getDelay(), _moveActionHandler);
    }
}
