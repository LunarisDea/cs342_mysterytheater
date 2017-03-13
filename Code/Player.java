import java.awt.event.KeyEvent;

public class Player extends Character implements GameInfo{
	private int speed = 2;
	private int xDir = 0;
	private int yDir = 0;
	private boolean keyDown[];
	
	public Player(String fName){
		name = fName;
		//loadImage(fName);
		vulnerable = true;
		loadAnimations();		
		setMaxHP(4);
		keyDown = new boolean[4];
		for (int i=0; i<4; i++)
			keyDown[i] = false;
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
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_A){
			curState = 2;
		}
		else if (key == KeyEvent.VK_LEFT){
			keyDown[3] = true;
			curState = 1;
			xDir = -1;	
			curDirection = 3;
			yDir = 0;
		}
		else if (key == KeyEvent.VK_RIGHT){	
			keyDown[1] = true;
			curState = 1;		
			xDir = 1;
			curDirection = 1;
			yDir = 0;
		}
		else if (key == KeyEvent.VK_UP){
			keyDown[0] = true;
			curState = 1;
			yDir = -1;
			curDirection = 0;
			xDir = 0;
		}
		else if (key == KeyEvent.VK_DOWN){	
			keyDown[2] = true;
			curState = 1;
			yDir = 1;
			curDirection = 2;
			xDir = 0;
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