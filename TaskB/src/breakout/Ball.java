package breakout;

import java.awt.Color;
import java.util.Objects;


import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

/**
 * Represents the state of a ball in the breakout game.
 * 
 * @mutable
 * @invar | getLocation() != null
 * @invar | getVelocity() != null
 */
public abstract class Ball {
		
	/**
	 * @invar | location != null
	 * @invar | velocity != null
	 */
	private Circle location;
	private Vector velocity;
	
	
	/**
	 * Construct a new ball at a given `location`, with a given `velocity`.
 	 * @throws IllegalArgumentException | location == null
 	 * @throws IllegalArgumentException | velocity == null
 	 * @post | getLocation().equals(location)
 	 * @post | getVelocity().equals(velocity)
	 */
	public Ball(Circle location, Vector velocity) throws IllegalArgumentException {
		if (location == null) throw new IllegalArgumentException();
		if (velocity == null) throw new IllegalArgumentException();
		this.location = location; //OK since Circle is immutable
		this.velocity = velocity; //same
	}

	
	/**
	 * Return this ball's location.
	 */
	public Circle getLocation() {
		return location; //OK since Circle immutable
	}

	/**
	 * Return this ball's velocity.
	 */
	public Vector getVelocity() {
		return velocity; //Vector immutable
	}
	
	/**
	 * Return this ball's center.
	 * 
	 * @post | getLocation().getCenter().equals(result)
	 * @inspects | this
	 */
	public Point getCenter() {
		return getLocation().getCenter();
	}
	
	
	
	/**
	 * Set this ball's location
	 * 
	 * @pre | location != null
	 * @mutates | this
	 * @post | getLocation().equals(location)
	 * @post | getVelocity().equals(old(getVelocity())) 
	 */
	public void setLocation(Circle location) {
		this.location = location;
	}
	
	/**
	 * Set this ball's velocity.
	 * 
	 * @pre | velocity != null
	 * @mutates | this
	 * @post | getVelocity().equals(velocity)
	 * @post | getLocation().equals(old(getLocation()))
	 * @post | getLocation().getDiameter() == old(getLocation()).getDiameter()
	 */
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Set this ball's position.
	 * 
	 * @pre | pos != null
	 * @mutates | this
	 * @post | getLocation().getCenter().equals(pos)
	 * @post | getVelocity().equals(old(getVelocity()))
	 * @post | getLocation().getDiameter() == old(getLocation()).getDiameter()
	 */
	public void setPosition(Point pos) {
		this.location = new Circle( pos, this.location.getDiameter() );
	}
	

	
	/**
	 * Move this BallState by the given vector, knowing that elapsedTime ms have ticked.
	 * 
	 * @pre | v != null
	 * @pre | elapsedTime >= 0
	 * @pre | elapsedTime <= Constants.MAX_ELAPSED_TIME
	 * @mutates | this
	 * @post | getLocation().getCenter().equals(old(getLocation()).getCenter().plus(v))
	 * @post | getLocation().getDiameter() == old(getLocation()).getDiameter()
	 * 
	 */
	public abstract void move(Vector v, int elapsedTime);
	
	/**
	 * LEGIT
	 * 
	 * if the ball "collidesWith" rect (ie both shapes approx. intersect and this ball moves toward rect): mirrors this ball speed 
	 * Returns true in this case.
	 * Else returns false.
	 * 
	 * @pre | rect != null
	 * @mutates | this
	 * @post | getLocation() .equals( old(getLocation() ))
	 * @post | ( (!old( collidesWith(rect))) && (old(getVelocity()) .equals( getVelocity()) ) && result == false) || 
	 * 	     | ( old( collidesWith( rect )) &&
	 *       |   (getVelocity() .equals( old(getVelocity()).mirrorOver(rect.collideWith(getLocation())) ) ) &&
	 *       |   (result == true)  )          
	 */
	public boolean hitRect(Rect rect) {
		if (collidesWith(rect)) {			
			velocity = bounceOn (rect );
			return true;
		}
		else return false;
	}
	
	/**
	 * LEGIT
	 * 
	 * If this ball "collidesWith" rect (approx. intersects a given `rect` and moves towards it): returns the mirrored (from bounce) velocity.
	 * else returns null.
	 * 
	 * @pre | rect != null
	 * @post | (rect.collideWith(getLocation()) == null && result == null) ||
	 *       | (rect.collideWith(getLocation()) != null && getVelocity().product(rect.collideWith(getLocation())) <= 0 && result == null) ||
	 *       | (rect.collideWith(getLocation()) != null && result.equals(getVelocity().mirrorOver(rect.collideWith(getLocation()))))
	 * @inspects | this
	 */
	public Vector bounceOn(Rect rect) {
		Vector coldir = rect.collideWith(location);
		if (coldir != null && velocity.product(coldir) > 0) {
			return velocity.mirrorOver(coldir);
		}
		return null;
	}	
	
	/**
	 * Update the BallState after hitting a block at a given location,
	 * taking into account whether the block was destroyed by the hit or not.
	 * Can change the velocity, and the diameter but not the center of the ball.
	 * 
	 * @pre | rect != null
	 * @pre | collidesWith(rect)
	 * @post | getCenter().equals(old(getCenter()))
	 * @mutates | this
	 */
	public abstract void hitBlock(Rect rect, boolean destroyed);

	/**
	 * Update the BallState after hitting a paddle at a given location.
	 * 
	 * @pre | rect != null
	 * @pre | collidesWith(rect)
	 * @pre | paddleVel != null
	 * @post | getCenter().equals(old(getCenter()))
	 * @mutates | this
	 */
	public abstract void hitPaddle(Rect rect, Vector paddleVel);

	/**
	 * Update the BallState after hitting a wall at a given location.
	 * 
	 * @pre | rect != null
	 * @pre | collidesWith(rect)
	 * @post | getLocation().equals(old(getLocation()))
	 * @post | getVelocity().equals(old(getVelocity()).mirrorOver(rect.collideWith(getLocation())))
	 * @mutates | this
	 */
	public abstract void hitWall(Rect rect);
	
	/**
	 * When it's no longer useful to be an instance of a Ball subclass,
	 * this method returns an instance of a less specialized subclass of Ball.
	 * It's possible that backToNormal yields an == result.
	 * 
	 * @creates | result
	 * @inspects | this
	 * @post | result.getCenter().equals(getCenter())
	 * @post | result.getVelocity().equals(getVelocity())
	 */
	public abstract Ball backToNormal();

	
	/**
	 * TODO
	 * 
	 * Return the color this ball should be painted in.
	 * 
	 * @post | result != null
	 * @inspects | this
	 * @post | result.equals((Math.sqrt(getVelocity().getSquareLength()) >= Constants.BALL_SPEED_THRESH) 
	 *       | ? Constants.BALL_FAST_COLOR : Constants.BALL_COLOR)
	 */
	public final Color getColor() {
		if(Math.sqrt(getVelocity().getSquareLength()) >= Constants.BALL_SPEED_THRESH) {
			return Constants.BALL_FAST_COLOR;
		}
		return Constants.BALL_COLOR;
	}
	
	
	/**
	 * LEGIT
	 * 
	 * Check whether this ball:  collides with a given `rect` (shapes approx intersect), and goes towards rect.
	 * In other words, checks whether the speed really needs to be updated upon collision (see Rect.collideWith)
	 * For instance if the ball is moving away from rect, this method returns false
	 * 
	 * @pre | rect != null
	 * @post | result == ((rect.collideWith(getLocation()) != null) &&
	 *       |            (getVelocity().product(rect.collideWith(getLocation())) > 0))
	 * @inspects | this
	 */
	public boolean collidesWith(Rect rect) {
		Vector coldir = rect.collideWith(getLocation());
		return coldir != null && (getVelocity().product(coldir) > 0);
	}
		

	
	/**
	 * Return a clone of this BallState with the given velocity.
	 * 
	 * @inspects | this
	 * @creates | result
	 * @post | result.getLocation().equals(getLocation())
	 * @post | result.getVelocity().equals(v)
	 */
	public abstract Ball cloneWithVelocity(Vector v);
	
	/**
	 * Return a clone of this BallState.
	 * 
	 * @inspects | this
	 * @creates | result
	 * @post | result.getLocation().equals(getLocation())
	 * @post | result.getVelocity().equals(getVelocity())
	 */
	public Ball clone() {
		return cloneWithVelocity(getVelocity());
	}
	
	@Override
	/**
	 * LEGIT
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ball other = (Ball) obj;
		if (! getLocation().equals(other.getLocation() ) )
			return false;
		if ( ! getVelocity().equals(other.getVelocity() ))
			return false;
		return true;
	}
	
	
	/**
	 * LEGIT
	 * 
	 * Careful: depends on mutable state of this object.
	 * As a result, Balls must not be modified while they are used as key in a hash set or table. 
	 * 
	 * @inspects | this
	 */
	@Override
	public int hashCode() {
		return Objects.hash(location, velocity);
	}
	
	
}
