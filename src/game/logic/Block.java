package game.logic;

import javafx.util.Pair;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class Block extends BasicBlock{
	
	public Block(int column, int row){
		super(column, row);
		
		if(myRow==0){
			yPosition = Game.blockLength;
			falling = true;
		}else{
			yPosition = 0;
		}
		
		Game.blocks[0][myColumn][myRow] = this;
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
	public boolean isBlocked() {
		if(myRow == Game.rowCount){ return true; }
		
		if(Game.blocks[0][myColumn][myRow+1] != null
		&& Game.blocks[0][myColumn][myRow+1].yPosition >= this.yPosition-Game.DROP_SPEED){
			return true;
		}

		return false;
		// PREVIOUSLY: yPosition==(Game.rowCount-1)*Game.blockLength
	}
	
	/**
	 * If block is the center of a line-up of three same-type blocks,
	 * this block and those two neighbors are added to the blocksToDelete list.
	 * @return true if cancellations occurred
	 */
	public boolean performCancellations(){
		//System.out.println(Game.myRowCount);
		if(myRow>0 && (myRow+1)<Game.rowCount 
				&& Game.blocks[0][myColumn][myRow-1]!=null
				&& Game.blocks[0][myColumn][myRow+1]!=null){
			
			if(type == Game.blocks[0][myColumn][myRow-1].type
					&& type == Game.blocks[0][myColumn][myRow+1].type){
				
				if( !this.isFalling()
						&& !Game.blocks[0][myColumn][myRow-1].isFalling()
						&& !Game.blocks[0][myColumn][myRow+1].isFalling() ){
				
					//GameOfflineController.updateDestroyedLabel(Statistics.blocksClearedCount+=3);
					Game.cancellationIndexes.add(new Pair<>(myColumn,myRow-1));
					Game.cancellationIndexes.add(new Pair<>(myColumn,myRow));
					Game.cancellationIndexes.add(new Pair<>(myColumn,myRow+1));
					return true;
				}
			}
		}
		if(myColumn>0 && (myColumn+1)<Game.columnCount 
				&& Game.blocks[0][myColumn-1][myRow]!=null
				&& Game.blocks[0][myColumn+1][myRow]!=null){
			
			if(type == Game.blocks[0][myColumn-1][myRow].type
					&& type == Game.blocks[0][myColumn+1][myRow].type){
				
				if( !this.isFalling()
						&& !Game.blocks[0][myColumn-1][myRow].isFalling()
						&& !Game.blocks[0][myColumn+1][myRow].isFalling() ){ //these lines
					
					//GameOfflineController.updateDestroyedLabel(Statistics.blocksClearedCount+=3);
					Game.cancellationIndexes.add(new Pair<>(myColumn-1,myRow));
					Game.cancellationIndexes.add(new Pair<>(myColumn,myRow));
					Game.cancellationIndexes.add(new Pair<>(myColumn+1,myRow));
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
		Game.blocks[0][myColumn][myRow] = null;
		Game.blocks[0][myColumn][++myRow] = this;
	}
	
	/**
	 * Shifts the Block one cell up
	 * (Can throw IndexOutOfBoundsException)
	 */
	public void moveUpOneCell(){
		if(Game.blocks[0][myColumn][myRow] != null){ 
			//System.out.println("moveUpOneCell() Tried moving block into another block");
		}
		if(myRow==0){ Game.setRunning(false); }
		Game.blocks[0][myColumn][myRow] = null;
		Game.blocks[0][myColumn][--myRow] = this;
		
	}
	
	/**
	 * Shifts the Block one cell to the right
	 * (Can throw IndexOutOfBoundsException)
	 * @return if block can be moved right
	 */
	public boolean moveRightOneCell(){
		if(myColumn == Game.columnCount - 1 ) return false;

		int prevCol = myColumn;
		Block temp = Game.blocks[0][prevCol + 1][myRow];
				
		if(temp != null){
			if(!temp.isBlocked()) return false;
			
			Game.blocks[0][prevCol + 1][myRow] = Game.blocks[0][prevCol][myRow];
			Game.blocks[0][prevCol][myRow] = temp;
			Game.blocks[0][prevCol][myRow].myColumn--;
			
		}else{
			Game.blocks[0][prevCol][myRow] = null;
			Game.blocks[0][prevCol + 1][myRow] = this;
			
		}
		//GameOfflineController.updateSwapsLabel(++Statistics.blockSwapCount);
		myColumn++;
		
		//yPosition = Game.blockLength;
		return true;
	}
	
	/**
	 * Shifts the Block one cell to the left
	 * (Can throw IndexOutOfBoundsException)
	 * @return if block can be moved left
	 */
	public boolean moveLeftOneCell(){
		if(myColumn == 0 ) return false;
		
		int prevCol = myColumn;
		Block temp = Game.blocks[0][prevCol - 1][myRow];
		
		if(temp != null){
			if (!temp.isBlocked()) return false;
			
			Game.blocks[0][prevCol - 1][myRow] = Game.blocks[0][prevCol][myRow];
			Game.blocks[0][prevCol][myRow] = temp;
			Game.blocks[0][prevCol][myRow].myColumn++;
		
		}else{
			Game.blocks[0][prevCol][myRow] = null;
			Game.blocks[0][prevCol - 1][myRow] = this;
			
		}
		//GameOfflineController.updateSwapsLabel(++Statistics.blockSwapCount);
		myColumn--;
		//yPosition = Game.blockLength;
		
		return true;
	}
	
	/**
	 * Performs movements that occur each frame.
	 */
	public void motionOnGameTick(){
		
		// The block is sitting on another block, not falling
		if(isBlocked()){ 
			falling = false;
			yPosition = yGround;
			
			if(yGround==Game.blockLength){
				yPosition = 0;
				moveUpOneCell();
			}
			return;
		}
		
		// The block is falling, there is aother block within one blockLength below it
		if(Game.blocks[0][myColumn][myRow+1]!=null){
			if(Game.blocks[0][myColumn][myRow+1].yPosition <= yPosition-Game.DROP_SPEED){
				falling = true;
				yPosition = yPosition - Game.DROP_SPEED;
			}else{
				falling = false;
				yPosition = yGround;
				
			}
		}
		
		// The block is falling and no block below is nearby
		else{
			falling = true;
			yPosition = yPosition - Game.DROP_SPEED;
			if(yPosition<0){
				moveDownOneCell();
				yPosition += Game.blockLength;
			}
		}
		
		//if(yPosition == -Game.blockLength){ yPosition = 0; }
	}
		
}
