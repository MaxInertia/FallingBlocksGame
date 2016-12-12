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

	public boolean falling = true;
	private boolean atBottom = false;
	//private int ticksLeftStationary;
	
	public int cubeLength; // TODO: make a static final varibale
	public int rowCount;
	
	public Cube(int column, int rowCount, int cubeLength){
		yPosition = -cubeLength; // So it starts just above the GamePanel
		this.column = column;
		this.cubeLength = cubeLength;
		this.rowCount = rowCount;
		
		int rand = ((int)(Math.random()*62));
		if(rand<10){ type = 0; }
		else if(rand<20){ type = 1; }
		else if(rand<30){ type = 2; }
		else if(rand<40){ type = 3; }
		else if(rand<50){ type = 4; }
		else if(rand<60){ type = 5; }
		else if(rand<70){ type = 6; }
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
	
	public boolean hasSameTypeNeighbors(){
		System.out.println(rowCount);
		if(row>0 && (row+1)<Game.rowCount 
				&& Game.cells[column][row-1]!=null
				&& Game.cells[column][row+1]!=null){
			
			if(type == Game.cells[column][row-1].type
					&& type == Game.cells[column][row+1].type){
				
				if( !this.falling
						&& !Game.cells[column][row-1].falling
						&& !Game.cells[column][row+1].falling ){
				
					Game.cubesToDelete.add(this);
					Game.cubesToDelete.add(Game.cells[column][row-1]);
					Game.cubesToDelete.add(Game.cells[column][row+1]);
				}
			}
		}
		if(column>0 && (column+1)<Game.columnCount 
				&& Game.cells[column-1][row]!=null
				&& Game.cells[column+1][row]!=null){
			
			if(type == Game.cells[column-1][row].type
					&& type == Game.cells[column+1][row].type){
				
				if( !this.falling
						&& !Game.cells[column-1][row].falling
						&& !Game.cells[column+1][row].falling ){
					
					Game.cubesToDelete.add(this);
					Game.cubesToDelete.add(Game.cells[column-1][row]);
					Game.cubesToDelete.add(Game.cells[column+1][row]);
				}
			}
		}
		return false;
	}
	
	public void moveDownOneCell(){
		//canMove = true;
		//ticksLeftStationary = 10;
		
		//Game.isOccupied[column][row] = false;
		Game.cells[column][row] = null;
		//Game.isOccupied[column][++row] = true;
		Game.cells[column][++row] = this;
	}
	
	public void motionOnGameTick(){
		if(atBottom){ System.out.println("return"); return; }
		
		else if(yPosition<0){ yPosition = yPosition + Game.DROP_SPEED; }
		
		else if((yPosition)%cubeLength == 0){
			//if(Game.isOccupied[column][row+1]){
			if(Game.cells[column][row+1]!=null){
				falling=false;
			}else{
				falling=true;
				yPosition = yPosition + Game.DROP_SPEED;
				moveDownOneCell();
			}
		} else {
			yPosition = yPosition + Game.DROP_SPEED;
		}
		
		if(yPosition==(rowCount-1)*cubeLength){
			atBottom = true;
			falling = false;
		}
	}
		
}
