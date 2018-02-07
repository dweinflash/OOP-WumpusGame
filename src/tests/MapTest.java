package tests;

/**
 * @author: David Weinflash
 * 
 * This class will test whether or not the map has been constructed correctly.
 * It will also provide tests to check wrap-around, hunter location at start, wumpus
 * warning signs, etc. 
 * 
 */

import static org.junit.Assert.*;

import org.junit.Test;

import model.CaveRoom;
import model.Map;

public class MapTest {

	// Set up predictable map that matches spec
	private Map theGame = new Map(false, 4);
	private CaveRoom[][] theBoard = theGame.getBoard();
	
  @Test
  public void boardSize() {
    // Game is 12x12 grid
	int rows = theBoard.length;
	int columns = theBoard[0].length;
	
    assertEquals(rows,12);
    assertEquals(columns,12);
  }

  @Test
  public void invisibleRooms()
  {
	  // Initialized game has 143 unvisited cave rooms
	  int invisible = 0;
	  
	  for (int i = 0; i < 12; i++)
	  {
		  for (int j = 0; j < 12; j++)
		  {
			  if(theBoard[i][j].getGamePiece() == 'X')
				  invisible++;
		  }
	  }
	  
	  assertEquals(invisible, 143);
  }
  
  @Test
  public void totalOccupants()
  {
	  // Spec has 6 total occupants (4 pits, 1 Wumpus, 1 Hunter)
	  int occupants = 0;
	  
	  for (int i = 0; i < 12; i++)
	  {
		  for (int j = 0; j < 12; j++)
		  {
			  if(!theBoard[i][j].noOccupant())
				  occupants += 1;
		  }
	  }
	  
	  assertEquals(occupants, 6);
	  
  }
  
  @Test
  public void totalWarnings()
  {
	  // Spec has 32 total warnings (Slime + Blood + Goop)
	  int warnings = 0;
	  
	  for (int i = 0; i < 12; i++)
	  {
		  for (int j = 0; j < 12; j++)
		  {
			  if(!theBoard[i][j].noWarning())
				  warnings += 1;
		  }
	  }
	  
	  assertEquals(warnings, 25);
	  
  }
  
  @Test
  public void hunterPos()
  {
	  // Hunter place on board in spec position
	  
	  CaveRoom room = theBoard[4][4];
	  char gamePiece = room.getGamePiece();
	  
	  assertEquals(gamePiece, 'O');
  }
  
  @Test
  public void slimeWarning()
  {
	  // Room with slime includes slime warning
	  CaveRoom slimeRoom = theBoard[0][9];
	  String slimeWarning = "I can hear the wind.\n";
	  
	  String getWarning = slimeRoom.getWarningMessage(theBoard);
	  
	  assertEquals(slimeWarning, getWarning);  
  }
  
  @Test
  public void bloodWarning()
  {
	  // Room with blood includes blood warning
	  CaveRoom bloodRoom = theBoard[8][0];
	  String bloodWarning = "I smell something foul.\n";
	  
	  String getWarning = bloodRoom.getWarningMessage(theBoard);
	  
	  assertEquals(bloodWarning, getWarning);
  }
  
  @Test
  public void pitFall()
  {
	  // Room with Pit includes game over message
	  CaveRoom pitRoom = theBoard[1][9];
	  String pitWarning = "You fell down a bottomless pit. You lose.\n";
	  
	  String getWarning = pitRoom.getWarningMessage(theBoard);
	  
	  assertEquals(pitWarning, getWarning);
  }
  
  @Test
  public void wumpusMurder()
  {
	  // Room with Wumpus includes game over message
	  CaveRoom wumpusRoom = theBoard[8][11];
	  String wumpusWarning = "You walked into the Wumpus. You lose.\n";
	  
	  String getWarning = wumpusRoom.getWarningMessage(theBoard);
	  
	  assertEquals(wumpusWarning, getWarning);
  }
  
  @Test
  public void goopWarning()
  {
	  // Room with slime includes slime warning
	  CaveRoom goopRoom = theBoard[9][10];
	  String slimeWarning = "I can hear the wind.\n";
	  String bloodWarning = "I smell something foul.\n";
	  String goopWarning = slimeWarning + bloodWarning;
	  
	  String getWarning = goopRoom.getWarningMessage(theBoard);
	  
	  assertEquals(goopWarning, getWarning);  
  }
  
  @Test
  public void hunterSafe()
  {
	  // Hunter does not spawn in a Blood/Slime/Goop room
	  CaveRoom hunterRoom = theBoard[4][4];
	  char warning = hunterRoom.getWarning();
	  
	  assertEquals(warning,' ');
  }
  
  @Test
  public void slimeWrapAround()
  {
	  // Slime correctly wraps from bottom to top of board
	  CaveRoom bottomSlime = theBoard[10][2];
	  CaveRoom topSlime = theBoard[0][2];
	  
	  char bottomWarning = bottomSlime.getWarning();
	  char topWarning = topSlime.getWarning();
	  
	  assertEquals(bottomWarning, 'S');
	  assertEquals(topWarning, 'S');
  }
  
  @Test
  public void bloodWrapAround()
  {
	  // Blood correctly wraps from right to left of board
	  CaveRoom rightSlime = theBoard[7][11];
	  CaveRoom leftSlime = theBoard[7][0];
	  
	  char rightWarning = rightSlime.getWarning();
	  char leftWarning = leftSlime.getWarning();
	  
	  assertEquals(rightWarning, 'B');
	  assertEquals(leftWarning, 'B');
  }
  
  @Test
  public void totalPits()
  {
	  // Between 3-5 pits on board
	  // 4 pits placed on spec board
	  
	  int numPits = 0;
	  String pitWarning = "You fell down a bottomless pit. You lose.\n";
	  String roomMsg;
	  
	  for (int i = 0; i < 12; i++)
	  {
		  for (int j = 0; j < 12; j++)
		  {
			  roomMsg = theBoard[i][j].getWarningMessage(theBoard);
			  if (roomMsg.equals(pitWarning))
				  numPits++;
		  }
	  }
	  
	  assertEquals(numPits,4);
  }
  
  @Test
  public void oneWumpus()
  {
	  // Only one Wumpus placed on board
	  
	  int numWumpus = 0;
	  String wumpusWarning = "You walked into the Wumpus. You lose.\n";
	  String roomMsg;
	  
	  for (int i = 0; i < 12; i++)
	  {
		  for (int j = 0; j < 12; j++)
		  {
			  roomMsg = theBoard[i][j].getWarningMessage(theBoard);
			  if (roomMsg.equals(wumpusWarning))
				  numWumpus++;
		  }
	  }
	  
	  assertEquals(numWumpus,1);
  }
  
}
