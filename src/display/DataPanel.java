package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import logic.Game;

/**
 * @author $Dorian Thiessen | dorian.thiessen@usask.ca | maxinertia.ca
 */
public class DataPanel extends JPanel{

	public static JButton startButton;
	public static JCheckBox continuousMotion;
	
	DataPanel(){
		super();
		super.add(startButton = new JButton("Start"));
		startButton.addActionListener(new StartButtonListener());
		
		super.add(continuousMotion = new JCheckBox("Continuous Motion"));
		continuousMotion.addActionListener((ActionEvent e) -> {
			if(((AbstractButton)e.getSource()).isSelected()){
				//((AbstractButton)e.getSource()).setSelected(false);
				//Game.gameTimer.stop();
				Game.gameTimer.setDelay(250);
				Game.DROP_SPEED = 25;
				Game.CUBE_SPAWNS_PER_HUNDRED_TICKS = 5;
				//Game.gameTimer.start();
			}else{
				//((AbstractButton)e.getSource()).setSelected(true);
				//Game.gameTimer.stop();
				Game.gameTimer.setDelay(100);
				Game.DROP_SPEED = 10;
				Game.CUBE_SPAWNS_PER_HUNDRED_TICKS = 2;
				//Game.gameTimer.start();
			}
			// TODO: Make method to modify drop speed and continuous/discrete mode that modifies all these values accordingly
		});
		
		
	}

	private static class StartButtonListener implements ActionListener {

		public StartButtonListener() {}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(((AbstractButton)e.getSource()).getText().equals("Start")){
				Game.startGame();
				startButton.setText("Pause");
			}else if(((AbstractButton)e.getSource()).getText().equals("Pause")){
				Game.gameTimer.stop();
				startButton.setText("Resume");
				
			}else{
				Game.gameTimer.start();
				startButton.setText("Pause");
			}
		}
	}
	
}
