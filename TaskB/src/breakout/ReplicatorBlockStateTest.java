package breakout;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import breakout.utils.Point;
import breakout.utils.Rect;

class ReplicatorBlockStateTest {

	Point p1;
	Point p2;
	Rect r1;
	BlockState b1;

	@BeforeEach
	void setUp() throws Exception {
		p1 = new Point(0, 0);
		p2 = new Point(4, 2);
		r1 = new Rect(p1, p2);
		b1 = new ReplicatorBlockState(r1);
	}

	@Test
	void testReplicatorBlockState() {
		assertEquals(r1, b1.getLocation());
	}

	@Test
	void testPaddleStateAfterHit() {
		PaddleState paddle = new ReplicatingPaddleState(p1,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0],4); 
		assertEquals(ReplicatingPaddleState.class, b1.paddleStateAfterHit(paddle).getClass());
		assertEquals(paddle.getCenter(), b1.paddleStateAfterHit(paddle).getCenter());
	}

	@Test
	void testGetColor() {
		assertEquals(new Color(100, 149, 237), b1.getColor());
	}

}
