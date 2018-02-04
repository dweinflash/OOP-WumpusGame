package model;

public class CaveRoom {

	int x_pos;
	int y_pos;
	boolean visible;
	char occupant;		// either O, W or P
	char warning;		// either B, S or G
	char gamePiece;		// either _, occupant, or warning
	
	CaveRoom(int x, int y)
	{
		// initialize gamePiece with piece '_'
		// occupant and warning set to ' '
		
		x_pos = x;
		y_pos = y;
		gamePiece = '_';
		visible = false;
		occupant = ' ';
		warning = ' ';
		
	}
	
	private void setGamePiece()
	{
		if (occupant != ' ')
			gamePiece = occupant;
		else if (warning != ' ')
			gamePiece = warning;
	}
	
	public void setWumpus(CaveRoom[][] board)
	{
		int x = x_pos;
		int y = y_pos;
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
		x = x_pos;
		y = y_pos;
	
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
		x = x_pos;
		y = y_pos;
		
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
		x = x_pos;
		y = y_pos;
		
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
		x = x_pos;
		y = y_pos;
		
		if (y == 0)
			y = 10;
		else if (y == 1)
			y = 11;
		else
			y = y - 2;
		
		room = board[y][x];
		room.setWarning('B');
		
		// bottom warning
		y = y_pos;
		
		if (y == 11)
			y = 1;
		else if (y == 10)
			y = 0;
		else
			y = y + 2;
		
		room = board[y][x];
		room.setWarning('B');
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
		return gamePiece;
	}
}
