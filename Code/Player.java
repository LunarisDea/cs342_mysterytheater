import java.awt.event.KeyEvent;//can do multiple inheritance in C++ but not in Java -- instead, use interfaces in Java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//import GameInfo.Global;

public class Player extends Character implements GameInfo{
	private static Player instance = null;
	private int speed = 2;
	private int xDir = 0;
	private int yDir = 0;
	private boolean keyDown[];
	private int weapon;
	private boolean pause = false;
	
	private int cutsceneNumber = 0;
	private int cutsceneFrame = -1;
	
	private int moveLeftMapping;
	private int moveRightMapping;
	private int moveUpMapping;
	private int moveDownMapping;
	private int attackMapping;
	private int actionButtonMapping;
	
	public static Player getInstance(){
		if (instance == null){
			instance = new Player();
		}
		return instance;
	}
	
	protected Player(){
		movable = true;
		attackHit = false;
		weapon = 0;
		Box hit = new Box(0, 0, 32, 32);		
		Box hurt = new Box(13, 30, 40, 30);
		
		initChar("MC", hurt, hit);

		keyDown = new boolean[5]; //UP, RIGHT, DOWN, LEFT, attackkey
		for (int i=0; i<5; i++) {
			keyDown[i] = false;
		}
		
		setLocation(293, 55);
		
		readFile();//this sets vals for mappings declared at approx 14-19
	}
	
	public void move(){
		if (xDir == 0 && yDir == 0){
			curState = 0;
		}
		else if (xDir != 0){ 
			if (xDir < 0) 
				curDirection = LEFT;
			else
				curDirection = RIGHT;
			changeLocation(xDir*speed, 0);
		}
		else{
			if (yDir < 0)
				curDirection = UP;
			else
				curDirection = DOWN;
			changeLocation(0, yDir*speed);	
		}			
	}
	
	private void keyPressedHelper(int key, int xdir, int ydir){
		curState = 1;
		keyDown[key] = true;
		curDirection = key;
		xDir = xdir;
		yDir = ydir;
	}
	
	private void performAction(){
		Room curRoom = Room.getInstance();

		for (int i=0; i < curRoom.getNumActables(); i++){
			if (hurtbox.isOverlapped(curRoom.getActionBox(i))){
				curRoom.performAction(i);
			}	
		}		
	}
	
	public void setWeapon(int weaponNum){
		weapon = weaponNum;
	}
	
	public int curWeapon(){
		return weapon;
	}
	
	public void keyPressed(KeyEvent e) {//e is an addy to an instance of the object
		if (actable == false || pause == true)
			return;
		int key = e.getKeyCode();
		//System.out.println("Number" + key);
		
		if (key == moveLeftMapping) {
			keyPressedHelper(3, -1, 0);
		}
		else if (key == moveRightMapping) {	
			keyPressedHelper(1, 1, 0);
		}
		else if (key == moveUpMapping) {
			keyPressedHelper(0, 0, -1);
		}
		else if (key == moveDownMapping) {	
			keyPressedHelper(2, 0, 1);
		}
		else if (key == actionButtonMapping){
			performAction();
		}
		else if (keyDown[4] == false && key == attackMapping){
			if (weapon != 0)
				attack();
			keyDown[4] = true;
		}
		else if (key == 27) {
			pause();
			//System.out.println("Escape");
			//JOptionPane.showMessageDialog(null, "Move left mapping: " + (char)moveLeftMapping);
			String Message = "<html><center>Mystery Theater<BR>" + 
			"--------------------<BR>" +
					"Hit return to resume play<BR>" +
			"--------------------<BR>" +
					"Move left: " + (char)moveLeftMapping + "<BR>" +
					"Move right: " + (char)moveRightMapping + "<BR>" +
					"Move up: " + (char)moveUpMapping + "<BR>" +
					"Move down: " + (char)moveDownMapping + "<BR>" +
					"Move attack: " + (char)attackMapping + "<BR>" +
					"Move action: " + (char)actionButtonMapping + "<BR>" +
					"</center></html>";
			//JOptionPane.showMessageDialog(null, Message);
			JFrame ex = new PauseMenu(Message, true, true);
			ex.setVisible(true);
		}
		//else if (key == )
	}

	public void readFile() {
		File file = new File("GameSettings.txt");
		try {
			FileReader reader = new FileReader(file);
			int resolution = reader.read();
			moveLeftMapping = reader.read();
			moveRightMapping = reader.read();
			moveUpMapping = reader.read();
			moveDownMapping = reader.read();
			attackMapping = reader.read();
			actionButtonMapping = reader.read();

			System.out.println(" move left mapping = " + moveLeftMapping);
			System.out.println(" move right mapping = " + moveRightMapping);
			System.out.println(" move up mapping = " + moveUpMapping);
			System.out.println(" move down mapping = " + moveDownMapping);
			System.out.println(" attack mapping = " + attackMapping);
			System.out.println(" action button mapping = " + actionButtonMapping);
			Global.size = resolution;
			reader.close();
		} catch (IOException e) {
			//TODO Auto-generated catch block
			//e.printStackTrace();
			//GameLauncher gl = new GameLauncher();
			System.exit(-1);
		}		
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();

		if (key == moveLeftMapping){
			keyDown[LEFT] = false;
			xDir = 0;
			checkOtherKeys();
		}
		else if (key == moveRightMapping){
			keyDown[RIGHT] = false;
			xDir = 0;	
			checkOtherKeys();			
		}
		else if (key == moveUpMapping){
			keyDown[UP] = false;
			yDir = 0;
			checkOtherKeys();			
		}
		else if (key == moveDownMapping){
			keyDown[DOWN] = false;
			yDir = 0;
			checkOtherKeys();			
		}
		else if (key == attackMapping){
			keyDown[4] = false;
		}
	}
	
	public void releaseKeys(){
		keyDown[LEFT] = false;
		keyDown[RIGHT] = false;
		keyDown[UP] = false;
		keyDown[DOWN] = false;
		keyDown[4] = false;
		xDir = 0;
		yDir = 0;
	}
	
	public void checkOtherKeys(){
		if (keyDown[UP]){
			yDir = -1;
		}
		else if (keyDown[RIGHT]){
			xDir = 1;
		}
		else if (keyDown[DOWN]){
			yDir = 1;
		}
		else if (keyDown[LEFT]){
			xDir = -1;
		}
	}
	
	public void cutscene(int number){
		setUnactable();
		releaseKeys();		
		cutsceneNumber = number;
	}
	
	private void endCutscene(){
		special = -1;
		setActable();
		cutsceneNumber = 0;
		cutsceneFrame = -1;
	}
	
	public boolean checkCutscene(){
		if (cutsceneNumber == 0)
			return false;
		
		cutsceneFrame++;
		if (cutsceneNumber == 2){ //yawn
			if (cutsceneFrame <= 60)
				special = -1;
			else if (cutsceneFrame <= 120)
				special = 1;
			else if (cutsceneFrame <= 180)
				special = 2;
			else{
				endCutscene();
			}
		}
		else if (cutsceneNumber == 1){ //enter bed
			if (y > 65){
				changeLocation(0, -1);
			}
			else if (y < 65){
				changeLocation(0, 1);
			}
			else if (x < 478){
				changeLocation(1, 0);
			}
			else if (cutsceneFrame <= 240){ 	
				special = 0;
				curDirection = DOWN;
			}
			else{
				endCutscene();
				Room.getInstance().changeOverlay(0);
			}
		}
		else if (cutsceneNumber == 100){ //victory
		special = -1;
			if (cutsceneFrame == 120){
				Room.getInstance().setRoom(0, 475, 65);;
				curDirection = DOWN;
				special = 0;
			}
			else if (cutsceneFrame <= 240){
				special = 0;
			}
			else if (cutsceneFrame == 241){
				pause();
				//victory
				String Message = "<html><center>Mystery Theater<BR>" + 
				"--------------------<BR>" +
						"Victory!!<BR>" +
				"--------------------<BR>" +
						"" + "<BR>" +
						"" +"<BR>" +
						"Congratulations on beating our game!" + "<BR>" +
						"We hope you enjoyed it!" + "<BR>" +
						"</center></html>";
				//JOptionPane.showMessageDialog(null, Message);
				JFrame ex = new PauseMenu(Message, false, true);
				ex.setVisible(true);
			}
		}
		return true;
	}
	
	@Override
	public boolean isPlayer(){
		return true;
	}
	
	public void pause(){
		releaseKeys();
		pause = true;
	}
	
	public void unPause(){
		pause = false;
	}
	public boolean isPaused(){
		return pause;
	}
}
