package cellsociety.view.grid;

import cellsociety.view.GridView;
import cellsociety.view.SimulationInfo;
import cellsociety.view.shapes.RectangleFactory;
import javafx.scene.shape.Shape;

/**
 * Extends gridview to create specific Rectangular shapes as cells
 *
 * @author Robert Cranston
 */
public class RectangleGridView extends GridView {

  public RectangleGridView(SimulationInfo record) {
    super(record);
  }
  @Override
  public void setupGrid(){
    getGridPane().setMaxHeight(350);
    getGridPane().setMaxWidth(340);
    getGridPane().setHgap(2);
    getGridPane().setVgap(2);
  }

  @Override
  public Shape createShape(int j, int i){
    Shape cell = RectangleFactory.createRectangle(getCellWidth(), getCellHeight());
    return cell;
  }

}
