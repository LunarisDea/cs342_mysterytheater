import java.awt.event.KeyEvent;//can do multiple inheritance in C++ but not in Java -- instead, use interfaces in Java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Image;

class Enemy extends Character implements GameInfo{
	
	public Enemy(){

	}

	public Enemy(int type, int X, int Y){
		movable = true;
		attackHit = false;		
		x = X;
		y = Y;
		if(type==1){
			initializeAdultFemaleSoul();
		}
		if(type==2){
			initializeAdultMaleSoul();
		}
	}

	private void initializeAdultFemaleSoul(){
		Box hit = new Box(0, 0, 64, 64);		
		Box hurt = new Box(0, 0, 64, 64);
		
		initChar("AF", hurt, hit);
	}

	private void initializeAdultMaleSoul(){
		Box hit = new Box(20, 20, 32, 32);		
		Box hurt = new Box(13, 38, 40, 22);
		
		initChar("MF", hurt, hit);
	}
	
	public void aiAction(){
		Player player = Player.getInstance();
		Room room = Room.getInstance();
		if (player.getX() > x + 80){
			curDirection = RIGHT;
			changeLocation(1, 0);
		}
		else if (player.getX() < x - 80){
			curDirection = LEFT;
			changeLocation(-1, 0);
		}
		else if (player.getY() > y + 50){
			curDirection = DOWN;
			changeLocation(0, 1);
		}
		else if (player.getY() < y - 80){
			curDirection = UP;
			changeLocation(0, -1);
		}
		else {
			attack();
		}
		room.envCollisionDetector(this);
		room.playerCollisionDetector(this);
	}
	
	private boolean enemyAttackCollisionDetector(){
		Player player = Player.getInstance();
		if (hitbox.isOverlapped(player.getHurtbox())){
			player.takeDamage(1);
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean attackHelper(){ //returns true if hit else false
		if (curDirection == UP){
			hitbox.changeOffset(x, y-20);
		}
		else if (curDirection == RIGHT){
			hitbox.changeOffset(x+20, y);
		}
		else if (curDirection == DOWN){
			hitbox.changeOffset(x, y+20);
		}
		else { //LEFT
			hitbox.changeOffset(x-20, y);
		}
		return enemyAttackCollisionDetector();			
	}
	
	@Override
	public Image getAttackImage(){
		attackFrames++;
		if (attackFrames < 60){ //attack windup
			return getWalkImage();
		}
		else if (attackFrames < 100){ //hitbox is out
			if (attackHelper()) //if attack hit
				attackHit = true;
			if (attackHit)
				return attackAnimation[attackDirection][3];
			return attackAnimation[attackDirection][0];
		}
		else if (attackFrames == 100){ //attack has ended
			movable = true;
			hitbox.changeOffset(10000, 10000);
			attackHit = false;
		}
		else if (attackFrames > 120){ //can attack again
			attackFrames = -1;
		}
		return getWalkImage();
	}
	
	@Override
	public Image getWalkImage(){
		return idleAnimation[curDirection];		
	}	
}
