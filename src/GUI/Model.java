package GUI;

import java.util.LinkedList;

public class Model {

    private final static byte ROWS = 10;
    private final static byte COLUMNS = 10;
    //todo add speed up
    //private final static float difficulty =

    private LinkedList<Coordinates> snake;
    private Coordinates justRemoved;//makes it easy to update view to track where tail just was
    private Coordinates apple;
    private Direction direction;

    private int moveDelay;//milliseconds between moves
    private static final double SPEED_MODIFIER = 0.975;//reduce move delay by this factor
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
        moveDelay = 400;//set milliseconds between snake movements
        justEaten = false;
        collision = false;
    }

    public void moveSnake() {
        Coordinates newHead = makeNewHead();
        justEaten = newHead.equals(apple);
        if (justEaten) {
            newApple();
            justRemoved = null;
            moveDelay *= SPEED_MODIFIER;
        } else {
            justRemoved = snake.removeLast();
        }
        collision = checkCollision(newHead);
        snake.addFirst(newHead);
    }

    public Coordinates getHead() {
        return new Coordinates(snake.getFirst().row(), snake.getFirst().column());
    }

    private Coordinates makeNewHead() {
        Coordinates oldHead = snake.getFirst();
        Coordinates newHead = new Coordinates(oldHead.row(), oldHead.column());
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

    public LinkedList<Coordinates> getSnake() {
        LinkedList<Coordinates> copy = new LinkedList<>();
        for (Coordinates snakeCoord : snake) {
            copy.add(new Coordinates(snakeCoord.row(), snakeCoord.column()));
        }
        return copy;
    }

    public int maxRows() {
        return ROWS;
    }

    public int maxColumns() {
        return COLUMNS;
    }

    public Coordinates getApple() {
        return new Coordinates(apple.row(), apple.column());
    }

    public int getMoveDelay() {
        return moveDelay;
    }

    public Coordinates getJustRemoved() {
        if (justRemoved == null) return null;
        return new Coordinates(justRemoved.row(), justRemoved.column());
    }

    public boolean isJustEaten() {
        return justEaten;
    }

    public boolean getCollision() {
        return collision;
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

        private byte row;
        private byte column;

        public Coordinates(byte row, byte column) {
            checkRow(row);
            checkColumn(column);
            this.row = row;
            this.column = column;
        }

        private void checkRow(byte row) {
            if (row >= Model.ROWS) {
                throw new IllegalArgumentException("Row must be < " + Model.ROWS);
            }
        }

        private void checkColumn(byte column) {
            if (column >= Model.COLUMNS) {
                throw new IllegalArgumentException("Column must be < " + Model.COLUMNS);
            }
        }

        public byte row() {
            return row;
        }

        public byte column() {
            return column;
        }

        public void setRow(byte row) {
            checkRow(row);
            this.row = row;
        }

        public void setColumn(byte column) {
            checkColumn(column);
            this.column = column;
        }


        @Override
        public String toString() {
            return "Coordinates{" +
                    "row=" + row +
                    ", column=" + column +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinates that = (Coordinates) o;

            if (row != that.row) return false;
            return column == that.column;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + (int) column;
            return result;
        }
    }
}
