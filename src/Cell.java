import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents one cell in the game of snake. Tracks cell state and contents.
 * If cell is snake body part, tracks how snake moved through this cell.
 * e.g. the snake came in by going up, so it will be drawn starting from the bottom edge.
 */
public class Cell {

    private static final Color SNAKE_COLOUR = Color.LAWNGREEN;
    private static int CELL_SIZE = 0,
            SNAKE_SIZE = 0,
            BORDER = 0;

    private State state;
    private int x, y;

    private StackPane stackPane;
    private Rectangle background;
    private Snake snake;

    public Pane getStackPane() {
        return stackPane;
    }

    enum State {
        EMPTY,
        SNAKE,
        APPLE;
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

    public static void setDimensions(int CELL_SIZE, int SNAKE_SIZE) {
        if (SNAKE_SIZE > CELL_SIZE) {
            throw new IllegalArgumentException("Snake size must be less or equal to cell size.");
        } else if ( CELL_SIZE <= 0 || SNAKE_SIZE <= 0) {
            throw new IllegalArgumentException("Cell and snake size must be greater than 0");
        }
        Cell.CELL_SIZE = CELL_SIZE;
        Cell.SNAKE_SIZE = SNAKE_SIZE;
        Cell.BORDER = (int) Math.ceil((CELL_SIZE - SNAKE_SIZE)/2.0);//ceil makes sure no gap of 1 pixel
    }

    private void setBackground() {
        double size = CELL_SIZE;
        background = new Rectangle(0, 0, size, size);
        if (x%2==0 && y%2==0 || x%2!=0 && y%2!=0) {
            background.setFill(Color.web("#292929"));
        } else {
            background.setFill(Color.web("#3b3b3b"));
        }
        stackPane.setMaxSize(size, size);
    }

    private void addRectangle(Rectangle rectangle) {
        stackPane.getChildren().add(rectangle);
    }

    public void moveSnakeIn(Edge in) {
        state = State.SNAKE;
        snake = new Snake(in);
        drawSnake();
    }

    public void continueSnake(Edge out) {
        if (state != State.SNAKE) {
            throw new IllegalStateException("Can't continue snake if haven't used moveSnakeIn()");
        }
        addRectangle(snake.goOut(out));
        drawSnake();
    }

    public void empty() {
        state = State.EMPTY;
        snake = null;
    }

    private void drawSnake() {

    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    private class Snake {
        Rectangle head;
        Edge in, out;//edge that snake came in and went out on
        // (both null if this isn't snake, out null if this is head of snake)

        protected Snake(Edge in) {
            this.in = in;
            addRectangle(this.in.rectangle);
            head = new Rectangle(BORDER, BORDER, SNAKE_SIZE, SNAKE_SIZE);
            head.setFill(Cell.SNAKE_COLOUR);
            addRectangle(head);
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

        private Edge(Rectangle rectangle) {
            System.out.println(rectangle);
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
