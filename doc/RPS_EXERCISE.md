# Rock Paper Scissors Lab Discussion
#### Robert Cranston (rbc6), Eric Xie (elx), Thivya Sivarjah (ts346)


### High Level Design Goals



### CRC Rock Paper Scissors Cards

This class's purpose or value is to represent a player's actions and score:

| Player                            |        |
|-----------------------------------|--------|
| boolean isWeaponChosen()          | Weapon |
| int getScore()                    | Game   |
| void resetScore(boolean gameOver) | Game   |
| void updateScore(boolean gameWon) | Game   |

| Relationships                         |        |
|---------------------------------------|--------|
| boolean checkIfBetter(Weapon, Weapon) | Weapon |
| void addRelationship()                | Game   |

| Game                              |        |
|-----------------------------------|--------|
| Player getWinner(List playerList) | Player |
| void createGame(game parameters)  | Player |
| void endGame(boolean gameOver)    | Player |
| void addWeapon(Weapon choice)     | Weapon |


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

* A new game is started with five players, their scores are reset to 0.
 ```java
 Game myGame = new Game(5);
 myGame.startGame;
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
 ```

* Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
 ```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
 ```

* A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
 ```
