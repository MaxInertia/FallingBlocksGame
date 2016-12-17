package networking;

import game.logic.BasicBlock;
import java.io.Serializable;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class GenUpdate implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//public String timeCreated;
	public String data;
	
	public BasicBlock[][][] board;
	
	public GenUpdate(String message){
		data = message;
		board = null;
	}

	/**
	 * Constructor for updating game status
	 * @param type String indicating the information type
	 * @param value String indicating new value for type
	 */
	public GenUpdate(String type, String value) {
		data = type+"|"+value;
		board = null;
	}
	
	/**
	 * Constructor for updating game board
	 * @param playerBlocks local board state
	 */
	public GenUpdate(BasicBlock[][][] playerBlocks) {
		data = "BoardUpdate|a";
		board = playerBlocks;
	}
	
	@Override
	public String toString(){
		return data;
	}
	
	// TODO: All information transferred to and from server are to be in this class
}
