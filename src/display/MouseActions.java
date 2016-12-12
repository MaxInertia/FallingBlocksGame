package display;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import logic.Cube;
import logic.Game;

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
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			BoardPanel.firstSwapCube = getTileAt(e.getX(),e.getY(),Game.cubeLength);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			BoardPanel.secondSwapCube = getTileAt(e.getX(),e.getY(),Game.cubeLength);
			// If first block exists, and can be moved
			if(BoardPanel.firstSwapCube!=null && !BoardPanel.firstSwapCube.falling){
				// If second block exists, and can be moved
				if(BoardPanel.secondSwapCube!=null && !BoardPanel.secondSwapCube.falling){
					// Check if they are neighbors
					if(BoardPanel.firstSwapCube.isNeighborOf(BoardPanel.secondSwapCube)){
						int temp = BoardPanel.firstSwapCube.type;
						BoardPanel.firstSwapCube.type = BoardPanel.secondSwapCube.type;
						BoardPanel.secondSwapCube.type = temp;
					}
				}
				// If second position is empty and piece one can move
				else{
					if(e.getX() < BoardPanel.firstSwapCube.column*BoardPanel.firstSwapCube.cubeLength) {
						if(Game.cells[BoardPanel.firstSwapCube.column-1][BoardPanel.firstSwapCube.row] == null){
							Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = null;
							BoardPanel.firstSwapCube.column--;
							Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = BoardPanel.firstSwapCube;
						}
					}else if(e.getX() > (BoardPanel.firstSwapCube.column+1)*BoardPanel.firstSwapCube.cubeLength){
						if(Game.cells[BoardPanel.firstSwapCube.column+1][BoardPanel.firstSwapCube.row] == null){
							Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = null;
							BoardPanel.firstSwapCube.column++;
							Game.cells[BoardPanel.firstSwapCube.column][BoardPanel.firstSwapCube.row] = BoardPanel.firstSwapCube;
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
