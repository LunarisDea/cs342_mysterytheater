import java.awt.Image;
import javax.swing.ImageIcon;

public class Room implements GameInfo{	
	private int roomNum = 100;
	private int framesSinceRoomEnter = 0;	
	private Image bgImage;
	
	public Room(){
		loadBackground();
	}
	
	public void loadBackground(){
		ImageIcon bg = new ImageIcon("Images/bg" + roomNum + ".png");		
		bgImage = bg.getImage();
			
		if (Global.size != 1)
			bgImage = bgImage.getScaledInstance(INTERNAL_WIDTH * Global.size, 
												INTERNAL_HEIGHT * Global.size, Image.SCALE_DEFAULT);		
	}
	
	private void upRoom(Player player){
		roomNum += 10;
		loadBackground();
		player.setLocation(player.getX(), INTERNAL_HEIGHT-player.getHeight());
		framesSinceRoomEnter = 0;			
	}
	
	private void downRoom(Player player){
		roomNum -= 10;
		loadBackground();
		player.setLocation(player.getX(), 0);		
		framesSinceRoomEnter = 0;		
	}
	
	private void leftRoom(Player player){
		roomNum -= 1;
		loadBackground();
		player.setLocation(INTERNAL_WIDTH-player.getWidth(), player.getY());
		framesSinceRoomEnter = 0;			
	}
	
	private void rightRoom(Player player){
		roomNum = roomNum + 1;
		loadBackground();
		player.setLocation(0, player.getY());
		framesSinceRoomEnter = 0;		
	}
	
	public void borderCollisionDetector(Player player){
		framesSinceRoomEnter++;
		
		if (player.getX() + player.getWidth() > INTERNAL_WIDTH){
			if (framesSinceRoomEnter > 60){
				rightRoom(player);
			}
			else{
				player.setLocation(INTERNAL_WIDTH - player.getWidth(), player.getY());
			}
		}
		else if (player.getX() < 0){
			if (framesSinceRoomEnter > 60){
				leftRoom(player);
			}
			else{
				player.setLocation(0, player.getY());
			}			
		}
		else if (player.getY() + player.getHeight() > INTERNAL_HEIGHT){
			if (framesSinceRoomEnter > 60){
				downRoom(player);
			}
			else{
				player.setLocation(player.getX(), INTERNAL_HEIGHT - player.getHeight());
			}
		}
		else if (player.getY() < 0){
			if (framesSinceRoomEnter > 60){
				upRoom(player);
			}
			else{
				player.setLocation(player.getX(), 0);
			}			
		}		
	}
	
	public Image getImage(){
		return bgImage;
	}
	
	public int getRoomNum(){
		return roomNum;
	}
}
