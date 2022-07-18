package cellsociety.model.unit;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
/**
 * Cell class
 * <p>
 * Purpose: Create a base unit to be used for different simulations
 * <p>
 * Assumptions: We assumed that each unit can only have one cell state at a time.
 * <p>
 * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
 * package.
 * <p>
 * Examples: One example of how to use the cell class is in intializing the grid from reading the XML file and
 * taking in the appropriate states as listed in the file.
 * <p>
 * Misc: This class is made to be extended upon for more complicated simulations
 * @author THIVYA SIVARAJAH
 */

public class Cell {

  private int myState;
  private int nextGenState;
  private int chronons;
  private int time = 0;


  // cell constructor which is made of the components that build the cell
  public Cell() {
    int cellWidth = 5;
    int cellHeight = 5;
  }

  // randomly assigns a cell a starting state based on the number of states from the simulation, which we get from the simulation classes
  public void setState(int stateNumbers) {
    // code below will be changing
    myState = stateNumbers;
  }


  // we get an array of the status of our neighbors, which is helpful in determining our own state based on the specific simulation rules
  // important to note that neighbors on the diagonal are indexes 4-7 in the array
  public int[] neighborStatus(Cell[][] inputGrid, int xCellCoord, int yCellCoord, int gridWidth, int gridHeight) {
    int[] neighborStatuses = new int[8];

    int [] [] dir = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{-1,1},{1,-1}};
    for (int i = 0; i<dir.length; i++){
      if (checkBoundaries(xCellCoord + dir[i][0], yCellCoord + dir[i][1], gridWidth, gridHeight)) {
        neighborStatuses[i] = inputGrid[xCellCoord + dir[i][0]][yCellCoord + dir[i][1]].myState;
      }
      else {
        neighborStatuses[i] = -1;
      }
    }

    return neighborStatuses;
  }

  // checks to see what the next gen state the neighbors are for a rectangle
  // same indexing as above for neighborStatus
  public int[] neighborNextStatus(Cell[][] inputGrid, int xCellCoord, int yCellCoord, int gridWidth, int gridHeight) {
    int[] neighborNextStatuses = new int[8];

    int [] [] dir = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{-1,1},{1,-1}};
    for (int i = 0; i<dir.length; i++){
      if (checkBoundaries(xCellCoord + dir[i][0], yCellCoord + dir[i][1], gridWidth, gridHeight)) {
        neighborNextStatuses[i] = inputGrid[xCellCoord + dir[i][0]][yCellCoord + dir[i][1]].nextGenState;
      }
      else {
        neighborNextStatuses[i] = -1;
      }
    }
    return neighborNextStatuses;
  }

  // check to see if neighbor cell actually exists in our grid
  private boolean checkBoundaries(int xCoordinate, int yCoordinate, int gridWidth, int gridHeight) {
    if (xCoordinate >= gridWidth || xCoordinate < 0 || yCoordinate >= gridHeight
        || yCoordinate < 0) {
      return FALSE;
    }
    return TRUE;
  }

  // method that stores next state for the next generation of the simulation
  public void setNextGenState(int inputState) {
    nextGenState = inputState;
  }

  // method that stores next state for the next generation of the simulation
  public int getMyState() {
    return myState;
  }

  // method that stores next state for the next generation of the simulation
  public int getNextGenState() {return nextGenState;}

  // method that gets the number of chronons (energy) left in a cell (used in Wator World)
  public int getChronons() {
    return chronons;
  }

  // method that sets the new value of chronons (energy) for the cell
  public void setChronons(int inputChronons) {
    chronons = inputChronons;
  }

  // method that gets the time that has passed
  public int getTime() {
    return time;
  }

  // method that resets the time/alters it in cases of reproduction and in new cells
  public void setTime(int inputTime) {
    time = inputTime;
  }



}
