package GUI;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public class View {
    private BorderPane root;
    private GridPane gameGrid;

    Model model;
    LinkedList<Rectangle> snake;

    public View(Model model) {
        this.model = model;

        root = new BorderPane();
        gameGrid = new GridPane();
        root.setCenter(gameGrid);

        resetGame();
    }

    private void resetGame() {
        snake = newSnake();
        //delete old apple
        //delete old timeline
        //add snake from model
        //add apple from model
        //add new timeline with delay from model
    }

    private LinkedList<Rectangle> newSnake() {
        LinkedList<Rectangle> newSnake = new LinkedList<>();
        for (Model.Coordinates snakeCoord : model.getSnake()) {
            newSnake.addLast(newSegment(snakeCoord));
        }
    }

    private Rectangle newSegment(Model.Coordinates snakeCoord) {
        return new Rectangle();//todo
    }

    private void addSnake() {

    }



}
