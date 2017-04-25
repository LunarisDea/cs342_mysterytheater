import java.awt.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Room implements GameInfo{
	private static Room instance = null;
	private static Clip clip;
	private static Clip clip2;
	private int roomNum;
	private int framesSinceRoomEnter;
	private Image bgImage;
	private Image fgImage;
	
	private int overlay;
	private int overlayIntensity;
	
	private int numCollidables;
	private int numActables;
	private int numTransitions;
	private int numEnemies;
	private Box[] collisionBoxes;
	private Box[] actionBoxes;	
	private Box[] transitionBoxes;
	private Enemy[] enemies;
	
	public static Room getInstance(){
		if (instance == null){
			instance = new Room();
		}
		return instance;
	}
	
	protected Room(){
		roomNum = 100;
		framesSinceRoomEnter = 0;
		overlay = 0;
		overlayIntensity = 125;
		loadImages();
		loadSound();
		Player.getInstance().cutscene(2);
	}
	
	private void loadImages(){
		readDataFile();
		
		ImageIcon bg = new ImageIcon("Images/bg/" + roomNum + ".png");	
		ImageIcon fg = new ImageIcon("Images/bg/" + roomNum + "foreground.png");
		bgImage = bg.getImage();
		fgImage = fg.getImage();
		//System.out.println("Play music for room: " + roomNum);

		if (Global.size != 1){
			bgImage = bgImage.getScaledInstance(INTERNAL_WIDTH * Global.size, 
												INTERNAL_HEIGHT * Global.size, Image.SCALE_DEFAULT);
			fgImage = fgImage.getScaledInstance(INTERNAL_WIDTH * Global.size, 
												INTERNAL_HEIGHT * Global.size, Image.SCALE_DEFAULT);													
		}
		loadSpecialImages();
	}
	
	private void loadSpecialImages(){
		if (roomNum == 110){ //mirror
			if (Player.getInstance().curWeapon() != 1){
				enemies[numEnemies] = new Enemy(20, 115, 135);
				numEnemies++;
			}
		}
	}
	
	
	private void loadSound(){
		/* lullaby music?
		if (room == 100){
			
		}
		*/
		
		if (roomNum == 110){
			
			try {
				if (Room.clip2 != null)
					Room.clip2.stop();
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream (
				     new File("Nightmare.wav"));
				clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		   catch (LineUnavailableException  | IOException | UnsupportedAudioFileException  e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	private void readDataFile(){
		collisionBoxes = new Box[16];
		actionBoxes = new Box[16];
		transitionBoxes = new Box[4];
		enemies= new Enemy[16];
		numCollidables = 0;
		numActables = 0;
		numTransitions = 0;
		numEnemies = 0;
		int val = 0;
	
		try{	
			Scanner scan = new Scanner(new File("Images/BG/"+roomNum+".txt"));
			val = scan.nextInt();
			
			while (val != -1){ //collisionBoxes
				int x = val;
				int y = scan.nextInt();
				int x2 = scan.nextInt();
				int y2 = scan.nextInt();
				collisionBoxes[numCollidables] = new Box(x, y, x2-x, y2-y);
				numCollidables++;
				val = scan.nextInt();
			}
			
			val = scan.nextInt();
			while (val != -1){ //actionBoxes
				int x = val;
				int y = scan.nextInt();
				int x2 = scan.nextInt();
				int y2 = scan.nextInt();
				actionBoxes[numActables] = new Box(x, y, x2-x, y2-y);
				numActables++;
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

			val= scan.nextInt();
			while(val != -1){ //enemies
				int type=val;
				int x = scan.nextInt();
				int y = scan.nextInt();
				enemies[numEnemies] = new Enemy(type, x, y);
				numEnemies++;
				val=scan.nextInt();
			}
			
		} catch (IOException e){
			System.out.println("Room data file not found");
		}
	}
	
	public int getNumCollidables(){
		return numCollidables;
	}
	
	public Box getCollisionBox(int objectNumber){
		return collisionBoxes[objectNumber];
	}
	
	public int getNumActables(){
		return numActables;
	}
	
	public Box getActionBox(int actableNumber){
		return actionBoxes[actableNumber];
	}
	
	public int getNumTransitions(){
		return numTransitions;
	}
	
	public Box getTransitionBox(int transitionNumber){
		return transitionBoxes[transitionNumber];
	}
	
	public int getNumEnemies(){
		return numEnemies;
	}
	
	public Enemy getEnemy(int i){
		return enemies[i];
	}
	
	public void setRoom(int newRoom, int playerX, int playerY){
		roomNum = newRoom;
		loadImages();
		Player.getInstance().setLocation(playerX, playerY);
		framesSinceRoomEnter = 0;
	}
	
	private void upRoom(Player player){
		roomNum += 10;
		loadImages();
		player.setLocation(player.getX(), INTERNAL_HEIGHT-player.getHeight());
		framesSinceRoomEnter = 0;			
	}
	
	private void downRoom(Player player){
		roomNum -= 10;
		loadImages();
		player.setLocation(player.getX(), 0);		
		framesSinceRoomEnter = 0;		
	}
	
	private void leftRoom(Player player){
		roomNum -= 1;
		loadImages();
		player.setLocation(INTERNAL_WIDTH-player.getWidth(), player.getY());
		framesSinceRoomEnter = 0;			
	}
	
	private void rightRoom(Player player){
		roomNum = roomNum + 1;
		loadImages();
		player.setLocation(0, player.getY());
		framesSinceRoomEnter = 0;		
	}
	
	public void roomTransitionWithoutMovement(int dir){
		if (dir == UP){
			roomNum = roomNum + 10;
			loadImages();
			framesSinceRoomEnter = 0;
		}
		else if (dir == RIGHT){
			roomNum = roomNum + 1;
			loadImages();
			framesSinceRoomEnter = 0;
		}
		else if (dir == DOWN){
			roomNum = roomNum - 10;
			loadImages();
			framesSinceRoomEnter = 0;
		}
		else if (dir == LEFT){
			roomNum = roomNum - 1;
			loadImages();
			framesSinceRoomEnter = 0;
		}		
	}
	
	public void borderCollisionDetector(Player player){	
		Box playerBox = player.getHurtbox();
		for (int i=0; i < numTransitions; i++){
			if (playerBox.isOverlapped(transitionBoxes[i]))
				roomTransitionWithoutMovement(i);
		}
		
		if (playerBox.getX() + playerBox.getWidth() > INTERNAL_WIDTH){
			if (framesSinceRoomEnter > 60){
				rightRoom(player);
			}
			else{
				player.moveToPrev();
			}
		}
		else if (playerBox.getX() < 0){
			if (framesSinceRoomEnter > 60){
				leftRoom(player);
			}
			else{
				player.moveToPrev();
			}
		}
		else if (playerBox.getY() + playerBox.getHeight() > INTERNAL_HEIGHT){
			if (framesSinceRoomEnter > 60){
				downRoom(player);
			}
			else{
				player.moveToPrev();
			}
		}
		else if (playerBox.getY() < 0){
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
		for (int i=0; i < numCollidables; i++){
			if (playerBox.isOverlapped(collisionBoxes[i]))
				player.moveToPrev();
		}
	}
	
	public void envCollisionDetector(Enemy enemy){
		Box enemyBox = enemy.getHurtbox();
		for (int i=0; i < numCollidables; i++){
			if (enemyBox.isOverlapped(collisionBoxes[i]))
				enemy.moveToPrev();
		}
	}
	
	public void playerCollisionDetector(Enemy enemy){
		Player player = Player.getInstance();
		if (player.getHurtbox().isOverlapped(enemy.getHurtbox()))
			enemy.moveToPrev();
	}
	
	public void enemyCollisionDetector(Player player){
		Box playerBox = player.getHurtbox();
		for (int i=0; i < numEnemies; i++){
			if (playerBox.isOverlapped(enemies[i].getHurtbox())){
				player.moveToPrev();			
			}
		}
	}
	
	public boolean attackCollisionDetector(Box attackBox){
		for (int i=0; i < numEnemies; i++){
			if (enemies[i].attackIsOut()){
				if (attackBox.isOverlapped(enemies[i].getHurtbox())){
					enemies[i].takeDamage(1);
					return true;
				}
			}
		}
		return false;
	}
	
	public void performAction(int actionNum){
		if (roomNum == 100){
			if (actionNum == 0){ //light switch
				overlay = 1;
				/*
				try {
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream (
					     new File("Sleeping.wav"));
					clip2 = AudioSystem.getClip();
					clip2.open(audioInputStream);
					clip2.loop(Clip.LOOP_CONTINUOUSLY);

				}
			   catch (LineUnavailableException  | IOException | UnsupportedAudioFileException  e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
			}
			if (actionNum == 1){ //enter bed
				//move player into bed
				//transition
				Player player = Player.getInstance();
				player.cutscene(1);
			}
		}
		if (roomNum == 110){
			if (actionNum == 0){ //pick up mirror
				Player player = Player.getInstance();
				player.setWeapon(1);
				enemies[numEnemies-1].takeDamage(100);
				player.pause();
			//System.out.println("Escape");
			//JOptionPane.showMessageDialog(null, "Move left mapping: " + (char)moveLeftMapping);
			String Message = "<html><center>The Mirror<BR>" + 
			"--------------------<BR>" +
					"These creatures may be scary, but <BR>you are not entirely defenseless!<BR>" +
			"--------------------<BR>" +
					"Use the mirror to reflect enemy attacks!<BR>" +
					"Be careful to time when you use your mirror <BR>for when the enemy is about to attack!<BR>" +
					"</center></html>";
			//JOptionPane.showMessageDialog(null, Message);
			JFrame ex = new PauseMenu(Message, true, false);
			ex.setVisible(true);
			}
		}
	}
	
	public Image getBgImage(){
		return bgImage;
	}
	
	public Image getFgImage(){
		return fgImage;
	}
	
	public void addOverlay(Graphics g){
		framesSinceRoomEnter++;	
		if (overlay == 0){ //no overlay
			return;
		}
		else if (overlay == 1){ //full black
			g.setColor(new Color(0, 0, 0, overlayIntensity));
			g.fillRect(0, 0, Global.size*640, Global.size*360);
		}
		else if (overlay == 2){ //flashlight mode
			return;
		}
	}
	
	public void moveEnemies(){
		for (int i=0; i<numEnemies; i++){
			enemies[i].aiAction();
		}
	}
	
	public boolean checkForVictory(){
		if (roomNum == 110){
			if (enemies[0].isDead()){
				Player.getInstance().cutscene(100);
				return true;
			}
		}		
		return false;
	}
	
	public void changeOverlay(int num){
		overlay = num;
	}
	
	public void changeOverlay(int num, int intensity){
		overlay = num;
		overlayIntensity = intensity;
	}
	
	public int getRoomNum(){
		return roomNum;
	}
}
