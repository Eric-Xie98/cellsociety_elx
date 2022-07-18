package cellsociety.model;

import cellsociety.model.unit.Cell;
import cellsociety.view.SimulationInfo;
import java.util.ArrayList;

/**
 * Falling Sand Water simulation class
 * <p>
 * Purpose: Used to create the Falling Sand Water simulation, a simulation of falling sand and water
 * in an environment that interacts with each other and metal
 * <p>
 * Assumptions: We assumed that the website provided on the CS308 website contained the rules for
 * the simulation. Other assumptions we made was that there were no interactions with the simulation
 * once the file was loaded (can't alter the simulation while it's running). Furthermore, we assume
 * that the code implemented below covers the edge cases, if not most of them.
 * <p>
 * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
 * package. Furthermore, it imports the cellsociety.view.SimulationInfo record, which contains the
 * necessary info to create the simulation. This also imports the cellsociety model Cell unit. It
 * imports the ArrayList to hold the specific indices of the neighborStatus array we need to check.
 * <p>
 * Examples: One example of how to use is to create an XML file following the examples provided in
 * the resources file labeled with csPercolation. The labels for cells range from 0 to 3 with them
 * being EMPTY, METAL, WATER, or SAND respectively. With this, you can set up your own simulation
 * with an initial configuration.
 * <p>
 * Once in the simulation, you can switch between files by using the upload file functionality and
 * choosing the XML file of choice in the resources file.
 * <p>
 * <p>
 * Misc: I like this simulation it's pretty cool.
 *
 * @author ERIC XIE
 */

public class FallingSandWater extends GridClass {

  private Cell[][] sandWaterGrid;

  private final static int EMPTY = 0;
  private final static int METAL = 1;
  private final static int WATER = 2;
  private final static int SAND = 3;

  private final static int[] waterDirectionIndexes = {0, 2, 3};
  private final static int WATER_DIRECTION_RIGHT = 2;
  private final static int WATER_DIRECTION_LEFT = 3;
  private final static int WATER_DIRECTION_DOWN = 0;

  private int gridWidth;
  private int gridHeight;

  /**
   * Purpose: Falling Sand Water constructor, which creates FallingSandWater simulation object
   * <p>
   * Assumptions: This constructor assumes that the SimulationInfo record data that is provided to
   * it is correct and contains the correct grid read from the initial configuration XML file. We
   * assume that this constructor is never seen by the user and is called by reading the XMl file.
   * <p>
   * Exceptions: Exceptions that might be thrown would be caused by the record file where the grid
   * is incorrectly read and passed into the simulation.
   *
   * @param record A SimulationInfo object that contains information about the simulation taken from
   *               the XMl file. This constructor reads the constructed int[][] grid off it.
   * @return creates a CSPercolation simulation object
   */

  public FallingSandWater(SimulationInfo record) {

    int[][] inputData = record.initialGrid();
    sandWaterGrid = new Cell[inputData.length][inputData[0].length];
    initializeGrid(sandWaterGrid);

    for (int row = 0; row < sandWaterGrid.length; row++) {
      for (int col = 0; col < sandWaterGrid[0].length; col++) {
        sandWaterGrid[row][col].setState(inputData[row][col]);
        sandWaterGrid[row][col].setNextGenState(inputData[row][col]);
      }
    }

    gridHeight = sandWaterGrid.length;
    gridWidth = sandWaterGrid[0].length;

  }

  // main private algorithm for moving the sand and water cells as well as their interactions with
  // one another and metals

  private void sandWaterAlgo() {

    for (int row = 0; row < gridHeight; row++) {
      for (int col = 0; col < gridWidth; col++) {

        Cell currentCell = sandWaterGrid[row][col];

        if (currentCell.getMyState() == WATER) {
          waterFall(currentCell, row, col);
        } else if (currentCell.getMyState() == SAND) {
          sandFall(currentCell, row, col);
        }

      }
    }

  }

  // handled the water falling algorithm and its interactions if it came into contact with metal,
  // water, or sand

  // also handled the randomness movement of water using Math.random

  private void waterFall(Cell inputCell, int row, int col) {

    int[] neighborData = inputCell.neighborStatus(sandWaterGrid, row, col, gridHeight,
        gridWidth);
    int[] neighborNextGen = inputCell.neighborNextStatus(sandWaterGrid, row, col,
        gridHeight, gridWidth);
    ArrayList<Integer> emptyDirections = new ArrayList<>();

    // adds empty directions to an arraylist
    for (int index : waterDirectionIndexes) {
      if (neighborData[index] == EMPTY && neighborNextGen[index] == EMPTY) {
        emptyDirections.add(index);
      }
    }
    if (emptyDirections.size() != 0) {
      int index = chooseRandomDirection(emptyDirections.size());
      // need to check if next
      changeWaterNextStatus(row, col, emptyDirections, index);

    }

  }

  // changed and moved the water cells based on where they moved, using an arraylist of the direction
  // indices

  private void changeWaterNextStatus(int row, int col, ArrayList<Integer> emptyDirections,
      int index) {
    if (emptyDirections.get(index) == WATER_DIRECTION_LEFT) {
      sandWaterGrid[row][col - 1].setNextGenState(WATER);
    } else if (emptyDirections.get(index) == WATER_DIRECTION_RIGHT) {
      sandWaterGrid[row][col + 1].setNextGenState(WATER);
    } else if (emptyDirections.get(index) == WATER_DIRECTION_DOWN) {
      sandWaterGrid[row + 1][col].setNextGenState(WATER);
    }
    if (sandWaterGrid[row][col].getNextGenState() == EMPTY
        || sandWaterGrid[row][col].getNextGenState() == WATER) {
      sandWaterGrid[row][col].setNextGenState(EMPTY);
    }

  }

  // handled the sand movement as it fell and its interactions if it came into contact with metal or water

  private void sandFall(Cell inputCell, int row, int col) {

    int[] neighborData = inputCell.neighborStatus(sandWaterGrid, row, col, gridHeight, gridWidth);
    int[] neighborNextGen = inputCell.neighborNextStatus(sandWaterGrid, row, col, gridHeight,
        gridWidth);

    int bottomCellStatus = neighborData[0];

    if (bottomCellStatus == EMPTY) {
      sandWaterGrid[row + 1][col].setNextGenState(SAND);
      sandWaterGrid[row][col].setNextGenState(EMPTY);
    } else if (bottomCellStatus == WATER) {
      sandWaterGrid[row + 1][col].setNextGenState(SAND);
      sandWaterGrid[row][col].setNextGenState(WATER);
    }

  }

  // takes the initialized Grid and fills it with new cells

  private void initializeGrid(Cell[][] inputGrid) {

    for (int row = 0; row < inputGrid.length; row++) {
      for (int col = 0; col < inputGrid[0].length; col++) {

        inputGrid[row][col] = new Cell();

      }
    }

  }

  // returns a random number between one and the arraysize - 1 in order to choose an indice out
  // of the arraylist which contains the possible directions the water could go

  private int chooseRandomDirection(int arraySize) {
    return 0 + (int) (Math.random() * (arraySize));
  }

  /**
   * Purpose: Overridden method used by view classes to get an individual cell's state for CSS
   * purposes on the front end; returns a String key that is used for the cell's color in CSS
   * <p>
   * Assumptions: Assuming that the method is used correctly to extract a cell's string state out
   * when passed a row and column that's within the grid size in the simulation. Furthermore, we're
   * assuming that the integer passed in is correct and knows which value is assigned to what kind
   * of cell.
   * <p>
   * Exceptions: Exceptions thrown include index out of error when a row or column integer provided
   * is out of the gameGrid's range
   *
   * @param row      an integer of the row of the cell
   * @param col      an int of the column of the cell
   * @param intValue the integer value of the cell status, to compare with and return the cell's
   *                 named status.
   * @return a String of the cell type for CSS purposes
   */

  @Override

  public String getCellState(int row, int col, boolean intValue) {

    if (sandWaterGrid[row][col].getMyState() == METAL) {
      return intValue ? "" + METAL : "METAL";
    } else if (sandWaterGrid[row][col].getMyState() == WATER) {
      return intValue ? "" + WATER : "WATERFILLED";
    } else if (sandWaterGrid[row][col].getMyState() == SAND) {
      return intValue ? "" + SAND : "SAND";
    } else {
      return intValue ? "" + EMPTY : "EMPTY";
    }

  }

  /**
   * PURPOSE: Method used to update the grid values or make each cell's next gen status to their new
   * status; furthermore it runs the sand water algorithm algorithm before doing so
   * <p>
   * Assumptions: We assume that this method is called by the view/UI handlers to update the grid to
   * its new generation using the next step button.
   * <p>
   * Exceptions: Exceptions may come from the RPS algorithm messing up or an index out of bounds
   * error
   * <p>
   * No parameters
   *
   * @return void
   */

  @Override
  public void updateGrid() {
    sandWaterAlgo();
    for (int row = 0; row < gridHeight; row++) {
      for (int col = 0; col < gridWidth; col++) {
        sandWaterGrid[row][col].setState(sandWaterGrid[row][col].getNextGenState());
      }
    }

  }


}
