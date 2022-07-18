package cellsociety.model;

import cellsociety.model.unit.Cell;
import cellsociety.view.SimulationInfo;
import cellsociety.XMLException;


/**
 * PURPOSE: Used to create the Spreading Fire simulation, which simulates the burning of trees and
 * the spread of fire in forest fires
 * <p>
 * Assumptions: We assumed that the rules from the first website were the "right" implementation of
 * the simulation. This simulation unlike the one described in the second website does not have
 * trees that grow back. Other assumptions we made was that there were no interactions with the
 * simulation once the file was loaded (can't alter the simulation while it's running). Furthermore,
 * we assume that the code implemented below covers the edge cases, if not most of them. We assume
 * that the input values required are provided.
 * <p>
 * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
 * package. Furthermore, it imports the cellsociety.view.SimulationInfo record, which contains the
 * necessary info to create the simulation. It also depends on the view XML exception class when a
 * required parameter for the probability isn't passed. This also imports the cellsociety model Cell
 * unit.
 * <p>
 * Examples: One example of how to use is to create an XML file following the examples provided in
 * the resources file labeled with spreadingFire. The labels for cells range from 0 to 2 with them
 * being EMPTY, TREE, or BURNING respectively. With this, you can set up your own simulation with an
 * initial configuration.
 * <p>
 * Once in the simulation, you can switch between files by using the upload file functionality and
 * choosing the XML file of choice in the resources file.
 * <p>
 * Other: The other private instance variables (TOTAL_NEIGHBORS_CHECKED and DECIMAL_CONSTANT) are
 * used for game logic. The first checks only the first 4 elements in the neighborStatus. The
 * decimal constant is used to divide the integer input provided into a decimal for easier random
 * calculation.
 *
 * @author ERIC XIE
 */

public class SpreadingFire extends GridClass {

  private static double probCatch;
  private static Cell[][] spreadingFireGrid;


  private final static int EMPTY = 0;
  private final static int TREE = 1;
  private final static int BURNING = 2;

  private static final int TOTAL_NEIGHBORS_CHECKED = 4;
  private static final int DECIMAL_CONSTANT = 100;

  /**
   * Purpose: This is a spreadingFire constructor, which is used to create spreading fire simulation
   * objects.
   * <p>
   * Assumptions: This constructor assumes that the SimulationInfo record data that is provided to
   * it is correct and contains the correct grid read from the initial configuration XML file. We
   * assume that this constructor is never seen by the user and is called by reading the XMl file.
   * <p>
   * Exceptions: Exceptions that might be thrown would be caused by when the required parameter of a
   * probability chance for the trees to catch fire is not provided. This throws and catches the
   * Null Pointer Exception.
   * <p>
   * Similar to GameOfLife, creates a padded grid with empty cells that line the perimeter for ease
   * of neighbor status checking
   *
   * @param record A SimulationInfo object that contains information about the simulation taken from
   *               the XMl file. This constructor reads the constructed int[][] grid off it.
   * @return creates a spreadingFire simulation object
   */

  public SpreadingFire(SimulationInfo record) {
    int[][] inputData = record.initialGrid();

    // creating the spreadingFire grid with a 1 layer cell padding of EMPTY value
    spreadingFireGrid = new Cell[inputData.length + 2][inputData[0].length + 2];
    fillEmptyPadding(spreadingFireGrid);

    for (int row = 1; row < spreadingFireGrid.length - 1; row++) {
      for (int col = 1; col < spreadingFireGrid[0].length - 1; col++) {
        spreadingFireGrid[row][col].setState(inputData[row - 1][col - 1]);
        spreadingFireGrid[row][col].setNextGenState(inputData[row - 1][col - 1]);
      }
    }
    try {
      probCatch = record.possibleParameters().get("Probability of Catching Fire");
    } catch (NullPointerException e) {
      throw new XMLException("Required parameter not given");
    }
    probCatch /= DECIMAL_CONSTANT;
    System.out.println(probCatch);


  }

  // The "main", private method to call to simulate the fire and tree interactions in a step

  private static void spreadFireAlgo() {

    for (int row = 1; row < spreadingFireGrid.length - 1; row++) {
      for (int col = 1; col < spreadingFireGrid[0].length - 1; col++) {

        Cell currentCell = spreadingFireGrid[row][col];

        if (currentCell.getMyState() == TREE) {
          checkFireSpread(currentCell, row, col);
        } else if (currentCell.getMyState() == BURNING) {
          currentCell.setNextGenState(EMPTY);
        }

      }
    }

  }

  // applies the algorithm to the cell[][] array specifically for tree cells

  private static void checkFireSpread(Cell inputCell, int row, int col) {

    // this algorithm only checks the N, E, S, W of the cell

    int[] cellNeighbors = inputCell.neighborStatus(spreadingFireGrid, row, col,
        spreadingFireGrid.length, spreadingFireGrid[0].length);

    for (int i = 0; i < TOTAL_NEIGHBORS_CHECKED; i++) {

      if (cellNeighbors[i] == BURNING) {
        if (Math.random() < probCatch) {
          inputCell.setNextGenState(BURNING);
        }

      }

    }

  }

  // this method is used when creating the specific gameOfLife grid in each gameOfLife simulation
  // it takes the newly created grid and fills the entire thing with empty cells

  private void fillEmptyPadding(Cell[][] fireGrid) {

    for (int row = 0; row < fireGrid.length; row++) {
      for (int col = 0; col < fireGrid[0].length; col++) {

        fireGrid[row][col] = new Cell();
        fireGrid[row][col].setState(EMPTY);
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
    spreadFireAlgo();
    for (int row = 1; row < spreadingFireGrid.length - 1; row++) {
      for (int col = 1; col < spreadingFireGrid[0].length - 1; col++) {

        spreadingFireGrid[row][col].setState(spreadingFireGrid[row][col].getNextGenState());

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

    if (spreadingFireGrid[row + 1][col + 1].getMyState() == 0) {
      return intValue ? "0" : "EMPTY";
    } else if (spreadingFireGrid[row + 1][col + 1].getMyState() == 1) {
      return intValue ? "1" : "TREE";
    } else {
      return intValue ? "2" : "BURNING";
    }

  }


}