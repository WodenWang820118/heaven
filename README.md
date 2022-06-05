# Oh_Heaven

The game is similar to [Contract Bridge](https://en.wikipedia.org/wiki/Contract_bridge), but the rule changes to be played as an individual. i.e., Without opposite partnership. The codebase is from the University of Melbourne, SWEN30006, Software Design and Modeling and the project is to practice GRASP principles and GoF design patterns.

## Overview
The game has been encapsulated in one file to achieve all functional goals. However, the source code is highly coupled with software design, and extending features could be complex. On the other hand, the game takes parameters such as a legal player and a human player in a group of four. The parameters could be hard to configure whenever new types of players join the game; it is necessary to modify the code, leading to chores and potential bugs. Therefore, apart from addressing the mentioned issues, we enabled the possible future extensions for including intelligent playing and more competent bidding behaviors according to the new design.

## Additional NPC types with configurability
To add more types of NPC players, there are two perspectives to be considered. One is how to add players to the game. Another is how an NPC player records all relevant information and makes a reasonable but legal card choice.

### Parameters configuration
To allow the game system to be more configurable, a static `PropertiesLoader` class is utilized to load properties such as player’s types: human, random, legal, and smart. In addition, the other parameters settings, nbStartCards, rounds, and enforceRules could be loaded without modifying the source code.

On the other hand, the random seed was set in the source code. Now, a static `ServiceRandom` class deals with the random behavior with a defined seed that could decouple and reduce dependencies. There’s no more needs to pass random as an argument to random behavior required classes. Further, the class is flexible to add and give different random behaviors if desired.

## More NPC types
After the `PropertiesLoader` class has been completed, the game can parse player types as a string list. However, the source code uses three arrays for scores, tricks, and bidding; use an array for tracking the player’s hand deck. The code is fragile that more record types could massively increase lines of code and make the project hard to develop and maintain. Therefore, to be cohesive with low-coupling, scores, tricks, bidding attributes, and related operations are wrapped in the `Player` class.

The design has considered the polymorphism for multiple player types and has implemented human, legal, random, and smart player types. However, it could be hard to tell only four types of players involved without a new one. Thus, we decided to store the player type as a string and use it in the entire game. The implementation reduces the code’s complexity, and in the future, no more classes will be added if new player types come into the system.

The `Player` class embraces more attributes of each player, irrespective of player types. In the design, in addition to the wrapped scores, tricks, and bids attributes, each player has a Brain to store all relevant information in the game. The `Brain` class is cohesive and semantically reasonable as part of a player.

### Consistent player pipeline instantiation: builder pattern
Instantiating players in the game is simple in the current development stage, but introducing more boilerplates. For example, to instantiate a human player:
```
HumanPlayer hp = new HumanPlayer();
hp.setBrain(new Brain());
hp.setScores(0);
hp.setTricks(0);
hp.setBids(0);
hp.setDeck(new Hand(deck));
```

Irrespective of player types, four players bring 20 lines of code. Moreover, it is possible to neglect the initial settings in such a boilerplate, and there could be more attributes in the future. The design uses a `PlayerBuilder` interface to define how a player object should be created, and the `ConcreteBuilder` class implements the interface, building players according to the parsed string type. Finally, the Director class defines the building pipeline, ensuring all necessary settings are constructed correctly.

### CompositePlayers: composite pattern
The composite patterns address and reduce each player's repetitive operations, such as picking a card. Further, processes could be different based on player types. An intelligent player applies an algorithm, and a random player gives a random choice. The design uses the composite pattern to enable customization for the same process but with different details.

### AlgorithmFactory: singleton factory with the strategy pattern
The singleton patterns ensure algorithms are accessible without instantiation by other classes, and only one factory instance can be referenced. As a result, It achieves low coupling. Further, the class also applies a pure fabrication pattern to handle related algorithm development. Finally, the factory creates and stores different algorithms. Although it’s reasonable not to use singleton and instantiate the factory object with players, it adds redundant chores to instantiate players beforehand from the perspective of algorithm development.

## Other improvements
### Packages: cohesion
Related .java files are stored under the folder with their categorical names to achieve a higher cohesion. For example, the package `playerbuilder` includes the `PlayerBuilder` interface, `ConcreteBuilder` class, and `Director` class.

### GRASP: Indirection
The new design proposes a facade object for future extension or simplifying complex operations.
- The `PlayerBoard` class plays the position but hasn’t been used. Nevertheless, it could be redundant in the current stage.
- The `Service` class wraps the `Rule` class, `Suit` enum, and `Rank` enum. The rule could be added or complicated in the future that the `Service` class might simplify the gaming procedure.

### User interface: pure fabrication
The user interface and other related elements are separated into the `GameBoard` class. Unless necessary adjustments are required, it’s easier to develop and focus on the functional core code. The class is extensible that `GameBoard` could have more sub-sets of UI elements from the [JGameGrid](https://aplu.ch/home/apluhomex.jsp?site=45) library.
