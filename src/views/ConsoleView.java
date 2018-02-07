package views;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import model.Map;

public class ConsoleView implements Observer {

	private Map theGame;
	private boolean endREPL;
	
	public ConsoleView(Map board)
	{
		theGame = board;
		System.out.println(theGame);
		endREPL = false;
	}
	
	public void startREPL()
	{
		Scanner sc = new Scanner(System.in);
		String move = "";
		
		while (!endREPL)
		{
			System.out.print("Move (n, e, s, w, arrow)? ");
			move = sc.nextLine().toLowerCase();
			
			if (!(move.equals("n") || move.equals("e") || move.equals("s") || 
					move.equals("w") || move.equals("arrow")))
			{
				System.out.println("Invalid move.");
				continue;
			}
			
			switch (move)
			{
				case "n":
					theGame.moveNorth();
					break;
				case "e":
					theGame.moveEast();
					break;
				case "s":
					theGame.moveSouth();
					break;
				case "w":
					theGame.moveWest();
					break;
				case "arrow":
					theGame.shootArrow(true, sc);
					break;
			}
			
		}
		
		sc.close();
		System.exit(0);
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		System.out.println(theGame);
		
		if (theGame.getGameOver() == true)
		{
			endREPL = true;
		}
		
	}

}