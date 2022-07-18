package cellsociety.view.grid;

import cellsociety.view.GridView;
import cellsociety.view.SimulationInfo;
import cellsociety.view.shapes.TriangleFactory;
import javafx.scene.shape.Shape;

/**
 * Extends gridview to create specific triangular shapes as cells
 *
 * @author Robert Cranston
 */
public class TriangleGridView extends GridView {

  public TriangleGridView(SimulationInfo record) {
    super(record);
  }

  @Override
  public void setupGrid() {
    getGridPane().setMaxHeight(350);
    getGridPane().setMaxWidth(355);
    getGridPane().setHgap(-150);
    getGridPane().setVgap(1);
  }

  @Override
  public Shape createShape(int j, int i) {
    Shape cell;
    cell = ((j + i) % 2) == 1 ? TriangleFactory.createTriangleTop(getCellWidth(), getCellHeight())
        : TriangleFactory.createTriangleBottom(getCellWidth(), getCellHeight());

    return cell;
  }
}
