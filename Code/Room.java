import java.awt.Image;
import javax.swing.ImageIcon;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;


public class Room implements GameInfo{	
	private int roomNum = 100;
	private int framesSinceRoomEnter = 0;	
	private Image bgImage;
	private Image fgImage;
	
	private int numObjects;
	private int numTransitions;
	private Box[] collisionBoxes;
	private Box[] transitionBoxes;
	
	public Room(){
		loadBackground();
	}
	
	public void loadBackground(){
		readDataFile();
		
		ImageIcon bg = new ImageIcon("Images/bg/" + roomNum + ".png");	
		ImageIcon fg = new ImageIcon("Images/bg/" + roomNum + "foreground.png");
		bgImage = bg.getImage();
		fgImage = fg.getImage();
			
		if (Global.size != 1){
			bgImage = bgImage.getScaledInstance(INTERNAL_WIDTH * Global.size, 
												INTERNAL_HEIGHT * Global.size, Image.SCALE_DEFAULT);
			fgImage = fgImage.getScaledInstance(INTERNAL_WIDTH * Global.size, 
												INTERNAL_HEIGHT * Global.size, Image.SCALE_DEFAULT);													
		}												
	}
	
	private void readDataFile(){	
		collisionBoxes = new Box[12];
		transitionBoxes = new Box[4];
		numObjects = 0;
		numTransitions = 0;
		int val = 0;
	
		try{	
			Scanner scan = new Scanner(new File("Images/BG/"+roomNum+".txt"));
			val = scan.nextInt();
			
			while (val != -1){ //collisionBoxes
				int x = val;
				int y = scan.nextInt();
				int x2 = scan.nextInt();
				int y2 = scan.nextInt();
				collisionBoxes[numObjects] = new Box(x, y, x2-x, y2-y);
				numObjects++;
				val = scan.nextInt();
			}
			
			val = scan.nextInt();
			while (val != -1){ //transitionBoxes
				int x = val;
				int y = scan.nextInt();
				int x2 = scan.nextInt();
				int y2 = scan.nextInt();
				transitionBoxes[numTransitions] = new Box(x, y, x2-x, y2-y);
				numTransitions++;
				val = scan.nextInt();
			}
			
		} catch (IOException e){
			System.out.println("Room data file not found");
		}
	}
	
	public int getNumObjects(){
		return numObjects;
	}
	
	public Box getCollisionBox(int objectNumber){
		return collisionBoxes[objectNumber];
	}
	
	public int getNumTransitions(){
		return numTransitions;
	}
	
	public Box getTransitionBox(int transitionNumber){
		return transitionBoxes[transitionNumber];
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
	
	public void roomTransitionWithoutMovement(int dir){
		if (dir == UP){
			roomNum = roomNum + 10;
			loadBackground();
			framesSinceRoomEnter = 0;
		}
		else if (dir == RIGHT){
			roomNum = roomNum + 1;
			loadBackground();
			framesSinceRoomEnter = 0;
		}
		else if (dir == DOWN){
			roomNum = roomNum - 10;
			loadBackground();
			framesSinceRoomEnter = 0;
		}
		else if (dir == LEFT){
			roomNum = roomNum - 1;
			loadBackground();
			framesSinceRoomEnter = 0;
		}		
	}
	
	public void borderCollisionDetector(Player player){
		framesSinceRoomEnter++;
		
		Box playerBox = player.getHurtbox();
		for (int i=0; i < numTransitions; i++){
			if (playerBox.isOverlapped(transitionBoxes[i]))
				roomTransitionWithoutMovement(i);
		}
		
		if (player.getX() + player.getWidth() > INTERNAL_WIDTH){
			if (framesSinceRoomEnter > 60){
				rightRoom(player);
			}
			else{
				player.moveToPrev();
			}
		}
		else if (player.getX() < 0){
			if (framesSinceRoomEnter > 60){
				leftRoom(player);
			}
			else{
				player.moveToPrev();
			}
		}
		else if (player.getY() + player.getHeight() > INTERNAL_HEIGHT){
			if (framesSinceRoomEnter > 60){
				downRoom(player);
			}
			else{
				player.moveToPrev();
			}
		}
		else if (player.getY() < 0){
			if (framesSinceRoomEnter > 60){
				upRoom(player);
			}
			else{
				player.moveToPrev();
			}
		}
	
	}
	
	public void envCollisionDetector(Player player){
		Box playerBox = player.getHurtbox();
		for (int i=0; i < numObjects; i++){
			if (playerBox.isOverlapped(collisionBoxes[i]))
				player.moveToPrev();
		}
	}
	
	public Image getBgImage(){
		return bgImage;
	}
	
	public Image getFgImage(){
		return fgImage;
	}
	
	public int getRoomNum(){
		return roomNum;
	}
}
