package game.online;

import game.logic.Game;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class GameState {

	private static GameState instance;
	
	public boolean[] ready;
	
	private GameState(){
		ready = new boolean[2];
		ready[0] = false;
		ready[1] = false;
	}
	
	public static boolean initGameState(){
		if(instance!=null){ return false; }
		
		instance = new GameState();
		return true;
	}

	/**
	 * Sets the ready-state of a player
	 * @param player 1 for local, 2 for opponent
	 * @param tof whether the player is ready
	 */
	public static void setReady(int player, boolean tof){
		instance.ready[player-1] = tof;
		
		if(instance.ready[0] && instance.ready[1]){
			Game.startGame();
		}
	}
	
}
