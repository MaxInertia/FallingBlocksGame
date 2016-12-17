package game.logic;

import game.offline.GameOfflineController;
import game.online.GameOnlineController;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Statistics {

	public static int blocksClearedCount;
	public static int blockSwapCount;
	public static int movementCount;
	
	public static void incrementClearedCount(int increase){
		if(increase<0) return;
		blocksClearedCount = blocksClearedCount+increase;
		if(Game.onlineORoffline.equals("ONLINE")){
			GameOnlineController.updateDestroyedLabel(blocksClearedCount);
		}else{
			GameOfflineController.updateDestroyedLabel(blocksClearedCount);
		}
	}
	
	public static void incrementMovementCount(int increase){
		if(increase<0) return;
		movementCount = movementCount+increase;
		if(Game.onlineORoffline.equals("ONLINE")){
			GameOnlineController.updateMovesLabel(movementCount);
		}else{
			GameOfflineController.updateMovesLabel(movementCount);
		}
	}
	
	public static void incrementSwapCount(int increase){
		if(increase<0) return;
		blockSwapCount = blockSwapCount+increase;
		if(Game.onlineORoffline.equals("ONLINE")){
			GameOnlineController.updateSwapsLabel(blockSwapCount);
		}else{
			GameOfflineController.updateSwapsLabel(blockSwapCount);
		}
	}
}
