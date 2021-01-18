package GUI;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import GUI.Model.Direction;

public class Contoller {

    Model _model;
    View _view;
    KeyEvent directionAction;

    public Contoller(Stage stage/*View view, Model model*/) {
        /*_model = model;
        _view = view;
        view.addMoveActionHandler(new DirectionActionHandler());

        startGame();*/
    }

    private void startGame() {
        _view.addTimeline(_model.getDelay(), new MoveActionHandler());
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
                _view.endGame();
            }
        }
    }


    public class DirectionActionHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.R) {
                _model.reset();
                _view.reset();
            } else {
                directionAction = event;
            }
        }
    }
}
