package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;
import logic.Cube;
import logic.Game;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public final class BoardPanel extends JPanel implements MouseActions, ImageLoader{
	
	public static BoardPanel instance;
	
	public static Cell[][] cells;
	public GridLayout cellGrid;
	protected int cellSize;
	
	public static Cube firstSwapCube;
	public static Cube secondSwapCube;
	
	public BoardPanel(int columns, int rows, int cellSize){
		super();
		instance = this;
		this.loadImages();
		this.cellSize = cellSize;
		
		this.addMouseListener(new MousePressListener());
		
		//cellGrid = new GridLayout(rows,columns);
		//gl.setHgap(1);
		//gl.setVgap(1);
		//super.setLayout(cellGrid);
		
		cells = new Cell[rows][columns];
		
		for(int x=0; x<columns; x++){
			for(int y=0; y<rows; y++){
				cells[y][x] = new Cell(Window.GRID_HEIGHT-cellSize*(y+1),x*cellSize,cellSize);
				//super.add(cells[y][x]);
			}
		}
		
		super.setSize(Window.GRID_WIDTH,Window.GRID_HEIGHT+Window.MENU_HEIGHT);
		System.out.println("GamePanel... "+this.getSize().width+","+this.getSize().height);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//((Graphics2D)g).setBackground(Color.BLACK); // TODO: Change background to black?
		//drawImages(g);
		paintCubes(g);
		System.out.println("Draw called");	
	}
	
	public void paintCubes(Graphics g){
		g.setColor(Color.BLACK);
		for(Cube aCube: Game.cubes){
			// Plain colors with borders
			/*g.setColor(aCube.color);
			g.fillRect(aCube.column*cellSize+1, aCube.yPosition+1, cellSize-2, cellSize-2);
			g.setColor(Color.BLACK);
			g.drawRect(aCube.column*cellSize+1, aCube.yPosition+1, cellSize-2, cellSize-2);*/
			
			// Andrew's images
			g.drawImage(IMAGES[aCube.type], aCube.column*cellSize, aCube.yPosition, cellSize, cellSize, this);
		}
	}
	
	public void drawImages(Graphics g){
		g.drawImage(IMAGES[0], 0, 0, 60, 60, this);
		g.drawImage(IMAGES[1], 60, 0, 60, 60, this);
		g.drawImage(IMAGES[2], 120, 0, 60, 60, this);
		g.drawImage(IMAGES[3], 180, 0, 60, 60, this);
		g.drawImage(IMAGES[4], 240, 0, 60, 60, this);
		g.drawImage(IMAGES[5], 300, 0, 60, 60, this);
		g.drawImage(IMAGES[6], 0, 60, 60, 60, this);
		
	}
	
	
	
}
