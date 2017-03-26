import java.awt.event.KeyEvent;//can do multiple inheritance in C++ but not in Java -- instead, use interfaces in Java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//import GameInfo.Global;

public class Player extends Character implements GameInfo{
	private int speed = 2;
	private int xDir = 0;
	private int yDir = 0;
	private boolean keyDown[];
	
	private int moveLeftMapping;
	private int moveRightMapping;
	private int moveUpMapping;
	private int moveDownMapping;
	private int attackOneMapping;
	private int attackTwoMapping;

	
	public Player(String fName){
		name = fName;
		//loadImage(fName);
		vulnerable = true;
		loadAnimations();		
		setMaxHP(4);
		keyDown = new boolean[4];
		for (int i=0; i<4; i++) {
			keyDown[i] = false;
		}
		readFile();//this sets vals for mappings declared at approx 14-19
	}
	
	public void move(){
		if (xDir == 0 && yDir == 0){
			curState = 0;
		}
		else if (xDir != 0){ 
			if (xDir < 0) 
				curDirection = 3;
			else
				curDirection = 1;
			changeLocation(xDir*speed, 0);
		}
		else{
			if (yDir < 0)
				curDirection = 0;
			else
				curDirection = 2;
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
	
	public void keyPressed(KeyEvent e) {//e is an addy to an instance of the object
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A) {
			curState = 2;
		}
		else if (key == KeyEvent.VK_LEFT) {
			keyPressedHelper(3, -1, 0);
		}
		else if (key == KeyEvent.VK_RIGHT) {	
			keyPressedHelper(1, 1, 0);		
		}
		else if (key == KeyEvent.VK_UP) {
			keyPressedHelper(0, 0, -1);			
		}
		else if (key == KeyEvent.VK_DOWN) {	
			keyPressedHelper(2, 0, 1);
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
		else if (key == attackOneMapping || key == attackTwoMapping) {
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
			attackTwoMapping = reader.read();
			System.out.println(" move left mapping = " + moveLeftMapping);
			System.out.println(" move right mapping = " + moveRightMapping);
			System.out.println(" move up mapping = " + moveUpMapping);
			System.out.println(" move right mapping = " + moveDownMapping);
			System.out.println(" attack one mapping = " + attackOneMapping);
			System.out.println(" attack two mapping = " + attackTwoMapping);
			//could let player choose chars for mapping
			//by updating GameLauncher by adding to GUI
			//hitting any key needs to go to event handler
			//doing mapping: "if key pressed

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
		
		if (key == KeyEvent.VK_LEFT ){
			keyDown[3] = false;
			xDir = 0;
			checkOtherKeys();
		}
		else if (key == KeyEvent.VK_RIGHT){
			keyDown[1] = false;
			xDir = 0;	
			checkOtherKeys();			
		}
		else if (key == KeyEvent.VK_UP){
			keyDown[0] = false;
			yDir = 0;
			checkOtherKeys();			
		}
		else if (key == KeyEvent.VK_DOWN){
			keyDown[2] = false;
			yDir = 0;
			checkOtherKeys();			
		}
	}
	
	public void checkOtherKeys(){
		if (keyDown[0]){
			yDir = -1;
		}
		else if (keyDown[1]){
			xDir = 1;
		}
		else if (keyDown[2]){
			yDir = 1;
		}
		else if (keyDown[3]){
			xDir = -1;
		}
	}
}
