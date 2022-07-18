package cellsociety.configuration;

import cellsociety.model.GridClass;
import cellsociety.view.CellSocietyView;
import cellsociety.view.SimulationInfo;
import cellsociety.XMLException;
import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Purpose: Write to a file when the save button is clicked
 * <p>
 * Assumptions: Assumed that the simulationview file being saved is already going to be formatted
 * correctly and won't have errors
 * <p>
 * Dependencies: Depends on a lot of JavaFx imports and w3c imports; takes in the cellsociety.config
 * package as well as a few other classes like Grid Class and such
 * <p>
 * Example: Used in SimulationView's saveState() method
 *
 * @author Robert Cranston
 */

public class FileWriters {

  public static final int WIDTH = 400;
  public static final int HEIGHT = 100;
  private final Stage myStage;
  private final VBox myRoot;
  private String myTitle;
  private String myDescription;
  private String myAuthor;
  private ResourceBundle myResources;
  private String myFileName;
  private SimulationInfo myRecord;
  private GridClass myCurrentGrid;

  /**
   * Purpose: Constructor for FileWriter class
   * <p>
   * Assumptions: Assumed that the simulationview file being saved is already going to be formatted
   * correctly and won't have errors
   * <p>
   *
   * @param resources   ResourceBundle class object
   * @param record      SimulationInfo object holding the information of the simulation
   * @param currentGrid the current grid in the simulation
   * @author Robert Cranston
   */

  public FileWriters(ResourceBundle resources, SimulationInfo record, GridClass currentGrid) {
    myStage = new Stage();
    myRoot = new VBox();
    myResources = resources;
    myRecord = record;
    myCurrentGrid = currentGrid;
    addInputs();
    Scene scene = new Scene(myRoot, WIDTH, HEIGHT);
    myStage.setScene(scene);
    myStage.show();
  }

  // writes to the file and catches an exception if unable to do so
  private void writeToFile(Document doc) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult file = new StreamResult(new File("src/main/resources/" + myFileName));
      transformer.transform(source, file);
    } catch (TransformerException e) {
      throw new XMLException("Cant write to file");
    }
  }

  // creates an XML by appending the root to the doc
  private void createXML(Document doc) {

    Element root = doc.createElement("simulation");
    doc.appendChild(root);
    appendDetails(doc, root);
    appendArray(doc, root);

  }

  // appends the array to the document that's given to the method
  private void appendArray(Document doc, Element root) {
    Element array = doc.createElement("array");
    root.appendChild(array);
    for (int i = 0; i < myRecord.height(); i++) {
      String ret = "";
      for (int j = 0; j < myRecord.width(); j++) {
        ret = ret + myCurrentGrid.getCellState(i, j, true) + ", ";
      }
      ret = ret.substring(0, ret.length() - 2);
      appendElement(doc, array, "row", ret);
    }
  }

  // appends general details of the simulation to the document
  private void appendDetails(Document doc, Element root) {
    Element details = doc.createElement("details");
    root.appendChild(details);
    appendElement(doc, details, "type", myRecord.type());
    appendElement(doc, details, "title", myTitle);
    appendElement(doc, details, "author", myAuthor);
    appendElement(doc, details, "description", myDescription);
    appendElement(doc, details, "width", "" + myRecord.width());
    appendElement(doc, details, "height", "" + myRecord.height());
    appendPossibleParameters(doc, details);
  }

  // appends any possible parameters for simulations that require it
  private void appendPossibleParameters(Document doc, Element details) {
    if (myRecord.possibleParameters() != null) {
      Element percentageParameters = doc.createElement("percentageParameters");
      details.appendChild(percentageParameters);
      for (String possible : myRecord.possibleParameters().keySet()) {
        appendElement(doc, percentageParameters, "possibleValue",
            possible + ":" + myRecord.possibleParameters().get(possible));
      }
    }
  }

  // appends any elements needed to document
  private void appendElement(Document doc, Element parent, String name, String value) {
    Element node = doc.createElement(name);
    node.appendChild(doc.createTextNode(value));
    parent.appendChild(node);
  }

  // method for setting up the document, catches exception if it is unable to create the new file
  private Document setupDocument() {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder;
    try {
      dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();
      return doc;
    } catch (ParserConfigurationException e) {
      throw new XMLException("Cannot create new file");
    }
  }

  // adds the input fields for users to name the title, author, etc.
  private void addInputs() {
    TextField title = new TextField(myResources.getString("UserInputEnterTitle"));
    TextField author = new TextField(myResources.getString("UserInputEnterAuthor"));
    TextField description = new TextField(myResources.getString("UserInputEnterDescription"));
    Button submit = CellSocietyView.makeButton("Submit",
        event -> submitUserInput(title.getCharacters().toString(),
            author.getCharacters().toString(), description.getCharacters().toString()),
        myResources);
    myRoot.getChildren().addAll(title, author, description, submit);
  }

  // submits whatever the user inputted into aforementioned text fields
  private void submitUserInput(String title, String author, String description) {
    myStage.close();
    myTitle = title != null ? title : myRecord.title();
    myFileName = myTitle.trim() + ".xml";
    myAuthor = author != null ? author : myRecord.author();
    myDescription = description != null ? description : myRecord.description();
    Document doc = setupDocument();
    createXML(doc);
    writeToFile(doc);
  }


}
