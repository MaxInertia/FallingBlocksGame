package game.utilities;

import game.logic.Block;
import game.logic.Game;
import game.logic.Statistics;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class KeyPressListener<T> implements EventHandler<KeyEvent>{

	public KeyPressListener() {
	}

	public void keyPressed(KeyEvent e) {
		if(Game.selectedCol==-1){ return; }
		
		if(e.getCode().getName().equals("Space")) {
			Game.blockSelected = true;
			return;
		}
		
		if(e.getCode().getName().equals("Shift")) {
			Game.PERIOD_OF_RAISE = Game.PERIOD_OF_RAISE/10;
			return;
		}
		
		int sRow = Game.selectedRow;
		int sCol = Game.selectedCol;
		
		switch(e.getCode().getName()){
			case "Up":
				if(sRow-1>=0 && sRow-1 < Game.rowCount 
				 && Game.blocks[0][sCol][sRow-1]!=null
				 && Game.blocks[0][sCol][sRow-1].isBlocked()) {
					System.out.println("up hit");
					if(Game.blockSelected) {
							Block.Type temp = Game.blocks[0][sCol][sRow].getType();
							Game.blocks[0][sCol][sRow].setType(Game.blocks[0][sCol][sRow-1].getType());
							Game.blocks[0][sCol][sRow-1].setType(temp);	
							Statistics.incrementSwapCount(1);
					}
					Game.selectedRow--;
					Statistics.incrementMovementCount(1);
				}
				break;
				
			case "Down":
				if(sRow+1>=0 && sRow+1 < Game.rowCount 
				 && Game.blocks[0][sCol][sRow+1]!=null
				 && Game.blocks[0][sCol][sRow+1].isBlocked()) {
					System.out.println("down hit");
					if(Game.blockSelected) {
							Block.Type temp = Game.blocks[0][sCol][sRow].getType();
							Game.blocks[0][sCol][sRow].setType(Game.blocks[0][sCol][sRow+1].getType());
							Game.blocks[0][sCol][sRow+1].setType(temp);	
							Statistics.incrementSwapCount(1);
					}
					Game.selectedRow++;
					Statistics.incrementMovementCount(1);
					return;
				}
				for(int row=sRow+1; row < Game.rowCount; row++) { 
					if( Game.blocks[0][sCol][row] != null
					 && Game.blocks[0][sCol][row].isBlocked() ) {
						
						Game.selectedRow = row;
						Statistics.incrementMovementCount(1);
						return;
					}
				}
				break;
				
			case "Right":
				if( (sCol+1) < Game.columnCount 
				 && Game.blockSelected) {
					if(!Game.blocks[0][Game.selectedCol][Game.selectedRow].isBlocked()) { return; }  // Prevents blocks from being carried through the sky
					
					if( Game.blocks[0][Game.selectedCol][Game.selectedRow].moveRightOneCell() ){
						Game.selectedCol++;
						Statistics.incrementSwapCount(1);
						Statistics.incrementMovementCount(1);
					}
					return;
				}
				for(int col=sCol+1; col < Game.columnCount; col++) { 
					if( Game.blocks[0][col][sRow] != null
					 && Game.blocks[0][col][sRow].isBlocked() ) {
						
						Game.selectedCol = col;
						Statistics.incrementMovementCount(1);
						return;
					}
				}
				break;
				
			case "Left":		
				if( (sCol-1) >= 0 
				 && Game.blockSelected) {
					if(!Game.blocks[0][Game.selectedCol][Game.selectedRow].isBlocked()) { return; } // Prevents blocks from being carried through the sky
					
					if( Game.blocks[0][Game.selectedCol][Game.selectedRow].moveLeftOneCell() ){
						Game.selectedCol--;
						Statistics.incrementMovementCount(1);
						Statistics.incrementSwapCount(1);
					}
					return;
				}
				
				for(int col=sCol-1; col>=0 ; col--) { 
					if(Game.blocks[0][col][sRow]!=null
					 && Game.blocks[0][col][sRow].isBlocked()) {
						
						Game.selectedCol = col;
						Statistics.incrementMovementCount(1);
						return;
					}
				}
				break;
		}
	}

	public void keyReleased(KeyEvent e){
		if(e.getCode().getName().equals("Space")){
			Game.blockSelected = false;
			
		}else if(e.getCode().getName().equals("Shift")) {
			Game.PERIOD_OF_RAISE = Game.PERIOD_OF_RAISE*10;
		}
	}

	@Override
	public void handle(KeyEvent event) {
		if(event.getEventType().equals(KeyEvent.KEY_PRESSED)){
			keyPressed(event);
			
		}else if(event.getEventType().equals(KeyEvent.KEY_RELEASED)){
			keyReleased(event);
		}
		
		System.out.println(event.getCode().getName());
	}

	
}
