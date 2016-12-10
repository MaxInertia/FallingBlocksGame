package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import logic.Game;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class DataPanel extends JPanel{

	public static JButton startButton;
	
	DataPanel(){
		super();
		super.add(startButton = new JButton("Start!"));
		startButton.addActionListener(new StartButtonListener());
	}

	private static class StartButtonListener implements ActionListener {

		public StartButtonListener() {}

		@Override
		public void actionPerformed(ActionEvent e) {
			Game.gameTimer.start();
			startButton.setEnabled(false);
		}
	}
	
}
