package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.Map;

/**
 * This TextAreaView provides a text area for the player to input moves.
 * The view includes a text area, text fields cardinal direction buttons to shoot an arrow.
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
	
	public TextAreaView(Map board)
	{
		theGame = board;
		
		// Game Message
		gameMsg = new Label("Safe for now");
	    Font myFont = new Font("Courier", 24);
		gameMsg.setFont(myFont);
		BorderPane.setMargin(gameMsg, new Insets(0, 30, 40, 30));
		
		// Button Grid
		rightGrid = new GridPane();
		rightGrid.setAlignment(Pos.CENTER);
		rightGrid.setHgap(10);
		rightGrid.setVgap(10);

	    BorderPane.setAlignment(rightGrid, Pos.CENTER_RIGHT);
	    BorderPane.setMargin(rightGrid, new Insets(0, 95, 0, 0));
	    
	    // text area
	    textArea = new TextArea();
	    textArea.setMaxHeight(480);
	    textArea.setMaxWidth(280);
	    textArea.setFont(myFont);
	    
	    BorderPane.setAlignment(textArea, Pos.TOP_LEFT);
	    BorderPane.setMargin(textArea, new Insets(30, 30, 0, 30));
	    
	    this.setCenter(textArea);
	    this.setBottom(gameMsg);
	    this.setRight(rightGrid);
	    textArea.setText(theGame.toString());
	    
	    initializePane();
		
	}
	
	  private void initializePane() {
		    
			/**
			* Add button handler to direction button to register arrow move.
			*/
			
			// Cardinal direction buttons
			Font myFont = new Font("Courier", 24);
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
			
			//ButtonListener handler = new ButtonListener();
			//button.setOnAction(handler);
		  }
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
