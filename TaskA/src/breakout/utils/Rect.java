package breakout.utils;

import java.util.Objects;

/**
 * this whole file is LEGIT
 * 
 * Represents a rectangle in a 2-dimensional integer coordinate system. 
 * @immutable
 * @invar | getTopLeft() != null
 * @invar | getBottomRight() != null
 * @invar | getTopLeft().isUpAndLeftFrom(getBottomRight())
 */
public class Rect {
	
	/**
	 * @invar | topLeft != null
	 * @invar | bottomRight != null
	 * @invar | topLeft.getX() <= bottomRight.getX()
	 * @invar | topLeft.getY() <= bottomRight.getY()
	 */
	private final Point topLeft;
	private final Point bottomRight;
	
	public static final Vector[] COLLISSION_DIRS = new Vector[] {
			Vector.UP, Vector.DOWN, Vector.LEFT, Vector.RIGHT
			}; 

	/**
	 * Construct a new rectangle with given top-left and bottom-right coordinate. 
	 * @pre | topLeft != null
	 * @pre | bottomRight != null
	 * @pre | topLeft.getX() <= bottomRight.getX()
	 * @pre | topLeft.getY() <= bottomRight.getY()
	 * @post | getTopLeft().equals(topLeft)
	 * @post | getBottomRight().equals(bottomRight)
	 */
	public Rect(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	/** Return the top-left point of this rectangle */
	public Point getTopLeft() {
		return topLeft;
	}

	/** Return the bottom-right point of this rectangle */
	public Point getBottomRight() {
		return bottomRight;
	}

	/**
	 * Return whether given point `loc` is inside this rectangle.
	 * @pre | loc != null
	 * @post | result == (getTopLeft().isUpAndLeftFrom(loc) && loc.isUpAndLeftFrom(getBottomRight()))  
	 */
	public boolean contains(Point loc) {
		return getTopLeft().isUpAndLeftFrom(loc) && loc.isUpAndLeftFrom(getBottomRight()); 
	}
	
	/**
	 * Return whether this rectangle contains a given circle. 
	 * 
	 * @post | result == (getTopLeft().plus(new Vector(loc.getDiameter(),loc.getDiameter())).isUpAndLeftFrom(getBottomRight()) &&
	 * 		 |				minusMargin(loc.getRadius()).contains(loc.getCenter()))
	 */
	public boolean contains(Circle loc) {
		return getTopLeft().plus(new Vector(loc.getDiameter(),loc.getDiameter())).isUpAndLeftFrom(getBottomRight()) &&
				minusMargin(loc.getRadius()).contains(loc.getCenter());
	}
	
	/**
	 * Return whether this rectangle contains a given other rectangle.
	 * 
	 * @post | result == (getTopLeft().isUpAndLeftFrom(other.getTopLeft()) && 
	 *       |            other.getBottomRight().isUpAndLeftFrom(getBottomRight()))
	 */
	public boolean contains(Rect other) {
		return getTopLeft().isUpAndLeftFrom(other.getTopLeft()) && 
				other.getBottomRight().isUpAndLeftFrom(getBottomRight());
	}
	
	/**
	 * Check whether this rectangle intersects with the given circle and if so, return the direction from the circle to the rectangle.
	 * This direction may be an approximation for simplicity.
	 * 
	 * @pre | circ != null
	 * @post | result == null || (result.getSquareLength() == 1 && this.contains(circ.getOutermostPoint(result)))
	 */
	public Vector collideWith(Circle circ) {		
		for (Vector coldir : COLLISSION_DIRS) {
			Point c = circ.getOutermostPoint(coldir);
			if(contains(c)) {
				return coldir;
			}
		}
		return null;
	}
	
	/**
	 * Return the rectangle obtained by subtracting an inner margin from all sides of this rectangle.
	 * 
	 * @pre | getTopLeft().plus(new Vector(2*dx,2*dy)).isUpAndLeftFrom(getBottomRight())
	 * @post | result != null
	 * @post | result.getTopLeft().equals(getTopLeft().plus(new Vector(dx,dy)))
	 * @post | result.getBottomRight().equals(getBottomRight().minus(new Vector(dx,dy)))
	 */
	public Rect minusMargin(int dx, int dy) {
		Vector dv = new Vector(dx,dy);
		return new Rect( topLeft.plus(dv),
						 bottomRight.minus(dv));
	}
	
	/**
	 * Return the rectangle obtained by subtracting an inner margin from all sides of this rectangle.
	 * 
	 * @pre | getTopLeft().plus(new Vector(2*d,2*d)).isUpAndLeftFrom(getBottomRight())
	 * @post | result != null
	 * @post | result.getTopLeft().equals(getTopLeft().plus(new Vector(d,d)))
	 * @post | result.getBottomRight().equals(getBottomRight().minus(new Vector(d,d)))
	 */
	public Rect minusMargin(int d) {
		Vector dv = new Vector(d,d);
		return new Rect( topLeft.plus(dv),
						 bottomRight.minus(dv));
	}
	
	/**
	 * Return the point inside this rectangle that is as close as possible to a given point p.
	 * @pre | p != null
	 * @post | result.getX() == Math.min(getBottomRight().getX(), Math.max(getTopLeft().getX(), p.getX()))
	 * @post | result.getY() == Math.min(getBottomRight().getY(), Math.max(getTopLeft().getY(), p.getY()))
	 */
	public Point constrain(Point p) {
		int nx = Math.min(getBottomRight().getX(), Math.max(getTopLeft().getX(), p.getX()));
		int ny = Math.min(getBottomRight().getY(), Math.max(getTopLeft().getY(), p.getY()));
		return new Point( nx, ny);
	}

	/**
	 * Return the width of this rectangle.
	 * 
	 * @post | result == (getBottomRight().getX() - getTopLeft().getX())
	 */
	public int getWidth() {
		return bottomRight.getX() - topLeft.getX();
	}
	
	/**
	 * Return the height of this rectangle.
	 * 
	 * @post | result == (getBottomRight().getY() - getTopLeft().getY())
	 */
	public int getHeight() {
		return bottomRight.getY() - topLeft.getY();
	}
	
	/**
	 * Move the argument circle by the least amount so that it falls entirely within this rect.
	 * 
	 * @pre | c.getDiameter() < getWidth()
	 * @post | contains(result)
	 * @post | result.getCenter().equals(this.minusMargin(c.getRadius()).constrain(c.getCenter()))
	 */
	public Circle constrain(Circle c) {
		Rect r = this.minusMargin(c.getRadius());
		Point nc = r.constrain(c.getCenter());
		return new Circle(nc,c.getDiameter());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bottomRight, topLeft);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rect other = (Rect) obj;
		return Objects.equals(bottomRight, other.bottomRight) && Objects.equals(topLeft, other.topLeft);
	}
}
