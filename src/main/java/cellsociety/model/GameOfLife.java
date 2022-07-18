package cellsociety.model;

import cellsociety.model.unit.Cell;
import cellsociety.view.SimulationInfo;

/**
 * Game Of Life simulation class
 * <p>
 * Purpose: Used to create the Game of Life simulation, a simulation of cell colonies that have
 * rules applied to each individual cell depending on the # of alive cells around it
 * <p>
 * Assumptions: We assumed that the website provided on CS308 by Stanford contained the rules for
 * the simulation. Other assumptions we made was that there were no interactions with the simulation
 * once the file was loaded (can't alter the simulation while it's running). Furthermore, we assume
 * that the code implemented below covers the edge cases, if not most of them.
 * <p>
 * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
 * package. Furthermore, it imports the cellsociety.view.SimulationInfo record, which contains the
 * necessary info to create the simulation.
 * <p>
 * Examples: One example of how to use is to create an XML file following the examples provided in
 * the resources file labeled with gameOfLife. The labels for cells range from 0 to 2 with them
 * being EMPTY, ALIVE, or BLOCKED respectively. With this, you can set up your own simulation with
 * an initial configuration.
 * <p>
 * Once in the simulation, you can switch between files by using the upload file functionality and
 * choosing the XML file of choice in the resources file.
 * <p>
 * Misc: The BLOCKED cell type isn't really used in this program and is more for making the logic of
 * cell society easier to implement. Essentially, it's an unseen block type that loops around the
 * outside of the grid, making cell status checking on the edges far easier.
 *
 * @author ERIC XIE
 */

public class GameOfLife extends GridClass {

  private final static int EMPTY = 0;
  private final static int ALIVE = 1;
  private final static int BLOCKED = 2; // untouchable and unchangeable cells used for the border

  private Cell[][] gameGrid;

  private final static int STATUS_LONELY = 1;
  private final static int STATUS_STABLE = 2;
  private final static int STATUS_GROWTH = 3;

  /**
   * PURPOSE: Game of Life simulation constructor, which creates a default simulation geared for
   * Game of Life.
   * <p>
   * Assumptions: This constructor assumes that the SimulationInfo record data that is provided to
   * it is correct and contains the correct grid read from the initial configuration XML file. We
   * assume that this constructor is never seen by the user and is called by reading the XMl file.
   * <p>
   * Exceptions: Exceptions that might be thrown would be caused by the record file where the grid
   * is incorrectly read and passed into the simulation.
   * <p>
   * A larger grid is created to make the perimeter of the grid BLOCKED cells, making the code logic
   * when checking cell neighbors easier to implement.
   *
   * @param record A SimulationInfo object that contains information about the simulation taken from
   *               the XMl file. This constructor reads the constructed int[][] grid off it.
   * @return creates a GameOfLife simulation object
   */

  public GameOfLife(SimulationInfo record) {

    int[][] inputData = record.initialGrid();
    gameGrid = new Cell[inputData.length + 2][inputData[0].length + 2];
    fillBlockedPadding(gameGrid);

    for (int row = 1; row < gameGrid.length - 1; row++) {
      for (int col = 1; col < gameGrid[0].length - 1; col++) {
        gameGrid[row][col].setState(inputData[row - 1][col - 1]);
        gameGrid[row][col].setNextGenState(inputData[row - 1][col - 1]);
      }
    }

  }

  // The "main", private method to call to simulate the cell interactions in a step

  private void lifeAlgo() {

    for (int row = 1; row < gameGrid.length - 1; row++) {
      for (int col = 1; col < gameGrid[0].length - 1; col++) {

        Cell currentCell = gameGrid[row][col];

        checkStability(currentCell, row, col);

      }
    }

  }

  // checks statuses of neighbors around it and pass an amount to another method changing its nextGenState based off that value
  // takes in the cell being checked and its row and column

  private void checkStability(Cell inputCell, int row, int col) {

    int[] cellNeighbors = inputCell.neighborStatus(gameGrid, row, col, gameGrid.length,
        gameGrid[0].length);
    int aliveStatus = 0;

    for (int i = 0; i < cellNeighbors.length; i++) {
      if (cellNeighbors[i] == ALIVE) {
        aliveStatus++;
      }
    }

    changeNextGenState(inputCell, aliveStatus);

  }

  // handles the hard logic for determining whether a cell will be empty or alive in the next generation
  // takes in the cell in question and the number of alive neighbors around it
  private void changeNextGenState(Cell inputCell, int aliveNeighbors) {

    if (aliveNeighbors <= STATUS_LONELY) {
      inputCell.setNextGenState(EMPTY);
    } else if (aliveNeighbors == STATUS_STABLE) {
      inputCell.setNextGenState(inputCell.getMyState());
    } else if (aliveNeighbors == STATUS_GROWTH) {
      inputCell.setNextGenState(ALIVE);
    } else {
      inputCell.setNextGenState(EMPTY);
    }
  }

  // this method is used when creating the specific gameOfLife grid in each gameOfLife simulation
  // it takes the newly created grid and fills the entire thing with blocked cells
  private void fillBlockedPadding(Cell[][] gameGrid) {
    for (int row = 0; row < gameGrid.length; row++) {
      for (int col = 0; col < gameGrid[0].length; col++) {

        gameGrid[row][col] = new Cell();
        gameGrid[row][col].setState(BLOCKED);
      }
    }
  }

  /**
   * PURPOSE: Method used to update the grid values or make each cell's next gen status to their new
   * status; furthermore it runs the game of life algorithm before doing so
   * <p>
   * Assumptions: We assume that this method is called by the view/UI handlers to update the grid to
   * its new generation using the next step button.
   * <p>
   * Exceptions: Exceptions that may be thrown would come from the lifeAlgorithm screwing up
   * <p>
   * No parameters
   *
   * @return void
   */
  @Override
  public void updateGrid() {

    lifeAlgo();
    for (int row = 1; row < gameGrid.length - 1; row++) {
      for (int col = 1; col < gameGrid[0].length - 1; col++) {
        gameGrid[row][col].setState(gameGrid[row][col].getNextGenState());
      }
    }


  }

  /**
   * Purpose: Overriden method used by view classes to get an individual cell's state for CSS
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

    if (gameGrid[row + 1][col + 1].getMyState() == 0) {
      return intValue ? "0" : "EMPTY";
    } else if (gameGrid[row + 1][col + 1].getMyState() == 1) {
      return intValue ? "1" : "ALIVE";
    } else {
      return intValue ? "2" : "BLOCKED";
    }

  }

}
