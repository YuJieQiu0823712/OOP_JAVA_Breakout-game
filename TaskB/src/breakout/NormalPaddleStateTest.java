package breakout;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import breakout.utils.Point;

class NormalPaddleStateTest {

	Point p;
	PaddleState p1; 
	
	@BeforeEach
	void setUp() throws Exception {
		p = new Point(2000,1000);
		p1 = new NormalPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0]); 
	}
	
	@Test
	void testNormalPaddleState() {
		assertEquals(p,p1.getCenter());
		assertArrayEquals(Constants.TYPICAL_PADDLE_COLORS(),p1.getPossibleColors());
		assertEquals(Constants.TYPICAL_PADDLE_COLORS()[0],p1.getCurColor());
		assertEquals(p1.getCurColor(),p1.getPossibleColors()[0]);
		
//		assertEquals(Arrays.stream(result).anyMatch(c -> c .equals(getCurColor())));
	}
	
	@Test
	void testNumberOfBallsAfterHit() {
		assertEquals(1,p1.numberOfBallsAfterHit());
	}

	@Test
	void testStateAfterHit() {
		PaddleState paddle = p1.stateAfterHit();
		assertEquals(true,paddle.equalContent(p1));
		assertArrayEquals(p1.getPossibleColors(),paddle.getPossibleColors());
		assertEquals(p1.getCurColor(),paddle.getCurColor());

		assertSame(p1.getCenter(),paddle.getCenter());
		assertNotSame(p1.getPossibleColors(),paddle.getPossibleColors());
		assertSame(p1.getCurColor(),paddle.getCurColor());
		
		assertEquals(NormalPaddleState.class,paddle.getClass());
	}

	@Test
	void testGetAcualColors() {
		assertEquals(p1.getCurColor(),p1.getActualColors()[0]);
		assertNotNull(Arrays.stream(p1.getActualColors()).parallel().findAny().get());
	}

	@Test
	void testReproduce() {
		assertEquals(true,p1.reproduce().equalContent(p1));
		assertNotSame(p1,p1.reproduce());
	}

	@Test
	void testEqualContent() {
		PaddleState normalPaddle1 = new NormalPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0]); 
		PaddleState normalPaddle2 = new NormalPaddleState(new Point(1000,1000),new Color[] {Color.green, Color.red},Color.red); 
		PaddleState replicatingPaddle = new ReplicatingPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0],3); 

		assertEquals(true, p1.equalContent(normalPaddle1));
		assertEquals(false, p1.equalContent(replicatingPaddle));
		assertEquals(false, normalPaddle1.equalContent(normalPaddle2));
		assertNotEquals(normalPaddle1,null);
		
		PaddleState normalPaddle3 = new NormalPaddleState(p,new Color[] {Color.green, Color.red},Color.red); 
		assertNotEquals(normalPaddle3.getPossibleColors(),normalPaddle1.getPossibleColors());
	}

}
