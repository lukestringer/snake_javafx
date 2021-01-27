import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

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

    private final StackPane stackPane;
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
        Rectangle background = new Rectangle(0, 0, size, size);
        stackPane.getChildren().add(background);
        //checkerboard pattern
        if (x%2==0 && y%2==0 || x%2!=0 && y%2!=0) {
            background.setFill(Color.web("#292929"));
        } else {
            background.setFill(Color.web("#3b3b3b"));
        }
    }

    private void addToCell(Node node) {
        stackPane.getChildren().add(node);
    }

    private void removeFromCell(Node node) {
        stackPane.getChildren().remove(node);
    }

    public void makeSnakeHead(Edge in) {
        if (state != State.EMPTY) throw new IllegalStateException("Cell must be empty() before snake can move in.");
        state = State.SNAKE;
        snake = new Snake(in);
    }

    public void makeSnakeBody(Edge out) {
        if (state != State.SNAKE) throw new IllegalStateException("Need a snake before makeSnakeBody()");
        snake.makeBody(out);
    }

    public void makeSnakeTail() {
        if (state != State.SNAKE) {
            throw new IllegalStateException("Can't make a snake tail if the cell doesn't have a snake yet.");
        }
        snake.makeTail();
    }

    public void empty() {
        if (state == State.SNAKE) {
            if (snake.in != null) removeFromCell(snake.in);
            if (snake.out != null) removeFromCell(snake.out);
            removeFromCell(snake.head);
            snake = null;
        } else if (state == State.APPLE) {
            removeFromCell(apple);
            apple = null;
        }
        state = State.EMPTY;

    }

    public void putApple() {
        if (state != State.EMPTY) throw new IllegalStateException("Cell must be empty() before putting apple in.");
        state = State.APPLE;
        apple = new Circle(APPLE_SIZE, APPLE_COLOUR);
        addToCell(apple);
    }

    public Pane getStackPane() {
        return stackPane;
    }


    private class Snake {
        Rectangle head, in, out;//edge that snake came in and went out on
        // ('out' is null if this is head of snake, neither null if body, 'in' is null if this is tail)

        protected Snake(Edge inEdge) {
            in = makeEdgeRectangle(inEdge);
            addToCell(in);
            head = new Rectangle(BORDER, BORDER, SNAKE_SIZE, SNAKE_SIZE);
            head.setFill(Cell.SNAKE_COLOUR);
            addToCell(head);
        }

        private Edge alignment(Rectangle rectangle) {
            return Edge.forPos(StackPane.getAlignment(rectangle));
        }

        /**
         * Turns the snake from a snake head to a snake body part, with in and out edges.
         * @param outEdge
         *          The edge of the rectangle for the snake head to leave the cell by
         */
        public void makeBody(Edge outEdge) {
            if (this.out != null) throw new IllegalStateException("Snake already has already gone out edge.");
            if (outEdge == alignment(in)) throw new IllegalArgumentException("Snake can't come in where it went out");
            out = makeEdgeRectangle(outEdge);
            addToCell(out);
        }

        /**
         * Sets the edge the snake came in by to null, turning the snake into a tail
         */
        public void makeTail() {
            if (this.out == null) throw new IllegalStateException("Need to call makeBody() before makeTail()");
            removeFromCell(in);
            in = null;
        }

        private Rectangle makeEdgeRectangle(Edge edge) {
            //top and bottom are as wide as the snake and as high as the border
            //left and right are as wide as the border and as high as the snake
            double width = (edge == Edge.TOP || edge == Edge.BOTTOM) ? SNAKE_SIZE : BORDER;
            double height = (edge == Edge.TOP || edge == Edge.BOTTOM) ? BORDER : SNAKE_SIZE;

            Rectangle edgeRectangle = new Rectangle(width, height);
            edgeRectangle.setFill(Cell.SNAKE_COLOUR);
            StackPane.setAlignment(edgeRectangle, edge.alignment);
            return edgeRectangle;
        }
    }


    enum Edge {
        TOP(Pos.TOP_CENTER),
        BOTTOM(Pos.BOTTOM_CENTER),
        LEFT(Pos.CENTER_LEFT),
        RIGHT(Pos.CENTER_RIGHT);

        public final Pos alignment;

        Edge(Pos alignment) {
            this.alignment = alignment;
        }

        static final Map<Pos, Edge> posToEdge = new HashMap<>();
        static {
            for (Edge e : values()) {
                posToEdge.put(e.alignment, e);
            }
        }

        public static Edge forPos(Pos pos) {
            return posToEdge.get(pos);
        }
    }
}
