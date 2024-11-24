package breakout;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import breakout.utils.Point;
import breakout.utils.Rect;

class ReplicatingPaddleStateTest {

	Point p; 
	ReplicatingPaddleState p2; 
	ReplicatingPaddleState p3; 

	@BeforeEach
	void setUp() throws Exception {
		p = new Point(2000,1000);
		p2 = new ReplicatingPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0],2); 
		p3 = new ReplicatingPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0],3); 
	}

	@Test
	void testReplicatingPaddleState() {
		assertEquals(p,p2.getCenter());
		assertArrayEquals(Constants.TYPICAL_PADDLE_COLORS(),p2.getPossibleColors());
		assertEquals(Constants.TYPICAL_PADDLE_COLORS()[0],p2.getCurColor());
		assertEquals(2,p2.getCount());
	}
	
	@Test
	void testNumberOfBallsAfterHit() {
		assertEquals(2,p2.numberOfBallsAfterHit());
	}
	
	@Test
	void testGetCount() {
		assertEquals(2,p2.getCount());
	}

	@Test
	void testStateAfterHit() {
		PaddleState newPaddle = p3.stateAfterHit();
		assertEquals(ReplicatingPaddleState.class,newPaddle.getClass());
		assertEquals(p,newPaddle.getCenter());
		assertArrayEquals(Constants.TYPICAL_PADDLE_COLORS(),newPaddle.getPossibleColors());
		assertEquals(Constants.TYPICAL_PADDLE_COLORS()[0],newPaddle.getCurColor());
		assertEquals(2,((ReplicatingPaddleState)newPaddle).getCount());
		
		PaddleState newPaddle2 = p2.stateAfterHit();
		assertEquals(NormalPaddleState.class,newPaddle2.getClass());
		assertEquals(p,newPaddle2.getCenter());
		assertArrayEquals(Constants.TYPICAL_PADDLE_COLORS(),newPaddle2.getPossibleColors());
		assertEquals(true,Arrays.asList(p2.getPossibleColors()).contains(newPaddle2.getCurColor()));

	}

	@Test
	void testGetAcualColors() {
		assertEquals(p2.getCurColor(),p2.getActualColors()[0]);
		assertNotNull(Arrays.stream(p2.getActualColors()).parallel().findAny().get());
	}


	@Test
	void testReproduce() {
		assertEquals(true,p2.reproduce().equalContent(p2));
		assertNotSame(p2,p2.reproduce());
	}
	

	@Test
	void testEqualContent() {
		PaddleState normalPaddle = new NormalPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0]); 
		PaddleState replicatingPaddle1 = new ReplicatingPaddleState(p,Constants.TYPICAL_PADDLE_COLORS(),Constants.TYPICAL_PADDLE_COLORS()[0],2); 
		PaddleState replicatingPaddle2 = new ReplicatingPaddleState(new Point(1000,1000),new Color[] {Color.green, Color.red},Color.red,4); 

		assertEquals(true, p2.equalContent(replicatingPaddle1));
		assertEquals(false, replicatingPaddle1.equalContent(normalPaddle));
		assertEquals(false, replicatingPaddle1.equalContent(replicatingPaddle2));
		assertNotEquals(replicatingPaddle1,null);
				
	}
}
