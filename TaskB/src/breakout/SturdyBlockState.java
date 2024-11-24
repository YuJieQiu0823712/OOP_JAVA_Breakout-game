package breakout;

import java.awt.Color;
import breakout.utils.Rect;

/**
 * Represents the state of a sturdyBlock in the breakout game.
 * 
 * @immutable
 * @invar | getColor() != null
 * @invar | getLivesLeft() > 0 || getLivesLeft() <= 3
 */
public class SturdyBlockState extends BlockState {

	/**
	 * @invar | COLOR1 != null
	 * @invar | COLOR2 != null
	 * @invar | COLOR3 != null 
	 */
	private static final Color COLOR1 = new Color(160, 82, 45);
	private static final Color COLOR2 = new Color(123, 63, 0);
	private static final Color COLOR3 = new Color(92, 64, 51);
	
	/**
	 * @invar | livesLeft > 0 || livesLeft <= 3
	 */
	private final int livesLeft;

	/**
	 * Construct a SturdyBlock occupying a given rectangle in the field.
	 * 
	 * @pre | location != null
	 * @pre | lives > 0 || lives <= 3
	 * @post | getLocation().equals(location)
	 * @post | getLivesLeft()==lives
	 */
	public SturdyBlockState(Rect location, int lives) {
		super(location);
		livesLeft = lives;
	}

	public int getLivesLeft() {
		return livesLeft;
	}
	
	@Override
	/**
	 * TODO
	 * Return null (block is destroyed) when the ball (with sufficient speed) hit the sturdy block with 1 life left.
	 * If block has >1 lives left, the block loses a life each time a ball hit it.
	 * 
	 * @inspects | this
	 * @pre | squaredSpeed >= 0 
	 * @post | (result ==  null 
	 *       | || result.getLocation().equals(getLocation()))
	 *       | || ((SturdyBlockState)result).getLivesLeft() < old(getLivesLeft())
	 * @creates | result
	 */
	public BlockState blockStateAfterHit(int squaredSpeed) {
		if(livesLeft==1 && Math.sqrt(squaredSpeed) >= Constants.BALL_SPEED_THRESH ) {
			return null;
		}

		else {
			return new SturdyBlockState(getLocation(),livesLeft-1);
		}
	}

	@Override
	/**
	 * TODO
	 * Return the ball state when the ball hit the sturdy block according to 
	 * the speed of the ball and the livesLeft number of the SturdyBlock.
	 * 
	 * @pre | ballState != null
	 * @post | result != null
	 * @post | result.getVelocity().equals(ballState.getVelocity()) ||
	 *       | result.getVelocity().equals(old(ballState.getVelocity()).mirrorOver(this.getLocation().collideWith(ballState.getLocation())))
	 * @mutates | this
	 */
	public Ball ballStateAfterHit(Ball ballState) {
		
		int speed = (int)Math.sqrt(ballState.getVelocity().getSquareLength());
		if(livesLeft == 1 &&  speed >= Constants.BALL_SPEED_THRESH ) {
			ballState.setVelocity(ballState.getVelocity());
		}
		if(livesLeft == 1 && speed < Constants.BALL_SPEED_THRESH) {
			ballState.setVelocity(ballState.bounceOn(getLocation()));
		}
		return ballState;
	}

	@Override
	/**
	 * TODO
	 * Return the paddle when the ball hit the paddle.
	 * 
	 * @pre | paddleState != null
	 * @post | result == paddleState
	 */
	public PaddleState paddleStateAfterHit(PaddleState paddleState) {
		return paddleState;
	}

	@Override
	/**
	 * LEGIT
	 */
	public Color getColor() {
		switch (livesLeft) {
		case 1:
			return COLOR1;
		case 2:
			return COLOR2;
		default:
			return COLOR3;
		}
	}

}
