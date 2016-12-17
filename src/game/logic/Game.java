package game.logic;

import game.online.GameOnlineController;
import java.util.ArrayList;
import javafx.util.Pair;
import networking.ClientNet;
import networking.GenUpdate;

//TODO: When the second client to login loses, they did not send "Lose" message

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public final class Game extends TimerEvents{

	public static String onlineORoffline;
	
	//public static LinkedList<Block> blocks;
	//public static Block[][] cells;
	public static Block[][][] blocks;
	protected static ArrayList<Pair<Integer,Integer>> cancellationIndexes;
	
	//protected static LinkedList<Block> blocksToDelete; // TODO: Replace redundant secondary list

	// Board properties
	public static double SPAWNS_PER_HUNDRED_TICKS = 3;
	public static int PERIOD_OF_RAISE = 10;
	public static int currentPeriod = 0;
	public static int columnCount;
	public static int rowCount;
	
	// Cube properties
	public static int blockLength;
	public static int DROP_SPEED = 5;
	
	// Selected block
	public static Block firstSwapBlock;
	public static Block secondSwapBlock;
	public static int selectedCol;
	public static int selectedRow;
	public static boolean blockSelected = false;
	
	public Game(String onlineORoffline, int columns, int rows, int blockLength){
		System.out.println("[Game] Constructor called.");
		//gameTimer = new Timer(15, new GameTick());
		Game.onlineORoffline = onlineORoffline;
		isPaused = true;
		
		columnCount = columns;
		rowCount = rows;
		Game.blockLength = blockLength;
		
		selectedRow = rowCount-1;
		selectedCol = columnCount/2;
		
		cancellationIndexes = new ArrayList<>();
		initializeBlockSpaces();
	}
	
	/**
	 * Creates the 2D cell array and sets all cells to null.
	 * cells: The set of locations blocks can reside (+/- yPosition)
	 */
	protected static void initializeBlockSpaces(){
		if(onlineORoffline.equals("ONLINE")) {
			blocks = new Block[2][columnCount][rowCount+1];
		} else {
			blocks = new Block[1][columnCount][rowCount+1];
		}
		
		for(int row = 0; row<rowCount+1; row++){
			for(int column = 0; column<columnCount; column++){
				blocks[0][column][row] = null;
			}
		}
	}
	
	/**
	 * Starts the game clock, initializes starting blocks, and activates mouse listener
	 */
	public static void startGame(){
		System.out.println("[Game]\tstartGame() called.");
		setRunning(true);
		if(onlineORoffline.equals("ONLINE")) GameOnlineController.setInfoTag("Game in Progress");
		
		for(int currentRow = rowCount-2; currentRow<rowCount+1; currentRow++){
			for(int currentColumn = 0; currentColumn<columnCount; currentColumn++){
				new Block(currentColumn, currentRow);
			}
		}
	}
	
	/**
	 * Pseudo-random block generation in each column top (non-visible) row.
	 * Whether a block spawns is determined by this method, the type of block
	 * is determined by the constructor of the Block class.
	 */
	public static void createFallingBlocks(){
		for(int column = 0; column < columnCount; column++){
			if(Math.random()*1000 <= SPAWNS_PER_HUNDRED_TICKS) {
				if(blocks[0][column][0]==null) {
					new Block(column,0);
					//ClientNet.sendObject(new GenUpdate("fallingBlock|"+column));
				}else if(blocks[0][column][0].isBlocked()){
					//CanvasPainter.displayLost(); // TODO: For single player display "Game Over"
					ClientNet.sendObject(new GenUpdate("Lost","true"));
					gameover(false);
				}
			}
		}
		//System.out.println("Falling block added to board on thread: "+Thread.currentThread().getName());
	}
	
	
	public static void gameover(boolean localIsWinner) {
		setRunning(false);
		if(localIsWinner){
			GameOnlineController.setInfoTag("You Win!");
		}else{
			GameOnlineController.setInfoTag("You Lose!");
		}
	}
	
}
