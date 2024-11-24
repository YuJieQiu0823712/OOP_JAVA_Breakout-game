package breakout;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import breakout.utils.*;

class SubmissionTestSuite {

	//fields for testing BreakoutState
	private Point BR;
	private BlockState ablock;
	private BlockState[] someblocks;
	private Ball aball;
	private Ball[] someballs;
	private PaddleState apad;
	
	@BeforeEach
	void setUp() {
		BR = new Point(Constants.WIDTH, Constants.HEIGHT);
		//top left of target normal block
		
		ablock = new NormalBlockState(
				new Rect( Constants.ORIGIN, new Point(Constants.BLOCK_WIDTH,Constants.BLOCK_HEIGHT)) );
		someblocks = new BlockState[] { ablock };
		
		apad = new NormalPaddleState(
				new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
				Constants.TYPICAL_PADDLE_COLORS(),
				Constants.TYPICAL_PADDLE_COLORS()[0]);
		
		aball =
				new NormalBall(
					new Circle(
						new Point(BR.getX() / 2 , Constants.HEIGHT / 2)
						, Constants.INIT_BALL_DIAMETER)
					, Constants.INIT_BALL_VELOCITY);
		someballs = new Ball[] { aball };
	}
	
		
		
	@Test
	void sanity() {
		Ball[] balls = new Ball[] { Setups.typicalNormalBall(4) }; //1.5 blocks from right wall
		BlockState[] blocks = Setups.typicalBlocks();
		
		BreakoutState bstate = new BreakoutState(
				balls,
				blocks,
				BR,
				apad);
		
		assertTrue( bstate.getBalls()[0].getVelocity().getX() == 7); //should hold because Setups must remain unchanged.
		assertEquals(1, bstate.getBalls().length);
		assertEquals(4, bstate.getBlocks().length);
		
		bstate.tickDuring( 400 );
		
		//ticking 400ms is not enough to bounce on the wall (1.5 blocks)
		//but enough to travel > 0.5 blocks.
		assertTrue( bstate.getBalls()[0].getVelocity().getX() >= 0 );  //vel. has not changed.
		
		//extra ~second is needed to bounce on the wall
		bstate.tickDuring(900);
		assertFalse( bstate.getBalls()[0].getVelocity().getX() >= 0 );
		
		assertEquals(1, bstate.getBalls().length);
		assertEquals(4, bstate.getBlocks().length);			
	}
	
	@Test
	void normalBlockGetsNormalBall() {
		Ball[] balls = new Ball[] { Setups.typicalNormalBall(0) };
		BlockState[] blocks = Setups.typicalBlocks();
		
		BreakoutState bstate = new BreakoutState(
				balls,
				blocks,
				BR,
				apad);
		
		//400 ms suffice for a slow ball to hit the block
		bstate.tickDuring(400);
		assertTrue( bstate.getBalls()[0].getVelocity().getX() <= 0);
		assertTrue( bstate.getBlocks().length == 3);
				
	}
	
	@Test
	void ReplPaddleGetsNormalBallAndSameVelocities() {
		//put ball right above the paddle
		aball.setLocation( new Circle(
				apad.getCenter().plus( new Vector( 0, - (Constants.BLOCK_HEIGHT / 2) )),
				Constants.INIT_BALL_DIAMETER ));
		// with down velocity
		aball.setVelocity( new Vector( 0 , 7 ));
		
		ReplicatingPaddleState replpad = new ReplicatingPaddleState(
				apad.getCenter(),
				Constants.TYPICAL_PADDLE_COLORS(),
				Constants.TYPICAL_PADDLE_COLORS()[0],
				4);
		
		BreakoutState bstate = new BreakoutState(
				someballs,
				someblocks,
				BR,
				replpad);
		
		assertTrue( bstate.getBalls()[0].getVelocity().getY() >= 0 ); //looking down
		
		bstate.tickDuring(400);
		
		assertTrue( bstate.getBalls().length == 4); //3 new balls have been generated.
		
		assertTrue( Arrays.stream(bstate.getBalls()).allMatch( 
		b -> b.getVelocity().equals( bstate.getBalls()[0].getVelocity() ) ));
		
	}
	
	@Test
	void PowerupBlockGetsNormalBall() {
		Ball[] balls = new Ball[] { Setups.typicalNormalBall(3) };
		BlockState[] blocks = Setups.typicalBlocks();
		
		BreakoutState bstate = new BreakoutState(
				balls,
				blocks,
				BR,
				apad);
		
		//400 ms suffice for a slow ball to hit the block
		bstate.tickDuring(400);
		assertTrue( bstate.getBalls()[0].getVelocity().getX() <= 0);
		assertTrue( bstate.getBlocks().length == 3);
		
		SuperChargedBall superBall = (SuperChargedBall) bstate.getBalls()[0];
		assertEquals( Constants.INIT_BALL_DIAMETER + 600 , superBall.getLocation().getDiameter() ); 
				
	}
	
	@Test
	void ReplBlockGetsNormalBall() {
		Ball[] balls = new Ball[] { Setups.typicalNormalBall(2) };
		BlockState[] blocks = Setups.typicalBlocks();
		
		BreakoutState bstate = new BreakoutState(
				balls,
				blocks,
				BR,
				apad);
		
		//400 ms suffice for a slow ball to hit the block
		bstate.tickDuring(400);
		assertTrue( bstate.getBalls()[0].getVelocity().getX() <= 0);
		assertTrue( bstate.getBlocks().length == 3);
		
		ReplicatingPaddleState rPad = (ReplicatingPaddleState) bstate.getPaddle(); //this should not throw exceptions
		assertEquals( 4, rPad.getCount() );
				
	}
	
	@Test
	void ReplBlockGetsSuperBall() {
		Ball[] balls = new Ball[] { Setups.typicalSuperBall(2) };
		BlockState[] blocks = Setups.typicalBlocks();
		
		BreakoutState bstate = new BreakoutState(
				balls,
				blocks,
				BR,
				apad);
		
		//400 ms suffice for a slow ball to hit the block
		bstate.tickDuring(400);
		assertTrue( bstate.getBalls()[0].getVelocity().getX() >= 0); //no change of vel.
		assertTrue( bstate.getBlocks().length == 3);
		
		ReplicatingPaddleState rPad = (ReplicatingPaddleState) bstate.getPaddle(); //this should not throw exceptions
				
	}


}
