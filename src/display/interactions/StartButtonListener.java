package display.interactions;

import static display.DataPanel.resetButton;
import static display.DataPanel.startButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import logic.Game;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class StartButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((AbstractButton)e.getSource()).getText()) {
			case "Start":
				resetButton.setEnabled(true);
				Game.startGame();
				startButton.setText("Pause");
				break;
			case "Pause":
				Game.setRunning(false);
				startButton.setText("Resume");
				break;
			default:
				Game.setRunning(true);
				startButton.setText("Pause");
				break;
		}
	}
}
