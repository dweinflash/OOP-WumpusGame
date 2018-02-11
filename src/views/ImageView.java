package views;

import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.CaveRoom;
import model.Map;

/**
 * @author - David Weinflash
 * 
 * This class will import all .png images and pass them to the map.
 * Images will be placed on the map according to the board's current layout in Map.java.
 * ImageView will adjust as Map.java adjusts with gameplay.
 */

public class ImageView extends BorderPane implements Observer {

	private Map theGame;
	private CaveRoom[][] gameBoard;
	private GridPane rightGrid;
	private GraphicsContext gc;
	private Group root;
	private Canvas canvas;
	
	private Font myFont;
	private Button west;
	private Button east;
	private Button north;
	private Button south;
	
	private Image blood;
	private Image goop;
	private Image ground;
	private Image slime;
	private Image slimePit;
	private Image hunter;
	private Image wumpus;
	
	public ImageView(Map board)
	{
		// initialize the game objects
		theGame = board;
		gameBoard = theGame.getBoard();
		
		// import Images
		blood = new Image("file:images/Blood.png", false);
		goop = new Image("file:images/Goop.png", false);
		ground = new Image("file:images/Ground.png", false);
		slime = new Image("file:images/Slime.png", false);
		slimePit = new Image("file:images/SlimePit.png", false);
		hunter = new Image("file:images/TheHunter.png", false);
		wumpus = new Image("file:images/Wumpus.png", false);
		
		root = new Group();
    	canvas = new Canvas(600,600);
    	gc = canvas.getGraphicsContext2D();
    	root.getChildren().add(canvas);
    	
		// Button Grid
		rightGrid = new GridPane();
		rightGrid.setAlignment(Pos.CENTER);
		rightGrid.setHgap(3);
		rightGrid.setVgap(3);
		
		// Cardinal direction buttons
		west = new Button();
		east = new Button();
		north = new Button();
		south = new Button();
			
		myFont = new Font("Courier New", 10);
		west.setFont(myFont);
		east.setFont(myFont);
		north.setFont(myFont);
		south.setFont(myFont);
				
		west.setText("W");
		east.setText("E");
		north.setText("N");
		south.setText("S");
					
		west.setMaxWidth(10);
		east.setMaxWidth(10);
		south.setMaxWidth(10);
		north.setMaxWidth(10);
					
		// Add buttons to rightGrid
		rightGrid.add(north, 1, 0);
		rightGrid.add(south, 1, 2);
		rightGrid.add(west, 0, 1);
		rightGrid.add(east, 2, 1);
		
	    BorderPane.setAlignment(rightGrid, Pos.CENTER_RIGHT);
	    BorderPane.setMargin(rightGrid, new Insets(0, 10, 0, 0));
    	
        BorderPane.setAlignment(canvas, Pos.CENTER);
        BorderPane.setMargin(canvas, new Insets(0, 0, 0, 0));
		this.setLeft(canvas);
	    this.setRight(rightGrid);
    	
    	this.initializeBoard();
	}
	
	public void initializeBoard()
	{
    	int[] hunterPos = new int[2]; 
		int x;
    	int y;
    	
    	// Draw ground on board
    	for (int i = 0; i < 12; i++)
    	{
    		y = 50*i;
    		for (int j = 0; j < 12; j++)
    		{
    			x = 50*j;
    			gc.drawImage(ground, x, y);
    		}
    	}
    	
    	// Draw Hunter on board
    	hunterPos = theGame.getHunterPos();
    	y = hunterPos[0]*50;
    	x = hunterPos[1]*50;
    	gc.fillRect(x, y, 50, 50);
    	gc.drawImage(hunter, x, y);

	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
