package model;

import java.util.Observable;
import java.util.Random;

/**
 * @author - David Weinflash
 * 
 * This class will keep track of the map and all objects on the map.
 * The map will be a 12x12 grid of Cave Room objects.
 * The map class will update WumpusMain as the game is played.
 *
 */
public class Map extends Observable {
	
	CaveRoom[][] board;
	
	public Map(boolean random, int numPits)
	{
		// if random false, create testable map
		// numPits either 3, 4 or 5
		
		

		// Add 3, 4 or 5 bottomless pits (no overlap with pit or Wumpus)
		// Add Hunter to random position on board (no overlap with pit, Wumpus or warning)
	
		
		// Construct a 12x12 grid of Cave Room objects
		board = new CaveRoom[12][12];
		
		CaveRoom room;
		for (int row = 0; row < 12; row++)
		{
			for (int col = 0; col < 12; col++)
			{
				room = new CaveRoom(row, col);
				board[row][col] = room;
			}
		}
		
		// Add Wumpus to random position on board
		Random rand = new Random();
		int randColumn = rand.nextInt(12);
		int randRow = rand.nextInt(12);
		
		// Add Wumpus to middle of map if testing game
		if (random == true)
			room = board[randRow][randColumn];
		else
			room = board[0][2];
		
		room.setWumpus(board);
		
	}
	
	@Override
	public String toString()
	{
		// Display a string version of the current board
		String map = "";
		CaveRoom room;
		char gamePiece;
		
		for (int row = 0; row < 12; row++)
		{
			for (int col = 0; col < 12; col++)
			{
				room = board[row][col];
				gamePiece = room.getGamePiece();
				map += gamePiece;

				if (col == 11)
					map += "\n";
				else
					map += " ";
			}
		}
		
		return map;
	}
	
	
  
}