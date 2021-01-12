import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class View {

    private Model _model;
    private StackPane stackPane;
    private Rectangle snake;
    private int segmentHeight, segmentWidth; //size of snake segments

    public View(Model model, int gameStageWidth, int gameStageHeight) {
        _model = model;
        //get how big each grid segment will be from the game stage size and grid size
        segmentWidth = gameStageWidth / _model.getColumns();
        segmentHeight = gameStageHeight / _model.getRows();
        stackPane = new StackPane();

    }


    public StackPane getRootNode() {
        return stackPane;
    }

    /*public void updateButtonNumber(Integer newNumber) {
        button.setText("Button clicked: " + newNumber);
    }

    public void addActionHandler(Contoller.ActionHandler actionHandler) {
        button.setOnAction(actionHandler);
    }*/
}
