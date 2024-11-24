package breakout.utils;


/**
 * this whole file is LEGIT
 * 
 * This class represents a point on a 2-dimensional integer grid.
 * 
 * @immutable
 */
public class Point {

	private final int x;
	private final int y;

	
	/**
	 * Return a new Point with given x and y coordinates.
	 * 
	 * @post | getX() == x
	 * @post | getY() == y
	 */
	public Point(int x, int y) {
		this.x = x; this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/** Return this point's x coordinate. */
	public int getX() {
		return x;
	}

	/** Return this point's y coordinate. */
	public int getY() {
		return y;
	}

	/**
	 * Return the point obtained by adding vector `v` to this point.
	 * 
	 * @pre | v != null
	 * @post | result != null
	 * @post | result.getX() == getX() + v.getX()
	 * @post | result.getY() == getY() + v.getY()
	 */
	public Point plus(Vector v) { 
		return new Point(x + v.getX(), y + v.getY());
	}

	/**
	 * Return the point obtained by adding vector `v` to this point.
	 * 
	 * @pre | v != null
	 * @post | result != null
	 * @post | result.getX() == getX() - v.getX()
	 * @post | result.getY() == getY() - v.getY()
	 */
	public Point minus(Vector v) {
		return plus(v.scaled(-1));
	}
	
	/**
	 * Return the vector obtained by subtracting a point `p` from this point.
	 * 
	 * @pre | p != null
	 * @post | result != null
	 * @post | result.getX() == getX() - p.getX()
	 * @post | result.getY() == getY() - p.getY()
	 */
	public Vector minus(Point p) {
		return new Vector(getX()-p.getX(), getY() - p.getY());
	}

	/**
	 * Return a string representation of this point.
	 * @post | result != null
	 */
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * Return the point obtained by mirroring this point over the vertical line through point (xmirror,0).
	 * 
	 * @post | result != null 
	 * @post | result.getY() == this.getY() 
	 * @post | result.getX() == 2*xmirror - getX() 
	 */
	public Point reflectVertical(int xmirror) {
		return new Point(2*xmirror-x,y);
	}
	/**
	 * Return the point obtained by mirroring this point over the horizontal line through point (0,ymirror).
	 * 
	 * @post | result != null 
	 * @post | result.getX() == this.getX() 
	 * @post | result.getY() == 2*ymirror - getY() 
	 */
	public Point reflectHorizontal(int ymirror) {
		return new Point(x,2*ymirror-y);
	}
	
	/**
	 * Return whether this point is up and left form a given other point. 
	 * @pre | other != null
	 * @post | result == (this.getX() <= other.getX() && this.getY() <= other.getY())
	 */
	public boolean isUpAndLeftFrom(Point other) {
		return this.getX() <= other.getX() && this.getY() <= other.getY();
	}
}
