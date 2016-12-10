package logic;

import java.awt.Color;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Cube {
	
	public int column;
	public int row;
	public int yPosition;
	
	public Color color;
	public int type;
	
	private final int DROP_SPEED = 5;
	public boolean canMove = true;
	private boolean atBottom = false;
	//private int ticksLeftStationary;
	
	public int cubeLength; // TODO: make a static final varibale
	public int rowCount;
	
	public Cube(int column, int rowCount, int cubeLength){
		yPosition = -cubeLength; // So it starts just above the GamePanel
		this.column = column;
		this.cubeLength = cubeLength;
		this.rowCount = rowCount;
		
		int rand = ((int)(Math.random()*10)%6)+1;
		switch(rand){
			case 1:
				color = Color.BLUE;
				type = 0;
				break;
			case 2:
				color = Color.RED;
				type = 2;
				break;
			case 3:
				color = Color.ORANGE;
				type = 3;
				break;
			case 4:
				color = Color.GREEN;
				type = 4;
				break;
			case 5:
				color = Color.PINK;
				type = 5;
				break;
			case 6:
				color = Color.BLACK;
				type = 6;
		}
	}
	
	public void setCell(int row, int column){
		this.row = row;
		//BoardPanel.cells[0][column].cube = this;
	}
	
	public boolean isNeighborOf(Cube otherCube){
		if( (this.row == otherCube.row)// ) {
			&& (Math.abs(this.column-otherCube.column)<=2) 
		){
			return true;
		}
		else if( (this.column == otherCube.column)// ) {
			&& Math.abs(this.row-otherCube.row)<=2){
			
			return true;
		}
		return false;
	}
	
	public void moveDownOneCell(){
		//canMove = true;
		//ticksLeftStationary = 10;
		
		Game.isOccupied[column][row] = false;
		Game.cells[column][row] = null;
		Game.isOccupied[column][++row] = true;
		Game.cells[column][row] = this;
	}
	
	public void motionOnGameTick(){
		if(atBottom){ System.out.println("return"); return; }
		
		else if(yPosition<0){ yPosition = yPosition + DROP_SPEED; }
		
		else if((yPosition)%cubeLength == 0){
			if(Game.isOccupied[column][row+1]){
				canMove=false;
			}else{
				canMove=true;
				yPosition = yPosition + DROP_SPEED;
				moveDownOneCell();
			}
		} else {
			yPosition = yPosition + DROP_SPEED;
		}
		
		if(yPosition==(rowCount-1)*cubeLength){
			atBottom = true;
			canMove = false;
		}
	}
		
}
