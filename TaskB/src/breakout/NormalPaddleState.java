package breakout;

import java.awt.Color;
import java.util.Arrays;

import breakout.utils.*;

/**
 * Represents the state of a normalPaddle in the breakout game.
 * 
 * @mutable
 */
public class NormalPaddleState extends PaddleState {

	/**
	 * Construct a normalPaddle located around a given center in the field.
	 * 
	 * @pre | center != null
	 * @pre | possColors != null
	 * @pre | possColors.length >= 1
	 * @pre | Arrays.stream(possColors).allMatch(e -> e != null)
	 * @pre | curColor != null
	 * @pre | Arrays.stream(possColors).anyMatch(e -> e.equals(curColor))
	 * @post | getCenter().equals(center)
	 * @post | Arrays.equals(getPossibleColors(),possColors)
	 * @post | getCurColor().equals(curColor)
	 */
	public NormalPaddleState(Point center, Color[] possColors, Color curColor) {
		super(center, possColors, curColor);
	}

	@Override
	/**
	 * TODO
	 * Returns the number of generated balls + 1 (the ball that is bouncing on the paddle).
	 * 
	 * @post | result == 1
	 */
	public int numberOfBallsAfterHit() {
		return 1;
	}

	@Override
	/**
	 * TODO
	 * Return the new state of the normalPaddle after it is hit by a ball.
	 * 
	 * @post | result != null
	 * @post | this.equalContent(result)
	 * @post | result.getLocation().equals(getLocation())
	 * @inspects | this
	 * @creates | result
	 */
	public PaddleState stateAfterHit() {
		return new NormalPaddleState(getCenter(),getPossibleColors(),getCurColor());
		
	}
	
	@Override
	/**
	 * TODO
	 * To paint the array, only use those colors.
	 * 
	 * @post | result != null
	 * @post | Arrays.stream(result).allMatch(e -> e != null)
	 * @post | result.length == 1
	 * @post | Arrays.stream(result).anyMatch(c -> c .equals(getCurColor()))
	 * @inspects | this
	 * @creates | result 
	 */
	public Color[] getActualColors() {
		Color[] colors = new Color[1];
		colors[0] = getCurColor();
		return colors;
		
	}
	
	@Override
	/**
	 * TODO
	 * A copy of the paddle at hand.
	 * 
	 * @post | this.equalContent(result)
	 * @inspects | this
	 * @creates | result
	 */
	public PaddleState reproduce() {
		return new NormalPaddleState(getCenter(),getPossibleColors(),getCurColor());	
	}
	
	/**
	 * LEGIT
	 */
	public boolean equalContent(PaddleState other) {
		if (getClass() != other.getClass()) { return false; }
		if (getCenter()!=(other.getCenter())) { return false; }
		if ( ! Arrays.equals(getPossibleColors(), other.getPossibleColors())) { return false; }
		if ( ! getCurColor() .equals( other.getCurColor() )) { return false; }
		return true;
		
	}

	

}
