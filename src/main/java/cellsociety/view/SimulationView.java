package cellsociety.view;

import cellsociety.XMLException;
import cellsociety.configuration.FileReader;
import cellsociety.configuration.FileWriters;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import cellsociety.view.grid.HexagonGridView;
import cellsociety.view.grid.RectangleGridView;
import cellsociety.view.grid.TriangleGridView;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Creates a border pane that contains the full simulation based on the record passed to it. This
 * includes all of the about information, the the controls, the configuration buttons, and the grid
 * itself
 *
 * Depends heavily on the GridView Class and its subclasses, and the SideInfoPanel class as these
 * two classes provide all of the simulation info.
 *
 * This class is used whenever a new simulation is created.
 *
 * @author Robert Cranston
 */
public class SimulationView {

  public static final FileChooser FILE_CHOOSER = makeChooser("*.xml");
  public static final int SLIDER_MINIMUM = 1;
  public static final int SLIDER_MAXIMUM = 10;
  public static final double SLIDER_START = 5;

  private final String hexagon;
  private final String triangle;
  private final String rectangle;
  private final List<String> GRID_SHAPE_OPTIONS;


  private Button myPauseButton;
  private Button myPlayButton;
  private Button myNextButton;
  private Button myResetButton;
  private VBox mySidePanel;
  private GridView myGridView;
  private BorderPane myRoot;
  private String myGridShape;
  private ResourceBundle myResources;
  private SimulationInfo myRecord;

  /**
   *
   * @param resources Resource Bundle that has text to display to user
   * @param record has initial information for the simulation
   */
  public SimulationView(ResourceBundle resources, SimulationInfo record) {
    myRoot = new BorderPane();
    myRecord = record;
    myResources = resources;
    hexagon = myResources.getString("Hexagon");
    triangle = myResources.getString("Triangle");
    rectangle = myResources.getString("Rectangle");
    GRID_SHAPE_OPTIONS = List.of(rectangle, triangle, hexagon);
    myGridShape = rectangle;
    myRoot.setCenter(chooseGrid(myRecord));
    myRoot.setLeft(makeSidePanel());
    myRoot.setBottom(makeConfigButtons());
  }

  //make buttons that allow the user to load a file, save the current state as a new xml file, and
  // choose the grid shape
  private Node makeConfigButtons() {
    HBox box = new HBox();
    box.setId("configBox");
    Button loadFile = CellSocietyView.makeButton("LoadFile", event -> selectNewFile(), myResources);
    Button saveFile = CellSocietyView.makeButton("SaveFile", event -> saveState(), myResources);
    ChoiceBox<String> gridShapeChoice = makeChoiceBox(GRID_SHAPE_OPTIONS,
        (ov, old_val, new_val) -> changeGridShape(new_val));
    gridShapeChoice.getSelectionModel().select(0);
    box.getChildren().addAll(loadFile, saveFile, gridShapeChoice);
    return box;
  }

  //pauses the animation if it is playing and writes a new file with the values
  private void saveState() {
    myGridView.pause();
    FileWriters save = new FileWriters(myResources, myRecord, myGridView.getCurrentGrid());
  }

  /**
   * Creates a choice box with the options provided, when an option is selected it preforms the event specified
   * @param options List of options to include in the choice box
   * @param handler action to occur when choice is selected
   * @return choice box with the options given in the parameter
   */
  public static ChoiceBox<String> makeChoiceBox(List<String> options,
      ChangeListener<String> handler) {
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    choiceBox.getItems().addAll(options);
    choiceBox.valueProperty().addListener(handler);
    return choiceBox;
  }

  //resets the grid with the new shape provided
  private void changeGridShape(String newShape) {
    myGridShape = newShape;
    System.out.println(newShape);
    myGridView.pause();
    myRoot.setCenter(chooseGrid(myRecord));
  }

  //Opens box to select a new file and selects it if it is valid.
  private void selectNewFile() {
    try {
      myGridView.pause();
      File dataFile = FILE_CHOOSER.showOpenDialog(new Stage());
      if (dataFile != null) {
        FileReader initial = new FileReader(dataFile.getCanonicalPath());
        myRecord = initial.getRecord();
        myRoot.setCenter(chooseGrid(myRecord));
        myRoot.setLeft(makeSidePanel());
      }
    } catch (XMLException | IOException e) {
      CellSocietyView.showMessage(AlertType.ERROR, e.getMessage());
    }
  }

  private VBox makeSidePanel() {
    mySidePanel = new VBox();
    mySidePanel.setId("sidePanel");
    SideInfoPanel info = new SideInfoPanel(myRecord);
    VBox infoBox = info.getPane();
    bindWidth(infoBox, mySidePanel);
    VBox control = displayControlButtons();
    bindWidth(control, mySidePanel);
    VBox speed = makeSpeedSlider();
    mySidePanel.getChildren().addAll(info.getPane(), control, speed);
    return mySidePanel;
  }

  private VBox makeSpeedSlider() {
    VBox speedBox = new VBox();
    speedBox.setId("speedBox");
    Slider slider = new Slider(SLIDER_MINIMUM, SLIDER_MAXIMUM, SLIDER_START);
    slider.valueProperty().addListener(
        (ov, old_val, new_val) -> myGridView.setAnimationSpeed(new_val));
    Label speed = new Label(myResources.getString("Speed"));
    speedBox.getChildren().addAll(slider, speed);
    return speedBox;
  }

  private void bindWidth(Pane B, Pane A) {
    A.prefWidthProperty().bind(B.widthProperty());
  }

  private VBox displayControlButtons() {
    VBox control = new VBox();
    control.setId("control");
    HBox playPause = new HBox();
    playPause.setId("playPause");
    HBox resetNext = new HBox();
    resetNext.setId("resetNext");

    makeControlButtons();

    resetNext.getChildren().addAll(myResetButton, myNextButton);
    playPause.getChildren().addAll(myPauseButton, myPlayButton);
    control.getChildren().addAll(playPause, resetNext);
    return control;
  }

  private void makeControlButtons() {
    myPauseButton = CellSocietyView.makeButton("PauseButton", event -> myGridView.pause(),
        myResources);
    myPlayButton = CellSocietyView.makeButton("PlayButton", event -> myGridView.play(),
        myResources);
    myResetButton = CellSocietyView.makeButton("ResetButton", event -> myGridView.reset(),
        myResources);
    myNextButton = CellSocietyView.makeButton("NextButton", event -> myGridView.next(),
        myResources);
  }


  public GridPane chooseGrid(SimulationInfo record) {
    if (myGridShape.equals(myResources.getString("Rectangle"))) {
      myGridView = new RectangleGridView(record);
    }
    if (myGridShape.equals(myResources.getString("Triangle"))) {
      myGridView = new TriangleGridView(record);
    }
    if (myGridShape.equals(myResources.getString("Hexagon"))) {
      myGridView = new HexagonGridView(record);
    }
    GridPane myGrid = myGridView.getDisplayGrid();
    return myGrid;
  }

  private static FileChooser makeChooser(String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(
        new ExtensionFilter("Text Files", extensionAccepted));
    return result;
  }

  public BorderPane getBorderPane() {
    return myRoot;
  }
}
