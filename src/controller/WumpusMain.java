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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Map;
import views.TextAreaView;
import views.ConsoleView;
import views.ImageView;

public class WumpusMain extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private Map theGame;
  private MenuBar menuBar;
  private BorderPane window;
  
  private Observer currentView;
  private Observer textAreaView;
  private Observer imageView;
  private Observer consoleView;
  
  @Override
  public void start(Stage stage) throws Exception {
    
	/**
	* Construct a stage for Iteration 2 of Hunt the Wumpus.
	* For Iteration 1, start a new game and add a Text Area View as observer.
	* Game will include a random number of pits, 3-5.
	* The console view will begin the REPL, which will not end until game over.
	*/
	
	// Random number of Slime pits (3, 4 or 5)
	Random rand = new Random();
	int randMinus = rand.nextInt(3);
	int numPits = 5 - randMinus;
	
	// Set up window
	window = new BorderPane();
	Scene scene = new Scene(window, 690, 630);
    setupMenus();
    window.setTop(menuBar);
    
    // Start new game
    theGame = new Map(true, numPits);
    
    // Set up Iteration 2 views
    textAreaView = new TextAreaView(theGame);
    theGame.addObserver(textAreaView);
    imageView = new ImageView(theGame);
    theGame.addObserver(imageView);
    
    // Console View - Iteration 1
    /**
    * consoleView = new ConsoleView(theGame);
    * theGame.addObserver(consoleView);
    * ((ConsoleView)consoleView).startREPL();
    */
    
    setViewTo(textAreaView);
    scene.setOnKeyPressed(new KeyListener());
    stage.setScene(scene);
    stage.show();
  }
  
  private void setupMenus() {
	    // Views menu options
	    Menu views = new Menu("Views");
	    MenuItem textView = new MenuItem("Text View");
	    MenuItem imageView = new MenuItem("Image View");
	    views.getItems().addAll(textView, imageView);

	    MenuItem newGame = new MenuItem("New Game");
	    Menu options = new Menu("Options");
	    options.getItems().addAll(newGame, views);

	    menuBar = new MenuBar();
	    menuBar.getMenus().addAll(options);
	 
	    // Add the same listener to all menu items requiring action
	    MenuItemListener menuListener = new MenuItemListener();
	    newGame.setOnAction(menuListener);
	    textView.setOnAction(menuListener);
	    imageView.setOnAction(menuListener);
	  }
  
  private void setViewTo(Observer newView) {
	    window.setCenter(null);
	    currentView = newView;
	    window.setCenter((Node) currentView);
	  }
  
  private class MenuItemListener implements EventHandler<ActionEvent> {

	    @Override
	    public void handle(ActionEvent e) {
	      // Find out the text of the JMenuItem that was just clicked
	      String text = ((MenuItem) e.getSource()).getText();
	      if (text.equals("Text View"))
	        setViewTo(textAreaView);
	      else if (text.equals("Image View"))
	        setViewTo(imageView);
	      else if (text.equals("New Game"))
	        theGame.startNewGame();
  
	    }
  	}
  
  private class KeyListener implements EventHandler<KeyEvent>  {

	  	// Collect user input from arrow keys to move character in theGame
	  
	    @Override
	    public void handle(KeyEvent event) {
	      if(KeyCode.UP == event.getCode())
	        theGame.moveNorth();
	      if(KeyCode.RIGHT == event.getCode())
	        theGame.moveEast();
	      if(KeyCode.DOWN == event.getCode())
	        theGame.moveSouth();
	      if(KeyCode.LEFT== event.getCode())
	        theGame.moveWest();
	    }  
  }
  
}