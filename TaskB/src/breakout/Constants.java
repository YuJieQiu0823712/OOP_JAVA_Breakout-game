package breakout;

import java.awt.Color;

import breakout.utils.Point;
import breakout.utils.Vector;

/**
 * this whole file is LEGIT
 * 
 * @immutable
 */
public class Constants {
	
	public static final Point ORIGIN = new Point(0,0);
	
	//paddle dims, speed
	public static final Vector PADDLE_VEL = new Vector(15, 0);
	public static final int PADDLE_HEIGHT = 500;
	public static final int PADDLE_WIDTH = 4500;
	
	//ball dim, speed
	public static final int INIT_BALL_DIAMETER = 700;
	public static final Vector INIT_BALL_VELOCITY = new Vector(5,3); // new Vector(5,7);
	public static final int MAX_BALL_SPEED = 16;
	public static final int MBS2 = MAX_BALL_SPEED * MAX_BALL_SPEED;
	public static final int MAX_BALL_REPLICAS = 3;
	public static final Vector[] BALL_VEL_VARIATIONS = new Vector[] { new Vector(0, 0), new Vector(1, -1),
			new Vector(-1, 1), new Vector(1, 1), new Vector(-1, -1) };
	public static final int BALL_SPEED_THRESH = 10; // below: "slow" ball. over: "fast" ball.
	public static final Color BALL_FAST_COLOR = Color.red;
	public static final Color BALL_COLOR = Color.yellow;
	public static final int SUPERCHARGED_BALL_LIFETIME = 10000;
	public static final Point[] REPL_SOURCE = {
		new Point( 25000, 19750 ) , new Point( 37500 , 19750 ), new Point( 12500, 19750 )  
	};
	
	//dimensions of typical game field / blocks
	public static final int HEIGHT = 30000;
	public static final int WIDTH = 50000;
	
	public static final int BLOCK_LINES = 8;
	public static final int BLOCK_COLUMNS = 10;
	
	public static final int BLOCK_WIDTH = WIDTH / BLOCK_COLUMNS;
	public static final int BLOCK_HEIGHT = HEIGHT / BLOCK_LINES;
	
	/**
	 * @creates | result
	 */
	public static Color[] TYPICAL_PADDLE_COLORS() {
		return new Color[] {Color.green, Color.magenta, Color.orange };
	}
	

	public static final Color BLOCK_COLOR = new Color(0x80,0x00,0xff);
	
	//time increments
	public static final int BALL_DELAYMS = 20;
	public static final int MAX_ELAPSED_TIME = 50;
	
	//game map examples
	public static final String initMap = """
##########
##########
##########
##########
     o

     =

""";

	public static final String initMap2 = """
##########
###!###R##
##########
SSS!#R#!#S
     o

     =

""";
	
	public static final String initMap3 = """
####!!!###
###!###R##
S!!!!!!!!S
S!!!!!!!!S
     o

     =

""";
	
	public static final String initMap4 = """
###R######
###!###R##
##R#######
SS#!!#!!#S
     o

     =

""";
	
	private Constants() {};
}
