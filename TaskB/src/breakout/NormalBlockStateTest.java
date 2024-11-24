package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

class NormalBlockStateTest {

	Point p1;
	Point p2;
	Rect r1;
	BlockState b1;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(0, 0);
		p2 = new Point(4, 2);
		r1 = new Rect(p1, p2);
		b1 = new NormalBlockState(r1);
	}

	@Test
	void testNormalBlockState() {
		assertEquals(r1, b1.getLocation());
	}

	@Test
	void testBlockStateAfterHit() {
		assertEquals(null, b1.blockStateAfterHit(100));
	}

	@Test
	void testBallStateAfterHit() {
		Ball ball = new NormalBall(new Circle(new Point(2, 4),4), new Vector(0,-2)); 
		assertEquals(ball, b1.ballStateAfterHit(ball));
	}

	@Test
	void testPaddleStateAfterHit() {
		PaddleState paddle = new NormalPaddleState(p1,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0]); 
		assertEquals(paddle, b1.paddleStateAfterHit(paddle));
	}

	@Test
	void testGetColor() {
		assertEquals(new Color(128, 128, 128), b1.getColor());
	}

}
