package cellsociety.model;

import cellsociety.model.unit.Cell;
import cellsociety.view.SimulationInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * Wator World simulation class
 *
 * Purpose: Used to create the Wator World simulation, a simulation of fish and shark cells that have
 * rules applied to each individual cell depending on the # of alive cells around it
 *
 * Assumptions: We assumed that the fish can only die if the shark eat them and that shark only die from lack of
 * energy from not eating
 *
 * Dependencies: This depends on all the imported javafx lines contained in the cellsociety.model
 * package. Furthermore, it imports the cellsociety.view.SimulationInfo record, which contains the
 * necessary info to create the simulation. Finally, it depends on the cell.society.model.unit package for each
 * individual cell
 *
 * Examples: One example of how to use is to create an XML file following the examples provided in
 * the resources file labeled with gameOfLife. The labels for cells range from 0 to 2 with them
 * being EMPTY, FISH, or SHARK respectively. With this, you can set up your own simulation with
 * an initial configuration.
 *
 * @author THIVYA SIVARAJAH
 */

public class WatorWorld<memo> extends GridClass {

    private Cell[][] watorWorldGrid;
    private int gridWidth;
    private int gridHeight;
    private int numberNeighbors = 4;
    private int resetTime = 0;
    private int sharkAteFish = 2;
    private int sharkEnergy = 4;
    private int reproductionThresh = 4;

    private final static int EMPTY = 0;
    private final static int FISH = 1;
    private final static int SHARK = 2;
    private HashMap<String, Integer> memo;

    // our constructor which sets up our grid portion of the UI
    public WatorWorld(SimulationInfo record){

        int[][] inputData = record.initialGrid();

        // creating the spreadingFire grid with a 1 layer cell padding of EMPTY value
        watorWorldGrid = new Cell[inputData.length][inputData[0].length];

        for (int row = 0; row < watorWorldGrid.length; row++) {
            for (int col = 0; col < watorWorldGrid[0].length; col++) {
                watorWorldGrid[row][col] = new Cell();
                watorWorldGrid[row][col].setState(inputData[row][col]);
                watorWorldGrid[row][col].setNextGenState(inputData[row][col]);
            }
        }


    }


    // updates based on neighbor stat
    private void updateOnCycle(int gridWidth, int gridHeight) {

        memo = new HashMap<>();
        for(int row = 0; row < gridWidth; row++) {
            for (int col = 0; col < gridHeight; col++) {
                int index = getDirection(row, col);
                memo.put(row + "," + col, index);
            }
        }
        System.out.println(memo);
        for(int row = 0; row < gridWidth; row++) {
            for(int col = 0; col < gridHeight; col++) {
                cellMoves(watorWorldGrid, row, col, memo.get(row + "," + col));
            }
        }

    }

    private void cellMoves(Cell[][] watorWorldGrid, int row, int col, int direction) {

        int index = direction;
        System.out.println(index);
        int [] [] dir = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{-1,1},{1,-1},{0,0}};
        index = updateTimeAndEnergy(index, watorWorldGrid[row][col]);

        if (index != -1) {
            if(watorWorldGrid[row][col].getMyState() == FISH || watorWorldGrid[row][col].getMyState() == SHARK) {
                swapCell(watorWorldGrid[row][col], watorWorldGrid[row + dir[index][0]][col + dir[index][1]]);
            }
            else if (watorWorldGrid[row][col].getMyState() == FISH && watorWorldGrid[row + dir[index][0]][col + dir[index][1]].getMyState() == SHARK) {
                watorWorldGrid[row + dir[index][0]][col + dir[index][1]].setChronons(sharkAteFish + watorWorldGrid[row + dir[index][0]][col + dir[index][1]].getChronons());
            }
            else if (watorWorldGrid[row + dir[index][0]][col + dir[index][1]].getMyState() == SHARK) {
                sharkReproduce(watorWorldGrid[row][col], watorWorldGrid[row + dir[index][0]][col + dir[index][1]]);
            }
            else if (watorWorldGrid[row + dir[index][0]][col + dir[index][1]].getMyState() == FISH) {
                fishReproduce(watorWorldGrid[row][col], watorWorldGrid[row + dir[index][0]][col + dir[index][1]]);
            }
        }
    }

    private int updateTimeAndEnergy(int index, Cell unit) {
        unit.setTime(unit.getTime() + 1);
        unit.setChronons(unit.getChronons() - 1);
        int direction;

        if(unit.getChronons() == 0) {
            direction = -1;
            unit.setState(EMPTY);
        }
        else {
            direction = index;
        }
        return direction;
    }

    private void swapCell(Cell oldCell, Cell newCell) {
        int state = oldCell.getMyState();
        int time = oldCell.getTime();
        int chronons = oldCell.getChronons();

        newCell.setState(state);
        newCell.setTime(time);
        newCell.setChronons(sharkEnergy);

        oldCell.setState(EMPTY);
        oldCell.setTime(resetTime);
    }

    private void sharkReproduce(Cell oldCell, Cell shark) {
        if (shark.getTime() == reproductionThresh && shark.getMyState() == SHARK) {
            oldCell.setState(SHARK);
            shark.setTime(resetTime);
        }
    }

    private void fishReproduce(Cell oldCell, Cell fish) {
        if (fish.getTime() == reproductionThresh) {
            oldCell.setState(FISH);
            fish.setTime(resetTime);
        }
    }

    // see where the fish moves, if at all
    // direction = 0, right; direction = 1, left; direction = 2, up; direction = 3, down
    private HashMap<Integer, Integer> fishDirectionOptions(Cell[][] watorWorldGrid, int row, int col) {
        int[] neighbors = watorWorldGrid[row][col].neighborStatus(watorWorldGrid, row, col, watorWorldGrid.length, watorWorldGrid[0].length);
        HashMap <Integer, Integer> places = new HashMap<>();

        for (int i = 0; i < numberNeighbors; i++){
            if (neighbors[i] == EMPTY) {
                places.put(i, neighbors[i]);
            }
        }
        // System.out.println(places);
        return places;
    }

    // see where the shark move, if at all
    private HashMap<Integer, Integer> sharkDirectionOptions(Cell[][] watorWorldGrid, int row, int col) {
        int[] neighbors = watorWorldGrid[row][col].neighborStatus(watorWorldGrid, row, col, watorWorldGrid.length, watorWorldGrid[0].length);
        HashMap <Integer, Integer> places = new HashMap<>();

        for (int i = 0; i < numberNeighbors; i++){
            if (neighbors[i] == EMPTY || neighbors[i] == FISH) {
                places.put(i, neighbors[i]);
            }
        }
        return places;
    }

    private int fishSharkDirection(HashMap<Integer, Integer> directionOptions) {
        int direction = 0;
        if (directionOptions.isEmpty()) {
            direction = -1;
        } else {
            ArrayList<Integer> randomDirection = new ArrayList<Integer>(directionOptions.keySet());
            Random r = new Random();
            direction = randomDirection.get(r.nextInt(randomDirection.size()));
        }
        // System.out.println(direction);
        return direction;
    }

    private int getDirection(int row, int col) {
        int direction = 0;
        HashMap<Integer, Integer> options;

        if (watorWorldGrid[row][col].getMyState() == FISH) {
            options = fishDirectionOptions(watorWorldGrid, row, col);
            direction = fishSharkDirection(options);
        } else if (watorWorldGrid[row][col].getMyState() == SHARK) {
            options = sharkDirectionOptions(watorWorldGrid, row, col);
            direction = fishSharkDirection(options);
        } else if (watorWorldGrid[row][col].getMyState() == EMPTY){
            direction = -1;
        }
        return direction;
    }

    @Override
    public void updateGrid() {
        updateOnCycle(watorWorldGrid.length, watorWorldGrid[0].length);

        for (int row = 0; row < watorWorldGrid.length; row++) {
            for (int col = 0; col < watorWorldGrid[row].length; col++) {

                System.out.print(watorWorldGrid[row][col].getMyState() + ", ");

            }
            System.out.println();
        }
    }

    @Override
    public String getCellState(int row, int col, boolean intValue){
        if(watorWorldGrid[row][col].getMyState() == 0){
            return intValue ? "0" : "EMPTY";
        }
        else if(watorWorldGrid[row][col].getMyState() == 1){
            return intValue ? "1" : "FISH";
        }
        else{
            return intValue ? "2" : "SHARK";
        }

    }
}

