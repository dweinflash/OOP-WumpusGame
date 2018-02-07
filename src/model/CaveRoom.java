package model;

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
		// initialize gamePiece with piece '_'
		// occupant and warning set to ' '
		
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
		if (occupant == ' ')
			occupant = 'O';
		else
			occOverlap = true;
		
		visible = true;
		this.setGamePiece();
	}
	
	public void removeHunter(CaveRoom[][] board)
	{
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
		// ****** VISIBLE TRUE FOR TESTING ************
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
