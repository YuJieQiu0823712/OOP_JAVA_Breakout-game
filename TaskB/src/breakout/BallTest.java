package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

class BallTest {

	Point p1;
	Point p2;
	Point p3;
	
	Rect r1;
	Vector v1;
	Circle c1;
	Ball b1;
	Ball b2;
	
	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(0,0);
		p2 = new Point(4,2);
		p3 = new Point(2,4);
		
		r1 = new Rect(p1,p2);
		c1 = new Circle(p3,4);
		v1 = new Vector(0,-2);
		b1 = new NormalBall(c1,v1);
		b2 = new NormalBall(c1,v1);
	}
	
	@Test
	void testBall() {
		assertEquals(p3, b1.getLocation().getCenter());
		assertEquals(4, b1.getLocation().getDiameter());
		assertEquals(v1, b1.getVelocity());
	}
	
	@Test
	void testBallThrowsError() {
		assertThrows(IllegalArgumentException.class,() -> new NormalBall(null,null));
		assertThrows(IllegalArgumentException.class,() -> new NormalBall(null,v1));
		assertThrows(IllegalArgumentException.class,() -> new NormalBall(c1,null));
		assertDoesNotThrow(() -> new NormalBall(c1,v1));
	}
	
	@Test
	void testSetter() {
		b1.setLocation(new Circle(p1,4));
		b1.setVelocity(new Vector(0,2));
		b1.setPosition(p2);
		
		assertEquals(p2, b1.getCenter());
		assertEquals(4, b1.getLocation().getDiameter());
		assertEquals(new Vector(0,2), b1.getVelocity());
	}
	
	@Test
	void testHitRect() {
		assertEquals(true,b1.hitRect(r1));
		assertEquals(new Vector(0,2), b1.getVelocity());
		assertEquals(false,b1.hitRect(r1));
	}
	
	@Test
	void testBounceOn() {
		//ball go up collide with rect
		assertEquals(new Vector(0, 2), b1.bounceOn(r1));

		//ball go down not collide with rect
		Ball ball3 = new NormalBall(new Circle(new Point(8,6),4),new Vector(0,2));
		assertEquals(null, ball3.bounceOn(r1));
	}
	
	@Test
	void testGetColor() {
		assertEquals(Constants.BALL_COLOR,b1.getColor());
		
		Ball ball = new NormalBall(new Circle(new Point(6,6),4),new Vector(0,20));
		assertEquals(Constants.BALL_FAST_COLOR,ball.getColor());
	}

	@Test
	void testClone() {
		b1.clone();
		assertEquals(new Vector(0,-2),b1.getVelocity());
	}
	
	@Test
	void testEquals() {
		Ball superBall = new SuperChargedBall(new Circle(new Point(25000,22500-580),700), new Vector(0,20),5000);		
		Ball b3 = new NormalBall(c1,new Vector(0,20));
		Ball b4 = new NormalBall(new Circle(p2,4),new Vector(0,20));

		assertEquals(b1,b1);
		assertEquals(b1,b2);
		assertNotEquals(b1,null);
		assertNotEquals(b1,superBall);
		assertNotEquals(b1,b3);
		assertNotEquals(b1,b4);


	}
	
	@Test
	void testHashCode() {
		assertEquals(b2.hashCode(),b1.hashCode());
	}
	
}
