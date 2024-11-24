package breakout;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

class SuperChargedBallTest {

	Point p1;
	Point p2;
	Point p3;
	Point p4;

	Rect r1;
	Vector v1;
	Vector v2;
	Circle c1;
	Circle c2;
	Ball b1;
	Ball b2;
	Ball b3;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(0, 0);
		p2 = new Point(4, 2);
		p3 = new Point(2, 4);
		p4 = new Point(2, 6);
		
		r1 = new Rect(p1, p2);
		c1 = new Circle(p3, 4);
		c2 = new Circle(p4, 4);
		v1 = new Vector(0,-2);
		v2 = new Vector(0,2);
		b1 = new SuperChargedBall(c1,v1,5000);
		b2 = new SuperChargedBall(c1,v1,-2);
		b3 = new SuperChargedBall(c2,v2,5000);
	}

	@Test
	void testBallThrowError() {
		assertThrows(IllegalArgumentException.class,() -> new SuperChargedBall(null,null,5000));
		assertThrows(IllegalArgumentException.class,() -> new SuperChargedBall(null,v1,5000));
		assertThrows(IllegalArgumentException.class,() -> new SuperChargedBall(c1,null,5000));
		assertDoesNotThrow(() -> new SuperChargedBall(c1,v1,5000));
	}
	
	@Test
	void testBall() {
		assertEquals(p3, b1.getLocation().getCenter());
		assertEquals(4, b1.getLocation().getDiameter());
		assertEquals(v1, b1.getVelocity());
	}

	@Test
	void testHitBlockDestroyed() {
		//ball's diameter larger than Constants.INIT_BALL_DIAMETER + 100
		Ball ball = new SuperChargedBall(new Circle(new Point(2, 400), 800),v1,5000);
		ball.hitBlock(r1, true);
		assertEquals(700,ball.getLocation().getDiameter());
	}
	
	@Test
	void testHitBlockNotDestroyed() {
		//SuperChargedBall collide with SturdyBlock (not destroyed)
		b1.hitBlock(r1, false);
		assertEquals(4,b1.getLocation().getDiameter());
		assertEquals(new Vector(0,2),b1.getVelocity());
		
		//NormalBall collide with SturdyBlock (not destroyed)
		b2.hitBlock(r1, false);
		assertEquals(4,b2.getLocation().getDiameter());
		assertEquals(new Vector(0,2),b2.getVelocity());
	}

	@Test 
	void testHitPaddle() {
		b1.hitPaddle(r1, new Vector(10,0));
		assertEquals(new Vector(2,2),b1.getVelocity());
		assertEquals(c1.getCenter().getX(),b1.getLocation().getCenter().getX());
		assertEquals(104,b1.getLocation().getDiameter());
	}
	
	@Test
	void testHitWall() {
		b1.hitWall(r1);
		assertEquals(new Vector(0,2),b1.getVelocity());
		assertEquals(c1,b1.getLocation());
	}

	@Test
	void testGetLifetime() {
		assertEquals(5000,((SuperChargedBall)b1).getLifetime());
	}
	
	@Test //LEGIT
	void testMove() {
		b1.move(new Vector(0,2), 1);
		assertEquals(v1,b1.getVelocity());
		assertEquals(new Point(2,6), b1.getLocation().getCenter());
	}
	
	@Test
	void testCloneWithVelocity() {
		b1.cloneWithVelocity(v1);
		assertEquals(new Vector(0,-2),b1.getVelocity());
		assertEquals(c1,b1.getLocation());
	}
	
	@Test
	void testBackToNormal() { //stay in SuperChargedBall state
		b1.backToNormal();
		assertInstanceOf(SuperChargedBall.class,b1);
		assertEquals(v1,b1.getVelocity());
		assertEquals(4,b1.getLocation().getDiameter());
	}

	@Test
	void testBackToNormal2() { //back to NormalBall state
		Ball SuperBall = new SuperChargedBall( new Circle(p3, 800),v1,-2);
		SuperBall.backToNormal();
		assertInstanceOf(NormalBall.class,SuperBall);
		assertEquals(800,SuperBall.getLocation().getDiameter());
		assertEquals(Constants.INIT_BALL_DIAMETER,SuperBall.backToNormal().getLocation().getDiameter());
	}

}
