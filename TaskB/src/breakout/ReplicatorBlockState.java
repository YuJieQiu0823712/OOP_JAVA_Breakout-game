package breakout;

import java.awt.Color;
import java.util.Arrays;

import breakout.utils.Rect;

/**
 * Represents the state of a replicatorBlock in the breakout game.
 * 
 * @immutable
 * @invar | getColor() != null
 */
public class ReplicatorBlockState extends NormalBlockState {

	/**
	 * @invar | COLOR != null
	 */
	private static final Color COLOR = new Color(100, 149, 237);//blue

	/**
	 * Construct a ReplicatorBlock occupying a given rectangle in the field.
	 * 
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	public ReplicatorBlockState(Rect location) {
		super(location);
	}

	@Override
	/**
	 * TODO
	 * Return a replicating paddle when ball hit the replicator block.
	 * 
	 * @pre | paddleState != null
	 * @post | result.getCenter().equals(paddleState.getCenter())
	 * @post | Arrays.equals(result.getPossibleColors(),paddleState.getPossibleColors())
	 * @post | result.getCurColor().equals(paddleState.getCurColor())
	 * @inspects | this
	 * @creates | result
	 */
	public PaddleState paddleStateAfterHit(PaddleState paddleState) {
		ReplicatingPaddleState replicatingPaddle = new ReplicatingPaddleState(paddleState.getCenter()
				               ,paddleState.getPossibleColors(),paddleState.getCurColor(),4);
		replicatingPaddle.getActualColors();
		return replicatingPaddle;
	}

	@Override
	public Color getColor() {
		return COLOR;
	}

}
