import java.awt.event.KeyEvent;//can do multiple inheritance in C++ but not in Java -- instead, use interfaces in Java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

import java.awt.Image;

class Enemy extends Character implements GameInfo{
	private int enemyType;
	private int aiFrames;
	
	public Enemy(){

	}

	public Enemy(int type, int X, int Y){
		aiFrames = -1;
		enemyType = type;
		movable = true;
		attackHit = false;		
		x = X;
		y = Y;
		if(enemyType==1){
			initializeAdultFemaleSoul();
		}
		else if(enemyType==2){
			initializeAdultMaleSoul();
		}
		else if (enemyType==3){
			initializeBlob();
		}
		else if (enemyType == 20){
			initializeMirror();
		}
	}

	private void initializeAdultFemaleSoul(){
		Box hit = new Box(10, 0, 108, 124);		
		Box hurt = new Box(10, 0, 108, 124);
	
		width = 124;
		height = 124;	
		initChar("AF", hurt, hit);
		curHP = 4;

	}

	private void initializeAdultMaleSoul(){
		Box hit = new Box(0, 0, 64, 64);		
		Box hurt = new Box(21, 21, 43, 43);
		
		initChar("MF", hurt, hit);
	}
	
	private void initializeBlob(){
		Box hit = new Box(0, 0, 64, 64);
		Box hurt = new Box(13, 13, 38, 38);
		
		initChar("Blob", hurt, hit);
		curHP = 1;
	}
	
	private void initializeMirror(){
		Box hit = new Box(0, 0, 0, 0);
		Box hurt = new Box(0, 0, 0, 0);
		
		initChar("Mirror", hurt, hit);
	}
	
	public void aiAction(){
		if (enemyType == 1)
			adultFemaleAI();
		else if (enemyType == 2)
			adultMaleAI();
		else if (enemyType == 3)
			blobAI();
		else if (enemyType == 20)
			mirrorAI();
	}
	
	private void blobAI(){
		curDirection = DOWN;
		attack();
		Room room = Room.getInstance();
		
		room.envCollisionDetector(this);
		room.playerCollisionDetector(this);			
	}
	
	private void adultFemaleAI(){
		if (movable == false)
			return;
		Player player = Player.getInstance();
		Room room = Room.getInstance();
		
		int px = player.getX();
		int py = player.getY();	
		int hor = x - px + 32;
		int vert = y - py + 32;
		int size = 50;
		
		if (Math.abs(hor) > Math.abs(vert)){
			if (hor < 0){
				curDirection = RIGHT;
				if (px > x + size + 124)
					changeLocation(1, 0);
				else
					attack();
			}
			else{
				curDirection = LEFT;
				if (px + 32 < x - size)
					changeLocation(-1, 0);
				else
					attack();
			}
		}
		else{
			if (vert < 0){
				curDirection = DOWN;
				if (py > y + size + 124)
					changeLocation(0, 1);
				else
					attack();
			}
			else{
				curDirection = UP;
				if (py + 64 < y - size)
					changeLocation(0, -1);
				else
					attack();
			}
		}
		
		/*if (Math.abs(vert) < 40 || Math.abs(hor) < 40) {
			attack();
		}*/
		
		room.envCollisionDetector(this);
		room.playerCollisionDetector(this);		
	}
	
	private void adultMaleAI(){
		
	}
	
	private void mirrorAI(){
		curState = 0;
		aiFrames++;
		if (aiFrames < 30)
			curDirection = UP;
		else if (aiFrames < 60)
			curDirection = RIGHT;
		else if (aiFrames < 90)
			curDirection = DOWN;
		else
			aiFrames = -1;
	}
	
	private boolean enemyAttackCollisionDetector(){
		Player player = Player.getInstance();
		if (hitbox.isOverlapped(player.getHurtbox())){
			player.takeDamage(1);
			return true;
		}
		return false;
	}
	
	
	private boolean adultFemaleAttack(){
		if (curDirection == UP){
			hitbox.changeOffset(x, y-40);
		}
		else if (curDirection == RIGHT){
			hitbox.changeOffset(x+40, y);
		}
		else if (curDirection == DOWN){
			hitbox.changeOffset(x, y+40);
		}
		else { //LEFT
			hitbox.changeOffset(x-40, y);
		}
		return enemyAttackCollisionDetector();			
	}
	
	private boolean blobAttack(){
		hitbox.changeOffset(x, y);
		return enemyAttackCollisionDetector();
	}
	
	@Override
	protected boolean attackHelper(){ //returns true if hit else false
		if (enemyType == 1)
			adultFemaleAttack();
		if (enemyType == 3)
			blobAttack();
		return false;
	}
	
	@Override
	public Image getAttackImage(){
		attackFrames++;
		if (attackFrames < 40){ //attack decision
		
		}
		else if (attackFrames < 80){ //attack windup
			return attackAnimation[attackDirection][0];
		}
		else if (attackFrames < 110){ //hitbox is out
			if (attackHelper()) //if attack hit
				attackHit = true;
			if (attackHit)
				return attackAnimation[attackDirection][3];
			return attackAnimation[attackDirection][1];
		}
		else if (attackFrames == 110){ //attack has ended
			movable = true;
			hitbox.changeOffset(10000, 10000);
			attackHit = false;
		}
		else if (attackFrames > 120){ //can attack again
			attackFrames = -1;
		}
		return getWalkImage();
	}
	
		public boolean attackIsOut(){
			if (attackFrames >= 75 && attackFrames <= 110)
				return true;
			return false;
		}
	
	@Override
	public Image getWalkImage(){
		return idleAnimation[curDirection];		
	}	
}
