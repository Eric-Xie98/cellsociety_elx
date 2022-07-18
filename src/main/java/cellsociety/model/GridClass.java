package cellsociety.model;

public abstract class GridClass {

  // private int myWidth;
  // private int myHeight;
  // private int myGrowthSpeed;
  // private Cell[][] myGrid;


  public GridClass(){


  }

  public abstract String getCellState(int row, int col, boolean intValue);


  public abstract void updateGrid();


}
