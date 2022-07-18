package cellsociety.view;


import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import static cellsociety.view.SideInfoPanel.switchTextColor;

/**
 * Holds all of the main view elements for the application
 *
 * <p>
 * Initializes a welcome page that first allows users to select the language that they want to use
 * for the project. After selecting and submitting a language it initializes a a simulation page
 * that holds all of the simulations and their configurations.
 * </p>
 *
 * <p>
 * Allows users to add any number of simulations that run completely separately, load any file into
 * those simulations, and configure those simulations separately.
 * </p>
 *
 * @author Robert Cranston
 */
public class CellSocietyView {

  public static final String DEFAULT_RESOURCE_PACKAGE = "/";
  private static final String DARK_BACKGROUND = "-fx-background-color: BLACK";
  private static final String LIGHT_BACKGROUND = "-fx-background-color: BEIGE";
  private String STYLESHEET;

  private ResourceBundle myResources;
  private GridPane gridOfSimulations;
  private BorderPane myRoot;
  private SimulationInfo initialRecord;
  private WelcomeScreen myWelcome;
  private HBox TitleBox;
  private ScrollPane ScrollBox;
  private HBox ScreenConfigBox;
  private Label titleText;

  private int currentGridY;
  private int currentGridX;
  private boolean inNightMode;

  /**
   * Initializes the starting properties need to create the initial simulation
   *
   * @param language - chooses what language file to read from between: English and Spanish
   * @param record   - record holding the info for the initial simulation to be displayed
   */
  public CellSocietyView(String language, SimulationInfo record) {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    initialRecord = record;
    currentGridY = 0;
    currentGridX = 0;
    myRoot = new BorderPane();
    STYLESHEET = "dayMode.css";
    inNightMode = false;
  }

  /**
   * Creates scene to hold all of the elements to be displayed to the user.
   *
   * @param width  - width in dixels of initial window
   * @param height - height in pixels of initial window
   * @return scene holding all elements displayed
   */
  public Scene makeScene(int width, int height) {
    displayWelcome();
    Scene scene = new Scene(myRoot, width, height);
    scene.getStylesheets()
        .add(getClass().getResource(DEFAULT_RESOURCE_PACKAGE + STYLESHEET).toExternalForm());
    return scene;
  }

  //Creates the initial welcome screen for the user to select a language
  private void displayWelcome() {
    myRoot.getChildren().clear();
    myWelcome = new WelcomeScreen(myResources);
    myRoot.setCenter(myWelcome.getPane());
    Button submitLanguage = CellSocietyView.makeButton("Submit", event -> submitLanguage(),
        myResources);
    myRoot.setBottom(submitLanguage);
    currentGridY = 0;
    currentGridX = 0;
  }

  //chooses the resource bundle to use based on the type
  private void submitLanguage() {
    myResources = myWelcome.getMyResources();
    myRoot.setTop(makeTitle());
    displayApplication();
  }

  //sets the root to contain the elements of the main application: the title, bottom configuration
  //panel and the grid of simulations
  private void displayApplication() {
    myRoot.getChildren().clear();
    setupSimulationGrid();
    ScrollBox = new ScrollPane();
    ScrollBox.setContent(gridOfSimulations);
    myRoot.setTop(makeTitle());
    myRoot.setCenter(ScrollBox);
    myRoot.setBottom(makeSimulationConfigRow());
    addSimulation();
  }

  //initializes the grid of simulations and ensures that it is the same height and width of the window.
  private void setupSimulationGrid() {
    gridOfSimulations = new GridPane();
    gridOfSimulations.prefWidthProperty().bind(myRoot.widthProperty());
    gridOfSimulations.prefHeightProperty().bind(myRoot.heightProperty());
  }

  //Makes the bottom row of configuration buttons that affect the whole application
  private Node makeSimulationConfigRow() {
    ScreenConfigBox = new HBox();
    Button addSim = makeButton("AddSimulation", event -> addSimulation(), myResources);
    Button removeSim = makeButton("RemoveSimulation", event -> removeSimulation(), myResources);
    Button changeMode = makeButton("ChangeMode", event -> changeViewMode(), myResources);
    Button changeLanguage = makeButton("ChangeLanguage", event -> displayWelcome(), myResources);
    ScreenConfigBox.setId("configBox");
    ScreenConfigBox.getChildren().addAll(addSim, removeSim, changeLanguage, changeMode);
    return ScreenConfigBox;

  }

  //removes the bottom-most right-most simulation that is currently being displayed
  private void removeSimulation() {
    if (!(currentGridY == 0 && currentGridX == 1)) {
      if (currentGridX == 1) {
        currentGridX--;
      } else {
        currentGridY--;
        currentGridX++;
      }
      gridOfSimulations.getChildren().remove(currentGridY * 2 + currentGridX);
    }
  }

  //adds a new simulation to the bottom-most right-most free simulation space. Goes infinitely
  // downwards but only two columns.
  private void addSimulation() {
    SimulationView sim = new SimulationView(myResources, initialRecord);
    gridOfSimulations.add(sim.getBorderPane(), currentGridX, currentGridY);
    if (currentGridX == 1) {
      currentGridY++;
      currentGridX = 0;
    } else {
      currentGridX++;
    }
  }

  //changes the simulation from light to dark mode
  private void changeViewMode() {

    if (!inNightMode) {
      inNightMode = true;
      changeBackgroundColors(DARK_BACKGROUND);
      titleText.setTextFill(Color.WHITE);
    } else {
      inNightMode = false;
      changeBackgroundColors(LIGHT_BACKGROUND);
      titleText.setTextFill(Color.BLACK);
    }
    switchTextColor(inNightMode);
  }

  //creates a popup message with the given passed message as a parameter
  public static void showMessage(AlertType type, String message) {
    (new Alert(type, message, new ButtonType[0])).showAndWait();
  }

  //creates a title centered on the top section of the root.
  private Node makeTitle() {
    TitleBox = new HBox();
    titleText = new Label(myResources.getString("Title"));
    titleText.setId("title");
    TitleBox.getChildren().add(titleText);
    TitleBox.setId("titleBox");
    return TitleBox;
  }

  //changes the background color of the window to the provided color
  private void changeBackgroundColors(String backgroundColor) {
    TitleBox.setStyle(backgroundColor);
    gridOfSimulations.setStyle(backgroundColor);
    ScrollBox.setStyle(backgroundColor);
    ScreenConfigBox.setStyle(backgroundColor);

  }

  //returns a button with the title provided linked to the event passed as a parameter
  public static Button makeButton(String property, EventHandler<ActionEvent> handler,
      ResourceBundle resources) {
    Button result = new Button();
    String label = resources.getString(property);
    result.setText(label);
    result.setOnAction(handler);
    return result;
  }

}
