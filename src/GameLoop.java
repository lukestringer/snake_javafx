import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class GameLoop {

    KeyEvent directionAction;
    Timeline timeline;
    BorderPane borderPane;
    Grid grid;
    Cell.Edge direction;

    public GameLoop(Grid grid) {
        this.grid = grid;
        borderPane = new BorderPane(grid.getGridPane());

        direction = Cell.Edge.RIGHT;

        KeyFrame keyFrame = new KeyFrame(Duration.millis(700), new MoveActionHandler());
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public BorderPane getRoot() {
        return borderPane;
    }


    private class MoveActionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            grid.moveSnake(direction);


        }
    }
}
