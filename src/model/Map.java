package model;

import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

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
	private int[] wumpusRoomPos = new int[2];
	boolean gameOver;
	
	public Map(boolean random, int numPits)
	{
		// if random false, create testable map
		// numPits either 3, 4 or 5

		// Construct a 12x12 grid of Cave Room objects
		board = new CaveRoom[12][12];
		gameOver = false;
		
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
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void moveSouth()
	{
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
		// Remove Hunter from current room
		// Add Hunter to room on left
		
		CaveRoom room;
		
		int x = hunterRoomPos[1];
		int y = hunterRoomPos[0];

		room = board[y][x];
		room.removeHunter(board);
		board[y][x] = room;
		
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
	
	public void shootArrow(boolean consoleView, Scanner sc)
	{
		boolean validInput = false;
		String move = "";
		
		int x = hunterRoomPos[1];
		int y = hunterRoomPos[0];
		
		if (consoleView == true)
		{		
			while (!validInput)
			{
				System.out.print("Shoot (n, e, s, w)? ");
			
				move = sc.nextLine().toLowerCase();
			
				if (!(move.equals("n") || move.equals("e") || move.equals("s") || 
					move.equals("w")))
				{
					System.out.println("Invalid move.");
					continue;
				}
				else
					validInput = true;
			}
		}
		
		if (move.equals("n") || move.equals("s"))
		{
			if (wumpusRoomPos[1] != x)
			{
				System.out.println("You just shot yourself. You lose.");
				sc.close();
				System.exit(0);
			}
			else
			{
				System.out.println("Your arrow hit the wumpus. You win.");
				sc.close();
				System.exit(0);
			}
		}
		else
		{
			if (wumpusRoomPos[0] != y)
			{
				System.out.println("You just shot yourself. You lose.");
				sc.close();
				System.exit(0);
			}
			else
			{
				System.out.println("Your arrow hit the wumpus. You win.");
				sc.close();
				System.exit(0);
			}
		}
		
	}
	
	private void setGameMessage()
	{
		// Game Message includes warnings and game over message
		// Does not include Arrow messages
		
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
	
	@Override
	public String toString()
	{
		String pitWarning = "You fell down a bottomless pit. You lose.\n";
		String wumpusWarning = "You walked into the Wumpus. You lose.\n";
		
		// Determine Game Message given Hunter position
		this.setGameMessage();
		
		// End game if Game Message is 'Fell into pit' or 'Walked into Wumpus'
		if (gameMessage.equals(pitWarning) || gameMessage.equals(wumpusWarning))
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