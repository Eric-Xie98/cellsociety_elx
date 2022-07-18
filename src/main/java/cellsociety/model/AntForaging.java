package cellsociety.model;

import cellsociety.model.unit.Cell;
import cellsociety.view.SimulationInfo;


public class AntForaging extends GridClass{

  // two different pheromones deposited by ants - locate food source and locate the nest
  // upon leaving nest, ants follow food pheromone trail while leaving home and leave nest pheromones
  // on their way back to home, they leave food pheromones

  // consider forward locations first (NE, N, and NW), each location has weight of pheromones
  // max # of ants in a cell, if all forward locations blocked, consider back 5
  // all blocked? stay in place

  // pheromones topped off? ant deposits nothing
  // desired value defined as max # of pheromones in surrounding 8 neighbors minus a constant

  // when reaching goal desired concentration is set to max

  // ignores obstacle blocks as well

  // GOING TO ASSUME MAX 1 ANT PER CELL


  /**
   * Ant Foraging simulation class
   * <p>
   * Purpose: Used to create the Ant Foraging simulation, a simulation of ants that find food and return to their nest using pheromones
   * <p>
   * Assumptions: We assumed that the website provided on CS308 contained the rules for
   * the simulation. Other assumptions we made was that there were no interactions with the simulation
   * once the file was loaded (can't alter the simulation while it's running). Furthermore, we assume
   * that the code implemented below covers the edge cases, if not most of them. Assumes only one ant
   * can be in a cell at a time.
   * <p>
   * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
   * package. Furthermore, it imports the cellsociety.view.SimulationInfo record, which contains the
   * necessary info to create the simulation.
   * <p>
   * Misc: Unfortunately, never finished.
   *
   * @author ERIC XIE
   */

  private final static int EMPTY = 0;
  private final static int ANT = 1;
  private final static int NEST = 2;
  private final static int FOOD = 3;

  private final static int MAX_ANTS = 100;
  private final static int MAX_PHEROMONE = 1000;
  private final static int MIN_PHEROMONE = 0;

  private Cell[][] antGrid;


  /**
   * PURPOSE: ForagingAnt simulation constructor, which creates a default simulation geared for
   * AntForager.
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
   * @return creates a AntForaging simulation object
   */

  public AntForaging(SimulationInfo record){

    int[][] inputData = record.initialGrid();
    antGrid = new Cell[inputData.length][inputData[0].length];

    // initialize grid with cells

    for(int row = 0; row < inputData.length; row++){
      for(int col = 0; col < inputData[0].length; col++){
        antGrid[row][col].setState(inputData[row][col]);
        antGrid[row][col].setNextGenState(inputData[row][col]);
      }
    }



  }

  // not going to full comment this, but it was supposed to be for the main algorithm
  public void antForageAlgo(){



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
    return null;
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

  }


}
