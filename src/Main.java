
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        //launch sets up the application then calls start()
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        int topWidth = 1000;
        int topHeight = 100;

        int bottomWidth = 1000;
        int bottomHeight = 100;

        int rightHeight = 600;
        int rightWidth = 200;

        int leftHeight = 600;
        int leftWidth = 200;

        int gridSize = 600;
        int gridCellSize = 60;
        int border = 10;


        GridPane gridPane = new GridPane();
        StackPane gridStack = new StackPane(gridPane);
        for (int j = 0; j < 10; j ++) {
            for (int i = 0; i < 10; i ++) {
                Rectangle rectangle = new Rectangle(gridCellSize - border, gridCellSize - border);
                rectangle.setFill(Color.BLACK);
                StackPane stackPane = new StackPane(rectangle);
                stackPane.setMinSize(gridCellSize, gridCellSize);
                gridPane.add(stackPane, i, j);
            }
        }


        HBox top = new HBox();
        Rectangle topRect = new Rectangle(topWidth - border, topHeight - border);
        StackPane topStack = new StackPane(topRect);
        topStack.setMinSize(topWidth, topHeight);
        top.getChildren().add(topStack);

        HBox bottom = new HBox();
        Rectangle bottomRect = new Rectangle(bottomWidth - border, bottomHeight - border);
        StackPane bottomStack = new StackPane(bottomRect);
        bottomStack.setMinSize(bottomWidth, bottomHeight);
        bottom.getChildren().add(bottomStack);

        VBox right = new VBox();
        Rectangle rectangle = new Rectangle(rightWidth - border, gridSize - border);
        StackPane stackPane = new StackPane(rectangle);
        stackPane.setMinSize(rightWidth, rightHeight);
        right.getChildren().add(stackPane);

        VBox left = new VBox();
        Rectangle leftRect = new Rectangle(leftWidth - border, leftHeight - border);
        StackPane leftStack = new StackPane(leftRect);
        leftStack.setMinSize(leftWidth, leftHeight);
        left.getChildren().add(leftStack);

        BorderPane borderPane = new BorderPane(gridStack, top, right, bottom, left);
        borderPane.setMinWidth(gridSize);
        borderPane.setMinHeight(gridSize);
        StackPane root = new StackPane(borderPane);
        Scene scene = new Scene(root);
        scene.setFill(Color.GREY);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();//because set resizeable has a bug that adds padding
        stage.show();
    }
}
