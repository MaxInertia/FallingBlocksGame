package logic;

import java.util.LinkedList;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public final class Game extends timerEvents{

	public static LinkedList<Block> blocks; //TODO: Replace blocks with cells
	public static Block[][] cells;
	
	protected static LinkedList<Block> blocksToDelete; // TODO: Replace redundant secondary list

	// Board properties
	public static double SPAWNS_PER_HUNDRED_TICKS = 3;
	public static int columnCount;
	public static int rowCount;
	
	// Cube properties
	public static int blockLength;
	public static int DROP_SPEED = 3;
	
	// Selected block
	public static Block firstSwapBlock;
	public static Block secondSwapBlock;
	public static int selectedCol = 0;
	public static int selectedRow = 0;
	public static boolean blockSelected = false;
	public static boolean isStationaryBlock = false;
	
	public Game(int columns, int rows, int blockLength){
		System.out.println("[Game] Constructor called.");
		//gameTimer = new Timer(15, new GameTick());
		isPaused = true;
		
		columnCount = columns;
		rowCount = rows;
		Game.blockLength = blockLength;
		
		blocks = new LinkedList<>();
		blocksToDelete = new LinkedList<>();
		initializeCells();
	}
	
	/**
	 * Creates the 2D cell array and sets all cells to null.
	 * cells: The set of locations blocks can reside (+/- yPosition)
	 */
	protected static void initializeCells(){
		cells = new Block[columnCount][rowCount];
		for(int row = 0; row<rowCount; row++){
			for(int column = 0; column<columnCount; column++){
				cells[column][row] = null;
			}
		}
	}
	
	/**
	 * Starts the game clock, initializes starting blocks, and activates mouse listener
	 */
	public static void startGame(){
		System.out.println("[Game]\tstartGame() called.");
		setRunning(true);
		for(int currentRow = rowCount-2; currentRow<rowCount; currentRow++){
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
				if(cells[column][0]==null) {
					new Block(column,0);
				}else if(!cells[column][0].isFalling()){
					setRunning(false);
				}
			}
		}
	}
	
	/* Commented out until functional
	private static void shuttleCubesUp(Block block){
		if(block.row == 0) return;
		
		Block blockAbove = cells[block.column][block.row-1];
		if(blockAbove!=null){
			shuttleCubesUp(blockAbove);
		}
		block.falling = false;
		cells[block.column][block.row-1] = block;
		block.row--;
		block.yPosition = (block.row)*blockLength;
	}
	
	private static void createCubeRow(){
		for(int currentColumn=0; currentColumn<=columnCount-1; currentColumn++){
			Block newCube = new Block(currentColumn, rowCount-1, blockLength);
			newCube.atBottom = true;
			newCube.falling = false;
			cells[currentColumn][rowCount-1] = newCube;
			blocks.add(newCube);
		}
	}
	
	public static void scrollDownOneRow(){
		
		for(int currentColumn=0; currentColumn<columnCount; currentColumn++){
			Block block = cells[currentColumn][rowCount-1];
			block.atBottom = false;
			
			shuttleCubesUp(block);
	
			createCubeRow();
		}
	}
	*/
}
