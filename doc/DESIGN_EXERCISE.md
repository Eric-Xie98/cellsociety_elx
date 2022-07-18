# Cell Society Design Lab Discussion
#### Robert Cranston (rbc6), Eric Xie (elx), Thivya Sivarjah (ts346)


### Simulations

* Commonalities
  * They all have a set of rules that each cell has to follow.
  * These rules are based on their surrounding neighbors
  * They all take an initial state from a xml file


* Variations
  * In the Forest fire model each cell has a random chance of populating instead of only being determined by its' neighbors.


### Discussion Questions

* How does a Cell know what rules to apply for its simulation?
  * There is going to be specific subclasses for each simulation that tell each cell its rules

* How does a Cell know about its neighbors?
  * There will be some sort of 2d array and each cell calls the surrounding neighbors to check their state

* How can a Cell update itself without affecting its neighbors update?
  * It can stash its state and a grid container can call each cell to move to the next state.

* What behaviors does the Grid itself have?
  * It needs to be able to update itself
  * Control the stepping and animation
    * pause
    * speed up 
    * step through

* How can a Grid update all the Cells it contains?
  * go through each cell and call its update

* What information about a simulation needs to be in the configuration file?
  * how the cell updates
  * initial config
  * rules

* How is configuration information used to set up a simulation?
  * sets the rules/parameters
  * sets initial state
  * gives simulation background

* How is the graphical view of the simulation updated after all the cells have been updated?
  * It should have a step function that moves it through as the grid updates



### Alternate Designs

#### Design Idea #1

* Data Structure #1 and File Format #1
  * HashMap containing values for each cell object.
* Data Structure #2 and File Format #2


#### Design Idea #2

* Data Structure #1 and File Format #1

* Data Structure #2 and File Format #2



### High Level Design Goals



### CRC Card Classes

This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](images/order_crc_card.png "Order Class")


This class's purpose or value is to represent a customer's order:

| Cell                                  |      |
|---------------------------------------|------|
| void createCell(boolean initialState) | Grid |
| boolean isAlive()                     | Grid |
| boolean isValidPayment (Customer)     |      |
| void deliverTo (OrderLine, Customer)  |      |

| Grid                |      |
|---------------------|------|
| update cells()      | Cell |
| speedUpAnimation()  | Cell |
| slowDownAnimation() |      |
| pause()             |      |
| about()             |      |


This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```


This class's purpose or value is to manage something:
```java
public class Something {
     // sums the numbers in the given data
     public int getTotal (Collection<Integer> data)
	 // creates an order from the given data
     public Order makeOrder (String structuredData)
 }
```


### Use Cases

* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```

* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```

* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in a data file
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```