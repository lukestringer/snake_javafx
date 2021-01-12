import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Contoller {

    Model _model;
    View _view;

    public Contoller(View view, Model model) {
        _model = model;
        _view = view;
        //view.addActionHandler(new ActionHandler());
    }

    public class ActionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            //update model and view
            //Integer newButtonNumber = _model.buttonClicked();
            //_view.updateButtonNumber(newButtonNumber);
        }
    }
}
