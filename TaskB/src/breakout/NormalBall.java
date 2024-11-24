package breakout;

import breakout.utils.*;

/**
 * Represents the state of a NormalBall in the breakout game.
 * 
 * @mutable
 */
public class NormalBall extends Ball {

	/**
	 * Construct a new ball at a given `location`, with a given `velocity`.
	 * 
 	 * @throws IllegalArgumentException | location == null
 	 * @throws IllegalArgumentException | velocity == null
 	 * @post | getLocation().equals(location)
 	 * @post | getVelocity().equals(velocity)
	 */
	public NormalBall(Circle location, Vector velocity) {
		super(location, velocity);

	}

	/**
	 * Update the BallState after hitting a block at a given location.
	 * 
	 * @pre | rect != null
	 * @pre | collidesWith(rect)
	 * @post | getCenter().equals(old(getCenter()))
	 * @post | getVelocity().equals(old(getVelocity()).mirrorOver(rect.collideWith(old(getLocation()))))
	 *       | || getVelocity().equals(old(getVelocity()))
	 * @mutates | this
	 */
	@Override
	public void hitBlock(Rect rect, boolean destroyed) {
		setVelocity(bounceOn(rect));
	}

	@Override
	/**
	 * LEGIT
	 */
	public void move(Vector v, int elapsedTime) {
		setLocation ( new Circle(getLocation().getCenter().plus(v), getLocation().getDiameter()) );
	}

	@Override
	/**
	 * LEGIT
	 */
	public void hitPaddle(Rect rect, Vector paddleVel) {
		Vector nspeed = bounceOn( rect );
		Vector mbMore = nspeed .plus(paddleVel.scaledDiv(5));
		if (mbMore.getSquareLength() <= Constants.MBS2) { setVelocity( mbMore ); }
		else { setVelocity( nspeed ); };
	}
	


	@Override
	public void hitWall(Rect rect) {
		setVelocity( bounceOn( rect ) );
	}


	@Override
	public Ball cloneWithVelocity(Vector v) {
		return new NormalBall(getLocation(), v);
	}
	
	@Override
	/**
	 * TODO
	 * Return this normal ball
	 * 
	 * @creates | result
	 * @inspects | this
	 * @post | result.getCenter().equals(getCenter())
	 * @post | result.getVelocity().equals(getVelocity())
	 * @post | result.equals(this)
	 */
	public Ball backToNormal() {
		return cloneWithVelocity(getVelocity());
		
	}


	
	


}
