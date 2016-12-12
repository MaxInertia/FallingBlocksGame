package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import logic.Cube;
import logic.Game;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public final class BoardPanel extends JPanel implements MouseActions, ImageLoader{
	
	private Window parentWindow;
	
	public static BoardPanel instance;
	protected int cellSize;
	
	public static Cube firstSwapCube;
	public static Cube secondSwapCube;
	
	public BoardPanel(int columns, int rows, int cellSize, Window window){
		super();
		instance = this;
		this.parentWindow = window;
		this.loadImages();
		this.cellSize = cellSize;
		
		super.setOpaque(true);
		this.addMouseListener(new MousePressListener());
		System.out.println("GamePanel... "+this.getSize().width+","+this.getSize().height);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		// TODO: Change background to black?
		((Graphics2D)g).setBackground(Color.BLACK);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, parentWindow.VIEW_WIDTH, parentWindow.VIEW_HEIGHT);

		paintCubes(g);
		//System.out.println("Draw called");	
	}
	
	public void paintCubes(Graphics g){
		for(Cube aCube: Game.cubes){
			g.drawImage(IMAGES[aCube.type], aCube.column*cellSize, aCube.yPosition, cellSize, cellSize, this);
		}
	}
	
	public void drawImages(Graphics g){
		g.drawImage(IMAGES[0], 0, 0, 60, 60, this);
		g.drawImage(IMAGES[1], 0, 60, 60, 60, this);
		g.drawImage(IMAGES[2], 0, 120, 60, 60, this);
		g.drawImage(IMAGES[3], 0, 180, 60, 60, this);
		g.drawImage(IMAGES[4], 0, 240, 60, 60, this);
		g.drawImage(IMAGES[5], 0, 300, 60, 60, this);
		g.drawImage(IMAGES[6], 0, 360, 60, 60, this);
	}	
}
