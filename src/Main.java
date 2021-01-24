
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int BORDER = 1;//pixel size of borders for cells, panes, whatever

    public static void main(String[] args) {
        //launch sets up the application then calls start()
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {



        Scene scene = setupScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();//because set resizeable has a bug that adds padding
        stage.show();
    }


    





    //VIEW

    private Scene setupScene() {
        int gridSize = 600;
        int cells = 10;

        StackPane gridStack = setupGrid(gridSize, cells);

        HBox top = setupHBox();
        HBox bottom = setupHBox();
        VBox right = setupVBox();
        VBox left = setupVBox();

        BorderPane borderPane = new BorderPane(gridStack, top, right, bottom, left);
        borderPane.setMinSize(gridSize, gridSize);

        StackPane root = new StackPane(borderPane);

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

    private StackPane setupGrid(int gridSize, int cells) {
        int gridCellSize = gridSize/cells;

        GridPane gridPane = new GridPane();
        StackPane gridStack = new StackPane(gridPane);
        for (int j = 0; j < cells; j ++) {
            for (int i = 0; i < cells; i ++) {
                int rectSize = gridCellSize - BORDER;
                Rectangle rectangle = new Rectangle(rectSize, rectSize);
                if (i%2==0 && j%2==0 || i%2!=0 && j%2!=0) {
                    rectangle.setFill(Color.web("#292929"));
                } else {
                    rectangle.setFill(Color.web("#3b3b3b"));
                }
                StackPane stackPane = new StackPane(rectangle);
                stackPane.setMinSize(gridCellSize, gridCellSize);
                gridPane.add(stackPane, i, j);
            }
        }
        return gridStack;
    }
}
