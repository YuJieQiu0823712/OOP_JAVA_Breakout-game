package breakout;

import java.awt.Color;
import breakout.utils.Rect;

/**
 * Represents the state of a normalBlock in the breakout game.
 * 
 * @immutable
 * @invar | getColor() != null
 */
public class NormalBlockState extends BlockState {

	/**
	 * @invar | COLOR != null
	 */
	private static final Color COLOR = new Color(128, 128, 128);//grey

	/**
	 * Construct a NormalBlock occupying a given rectangle in the field.
	 * 
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	public NormalBlockState(Rect location) {
		super(location);
	}

	@Override
	/**
	 * TODO
	 * Return null (block is destroyed) when the ball hit the normal block
	 * 
	 * @pre | squaredSpeed >= 0 
	 * @post | result == null
	 */
	public BlockState blockStateAfterHit(int squaredSpeed) {
		return null;
	}

	@Override
	/**
	 * TODO
	 * Return the ball when the ball hit the normal block
	 * 
	 * @inspects | this
	 * @pre | ballState != null
	 * @post | result.equals(ballState)
	 */
	public Ball ballStateAfterHit(Ball ballState) {
		return ballState;
	}

	@Override
	/**
	 * TODO
	 * Return the paddle when the ball hit the paddle
	 * 
	 * @inspects | this
	 * @pre | paddleState != null
	 * @post | result.equals(paddleState)
	 */
	public PaddleState paddleStateAfterHit(PaddleState paddleState) {
		return paddleState;
	}

	/**
	 * Return the color of the normalBlock.
	 */
	@Override
	public Color getColor() {
		return COLOR;
	}

}
