package breakout.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CircleTest {
	Point p11;
	Point p25;
	Point p38;
	Point pm14;
	
	Circle c252;
	Circle c389;
	

	@BeforeEach
	void setUp() throws Exception {
		p11 = new Point(1,1);
		p25 = new Point(2,5);
		p38 = new Point(3,8);
		pm14 = new Point(-1,4);
		c252 = new Circle(p25,2);
		c389 = new Circle(p38,9);
	}

	@Test
	void testCircle() {
		assertEquals(p25,c252.getCenter());
		assertEquals(2, c252.getDiameter());
		assertEquals(p38, c389.getCenter());
		assertEquals(9, c389.getDiameter());
	}

	@Test
	void testGetRadius() {
		assertEquals(1,c252.getRadius());
		assertEquals(4, c389.getRadius());
	}

	@Test
	void testGetRightmostPoint() {
		assertEquals(new Point(3,5), c252.getRightmostPoint());
		assertEquals(new Point(7,8), c389.getRightmostPoint());
	}

	@Test
	void testGetLeftmostPoint() {
		assertEquals(new Point(1,5), c252.getLeftmostPoint());
		assertEquals(new Point(-1,8), c389.getLeftmostPoint());
	}

	@Test
	void testGetTopmostPoint() {
		assertEquals(new Point(2,4), c252.getTopmostPoint());
		assertEquals(new Point(3,4), c389.getTopmostPoint());
	}

	@Test
	void testGetBottommostPoint() {
		assertEquals(new Point(2,6), c252.getBottommostPoint());
		assertEquals(new Point(3,12), c389.getBottommostPoint());
	}

	@Test
	void testGetTopLeftPoint() {
		assertEquals(new Point(1,4), c252.getTopLeftPoint());
		assertEquals(new Point(-1,4), c389.getTopLeftPoint());
	}

	@Test
	void testGetBottomRightPoint() {
		assertEquals(new Point(3,6), c252.getBottomRightPoint());
		assertEquals(new Point(7,12), c389.getBottomRightPoint());
	}

	@Test
	void testGetOutermostPoint() {
		assertEquals(new Point(3,5), c252.getOutermostPoint(Vector.RIGHT));
		assertEquals(new Point(7,8), c389.getOutermostPoint(Vector.RIGHT));
		assertEquals(new Point(1,5), c252.getOutermostPoint(Vector.LEFT));
		assertEquals(new Point(-1,8), c389.getOutermostPoint(Vector.LEFT));
		assertEquals(new Point(2,4), c252.getOutermostPoint(Vector.UP));
		assertEquals(new Point(3,4), c389.getOutermostPoint(Vector.UP));
		assertEquals(new Point(2,6), c252.getOutermostPoint(Vector.DOWN));
		assertEquals(new Point(3,12), c389.getOutermostPoint(Vector.DOWN));
	}

	@Test
	void testWithCenter() {
		Circle c = c252.withCenter(p11);
		assertEquals(p11,c.getCenter());
		assertEquals(p25,c252.getCenter());
		assertEquals(2,c.getDiameter());
	}

}
