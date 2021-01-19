package GUI;


import javafx.stage.Stage;

public class Contoller {
    Stage stage;
    private final Model model;
    private final View view;

    public Contoller(Stage stage) {
        this.stage = stage;

        model = new Model();
        view = new View(model, stage);

   }

   public void launchGame() {
        view.startTimeline();
   }

}
