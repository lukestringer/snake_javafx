
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
        int gameStageWidth, gameStageHeight;//width and height of game area of stage
        gameStageHeight = gameStageWidth = 600;//game is a square, but can be changed
        Model model = new Model();
        View view = new View(model, gameStageWidth, gameStageHeight);
        new Contoller(view, model);

        String title = "Snake";
        stage.setScene(view.getScene());
        stage.setResizable(false);//maybe one day: resizeable game window
        //set up scene further here e.g. min height
        stage.setTitle(title);

        stage.show();
    }
}
