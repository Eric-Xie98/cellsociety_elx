Cell Society
====

This project implements a cellular automata simulator.

Names: Robert Cranston, Thivya Sivarajah, Eric Xie

### Timeline

Start Date: 1/20/22

Finish Date:  2/7/22

Hours Spent: 60

### Primary Roles

* <b>Robert:</b> Designed the front end to display multiple simulations with options for different
  languages and shapes. Handled updating and animating the view based on the model specified. Read
  the xml to create a specified or random simulation and writing to a new xml with the current
  state. Handled displaying error messages for possible errors with handling the xml and created
  test files to check errors
* <b>Thivya:</b> Handled back-end with simulation logic, tested and implemented initial config files
  for a portion of the simulations, and invested time into creating a hexagonal grid type
* <b>Eric:</b> Handled back-end with simulation logic, tested and implemented initial config files
  for a portion of the simulations, and dabbled in some front-end and CSS to implement a night mode
  for the grids

### Resources Used

Resources we took advantage of were:

* StackOverflow forums
* Websites provided by CS308 for simulation rules and description
* JavaFX official documentation
* Youtube Tutorials for JavaFX UI

### Running the Program

Main class:

The main class contains only the bare necessary items to start the simulation with one method
start(). In this method, the file reader takes in the initial simulation file name and creates a
simulation based off the record created after parsing through the XML in another class. From there,
the window is created and added to the stage for displaying.

Data files needed:

The main class starts with a data XML file already put in, so every new simulation created will have
that simulation already preloaded into it.

The data folder also contains the errorTest xml files used including the ArrayFormat, EmptyFile, and
more that were used for testing error catching.

Features implemented:

In the end, we implemented a large number of features, but we unfortunately also could not get to a
sizable chunk of features as well. Instead of trying to cram everything in, we decided to focus more
on what we had and refactor rather than waste time implementing a difficult simulation (like
ForagingAnt).

To the best of my ability to recall, we implemented:

* 7 working simulations (GameOfLife, CSPerc, WatorWorld, SpreadingFire, Schelling, RPS, and
  FallingSandWater)
* Working UI that holds the simulation's descriptive information (author, name, type, etc.)
* Ability to adjust speed and movement of the simulation with buttons
* Ability to load and save files as well as a drop-down menu for changing the shape of the grids
* Implemented the 3 different grid shapes (rectangle, triangle, and hexagonal - somewhat)
* Ability to add and remove simulations on the same screen
* Change the language on a welcome screen to Spanish or English
* Change the view of the UI to a night mode
* Prevent window from changing based on size of grid
* Take in and read new XML files based on the different simulations
* Multiple test files for each simulation for your perusal
* Error checking files in data
* Control buttons connected to each simulation, so you can pause one simulation while keeping the
  other one running

### Notes/Assumptions

<b>Assumptions or Simplifications:</b>

Throughout the program, we made a number of assumptions in order for the program to run smoothly.
One example is the expectation that users will provide the correct input values for simulations when
necessary. Even though we do have error catching for this, the XML file has many places where if the
user creates an initial configuration incorrectly (miss an angle bracket, misspell a simulation),
the program will fail.

Another assumption made was during the implementation of simulations like Spreading Fire, where the
websites' rules implementations were slightly different. For example, one mentioned how empty cells
could regrow back into trees while the other didn't. As a result, we assumed that the website we
chose had the "correct" implementation.

Assumptions involving cells include how each cell holds one main status and one next generation
status at a time for transitioning between steps. Another assumption made was that only one "cell"
could fit inside one of the grid cells at a time.

<b>Interesting data files</b>:

Interesting data files you should take a look at are csPercolation2 (satisfying) and the
fallingSandWater ones because they're cool in general. The gameOfLife configs are all pretty cool,
but you gotta look at the 5th one, which is a recreation of a glider gun! I found the
schellingSegregation and watorWorld files neat as well as they randomly come together or eat one
another.

Known Bugs:
When adding more than one simulation, night mode is only applied to the most recently touched
simulation text while the other ones are left unedited.

Noteworthy Features:

* Implementation of a welcome screen and language functionality in Spanish is pretty neat.
* Speed slider that allows users to change how fast they want the simulation to step through from
  1-10
* Triangle and hexagonal grid shapes are cool as well
* Check out the spreadingFireRandomGrid xml that creates random grids!

### Impressions

* Robert: Seemed simple enough from the first week but the changes in the second week really made me
  have to re-evaluate my overall design and make it easier to add onto in almost every aspect
* Thivya: The second week changes made up rethink how to structure our packages and reorganize our
  code. With the design changes though, it also became easier to create classes that could be
  extended and methods that didn’t need editing
* Eric: Overall, I enjoyed working on the back-end, even more so when the UI for the grid was
  implemented. We could now see our logic flowing in front of us in the simulations, and I loved
  creating XML tests for each simulation I made. I found the UI side of the project pretty scary, so
  props to Robert for really handling that well! The second week shift definitely was a challenge as
  we had to rethink how we could implement new shapes of grids and interactions between cells.