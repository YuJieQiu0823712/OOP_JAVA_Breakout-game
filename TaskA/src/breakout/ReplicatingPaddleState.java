package breakout;

import java.awt.Color;
import java.util.Arrays;

import breakout.utils.*;

/**
 * Represents the state of a paddle in the breakout game.
 * 
 * @mutable
 * @invar | getCount() > 1 || getCount() <= Constants.MAX_BALL_REPLICAS + 1
 */
public class ReplicatingPaddleState extends PaddleState {
	
	/**
	 * count = the number of balls that will be generated upon hitting this paddle + 1.
	 * 
	 * @invar | count > 1 || count <= Constants.MAX_BALL_REPLICAS + 1
	 */
	private int count;

	/**
	 * Returns the number of generated balls + 1 (the ball that is bouncing on the paddle).
	 * 
	 * @post | result > 1
	 * @post | result <=  Constants.MAX_BALL_REPLICAS + 1
	 */
	@Override
	public int numberOfBallsAfterHit() {
		return count;
	}
	
	/**
	 * Returns the remaining amount of ball replications this paddle will perform + 1
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Construct a replicatingPaddle located around a given center in the field.
	 * 
	 * @pre | center != null
	 * @pre | possCols != null
	 * @pre | possCols.length >= 1
	 * @pre | Arrays.stream(possCols).allMatch(e -> e != null)
	 * @pre | curColor != null
	 * @pre | Arrays.stream(possCols).anyMatch(e -> e.equals(curColor))
	 * @pre | count > 1 || count <= Constants.MAX_BALL_REPLICAS + 1
	 * @post | getCenter().equals(center)
	 * @post | Arrays.equals(getPossibleColors(),possCols)
	 * @post | getCurColor().equals(curColor)
	 * @post | getCount() == count
	 */
	public ReplicatingPaddleState(Point center, Color[] possCols, Color curColor, int count) {
		super(center,
				new Color[] { possCols[2], possCols[1], possCols[1] },
				curColor);
		this.count = count;
//		super(center,possCols,curColor);
//		this.count = count;
	}

	@Override
	/**
	 * Return the new state of the replicatingPaddle after it is hit by a ball.
	 * 
	 * @mutates | this
	 * @post | result != null
	 * @post | result.getLocation().equals(old(getLocation()))
	 * @post | result.getCenter().equals(old(getCenter()))
	 * @post | Arrays.equals(result.getPossibleColors(),old(getPossibleColors()))
	 * @Post | result.getCurColor().equals((getCount()>2) 
	 *       | ? old(getCurColor()) : Arrays.stream(getPossibleColors()).parallel().findAny().get())
	 * @post | getCount() <= old(getCount())
	 * @creates | result
	 */
	public PaddleState stateAfterHit() {
		if (count > 2) {
			return this;
		} else {
			PaddleState res =
					new ReplicatingPaddleState(getCenter(), getPossibleColors(), getCurColor(), 1);
			return res;
		}
//		if (count > 2) {
//			return new ReplicatingPaddleState(getCenter(), getPossibleColors(), getCurColor(),count-1);
//		} else {
//			tossCurColor();
//			return new NormalPaddleState(getCenter(), getPossibleColors(), getCurColor());
//		}

	}
	
	@Override
	/**
	 * TODO
	 * To paint the array, only use those colors.
	 * 
	 * @post | result != null
	 * @post | Arrays.stream(result).allMatch(e -> e != null)
	 * @post | result.length == 3
	 * @post | Arrays.stream(result).anyMatch(c -> c .equals(getCurColor()))
	 * @inspects | this
	 * @creates | result 
	 */
	public Color[] getActualColors() {
		Color[] colors = new Color[3];
		colors = getPossibleColors().clone();
		return colors;
	}
	
	@Override
	/**
	 * TODO
	 * A copy of the paddle at hand.
	 * 
	 * @post | this.equalContent(result)
	 * @creates | result
	 * @inspects | this
	 */
	public PaddleState reproduce() {
		return new ReplicatingPaddleState(getCenter(),getPossibleColors(),getCurColor(),getCount());
	}
	
	@Override
	/**
	 * LEGIT
	 */
	public boolean equalContent(PaddleState other) {
		if (getClass() != other.getClass()) { return false; }
		ReplicatingPaddleState oth = (ReplicatingPaddleState) other;
		if (getCenter() != oth.getCenter()) { return false; }
		if ( ! Arrays.equals(getPossibleColors(), oth.getPossibleColors())) { return false; }
		if ( ! getCurColor() .equals( oth.getCurColor() )) { return false; }
		if ( count != oth.getCount() ) { return false; }
		return true;
		
	}
	

}
