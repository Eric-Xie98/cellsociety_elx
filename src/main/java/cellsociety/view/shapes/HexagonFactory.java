package cellsociety.view.shapes;

import javafx.scene.shape.Polygon;

public class HexagonFactory {
    public static Polygon createHexagonTopFirst(double cellWidth, double cellHeight){
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(
                cellWidth / 2, 0.0,
                cellWidth, cellHeight / 4,
                cellWidth, (3 * cellHeight) / 4,
                cellWidth / 2, cellHeight,
                0.0, (3 * cellHeight) / 4,
                0.0, cellHeight / 4);

        return hexagon;
    }
}
