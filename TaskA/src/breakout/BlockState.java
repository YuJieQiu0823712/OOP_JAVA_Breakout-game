package breakout;

import java.awt.Color;
import java.util.Objects;

import breakout.utils.Rect;

/**
 * Represents the state of a block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */
public abstract class BlockState {
	
	/**
	 * @invar | location != null
	 */
	private final Rect location;

	/**
	 * Construct a block occupying a given rectangle in the field.
	 * 
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	public BlockState(Rect location) {
		this.location = location;
	}

	/**
	 * Return the rectangle occupied by this block in the field.
	 */
	public Rect getLocation() {
		return location;
	}
	
	/**
	 * Returns the block state after getting hit by a ball (having speed = sqrt( squaredSpeed)),
	 * or null if block is destroyed.
	 * 
	 * @pre | squaredSpeed >= 0
	 * @post | (result == null || result.getLocation().equals(getLocation()))
	 */
	public abstract BlockState blockStateAfterHit(int squaredSpeed);

	/**
	 * Returns the ball state after "this" block  is hit by the incoming ball, based on the incoming ball
	 * state. This method is not intended to change the ball speed.
	 * 
	 * @pre | ballState != null
	 * @post | result != null && (result.getVelocity() .equals( ballState.getVelocity() ))
	 */
	public abstract Ball ballStateAfterHit(Ball ballState);

	/**
	 * Returns the paddle state after getting hit by the ball, based on the old
	 * paddle state.
	 * 
	 * @pre | paddleState != null
	 */
	public abstract PaddleState paddleStateAfterHit(PaddleState paddleState);

	/**
	 * Return this block state's color.
	 * 
	 * @post | result != null
	 */
	public abstract Color getColor();
	
//	@Override
//	/**
//	 * override equals method
//	 */
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		BlockState other = (BlockState) obj;
//		if (! getLocation().equals(other.getLocation() ) )
//			return false;
//		return true;
//	}
//	
//	
//	/**
//	 *Return the hashcode of the block
//	 * 
//	 * @inspects | this
//	 */
//	@Override
//	public int hashCode() {
//		return Objects.hash(location);
//	}
	
}
