package cellsociety.model;

import cellsociety.XMLException;
import cellsociety.model.unit.Cell;
import cellsociety.view.SimulationInfo;
import cellsociety.XMLException;
import java.util.HashMap;

/**
 * Rock Paper Scissors simulation class
 * <p>
 * Purpose: Used to create the RPS simulation, a simulation of cells playing RPS with one another
 * and changing statuses based off whether there's enough winners compared to a threshhold
 * <p>
 * Assumptions: We assumed that the website provided on the CS308 website contained the rules for
 * the simulation. Other assumptions we made was that there were no interactions with the simulation
 * once the file was loaded (can't alter the simulation while it's running). Furthermore, we assume
 * that the code implemented below covers the edge cases, if not most of them. We assume that the input
 * values required are provided.
 * <p>
 * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
 * package. Furthermore, it imports the cellsociety.view.SimulationInfo record, which contains the
 * necessary info to create the simulation. It also imports HashMap to hold the various interactions
 * between rock, paper, and scissors.This also imports the cellsociety model Cell unit.
 * <p>
 * Examples: One example of how to use is to create an XML file following the examples provided in
 * the resources file labeled with csPercolation. The labels for cells range from 0 to 3 with them
 * being ROCK, PAPER, SCISSORS, or VOID respectively. With this, you can set up your own simulation
 * with an initial configuration.
 * <p>
 * Once in the simulation, you can switch between files by using the upload file functionality and
 * choosing the XML file of choice in the resources file.
 * <p>
 * Misc: The VOID cell type isn't really used in this program and is more for making the logic of
 * cell society easier to implement. Essentially, it's an unseen block type that loops around the
 * outside of the grid, making cell status checking on the edges far easier.
 *
 * @author ERIC XIE
 */

public class RockPaperScissors extends GridClass {

  // every pixel color calculated by a 1 v 8 rock paper scissors fight with an individual ceel against
  // its 8 neighbors around it. (moore neighbors)

  private int threshold = 2; // to be replaced by input value later
  private Cell[][] rpsGrid;

  private final static int ROCK = 0;
  private final static int PAPER = 1;
  private final static int SCISSORS = 2;
  private final static int VOID = 3; // untouchable and unchangeable cells used for the border

  private HashMap interactions;

  /**
   * Purpose: This is a RockPaperScissors constructor, which is used to create Rock Paper Scissors
   * simulation objects.
   * <p>
   * Assumptions: This constructor assumes that the SimulationInfo record data that is provided to
   * it is correct and contains the correct grid read from the initial configuration XML file. We
   * assume that this constructor is never seen by the user and is called by reading the XMl file.
   * <p>
   * Exceptions: Exceptions that might be thrown would be caused by when the required parameter of a
   * threshold to calculate wins for the cells is not provided. This throws and catches the Null
   * Pointer Exception.
   * <p>
   * Similar to GameOfLife, creates a padded grid with void cells that line the perimeter for ease
   * of neighbor status checking
   *
   * @param record A SimulationInfo object that contains information about the simulation taken from
   *               the XMl file. This constructor reads the constructed int[][] grid off it.
   * @return creates a RockPaperScissors simulation object
   */

  public RockPaperScissors(SimulationInfo record) {

    int[][] inputData = record.initialGrid();
    rpsGrid = new Cell[inputData.length + 2][inputData[0].length + 2];
    fillVoidPadding(rpsGrid);

    for (int row = 1; row < rpsGrid.length - 1; row++) {
      for (int col = 1; col < rpsGrid[0].length - 1; col++) {
        rpsGrid[row][col].setState(inputData[row - 1][col - 1]);
        rpsGrid[row][col].setNextGenState(inputData[row - 1][col - 1]);
      }
    }

    // HashMap of interactions
    interactions = new HashMap<Integer, Integer>();
    interactions.put(ROCK, PAPER);
    interactions.put(PAPER, SCISSORS);
    interactions.put(SCISSORS, ROCK);

    try {
      threshold = record.possibleParameters().get("Threshold Value");
    } catch (NullPointerException e) {
      throw new XMLException("Required parameter not given");
    }

    for (int row = 0; row < rpsGrid.length; row++) {
      for (int col = 0; col < rpsGrid[0].length; col++) {

        System.out.print(rpsGrid[row][col].getMyState() + ", ");

      }
      System.out.println();
    }

  }

  // main algorithm method for cells playing rock paper scissors w the cells around it
  private void rpsAlgo() {

    for (int row = 1; row < rpsGrid.length - 1; row++) {
      for (int col = 1; col < rpsGrid[0].length - 1; col++) {

        Cell currentCell = rpsGrid[row][col];

        if (checkRPSStatus(currentCell, row, col) > threshold) {
          currentCell.setNextGenState((int) interactions.get(currentCell.getMyState()));
        }

      }
    }
  }

  // checks the neighbor cells for what they played and counts up the total of cells that beat the
  // current cell
  private int checkRPSStatus(Cell currentCell, int row, int col) {

    int[] neighborData = currentCell.neighborStatus(rpsGrid, row, col, rpsGrid.length,
        rpsGrid[0].length);
    int beatenTotal = 0;

    for (int i = 0; i < neighborData.length; i++) {
      if (neighborData[i] == (int) interactions.get(currentCell.getMyState())) {
        beatenTotal++;
      }
    }

    return beatenTotal;
  }

  // this method is used when creating the specific grid in each RPS simulation
  // it takes the newly created grid and fills the entire thing with void cells

  private void fillVoidPadding(Cell[][] inputGrid) {
    for (int row = 0; row < inputGrid.length; row++) {
      for (int col = 0; col < inputGrid[0].length; col++) {
        inputGrid[row][col] = new Cell();
        inputGrid[row][col].setState(VOID);
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

    if (rpsGrid[row + 1][col + 1].getMyState() == ROCK) {
      return intValue ? "" + ROCK : "ROCK";
    } else if (rpsGrid[row + 1][col + 1].getMyState() == PAPER) {
      return intValue ? "" + PAPER : "PAPER";
    } else if (rpsGrid[row + 1][col + 1].getMyState() == SCISSORS) {
      return intValue ? "" + SCISSORS : "SCISSORS";
    } else {
      return intValue ? "" + VOID : "EMPTY";
    }

  }

  /**
   * PURPOSE: Method used to update the grid values or make each cell's next gen status to their new
   * status; furthermore it runs the RPS algorithm before doing so
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
    rpsAlgo();
    for (int row = 1; row < rpsGrid.length - 1; row++) {
      for (int col = 1; col < rpsGrid[0].length - 1; col++) {

        rpsGrid[row][col].setState(rpsGrid[row][col].getNextGenState());

      }
    }

  }
}
