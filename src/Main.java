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

        int GRID_CELL_SIZE = 10, GRID_PIXEL_SIZE = 600;

        int CELL_SIZE = GRID_PIXEL_SIZE/GRID_CELL_SIZE,
                SNAKE_SIZE = CELL_SIZE/2,
                APPLE_SIZE = CELL_SIZE/3;

        Cell.setDimensions(CELL_SIZE, SNAKE_SIZE, APPLE_SIZE);

        Grid grid = new Grid(GRID_CELL_SIZE);

        grid.getCell(5, 5).makeSnakeHead(Cell.Edge.LEFT);

        grid.getCell(7, 7).makeSnakeHead(Cell.Edge.LEFT);
        grid.getCell(7, 7).makeSnakeBody(Cell.Edge.TOP);
        //grid.getCell(7, 7).makeSnakeTail();

        /*Cell tail = grid.getCell(1, 5 );
        tail.makeSnakeHead(Cell.Edge.TOP);
        tail.makeSnakeBody(Cell.Edge.RIGHT);
        tail.makeSnakeTail();

        Cell body = grid.getCell(2, 5);
        body.makeSnakeHead(Cell.Edge.LEFT);
        body.makeSnakeBody(Cell.Edge.RIGHT);

        Cell head = grid.getCell(3, 5);
        head.makeSnakeHead(Cell.Edge.LEFT);


        grid.getCell(6,6).putApple();*/


        Scene scene = new Scene(grid.getGridPane());
        scene.setFill(Color.GREY);

        stage.setScene(scene);
        stage.show();
    }
}
