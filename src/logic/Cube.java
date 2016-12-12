package logic;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Cube {
	
	public int column;
	public int row;
	public int yPosition;
	
	public int type;

	public boolean falling = true;
	public boolean atBottom = false;
	//private int ticksLeftStationary;
	
	public int cubeLength; // TODO: make a static final varibale
	
	public Cube(int column, int row, int cubeLength){
		//yPosition = -cubeLength; // So it starts just above the GamePanel
		yPosition = (row-1)*cubeLength;
		this.column = column;
		this.row = row;
		this.cubeLength = cubeLength;
		
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
		//System.out.println(Game.rowCount);
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
						&& !Game.cells[column+1][row].falling ){ //these lines
					
					Game.cubesToDelete.add(this);
					Game.cubesToDelete.add(Game.cells[column-1][row]);
					Game.cubesToDelete.add(Game.cells[column+1][row]);
				}
			}
		}
		return false;
	}
	
	public void moveDownOneCell(){
		//falling = true;
		//ticksLeftStationary = 10;
		
		Game.cells[column][row] = null;
		Game.cells[column][++row] = this;
	}
	
	public void motionOnGameTick(){
		if(atBottom){ return; }
		
		else if(yPosition<0){ yPosition = yPosition + Game.DROP_SPEED; }
		
		else if((yPosition)%cubeLength == 0){
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
		
		if(yPosition==(Game.rowCount-1)*cubeLength){
			atBottom = true;
			falling = false;
		}
	}
		
}
