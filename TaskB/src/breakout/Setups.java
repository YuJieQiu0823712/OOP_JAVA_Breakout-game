package breakout;

import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

/**
 * this whole file is LEGIT
 * 
 * a class with setups for tests.
 */
public class Setups {
	
	private Setups() {};
	
	/**
	 * An array of blocks. The blocks are placed in the bottom right corner (stacked in the last column).
	 * From low to high locations (" # 0, 1, 2, 3")
	 * normal - sturdy - replicator - powerupball
	 * 
	 */
	public static BlockState[] typicalBlocks() {
		int x = Constants.WIDTH - Constants.BLOCK_WIDTH;
		// from low to high
		int[] ys = new int[4];
		for (int i = 0 ; i < 4 ; i++) {
			ys[i] = Constants.HEIGHT - (i + 1) * Constants.BLOCK_HEIGHT;
		}
		//top left points of blocks from low to high
		Point[] tls = new Point[4];
		for (int i = 0 ; i < 4 ; i++) {
			tls[i] = new Point( x , ys[i]);
		}
		//bot right points of blocks from low to high
		Point[] brs = new Point[4];
		for (int i = 0 ; i < 4 ; i ++) {
			brs[i] = tls[i].plus( new Vector( Constants.BLOCK_WIDTH , Constants.BLOCK_HEIGHT));
		}
		//rects from low to high
		Rect[] rects = new Rect[4];
		for (int i = 0 ; i < 4 ; i ++) {
			rects[i] = new Rect( tls[i] , brs[i] );
		}
		return new BlockState[] {
				new NormalBlockState( rects[0] ),
				new SturdyBlockState( rects[1], 3),
				new ReplicatorBlockState( rects[2] ),
				new PowerupBallBlockState( rects[3] )
		};
	}
	
	
	/**
	 * A normal (slow) ball ready to hit block # n from the left.
	 * See typicalBlocks() for block # n.
	 * 
	 * @pre | n >= 0
	 * @pre allowing n == 4 to test wall bouncing | n <= 3 || n == 4
	 * @creates | result
	 * 
	 *  
	 */
	public static NormalBall typicalNormalBall(int n) {
		int x = Constants.WIDTH - Constants.BLOCK_WIDTH - (Constants.BLOCK_WIDTH / 2);
		int yn = Constants.HEIGHT - (Constants.BLOCK_HEIGHT / 2) - (n * Constants.BLOCK_HEIGHT);
		Vector rightVel = new Vector( 7 , 0 );
		return new NormalBall(
				new Circle( new Point(x, yn) , Constants.INIT_BALL_DIAMETER ),
				rightVel);
	}
	
	/**
	 * A supercharged (slow) ball ready to hit block # n from the left.
	 * See typicalBlocks() for block # n.
	 * 
	 * @pre | n >= 0
	 * @pre allowing n == 4 to test wall bouncing  | n <= 3 || n == 4
	 * @creates | result
	 * 
	 *  
	 */
	public static SuperChargedBall typicalSuperBall(int n) {
		int x = Constants.WIDTH - Constants.BLOCK_WIDTH - (Constants.BLOCK_WIDTH / 2);
		int yn = Constants.HEIGHT - (Constants.BLOCK_HEIGHT / 2) - (n * Constants.BLOCK_HEIGHT);
		Vector rightVel = new Vector( 7 , 0 );
		return new SuperChargedBall(
				new Circle( new Point(x, yn) , Constants.INIT_BALL_DIAMETER + 600),
				rightVel,
				Constants.SUPERCHARGED_BALL_LIFETIME);
	}

}
