package controller;

import java.util.Observer;
import java.util.Random;

/**
 * @author - David Weinflash
 * 
 * This class is the application of the game. It will contain references to 
 * the model and all views. This class' main responsibility is updating the screen with the
 * game board.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Map;
import views.ConsoleView;

public class WumpusMain extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private Map theGame;
  private Observer consoleView;
  
  @Override
  public void start(Stage stage) throws Exception {
    
	/**
	* Construct a stage for Iteration 2 of Hunt the Wumpus.
	* For Iteration 1, start a new game and add a Console View as observer.
	* Game will include a random number of pits, 3-5.
	* The console view will begin the REPL, which will not end until game over.
	*/
	  
	// Remove for Iteration 1
	// BorderPane pane = new BorderPane();

	// Random number of Slime pits (3, 4 or 5)
	Random rand = new Random();
	int randMinus = rand.nextInt(3);
	int numPits = 5 - randMinus;
	
	// Set up new game
    theGame = new Map(true, numPits);
    
    // Set up views
    consoleView = new ConsoleView(theGame);
    theGame.addObserver(consoleView);
    ((ConsoleView)consoleView).startREPL();
    
    // Remove for Iteration 1
    //Scene scene = new Scene(pane, 690, 630);
    //stage.setScene(scene);
    //stage.show();
  }
}