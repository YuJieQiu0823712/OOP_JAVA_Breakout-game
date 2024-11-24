package breakout;

import java.util.ArrayList;
import java.util.Arrays;
import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;


/**
 * Represents the current state of a breakout game.
 *  
 * @invar | getBalls() != null
 * @invar | getBlocks() != null
 * @invar | getPaddle() != null
 * @invar | Arrays.stream(getBalls()).allMatch(b -> b != null)
 * @invar | Arrays.stream(getBlocks()).allMatch(b -> b != null)
 * @invar | getBottomRight() != null
 * @invar | Constants.ORIGIN.isUpAndLeftFrom(getBottomRight())
 * @invar | Arrays.stream(getBalls()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | Arrays.stream(getBlocks()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | getField().contains(getPaddle().getLocation())
 */
public class BreakoutState {

	
	/**
	 * @invar | bottomRight != null
	 * @invar | Constants.ORIGIN.isUpAndLeftFrom(bottomRight)
	 */
	private final Point bottomRight;
	/**
	 * @invar | balls != null
	 * @invar | Arrays.stream(balls).allMatch(b -> b != null)
	 * @invar | Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 * @representationObjects
	 */
	private Ball[] balls;	
	/**
	 * @invar | blocks != null
	 * @invar | Arrays.stream(blocks).allMatch(b -> b != null)
	 * @invar | Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 */
	private BlockState[] blocks;
	/**
	 * @invar | paddle != null
	 * @invar | getFieldInternal().contains(paddle.getLocation())
	 */
	private PaddleState paddle;

	private final Rect topWall;
	private final Rect rightWall;
	private final Rect leftWall;
	private final Rect[] walls;

	/**
	 * Construct a new BreakoutState with the given balls, blocks, paddle.
	 * 
	 * @throws IllegalArgumentException | balls == null
	 * @throws IllegalArgumentException | blocks == null
	 * @throws IllegalArgumentException | bottomRight == null
	 * @throws IllegalArgumentException | paddle == null
	 * @throws IllegalArgumentException | !Arrays.stream(balls).allMatch(b -> b != null)
	 * @throws IllegalArgumentException | !Arrays.stream(blocks).allMatch(b -> b != null)
	 * @throws IllegalArgumentException | !Constants.ORIGIN.isUpAndLeftFrom(bottomRight)
	 * @throws IllegalArgumentException | !(new Rect(Constants.ORIGIN,bottomRight)).contains(paddle.getLocation())
	 * @throws IllegalArgumentException | !Arrays.stream(blocks).allMatch(b -> (new Rect(Constants.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @throws IllegalArgumentException | !Arrays.stream(balls).allMatch(b -> (new Rect(Constants.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @inspects | balls
	 * @inspects | blocks
	 * @post | Arrays.equals(getBalls(),balls)
	 * @post | Arrays.equals(getBlocks(),blocks)
	 * @post | getBottomRight().equals(bottomRight)
	 * @post | getPaddle().equalContent(paddle)
	 */
	public BreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		if( balls == null) throw new IllegalArgumentException();
		if( blocks == null) throw new IllegalArgumentException();
		if( bottomRight == null) throw new IllegalArgumentException();
		if( paddle == null) throw new IllegalArgumentException();
		
		if (Arrays.stream(balls).anyMatch(b -> b == null)) throw new IllegalArgumentException();
		if (Arrays.stream(blocks).anyMatch(b -> b == null)) throw new IllegalArgumentException();

		if(!Constants.ORIGIN.isUpAndLeftFrom(bottomRight)) throw new IllegalArgumentException();
		this.bottomRight = bottomRight;
		if(!getFieldInternal().contains(paddle.getLocation())) throw new IllegalArgumentException();
		if(!Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();
		if(!Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();
	
		// deep copy in
	    this.balls= new Ball[balls.length];
	    for (int i = 0 ; i < balls.length ; i++) {
	    	this.balls[i] = balls[i].clone();
	    }
		this.blocks = blocks.clone(); // copy
		this.paddle = paddle;
//		this.paddle = paddle.reproduce(); // copy

		this.topWall = new Rect( new Point(0,-1000), new Point(bottomRight.getX(),0));
		this.rightWall = new Rect( new Point(bottomRight.getX(),0), new Point(bottomRight.getX()+1000,bottomRight.getY()));
		this.leftWall = new Rect( new Point(-1000,0), new Point(0,bottomRight.getY()));
		this.walls = new Rect[] {topWall,rightWall, leftWall };
	}

	/**
	 * Return the balls of this BreakoutState.
	 * 
	 * @creates | result, ...result
	 */
	public Ball[] getBalls() {
		//deep copy out
		Ball[] res = new Ball[balls.length];
		for (int i = 0 ; i < balls.length ; i++) {
			res[i] = balls[i].clone();
		}
		return res;
	}

	/**
	 * Return the blocks of this BreakoutState. 
	 *
	 * @creates | result
	 */
	public BlockState[] getBlocks() {
		return blocks.clone();
	}

	/**
	 * Return the paddle of this BreakoutState. 
	 * 
	 * @creates | result
	 */
	public PaddleState getPaddle() {
		return paddle;
//		return paddle.clone();
	}

	/**
	 * Return the point representing the bottom right corner of this BreakoutState.
	 * The top-left corner is always at Coordinate(0,0). 
	 */
	public Point getBottomRight() {
		return bottomRight;
	}

	// internal version of getField which can be invoked in partially inconsistent states
	private Rect getFieldInternal() {
		return new Rect(Constants.ORIGIN, bottomRight);
	}
	
	/**
	 * Return a rectangle representing the game field.
	 * @post | result != null
	 * @post | result.getTopLeft().equals(Constants.ORIGIN)
	 * @post | result.getBottomRight().equals(getBottomRight())
	 */
	public Rect getField() {
		return getFieldInternal();
	}
	
	private void bounceWalls(Ball ball) {
		for (Rect wall : walls) {
			ball.hitRect(wall);
		}
	}
	

	private Ball removeDead(Ball ball) {
		if( ball.getLocation().getBottommostPoint().getY() > bottomRight.getY()) { return null; }
		else { return ball; }
	}

	private void clampBall(Ball b) {
		Circle loc = getFieldInternal().constrain(b.getLocation());
		b.setLocation(loc);
	}
	
	/**
	 * TODO
	 * Returns the ball state based on whether or not hit the block and the block is destroyed.
	 * If ball hit the block and speed is larger than threshold, the ball velocity changed and the block is destroyed.
	 * If ball hit the sturdy block with livesLeft = 1, with high enough speed, ball's velocity is not modified.
	 * The paddle state changed to replicating paddle state if the ball hit the replicator block. 
	 * 
	 * @pre | ball != null
	 * @mutates | ball 
	 * @mutates | this
	 * @post | result != null
	 * @post | result.getCenter().equals(old( ball.getCenter() ))
	 * @post | result.getVelocity().equals(old(ball.getVelocity())) || ! result.getVelocity().equals(old(ball.getVelocity()))
	 */
	private Ball collideBallBlocks(Ball ball) {
		for(BlockState block : blocks) {
			if(ball.collidesWith(block.getLocation())) {
				boolean destroyed = hitBlock(block,ball.getVelocity().getSquareLength());
				ball.hitBlock(block.getLocation(),destroyed);
				paddle = block.paddleStateAfterHit(paddle);
				return block.ballStateAfterHit(ball);
			}
		}
		return ball;
	}

	/**
	 * TODO
	 * 
	 * "Hits" the block argument once with a ball having speed sqrt(squaredSpeed).
	 * This can result in the block being destructed, i.e. not tracked in getBlocks() (returns ture in that case).
	 * Or no destruction occurs; in this case the block is updated instead of being removed. (returns false in that case).
	 * 
	 * Does not affect the balls.
	 * 
	 * @pre | block != null
	 * @pre | squaredSpeed >= 0
	 * @mutates | this
	 * @post | getBlocks().length <= old(getBlocks().length)
	 * @post | result == (block.blockStateAfterHit(squaredSpeed) == null) ? true : false
	 * @creates | result
	 */
	private boolean hitBlock(BlockState block, int squaredSpeed) {
		boolean destroyed = true;
		ArrayList<BlockState> nblocks = new ArrayList<BlockState>();
		for(BlockState b : blocks) {
			if(b != block) {
				nblocks.add(b);
			}else {
				BlockState bs = block.blockStateAfterHit(squaredSpeed);
				if(bs != null) {
					nblocks.add(bs);
					destroyed = false;
					
				}
			}
		}
		blocks = nblocks.toArray(new BlockState[nblocks.size()]);
		return destroyed;
	}
	
	/**
	 * Return the new state of the paddle after it is hit by a ball.
	 * Change number of replicated balls and set their initial location.
	 * 
	 * @pre | ball != null
	 * @pre | paddleVel.equals(Constants.PADDLE_VEL.scaled(-1)) || 
	 *      | paddleVel.equals(Constants.PADDLE_VEL.scaled(0)) ||
	 *      | paddleVel.equals(Constants.PADDLE_VEL.scaled(1))
	 * @mutates | ball
	 * @mutates | this
	 * @post | ball.getCenter().equals(old( ball.getCenter() ))
	 * @post | ball.getVelocity().equals(old(ball.getVelocity())) || ! ball.getVelocity().equals(old(ball.getVelocity()))
	 * @post | getBalls().length >= old(getBalls().length)
	 * @creates | result
	 */
	private void collideBallPaddle(Ball ball, Vector paddleVel) {
		
		if (ball.collidesWith(paddle.getLocation())) {
			ball.hitPaddle(paddle.getLocation(),paddleVel);
			int nrBalls = paddle.numberOfBallsAfterHit();
			if(nrBalls > 1) {
				Ball[] curballs = balls;
				balls = new Ball[curballs.length + nrBalls - 1];
				for(int i = 0; i < curballs.length; i++) {//i++
					balls[i] = curballs[i];
//					balls[i] = curballs[i].clone(); //deep copy
				}
				for(int i = 1; i < nrBalls; ++i) { //i++
					Ball newBall = new NormalBall( ball.getLocation() , ball.getVelocity() );
					newBall .setPosition( ball.getCenter() .plus( new Vector(i * 500 , 0)));
					balls[curballs.length + i -1] = newBall;
					
//					Ball newBall = ball.clone(); //deep copy
//					newBall.setPosition(Constants.REPL_SOURCE[i-1]);
//					balls[curballs.length + i -1] = newBall.cloneWithVelocity(ball.getVelocity());	
				}
			}
			paddle = paddle.stateAfterHit();
		}
	}

	/**
	 * LEGIT
	 * 
	 * Move all moving objects one step forward. please prefer tickDuring.
	 * 
	 * @pre | paddleDir == -1 || paddleDir == 0 || paddleDir == 1
	 * 
	 * @pre | elapsedTime >= 0
	 * @pre | elapsedTime <= Constants.MAX_ELAPSED_TIME
	 * 
	 * @mutates | this
	 * @mutates | ...getBalls()
	 */
	void tick(int paddleDir, int elapsedTime) {
		//the comments indicate some events that may occur in each call.
		stepBalls(elapsedTime); //move balls a bit
		bounceBallsOnWalls(); //ball velocities may change
		removeDeadBalls(); //remove balls out of field.
		bounceBallsOnBlocks(); //blocks may be affected and ball may be affected/replaced (but not their center). 
		bounceBallsOnPaddle(paddleDir); //balls velocities may change, and new balls may be created.
		clampBalls(); //for balls to remain within field.
		balls = Arrays.stream(balls).filter(x -> x != null).toArray(Ball[]::new); //get rid of null balls.
		noLongerSuperCharged(); //useless supercharged instances replaced with normal balls.
	}
	
	/**
	 * LEGIT
	 * 
	 * ticking by 20ms increments
	 */
	public void tickDuring(int elapsedTime) {
		for (int i = 0 ; i + 20 <= elapsedTime ; i += 20) {
			tick(0, 20);
		}
		if( elapsedTime % 20 != 0) { 
		  tick(0, elapsedTime % 20);
		}
	}

	/**
	 * LEGIT
	 */
	private void clampBalls() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				clampBall(balls[i]);
			}		
		}
	}

	/**
	 * LEGIT
	 * 
	 * @pre | paddleDir == -1 || paddleDir == 0 || paddleDir == 1
	 */
	private void bounceBallsOnPaddle(int paddleDir) {
		Vector paddleVel = Constants.PADDLE_VEL.scaled(paddleDir);
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				collideBallPaddle(balls[i], paddleVel);
			}
		}
	}

	/**
	 * LEGIT
	 */
	private void bounceBallsOnBlocks() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				balls[i] = collideBallBlocks(balls[i]);
			}
		}
	}

	/**
	 * LEGIT
	 */
	private void removeDeadBalls() {
		for(int i = 0; i < balls.length; ++i) {
			balls[i] = removeDead(balls[i]);
		}
	}

	/**
	 * LEGIT
	 */
	private void bounceBallsOnWalls() {
		for(int i = 0; i < balls.length; ++i) {
			bounceWalls(balls[i]);
		}
	}

	/**
	 * LEGIT
	 */
	private void stepBalls(int elapsedTime) {
		for(int i = 0; i < balls.length; ++i) {
			balls[i].move(balls[i].getVelocity().scaled(elapsedTime), elapsedTime);
		}
	}
	
	/**
	 * Replaces SuperChargedBalls having lifeTime’s <= 0 with normal balls (with normal diameter).
	 * 
	 * @mutates | this
	 */
	private void noLongerSuperCharged() {
		for (int i = 0 ; i < balls.length ; i++) {
			balls[i] = balls[i].clone();
//			balls[i] = balls[i].backToNormal();
		}
	}

	/**
	 * Move the paddle right.
	 * LEGIT
	 * 
	 * @mutates | this
	 */
	public void movePaddleRight(int elapsedTime) {
		paddle.move(Constants.PADDLE_VEL.scaled(elapsedTime), getField());
	}

	/**
	 * Move the paddle left.
	 * LEGIT
	 * 
	 * @mutates | this
	 */
	public void movePaddleLeft(int elapsedTime) {
		paddle.move(Constants.PADDLE_VEL.scaled(-elapsedTime), getField());
	}

	/**
	 * LEGIT
	 * Return whether this BreakoutState represents a game where the player has won.
	 * 
	 * @post | result == (getBlocks().length == 0 && !isDead())
	 * @inspects | this
	 */
	public boolean isWon() {
		return getBlocks().length == 0 && !isDead();
	}

	/**
	 * LEGIT
	 * Return whether this BreakoutState represents a game where the player is dead.
	 * 
	 * @post | result == (getBalls().length == 0)
	 * @inspects | this
	 */
	public boolean isDead() {
		return getBalls().length == 0;
	}
}
