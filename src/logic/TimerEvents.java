/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;


import java.util.LinkedList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.MainController;
import utilities.CanvasPainter;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public abstract class TimerEvents {
	
	private static Timeline gameFrameTimeline;
	
	private static Timeline clockTimeline;
	private static int seconds = 0;
	private static int minutes = 0;
	
	//public static Timer gameTimer;
	public static boolean isPaused;
	
	
	private static void initializeGameTimeline(){
		gameFrameTimeline = new Timeline(
			new KeyFrame(
			  Duration.ZERO,
			  actionEvent -> gameTick()
			),
			new KeyFrame(
			  Duration.millis(15)
			)
		);
		gameFrameTimeline.setCycleCount(Timeline.INDEFINITE);
	}
	
	private static void initializeClockTimeline(){
		clockTimeline = new Timeline(
			new KeyFrame(
				Duration.ZERO,
				actionEvent -> {
					String secondsString = ""+seconds;
					String minutesString = ""+minutes;
			
					if(seconds<=9) { secondsString = "0"+secondsString; }
					if(minutes<=9) { minutesString = "0"+minutesString; }
					
					MainController.updateTimeLabel(minutesString, secondsString);
					seconds++;

					if(seconds==60){
						minutes++;
						seconds = 0;
					}
				}
			),
			new KeyFrame(
			  Duration.seconds(1)
			)
		);
		clockTimeline.setCycleCount(Timeline.INDEFINITE);
	}
	
	/**
	 * Used to pause or resume the game. set runstate to false to pause and true to resume the game.
	 * Setting runstate to true when not paused or false when paused does nothing. 
	 * The mouseListener is deactivated when paused.
	 * 
	 * @param tof desired runstate
	 */
	public static void setRunning(boolean tof){
		if(tof){
			if(gameFrameTimeline==null){
				initializeGameTimeline();
			}
			if(clockTimeline==null){
				initializeClockTimeline();
			}
			clockTimeline.play();
			gameFrameTimeline.play();
			isPaused = false;
		}else{
			if(clockTimeline != null){ clockTimeline.pause(); }
			if (gameFrameTimeline != null) { gameFrameTimeline.pause(); }
			isPaused = true;
		}
	}
	
	/**
	 * Check if the game is currently running
	 * @return runstate
	 */
	public static boolean isRunning(){
		return !isPaused;
	}
	
	/**
	 * Resets the game to new random initial conditions
	 */
	public static void reset(){
		setRunning(false);
		Game.blocks = new LinkedList<>();
		Game.blocksToDelete = new LinkedList<>();
		Game.initializeCells();
	}
	

	private static void gameTick() {
		//System.out.println("[Game]\ttimer clicked");
		Game.createFallingBlocks();

		if(Game.cells[Game.selectedCol][Game.selectedRow]==null){
			Game.isStationaryBlock = false;
		}

		Game.blocks.stream().forEach((aCube) -> {
			if(!aCube.hasSameTypeNeighbors() && !Game.isStationaryBlock){
				Game.isStationaryBlock = true;
				Game.selectedCol = aCube.myColumn;
				Game.selectedRow = aCube.myRow;
			}
		});

		Game.blocksToDelete.stream().map((aCube) -> {
			Game.cells[aCube.myColumn][aCube.myRow] = null;
			Statistics.blocksClearedCount++;
			return aCube;
		}).forEach((aCube) -> {
			Game.blocks.remove(aCube);
		});
		MainController.updateDestroyedLabel(Statistics.blocksClearedCount);
		
		Game.blocksToDelete = new LinkedList<>();

		Game.blocks.stream().forEach((aCube) -> {
			aCube.motionOnGameTick();
		});

		CanvasPainter.repaint();
	}
}
