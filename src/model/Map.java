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
	
	private CaveRoom[][] board;
	private String gameMessage;
	private int[] hunterRoomPos = new int[2];
	
	public Map(boolean random, int numPits)
	{
		// if random false, create testable map
		// numPits either 3, 4 or 5

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
		int randRow = rand.nextInt(12);
		int randColumn = rand.nextInt(12);
		
		// Add Wumpus to middle of map if testing game
		if (random == true)
			room = board[randRow][randColumn];
		else
			room = board[8][11];
		
		room.setWumpus(board);
		
		// Add 3, 4 or 5 bottomless bits if random
		// Follow spec example pits if not random
		
		int[] pitExampleRows = {1, 1, 10, 11};
		int[] pitExampleCols = {7, 9, 10, 2};
		
		// random pits
		if (random == true)
		{
			for (int i = 0; i < numPits; i++)
			{
				while(room.noOccupant() == false)
				{
					randRow = rand.nextInt(12);
					randColumn = rand.nextInt(12);
					room = board[randRow][randColumn];
				}
				
				room.setPit(board);
			}
		}
		// spec example Pits
		else
		{
			for (int i = 0; i < 4; i++)
			{
				room = board[pitExampleRows[i]][pitExampleCols[i]];
				room.setPit(board);
			}
		}
		
		// Add Hunter to random position on board
		// Follow spec example Hunter if not random
		
		//random Hunter
		if (random == true)
		{
			while(room.noOccupant() == false || room.noWarning() == false)
			{
				randRow = rand.nextInt(12);
				randColumn = rand.nextInt(12);
				room = board[randRow][randColumn];
			}
			
			room.setHunter(board);
			hunterRoomPos[0] = randRow;
			hunterRoomPos[1] = randColumn;
		}
		// spec example Hunter
		else
		{
			room = board[4][4];
			room.setHunter(board);
			hunterRoomPos[0] = 4;
			hunterRoomPos[1] = 4;
		}
		
	}
	
	public void moveNorth()
	{
		// Remove Hunter from current room
		// Add Hunter to room above
		
		CaveRoom room;
		
		int x = hunterRoomPos[1];
		int y = hunterRoomPos[0];

		room = board[y][x];
		room.removeHunter(board);
		board[y][x] = room;
		
		if (y == 0)
			y = 11;
		else
			y = y - 1;
		
		room = board[y][x];
		room.setHunter(board);
		
		hunterRoomPos[0] = y;
		hunterRoomPos[1] = x;
		
		setChanged();
		notifyObservers();
		
	}
	
	private void setGameMessage()
	{
		// Game Message includes warnings and game over message
		// Does not include Arrow messages
		
		CaveRoom hunterRoom;
		hunterRoom = board[hunterRoomPos[0]][hunterRoomPos[1]];
		gameMessage = hunterRoom.getWarningMessage(board);
	}
	
	@Override
	public String toString()
	{
		// Determine Game Message given Hunter position
		this.setGameMessage();
		
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
		
		map += gameMessage;
		
		return map;
	}
	
	
  
}