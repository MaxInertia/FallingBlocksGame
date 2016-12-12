package display;

import display.interactions.ResetButtonListener;
import display.interactions.StartButtonListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class DataPanel extends JPanel{

	public static JButton startButton;
	public static JButton resetButton;
	
	DataPanel(){
		super();
		
		super.add(startButton = new JButton("Start"));
		startButton.addActionListener(new StartButtonListener());
		
		super.add(resetButton = new JButton("Reset"));
		resetButton.addActionListener(new ResetButtonListener());
		resetButton.setEnabled(false);
	}

}
