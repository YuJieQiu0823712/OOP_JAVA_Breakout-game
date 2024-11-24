package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

class GameViewTest {

	@Test
	void test() {
		
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_C);

			// possibly want a small Thread.sleep(...) here?

			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_ALT);
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
