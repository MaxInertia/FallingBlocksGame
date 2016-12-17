package game.online;

import game.Swipe;
import game.logic.Game;
import game.online.netops.PassUpdateOperation;
import game.utilities.CanvasPainter;
import game.utilities.KeyPressListener;
import io.Standard;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import networking.ClientNet;
import networking.GenUpdate;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class GameOnlineController implements Initializable {
	private static GameOnlineController instance;
	
	private static ClientNet<GenUpdate> clientNet;
	
	Game gameLogic;
	CanvasPainter painter;
	
	@FXML private Canvas myGameCanvas;
	@FXML private Canvas oppGameCanvas;
	
	@FXML private Button readyButton;
	
	@FXML private Label infoLabel;
	@FXML private Label timeLabel;
	
	//@FXML private Label myDestroyedLabel, mySwapsLabel, myMovesLabel;
	//@FXML private Label oppDestroyedLabel, oppSwapsLabel, oppMovesLabel;
	
	public GameOnlineController(){
		instance = this;
		clientNet.setPassAction(Standard.IOMode.JFX, new PassUpdateOperation());
		GameState.initGameState();
	}
	
		@Override
	public void initialize(URL url, ResourceBundle rb) {
		gameLogic = new Game("ONLINE",Swipe.COLUMNS,Swipe.ROWS,Swipe.CUBE_SIDE_LENGTH);
		
		myGameCanvas.setWidth(Swipe.CUBE_SIDE_LENGTH*Swipe.COLUMNS);
		myGameCanvas.setHeight(Swipe.CUBE_SIDE_LENGTH*Swipe.ROWS);
		
		oppGameCanvas.setWidth(Swipe.CUBE_SIDE_LENGTH*Swipe.COLUMNS);
		oppGameCanvas.setHeight(Swipe.CUBE_SIDE_LENGTH*Swipe.ROWS);
		
		painter = new CanvasPainter(myGameCanvas, oppGameCanvas);
		
		readyButton.setFocusTraversable(false);
		
		//Swipe.primaryStage.addEventHandler(EventType eventType, new KeyPressListener());
		Swipe.primaryStage.addEventHandler(KeyEvent.ANY, new KeyPressListener<KeyEvent>());
	}	
	
	@FXML
	private void handleButtonAction(ActionEvent event) {
		
		switch (((Labeled)event.getSource()).getText()) {
			case "Ready":
				System.out.println("[GameOnlineControlle]\tReady button Pressed!");
				ClientNet.sendObject(new GenUpdate("ready","true"));
				GameState.setReady(1, true);
				readyButton.setText("unReady");
				break;
				
			case "unReady":
				System.out.println("[GameOnlineController]\tunReady button pressed!");
				ClientNet.sendObject(new GenUpdate("ready","false"));
				GameState.setReady(1, false);
				readyButton.setText("Ready");
				break;
			default:
				System.out.println("[MainController]\tAn unknown button was pressed!");
				break;
		}
	}
	
	public static void startup(){
		instance.readyButton.setDisable(true);
		Game.startGame();
	}
	
	public static void updateTimeLabel(String minutes, String seconds){
		instance.timeLabel.setText(minutes+" : "+seconds);
	}
	
	public static void updateDestroyedLabel(int count){
		//instance.myDestroyedLabel.setText("Destroyed: "+count);
	}
	
	public static void updateMovesLabel(int count){
		//instance.myMovesLabel.setText("Moves: "+count);
	}
	
	public static void updateSwapsLabel(int count){
		//instance.mySwapsLabel.setText("Swaps: "+count);
		
	}
	
	public static void setNetwork(ClientNet<GenUpdate> cN){
		clientNet = cN;
	}
	
	public static void setInfoTag(String text){
		if(text.equals("You Win!")){
			instance.infoLabel.setTextFill(Color.GREEN);
		}else if(text.equals("You Lose!")){
			instance.infoLabel.setTextFill(Color.RED);
		}
		instance.infoLabel.setText(text);
	}
}
