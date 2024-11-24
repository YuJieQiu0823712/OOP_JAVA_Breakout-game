package breakout;

import java.awt.Color;

import breakout.utils.Rect;
import breakout.utils.Circle;

/**
 * Represents the state of a powerupBallBlock in the breakout game.
 * 
 * @immutable
 * @invar | getColor() != null
 */
public class PowerupBallBlockState extends NormalBlockState {

	/**
	 * @invar | COLOR != null
	 */
	private static final Color COLOR = new Color(215, 0, 64);//red

	/**
	 * Construct a PowerupBallBlockState occupying a given rectangle in the field.
	 * 
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	public PowerupBallBlockState(Rect location) {
		super(location);
	}

	/**
	 * Return the SuperChargedBall when the ball hit the PowerUp block.
	 * 
	 * @pre | b != null
	 * @post | result.getVelocity().equals(b.getVelocity())
	 * @post | result.getCenter().equals(b.getCenter())
	 * @post | result.getLocation().getDiameter() == 
	 *       | ((b.getLocation().getDiameter() > Constants.INIT_BALL_DIAMETER)
	 *       | ? b.getLocation().getDiameter() : Constants.INIT_BALL_DIAMETER + 600)
	 * @creates | result
	 * @inspects | this
	 */
	@Override
	public Ball ballStateAfterHit(Ball b) {
		int superDiam;
		if (b.getLocation().getDiameter() > Constants.INIT_BALL_DIAMETER) {
			superDiam = b.getLocation().getDiameter();
		}
		else {
			superDiam = Constants.INIT_BALL_DIAMETER + 600;
		}
		Circle superLoc = new Circle( b.getCenter(), superDiam);
		return new SuperChargedBall(
				superLoc,
				b.getVelocity(),
				Constants.SUPERCHARGED_BALL_LIFETIME);
	}

	@Override
	public Color getColor() {
		return COLOR;
	}

}
