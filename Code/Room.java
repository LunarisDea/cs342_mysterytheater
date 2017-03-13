import java.awt.Image;
import javax.swing.ImageIcon;

public class Room implements GameInfo{	
	private int roomNum = 0;
	private Image bgImage;
	
	public Room(){
		loadBackground();
	}
	
	public void loadBackground(){
		ImageIcon bg = new ImageIcon("bg" + roomNum + ".png");
		bgImage = bg.getImage();
		if (Global.size != 1)
			bgImage = bgImage.getScaledInstance(INTERNAL_WIDTH * Global.size, 
												INTERNAL_HEIGHT * Global.size, Image.SCALE_DEFAULT);		
	}
	
	public Image getImage(){
		return bgImage;
	}
	
	public int getRoomNum(){
		return roomNum;
	}
}