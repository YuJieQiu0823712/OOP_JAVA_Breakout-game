package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

class PowerupBallBlockStateTest {

	Point p1;
	Point p2;
	Point p3;
	Rect r1;
	BlockState b1;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(0, 0);
		p2 = new Point(4, 2);
		p3 = new Point(2, 4);
		r1 = new Rect(p1, p2);
		b1 = new PowerupBallBlockState(r1);
	}

	@Test
	void testPowerupBallBlockState() {
		assertEquals(r1, b1.getLocation());
	}

	@Test
	void testBallStateAfterHit() {// A fresh SuperChargedBall 
		Ball ball = new NormalBall(new Circle(p3,Constants.INIT_BALL_DIAMETER), new Vector(0,-2)); 
		Ball newBall = b1.ballStateAfterHit(ball);
		assertEquals(SuperChargedBall.class, newBall.getClass());
		assertEquals(ball.getLocation().getCenter().getX(), newBall.getLocation().getCenter().getX());
		assertEquals(ball.getLocation().getDiameter()+600, newBall.getLocation().getDiameter());
		assertEquals(10000, ((SuperChargedBall)newBall).getLifetime());
	}
	
	@Test
	void testBallStateAfterHit2() {// Not a fresh SuperChargedBall 
		Ball ball = new NormalBall(new Circle(p3,800), new Vector(0,-2)); 
		Ball newBall = b1.ballStateAfterHit(ball);
		assertEquals(SuperChargedBall.class, newBall.getClass());
		assertEquals(ball.getLocation().getCenter().getX(), newBall.getLocation().getCenter().getX());
		assertEquals(ball.getLocation().getDiameter(), newBall.getLocation().getDiameter());
	}

	@Test
	void testGetColor() {
		assertEquals(new Color(215, 0, 64), b1.getColor());
	}

}
