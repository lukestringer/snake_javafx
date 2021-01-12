import java.util.LinkedList;

public class Model {
    private final int columns, rows;//grid dimensions
    private final int[][] grid;//grid state
    private int[] apple;//apple position stored as row, column
    private int[] head;//snake head row and column
    private int direction;//the direction the snake is heading
    private LinkedList<int[]> tail; //snake tail as linked list of row and columns

    /* -------------------- CONSTANTS -------------------- */
    //IMPORTANT - all of grid[] will be EMPTY (0) by default
    private static final int EMPTY = 0;
    private static final int SNAKE = 1;
    private static final int APPLE = 2;

    //constants for accessing and storing apple and head info
    private static final int ROW = 0;
    private static final int COLUMN = 1;
    //I thought Java enums worked like C enums... this is the easiest alternative
    private static final int RIGHT = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int UP = 3;

    public Model() {
        columns = rows = 10;
        grid = new int[columns][rows];
        addSnake();
        addApple();
    }

    /**
     * Initialise head and tail, adding snake to the grid with default starting positions.
     */
    private void addSnake() {
        //start snake head close to board center
        head[ROW] = rows/2 - 2;
        head[COLUMN] = columns/2;
        direction = RIGHT;

        //add snake head to grid
        updateGridCell(head, SNAKE);

        //add tail to list and grid (two of the cells to the left of the head)
        tail = new LinkedList<>();
        for (int i = 1; i < 3; i++) {
            int[] tailPiece = {head[ROW], head[COLUMN] - i};
            tail.addLast(tailPiece);
            updateGridCell(tailPiece, SNAKE);
        }
    }

    /**
     * Add apple to the grid with default starting position.
     */
    private void addApple() {
        //start apple near the top right corner
        apple[ROW] = 1;
        apple[COLUMN] = columns - 2;

        //add apple a couple away from snake head to the right
        updateGridCell(apple, APPLE);
    }

    /**
     * Updates the grid cell at the given row and column with the given new state.
     * State should be one of EMPTY, SNAKE, APPLE.
     *
     * @param cell
     *          the row and column of the cell to update
     * @param newState
     *          the new state for the cell to be updated to
     * @return
     *          the old state that the cell used to be
     */
    private int updateGridCell(int[] cell, int newState) {
        int row = cell[ROW];
        int column = cell[COLUMN];

        int oldState = grid[row][column];
        grid[row][column] = newState;

        return oldState;
    }

    public int moveSnake() {
        //move the head and get the state of the cell it will move into
        int cellState = moveHead();
        moveTail();
        return cellState;
    }

    /**
     * Remove the end of the tail and add the head position to the start of the tail
     */
    private void moveTail() {
        tail.removeFirst();
        tail.addLast(head);
    }

    /**
     * Moves the head by one cell in the direction it is currently facing.
     * Will wrap around when hitting edge of grid.
     *
     * @return
     *      the state of the cell being moved into. EMPTY, SNAKE, APPLE (0,1,2)
     */
    private int moveHead() {
        //update head position
        switch (direction) {
            case RIGHT:
                head[ROW] = (head[ROW] + 1) % rows;
                break;
            case DOWN:
                head[COLUMN] = (head[COLUMN] + 1) % columns;
                break;
            case LEFT:
                head[ROW] = (head[ROW] - 1 + rows) % rows;
                break;
            case UP:
                head[COLUMN] = (head[COLUMN] - 1 + columns) % columns;
                break;
            default:
                System.out.println("--- moveHead() - bad value for head direction? ---");

        }
        //update new cell with head, returning its previous value
        return updateGridCell(head, SNAKE);
    }

    /**
     * Changes the snake head's direction to the one provided.
     * Provided direction should be one of RIGHT, DOWN, LEFT, UP (0,1,2,3)
     *
     * @param newDirection
     *              int describing new snake direction: RIGHT, DOWN, LEFT, UP (0,1,2,3)
     */
    private void changeDirection(int newDirection) {
        direction = newDirection;
    }


    /* -------------------- GETTERS -------------------- */

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
