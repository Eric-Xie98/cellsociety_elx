# Refactoring Lab Discussion
#### Eric Xie, Robert Cranston, Thivya Sivarajah
#### Cell Society Team 9


## Principles

Definitions  (for reference):

Liskov Substitution - The Liskov Substitution Principle states that any subclass object should be substitutable for the superclass object from which it is derived. This semantic relationship often called behavioral subtyping, is applied to develop more correct, extendable, and reusable software.

Open / Close - The Open-Closed Principle (OCP) states that software entities (classes, modules, methods, etc.) should be open for extension, but closed for modification. In practice, this means creating software entities whose behavior can be changed without the need to edit and recompile the code itself.

### Current Abstractions

#### Abstraction #1

In our code, we created a GridClass super class that handles the general grid functions of all simulation grids. For now, this super class holds two simple methods including updateGrid() and getCellState(), which are used in other classes.
This overarching GridClass closes the simluation algorithm in our project under one roof. Currently, we're thinking of adding more general methods
as we notice patterns with the new simulations we need to add and update preexisting ones like updateGrid(), which seems to be hold duplicated code in different simulations.

* Open/Close
  * The GridClass is open for extension that allows for change in behavior of all the gridClasses without having to go in and manually edit each simulation. Instead of having to create a brand-new simulation each time, we can extend it off of GridClass and make it inherit its methods. 

* Liskov Substitution
  * The subclasses are designed to be substitutable by the superclass as the superclass is made to be extremely general, holding only methods and variables that are consistent across the simulations that extend it.



#### Abstraction #2
The GridView class as a superclass which is extended by TriangleGridView and RectangleGridView. 
The GridView handles all of the process that are the same no matter the shape while allowing for new shapes
* Open/Close
  * To add a new type of shape one does not need to touch the overall gridView class, they would 
  create a new class that extends it and override the methods that add a specific shape.

* Liskov Substution
  * Every Location that gridView or one of its subclasses is called, the other subclasses or superclass can also be used. In other words they all have the same methods just implemented differently to produce the desired effect.

-> 

#### Abstraction #3
* Open/Close

* Liskov Substution


### New Abstractions

#### Abstraction #1

Implementing a Neighborhood class that would handle Cell interactions with one another

* Open/Close - This allows us to modify behavior without having to go into each simulation and change how it interacts in 
triangle or hexagonal circumstances, etc.

* Liskov Substution

#### Abstraction #2

* Open/Close

* Liskov Substution



## Issues in Current Code

### Method or Class

The SimulationView class

* Design issues 
  * Heavily meshed with other view classes, causing a refactoring error to pop up the in the static analysis error log. The exact error points to how the classes should not be coupled to too many other classes (single responsibility principle).

We're looking at solving this problem by going through and refactoring the view classes with a fine tooth comb, separating out identical dependencies and moving ones that have the same dependencies to one class rather than keep them spread out.

### Method or Class

The Cell class neighborStatus method is extremely convoluted for the simple purpoes it serves: outputting an array of the input cell's neighbors' states

* Design issues

Convoluted logic statements result in a poor flow of control where we're stacking if statements on top of each other, performing a boundaryCheck on each neighbor and getting its state.

In order to solve this, we've been talking with TA who has provided us with a far easier solution in the form of a HashMap. This helps reduce the amount of logic control and lines taken up by the method, thus making it much more readable.



## Refactoring Plan

* What are the code's biggest issues?

Currently, the code's biggest issues lie in convoluted methods, magic numbers, and exception handling in terms of the size of the error. In terms of significance however, we're looking at refactoring the code to focus on Open/Closed and Liskov rather than on functionality.

* Which issues are easy to fix and which are hard?

The easiest issues to fix would probably be the magic numbers, which require creating final ints and constants at the start of the class with descriptive names, indicating their purpose. Hard issues would be reworking and refactoring the superclasses, helping ease their responsibilities onto methods
and making classes more defined rather than mixed. In this case, our view classes seem to depend on each other heavily, so hopefully we can refactor them to have a single responsibility instead. 

* What are good ways to implement the changes "in place"?

Good ways to implement the changes "in place" include identifying whether the changed code would significantly affect any other areas and discussing in depth will your team the change you're about to make.



## Refactoring Work

* Issue chosen: Fix and Alternatives

One issue we chose to focus on and fix was the magic number problem in multiple classes and methods throughout the project. For example, in spreadingFire simulation, I 
wanted to convert the probability that a tree would catch on fire to a decimal number between 0 and 1. In order to do this, I divided by it by a constant of 100 in the program, which I didn't explain
where the 100 came from. 

This was an example of implementing a change "in place" as it was simple to create a private static final int instance variable that held 100 and used a descriptive name.

An alternative to doing this was potentially making the XML reader file handle this mathematical logic rather than wait for it to be taken in by the spreadingFire class and doing it there.


* Issue chosen: Fix and Alternatives
  * Removed duplication in Cell View class by assigning String values that I want to be tied 
  together to a constant String variable and then assigning that variable