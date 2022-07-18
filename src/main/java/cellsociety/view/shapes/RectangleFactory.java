package cellsociety.view.shapes;

import javafx.scene.shape.Rectangle;

public class RectangleFactory {
  public static Rectangle createRectangle(double cellWidth, double cellHeight){
    Rectangle rect = new Rectangle();
    rect.setX(0);
    rect.setY(0);

    rect.setHeight(cellHeight);
    rect.setWidth(cellWidth);
    return rect;
  }
}
