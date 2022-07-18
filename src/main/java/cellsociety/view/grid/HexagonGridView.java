package cellsociety.view.grid;

import cellsociety.view.GridView;
import cellsociety.view.SimulationInfo;
import cellsociety.view.shapes.HexagonFactory;
import javafx.scene.shape.Shape;

public class HexagonGridView extends GridView {

    public HexagonGridView(SimulationInfo record) {
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
        Shape cell;
        cell = HexagonFactory.createHexagonTopFirst(getCellWidth(), getCellHeight());
        return cell;

    }
}
