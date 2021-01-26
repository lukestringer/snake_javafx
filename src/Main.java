import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        //launch sets up the application then calls start()
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Cell.setDimensions(60, 30);
        Cell cell = new Cell(0,0);
        cell.moveSnakeIn(Cell.Edge.TOP);
        cell.continueSnake(Cell.Edge.RIGHT);
        Scene scene = new Scene(cell.getStackPane());
        scene.setFill(Color.GREY);
        stage.setScene(scene);
        stage.show();
    }
}
