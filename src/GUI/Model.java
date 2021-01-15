package GUI;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.sun.xml.internal.ws.resources.UtilMessages;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



public class Model {
    private final int columns, rows;//grid dimensions
    private LinkedList<int[]> snake; //snake as linked list of row and columns
    private int[] apple;//apple position stored as row, column
    private Direction direction;//the direction the snake is heading

    //constants for accessing coordinates from int[]
    private static final int ROW = 0;
    private static final int COLUMN = 1;

    public Model() {
        columns = rows = 10;

        direction = Direction.RIGHT;//default starting direction
        addSnake();
        addApple();
    }

    /**
     * Initialise snake with default starting positions.
     */
    private void addSnake() {
        snake = new LinkedList<>();

        //start snake head close to board center
        for (int i = 0; i < 3; i++) {
            int[] tailPiece = {rows/2 - 1, columns/2 - 2 - i};
            snake.addLast(tailPiece);
        }
    }

    /**
     * Add apple to the grid with default starting position.
     */
    private void addApple() {
        apple = new int[2];
        //start apple near the top right corner
        apple[ROW] = 1;
        apple[COLUMN] = columns - 2;
        //todo make place apple is added random (checking for placing on snake tail)
    }

    /**
     * Moves snake in the direction it's facing. Will wrap around board if on edge.
     *
     * @return
     *       true if apple eaten, false if apple not eaten, null if snake collision
     */
    public Boolean moveSnake() {
        //update head position
        int[] oldHead = snake.getFirst();
        int[] newHead = oldHead.clone();
        switch (direction) {
            case RIGHT:
                newHead[COLUMN] = (oldHead[COLUMN] + 1) % columns;
                break;
            case DOWN:
                newHead[ROW] = (oldHead[ROW] + 1) % rows;
                break;
            case LEFT:
                newHead[COLUMN] = (oldHead[COLUMN] - 1 + columns) % columns;
                break;
            case UP:
                newHead[ROW] = (oldHead[ROW] - 1 + rows) % rows;
                break;
            default:
                System.out.println("--- moveHead() - bad value for head direction? ---");
                //throw UnexpectedItemInBaggingAreaException
        }
        //check for collision
        for (int[] segment : snake) {
            if (segment[0] == newHead[0] && segment[1] == newHead[1]) {
                //collision
                System.out.println("Collision!");
                return null;
            }
        }

        //add new head position to snake
        snake.addFirst(newHead);
        //If no apple was eaten, remove the end of the snake to keep length the same
        if (!Arrays.equals(apple, newHead)) snake.removeLast();

        //Return if apple was eaten
        return Arrays.equals(apple, newHead);
    }

    /**
     * Changes the snake head's direction to the one provided as long as it is valid.
     * (e.g. cannot go from RIGHT to LEFT)
     *
     * @param newDirection
     *              int describing new snake direction: RIGHT, DOWN, LEFT, UP (0,1,2,3)
     */
    public void setDirection(Direction newDirection) {
        if (isNewDirectionValid(direction, newDirection)) {
            direction = newDirection;
        }
    }

    /**
     * Finds if the new direction is valid compared to the old one.
     * The new direction must not be opposite to the old one.
     *
     * @param oldDirection
     *                  The direction the snake is currently going
     * @param newDirection
     *                  The direction the snake wants to take now
     * @return
     *                  true if the new direction is valid and false otherwise
     */
    private boolean isNewDirectionValid(Direction oldDirection, Direction newDirection) {
        return oldDirection.opposite != newDirection;
    }


    /* -------------------- GETTERS -------------------- */

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int[] getApple() {
        return apple;
    }

    public LinkedList<int[]> getSnake() {
        return snake;
    }

    public int[] getHead() {
        return getSnake().getFirst();
    }

    /* -------------------- enum -------------------- */

    public enum Direction {
        RIGHT,
        DOWN,
        LEFT,
        UP;

        private Direction opposite;

        static {
            RIGHT.opposite = LEFT;
            LEFT.opposite = RIGHT;
            DOWN.opposite = UP;
            UP.opposite = DOWN;
        }

        public Direction getOpposite() {
            return opposite;
        }
    }
}
