package cellsociety.view;

import static cellsociety.view.CellSocietyView.DEFAULT_RESOURCE_PACKAGE;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Creates a splash screen that allows the user to select a language before displaying the full
 * application
 *
 * @author Robert Cranston
 */

public class WelcomeScreen {

  Pane myPane;
  ResourceBundle myResources;
  private String language;
  private List<String> LANGUAGE_OPTIONS;
  private Label myWelcome;
  private Label selectLang;
  private ChoiceBox myLangChoiceBox;
  private VBox myContainer;

  public WelcomeScreen(ResourceBundle resources) {
    myPane = new StackPane();
    myContainer = new VBox();
    myResources = resources;

    createScreen();
  }

  private void createScreen() {
    LANGUAGE_OPTIONS = List.of(myResources.getString("English"), myResources.getString("Spanish"));
    myLangChoiceBox = SimulationView.makeChoiceBox(LANGUAGE_OPTIONS,
        (ov, old_val, new_val) -> updateWelcomeScreen(new_val));
    createElements("English");
    myPane.getChildren().add(myContainer);
  }

  private void updateWelcomeScreen(String new_val) {
    language = new_val;
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    createElements(new_val);
  }

  private void createElements(String val) {
    myContainer.getChildren().clear();
    myWelcome = new Label(myResources.getString("Welcome"));
    selectLang = new Label(myResources.getString("SelectLanguage"));
    myWelcome.setId("welcomeText");
    myContainer.setId("welcomeScreen");
    myContainer.getChildren().addAll(myWelcome, selectLang, myLangChoiceBox);
  }


  public Pane getPane() {
    return myPane;
  }

  public ResourceBundle getMyResources() {
    return myResources;
  }
}
