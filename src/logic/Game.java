package logic;

import display.BoardPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.Timer;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Game{

	/**
	 *
	 */
	public static Timer gameTimer;

	public static LinkedList<Cube> cubes;
	public static boolean[][] isOccupied;
	public static Cube[][] cells;
	public static int cubeLength;
	
	public static LinkedList<Cube> cubesToDelete;

	protected int columnCount;
	protected int rowCount;
	
	
	
	protected final double cubeSpawnsPerHundredTicks = 2;
	
	public Game(int columns, int rows, int cubeLength){
		gameTimer = new Timer(10, new GameTick());
		cubes = new LinkedList<>();
		columnCount = columns;
		rowCount = rows;
		this.cubeLength = cubeLength;
		this.cubesToDelete = new LinkedList<>();
		initializeOccupiedArray();
	}
	
	public void initializeOccupiedArray(){
		cells = new Cube[columnCount][rowCount];
		isOccupied = new boolean[columnCount][rowCount];
		for(int row = 0; row<rowCount; row++){
			for(int column = 0; column<columnCount; column++){
				isOccupied[column][row] = false;
				cells[column][row] = null;
			}
		}
	}
	
	public void startGame(){
		
	}
	
	class GameTick implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int column = 0; column < columnCount; column++){
				if(Math.random()*100 <= cubeSpawnsPerHundredTicks
						&& !isOccupied[column][0]) {
					Cube newCube = new Cube(column,rowCount,cubeLength);
					isOccupied[column][0] = true;
					cells[column][0] = newCube;
					cubes.add(newCube);
					newCube.setCell(0,column);
					System.out.println("Spawned a cube");
				}
			}
			for(Cube aCube: cubesToDelete){
				cubes.remove(aCube);
			}
			
			cubesToDelete=null;
			cubesToDelete = new LinkedList<>();
			
			for(Cube aCube: cubes){
				aCube.motionOnGameTick();
			}
			BoardPanel.instance.repaint();
		}
	}
}
