package game;

import game.logic.Game;
import game.online.GameOnlineController;
import game.online.netops.OnConnectedOperation;
import game.utilities.SoundLoader;
import io.Standard;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;
import networking.ClientNet;
import networking.GenUpdate;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class MainController implements Initializable, SoundLoader{
	private static MainController instance;
	
	private ClientNet<GenUpdate> clientNet;
	
	@FXML Button offlineButton, onlineButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		onlineButton.setDisable(true);
		
		clientNet = new ClientNet<>("172.16.1.80",31415);
		clientNet.setConnectionAction(Standard.IOMode.JFX, new OnConnectedOperation());
		clientNet.start();
	}
	
	public static void setOnlineStatus(boolean tof){
		instance.onlineButton.setDisable(!tof);
	}
	
	public void handleAction(ActionEvent event){
		if(event.getSource()==offlineButton){
			try{
				openOfflineMode();
			}catch(IOException ioException){
				if(Swipe.printStackTraces) ioException.printStackTrace();
			}
		}else if(event.getSource()==onlineButton){
			try{
				openOnlineMode();
			}catch(IOException ioException){
				if(Swipe.printStackTraces) ioException.printStackTrace();
			}
		}
	}
	
	private void openOfflineMode() throws IOException{
		clientNet.disconnect();
		
		Parent root = FXMLLoader.load(getClass().getResource("offline/GameOffline.fxml"));
		Scene scene = new Scene(root);
		
		//mediaPlayer = setupMusic("offline");
		//mediaPlayer.setOnEndOfMedia(() -> {
		//	mediaPlayer.seek(Duration.ZERO);
		//});
		//mediaPlayer.play();
		//MediaView mediaView = new MediaView();
        //((Pane)scene.getRoot()).getChildren().add(mediaView);
		
		
		Swipe.primaryStage.setOnCloseRequest((WindowEvent event) -> {
			Game.setRunning(false);
			Platform.exit();
		});
		Swipe.primaryStage.setScene(scene);
	}
	
	private void openOnlineMode() throws IOException{
		GameOnlineController.setNetwork(clientNet);
		
		Parent root = FXMLLoader.load(getClass().getResource("online/GameOnline.fxml"));
		Scene scene = new Scene(root);
		
		//mediaPlayer = setupMusic("online");
		//mediaPlayer.setOnEndOfMedia(() -> {
		//	mediaPlayer.seek(Duration.ZERO);
		//});
		//mediaPlayer.play();
		//MediaView mediaView = new MediaView();
        //((Pane)scene.getRoot()).getChildren().add(mediaView);
		
		
		Swipe.primaryStage.setOnCloseRequest((WindowEvent event) -> {
			clientNet.disconnect();
			Game.setRunning(false);
			Platform.exit();
		});
		Swipe.primaryStage.setScene(scene);
	}
	
}
