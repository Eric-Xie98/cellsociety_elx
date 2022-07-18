package cellsociety.model;

import cellsociety.model.unit.Cell;
import cellsociety.view.SimulationInfo;
import java.util.ArrayList;

/**
 * Schelling Segregation simulation class
 *
 * Purpose: Used to create the Schelling Segregation simulation, a simulation of two different types of neighbors
 * where like neighbors want to be surrounded by like neighbors and will move to find such neighbors.
 *
 * Assumptions: We assumed that there can be eight possible neighbors.
 *
 * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
 * package. Furthermore, it imports the cellsociety.view.SimulationInfo record, which contains the
 * necessary info to create the simulation. Finally, it depends on the cell.society.model.unit package for each
 * individual cell
 *
 * Examples: One example of how to use is to create an XML file following the examples provided in
 * the resources file labeled with gameOfLife. The labels for cells range from 0 to 2 with them
 * being EMPTY, AGENT1, or AGENT2 respectively. With this, you can set up your own simulation with
 * an initial configuration.
 *
 * @author THIVYA SIVARAJAH
 */

public class SchellingSegregation extends GridClass {

    private static final double satisfactionThreshold = 0.5;
    private Cell[][] schellingSegGrid;
    private int gridWidth;
    private int gridHeight;

    private static final int EMPTY = 0;


    // our constructor which sets up our grid portion of the UI
    public SchellingSegregation(SimulationInfo record){

        int[][] inputData = record.initialGrid();

        // creating the spreadingFire grid with a 1 layer cell padding of EMPTY value
        schellingSegGrid = new Cell[inputData.length][inputData[0].length];

        for (int row = 0; row < schellingSegGrid.length; row++) {
            for (int col = 0; col < schellingSegGrid[0].length; col++) {
                schellingSegGrid[row][col] = new Cell();
                schellingSegGrid[row][col].setState(inputData[row][col]);
                schellingSegGrid[row][col].setNextGenState(inputData[row][col]);
            }
        }

        gridWidth = record.width();
        gridHeight = record.height();
    }

    // see which agents in the grid are empty
    private ArrayList<Pairs> createEmptyList() {
        ArrayList<Pairs> emptyCells = new ArrayList<>();
        for(int row = 0; row < schellingSegGrid.length; row++) {
            for (int col = 0; col < schellingSegGrid[row].length; col++) {
                if (schellingSegGrid[row][col].getMyState() == EMPTY) {
                    Pairs p = new Pairs(row, col);
                    emptyCells.add(p);
                }

            }
        }
        return emptyCells;
    }

    // check to see if the agent needs to move or not depending on if they are satisfied with their neighbors
    private int isAgentSatisfied(Cell agent, int xCoordCell, int yCoordCell) {
        double neighborStatus = sameNeighbors(agent, xCoordCell, yCoordCell);
        if (neighborStatus >= satisfactionThreshold) {
            return 0;
        } else {
            return 1;
        }
    }

    // updates based on neighbor stat
    private void updateOnCycle() {

        for(int row = 0; row < schellingSegGrid.length; row++) {
            for (int col = 0; col < schellingSegGrid[row].length; col++) {
                ArrayList<Pairs> newSpots = createEmptyList();
                Pairs check = new Pairs(row, col);
                if (newSpots.contains(check)) { continue; }
                // swap the state of an empty cell with a dissatisfied agent
                if (isAgentSatisfied(schellingSegGrid[row][col], row, col) == 1){
                    System.out.println("satisfied");
                    makeTheMove(row, col, newSpots);
                }

            }
        }

    }

    //switch state of two elements
    private void makeTheMove(int row, int column, ArrayList<Pairs> spots) {
        int houseSwitchNum = getRandomNumber(0, spots.size() - 1);
        int stateTemp1 = schellingSegGrid[row][column].getMyState();
        Pairs emptyCoord = spots.get(houseSwitchNum);
        schellingSegGrid[emptyCoord.x][emptyCoord.y].setState(stateTemp1);
        spots.remove(spots.get(houseSwitchNum));
        schellingSegGrid[row][column].setState(EMPTY);
    }

    // random number generator for deciding where to make the move
    private int getRandomNumber(int min, int max) {

        return (int) ((Math.random() * (max - min)) + min);
    }

    // checks to see what fraction of our valid neighbors (empty cells not included) are the same
    private double sameNeighbors(Cell agent, int xCoordCell, int yCoordCell) {
        int[] neighbors = agent.neighborStatus(schellingSegGrid, xCoordCell, yCoordCell, schellingSegGrid.length, schellingSegGrid[0].length);
        int numNeighbors = 0;
        int sameNeighbors = 0;
        for(int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] > 0) {
                numNeighbors += 1;
                if (agent.getMyState() == neighbors[i] ) {
                    sameNeighbors += 1;
                }
            }
        }
        double neighborhoodStats = ((float) sameNeighbors) / numNeighbors;
        System.out.println(neighborhoodStats);
        return neighborhoodStats;
    }

    @Override
    public void updateGrid() {
        updateOnCycle();

        System.out.println();
        // testing for correctly updated grid
        for (int row = 0; row < schellingSegGrid.length; row++) {
            for (int col = 0; col < schellingSegGrid[row].length; col++) {

                System.out.print(schellingSegGrid[row][col].getMyState() + ", ");

            }
            System.out.println();
        }
    }

    @Override
    public String getCellState(int row, int col, boolean intValue){

        if(schellingSegGrid[row][col].getMyState() == 0){
            return intValue ? "0" : "EMPTY";
        }
        else if(schellingSegGrid[row][col].getMyState() == 1){
            return intValue ? "1" : "AGENT1";
        }
        else{
            return intValue ? "2" : "AGENT2";
        }
    }

}

