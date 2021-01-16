package GUI;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class View {

    private Model _model;
    private Group root;
    private LinkedList<Rectangle> snake;
    private Rectangle apple;
    private int segmentHeight, segmentWidth; //size of snake segments

    private static final Color snakeColor = Color.LAWNGREEN;
    private static final Color appleColor = Color.RED;
    private Scene scene;

    public View(Model model, int gameStageWidth, int gameStageHeight) {

        _model = model;
        //get how big each grid segment will be from the game stage size and grid size
        segmentWidth = gameStageWidth / _model.getColumns();
        segmentHeight = gameStageHeight / _model.getRows();
        root = new Group();

        addSnake();
        addApple();

        scene = new Scene(root, gameStageWidth, gameStageHeight, Color.BLACK);


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
        int y = coords[0] * segmentHeight;
        int x = coords[1] * segmentWidth;

        //currently, rectangles don't quite fill up their segment (I think it looks better)
        Rectangle segment = new Rectangle(x, y, segmentWidth - 5, segmentHeight - 5);
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
        }

        //add new head position
        Rectangle head = addSegment(_model.getHead(), snakeColor);
        snake.addFirst(head);
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
        for (Rectangle segment : snake) {
            segment.setFill(Color.DARKGREEN);
        }
    }
}
