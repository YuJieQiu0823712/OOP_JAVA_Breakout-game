package breakout.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RectTest {
	Point p11;
	Point p25;
	Point p38;
	Point pm14;
	
	Rect r1138;
	Rect rm1438;
	Rect r1125;
	
	Circle c251;
	Circle c389;
	
	@BeforeEach
	void setUp() throws Exception {
		p11 = new Point(1,1);
		p25 = new Point(2,5);
		p38 = new Point(3,8);
		pm14 = new Point(-1,4);
		r1138 = new Rect(p11,p38);
		rm1438 = new Rect(pm14,p38);
		r1125 = new Rect(p11,p25);
		c251 = new Circle(p25,1);
		c389 = new Circle(p38,9);
	}

	@Test
	void testRect() {
		assertEquals(p11, r1138.getTopLeft());
		assertEquals(p38, r1138.getBottomRight());
	}

	@Test
	void testContainsPoint() {
		assertTrue(r1138.contains(p25));
		assertTrue(r1138.contains(p11));
		assertFalse(r1125.contains(pm14));
		assertTrue(rm1438.contains(p38));
	}

	@Test
	void testContainsCircle() {
		assertTrue(r1138.contains(c251));
		assertFalse(r1138.contains(c389));
		assertTrue(rm1438.contains(c251));
	}

	@Test
	void testContainsRect() {
		assertTrue(r1138.contains(r1125));
		assertTrue(r1138.contains(r1138));
		assertFalse(rm1438.contains(r1125));
		assertFalse(rm1438.contains(r1138));
	}

	@Test
	void testCollideWith() {
		Circle c = new Circle(new Point(2,0),2);
		Vector v = r1138.collideWith(c);
		assertEquals(Vector.DOWN,v);
		assertNull(rm1438.collideWith(c));
	}

	@Test
	void testMinusMarginIntInt() {
		Rect r = rm1438.minusMargin(0,1);
		assertEquals(new Point(-1,5),r.getTopLeft());
		assertEquals(new Point(3,7),r.getBottomRight());
		Rect r2 = rm1438.minusMargin(2,1);
		assertEquals(new Point(1,5),r2.getTopLeft());
		assertEquals(new Point(1,7),r2.getBottomRight());
	}

	@Test
	void testMinusMarginInt() {
		Rect r = rm1438.minusMargin(1);
		assertEquals(new Point(0,5),r.getTopLeft());
		assertEquals(new Point(2,7),r.getBottomRight());
		Rect r2 = rm1438.minusMargin(2);
		assertEquals(new Point(1,6),r2.getTopLeft());
		assertEquals(new Point(1,6),r2.getBottomRight());
	}

	@Test
	void testConstrain() {
		assertEquals (p25,r1138.constrain(p25));
		assertEquals (new Point(1,4),r1138.constrain(pm14));
	}

}
