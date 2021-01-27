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

        Cell.setDimensions(60, 30, 20);

        Grid grid = new Grid(10, 10);

        Scene scene = new Scene(grid.getGridPane());
        scene.setFill(Color.GREY);

        stage.setScene(scene);
        stage.show();
    }
}
