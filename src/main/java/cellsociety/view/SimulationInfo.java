package cellsociety.view;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Record to store all of the initial information for a simulation. Is filled after a file is read.
 *
 * @author Robert Cranston
 */
public record SimulationInfo(String type, String title, String author, String description,
                             int width, int height,
                             int[][] initialGrid, Map<String, Integer> possibleParameters) {

  public static final List<String> tags = List.of("type", "title", "author", "description",
      "width", "height");


  public SimulationInfo(Map<String, String> info, int[][] grid,
      Map<String, Integer> possibleParameters) {
    this(info.get(tags.get(0)), info.get(tags.get(1)),
        info.get(tags.get(2)), info.get(tags.get(3)),
        Integer.parseInt(info.get(tags.get(4))), Integer.parseInt(info.get(tags.get(5))), grid,
        possibleParameters);
  }

  public SimulationInfo(String type, String title, String author, String description, int width,
      int height, int[][] initialGrid, Map<String, Integer> possibleParameters) {
    this.type = type.toLowerCase().replaceAll(" ", "");
    this.title = title;
    this.author = author;
    this.description = description;
    this.width = width;
    this.height = height;
    this.initialGrid = initialGrid;
    this.possibleParameters = possibleParameters;

  }
}
