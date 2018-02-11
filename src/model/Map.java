package model;

import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

/**
 * @author - David Weinflash
 * 
 * This class will keep track of the map and all objects on the map.
 * The map will be a 12x12 grid of Cave Room objects.
 * The map class will update all views (observers) as the game is played.
 *
 */
public class Map extends Observable {
	
	/**
	* Construct a 12x12 map made up of Cave Room objects.
	* If random is true, add Wumpus, Pits and Hunter to random Cave Rooms on map.
	* If random is false, construct a map that matches the map in the spec.
	* Testing will rely on random = false.
	*/
	
	private CaveRoom[][] board;
	private String gameMessage;
	private int[] hunterRoomPos;
	private int[] wumpusRoomPos;
	boolean gameOver;
	boolean arrowShot;
	
	public Map(boolean random, int numPits)
	{
		// if random false, create testable map
		// numPits either 3, 4 or 5

		// Construct a 12x12 grid of Cave Room objects
		board = new CaveRoom[12][12];
		hunterRoomPos = new int[2];
		wumpusRoomPos = new int[2];
		
		this.initializeBoard(random, numPits);
	}
		
	public void initializeBoard(boolean random, int numPits)
	{	
		gameOver = false;
		arrowShot = false;
		
		CaveRoom room;
		// initialize board with 124 Cave Room objects
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
		{
			room = board[randRow][randColumn];
			wumpusRoomPos[0] = randRow;
			wumpusRoomPos[1] = randColumn;
		}
		else
		{
			room = board[8][11];
			wumpusRoomPos[0] = 8;
			wumpusRoomPos[1] = 11;
		}
		
		room.setWumpus(board);
		
		// Follow spec pit positions if Map not random
		int[] pitExampleRows = {1, 1, 10, 11};
		int[] pitExampleCols = {7, 9, 10, 2};
		
		// random Pits
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
		
		// Add Hunter to random, safe position on board
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
	
	public void startNewGame()
	{
		// re-initialize the board to start a new game
		Random rand = new Random();
		int randMinus = rand.nextInt(3);
		int numPits = 5 - randMinus;
		
		this.initializeBoard(true, numPits);
	    setChanged();
	    notifyObservers("startNewGame()");
	}
	
	public void moveNorth()
	{
		/**
		* Update the Map so that Hunter is in room above current room.
		* Notify observers once map has changed.
		*/
		
		if (gameOver == true)
			return;
		
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
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void moveSouth()
	{
		/**
		* Update the Map so that Hunter is in room below current room.
		* Notify observers once map has changed.
		*/
		
		if (gameOver == true)
			return;
		
		// Remove Hunter from current room
		// Add Hunter to room below
		
		CaveRoom room;
		
		int x = hunterRoomPos[1];
		int y = hunterRoomPos[0];

		room = board[y][x];
		room.removeHunter(board);
		board[y][x] = room;
		
		if (y == 11)
			y = 0;
		else
			y = y + 1;
		
		room = board[y][x];
		room.setHunter(board);
		
		hunterRoomPos[0] = y;
		hunterRoomPos[1] = x;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void moveWest()
	{
		/**
		* Update the Map so that Hunter is in room left of current room.
		* Notify observers once map has changed.
		*/
		
		if (gameOver == true)
			return;
		
		// Remove Hunter from current room
		// Add Hunter to room on left
		
		CaveRoom room;
		
		int x = hunterRoomPos[1];
		int y = hunterRoomPos[0];

		room = board[y][x];
		room.removeHunter(board);
		//board[y][x] = room;
		
		if (x == 0)
			x = 11;
		else
			x = x - 1;
		
		room = board[y][x];
		room.setHunter(board);
		
		hunterRoomPos[0] = y;
		hunterRoomPos[1] = x;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void moveEast()
	{
		/**
		* Update the Map so that Hunter is in room right of current room.
		* Notify observers once map has changed.
		*/
		
		if (gameOver == true)
			return;
		
		// Remove Hunter from current room
		// Add Hunter to room on right
		
		CaveRoom room;
		
		int x = hunterRoomPos[1];
		int y = hunterRoomPos[0];

		room = board[y][x];
		room.removeHunter(board);
		board[y][x] = room;
		
		if (x == 11)
			x = 0;
		else
			x = x + 1;
		
		room = board[y][x];
		room.setHunter(board);
		
		hunterRoomPos[0] = y;
		hunterRoomPos[1] = x;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void shootArrow(String move)
	{
		/**
		* Get input from user to specify in which direction to shoot the arrow.
		* If Wumpus in direction of shot, user wins. Else, user loses.
		*/
		
		// Don't shoot arrow if game over
		if (gameOver == true)
			return;
		
		arrowShot = true;
		
		int x = hunterRoomPos[1];
		int y = hunterRoomPos[0];
		
		// arrow shot vertical
		if (move.equals("N") || move.equals("S"))
		{
			if (wumpusRoomPos[1] != x)
			{
				gameMessage = "You just shot yourself. You lose.\n";
			}
			else
			{
				gameMessage = "Your arrow hit the wumpus. You win.\n";
			}
		}
		// arrow shot horizontal
		else
		{
			if (wumpusRoomPos[0] != y)
			{
				gameMessage = "You just shot yourself. You lose.\n";
			}
			else
			{
				gameMessage = "Your arrow hit the wumpus. You win.\n";
			}
		}
		
		this.setChanged();
		this.notifyObservers();
		
	}
	
	private void setGameMessage()
	{
		/**
		* Get message from Hunter's current location room.
		* Game Message includes warnings, game over, and arrow messages.
		*/
		
		// Game Message already set when player shot arrow
		if (arrowShot == true)
			return;
		
		// Get Game Message from current Cave Room
		CaveRoom hunterRoom;
		hunterRoom = board[hunterRoomPos[0]][hunterRoomPos[1]];
		gameMessage = hunterRoom.getWarningMessage(board);
	}
	
	public boolean getGameOver()
	{
		return gameOver;
	}
	
	private void gameOver()
	{
		gameOver = true;
	}
	
	public CaveRoom[][] getBoard()
	{
		return board;
	}
	
	public String getGameMessage()
	{
		this.setGameMessage();
		return gameMessage;
	}
	
	public int[] getHunterPos()
	{
		return hunterRoomPos;
	}
	
	@Override
	public String toString()
	{
		/**
		* Get any Cave Room messages given the Hunter's current position on board.
		* Display the current board to stdout and any game messages below the board.
		* If game message is Pit/Wumpus death, set gameOver = true.
		*/
		
		String pitWarning = "You fell down a bottomless pit. You lose.\n";
		String wumpusWarning = "You walked into the Wumpus. You lose.\n";
		String arrowWin = "Your arrow hit the wumpus. You win.\n";
		String arrowLose = "You just shot yourself. You lose.\n";
		
		// Determine Game Message given Hunter position
		this.setGameMessage();
		
		// End game if Game Message is 'Fell into pit' or 'Walked into Wumpus'
		if (gameMessage.equals(pitWarning) || gameMessage.equals(wumpusWarning) || 
				gameMessage.equals(arrowLose) || gameMessage.equals(arrowWin))
		{
			this.gameOver();
		}
		
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
				
				// Print end game result
				if (gameOver == true)
				{
					if (gamePiece == 'X')
					{
						room.setVisible();
						gamePiece = room.getGamePiece();
						if (gamePiece == ' ')
							gamePiece = '_';
					}
				}

				map += gamePiece;

				if (col == 11 && row != 11)
					map += "\n";
				else
					map += ' ';
				
			}
		}
		
		return map;
	}
	
}