package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.Map;

/**
 * This TextAreaView provides a text area for the player to input moves.
 * The view includes a text area, text fields and cardinal direction buttons 
 * to shoot an arrow.
 * 
 * @author David Weinflash
 */

public class TextAreaView extends BorderPane implements Observer {

	private Map theGame;
	private GridPane rightGrid;
	private TextArea textArea;
	private Label gameMsg;
	private Button west;
	private Button east;
	private Button north;
	private Button south;
	private Font myFont; //mono-space font
	
	public TextAreaView(Map board)
	{
		/**
		* Set up the scene with a Text Area for Game Board and four buttons
		* in grid for Arrow Shoot. Event handlers for buttons match ImageView
		* event handler.
		*/
		
		theGame = board;
		
		// Game Message
		gameMsg = new Label("Safe for now");
	    myFont = new Font("Courier New", 24);
		gameMsg.setFont(myFont);
		BorderPane.setMargin(gameMsg, new Insets(0, 40, 90, 30));
		
		// Button Grid
		rightGrid = new GridPane();
		rightGrid.setAlignment(Pos.CENTER);
		rightGrid.setHgap(10);
		rightGrid.setVgap(10);

	    BorderPane.setAlignment(rightGrid, Pos.CENTER_RIGHT);
	    BorderPane.setMargin(rightGrid, new Insets(0, 50, 0, 0));
	    
	    // text area
	    textArea = new TextArea();
	    textArea.setMaxHeight(350);
	    textArea.setMaxWidth(380);
	    textArea.setFont(myFont);
	    
	    BorderPane.setAlignment(textArea, Pos.TOP_LEFT);
	    BorderPane.setMargin(textArea, new Insets(30, 30, 0, 30));
	    
	    this.setCenter(textArea);
	    this.setBottom(gameMsg);
	    this.setRight(rightGrid);
	    textArea.setText(theGame.toString());
	    
	    initializePane();
		textArea.setMouseTransparent(false);
		textArea.setFocusTraversable(false);
	}
	
	private void initializePane() {
		    
			/**
			* Add button handlers to each direction button to register 
			* arrow moves. Add each button to grid like a compass.
			*/
			
			// Cardinal direction buttons
			west = new Button();
			east = new Button();
			north = new Button();
			south = new Button();
			
			west.setFont(myFont);
			east.setFont(myFont);
			north.setFont(myFont);
			south.setFont(myFont);
			
			west.setText("W");
			east.setText("E");
			north.setText("N");
			south.setText("S");
			
			west.setMinWidth(50);
			east.setMinWidth(50);
			south.setMinWidth(50);
			north.setMinWidth(50);
			
			// Add buttons to rightGrid
			rightGrid.add(north, 1, 0);
			rightGrid.add(south, 1, 2);
			rightGrid.add(west, 0, 1);
			rightGrid.add(east, 2, 1);
			
			ButtonListener handler = new ButtonListener();
			west.setOnAction(handler);
			east.setOnAction(handler);
			south.setOnAction(handler);
			north.setOnAction(handler);
		  }
	

	private class ButtonListener implements EventHandler<ActionEvent> {

			/**
			* Shoot arrow in direction represented by button.
			* If a player wins, indicate winner in Game Message.
			*/
		  
		@Override
		public void handle(ActionEvent e) 
		{
			// Get direction from source button
			// Fire arrow in appropriate direction
			
			String dir = ((Button) e.getSource()).getText();
			
			switch(dir)
			{
				case "N":
					theGame.shootArrow(dir);
					break;
				case "S":
					theGame.shootArrow(dir);
					break;
				case "E":
					theGame.shootArrow(dir);
					break;
				case "W":
					theGame.shootArrow(dir);
					break;
			}
		}
		  
	}
	  
	@Override
	public void update(Observable arg0, Object arg1) {
		
		/**
		 * After move made on board, Map will update TextAreaView.
		 * With update, display new board and determine if game over.
		 */
		
		// Warning and Game Over messages
		String boardMessage = theGame.getGameMessage();
		
		if (boardMessage.equals(""))
			boardMessage = "Safe for now";
		
		gameMsg.setText(boardMessage);
		
		// Print board to TextArea
		textArea.setText(theGame.toString());
	}

}
