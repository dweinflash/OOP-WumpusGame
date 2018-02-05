package views;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import model.Map;

public class ConsoleView implements Observer {

	private Map theGame;
	
	public ConsoleView(Map board)
	{
		theGame = board;
		System.out.println(theGame);
		startREPL();
	}
	
	public void startREPL()
	{
		boolean gameOver = false;
		Scanner sc = new Scanner(System.in);
		String move = "";
		
		while (!gameOver)
		{
			System.out.print("Move (n, e, s, w, arrow)? ");
			move = sc.nextLine();
			
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
					System.out.println(theGame);
					break;
				case "e":
					break;
				case "s":
					break;
				case "w":
					break;
				case "arrow":
					break;
			}
			
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		System.out.println(theGame);
	}

}
