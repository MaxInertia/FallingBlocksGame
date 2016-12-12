package logic;

import display.BoardPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.Timer;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public final class Game{

	/**
	 *
	 */
	public static Timer gameTimer;

	public static LinkedList<Cube> cubes;
	//public static boolean[][] isOccupied;
	public static Cube[][] cells;
	public static int cubeLength;
	
	protected static LinkedList<Cube> cubesToDelete;

	protected static int columnCount;
	protected static int rowCount;
	
	public static int DROP_SPEED = 5;
	public static double CUBE_SPAWNS_PER_HUNDRED_TICKS = 6;
	
	public Game(int columns, int rows, int cubeLength){
		gameTimer = new Timer(50, new GameTick());
		
		columnCount = columns;
		rowCount = rows;
		Game.cubeLength = cubeLength;
		
		cubes = new LinkedList<>();
		cubesToDelete = new LinkedList<>();
		initializeCells();
	}
	
	public void initializeCells(){
		cells = new Cube[columnCount][rowCount];
		for(int row = 0; row<rowCount; row++){
			for(int column = 0; column<columnCount; column++){
				cells[column][row] = null;
			}
		}
	}
	
	public static void startGame(){
		gameTimer.start();
		
		for(int currentRow = rowCount-2; currentRow<rowCount; currentRow++){
			for(int currentColumn = 0; currentColumn<columnCount; currentColumn++){
				Cube startingCube = new Cube(currentColumn, currentRow, 60);
				cells[currentColumn][currentRow] = startingCube;
				
				startingCube.yPosition = (currentRow)*cubeLength;
				startingCube.falling = false;
				
				cubes.add(startingCube);
				
				// Check if currentRow is the last visible row on the bottom
				if(currentRow == rowCount-1){
					startingCube.atBottom = true;
				} 
			}
		}
	}
	
	public void createFallingBlocks(){
		for(int column = 0; column < columnCount; column++){
			if(Math.random()*1000 <= CUBE_SPAWNS_PER_HUNDRED_TICKS
					&& cells[column][0]==null) {

				// Create new cube above visible area: 
				//  - Create cube object with column
				//  - Add to cells in appropriate column, row zero
				//  - Add to list of cubes
				Cube newCube = new Cube(column,0,cubeLength);
				cells[column][0] = newCube;
				cubes.add(newCube);
				newCube.setCell(0,column);
				//System.out.println("Spawned a cube");
			}
		}
	}
	
	/* Commented out until functional
	private static void shuttleCubesUp(Cube cube){
		if(cube.row == 0) return;
		
		Cube cubeAbove = cells[cube.column][cube.row-1];
		if(cubeAbove!=null){
			shuttleCubesUp(cubeAbove);
		}
		cube.falling = false;
		cells[cube.column][cube.row-1] = cube;
		cube.row--;
		cube.yPosition = (cube.row)*cubeLength;
	}
	
	private static void createCubeRow(){
		for(int currentColumn=0; currentColumn<=columnCount-1; currentColumn++){
			Cube newCube = new Cube(currentColumn, rowCount-1, cubeLength);
			newCube.atBottom = true;
			newCube.falling = false;
			cells[currentColumn][rowCount-1] = newCube;
			cubes.add(newCube);
		}
	}
	
	public static void scrollDownOneRow(){
		
		for(int currentColumn=0; currentColumn<columnCount; currentColumn++){
			Cube cube = cells[currentColumn][rowCount-1];
			cube.atBottom = false;
			
			shuttleCubesUp(cube);
	
			createCubeRow();
		}
	}
	*/
	
	class GameTick implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			createFallingBlocks();
			
			cubes.stream().forEach((aCube) -> {
				aCube.hasSameTypeNeighbors();
			});
			
			cubesToDelete.stream().map((aCube) -> {
				cells[aCube.column][aCube.row] = null;
				return aCube;
			}).forEach((aCube) -> {
				cubes.remove(aCube);
			});
			
			cubesToDelete = new LinkedList<>();
			
			cubes.stream().forEach((aCube) -> {
				aCube.motionOnGameTick();
			});
			
			BoardPanel.instance.repaint();
		}
	}
}
