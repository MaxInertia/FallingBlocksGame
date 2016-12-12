package display;

import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;
import java.awt.event.MouseListener;
import logic.Cube;
import logic.Game;
import main.ClickQuick;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public interface MouseActions {
	
	public static Cube getTileAt(int x, int y, int cellSize){
		int column = x/cellSize;
		int row = y/cellSize;
		
		return Game.cells[column][row];
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
			if(e.getButton()==BUTTON1){
				BoardPanel.firstSwapCube = getTileAt(e.getX(),e.getY(),Game.cubeLength);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton()!=BUTTON1){ return; }
			
			BoardPanel.secondSwapCube = getTileAt(e.getX(),e.getY(),Game.cubeLength);
			
			// If first block exists, and can be moved
			if(BoardPanel.firstSwapCube!=null && !BoardPanel.firstSwapCube.falling){ // Since we check if the block is falling on release, blocks can be selected while falling, and swapped as soon as they stop falling.  (potentially unwanted behavior)
				
				// If second block exists, and can be moved
				if(BoardPanel.secondSwapCube!=null && !BoardPanel.secondSwapCube.falling){
					
					// Check if they are neighbors
					if(BoardPanel.firstSwapCube.isNeighborOf(BoardPanel.secondSwapCube)){
						int temp = BoardPanel.firstSwapCube.type;
						BoardPanel.firstSwapCube.type = BoardPanel.secondSwapCube.type;
						BoardPanel.secondSwapCube.type = temp;
					}
				}
				// If second position is 'empty' and piece one can move
				else{
					// second position is to the left of firstSwapCube
					if(e.getX() < BoardPanel.firstSwapCube.column*BoardPanel.firstSwapCube.cubeLength) {
						if(Game.cells[BoardPanel.firstSwapCube.column-1][BoardPanel.firstSwapCube.row] == null){
							
							// Check if block is on the floor (can still fall)
							if(BoardPanel.firstSwapCube.row < ClickQuick.ROWS-1){
								
								// We need to check to see if a falling block is overlapping the position it will move to
								Cube cubeInLowerCell = Game.cells[BoardPanel.firstSwapCube.column-1][BoardPanel.firstSwapCube.row+1];
								if( cubeInLowerCell==null || 
										(BoardPanel.firstSwapCube.yPosition+Game.cubeLength <= cubeInLowerCell.yPosition) ){
									Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = null;
									BoardPanel.firstSwapCube.column--;
									Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = BoardPanel.firstSwapCube;
								}
							}else{
								Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = null;
								BoardPanel.firstSwapCube.column--;
								Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = BoardPanel.firstSwapCube;
							}
						}
					// second position is to the right of firstSwapCube
					}else if(e.getX() >= (BoardPanel.firstSwapCube.column+1)*BoardPanel.firstSwapCube.cubeLength){
						if(Game.cells[BoardPanel.firstSwapCube.column+1][BoardPanel.firstSwapCube.row] == null){
							
							// Check if block is on the floor (can still fall)
							if(BoardPanel.firstSwapCube.row < ClickQuick.ROWS-1){
								
								// We need to check to see if a falling block is overlapping the position it will move to
								Cube cubeInLowerCell = Game.cells[BoardPanel.firstSwapCube.column+1][BoardPanel.firstSwapCube.row+1];
								if( cubeInLowerCell==null || 
										(BoardPanel.firstSwapCube.yPosition+Game.cubeLength <= cubeInLowerCell.yPosition) ){
									Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = null;
									BoardPanel.firstSwapCube.column++;
									Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = BoardPanel.firstSwapCube;
								}
							}else{
								// Block is on the floor, no extra check required
								Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = null;
								BoardPanel.firstSwapCube.column++;
								Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = BoardPanel.firstSwapCube;
							}
						}
					}
				}
			}
			BoardPanel.firstSwapCube = null;
			BoardPanel.secondSwapCube = null;
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
