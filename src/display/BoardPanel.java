package display;

import display.interactions.KeyPressListener;
import display.interactions.MouseActions;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import logic.Block;
import logic.Game;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public final class BoardPanel extends JPanel implements MouseActions, ImageLoader{
	
	public static BoardPanel instance;
	
	private final Window parentWindow;
	
	public static Block firstSwapBlock;
	public static Block secondSwapBlock;
		
	public BoardPanel(int columns, int rows, Window window){
		super();
		instance = this;
		parentWindow = window;
		this.loadImages();
		
		super.setOpaque(true);
		super.setFocusable(true);
		System.out.println(super.isFocusOwner());
		super.addMouseListener(new MousePressListener());
		super.addKeyListener(new KeyPressListener());
		System.out.println("GamePanel... "+this.getSize().width+","+this.getSize().height);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, instance.parentWindow.VIEW_WIDTH, instance.parentWindow.VIEW_HEIGHT);
		
		paintBlocks(g);
	}
	
	private void paintBlocks(Graphics g){
		Game.blocks.stream().forEach((aBlock) -> {
			g.drawImage(IMAGES[aBlock.getType().id], 
					aBlock.myColumn*Game.blockLength, 
					aBlock.myRow*Game.blockLength - aBlock.yPosition, 
					Game.blockLength, 
					Game.blockLength, 
					this);
		});
		if(Game.isStationaryBlock){
			g.drawImage(IMAGES[7],
					Game.selectedCol*Game.blockLength,
					Game.selectedRow*Game.blockLength,
					//0,
					//0,
					Game.blockLength,
					Game.blockLength,
					this);
		}
	}
	
	/**
	 * Draws images in a standard size on the screen, for testing.
	 * @param g the graphics object
	 */
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
