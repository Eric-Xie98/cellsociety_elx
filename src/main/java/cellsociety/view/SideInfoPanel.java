package cellsociety.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Creates an Info Panel that displays the information for the current simulation to the left of the
 * grid
 *
 * @author Robert Cranston
 */
public class SideInfoPanel {

  private VBox info;
  private static Label title;
  private static Label author;
  private static Label description;


  private SimulationInfo myRecord;
  public static final Insets insets = new Insets(10);
  public static final int BORDER_WIDTH = 300;
  public static final int INFO_MAX_HEIGHT = 300;

  /**
   * Initializes the record the contains all of the information for the simulation
   */
  public SideInfoPanel(SimulationInfo record) {
    myRecord = record;
    setupInfoPane();
  }

  //Create labels for all of the information to be stored in
  private void addInfoToPane() {
    title = new Label(myRecord.title());
    title.setId("simulationTitle");
    title.setWrapText(true);
    author = new Label(myRecord.author());
    author.setWrapText(true);
    author.setId("author");
    description = new Label(myRecord.description());
    description.setWrapText(true);
    info.getChildren().addAll(title, author, description);
  }

  private void setupInfoPane() {
    info = new VBox();
    info.setPadding(insets);
    info.setMaxWidth(BORDER_WIDTH);
    info.setMaxHeight(INFO_MAX_HEIGHT);
    addInfoToPane();
  }


  public VBox getPane() {
    return info;
  }


  public static void switchTextColor(boolean isNightMode) {
    if (isNightMode) {
      title.setTextFill(Color.WHITE);
      author.setTextFill(Color.WHITE);
      description.setTextFill(Color.WHITE);
    } else {
      title.setTextFill(Color.BLACK);
      author.setTextFill(Color.BLACK);
      description.setTextFill(Color.BLACK);
    }
  }

}
