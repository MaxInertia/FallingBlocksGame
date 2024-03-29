/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.logic;


import static game.logic.BasicBlock.yGround;
import static game.logic.Game.cancellationIndexes;
import static game.logic.Game.columnCount;
import game.offline.GameOfflineController;
import game.online.GameOnlineController;
import game.utilities.CanvasPainter;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.util.Pair;
import networking.ClientNet;
import networking.GenUpdate;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public abstract class TimerEvents {
	
	private static Timeline gameFrameTimeline;
	
	private static Timeline clockTimeline;
	
	private static int tick = 0;
	
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
					
					switch(Game.onlineORoffline){
						case "ONLINE":
							GenUpdate gu = new GenUpdate(Game.blocks);
							ClientNet.sendObject(gu);
						
							if(tick%10==0){ GameOnlineController.updateTimeLabel(minutesString, secondsString); }
							break;
						case "OFFLINE":
							GameOfflineController.updateTimeLabel(minutesString, secondsString);
							break;
					}
					
					if(tick%10==0){
						seconds++;
						
						if(seconds==60){
						minutes++;
						seconds = 0;
						}
					}
					
					tick++;
					
				}
			),
			new KeyFrame(
			  Duration.millis(100)
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
		//Game.blocks = new LinkedList<>();
		//Game.blocksToDelete = new LinkedList<>();
		Game.initializeBlockSpaces();
	}
	
	private static void createBottomRow(){
		for(int currentColumn = 0; currentColumn<columnCount; currentColumn++){
			new Block(currentColumn, Game.rowCount);
		}
	}
	

	private static void gameTick() {
		
		//System.out.println("[Game]\ttimer clicked");
		Game.createFallingBlocks();
		Game.currentPeriod++;
		
		if(Game.currentPeriod>=Game.PERIOD_OF_RAISE){
			yGround+=0.50;
			Game.currentPeriod = 0;
		}

		// Goes to r<Game.rowCount+1 because the lower layer must move up too
		int players;
		if(Game.onlineORoffline.equals("ONLINE")){ players = 1; }
		else{ players = 2; }
		
		// For each player...
		//for(int p=0; p<players; p++){
		
		// Calculate new positions
		// For each column..
		for(int c=0; c<Game.columnCount; c++){
			// For each row.
			for(int r=0; r<Game.rowCount+1; r++){
				Block b = Game.blocks[0][c][r];
				if(b!=null){
					b.motionOnGameTick();
				}
			}
		}
		
		// Calculate Cancellations
		// For each column
		for(int c=0; c<Game.columnCount; c++){
			// For each row.
			for(int r=0; r<Game.rowCount+1; r++){
				Block b = Game.blocks[0][c][r];
				if(b!=null && b.myRow != Game.rowCount){
					b.performCancellations();
				}
			}
		}
		
		int deletedBlocks = 0;
		for(Pair<Integer,Integer> p: cancellationIndexes){
			if(Game.blocks[0][p.getKey()][p.getValue()] != null) { deletedBlocks++; }
			Game.blocks[0][p.getKey()][p.getValue()] = null;
		}
		Statistics.incrementClearedCount(deletedBlocks);
		Game.cancellationIndexes = null;
		Game.cancellationIndexes = new ArrayList<>();
		
		if(yGround >= Game.blockLength){
			yGround = 0;
			createBottomRow();
			Game.selectedRow--;
		}
		
		if(Game.onlineORoffline.equals("ONLINE")){
			GameOnlineController.updateDestroyedLabel(Statistics.blocksClearedCount);
		}else{
			GameOfflineController.updateDestroyedLabel(Statistics.blocksClearedCount);
		}
		CanvasPainter.repaint();
	}
}
