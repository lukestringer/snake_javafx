import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private final GridPane gridPane;
    private final List<List<Cell>> cells;

    public Grid(int size) {
        this(size, size);
    }

    public Grid(int width, int height) {
        gridPane = new GridPane();
        cells = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            cells.add(new ArrayList<>());
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell(x, y);
                cells.get(y).add(cell);
                gridPane.add(cell.getStackPane(), x, y);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells.get(y).get(x);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

}
