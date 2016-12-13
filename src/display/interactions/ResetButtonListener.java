package display.interactions;

import static display.DataPanel.resetButton;
import static display.DataPanel.startButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logic.Game;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class ResetButtonListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		Game.reset();
		startButton.setText("Start");
		resetButton.setEnabled(false);
	}
}
