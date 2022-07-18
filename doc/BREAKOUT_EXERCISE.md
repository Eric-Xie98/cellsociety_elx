# Breakout Abstractions Lab Discussion
#### Robert Cranston, Eric Xie, and Thivya Sivarjah


## Princple Slogans

* Single Responsibility

* Open Closed



### Block

This superclass's purpose as an abstraction:
```java
 public class Block {
     public int something ()
     // handles all normal interactions with the bouncer (getting destroyed when hit for instance)
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class badBlock extends Brick {
     public int something ()
     // a bad block is a brick that when hit, removes the ball from the game
     // it uses a lot of the same methods as a normal Brick, such as the intersecting method
 }
```

#### Affect on Game/Level class (the Closed part)
You can't change the way you create a Brick, but you can change the way the brick reacts when it is hit by the ball. 


### Power-up

This superclass's purpose as an abstraction:
```java
 public class PowerUp {
     public int something ()
     // for every powerup, the way the powerup is dropped/interacts with the bouncer before its release is the same
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends PowerUp {
     public int something ()
     // this class is for the specific effects the powerup has on the game once released 
 }
```

#### Affect on Game/Level class (the Closed part)
For the powerup class, you can't change a powerup is released (speed it falls, etc), but you can change the effect one specific powerup can have in the game. 


### Level

This superclass's purpose as an abstraction:
```java
 public class Level {
     public int something ()
     // sets up the basic parameters of a level (ie reading the text file) 
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends Level {
     public int something ()
     // implements the specific features of each level like having the bricks gradually fall (if a brick hits a paddle, you lose a life)
 }
```

#### Affect on Game class (the Closed part)
For the Level Class, you can't change the size of the bricks or the way you read a text file, but you can add on different elements that increase the difficulty of each level.


### Others?
