package breakout;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import breakout.utils.Point;
import breakout.utils.Rect;

class BlockStateTest {
	Point p1;
	Point p2;	
	Rect r1;
	BlockState b1;
	
	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(0,0);
		p2 = new Point(4,2);
		r1 = new Rect(p1,p2);
		b1 = new NormalBlockState(r1);
	}
	
	@Test
	void testBlockState() {
		assertEquals(new Rect(p1,p2),b1.getLocation());
	}
	
	@Test
	void testEquals() {
		BlockState normalBlock1 = new NormalBlockState(new Rect( new Point(0,0), new Point(4,2)) );
		BlockState normalBlock2 = new NormalBlockState(new Rect( new Point(0,0), new Point(4,2)) );
		BlockState normalBlock3 = new NormalBlockState(new Rect( new Point(5,5), new Point(10,10)) );
		BlockState sturdyBlock = new SturdyBlockState(new Rect( new Point(4,0), new Point(8,2)),2 );
		
		assertEquals(normalBlock1,normalBlock1);
		assertEquals(normalBlock1,normalBlock2);
		assertNotEquals(normalBlock1,normalBlock3);
		assertNotEquals(normalBlock1,sturdyBlock);
		assertNotEquals(normalBlock1,null);
	}
	
	@Test
	void testHashCode() {		
		BlockState normalBlock1 = new NormalBlockState(new Rect( new Point(0,0), new Point(4,2)) );
		BlockState normalBlock2 = new NormalBlockState(new Rect( new Point(0,0), new Point(4,2)) );
		BlockState sturdyBlock = new SturdyBlockState(new Rect( new Point(4,0), new Point(8,2)),2 );
		
		assertEquals(normalBlock1.hashCode(),normalBlock2.hashCode());
		assertNotEquals(normalBlock1.hashCode(),sturdyBlock.hashCode());
	}

}
