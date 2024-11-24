package breakout;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import breakout.BlockState;
import breakout.BreakoutState;
import breakout.GameMap;
import breakout.PaddleState;
import breakout.utils.*;

/**
 * Those tests should fail on the provided bad implementation, succeed on the model solution.
 */
class TaskBTestSuite {
	
	private Point BR;
	private BlockState ablock;
	private BlockState[] someblocks;
	private Ball aball;
	private Ball[] someballs;
	private PaddleState apad;
	private Point bottomRight;
	private BreakoutState abreakoutState;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	/**
	 * some basic values to help us write tests
	 */
	void setUp() {
		BR = new Point(Constants.WIDTH, Constants.HEIGHT);//50000 30000
		
		ablock = new NormalBlockState(
				new Rect( Constants.ORIGIN, new Point(Constants.BLOCK_WIDTH,Constants.BLOCK_HEIGHT)) );//5000 3750
		someblocks = new BlockState[] { ablock };
		
		apad = new NormalPaddleState(
				new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
				Constants.TYPICAL_PADDLE_COLORS(),
				Constants.TYPICAL_PADDLE_COLORS()[0]);//25000 22500
		
		aball =	new NormalBall(
					new Circle(
						new Point(BR.getX() / 2 , Constants.HEIGHT / 2)
						, Constants.INIT_BALL_DIAMETER)
					, Constants.INIT_BALL_VELOCITY);
		someballs = new Ball[] { aball };
		
		bottomRight = new Point(Constants.WIDTH, Constants.HEIGHT);
		
		abreakoutState = new BreakoutState(someballs,someblocks,bottomRight,apad);
		
	}
	
	//SuperChargedBall
	@Test
	void testHitBlock() { //SuperCargedBall move up and collide with NormalBlock
		//when SuperCargedBall's diameter = Constants.INIT_BALL_DIAMETER + 100 (not +300)
		//The diameter of the ball needs to decrease by 100 each time it hits a block.
		Ball superBall = new SuperChargedBall(new Circle(new Point(2500,3750+460),Constants.INIT_BALL_DIAMETER + 200), new Vector(0,-20),5000);
		Ball[] balls = new Ball[] {superBall};
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,apad);
		
		breakoutState.tickDuring(1);
		assertEquals(0,breakoutState.getBlocks().length);
		assertEquals(superBall.getLocation().getDiameter()-100,breakoutState.getBalls()[0].getLocation().getDiameter());
		assertNotEquals(900,breakoutState.getBalls()[0].getLocation().getDiameter());	
	}
	
	@Test
	void testHitPaddle() { //paddle center:(25000,22500) H:250 W:1500 
		//Each time a SuperCargedBall hits the paddle, its diameter is increased by 100.
		Ball superBall = new SuperChargedBall(new Circle(new Point(25000,22500-580),700), new Vector(0,20),5000);
		Ball[] balls = new Ball[] {superBall};
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,apad);
		
		breakoutState.tickDuring(1); // paddle is not moved 
		assertEquals(superBall.getLocation().getDiameter()+100,breakoutState.getBalls()[0].getLocation().getDiameter());
		assertNotEquals(700,breakoutState.getBalls()[0].getLocation().getDiameter());	
	}
	
	//BlockState
	@Test
	void testNoEquals() {
		BlockState normalBlock1 = new NormalBlockState(new Rect( new Point(0,0), new Point(4,2)) );
		BlockState normalBlock2 = new NormalBlockState(new Rect( new Point(0,0), new Point(4,2)) );
		BlockState sturdyBlock = new SturdyBlockState(new Rect( new Point(4,0), new Point(8,2)),2 );
		
		assertEquals(normalBlock1,normalBlock2);
		assertEquals(false,normalBlock1.equals(sturdyBlock));
		assertEquals(false,normalBlock1.equals(null));
	}
	
	@Test
	void testNoHashCode() {
		BlockState normalBlock1 = new NormalBlockState(new Rect( new Point(0,0), new Point(4,2)) );
		BlockState normalBlock2 = new NormalBlockState(new Rect( new Point(0,0), new Point(4,2)) );
		BlockState sturdyBlock = new SturdyBlockState(new Rect( new Point(4,0), new Point(8,2)),2 );
		
		assertEquals(normalBlock1.hashCode(),normalBlock2.hashCode());
		assertNotEquals(normalBlock1.hashCode(),sturdyBlock.hashCode());
	}
	
	//ReplicatingPaddleState
	@Test
	void testConstructor() { // ball hit a ReplicatorBlock, NormalPaddle become RepicatingPaddle
		Ball[] balls = new Ball[] { Setups.typicalNormalBall(2) };
		BlockState[] blocks = Setups.typicalBlocks();
		BreakoutState breakoutState = new BreakoutState(balls,blocks,BR,apad);
		
		breakoutState.tickDuring(400);
		Color[] array1 = new Color[] {Color.green, Color.magenta, Color.orange };
		Color[] array2 = new Color[] {Color.orange, Color.magenta, Color.magenta };
		assertArrayEquals(array1,breakoutState.getPaddle().getPossibleColors());
		assertFalse(Arrays.equals(array2, breakoutState.getPaddle().getPossibleColors()));
	}
	
	@Test
	void testStateAfterHit1() { //paddle center:(25000,22500) H:250 W:1500 
		//ball collide with ReplicatingPaddle (count=2) -> turn into NormalPaddle
		Ball ball = new NormalBall(new Circle(new Point(25000,22500-130),2), new Vector(0,2));
		Ball SuperChargedBall = new SuperChargedBall(new Circle(new Point(25000,22500+130),2), new Vector(0,-2),10000);
		Ball[] balls = new Ball[] {ball,SuperChargedBall};
		PaddleState pad = new ReplicatingPaddleState(
				new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
				Constants.TYPICAL_PADDLE_COLORS(),
				Constants.TYPICAL_PADDLE_COLORS()[0],2);
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,pad);
		
		breakoutState.tickDuring(1);
		assertEquals(NormalPaddleState.class,breakoutState.getPaddle().getClass());
		assertNotEquals(ReplicatingPaddleState.class,breakoutState.getPaddle().getClass());
	}
	
	@Test
	void testStateAfterHit2() { //paddle center:(25000,22500) H:250 W:1500 
		//ball collide with ReplicatingPaddle (count=3) -> become ReplicatingPaddle (count=2)
		
		Ball ball = new NormalBall(new Circle(new Point(25000,22500-130),2), new Vector(0,2));
		Ball SuperChargedBall = new SuperChargedBall(new Circle(new Point(25000,22500+130),2), new Vector(0,-2),10000);
		Ball[] balls = new Ball[] {ball,SuperChargedBall};
		PaddleState ReplicatingPad = new ReplicatingPaddleState(
				new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
				Constants.TYPICAL_PADDLE_COLORS(),
				Constants.TYPICAL_PADDLE_COLORS()[0],3);
		BreakoutState breakoutState2 = new BreakoutState(balls,someblocks,bottomRight,ReplicatingPad);
		
		breakoutState2.tickDuring(1);
		assertEquals(ReplicatingPaddleState.class,breakoutState2.getPaddle().getClass());
		assertEquals(2,((ReplicatingPaddleState)breakoutState2.getPaddle()).getCount());	
	}
	
	//BreakoutState
	@Test
	void testConstructorPaddle() {		
		abreakoutState.getPaddle().setCenter(BR);
		assertSame(apad.getCenter(),abreakoutState.getPaddle().getCenter());
		
		// do not change the center of the original object => not cause representation leakage
		assertEquals(new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),abreakoutState.getPaddle().getCenter());
	}
	
	@Test
	void testCollideBallPaddle() {	//paddle center:(25000,22500) H:250 W:1500 
		//SuperChargedBall hit the ReplicatingPaddle (count=4)	
		Ball SuperChargedBall = new SuperChargedBall(new Circle(new Point(25000,22500+130),2), new Vector(0,-2),10000);
		Ball[] balls = new Ball[] {SuperChargedBall};
		PaddleState ReplicatingPad = new ReplicatingPaddleState(
				new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
				Constants.TYPICAL_PADDLE_COLORS(),
				Constants.TYPICAL_PADDLE_COLORS()[0],2); 
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,ReplicatingPad);
		
		breakoutState.tickDuring(1);

		assertEquals(2,((ReplicatingPaddleState)ReplicatingPad).getCount());
		assertEquals(SuperChargedBall.class,breakoutState.getBalls()[0].getClass());
		assertEquals(SuperChargedBall.class,breakoutState.getBalls()[1].getClass());
		assertNotEquals(NormalBall.class,breakoutState.getBalls()[1].getClass());
		assertEquals(new Point(25000, 19750),breakoutState.getBalls()[1].getLocation().getCenter());
	}
	
	@Test
	void testNoLongerSuperCharged() {		
		Ball SuperChargedBall = new SuperChargedBall(new Circle(new Point(25000,22500+130),2), new Vector(0,-2),1);
		Ball[] balls = new Ball[] {SuperChargedBall};
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,apad);
		
		breakoutState.tickDuring(1);

		assertEquals(NormalBall.class,breakoutState.getBalls()[0].getClass());
		assertNotEquals(SuperChargedBall.class,breakoutState.getBalls()[0].getClass());
		assertEquals(Constants.INIT_BALL_DIAMETER,breakoutState.getBalls()[0].getLocation().getDiameter());
		assertNotEquals(2,breakoutState.getBalls()[0].getLocation().getDiameter());
	}
}

