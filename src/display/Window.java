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
	
	protected final int MENU_HEIGHT = 100;
	protected final int VIEW_WIDTH; //360+21
	protected final int VIEW_HEIGHT; //540+22
	
	public Window(int columns,int rows,int cubeSize){
		super();

		VIEW_WIDTH = cubeSize*columns;
		VIEW_HEIGHT= cubeSize*(rows+1);
		
		gamePanel = new BoardPanel(columns,rows,cubeSize,this);
		gamePanel.setSize(VIEW_WIDTH,VIEW_HEIGHT+MENU_HEIGHT);
		super.add(gamePanel,BorderLayout.CENTER);
		
		DataPanel bottomPanel = new DataPanel();
		super.add(bottomPanel,BorderLayout.SOUTH);
	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//super.setOpacity((float)1.0);
		super.setTitle("Swipe");
		super.setSize(VIEW_WIDTH, VIEW_HEIGHT);
		super.setResizable(false);
		
		int centerX = (screenSize.width - super.getWidth())/2;
		int centerY = (screenSize.height - super.getHeight())/2;
		super.setLocation(centerX,centerY);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println(super.getWidth()+" "+super.getHeight());
		super.setVisible(true);
	}
	
}
