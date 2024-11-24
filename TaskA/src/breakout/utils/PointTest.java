package breakout.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointTest {
	Point p34;
	Point p45;
	Point pm28;
	
	@BeforeEach
	void setUp() throws Exception {
		p34 = new Point(3,4);
		p45 = new Point(4,5);
		pm28 = new Point(-2,8);
	}

	@Test
	void testPoint() {
		assertEquals(3,p34.getX());
		assertEquals(4,p45.getX());
		assertEquals(-2,pm28.getX());
		assertEquals(4,p34.getY());
		assertEquals(5,p45.getY());
		assertEquals(8,pm28.getY());
	}

	@Test
	void testEqualsObject() {
		assertEquals(p34,p34);
		assertEquals(new Point(3,4),p34);
		assertNotEquals(p45,p34);
		assertNotEquals(null,p34);
	}

	@Test
	void testPlus() {
		assertEquals(p45,p34.plus(new Vector(1,1)));
		assertEquals(pm28,p34.plus(new Vector(-5,4)));
	}

	@Test
	void testMinus() {
		assertEquals(p45,p34.minus(new Vector(-1,-1)));
		assertEquals(pm28,p34.minus(new Vector(5,-4)));
	}

	@Test
	void testToString() {
		assertEquals(p34.toString(), "(3,4)");
	}

	@Test
	void testReflectVertical() {
		assertEquals(new Point(1,4),p34.reflectVertical(2));
		assertEquals(new Point(-2,8),pm28.reflectVertical(-2));
		assertEquals(new Point(-4,8),pm28.reflectVertical(-3));
	}

	@Test
	void testReflectHorizontal() {
		assertEquals(new Point(3,0),p34.reflectHorizontal(2));
		assertEquals(new Point(-2,-12),pm28.reflectHorizontal(-2));
		assertEquals(new Point(-2,10),pm28.reflectHorizontal(9));
	}

	@Test
	void testIsUpAndLeftFrom() {
		assertTrue(p34.isUpAndLeftFrom(p45));
		assertTrue(p34.isUpAndLeftFrom(p34));
		assertFalse(p34.isUpAndLeftFrom(pm28));
		assertFalse(pm28.isUpAndLeftFrom(p34));
	}

}
