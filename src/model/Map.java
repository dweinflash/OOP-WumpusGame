package model;

import java.util.Observable;

/**
 * @author - David Weinflash
 * 
 * This class will keep track of the map and all objects on the map.
 * The map will be a 12x12 grid of Cave Room objects.
 * The map class will update WumpusMain as the game is played.
 *
 */
public class Map extends Observable {
	
	public Map(boolean random, int numPits)
	{
		// if random false, create testable map
		// numPits either 3, 4 or 5
		
		// Construct a 12x12 grid of Cave Room objects
		// Add Wumpus to random position on board
		// Add 3, 4 or 5 bottomless pits (no overlap with pit or Wumpus)
		// Add Hunter to random position on board (no overlap with pit, Wumpus or warning)
	
		
	}
	
	
  
}