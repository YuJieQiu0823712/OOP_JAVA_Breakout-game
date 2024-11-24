package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

class BreakoutStateTest {

	private Point BR;
	private BlockState ablock;
	private BlockState[] someblocks;
	private Ball aball;
	private Ball[] someballs;
	private PaddleState apad;
	private Point bottomRight;
	private BreakoutState abreakoutState;
	private Point p;
	private Vector v;
	
	@BeforeEach
	void setUp() throws Exception {
		BR = new Point(Constants.WIDTH, Constants.HEIGHT);//50000 30000
		
		ablock = new NormalBlockState(new Rect(Constants.ORIGIN, 
				new Point(Constants.BLOCK_WIDTH,Constants.BLOCK_HEIGHT)));//5000,3750
		
		someblocks = new BlockState[] {ablock};
		
		apad = new NormalPaddleState(
				new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
				Constants.TYPICAL_PADDLE_COLORS(),
				Constants.TYPICAL_PADDLE_COLORS()[0]); //25000 22500
		
		aball =	new NormalBall(
					new Circle(
						new Point(BR.getX() / 2 , Constants.HEIGHT / 2)
						, Constants.INIT_BALL_DIAMETER)
					, Constants.INIT_BALL_VELOCITY);
		
		someballs = new Ball[] { aball };
		
		bottomRight = new Point(Constants.WIDTH, Constants.HEIGHT);
		
		abreakoutState = new BreakoutState(someballs,someblocks,bottomRight,apad);
		
		p = new Point(BR.getX() / 2 , Constants.HEIGHT / 2);
		
		v = Constants.INIT_BALL_VELOCITY;
	}

	
	@Test
	void testThrowsError() {
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(null,null,null,null));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(null,someblocks,bottomRight,apad));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(someballs,null,bottomRight,apad));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(someballs,someblocks,null,apad));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(someballs,someblocks,bottomRight,null));
		assertDoesNotThrow(() -> new BreakoutState(someballs,someblocks,bottomRight,apad));
		
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(new Ball[] {null},someblocks,bottomRight,null));
		assertThrows(IllegalArgumentException.class, () -> new BreakoutState(someballs,new BlockState[] {null},bottomRight,null));	
	}
	
	@Test
	void testGetters() {
		assertArrayEquals(someballs, abreakoutState.getBalls());
		assertArrayEquals(someblocks, abreakoutState.getBlocks());
		assertTrue(abreakoutState.getPaddle().equalContent(apad));
		assertArrayEquals(apad.getPossibleColors(),abreakoutState.getPaddle().getPossibleColors());
		assertEquals(bottomRight,abreakoutState.getBottomRight());
		assertEquals(1, abreakoutState.getBalls().length);
		assertEquals(1, abreakoutState.getBlocks().length);
		
		abreakoutState.tickDuring(1);
		
		//ball is moved by one step: ball center (point) plus ball velocity (vector).
		assertTrue(abreakoutState.getBalls()[0].getCenter().equals(p.plus(v.scaled(1))));
		assertEquals(new Point(25005,15003),abreakoutState.getBalls()[0].getCenter());
		assertEquals(new Vector(5,3),abreakoutState.getBalls()[0].getVelocity());

		//paddle is not moved.
		assertEquals(apad.getCenter(),abreakoutState.getPaddle().getCenter());
		assertEquals(new Point(25000,22500),abreakoutState.getPaddle().getCenter());
		assertArrayEquals(apad.getPossibleColors(),abreakoutState.getPaddle().getPossibleColors());	

		//block is not moved.
		assertEquals(someblocks[0].getLocation().getTopLeft(),abreakoutState.getBlocks()[0].getLocation().getTopLeft());
		assertEquals(someblocks[0].getLocation().getBottomRight(),abreakoutState.getBlocks()[0].getLocation().getBottomRight());
	}
	
	@Test
	void testBounceWalls() { // ball moves up and bounce on wall
		Ball ball = new NormalBall(new Circle(new Point(2,2),1), new Vector(0,-2));
		Ball[] balls = new Ball[] {ball};
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,apad);
		
		breakoutState.tickDuring(1);
		assertEquals(new Point(2,0),breakoutState.getBalls()[0].getCenter());
		assertEquals(new Vector(0,2),breakoutState.getBalls()[0].getVelocity());
	}

	@Test
	void testRemoveDead() {
		Ball ball = new NormalBall(new Circle(new Point(25000,30000-20),1), new Vector(0,1));
		Ball[] balls = new Ball[] {ball};
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,apad);
		
		breakoutState.tickDuring(21);
		assertEquals(0,breakoutState.getBalls().length);
		assertEquals(1,breakoutState.getBlocks().length);
		assertEquals(true,breakoutState.isDead());
		assertEquals(false,breakoutState.isWon());
	}
	
	@Test
	void testCollideBallBlocks() { // ball moves up and collide with block
		Ball ball = new NormalBall(new Circle(new Point(2500,3750+2),1), new Vector(0,-2));
		Ball[] balls = new Ball[] {ball};
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,apad);
		
		breakoutState.tickDuring(1);
		assertEquals(0,breakoutState.getBlocks().length);
		assertEquals(1,breakoutState.getBalls().length);
		assertEquals(false,breakoutState.isDead());
		assertEquals(true,breakoutState.isWon());
	}
	
	@Test
	void testHitBlock() { // ball moves up and collide with SturdyBlock-bottomRight(5000,3750)
		Ball ball = new NormalBall(new Circle(new Point(7500,3750+2),1), new Vector(0,-2));
		Ball[] balls = new Ball[] {ball};
		BlockState SturdyBlock = new SturdyBlockState(
				new Rect(new Point(5000,0), new Point(10000,3750)),3);
		
		BlockState[] someblocks = new BlockState[] {ablock,SturdyBlock};
		BreakoutState breakoutState = new BreakoutState(balls,someblocks,bottomRight,apad);
		
		breakoutState.tickDuring(2);
		assertEquals(2,breakoutState.getBlocks().length);
		assertEquals(1,breakoutState.getBalls().length);
		assertEquals(false,breakoutState.isDead());
		assertEquals(false,breakoutState.isWon());
	}
	
	@Test
	void testCollideBallPaddle1() { //paddle center:(25000,22500) H:250 W:1500 
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
		assertEquals(new Point( Constants.WIDTH / 2 , (3 * Constants.HEIGHT) / 4),breakoutState.getPaddle().getCenter());
		assertArrayEquals(Constants.TYPICAL_PADDLE_COLORS(),breakoutState.getPaddle().getPossibleColors());
		assertEquals(breakoutState.getPaddle().getCurColor(),breakoutState.getPaddle().getActualColors()[0]);
		assertTrue(Arrays.asList(pad.getPossibleColors()).contains(breakoutState.getPaddle().getCurColor()));
	}
	
	@Test
	void testCollideBallPaddle2() { //paddle center:(25000,22500) H:250 W:1500 
		//ball collide with ReplicatingPaddle (count=3)
		Ball ball = new NormalBall(new Circle(new Point(25000,22500-130),2), new Vector(0,2));
		Ball SuperChargedBall = new SuperChargedBall(new Circle(new Point(25000,22500+130),2), new Vector(0,-2),10000);
		Ball[] balls = new Ball[] {ball,SuperChargedBall};
		PaddleState ReplicatingPad = new ReplicatingPaddleState(
				new Point( Constants.WIDTH / 2, (3 * Constants.HEIGHT) / 4),
				Constants.TYPICAL_PADDLE_COLORS(),
				Constants.TYPICAL_PADDLE_COLORS()[0],3); 
		BreakoutState breakoutState2 = new BreakoutState(balls,someblocks,bottomRight,ReplicatingPad);
		
		breakoutState2.tickDuring(20);
		assertEquals(ReplicatingPaddleState.class,breakoutState2.getPaddle().getClass());
		assertEquals(new Point( Constants.WIDTH / 2 , (3 * Constants.HEIGHT) / 4),breakoutState2.getPaddle().getCenter());
		assertArrayEquals(Constants.TYPICAL_PADDLE_COLORS(),breakoutState2.getPaddle().getPossibleColors());
		assertArrayEquals(Constants.TYPICAL_PADDLE_COLORS(),breakoutState2.getPaddle().getActualColors());
		assertTrue(Arrays.asList(ReplicatingPad.getPossibleColors()).contains(breakoutState2.getPaddle().getCurColor()));
		assertEquals(2,((ReplicatingPaddleState)breakoutState2.getPaddle()).getCount());
	}
	
	@Test
	void testMovePaddleRight() {
		BreakoutState breakoutSate = new BreakoutState(someballs,someblocks,bottomRight,apad);
		breakoutSate.movePaddleRight(1);
		assertEquals(new Point( Constants.WIDTH / 2 + 15, (3 * Constants.HEIGHT) / 4),breakoutSate.getPaddle().getCenter());
	}
	
	@Test
	void testMovePaddleLeft() {
		BreakoutState breakoutSate = new BreakoutState(someballs,someblocks,bottomRight,apad);
		breakoutSate.movePaddleLeft(1);
		assertEquals(new Point( Constants.WIDTH / 2 - 15, (3 * Constants.HEIGHT) / 4),breakoutSate.getPaddle().getCenter());		
	}


}
