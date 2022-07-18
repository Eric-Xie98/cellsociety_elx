# Cell Society Design Final
### Robert Cranston, Eric Xie, and Thivya Sivarajah

## Team Roles and Responsibilities

 * Robert Cranston
   * Designed the frontend to display multiple simulations with options for different languges and shapes
   * Handled updating and animating the view based on the model specified
   * Read the xml to create a specified or random simulation and writing to a new xml with the current state
   * Handled displaying error messages for possible errors with handling the xml and created test files to check erros

 * Eric Xie
   * Handled backend with simulation logic
   * Tested and implemented initial config files for a portion of the simulations
   * Dabbled in some frontend to implement a night mode
   * Assisted in frontend testing and debugging of features

 * Thivya Sivarajah
   * Handled backend with simulation logic
   * Tested and implemented initial config files for a portion of the simulations
   * Dabbled in frontend with creation of hexagon grid
   * Created initial design overview for project



## Design goals
* Create a Cell class that covers the basic rules of all of our simulations
* Create an abstract Grid Class to set up all of our simulations 
* Create a Neighborhood class that can be used for different tiling patterns and different simulations


#### What Features are Easy to Add
* Different types of cells are easy to add (add onto unit package)
* Different simulations (add onto model package)
* Different tiling layouts (add onto shapes and grid package)


## High-level Design
Design Overview of our Packages and Classes (High Level):
[Design Overview](images/designoverview.png)

#### Core Classes
* Cell class
* GridClass class
* SimulationInfo class

## Assumptions that Affect the Design
* States per cell
  * Currently, a given cell can only have one state at a time
* Neighbors per cell
  * Original design assumption was that there were only rectangle cells so the number of neighbors was set at either 8 or 4 depending on if you counter diagonal neighbors or not
  * We decided to create a neighborhood class to fix this
  * Although incomplete, it would've housed the methods in Cell.java that give us the state of our neighbors

#### Features Affected by Assumptions
* Reading the XML file
  * We assume that the user is correctly making and using the XML file for the simulation

## Significant differences from Original Plan
* Our GridClass became our abstract class
* Our Cell class handles the units for all of our simulations
  * Initially had a fish and a shark class for Wator World
* Our methods are private with the exception of get and set methods for states and other parameters that need to be accessed between classes

## New Features HowTo

#### Easy to Add Features
* Relatively easy to introduce a new way to layout the grid
  * While it is easy to implement new tiling, it is not easy at the moment to implement new ways of choosing neighbors based on different tiling arrangements
* New types of simulations
  * All one needs to do is add a new simulation to the model package
  * Even if the simulation calls for a unique type of cell, one can easily extend the cell class by adding to the unit package
* Languages
  * We currently have Spanish and English implemented as language options, other languages can also be easily added to our Welcome Window

#### Other Features not yet Done
* Hexagon Grid
  * Couldn't manage to set tiling to align vertically, but aligns horizontally 
  * In this sense, it mimics our rectangle grid
* Neighborhood class
  * Would have made it easier to find out different neighbor states based on different simulation demands and different tiling patters
* Grid Edge Types
  * We only allow for one grid edge type and that has limited our ability to add onto other types of simulations where the changes are more dynamic
* Did not get to convert error message to property files

