
import GUI.Contoller;
import GUI.Model;
import GUI.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        //launch sets up the application then calls start()
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Contoller contoller = new Contoller(stage);
        contoller.launchGame();

    }
}
