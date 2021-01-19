package GUI;

import java.util.Arrays;
import java.util.LinkedList;

public class Model {

    private final static byte ROWS = 10;
    private final static byte COLUMNS = 10;

    private LinkedList<Coordinates> snake;
    private Coordinates apple;
    private Direction direction;
    private int gameSpeed;//milliseconds between moves
    private boolean justEaten;//did the snake eat an apple on the most recent move?
    private boolean collision;//did the snake collide on the most recent move?


    public Model() {
        resetGame();
    }

    /**
     * Sets up the default game state.
     * Affects all the private, non-static, non-final variables.
     */
    public void resetGame() {
        snake = new LinkedList<>();
        direction = Direction.RIGHT;
        for (int i = 0; i < 3; i++) {
            snake.addLast(new Coordinates((byte)(ROWS/2), (byte)(COLUMNS/2 - i)));
        }
        apple = new Coordinates((byte) (ROWS/2), (byte) (COLUMNS - 2));
        gameSpeed = 1000;//set to 1000 milliseconds between snake movements
        justEaten = false;
        collision = false;
    }

    public void moveSnake() {
        Coordinates newHead = getNewHead();
        justEaten = newHead.equals(apple);
        if (justEaten) {
            newApple();
        } else {
            snake.removeLast();
        }
        collision = checkCollision(newHead);
        if (!collision) {
            snake.addFirst(newHead);
        }
    }

    private Coordinates getNewHead() {
        Coordinates oldHead = snake.getFirst();
        Coordinates newHead = new Coordinates(oldHead);
        switch (direction) {
            case RIGHT:
                newHead.setColumn((byte) ((oldHead.column() + 1) % COLUMNS));
                break;
            case DOWN:
                newHead.setRow((byte) ((oldHead.row() + 1) % ROWS));
                break;
            case LEFT:
                newHead.setColumn((byte) ((oldHead.column() - 1 + COLUMNS) % COLUMNS));
                break;
            case UP:
                newHead.setRow((byte) ((oldHead.row() - 1 + ROWS) % ROWS));
                break;
            default:
                System.out.println("--- moveHead() - bad value for head direction? ---");
                //throw UnexpectedItemInBaggingAreaException
        }
        return newHead;
    }

    private boolean checkCollision(Coordinates toCheck) {
        for (Coordinates coord : snake) {
            if (toCheck.equals(coord)) {
                return true;
            }
        }
        return false;
    }

    private void newApple() {
        do {
            byte row = (byte) (Math.random() * ROWS);
            byte column = (byte) (Math.random() * COLUMNS);
            apple = new Coordinates(row, column);
        } while (checkCollision(apple));
    }

    public void changeDirection(Direction newDirection) {
        if (direction.opposite != newDirection) {
            direction = newDirection;
        }
    }

    public Coordinates[] getSnake() {
        return (Coordinates[]) snake.clone();
    }

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

    static class Coordinates {
        private byte[] coord;

        public Coordinates(byte row, byte column) {
            this(new byte[] {row, column});
        }

        public Coordinates(byte[] array) {
            if (array.length != 2) {
                /*throw new Exception*/
                System.out.println("Cannot create Coordinates: int array must be of size two");
            }
            if (rowInvalid(array[0])) {
                /*throw new Exception*/
                System.out.println("Cannot create Coordinates: row must be between 0 and " + (Model.ROWS - 1));
            } else if (columnInvalid(array[1])) {
                /*throw new Exception*/
                System.out.println("Cannot create Coordinates: column must be between 0 and " + (Model.COLUMNS-1));
            }
            coord = array.clone();
        }

        public Coordinates(Coordinates copy) {
            coord = copy.coord;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinates that = (Coordinates) o;

            return Arrays.equals(coord, that.coord);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(coord);
        }

        public byte row() {
            return coord[0];
        }

        public byte column() {
            return coord[1];
        }

        public boolean setCoordinates(byte[] newCoords) {
            if (newCoords.length != 2) return false;
            return setCoordinates(newCoords[0], newCoords[1]);
        }

        /**
         * Sets the coordinates to the provided bytes, if they are valid coords for the model.
         * Returns if the coordinates are valid
         *
         * @param row
         *          row coord to set this to
         * @param column
         *          column coord to set this to
         * @return
         *          true if coordinates are valid, false if not
         */
        public boolean setCoordinates(byte row, byte column) {
            if (coordinatesInvalid(row, column)) {
                return false;
            }
            coord = new byte[] {row, column};
            return true;
        }

        public boolean setRow(byte row) {
            return setCoordinates(row, coord[1]);
        }

        public boolean setColumn(byte column) {
            return setCoordinates(coord[0], column);
        }

        private boolean coordinatesInvalid(byte[] coords) {
            return rowInvalid(coords[0]) || columnInvalid(coords[1]) ;
        }

        private boolean coordinatesInvalid(byte row, byte column) {
            return coordinatesInvalid(new byte[]{row, column});
        }

        private boolean columnInvalid(byte column) {
            return column >= Model.COLUMNS || column < 0;
        }

        private boolean rowInvalid(byte row) {
            return row >= Model.ROWS || row < 0;
        }

    }
}
