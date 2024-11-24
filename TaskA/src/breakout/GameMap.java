package breakout;

import java.util.ArrayList;


import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

/**
 * this whole file is LEGIT
 */
public class GameMap {


	private GameMap() { throw new AssertionError("This class is not intended to be instantiated"); }

	private static BlockState createBlock(Point topLeft, char type) {
		Vector marginBL = new Vector(20, 20);
		Vector size = new Vector(Constants.BLOCK_WIDTH - 70, Constants.BLOCK_HEIGHT - 70);
		Point blockTL = topLeft.plus(marginBL);
		Point blockBR = blockTL.plus(size);
		switch (type) {
		case '#':
			return new NormalBlockState(new Rect (blockTL, blockBR));
		case 'S':
			return new SturdyBlockState(new Rect (blockTL, blockBR), 3);
		case 'R':
			return new ReplicatorBlockState(new Rect (blockTL, blockBR));
		case '!':
			return new PowerupBallBlockState(new Rect (blockTL, blockBR));
		default:
			return null;
		}
	}	
	private static PaddleState createPaddle(Point topLeft) {
		Vector size = new Vector(Constants.BLOCK_WIDTH/2,Constants.BLOCK_HEIGHT/2);
		Point center = topLeft.plus(size);
		return new NormalPaddleState(center, Constants.TYPICAL_PADDLE_COLORS(), Constants.TYPICAL_PADDLE_COLORS()[0]);
	}
	private static Ball createBall(Point topLeft) {
		Vector centerD = new Vector(Constants.BLOCK_WIDTH/2,Constants.BLOCK_HEIGHT/2);
		Point center = topLeft.plus(centerD);
		int diameter = Constants.INIT_BALL_DIAMETER;
		return new NormalBall(new Circle(center,diameter), Constants.INIT_BALL_VELOCITY);
	}
	

	
	
	/**
	 * Return the initial breakout state represented by string `description`.
	 * 
	 * @pre | description != null
	 * @post | result != null
	 */
	public static BreakoutState createStateFromDescription(String description) {
		String[] lines = description.split("\n", Constants.BLOCK_LINES);

		Vector unitVecRight = new Vector(Constants.BLOCK_WIDTH, 0);
		Vector unitVecDown = new Vector(0, Constants.BLOCK_HEIGHT);
		ArrayList<BlockState> blocks = new ArrayList<BlockState>();
		ArrayList<Ball> balls = new ArrayList<Ball>();
		PaddleState paddle = null;

		Point topLeft = new Point(0, 0);
		assert lines.length <= Constants.BLOCK_LINES;
		for (String line : lines) {
			assert line.length() <= Constants.BLOCK_COLUMNS;
			Point cursor = topLeft;
			for (char c : line.toCharArray()) {
				switch (c) {
				case '#':
					blocks.add(createBlock(cursor, '#'));
					break;
				case 'S':
					blocks.add(createBlock(cursor, 'S'));
					break;
				case 'R':
					blocks.add(createBlock(cursor, 'R'));
					break;
				case '!':
					blocks.add(createBlock(cursor, '!'));
					break;
				case 'o':
					balls.add(createBall(cursor));
					break;
				case '=':
					paddle = createPaddle(cursor);
					break;
				}
				cursor = cursor.plus(unitVecRight);
			}
			topLeft = topLeft.plus(unitVecDown);
		}
		
		Point botRight = new Point(Constants.WIDTH, Constants.HEIGHT);
		
		return new BreakoutState(
				balls.toArray(new Ball[] {}),
				blocks.toArray(new BlockState[] {}),
				botRight,
				paddle);

	}	
	
	
	
	
	
}