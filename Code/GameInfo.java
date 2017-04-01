public interface GameInfo{
	public static final int INTERNAL_WIDTH = 640;
	public static final int INTERNAL_HEIGHT = 360;
	public static final int DEFAULT_HEIGHT = 64;
	public static final int DEFAULT_WIDTH = 64;	
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;	

	public class Global{
		public static int size = 1;
		public static boolean showHitboxes = false;
	}
	
	public enum Direction{ UP, RIGHT, DOWN, LEFT }
}
