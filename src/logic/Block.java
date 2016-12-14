package logic;

import main.MainController;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Block {
	
	public enum Type{
		SUN(0,1),MOON(1,4),WINTER(2,1),
		IRON(3,1),ATOMIC(4,1),BIO(5,1),
		LASER(6,1);
		
		public final int id;
		public final int range;
		
		Type(int typeID, int typeRange){
			id = typeID;
			range = typeRange;
		}
	}
	
	private Type type;
	
	public int myColumn;
	public int myRow;
	public int yPosition;
	private boolean falling;
		
	public Block(int column, int row){	
		this.myColumn = column;
		this.myRow = row;
		
		if(myRow==0){
			yPosition = Game.blockLength;
			falling = true;
		}else{
			yPosition = 0;
		}
		
		assignInitialType();
		
		Game.cells[myColumn][myRow] = this;
		Game.blocks.add(this);	
	}
	
	private void assignInitialType(){
		int rand = ((int)(Math.random()*62));
		if(rand<10){ type = Type.SUN; }
		else if(rand<20){ type = Type.MOON; }
		else if(rand<30){ type = Type.WINTER; }
		else if(rand<40){ type = Type.IRON; }
		else if(rand<50){ type = Type.ATOMIC; }
		else if(rand<60){ type = Type.BIO; }
		else if(rand<70){ type = Type.LASER; }
	}
	
	public void setType(Type t){
		type = t;
	}
	
	public Type getType(){
		return type;
	}
	
	/**
	 * Check if otherBlock is within range of this block
	 * @param otherBlock another block on the board
	 * @return if otherBlock is in range of this block
	 */
	public boolean isInRangeOf(Block otherBlock){
		if( (this.myRow == otherBlock.myRow) && Math.abs(this.myColumn-otherBlock.myColumn)<=this.type.range) {
			return true;
		}
		else if( (this.myColumn == otherBlock.myColumn) && Math.abs(this.myRow-otherBlock.myRow)<=this.type.range) {
			return true;
		}
		return false;
	}
	
	public boolean isFalling(){
		return falling;
	}
	
	/**
	 * Checks if the block is on the bottom row / floor of the game grid
	 * @return if the block is on the bottom row
	 */
	public boolean isAtBottom(){
		return yPosition==0 && myRow==(Game.rowCount-1);
		// PREVIOUSLY: yPosition==(Game.rowCount-1)*Game.blockLength
	}
	
	/**
	 * If block is the center of a line-up of three same-type blocks,
	 * this block and those two neighbors are added to the blocksToDelete list.
	 */
	public boolean hasSameTypeNeighbors(){
		//System.out.println(Game.myRowCount);
		if(myRow>0 && (myRow+1)<Game.rowCount 
				&& Game.cells[myColumn][myRow-1]!=null
				&& Game.cells[myColumn][myRow+1]!=null){
			
			if(type == Game.cells[myColumn][myRow-1].type
					&& type == Game.cells[myColumn][myRow+1].type){
				
				if( !this.isFalling()
						&& !Game.cells[myColumn][myRow-1].isFalling()
						&& !Game.cells[myColumn][myRow+1].isFalling() ){
				
					Game.blocksToDelete.add(this);
					Game.blocksToDelete.add(Game.cells[myColumn][myRow-1]);
					Game.blocksToDelete.add(Game.cells[myColumn][myRow+1]);
					return true;
				}
			}
		}
		if(myColumn>0 && (myColumn+1)<Game.columnCount 
				&& Game.cells[myColumn-1][myRow]!=null
				&& Game.cells[myColumn+1][myRow]!=null){
			
			if(type == Game.cells[myColumn-1][myRow].type
					&& type == Game.cells[myColumn+1][myRow].type){
				
				if( !this.isFalling()
						&& !Game.cells[myColumn-1][myRow].isFalling()
						&& !Game.cells[myColumn+1][myRow].isFalling() ){ //these lines
					
					Game.blocksToDelete.add(this);
					Game.blocksToDelete.add(Game.cells[myColumn-1][myRow]);
					Game.blocksToDelete.add(Game.cells[myColumn+1][myRow]);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Shifts the block one cell down
	 * (Can throw IndexOutOfBoundsException)
	 */
	public void moveDownOneCell(){
		yPosition = Game.blockLength;
		Game.cells[myColumn][myRow] = null;
		Game.cells[myColumn][++myRow] = this;
	}
	
	/**
	 * Shifts the Block one cell up
	 * (Can throw IndexOutOfBoundsException)
	 */
	public void moveUpOneCell(){
		yPosition = Game.blockLength;
		Game.cells[myColumn][myRow] = null;
		Game.cells[myColumn][--myRow] = this;
	}
	
	/**
	 * Shifts the Block one cell to the right
	 * (Can throw IndexOutOfBoundsException)
	 * @return 
	 */
	public boolean moveRightOneCell(){
		if(myColumn == Game.columnCount - 1 ) return false;

		int prevCol = myColumn;
		Block temp = Game.cells[prevCol + 1][myRow];
				
		if(temp != null){
			if(temp.isFalling()) return false;
			
			Game.cells[prevCol + 1][myRow] = Game.cells[prevCol][myRow];
			Game.cells[prevCol][myRow] = temp;
			Game.cells[prevCol][myRow].myColumn--;
			
		}else{
			Game.cells[prevCol][myRow] = null;
			Game.cells[prevCol + 1][myRow] = this;
			
		}
		MainController.updateSwapsLabel(++Statistics.blockSwapCount);
		myColumn++;
		
		//yPosition = Game.blockLength;
		return true;
	}
	
	/**
	 * Shifts the Block one cell to the left
	 * (Can throw IndexOutOfBoundsException)
	 */
	public boolean moveLeftOneCell(){
		if(myColumn == 0 ) return false;
		
		int prevCol = myColumn;
		Block temp = Game.cells[prevCol - 1][myRow];
		
		if(temp != null){
			if (temp.isFalling()) return false;
			
			Game.cells[prevCol - 1][myRow] = Game.cells[prevCol][myRow];
			Game.cells[prevCol][myRow] = temp;
			Game.cells[prevCol][myRow].myColumn++;
		
		}else{
			Game.cells[prevCol][myRow] = null;
			Game.cells[prevCol - 1][myRow] = this;
			
		}
		MainController.updateSwapsLabel(++Statistics.blockSwapCount);
		myColumn--;
		//yPosition = Game.blockLength;
		
		return true;
	}
	
	/**
	 * Performs movements that occur each frame.
	 */
	public void motionOnGameTick(){
		if(isAtBottom()){ return; }
		
		if(yPosition==0){
			if(Game.cells[myColumn][myRow+1]!=null){
				falling = false;
				return;
			}else{
				falling=true;
				moveDownOneCell();
			}
		}
		
		yPosition = yPosition - Game.DROP_SPEED;
		
		if(yPosition == -Game.blockLength){ yPosition = 0; }
		if(isAtBottom()){ falling = false; }
	}
		
}
