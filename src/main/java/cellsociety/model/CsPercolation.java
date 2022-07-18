package cellsociety.model;

import cellsociety.model.unit.Cell;
import cellsociety.view.SimulationInfo;


/**
 * CS Percolation simulation class
 * <p>
 * Purpose: Used to create the Percolation simulation, a simulation of water percolating through a
 * created structure to the bottom of the screen
 * <p>
 * Assumptions: We assumed that the Google document provided in CS201 contained the rules for the
 * simulation. Other assumptions we made was that there were no interactions with the simulation
 * once the file was loaded (can't alter the simulation while it's running). Furthermore, we assume
 * that the code implemented below covers the edge cases, if not most of them.
 * <p>
 * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
 * package. Furthermore, it imports the cellsociety.view.SimulationInfo record, which contains the
 * necessary info to create the simulation. This also imports the cellsociety model Cell unit.
 * <p>
 * Examples: One example of how to use is to create an XML file following the examples provided in
 * the resources file labeled with csPercolation. The labels for cells range from 0 to 2 with them
 * being EMPTY, FILLED, or BLOCKED respectively. With this, you can set up your own simulation with
 * an initial configuration.
 * <p>
 * Once in the simulation, you can switch between files by using the upload file functionality and
 * choosing the XML file of choice in the resources file.
 * <p>
 * Misc: The BLOCKED cell type isn't really used in this program and is more for making the logic of
 * cell society easier to implement. Essentially, it's an unseen block type that loops around the
 * outside of the grid, making cell status checking on the edges far easier.
 * <p>
 * The TOTAL_NEIGHBORS_CHECKED final static private int is for selecting the first 4 elements of the
 * neighbor status grid, as only the North, South, East, and West directions need to be checked.
 *
 * @author ERIC XIE
 */

public class CsPercolation extends GridClass {

  private static Cell[][] percolationGrid;

  private final static int EMPTY = 0;
  private final static int FILLED = 1;
  private final static int BLOCKED = 2;

  private final static int TOTAL_NEIGHBORS_CHECKED = 4;

  /**
   * Purpose: The CSPercolation constructor, which creates a CS Percolation simulation object
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

  public CsPercolation(SimulationInfo record) {

    // padding the edges of the grid to check for neighbors
    int[][] inputData = record.initialGrid();
    percolationGrid = new Cell[inputData.length + 2][inputData[0].length + 2];
    fillBlockedPadding(percolationGrid);

    for (int row = 1; row < percolationGrid.length - 1; row++) {
      for (int col = 1; col < percolationGrid[0].length - 1; col++) {

        // each cell is initialized into the percolationGrid
        percolationGrid[row][col].setState(inputData[row - 1][col - 1]);
        percolationGrid[row][col].setNextGenState(inputData[row - 1][col - 1]);

      }
    }

  }

  // main algorithm for all cells in the percolationGrid that simulates the water in each step

  private void percolationAlgo() {

    for (int row = 1; row < percolationGrid.length - 1; row++) {
      for (int col = 1; col < percolationGrid[0].length - 1; col++) {

        Cell currentCell = percolationGrid[row][col];

        if (currentCell.getMyState() == EMPTY) {
          checkWater(currentCell, row, col);
        }

      }
    }


  }

  // applies the algorithm to the cell[][] array specifically for empty cells

  private void checkWater(Cell inputCell, int row, int col) {

    // this algorithm only checks the N, E, S, W of the cell

    int[] cellNeighbors = inputCell.neighborStatus(percolationGrid, row, col,
        percolationGrid.length, percolationGrid[0].length);

    for (int i = 0; i < TOTAL_NEIGHBORS_CHECKED; i++) {

      if (cellNeighbors[i] == FILLED) {
        inputCell.setNextGenState(FILLED);

      }

    }
  }

  // this method is used when creating the specific gameOfLife grid in each gameOfLife simulation
  // it takes the newly created grid and fills the entire thing with blocked cells

  private void fillBlockedPadding(Cell[][] percolationGrid) {

    for (int row = 0; row < percolationGrid.length; row++) {
      for (int col = 0; col < percolationGrid[0].length; col++) {

        percolationGrid[row][col] = new Cell();
        percolationGrid[row][col].setState(BLOCKED);
      }
    }
  }

  /**
   * PURPOSE: Method used to update the grid values or make each cell's next gen status to their new
   * status; furthermore it runs the spreadingFire algorithm before doing so
   * <p>
   * Assumptions: We assume that this method is called by the view/UI handlers to update the grid to
   * its new generation using the next step button.
   * <p>
   * Exceptions: Exceptions may come from the spreadFireAlgorithm messing up or an index out of
   * bounds error
   * <p>
   * No parameters
   *
   * @return void
   */

  @Override
  public void updateGrid() {

    percolationAlgo();
    for (int row = 1; row < percolationGrid.length - 1; row++) {
      for (int col = 1; col < percolationGrid[0].length - 1; col++) {
        percolationGrid[row][col].setState(percolationGrid[row][col].getNextGenState());
      }
    }
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

    if (percolationGrid[row + 1][col + 1].getMyState() == 0) {
      return intValue ? "0" : "EMPTY";
    } else if (percolationGrid[row + 1][col + 1].getMyState() == 1) {
      return intValue ? "1" : "WATERFILLED";
    } else {
      return intValue ? "2" : "WATERBLOCKED";
    }

  }


}
