import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static final int BORDER = 1,
            GRID_SIZE = 600,
            NUM_CELLS = 10,
            CELL_SIZE = GRID_SIZE/NUM_CELLS,
            SNAKE_SIZE = (int) (CELL_SIZE*0.75);

    private StackPane root;

    public static void main(String[] args) {
        //launch sets up the application then calls start()
        launch(args);
    }

    @Override
    public void start(Stage stage) {


        //root:StackPane -> {borderPane -> {center -> {gridStack -> gridPane}, ...}}
        Scene scene = setupScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();//because set resizeable has a bug that adds padding
        stage.show();

    }







    //VIEW

    private Scene setupScene() {

        StackPane gridStack = setupGrid();

        HBox top = setupHBox();
        HBox bottom = setupHBox();
        VBox right = setupVBox();
        VBox left = setupVBox();

        BorderPane borderPane = new BorderPane(gridStack, top, right, bottom, left);
        borderPane.setMinSize(GRID_SIZE, GRID_SIZE);

        root = new StackPane(borderPane);

        Scene scene = new Scene(root);
        scene.setFill(Color.GREY);

        return scene;
    }

    private VBox setupVBox() {
        int rightHeight = 600;
        int rightWidth = 200;

        VBox right = new VBox();
        Rectangle rectangle = new Rectangle(rightWidth - BORDER, rightHeight - BORDER);
        StackPane stackPane = new StackPane(rectangle);
        stackPane.setMinSize(rightWidth, rightHeight);
        right.getChildren().add(stackPane);
        return right;
    }

    private HBox setupHBox() {
        int topWidth = 1000;
        int topHeight = 100;

        HBox hBox = new HBox();
        Rectangle topRect = new Rectangle(topWidth - BORDER, topHeight - BORDER);
        StackPane topStack = new StackPane(topRect);
        topStack.setMinSize(topWidth, topHeight);
        hBox.getChildren().add(topStack);
        return hBox;
    }

    private StackPane setupGrid() {

        GridPane gridPane = new GridPane();
        StackPane gridStack = new StackPane(gridPane);
        for (int j = 0; j < NUM_CELLS; j ++) {
            for (int i = 0; i < NUM_CELLS; i ++) {
                int rectSize = CELL_SIZE - BORDER;
                Rectangle rectangle = new Rectangle(rectSize, rectSize);
                if (i%2==0 && j%2==0 || i%2!=0 && j%2!=0) {
                    rectangle.setFill(Color.web("#292929"));
                } else {
                    rectangle.setFill(Color.web("#3b3b3b"));
                }
                StackPane stackPane = new StackPane(rectangle);
                stackPane.setMinSize(CELL_SIZE, CELL_SIZE);
                gridPane.add(stackPane, i, j);
            }
        }
        return gridStack;
    }


    private GridPane getGrid() {
        //root:StackPane -> {borderPane -> {center -> {gridStack -> gridPane}, ...}}
        BorderPane borderPane = (BorderPane) root.getChildren().get(0);
        StackPane gridStack = (StackPane) borderPane.getCenter();
        return (GridPane) gridStack.getChildren().get(0);
    }
}
