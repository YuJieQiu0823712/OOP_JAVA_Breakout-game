package breakout;

import breakout.utils.*;

/**
 * Represents the state of a SuperChargedBall in the breakout game.
 * 
 * @mutable
 */
public class SuperChargedBall extends NormalBall {

	/**
	 * @invar | lifetime <= 10000 
	 */
	private int lifetime;
	
	/**
	 * Construct a new ball at a given `location`, with a given `velocity`.
	 * 
 	 * @throws IllegalArgumentException | location == null
 	 * @throws IllegalArgumentException | velocity == null
 	 * @throws IllegalArgumentException | lifetime > 10000 
 	 * @post | getLocation().equals(location)
 	 * @post | getVelocity().equals(velocity)
 	 * @post | getLifetime() == lifetime
	 */
	public SuperChargedBall(Circle location, Vector velocity, int lifetime) {
		super(location, velocity);
		
		if(lifetime>10000) {
			throw new IllegalArgumentException();
		}
		this.lifetime = lifetime;
	}

	/**
	 * Update the BallState after hitting a block at a given location.
	 * 
	 * @pre | rect != null
	 * @pre | collidesWith(rect)
	 * @post | getCenter().equals(old(getCenter()))
	 * @post | (getLifetime() < 0 || !destroyed) || getVelocity().equals(old(getVelocity()))
	 * @post | getLocation().getDiameter() <= old(getLocation().getDiameter())
	 * @mutates | this
	 */
	@Override
	public void hitBlock(Rect rect, boolean destroyed) {
		if(lifetime < 0 || !destroyed) { //bounces if normal ball again, or sturdy block
			super.hitBlock(rect, destroyed);
		}

		if (getLocation().getDiameter() >= Constants.INIT_BALL_DIAMETER + 100) {
			setLocation( new Circle ( getCenter() , getLocation().getDiameter() - 100));
		}
	}

	@Override
	/**
	 * Update the BallState after hitting a paddle at a given location.
	 * 
     * @pre | loc != null
	 * @pre | collidesWith(loc)
	 * @pre | paddleVel != null
	 * @mutates | this
	 * @post | getCenter().equals(old(getCenter()))
	 * @post | getLocation().getDiameter() > old(getLocation().getDiameter())
	 */
	public void hitPaddle(Rect loc, Vector paddleVel) {
		super.hitPaddle(loc,paddleVel);
		setLocation(new Circle(getLocation().getCenter(), getLocation().getDiameter()+100));
	}

	@Override
	public void hitWall(Rect rect) {
		super.hitWall(rect);
	}

	public int getLifetime() {
		return lifetime;
	}
	
	@Override
	/**
	 * LEGIT
	 */
	public void move(Vector v, int elapsedTime) {
		if(lifetime >= 0) {
			lifetime -= elapsedTime;
		}
		setLocation( new Circle(getLocation().getCenter().plus(v), getLocation().getDiameter()) );
	}

	@Override
	public Ball cloneWithVelocity(Vector v) {
		return new SuperChargedBall(getLocation(), v, lifetime);
	}
	
	@Override
	/**
	 * TODO
	 * Return a NormalBall if lifetime <= 0, otherwise return this SuperChargedBall
	 * 
	 * @creates | result
	 * @mutates | this
	 * @post | result.getCenter().equals(old(getCenter()))
	 * @post | result.getVelocity().equals(old(getVelocity()))
	 * @post | result.getLocation().getDiameter() <= old(getLocation().getDiameter())
	 */
	public Ball backToNormal() {
		if(lifetime <= 0) {
			NormalBall nball = new NormalBall(getLocation(),getVelocity());
			nball.setLocation(new Circle(getCenter(), Constants.INIT_BALL_DIAMETER));
			return nball;
		}
		return cloneWithVelocity(getVelocity());
	}
	
	

}
