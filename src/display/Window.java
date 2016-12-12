package display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Window extends JFrame{
	
	BoardPanel gamePanel;
	
	protected final int VIEW_WIDTH;
	protected final int VIEW_HEIGHT; 
	
	public Window(int columns,int rows,int cubeSize){
		super();

		VIEW_WIDTH = cubeSize*columns;
		VIEW_HEIGHT= cubeSize*(rows+1);
		
		gamePanel = new BoardPanel(columns,rows,this);
		gamePanel.setSize(VIEW_WIDTH,VIEW_HEIGHT);
		super.add(gamePanel,BorderLayout.CENTER);
		
		DataPanel bottomPanel = new DataPanel();
		super.add(bottomPanel,BorderLayout.SOUTH);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.setSize(VIEW_WIDTH, VIEW_HEIGHT);
		int centerX = (screenSize.width - super.getWidth())/2;
		int centerY = (screenSize.height - super.getHeight())/2;
		super.setLocation(centerX,centerY);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("Window dimensions: "+super.getWidth()+"x"+super.getHeight());
		super.setTitle("Swipe");
		super.setResizable(false);
		super.setVisible(true);
	}
	
}
