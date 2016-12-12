package display.interactions;

import display.BoardPanel;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;
import java.awt.event.MouseListener;
import logic.Block;
import logic.Game;
import main.Swipe;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public interface MouseActions {
	
	public static Block getTileAt(int x, int y, int cellSize){
		try{
			int column = x/cellSize;
			int row = y/cellSize;
			return Game.cells[column][row];
		}catch(IndexOutOfBoundsException ioobException){
				return null;
		}
	}
	
	class MousePressListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton()==BUTTON3){
				//System.out.println("Right mouse button clicked!");
				//Game.scrollDownOneRow();
			}
				
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(!Game.isRunning()){ return; }
			
			if(e.getButton()==BUTTON1){
				BoardPanel.firstSwapBlock = getTileAt(e.getX(),e.getY(),Game.blockLength);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(!Game.isRunning()){ return; }
			if(e.getButton()!=BUTTON1){ return; }
		
			BoardPanel.secondSwapBlock = getTileAt(e.getX(),e.getY(),Game.blockLength);
			if(BoardPanel.secondSwapBlock==null){ return; }
			
			// If first block exists, and can be moved
			if(BoardPanel.firstSwapBlock!=null && !BoardPanel.firstSwapBlock.isFalling()){ // Since we check if the block is falling on release, blocks can be selected while falling, and swapped as soon as they stop falling.  (potentially unwanted behavior)
				
				// If second block exists, and can be moved
				if(BoardPanel.secondSwapBlock!=null && !BoardPanel.secondSwapBlock.isFalling()){
					
					// Check if they are neighbors
					if(BoardPanel.firstSwapBlock.isInRangeOf(BoardPanel.secondSwapBlock)){
						Block.Type tempType = BoardPanel.firstSwapBlock.getType();
						BoardPanel.firstSwapBlock.setType(BoardPanel.secondSwapBlock.getType());
						BoardPanel.secondSwapBlock.setType(tempType);
					}
				}
				// If second position is 'empty' and piece one can move
				else{
					// second position is to the left of firstSwapBlock
					if(e.getX() < BoardPanel.firstSwapBlock.myColumn*Game.blockLength) {
						if(Game.cells[BoardPanel.firstSwapBlock.myColumn-1][BoardPanel.firstSwapBlock.myRow] == null){
							
							// Check if block is on the floor (can still fall)
							if(BoardPanel.firstSwapBlock.myRow < Swipe.ROWS-1){
								
								// We need to check to see if a falling block is overlapping the position it will move to
								Block blockInLowerCell = Game.cells[BoardPanel.firstSwapBlock.myColumn-1][BoardPanel.firstSwapBlock.myRow+1];
								if( blockInLowerCell==null || blockInLowerCell.yPosition==0 ){
									Game.cells[BoardPanel.firstSwapBlock.myColumn][BoardPanel.firstSwapBlock.myRow] = null;
									BoardPanel.firstSwapBlock.myColumn--;
									Game.cells[BoardPanel.firstSwapBlock.myColumn][BoardPanel.firstSwapBlock.myRow] = BoardPanel.firstSwapBlock;
								}
							}else{
								Game.cells[BoardPanel.firstSwapBlock.myColumn][BoardPanel.firstSwapBlock.myRow] = null;
								BoardPanel.firstSwapBlock.myColumn--;
								Game.cells[BoardPanel.firstSwapBlock.myColumn][BoardPanel.firstSwapBlock.myRow] = BoardPanel.firstSwapBlock;
							}
						}
					// second position is to the right of firstSwapBlock
					}else if(e.getX() >= (BoardPanel.firstSwapBlock.myColumn+1)*Game.blockLength){
						if(Game.cells[BoardPanel.firstSwapBlock.myColumn+1][BoardPanel.firstSwapBlock.myRow] == null){
							
							// Check if block is on the floor (can still fall)
							if(BoardPanel.firstSwapBlock.myRow < Swipe.ROWS-1){
								
								// We need to check to see if a falling block is overlapping the position it will move to
								Block blockInLowerCell = Game.cells[BoardPanel.firstSwapBlock.myColumn+1][BoardPanel.firstSwapBlock.myRow+1];
								if( blockInLowerCell==null || blockInLowerCell.yPosition==0 ){
									Game.cells[BoardPanel.firstSwapBlock.myColumn][BoardPanel.firstSwapBlock.myRow] = null;
									BoardPanel.firstSwapBlock.myColumn++;
									Game.cells[BoardPanel.firstSwapBlock.myColumn][BoardPanel.firstSwapBlock.myRow] = BoardPanel.firstSwapBlock;
								}
							}else{
								// Block is on the floor, no extra check required
								Game.cells[BoardPanel.firstSwapBlock.myColumn][BoardPanel.firstSwapBlock.myRow] = null;
								BoardPanel.firstSwapBlock.myColumn++;
								Game.cells[BoardPanel.firstSwapBlock.myColumn][BoardPanel.firstSwapBlock.myRow] = BoardPanel.firstSwapBlock;
							}
						}
					}
				}
			}
			BoardPanel.firstSwapBlock = null;
			BoardPanel.secondSwapBlock = null;
		}
			

		@Override
		public void mouseEntered(MouseEvent e) {
		//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void mouseExited(MouseEvent e) {
		//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}
		
	}
}
