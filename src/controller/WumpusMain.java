package controller;

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

public class WumpusMain extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    BorderPane pane = new BorderPane();

    Map game = new Map(true, 4);
    String gameBoard = game.toString();
    System.out.println(gameBoard);
    
    Scene scene = new Scene(pane, 690, 630);
    stage.setScene(scene);
    stage.show();
  }
}