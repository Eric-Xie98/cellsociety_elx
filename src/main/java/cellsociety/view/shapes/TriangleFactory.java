package cellsociety.view.shapes;

import javafx.scene.shape.Polygon;

public class TriangleFactory {

  public static Polygon createTriangleTop(double cellWidth, double cellHeight){
    Polygon triangle = new Polygon();
    triangle.getPoints().addAll(0.0, 0.0,
        cellWidth, 0.0,
        cellWidth/2, cellHeight);
    return triangle;
  }

  public static Polygon createTriangleBottom(double cellWidth, double cellHeight){
    Polygon triangle = new Polygon();
    triangle.getPoints().addAll(cellWidth, cellHeight,
        cellWidth/2, 0.0,
        0.0, cellHeight);
    return triangle;
  }

}
