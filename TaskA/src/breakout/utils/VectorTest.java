package breakout.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VectorTest {
	Vector v00;
	Vector v34;
	Vector vm18;
	

	@BeforeEach
	void setUp() throws Exception {
		v00 = new Vector(0,0);
		v34 = new Vector(3,4);
		vm18 = new Vector(-1,8);
	}

	@Test
	void testVector() {
		assertEquals(0, v00.getX());
		assertEquals(0, v00.getY());
		assertEquals(3, v34.getX());
		assertEquals(4, v34.getY());
		assertEquals(-1, vm18.getX());
		assertEquals(8, vm18.getY());
	}

	@Test
	void testEqualsObject() {
		assertEquals(v00,new Vector(0,0));
		assertNotEquals(v00, v34);
		assertEquals(v00,v00);
		assertNotEquals(vm18, null);
	}

	@Test
	void testScaled() {
		assertEquals(v00,v00.scaled(3));
		assertEquals(new Vector(-6,-8), v34.scaled(-2));
		assertEquals(new Vector(1,-8), vm18.scaled(-1));
	}

	@Test
	void testPlus() {
		assertEquals(v00,v00.plus(v00));
		assertEquals(v34, v34.plus(v00));
		assertEquals(v34, v00.plus(v34));
		assertEquals(new Vector(2,12), v34.plus(vm18));
	}

	@Test
	void testMinus() {
		assertEquals(v00,v00.minus(v00));
		assertEquals(v34, v34.minus(v00));
		assertEquals(new Vector(-3,-4), v00.minus(v34));
		assertEquals(new Vector(4,-4), v34.minus(vm18));
	}

	@Test
	void testToString() {
		assertEquals("(0,0)",v00.toString());
		assertEquals("(-1,8)",vm18.toString());
	}

	@Test
	void testProduct() {
		assertEquals(0,v00.product(v34));
		assertEquals(0,v00.product(vm18));
		assertEquals(29,v34.product(vm18));
	}

	@Test
	void testGetSquareLength() {
		assertEquals(0, v00.getSquareLength());
		assertEquals(25, v34.getSquareLength());
		assertEquals(65, vm18.getSquareLength());
	}

	@Test
	void testMirrorOver() {
		assertEquals(v00,v00.mirrorOver(Vector.DOWN));
		assertEquals(new Vector(3,-4),v34.mirrorOver(Vector.DOWN));
		assertEquals(new Vector(3,-4),v34.mirrorOver(Vector.UP));
		assertEquals(new Vector(1,8),vm18.mirrorOver(Vector.LEFT));
	}
	@Test
	void testScaledDiv() {
		assertEquals(v00,v00.scaledDiv(3));
		assertEquals(new Vector(-1,-2), v34.scaledDiv(-2));
		assertEquals(new Vector(1,-8), vm18.scaledDiv(-1));
	}


}
