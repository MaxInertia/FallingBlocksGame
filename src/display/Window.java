package display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Window extends JFrame{
	
	BoardPanel gamePanel;
	
	protected static final int MENU_HEIGHT = 100;
	protected static final int GRID_WIDTH = 360+21;
	protected static final int GRID_HEIGHT = 540+22;
	
	public Window(int columns,int rows,int cubeSize){
		super();

		gamePanel = new BoardPanel(6,8,60);
		super.add(gamePanel,BorderLayout.CENTER);
		
		DataPanel bottomPanel = new DataPanel();
		super.add(bottomPanel,BorderLayout.SOUTH);
	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		super.setTitle("Swipe");
		super.setSize(GRID_WIDTH, GRID_HEIGHT);
		//super.pack();
		//super.setResizable(false);
		
		int centerX = (screenSize.width - super.getWidth())/2;
		int centerY = (screenSize.height - super.getHeight())/2;
		super.setLocation(centerX,centerY);
		
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println(super.getWidth()+" "+super.getHeight());
		
		super.setVisible(true);
	}
	
}
