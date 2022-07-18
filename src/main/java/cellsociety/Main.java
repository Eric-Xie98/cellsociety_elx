package cellsociety;

import cellsociety.configuration.FileReader;
import cellsociety.view.CellSocietyView;
import cellsociety.view.SimulationInfo;
import javafx.application.Application;
import javafx.stage.Stage;
import java.awt.Dimension;



/**
 * Purpose: The main class which the program is run from. Contains the bare necessary static
 * final variables for the start method in taking and creating a CellSocietyView object to display
 *
 * Assumptions: Wouldn't do much besides start up the simulation and display it
 *
 * Depends on SimulationInfo, extends Application, JavaFx stage and dimension to accomplish its task
 *
 * Example: When we run the program, it should start up the simulation viewer.
 *
 * Other: N/A
 *
 * @author Robert Cranston, Thivya Sivarajah, Eric Xie
 */

public class Main extends Application {
    // useful names for constant values used
  public static final String TITLE = "Cell Society";
  public static final String LANGUAGE = "English";
  public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
  public static final String INITIAL_SIMULATION = "data/testSimulations/spreadingFireEDGE4.xml";



    /**
     * Purpose: The place where it all starts. Literally.
     *
     * Assumptions: Run auto by the program when we boot it up; takes in our default file and creates
     * the simulation viewer with the help of the other classes and displays it
     *
     * Exceptions: Many exceptions could occur inside the classes it depends on as errors regarding arrays
     * or XML files can occur
     *
     * @param stage The JavaFX stage where things are placed for displaying
     *
     * @return void
     *
     *
     */

    @Override
    public void start (Stage stage) {

      FileReader initial = new FileReader(INITIAL_SIMULATION);
      SimulationInfo myRecord = initial.getRecord();
      CellSocietyView view = new CellSocietyView(LANGUAGE, myRecord);
      // give the window a title
      stage.setTitle(TITLE);
      // add our user interface components to Frame and show it
      stage.setScene(view.makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
      stage.show();

    }

}
