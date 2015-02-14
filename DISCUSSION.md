##RECITATION DISCUSSION 2/6/15：
# Author: Cosette Goldstein (cmg64), Catherine Zhang (ycz2)

##Refactoring

We decided to refactor the AgentO and AgentX, which are subclasses of characters. AgentO and AgentX are two types of characters used in Segregation; other than the difference in colour, their behavior and rules for updating is exactly the same. Hence the two classes are entirely identical except for code for setType() and setColor(). One way to refactor this would be to combine AgentX and AgentO into a general class Agent, and have it take in an extra parameter in its constructor String type. In the constructor, the object would setType(type), and from then on the act method would depend on its actual type that it was set to. An alternative would be to get rid of the character Agent class completely, add its Type as parameters in Location, and then iterate through the Locations in which these Chararacters reside in and have the update logic restored in the step method in SegregationCellWorld. However, this would involve a change in the core design of how the simulation is updated and would need refactoring of other simulation classes and characters. 