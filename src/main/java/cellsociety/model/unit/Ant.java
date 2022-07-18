package cellsociety.model.unit;

/**
 * What was supposed to be an Ant cell object
 * <p>
 *
 * Depends on the cell society unit package
 *
 * Other: Catches incorrect XML file names that are passed to it
 *
 * @author Eric Xie
 *
 */

public class Ant extends Cell {

  // ant cell states
  private boolean hasFoodItem;
  private int foodPheromone;
  private int nestPheromone;


  /**
   * Purpose: The Ant constructor, which creates Ant objects
   * <p>
   * Assumptions: This constructor was going to be used by a nest to create Ants at a constant rate
   *
   * @return an Ant object
   */

  public Ant(){



  }


  /**
   * Purpose: Used to check whether or not the Ant had acquired a Food Item
   * <p>
   * Assumptions: Assumed to be set by the ForagingAnt class depending on whether it found food or not
   *
   * @return void
   *
   */
  public boolean checkFoodItem(){return hasFoodItem;}

  /**
   * Purpose: Set an Ant's food status to true or false based on if they found food or not
   * <p>
   * Assumptions: Assuming the value passed to it by the ForagingAnt class was correct
   *
   * @return void
   *
   */
  public void setHasFoodItem(boolean foodStatus){hasFoodItem = foodStatus;}

  // method that sets foodPheromone amount

  // method that gets foodPheromone amount

  // method that sets nestPheromone amount

  // method that gets foodPheromone amount

}
