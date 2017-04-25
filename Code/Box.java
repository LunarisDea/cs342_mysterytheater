public class Box{
	private Coordinate corners[];
	private int offsetX = 0;
	private int offsetY = 0;
	private boolean active;
	
	public Box(int x, int y, int width, int height){
		active = true;
		corners = new Coordinate[4];
		corners[0] = new Coordinate(x, y);
		corners[1] = new Coordinate(x+width, y);
		corners[2] = new Coordinate(x, y+height);
		corners[3] = new Coordinate(x+width, y+height);
	}
	
	public void makeInactive(){
		active = false;
	}
	
	public void makeActive(){
		active = true;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public boolean isOverlapped(Box box2){
		if (active == false || box2.isActive() == false)
			return false;
		int offsetDiffX = box2.getX() - offsetX;
		int offsetDiffY = box2.getY() - offsetY;
		for (int i=0; i<4; i++){
			if (corners[i].x >= offsetDiffX && 
			    corners[i].x <= offsetDiffX + box2.getWidth() &&
			    corners[i].y >= offsetDiffY && 
				corners[i].y <= offsetDiffY + box2.getHeight()){
				return true;
			}			
		}
		
		offsetDiffX = getX() - box2.getOffsetX();
		offsetDiffY = getY() - box2.getOffsetY();
		for (int i=0; i<4; i++){
			Coordinate tempCorner = box2.getCorner(i);
			if (tempCorner.x >= offsetDiffX && 
			    tempCorner.x <= offsetDiffX + getWidth() &&
			    tempCorner.y >= offsetDiffY && 
				tempCorner.y <= offsetDiffY + getHeight()){
				return true;
			}			
		}
		
		return false;
	}
	
	public int getOffsetX(){
		return offsetX;
	}
	
	public int getOffsetY(){
		return offsetY;
	}
	
	public void changeOffset(int x, int y){
		offsetX = x;
		offsetY = y;
	}
	
	public Coordinate getCorner(int c){
		return corners[c];
	}
	
	public Coordinate getCorner(){
		return corners[0];
	}
	
	public int getX(){
		return corners[0].x + offsetX;
	}
	
	public int getY(){
		return corners[0].y + offsetY;
	}
	
	public int getWidth(){
		return (corners[1].x - corners[0].x);
	}
	
	public int getHeight(){
		return (corners[3].y - corners[0].y);
	}
}
