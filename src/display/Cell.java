package display;

import java.awt.Dimension;
import logic.Cube;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Cell{// extends JPanel{
	
	public Cube cube;
	
	public int xLeft;
	public int yTop;
	public int cellSideLength;
	
	public Cell(int x, int y, int sideLength){
		super();
		
		xLeft = x;
		yTop = y;
		cellSideLength = sideLength;
		
		//super.setSize(sideLength, sideLength);
		//super.setBounds(x, y, sideLength,sideLength);
		Dimension dim = new Dimension();
		dim.setSize(sideLength, sideLength);
		//super.setMinimumSize(dim);
		//super.setMaximumSize(dim);
		
		//System.out.println("Cell height: "+this.getSize().height+", Cell width:"+this.getSize().width);
	}
	/*
	@Override
	public void paintComponent(Graphics g0){
		Graphics2D g = (Graphics2D) g0.create();
		g.setBackground(Color.WHITE);
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0, 0, cellSideLength, cellSideLength);
		
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, cellSideLength-1, cellSideLength-1);
		//g.drawOval(0, 0, cellSideLength, cellSideLength);
 	}*/
}
