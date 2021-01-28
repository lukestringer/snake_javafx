import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Grid {

    private final GridPane gridPane;
    private final List<List<Cell>> cells;
    private final int cellsPerSide;
    private LinkedList<Cell> snake;
    private Cell apple;

    public Grid(int cellsPerSide, int cellSize) {
        this.cellsPerSide = cellsPerSide;
        Cell.setDimensions(cellSize);
        gridPane = new GridPane();
        cells = setupCells();
        setupSnakeAndApple();
    }

    private List<List<Cell>> setupCells() {
        List<List<Cell>> cells = new ArrayList<>();
        for (int y = 0; y < cellsPerSide; y++) {
            cells.add(new ArrayList<>());
            for (int x = 0; x < cellsPerSide; x++) {
                Cell cell = new Cell(x, y);
                cells.get(y).add(cell);
                gridPane.add(cell.getStackPane(), x, y);
            }
        }
        return cells;
    }

    public Cell getCell(int x, int y) {
        try {
            return cells.get(y).get(x);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    public Cell getCell(int[] coords) {
        return cells.get(coords[1]).get(coords[0]);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Cell getNeighbour(Cell cell, Cell.Edge edge) {
        int dY = 0;
        int dX = 0;
        switch (edge) {
            case TOP:       dY--;    break;
            case BOTTOM:    dY++;    break;
            case LEFT:      dX--;    break;
            case RIGHT:     dX++;    break;
        }
        return getCell(cell.getX() + dX, cell.getY() + dY);
    }

    public void setupSnakeAndApple() {
        snake = new LinkedList<>();
        int totalParts = 3;//total num of parts in default snake
        for (int i = 0; i < totalParts; i++) {
            int x = i+1;
            Cell cell = getCell(x, cellsPerSide/2);
            cell.makeSnakeHead(Cell.Edge.LEFT);
            //if this isn't the last snake part (i.e. the head), make it a body
            if (x != totalParts) cell.makeSnakeBody(Cell.Edge.RIGHT);
            snake.addFirst(cell);
        }
        snake.getLast().makeSnakeTail();//make the last cell in the snake look like a tail

        apple = getCell(cellsPerSide - 2, cellsPerSide - 2);
        apple.putApple();
    }

    public void moveSnake(Cell.Edge direction) {
        Cell neighbour = getNeighbour(snake.getFirst(), direction);
        if (neighbour == null) {
            //hit edge of board
        } else {
            switch (neighbour.getState()) {
                case EMPTY:
                    snake.removeLast().empty();
                    snake.getLast().makeSnakeTail();
                    snake.getFirst().makeSnakeBody(direction);
                    neighbour.makeSnakeHead(direction.getOpposite());
                    snake.addFirst(neighbour);

                    break;
                case SNAKE:
                    break;
                case APPLE:
                    break;
            }
        }
    }
}
