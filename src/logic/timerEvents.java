/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import display.BoardPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.Timer;
//import static logic.Game.blocks;
//import static logic.Game.blocksToDelete;
//import static logic.Game.setRunning;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public abstract class timerEvents {
	public static Timer gameTimer;
	public static boolean isPaused;
	
	/**
	 * Used to pause or resume the game. set runstate to false to pause and true to resume the game.
	 * Setting runstate to true when not paused or false when paused does nothing. 
	 * The mouseListener is deactivated when paused.
	 * 
	 * @param tof desired runstate
	 */
	public static void setRunning(boolean tof){
		if(tof){
			Game.gameTimer.start();
			isPaused = false;
		}else{
			Game.gameTimer.stop();
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
	
	class GameTick implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
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
				return aCube;
			}).forEach((aCube) -> {
				Game.blocks.remove(aCube);
			});
			
			Game.blocksToDelete = new LinkedList<>();
			
			Game.blocks.stream().forEach((aCube) -> {
				aCube.motionOnGameTick();
			});
			
			BoardPanel.instance.repaint();
		}
	}
}
