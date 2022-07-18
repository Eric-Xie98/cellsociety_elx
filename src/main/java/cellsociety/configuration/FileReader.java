package cellsociety.configuration;

import static java.lang.Integer.parseInt;

import cellsociety.view.SimulationInfo;
import cellsociety.XMLException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.Random;

/**
 * Class to read a xml file and store the descriptive and array information inside it.
 * <p>
 * Assumptions: Assuming that the file is correctly passed in an XML file that exists; we have error
 * catching for this
 * <p>
 * Dependencies: This class depends on a lot of other classes to work and imports the
 * cellsociety.configuration.
 * <p>
 * Example: Used in Main to read the input file
 * <p>
 * Other: Catches incorrect XML file names that are passed to it
 *
 * @author Robert Cranston
 */
public class FileReader {

  String width = "width";
  String height = "height";
  private final List<String> tagsToAdd = List.of("type", "title", "author", "description",
      width, height);
  private Map<String, Integer> possibleParameters;
  private File myFile;
  private Map<String, String> aboutSimulation;
  private int[][] myGrid;
  private SimulationInfo myRecord;

  /**
   * Initializes the file path and starts method to read the simulation.
   * <p>
   * assumes the string file is correct
   * <p>
   * exceptions: will throw a emptyFile error if no error is found
   *
   * @param file file to be read
   */
  public FileReader(String file) {
    myFile = new File(file);
    possibleParameters = new HashMap<>();
    readXML();
  }

  //Reads the descriptive text and the array from the xml file
  private void readXML() {
    aboutSimulation = new HashMap();
    Document document = openDocument();
    if (document != null) {
      try {
        Element description = (Element) document.getElementsByTagName("details").item(0);
        addToMap(description);
        addPossibleParameters(
            (Element) description.getElementsByTagName("percentageParameters").item(0));
        createGrid(document);
        myRecord = new SimulationInfo(aboutSimulation, myGrid, possibleParameters);

      } catch (XMLException e) {
        throw new XMLException(e.getMessage());
      }
    }
  }

  // creates a new 2d grid after parsing through the XML file and pulling its height and width
  private void createGrid(Document document) {
    try {
      myGrid = new int[parseInt(aboutSimulation.get(height))][parseInt(aboutSimulation.get(width))];
      Element grid = (Element) document.getElementsByTagName("array").item(0);
      if (grid.getElementsByTagName("randomize").item(0) != null) {
        createRandomGrid((Element) grid.getElementsByTagName("randomize").item(0));
      } else {
        NodeList rows = grid.getElementsByTagName("row");
        createDefinedGrid(rows);
      }
    } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
      throw new NullPointerException("Incorrect Array formatting");
    }
  }

  // creates a random 2d grid
  private void createRandomGrid(Element randomize) {
    int maxValue = Integer.parseInt(
        randomize.getElementsByTagName("maxValue").item(0).getTextContent());
    Random rand = new Random();
    int val;
    for (int i = 0; i < Integer.parseInt(aboutSimulation.get("height")); i++) {
      for (int j = 0; j < Integer.parseInt(aboutSimulation.get("width")); j++) {
        val = rand.nextInt(maxValue + 1);
        myGrid[i][j] = val;
      }
    }
  }

  private void addPossibleParameters(Element parameters) {
    if (parameters != null) {
      try {

        NodeList percentages = parameters.getElementsByTagName("possibleValue");
        for (int i = 0; i < percentages.getLength(); i++) {
          Node value = percentages.item(i);
          String[] contents = value.getTextContent().split(":");
          possibleParameters.put(contents[0].trim(), parseInt(contents[1].trim()));
        }
      } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
        throw new XMLException("Error with possible parameters");
      }
    }
  }

  private void createDefinedGrid(NodeList rows) {

    for (int i = 0; i < rows.getLength(); i++) {
      Node row = rows.item(i);
      addToGrid(row, i);
    }
  }


  //takes in a string containing the initial values for a row and adds them to the grid instance variable
  private void addToGrid(Node row, int rowNumber) {
    try {
      String rowContents = row.getTextContent();
      rowContents = rowContents.replaceAll("\\s", "");
      String[] strArray = rowContents.split(",");
      int columnPosition = 0;
      for (String element : strArray) {
        myGrid[rowNumber][columnPosition] = parseInt(element);
        columnPosition++;
      }
    } catch (NullPointerException | IndexOutOfBoundsException e) {
      throw new XMLException("Error with array format");
    }
  }

  //attempts to open the file provided in the constructor and sets badData to true if it can't.
  private Document openDocument() {
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document document = db.parse(myFile);
      document.getDocumentElement().normalize();
      return document;
    } catch (IOException | SAXException | ParserConfigurationException e) {
      throw new XMLException("Cannot open file");
    }
  }

  //given a root element and a list of tags to look for, it adds the tags the map private instance variable.
  private void addToMap(Element element) {
    for (String tag : tagsToAdd) {
      try {
        aboutSimulation.put(tag, element.getElementsByTagName(tag).item(0).getTextContent());
      } catch (NullPointerException e) {
        throw new XMLException("Error with format of tag: " + tag);
      }
    }
  }

  /**
   * @return Map wit details about the simulation
   */
  public SimulationInfo getRecord() {
    return myRecord;
  }

  /**
   * @return int[][] with the initial state of the grid
   */
  public int[][] getGrid() {
    return myGrid;
  }
}

