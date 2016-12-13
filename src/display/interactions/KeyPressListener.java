package display.interactions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import logic.Block;
import logic.Game;
import static logic.Game.cells;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class KeyPressListener extends KeyAdapter {

	public KeyPressListener() {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(Game.selectedCol==-1){ return; }
		
		if(e.getKeyChar()==' ') {
			Game.blockSelected = true;
			return;
		}
		
		int sRow = Game.selectedRow;
		int sCol = Game.selectedCol;
		
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP:
				if(sRow-1>=0 && sRow-1 < Game.rowCount 
				 && Game.cells[sCol][sRow-1]!=null
				 && !Game.cells[sCol][sRow-1].isFalling()) {
					
					if(Game.blockSelected) {
							Block.Type temp = cells[sCol][sRow].getType();
							Game.cells[sCol][sRow].setType(Game.cells[sCol][sRow-1].getType());
							Game.cells[sCol][sRow-1].setType(temp);	
					}
					Game.selectedRow--;
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(sRow+1>=0 && sRow+1 < Game.rowCount 
				 && Game.cells[sCol][sRow+1]!=null
				 && !Game.cells[sCol][sRow+1].isFalling()) {
					
					if(Game.blockSelected) {
							Block.Type temp = cells[sCol][sRow].getType();
							Game.cells[sCol][sRow].setType(Game.cells[sCol][sRow+1].getType());
							Game.cells[sCol][sRow+1].setType(temp);	
					}
					Game.selectedRow++;
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if( (sCol+1) < Game.columnCount 
				 && Game.blockSelected) {
					
					if( Game.cells[Game.selectedCol][Game.selectedRow].moveRightOneCell() ){
						Game.selectedCol++;
					}
					return;
				}
				
				for(int col=sCol+1; col < Game.columnCount; col++) { 
					if( Game.cells[col][sRow] != null
					 && !Game.cells[col][sRow].isFalling() ) {
						
						Game.selectedCol = col;
						return;
					}
				}
				break;
				
			case KeyEvent.VK_LEFT:
				
				if( (sCol-1) >= 0 
				 && Game.blockSelected) {
					
					if( Game.cells[Game.selectedCol][Game.selectedRow].moveLeftOneCell() ){
						Game.selectedCol--;
					}
					return;
				}
				
				for(int col=sCol-1; col>=0 ; col--) { 
					
					if(Game.cells[col][sRow]!=null
					 && !Game.cells[col][sRow].isFalling()) {
						
						Game.selectedCol = col;
						return;
					}
				}
				break;
		}
	}


	public void keyReleased(KeyEvent e){
		if(e.getKeyChar()==' '){
			Game.blockSelected = false;
		}
	}
	
}
