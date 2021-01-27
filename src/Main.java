import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        //launch sets up the application then calls start()
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GridPane gridPane = new GridPane();

        Cell.setDimensions(60, 30, 20);
        Cell cell = new Cell(0,0);
        cell.moveSnakeIn(Cell.Edge.TOP);
        cell.moveSnakeOut(Cell.Edge.RIGHT);

        gridPane.add(cell.getStackPane(), 0, 0);

        Scene scene = new Scene(gridPane);
        scene.setFill(Color.GREY);

        stage.setScene(scene);
        stage.show();

        cell.empty();

        cell.moveSnakeIn(Cell.Edge.LEFT);
        cell.moveSnakeOut(Cell.Edge.BOTTOM);

        //cell.empty();

        cell.putApple();

        //cell.empty();
    }
}
