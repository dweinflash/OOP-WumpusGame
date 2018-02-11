package model;

/**
 * @author - David Weinflash
 * 
 * This class will keep track of the Cave Room objects on the Map.
 * The Cave Room will include occupants (O, W or P) and/or warnings (B, S, G).
 * This class is primarily responsible with initializing Cave Rooms (methods to set warnings)
 * and keeping track of the occupants of each room.
 */

public class CaveRoom {

	int row;
	int column;
	boolean visible;
	char occupant;			// either O, W or P
	char warning;			// either B, S or G
	char gamePiece;			// either _, occupant, or warning
	String warningMessage; 	// includes warnings and game over messages
	boolean occOverlap;		// the Hunter is on top of W or P
	
	CaveRoom(int r, int c)
	{
		// initialize gamePiece with piece ' '
		// occupant and warning set to ' '
		// intialize visible as false
		
		row = r;
		column = c;
		gamePiece = ' ';
		visible = false;
		occupant = ' ';
		warning = ' ';
		warningMessage = "";
		occOverlap = false;
	}
	
	private void setGamePiece()
	{
		/** Set the piece to represent the CaveRoom.
		 * If Hunter on top of W or S, represent room as 'O'.
		 * If no occupant, represent with warning.
		 * Else room is empty and game piece is blank.
		 */
		
		if (occOverlap == true)
			gamePiece = 'O';
		else if (occupant != ' ')
			gamePiece = occupant;
		else if (warning != ' ')
			gamePiece = warning;
		else
			gamePiece = ' ';
	}
	
	public void setWumpus(CaveRoom[][] board)
	{
		/** Place the Wumpus on the board and set all Blood rooms adjacent.
		 * Wumpus room is cur object, board passed in is Game Map.
		 */
		
		int x = column;
		int y = row;
		CaveRoom room;
		
		// set room with Wumpus
		occupant = 'W';
		this.setGamePiece();
		
		// set adjacent rooms with Wumpus warnings
		// left warnings
		for (int i = 0; i < 2; i++)
		{
			if (x == 0)
				x = 11;
			else
				x--;
			
			room = board[y][x];
			room.setWarning('B');
		}
		
		// right warnings
		x = column;
		y = row;
	
		for (int i = 0; i < 2; i++)
		{
			if (x == 11)
				x = 0;
			else
				x++;
			
			room = board[y][x];
			room.setWarning('B');
		}
		
		// upper layer warnings
		x = column;
		y = row;
		
		if (y == 0)
			y = 11;
		else
			y--;
		
		if (x == 0)
			x = 10;
		else
			x = x - 2;
		
		for (int i = 0; i < 3; i++)
		{
			if (x == 11)
				x = 0;
			else
				x++;
			
			room = board[y][x];
			room.setWarning('B');
		}
		
		// lower layer warnings
		x = column;
		y = row;
		
		if (y == 11)
			y = 0;
		else
			y++;
		
		if (x == 0)
			x = 10;
		else
			x = x - 2;
				
			
		for (int i = 0; i < 3; i++)
		{
			if (x == 11)
				x = 0;
			else
				x++;
			
			room = board[y][x];
			room.setWarning('B');
		}
		
		// top warning
		x = column;
		y = row;
		
		if (y == 0)
			y = 10;
		else if (y == 1)
			y = 11;
		else
			y = y - 2;
		
		room = board[y][x];
		room.setWarning('B');
		
		// bottom warning
		y = row;
		
		if (y == 11)
			y = 1;
		else if (y == 10)
			y = 0;
		else
			y = y + 2;
		
		room = board[y][x];
		room.setWarning('B');
	}
	
	public void setPit(CaveRoom[][] board)
	{
		/** Place the Pit on the board and set all Slime rooms adjacent.
		 * Pit room is cur object, board passed in is Game Map.
		 */
		
		int x = column;
		int y = row;
		CaveRoom room;
		
		// set room with Pit
		occupant = 'P';
		this.setGamePiece();
		
		// set top warning
		if (y == 0)
			y = 11;
		else
			y--;
		
		room = board[y][x];
		room.setWarning('S');
		
		// set bottom warning
		y = row;
		
		if (y == 11)
			y = 0;
		else
			y++;
		
		room = board[y][x];
		room.setWarning('S');
		
		// set left warning
		y = row;
		
		if (x == 0)
			x = 11;
		else
			x--;
		
		room = board[y][x];
		room.setWarning('S');
		
		// set right warning
		x = column;
		
		if (x == 11)
			x = 0;
		else
			x++;
		
		room = board[y][x];
		room.setWarning('S');
	}
	
	public String getWarningMessage(CaveRoom[][] board)
	{
		/** Determine the message associated with the current Cave Room.
		 * Message will be only one line, unless Cave Room has Warning 'G'.
		 * 'G' rooms will include both Slime and Blood warnings.
		 */
		
		warningMessage = "";
		String slimeWarning = "I can hear the wind.\n";
		String bloodWarning = "I smell something foul.\n";
		String pitWarning = "You fell down a bottomless pit. You lose.\n";
		String wumpusWarning = "You walked into the Wumpus. You lose.\n";
		
		// Walk into Wumpus - return loss
		if (occupant == 'W')
		{
			warningMessage = wumpusWarning;
			return warningMessage;
		}
		
		// Walk into Pit - return loss
		if (occupant == 'P')
		{
			warningMessage = pitWarning;
			return warningMessage;
		}
		
		// Standing on Slime, Blood, or Goop
		if (warning != ' ')
		{
			// On top of Slime or Goop
			if (warning == 'S' || warning == 'G')
				warningMessage = slimeWarning;
	
			// On top of Blood or Goop
			if (warning == 'B' || warning == 'G')
				warningMessage += bloodWarning;
		
			return warningMessage;
		}

		return warningMessage;
		
	}
	
	public void setHunter(CaveRoom[][] board)
	{
		/** Set the Hunter to the current room.
		 * If current room already contains an occupant, set overlap to true.
		 * Make room visible once it includes the Hunter.
		 */
		
		if (occupant == ' ')
			occupant = 'O';
		else
			occOverlap = true;
		
		visible = true;
		this.setGamePiece();
	}
	
	public void removeHunter(CaveRoom[][] board)
	{
		// Remove Hunter from the current room
		
		occupant = ' ';
		visible = true;
		this.setGamePiece();
	}
	
	public void setWarning(char sign)
	{
		// set to Goop if warning in current room is Blood and sign is Slime
		if (warning == 'B' && sign == 'S')
			warning = 'G';
		else
			warning = sign;
		
		this.setGamePiece();
	}
	
	public char getGamePiece()
	{
		// return the game piece representing the current room on board
		
		// ***** VISIBLE TRUE FOR TESTING ONLY *****
		//visible = true;
		
		if (visible == true)
			return gamePiece;
		else
			return 'X';
	}
	
	public char getWarning()
	{
		return warning;
	}
	
	public void setVisible()
	{
		visible = true;
	}
	
	public boolean noWarning()
	{
		// return true if no warning in room
		if (warning == ' ')
			return true;
		else
			return false;
	}
	
	public boolean noOccupant()
	{
		// return true if no occupant in room
		if (occupant == ' ')
			return true;
		else
			return false;
	}
}
