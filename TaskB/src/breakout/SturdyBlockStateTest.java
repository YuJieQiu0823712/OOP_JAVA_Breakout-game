package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

class SturdyBlockStateTest {

	Point p1;
	Point p2;
	Point p3;
	Rect r1;
	BlockState b1;
	BlockState b2;
	BlockState b3;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(0, 0);
		p2 = new Point(4, 2);
		r1 = new Rect(p1, p2);
		b1 = new SturdyBlockState(r1,1);
		b2 = new SturdyBlockState(r1,2);
		b3 = new SturdyBlockState(r1,3);
	}

	@Test
	void testSturdyBlockState() {
		assertEquals(r1, b1.getLocation());
	}
	
	@Test
	void testGetLivesLeft() {
		assertEquals(1, ((SturdyBlockState)b1).getLivesLeft());
	}
	
	
	@Test
	void testBlockStateAfterHitNotDead() {
		//slow ball hit with a sturdyBlock (livesleft = 3)
		assertEquals(SturdyBlockState.class, b3.blockStateAfterHit(5).getClass());
		assertEquals(2, ((SturdyBlockState)b3.blockStateAfterHit(5)).getLivesLeft());
		assertEquals(b3.getLocation(), ((SturdyBlockState)b3.blockStateAfterHit(5)).getLocation());
	
		//slow ball hit with a sturdyBlock (livesleft = 1)
		assertEquals(SturdyBlockState.class, b1.blockStateAfterHit(5).getClass());
		assertEquals(0, ((SturdyBlockState)b1.blockStateAfterHit(5)).getLivesLeft());
		assertEquals(b1.getLocation(), ((SturdyBlockState)b1.blockStateAfterHit(5)).getLocation());
	
	}

	@Test
	void testBlockStateAfterHitDead() {
		assertEquals(null, b1.blockStateAfterHit(100));
	}

	@Test
	void testBallStateAfterHitNotBounceOn() {
		Ball b = new NormalBall(new Circle(new Point(2,4),4),new Vector(0,-10)); 
		assertEquals(b, b1.ballStateAfterHit(b));
		assertEquals(new Vector(0,-10), b.getVelocity());
	}
	
	@Test
	void testBallStateAfterHitBounceOn() {
		Ball b = new NormalBall(new Circle(new Point(2,4),4),new Vector(0,-2)); 
		assertEquals(b, b1.ballStateAfterHit(b));
		assertEquals(new Vector(0,2), b.getVelocity());
	}

	@Test
	void testPaddleStateAfterHit() {
		PaddleState paddle = new NormalPaddleState(p1,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0]); 
		assertEquals(paddle, b3.paddleStateAfterHit(paddle));
	}

	@Test
	void testGetColor() {
		assertEquals(new Color(160, 82, 45), b1.getColor());
		assertEquals(new Color(123, 63, 0), b2.getColor());
		assertEquals(new Color(92, 64, 51), b3.getColor());
		
	}

}
