public class Box{
	private Coordinate corners[];
	
	public Box(int x, int y, int width, int height){
		corners = new Coordinate[4];
		corners[0] = new Coordinate(x, y);
		corners[1] = new Coordinate(x+width, y);
		corners[2] = new Coordinate(x, y+height);
		corners[3] = new Coordinate(x+width, y+height);
	}
	
	public boolean isOverlapped(Box box2){
		for (int i=0; i<4; i++){
			if (corners[i].x > box2.getCorner(0).x && corners[i].x < box2.getCorner(1).x &&
			    corners[i].y > box2.getCorner(0).y && corners[i].y < box2.getCorner(3).y){
				return true;
			}
		}
		return false;
	}
	
	public Coordinate getCorner(int c){
		return corners[c];
	}
}