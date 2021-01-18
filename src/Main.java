
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









        //todo 1 speed up after each apple by stopping old timeline and replacing with faster one (delay in model, timeline in view, controller mediates.)
        //https://stackoverflow.com/questions/19549852/javafx-binding-timelines-duration-to-a-property
        //todo 2 export and deploy as application
        //https://www.youtube.com/watch?v=yG8YCLYccVo
        //todo 3 find out why "Collision!" is being printed to the console before the move has been made (Changed direction in time)
        //todo 4 implement a reset button
        //todo 5 implement a score (with options, so time take reduces score to increase riskier play)
        //todo 6 MAYBE - make ai player (beating game with full screen snake, doing the same with fastest play, having ai for player to vs that loses sometimes)

        //todo refactor code structure. start by looking at that textbook from csse2002, the mvc section
    }
}
