package logic;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public final class Game extends TimerEvents{

	//public static LinkedList<Block> blocks;
	//public static Block[][] cells;
	public static Block[][] blocks;
	
	//protected static LinkedList<Block> blocksToDelete; // TODO: Replace redundant secondary list

	// Board properties
	public static double SPAWNS_PER_HUNDRED_TICKS = 3;
	public static int PERIOD_OF_RAISE = 5;
	public static int currentPeriod = 0;
	public static int columnCount;
	public static int rowCount;
	
	// Cube properties
	public static int blockLength;
	public static int DROP_SPEED = 10;
	
	// Selected block
	public static Block firstSwapBlock;
	public static Block secondSwapBlock;
	public static int selectedCol;
	public static int selectedRow;
	public static boolean blockSelected = false;
	
	public Game(int columns, int rows, int blockLength){
		System.out.println("[Game] Constructor called.");
		//gameTimer = new Timer(15, new GameTick());
		isPaused = true;
		
		columnCount = columns;
		rowCount = rows;
		Game.blockLength = blockLength;
		
		selectedRow = rowCount-1;
		selectedCol = columnCount/2;
		
		//blocks = new LinkedList<>();
	    //blocksToDelete = new LinkedList<>();
		initializeBlockSpaces();
	}
	
	/**
	 * Creates the 2D cell array and sets all cells to null.
	 * cells: The set of locations blocks can reside (+/- yPosition)
	 */
	protected static void initializeBlockSpaces(){
		blocks = new Block[columnCount][rowCount+1];
		for(int row = 0; row<rowCount+1; row++){
			for(int column = 0; column<columnCount; column++){
				blocks[column][row] = null;
			}
		}
	}
	
	/**
	 * Starts the game clock, initializes starting blocks, and activates mouse listener
	 */
	public static void startGame(){
		System.out.println("[Game]\tstartGame() called.");
		setRunning(true);
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
				if(blocks[column][0]==null) {
					new Block(column,0);
				}else if(blocks[column][0].isBlocked()){
					setRunning(false);
				}
			}
		}
	}
}
