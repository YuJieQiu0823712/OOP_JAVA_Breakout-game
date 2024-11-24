package breakout;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class BreakoutApplication {

	/**
	 * LEGIT
	 */
	public static void main(String[] args) {
		BreakoutState state = GameMap.createStateFromDescription(Constants.initMap4);
		EventQueue.invokeLater(() -> {
			GameView mazeView = new GameView(state);
			JFrame frame = new JFrame("Breakout");
			frame.getContentPane().add(mazeView);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
