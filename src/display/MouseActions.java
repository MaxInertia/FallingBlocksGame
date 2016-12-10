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
			if(!BoardPanel.firstSwapCube.canMove && !BoardPanel.secondSwapCube.canMove
					&& BoardPanel.firstSwapCube.isNeighborOf(BoardPanel.secondSwapCube)){
				int temp = BoardPanel.firstSwapCube.type;
				BoardPanel.firstSwapCube.type = BoardPanel.secondSwapCube.type;
				BoardPanel.secondSwapCube.type = temp;
			}
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
