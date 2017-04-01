import java.awt.event.KeyEvent;//can do multiple inheritance in C++ but not in Java -- instead, use interfaces in Java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//import GameInfo.Global;

public class Player extends Character implements GameInfo{
	private static Player instance = null;
	private int speed = 2;
	private int xDir = 0;
	private int yDir = 0;
	private boolean keyDown[];
	
	private boolean actable = false;
	private int cutsceneNumber = 0;
	private int cutsceneFrame = 0;
	
	private int moveLeftMapping;
	private int moveRightMapping;
	private int moveUpMapping;
	private int moveDownMapping;
	private int attackOneMapping;
	private int actionButtonMapping;

	
	public static Player getInstance(){
		if (instance == null){
			instance = new Player();
		}
		return instance;
	}
	
	protected Player(){
		name = "MC";
		
		hurtbox = new Box(13, 38, 40, 22);
		hurtbox.changeOffset(x, y);
		vulnerable = true;
		loadAnimations();		
		setMaxHP(4);
		keyDown = new boolean[4];
		for (int i=0; i<4; i++) {
			keyDown[i] = false;
		}
		
		setLocation(293, 55);
		curDirection = DOWN;
		
		readFile();//this sets vals for mappings declared at approx 14-19
		actable = true;
	}
	
	public void setUnactable(){
		actable = false;
	}
	
	public void setActable(){
		actable = true;
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
	
	public void keyPressed(KeyEvent e) {//e is an addy to an instance of the object
		if (actable == false)
			return;
		int key = e.getKeyCode();
		
		//REMOVE once keymapping 100% works
		if (key == KeyEvent.VK_LEFT) {
			keyPressedHelper(LEFT, -1, 0);
		}
		else if (key == KeyEvent.VK_RIGHT) {	
			keyPressedHelper(RIGHT, 1, 0);		
		}
		else if (key == KeyEvent.VK_UP) {
			keyPressedHelper(UP, 0, -1);			
		}
		else if (key == KeyEvent.VK_DOWN) {	
			keyPressedHelper(DOWN, 0, 1);
		}
		//REMOVE once keymapping 100% works
		else if (key == actionButtonMapping){
			performAction();
		}
		else if (key == moveLeftMapping) {
			keyPressedHelper(3, -1, 0);               //same as VK_LEFT
		}
		else if (key == moveRightMapping) {	
			keyPressedHelper(1, 1, 0);                //same as VK_RIGHT	
		}
		else if (key == moveUpMapping) {
			keyPressedHelper(0, 0, -1);               //same as VK_UP		
		}
		else if (key == moveDownMapping) {	
			keyPressedHelper(2, 0, 1);                //same as VK_DOWN
		}
		else if (key == attackOneMapping || key == actionButtonMapping) {
			curState = 2;
		}
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
			attackOneMapping = reader.read();
			actionButtonMapping = reader.read();
			System.out.println(" move left mapping = " + moveLeftMapping);
			System.out.println(" move right mapping = " + moveRightMapping);
			System.out.println(" move up mapping = " + moveUpMapping);
			System.out.println(" move right mapping = " + moveDownMapping);
			System.out.println(" attack one mapping = " + attackOneMapping);
			System.out.println(" attack two mapping = " + actionButtonMapping);

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

		if (key == KeyEvent.VK_LEFT || key == moveLeftMapping){
			keyDown[LEFT] = false;
			xDir = 0;
			checkOtherKeys();
		}
		else if (key == KeyEvent.VK_RIGHT || key == moveRightMapping){
			keyDown[RIGHT] = false;
			xDir = 0;	
			checkOtherKeys();			
		}
		else if (key == KeyEvent.VK_UP || key == moveUpMapping){
			keyDown[UP] = false;
			yDir = 0;
			checkOtherKeys();			
		}
		else if (key == KeyEvent.VK_DOWN || key == moveDownMapping){
			keyDown[DOWN] = false;
			yDir = 0;
			checkOtherKeys();			
		}
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
		cutsceneNumber = number;
	}
	
	public boolean checkCutscene(){
		if (cutsceneNumber == 0)
			return false;
		
		cutsceneFrame++;
		if (cutsceneNumber == 1){ //enter bed
			if (y > 65){
				changeLocation(0, -1);
			}
			else if (x < 478){
				changeLocation(1, 0);
			}
			else if (cutsceneFrame <= 180){
				curDirection = DOWN;
			}
			else{
				setActable();
				cutsceneNumber = 0;
			}
		}
		return true;
	}
}
