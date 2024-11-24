package breakout;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

class NormalBallTest {

	Point p1;
	Point p2;
	Point p3;

	Rect r1;
	Vector v1;
	Circle c1;
	Ball b1;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(0, 0);
		p2 = new Point(4, 2);
		p3 = new Point(2, 4);
		
		r1 = new Rect(p1, p2);
		c1 = new Circle(p3, 4);
		v1 = new Vector(0,-2);
		b1 = new NormalBall(c1,v1);
	}

	@Test
	void testBallThrowError() {
		assertThrows(IllegalArgumentException.class,() -> new NormalBall(null,null));
		assertThrows(IllegalArgumentException.class,() -> new NormalBall(null,v1));
		assertThrows(IllegalArgumentException.class,() -> new NormalBall(c1,null));
		assertDoesNotThrow(() -> new NormalBall(c1,v1));
	}
	
	@Test
	void testBall() {
		assertEquals(p3, b1.getLocation().getCenter());
		assertEquals(4, b1.getLocation().getDiameter());
		assertEquals(v1, b1.getVelocity());
	}

	@Test
	void testHitBlockDestroyed() {
		b1.hitBlock(r1, true);
		assertEquals(new Vector(0,2),b1.getVelocity());
		assertEquals(c1,b1.getLocation());
	}
	
	@Test
	void testHitBlockNotDestroyed() {
		b1.hitBlock(r1, false);
		assertEquals(new Vector(0,2),b1.getVelocity());
		assertEquals(c1,b1.getLocation());
	}

	@Test //LEGIT
	void testMove() {
		b1.move(new Vector(0,2), 1);
		assertEquals(v1,b1.getVelocity());
		assertEquals(new Point(2,6), b1.getLocation().getCenter());
	}
	

	@Test //LEGIT
	void testHitPaddle() {
		b1.hitPaddle(r1, new Vector(10,0));
		assertEquals(new Vector(2,2),b1.getVelocity());
		assertEquals(c1,b1.getLocation());
	}
	
	@Test //LEGIT
	void testHitPaddle2() {//ball.getSquareLength() higher than Constants.MBS2
		Ball ball = new NormalBall(c1,new Vector(0,-20));
		ball.hitPaddle(r1, new Vector(0,0));
		assertEquals(new Vector(0,20),ball.getVelocity());
		assertEquals(c1,ball.getLocation());
	}
	
	@Test
	void testHitWall() {
		b1.hitWall(r1);
		assertEquals(new Vector(0,2),b1.getVelocity());
		assertEquals(c1,b1.getLocation());
	}

	@Test
	void testCloneWithVelocity() {
		b1.cloneWithVelocity(v1);
		assertEquals(new Vector(0,-2),b1.getVelocity());
		assertEquals(c1,b1.getLocation());
	}
	
	@Test
	void testBackToNormal() {
		b1.backToNormal();
		assertEquals(new Vector(0,-2),b1.getVelocity());
		assertEquals(c1,b1.getLocation());
	}

}
