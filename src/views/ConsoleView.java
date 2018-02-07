package views;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import model.Map;

/**
 * @author - David Weinflash
 * 
 * This class is primarily responsible with collecting user input and displaying the board
 * to stdout. Once valid input is collected, a move is made on the board, which then updates
 * the ConsoleView class and prompts the class to display the new board on the console.
 */

public class ConsoleView implements Observer {

	private Map theGame;
	private boolean endREPL;
	
	public ConsoleView(Map board)
	{
		// Display the current Map passed in to stdout.
		theGame = board;
		System.out.println(theGame);
		endREPL = false;
	}
	
	public void startREPL()
	{
		/**
		 * Collect valid user input until 1) user walks into pit/wumpus or
		 * 2) user fires arrow. Input is not case sensitive. REPL will end
		 * when theGame.gameOver == true.
		 */
		
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
		
		// Exit here when user falls into pit or walks into Wumpus
		sc.close();
		System.exit(0);
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		/**
		 * After move made on board, Map will update ConsoleView.
		 * With update, display new board and determine if game over.
		 */
		
		System.out.println(theGame);
		
		if (theGame.getGameOver() == true)
		{
			endREPL = true;
		}
		
	}

}
