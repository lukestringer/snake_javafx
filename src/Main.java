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

        Grid grid = new Grid(10, 60);
        GameLoop gameLoop = new GameLoop(grid);


        Scene scene = new Scene(gameLoop.getRoot());
        scene.setFill(Color.GREY);

        stage.setScene(scene);
        stage.show();
    }
}
