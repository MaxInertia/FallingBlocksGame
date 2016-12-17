package game.offline;

import game.Swipe;
import game.logic.Game;
import game.utilities.CanvasPainter;
import game.utilities.KeyPressListener;
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

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class GameOfflineController implements Initializable {
		
	private static GameOfflineController instance;
	
	Game gameLogic;
	CanvasPainter painter;
	
	@FXML private Canvas gameCanvas;
	
	@FXML private Button startButton;
	
	@FXML private Label timeLabel;
	
	@FXML private Label destroyedLabel, swapsLabel, movesLabel;
	
	@FXML
	private void handleButtonAction(ActionEvent event) {
		
		switch (((Labeled)event.getSource()).getText()) {
			case "Start":
				System.out.println("[MainController]\tStart button Pressed!");
				//resetButton.setEnabled(true);
				Game.startGame();
				startButton.setText("Pause");
				startButton.setDisable(true);
				break;
				/*
			case "Pause":
				System.out.println("[GameOfflineController]\tPause button pressed!");
				Game.setRunning(false);
				startButton.setText("Resume");
				break;*/
			default:
				System.out.println("[MainController]\tAn unknown button was pressed!");
				Game.setRunning(true);
				startButton.setText("Pause");
				break;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		instance = this;
		
		gameLogic = new Game("OFFLINE",Swipe.COLUMNS,Swipe.ROWS,Swipe.CUBE_SIDE_LENGTH);
		
		gameCanvas.setWidth(Swipe.CUBE_SIDE_LENGTH*Swipe.COLUMNS);
		gameCanvas.setHeight(Swipe.CUBE_SIDE_LENGTH*Swipe.ROWS);
		painter = new CanvasPainter(gameCanvas);
		
		startButton.setFocusTraversable(false);
		
		//Swipe.primaryStage.addEventHandler(EventType eventType, new KeyPressListener());
		Swipe.primaryStage.addEventHandler(KeyEvent.ANY, new KeyPressListener<KeyEvent>());
	}	
	
	public static void updateTimeLabel(String minutes, String seconds){
		instance.timeLabel.setText(minutes+" : "+seconds);
	}
	
	public static void updateDestroyedLabel(int count){
		instance.destroyedLabel.setText("Destroyed: "+count);
	}
	
	public static void updateMovesLabel(int count){
		instance.movesLabel.setText("Moves: "+count);
	}
	
	public static void updateSwapsLabel(int count){
		instance.swapsLabel.setText("Swaps: "+count);
		
	}
}
