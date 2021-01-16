package GUI;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import GUI.Model.Direction;

public class Contoller {

    Model _model;
    View _view;
    Timeline timeline;
    private int delay;//delay in milliseconds between each frame
    KeyEvent directionAction;

    public Contoller(View view, Model model) {
        _model = model;
        _view = view;
        view.addMoveActionHandler(new DirectionActionHandler());

        startGame();
    }

    private void startGame() {
        delay = 500;
        KeyFrame keyFrame = new KeyFrame(Duration.millis(delay), new MoveActionHandler());
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private class MoveActionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //check if player has made a direction change
            if (directionAction != null) {
                switch (directionAction.getCode()) {
                    case RIGHT: _model.setDirection(Direction.RIGHT);   break;
                    case DOWN:  _model.setDirection(Direction.DOWN);    break;
                    case LEFT:  _model.setDirection(Direction.LEFT);    break;
                    case UP:    _model.setDirection(Direction.UP);      break;
                }
                //done with the action, reset it to null
                directionAction = null;
            }
            //update model and view to move snake
            Boolean appleEaten = _model.moveSnake();
            if (appleEaten != null) {
                _view.moveSnake(appleEaten);
            } else {
                timeline.stop();
                _view.endGame();
            }
        }
    }


    public class DirectionActionHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            directionAction = event;
        }
    }
}
