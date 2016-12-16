package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Game;
import utilities.SoundLoader;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Swipe extends Application implements SoundLoader{
	
	public static Stage primaryStage;
	public static MediaPlayer mediaPlayer;
	
	public static final int COLUMNS = 10;
	public static final int ROWS = 14;
	public static final int CUBE_SIDE_LENGTH = 40;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
		//Game clickQuickLogic = new Game(COLUMNS,ROWS,CUBE_SIDE_LENGTH);
		//Window clickQuickWindow = new Window(COLUMNS,ROWS,CUBE_SIDE_LENGTH);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Swipe.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

		Scene scene = new Scene(root);
		
		/*
		mediaPlayer = setupMusic();
		mediaPlayer.play();
		MediaView mediaView = new MediaView();
        ((Pane)scene.getRoot()).getChildren().add(mediaView);
		*/
		primaryStage.setOnCloseRequest((WindowEvent event) -> {
			Game.setRunning(false);
			Platform.exit();
		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println("[Swipe]\tShowing stage.");
	}
	
}
