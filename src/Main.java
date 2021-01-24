import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static final int BORDER = 1,
            GRID_SIZE = 600,
            NUM_CELLS = 10,
            CELL_SIZE = GRID_SIZE/NUM_CELLS,
            SNAKE_SIZE = (int) (CELL_SIZE*0.5),
            EDGE_SIZE =  (CELL_SIZE - SNAKE_SIZE)/2 - 5;//size between snake and cell edge
    private static final Color SNAKE_COLOUR = Color.LAWNGREEN;
    private static final Pos TOP = Pos.TOP_CENTER,
            RIGHT = Pos.CENTER_RIGHT,
            BOTTOM = Pos.BOTTOM_CENTER,
            LEFT = Pos.CENTER_LEFT;
    private static final Pos[] POSITIONS = {TOP, RIGHT, BOTTOM, LEFT};


    private StackPane root;
    ///private List<List<StackPane>> cells;///

    public static void main(String[] args) {
        //launch sets up the application then calls start()
        launch(args);
    }

    @Override
    public void start(Stage stage) {




        Scene scene = setupScene();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();//because set resizeable has a bug that adds padding
        stage.show();

    }

    private StackPane getCellStack(int x, int y) {
        //NB: this method can break if the order of the cells within the grid is not right-to-left, top-to-bottom
        return (StackPane) getGrid().getChildren().get(x + y * NUM_CELLS);
    }

    //VIEW

    private Scene setupScene() {

        StackPane gridStack = setupGrid();

        HBox top = setupHBox();
        HBox bottom = setupHBox();
        VBox right = setupVBox();
        VBox left = setupVBox();

        BorderPane borderPane = new BorderPane(gridStack, top, right, bottom, left);
        borderPane.setMinSize(GRID_SIZE, GRID_SIZE);

        root = new StackPane(borderPane);

        Scene scene = new Scene(root);
        scene.setFill(Color.GREY);

        return scene;
    }

    private VBox setupVBox() {
        int rightHeight = 600;
        int rightWidth = 200;

        VBox right = new VBox();
        Rectangle rectangle = new Rectangle(rightWidth - BORDER, rightHeight - BORDER);
        StackPane stackPane = new StackPane(rectangle);
        stackPane.setMinSize(rightWidth, rightHeight);
        right.getChildren().add(stackPane);
        return right;
    }

    private HBox setupHBox() {
        int topWidth = 1000;
        int topHeight = 100;

        HBox hBox = new HBox();
        Rectangle topRect = new Rectangle(topWidth - BORDER, topHeight - BORDER);
        StackPane topStack = new StackPane(topRect);
        topStack.setMinSize(topWidth, topHeight);
        hBox.getChildren().add(topStack);
        return hBox;
    }

    private StackPane setupGrid() {
        ///cells = new ArrayList<>();///
        ///for (int i = 0; i < CELL_SIZE; i++) cells.add(new ArrayList<>());///

        GridPane gridPane = new GridPane();
        StackPane gridStack = new StackPane(gridPane);
        for (int y = 0; y < NUM_CELLS; y ++) {
            for (int x = 0; x < NUM_CELLS; x ++) {
                StackPane cellStack = makeCellStack(x,y);
                gridPane.add(cellStack, x, y);
                ///cells.get(y).add(x, cellStack);///
            }
        }

        addSnakeToGrid();

        return gridStack;
    }

    private void addSnakeToGrid() {
        //default starting positions
        StackPane cell = getCellStack(1, NUM_CELLS/2);
        addCenterToCell(cell);
    }

    private void addCenterToCell(StackPane cell) {
        Rectangle rectangle = getCentreRectangle(cell);
        rectangle.setFill(SNAKE_COLOUR);

    }

    private Rectangle getCentreRectangle(StackPane cell) {
        for (Node child : cell.getChildren()) {
            Rectangle rectangle = (Rectangle) child;
            if (rectangle.getWidth() == SNAKE_SIZE) {//this is trash
                return rectangle;
            }
        }
        return (Rectangle) cell.getChildren().get(0);//also trash
    }

    private StackPane makeCellStack(int x, int y) {
        List<Rectangle> cellRectangles = makeCellRectangles(x, y);

        ///cells.put(new int[] {x, y}, cellRectangles);///

        StackPane cellStack = new StackPane();
        cellStack.getChildren().addAll(cellRectangles);
        cellStack.setMinSize(CELL_SIZE, CELL_SIZE);

        return cellStack;
    }

    private List<Rectangle> makeCellRectangles(int x, int y) {
        List<Rectangle> cellRectangles = new ArrayList<>();
        cellRectangles.add(makeCellBackgroundRectangle(x,y));
        cellRectangles.add(makeCentreRectangle());
        for (Pos position : POSITIONS) {
            cellRectangles.add(makeEdgeRectangle(position));
        }
        return cellRectangles;
    }

    private Rectangle makeEdgeRectangle(Pos alignment) {
        Rectangle edge;

        boolean topOrBottom = alignment == TOP || alignment == BOTTOM;
        boolean rightOrLeft = alignment == LEFT || alignment == RIGHT;

        if (topOrBottom) {
            edge = new Rectangle(SNAKE_SIZE, EDGE_SIZE);
        } else if (rightOrLeft) {
            edge = new Rectangle(EDGE_SIZE, SNAKE_SIZE);
        } else {
            System.out.println("Edge Rectangle given wrong alignment");
            return new Rectangle(CELL_SIZE, CELL_SIZE, Color.RED);
        }
        StackPane.setAlignment(edge, alignment);
        return edge;
    }

    private Rectangle makeCentreRectangle() {
        return new Rectangle(SNAKE_SIZE, SNAKE_SIZE);
    }

    private Rectangle makeCellBackgroundRectangle(int x, int y) {
        int rectSize = CELL_SIZE - BORDER;
        Rectangle background = new Rectangle(rectSize, rectSize);
        if (x%2==0 && y%2==0 || x%2!=0 && y%2!=0) {
            background.setFill(Color.web("#292929"));
        } else {
            background.setFill(Color.web("#3b3b3b"));
        }
        return background;
    }

    private GridPane getGrid() {
        //root:StackPane -> {borderPane -> {center -> {gridStack -> {gridPane -> {stackPane -> {rectangles}}}}, .
        // ..}}
        BorderPane borderPane = (BorderPane) root.getChildren().get(0);
        StackPane gridStack = (StackPane) borderPane.getCenter();
        return (GridPane) gridStack.getChildren().get(0);
    }


}
