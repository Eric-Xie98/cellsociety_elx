package cellsociety.view;

import cellsociety.XMLException;
import cellsociety.model.CsPercolation;
import cellsociety.model.FallingSandWater;
import cellsociety.model.GameOfLife;
import cellsociety.model.GridClass;
import cellsociety.model.RockPaperScissors;
import cellsociety.model.SchellingSegregation;
import cellsociety.model.SpreadingFire;
import cellsociety.model.WatorWorld;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * Displays the Grid for the specific simulation passed to it.
 * <p>
 * Takes a SimulationInfo record as a parameter to its constructor. This record holds the initial
 * information for the simulation and is used to set the initial state of the grid. The type of
 * model created is based on the type passed in the record and to update the view, the class gets
 * the state of each cell from the model and changes the CSS ID of each cell in the view to reflect
 * its state{@link #updateGridPane()}
 *
 * @author Robert Cranston
 */
public class GridView {

  public static final double BASE_ANIMATION_SPEED = .5;
  private double cellHeight;
  private double cellWidth;
  private SimulationInfo myRecord;
  private GridClass gridModel;
  private double cellWidthPercent;
  private double cellHeightPercent;
  protected GridPane grid;
  private List<List<Shape>> displayedCellsList;
  private boolean isAnimated;
  private double animationSpeed;

  /**
   * Initializes the simulation speed and calls the method to display the initial state of the
   * simulation.
   *
   * @param record SimulationInfo containing the initial information for the simulation.
   */
  public GridView(SimulationInfo record) {
    myRecord = record;
    isAnimated = false;
    animationSpeed = SimulationView.SLIDER_START;
    displayGrid();
  }

  //initializes the grid model based on the type specified in the record. Displays an error message
  //if it cannot initialized for whatever reason.
  protected GridClass chooseSimulation() {
    try {
      switch (myRecord.type()) {
        case "schellingsegregation" -> {
          return new SchellingSegregation(myRecord);
        }
        case "gameoflife" -> {
          return new GameOfLife(myRecord);
        }
        case "watorworld" -> {
          return new WatorWorld(myRecord);
        }
        case "spreadingoffire" -> {
          return new SpreadingFire(myRecord);
        }
        case "cspercolation" -> {
          return new CsPercolation(myRecord);
        }
        case "rockpaperscissors" -> {
          return new RockPaperScissors(myRecord);
        }
        case "fallingsandandwater" -> {
          return new FallingSandWater(myRecord);
        }
      }
    } catch (XMLException e) {
      CellSocietyView.showMessage(AlertType.ERROR, e.getMessage());
    }
    return null;
  }

  //handles full process of setting up and displaying the grid
  protected void displayGrid() {
    gridModel = chooseSimulation();
    grid = new GridPane();
    setupGrid();
    displayedCellsList = new ArrayList();
    setCellParameters();
    initializeGridDisplay();
  }

  //controls the spacing between the cells of the grid to achieve the desired look.
  protected void setupGrid() {
  }

  //creates the shape of each individual cell in the grid
  protected Shape createShape(int j, int i) {
    return null;
  }

  //initializes the height ond width of the cells so that the size of the grid can be constant. Also
  // used to create the shapes
  protected void setCellParameters() {
    cellHeightPercent = 100.0 / myRecord.height();
    cellWidthPercent = 100.0 / myRecord.width();
    cellHeight = grid.getMaxHeight() / myRecord.height();
    cellWidth = grid.getMaxWidth() / myRecord.width();

  }

  //creates a cell of the intended shape for each cell of the grid and adds it to the grid
  protected void initializeGridDisplay() {
    setRowColumnParameters();
    for (int i = 0; i < myRecord.height(); i++) {
      displayedCellsList.add(new ArrayList());
      for (int j = 0; j < myRecord.width(); j++) {
        Shape cell = createShape(j, i);
        displayedCellsList.get(i).add(cell);
        cell.setId(gridModel.getCellState(i, j, false));
        grid.add(cell, j, i);
      }
    }
  }

  //Restricts the rows and columns of the cells to fit in the space set for the grid
  private void setRowColumnParameters() {
    for (int i = 0; i < myRecord.width(); i++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(cellWidthPercent);
      grid.getColumnConstraints().add(column);
    }
    for (int i = 0; i < myRecord.height(); i++) {
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(cellHeightPercent);
      grid.getRowConstraints().add(row);
    }
    ;
  }

  protected double getCellWidth() {
    return cellWidth;
  }

  protected double getCellHeight() {
    return cellHeight;
  }

  protected GridPane getGridPane() {
    return grid;
  }

  protected GridPane getDisplayGrid() {
    return grid;
  }

  public GridClass getCurrentGrid() {
    return gridModel;
  }

  //stops the animation
  public void pause() {
    isAnimated = false;
  }

  //resumes the animation
  public void play() {
    if (!isAnimated) {
      isAnimated = true;
      animate();
    }
  }

  //updates all of the cells of the grid once
  public void next() {
    System.out.println(myRecord.type());
    gridModel.updateGrid();
    updateGridPane();
  }

  //changes the color of the cells to reflect their next state
  public void updateGridPane() {
    for (int i = 0; i < displayedCellsList.size(); i++) {
      for (int j = 0; j < displayedCellsList.get(i).size(); j++) {
        Shape cell = displayedCellsList.get(i).get(j);
        cell.setId(gridModel.getCellState(i, j, false));
      }
    }
  }

  /**
   * Controols the animation speed for the grid
   *
   * @param newValue value to set the new animation speed to
   */
  public void setAnimationSpeed(Number newValue) {
    animationSpeed = (double) newValue;
  }

  //Calls next on a loop to simulation the grid animating. Changes the delay based on the animation
  //speed
  public void animate() {
    if (isAnimated) {
      next();
      PauseTransition delay = new PauseTransition(
          Duration.seconds(BASE_ANIMATION_SPEED / animationSpeed));
      delay.setOnFinished(event -> animate());
      delay.play();
    }
  }

  public void reset() {
    gridModel = chooseSimulation();
    isAnimated = false;
    updateGridPane();
  }
}
