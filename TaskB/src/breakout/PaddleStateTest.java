package breakout;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import breakout.utils.Point;

class PaddleStateTest {
	
	Point p;
	PaddleState p1;

	@BeforeEach
	void setUp() throws Exception {
		p = new Point(2000,1000);
		p1 = new NormalPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0]); 
	}
	
	@Test
	void testPaddlePreconditon() {
		
//		Object[][] someArray = new Object[5][];
//        someArray[0] = new Object[10];
//        someArray[1] = null;
//        someArray[2] = new Object[1];
//        someArray[3] = null;
//        someArray[4] = new Object[5];
//
//        for (int i=0; i<=someArray.length-1; i++) {
//            if (someArray[i] != null) {
//                System.out.println("not null");
//            } else {
//                System.out.println("null");
//            }
//        }
//        
//        
//		PaddleState otherPaddle = new NormalPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0]); 
//		assertEquals(true, p1.equals(otherPaddle));
//		
//		PaddleState otherPaddle2 = new ReplicatingPaddleState(new Point(1000,1000),new Color[] {Color.green, Color.magenta},Constants.TYPICAL_PADDLE_COLORS()[1],4); 
//		assertEquals(false, p1.equals(otherPaddle2));
//		
//		assertEquals(true, p1.equals(p1));
//		assertEquals(false, p1.equals(null));
	}
	
	@Test
	void testNormalPaddleState() {
		assertEquals(p,p1.getCenter());
		assertArrayEquals(Constants.TYPICAL_PADDLE_COLORS(),p1.getPossibleColors());
		assertEquals(p1.getCurColor(),p1.getPossibleColors()[0]);
		assertEquals(Constants.TYPICAL_PADDLE_COLORS()[0],p1.getCurColor());
		assertTrue((Arrays.asList(p1.getPossibleColors())).contains(p1.getCurColor()));

	}

	@Test
	void testSetCenter() {
		PaddleState paddle = new NormalPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0]); 
		paddle.setCenter(new Point(1000,1000));
		assertEquals(new Point(1000,1000),paddle.getCenter());
	}

	@Test
	void testTossCurColor() {
		p1.tossCurColor(); 
		assertTrue((Arrays.asList(p1.getPossibleColors())).contains(p1.getCurColor()));
		assertNotEquals(null,p1.getPossibleColors());
	}

	


}
