import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class Contoller {

    Model _model;
    View _view;
    Timeline timeline;

    public Contoller(View view, Model model) {
        _model = model;
        _view = view;
        view.addMoveActionHandler(new DirectionActionHandler());

        startGame();
    }

    private void startGame() {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new MoveActionHandler());
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private class MoveActionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //update model and view to move snake
            Boolean appleEaten = _model.moveSnake();
            if (appleEaten == null) {
                timeline.stop();
            } else {
                _view.moveSnake(appleEaten);
            }

        }
    }


    public class DirectionActionHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case RIGHT: _model.setDirection(Direction.RIGHT);   break;
                case DOWN:  _model.setDirection(Direction.DOWN);    break;
                case LEFT:  _model.setDirection(Direction.LEFT);    break;
                case UP:    _model.setDirection(Direction.UP);      break;

            }
        }
    }
}
