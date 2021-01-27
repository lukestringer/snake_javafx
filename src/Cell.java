import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Represents one cell in the game of snake. Tracks cell state and contents.
 * If cell is snake body part, tracks how snake moved through this cell.
 * e.g. the snake came in by going up, so it will be drawn starting from the bottom edge.
 */
public class Cell {

    private static final Color SNAKE_COLOUR = Color.LAWNGREEN;
    private static final Color APPLE_COLOUR = Color.RED;
    private static int CELL_SIZE = 0,//makes it clear their default is 0
            BORDER = 0,
            SNAKE_SIZE = 0,
            APPLE_SIZE = 0;

    private State state;
    private final int x, y;

    private StackPane stackPane;
    private Rectangle background;
    private Snake snake;
    private Circle apple;

    enum State {
        EMPTY,
        SNAKE,
        APPLE
    }

    public Cell(int x, int y) {
        if (CELL_SIZE == 0 || SNAKE_SIZE == 0) {
            throw new IllegalStateException("Please use Cell.setDimensions() before making any cells.");
        }
        this.x = x;
        this.y = y;
        this.state = State.EMPTY;
        stackPane = new StackPane();
        setBackground();
    }

    public static void setDimensions(int CELL_SIZE, int SNAKE_SIZE, int APPLE_SIZE) {
        if (SNAKE_SIZE > CELL_SIZE || APPLE_SIZE > CELL_SIZE) {
            throw new IllegalArgumentException("Snake and apple sizes must be less or equal to cell size.");
        } else if ( CELL_SIZE <= 0 || SNAKE_SIZE <= 0 || APPLE_SIZE <= 0) {
            throw new IllegalArgumentException("Sizes must be greater than 0");
        }
        Cell.CELL_SIZE = CELL_SIZE;
        Cell.BORDER = (int) Math.ceil((CELL_SIZE - SNAKE_SIZE)/2.0);//ceil makes sure no gap of 1 pixel
        Cell.SNAKE_SIZE = SNAKE_SIZE;
        Cell.APPLE_SIZE = APPLE_SIZE;

    }

    private void setBackground() {
        double size = CELL_SIZE;
        stackPane.setMaxSize(size, size);
        background = new Rectangle(0, 0, size, size);
        stackPane.getChildren().add(background);
        //checkerboard pattern
        if (x%2==0 && y%2==0 || x%2!=0 && y%2!=0) {
            background.setFill(Color.web("#292929"));
        } else {
            background.setFill(Color.web("#3b3b3b"));
        }
    }

    private void addNode(Node node) {
        stackPane.getChildren().add(node);
    }

    private void removeNode(Node node) {
        stackPane.getChildren().remove(node);
    }

    public void moveSnakeIn(Edge in) {
        if (state != State.EMPTY) throw new IllegalStateException("Cell must be empty() before snake can move in");
        state = State.SNAKE;
        snake = new Snake(in);
    }

    public void moveSnakeOut(Edge out) {
        if (state != State.SNAKE) {
            throw new IllegalStateException("Can't continue snake if haven't used moveSnakeIn()");
        } else if (out == snake.in) {
            throw new IllegalArgumentException("Snake can't double back on itself (in cannot equal out)");
        }
        addNode(snake.goOut(out));
    }

    public void empty() {
        if (state == State.SNAKE) {
            removeNode(snake.in.rectangle);
            removeNode(snake.out.rectangle);
            removeNode(snake.head);
            snake = null;
        } else if (state == State.APPLE) {
            removeNode(apple);
            apple = null;
        }
        state = State.EMPTY;

    }

    public void putApple() {
        if (state != State.EMPTY) throw new IllegalStateException("Cell must be empty() before putting apple in.");
        state = State.APPLE;
        apple = new Circle(APPLE_SIZE, APPLE_COLOUR);
        addNode(apple);
    }

    public Pane getStackPane() {
        return stackPane;
    }


    private class Snake {
        Rectangle head;
        Edge in, out;//edge that snake came in and went out on
        // (both null if this isn't snake, out null if this is head of snake)

        protected Snake(Edge in) {
            this.in = in;
            addNode(this.in.rectangle);
            head = new Rectangle(BORDER, BORDER, SNAKE_SIZE, SNAKE_SIZE);
            head.setFill(Cell.SNAKE_COLOUR);
            addNode(head);
        }

        public Rectangle goOut(Edge edge) {
            if (this.out != null) throw new IllegalStateException("Snake already has already gone out edge.");
            this.out = edge;
            return this.out.rectangle;
        }
    }

    enum Edge {
        TOP(Edge.makeRectangle(SNAKE_SIZE, BORDER, Pos.TOP_CENTER)),
        BOTTOM(Edge.makeRectangle(SNAKE_SIZE, BORDER, Pos.BOTTOM_CENTER)),
        LEFT(Edge.makeRectangle(BORDER, SNAKE_SIZE, Pos.CENTER_LEFT)),
        RIGHT(Edge.makeRectangle(BORDER, SNAKE_SIZE, Pos.CENTER_RIGHT));

        public final Rectangle rectangle;

        Edge(Rectangle rectangle) {
            this.rectangle = rectangle;
        }

        private static Rectangle makeRectangle(double width, double height, Pos alignment) {
            Rectangle rectangle = new Rectangle(width, height);
            rectangle.setFill(Cell.SNAKE_COLOUR);
            StackPane.setAlignment(rectangle, alignment);
            return rectangle;
        }
    }
}
