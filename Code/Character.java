import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Character implements GameInfo{	
	protected int x; private int prevX;
	protected int y; private int prevY;
	protected int curHP;
	protected int maxHP;
	protected Box hurtbox;
	protected int curState;
	protected int curDirection;
	protected boolean vulnerable;
	protected boolean movable;
	protected int invulnFrames;
	protected boolean dead;
	protected boolean actable;
	
	protected int attackFrames;
	protected int attackDirection;
	protected boolean attackHit;
	protected Box hitbox;
	
	private int frameCounter;
	protected Image[] idleAnimation = new Image[4];
	protected Image[] damageAnimation = new Image[4];
	protected Image[][] walkAnimation = new Image[4][4];
	protected Image[][] attackAnimation = new Image[4][4];
	protected Image[] specialImages = new Image[4];
	protected int special = -1;
	
	protected int width = 64;
	protected int height = 64;
	private Image charImage;
	String name;
	
	public Character(){
		
	}

	protected void initChar(String fName, Box hurt, Box hit){
		hurtbox = hurt;
		hurtbox.changeOffset(x, y);
		vulnerable = true;
		maxHP = 4;
		curHP = 4;
		
		hitbox = hit;
		hitbox.changeOffset(10000, 10000);
		attackFrames = -1;
		invulnFrames = -1;
		
		frameCounter = 0;
		curState = 0;
		curDirection = DOWN;
		name = fName;
		loadAnimations();
		vulnerable = true;
		dead = false;
		actable = true;
	}	
	
	public void loadAnimations(){
		String fName = "Images/"+name+"/";
		ImageIcon ii;
		for (int i=0; i<3; i++){
			ii = new ImageIcon(fName + "s" + i + ".png");
			specialImages[i] = ii.getImage();
			specialImages[i] = specialImages[i].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
		}
		
		for (int j = 0; j<4; j++){
			ii = new ImageIcon(fName + j + ".png");
			idleAnimation[j] = ii.getImage();
			idleAnimation[j] =  idleAnimation[j].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
			
			ii = new ImageIcon(fName + "d" + j + ".png");
			damageAnimation[j] = ii.getImage();
			damageAnimation[j] =  damageAnimation[j].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);			
			
			for (int i = 0; i<4; i++){		
				ii = new ImageIcon(fName + "w" + j + i + ".png");
				//walkAnimation[i] = ii.getImage();
				walkAnimation[j][i] = ii.getImage();
				walkAnimation[j][i] = walkAnimation[j][i].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
				
				ii = new ImageIcon(fName + "a" + j + i + ".png");
				attackAnimation[j][i] = ii.getImage();
				attackAnimation[j][i] = attackAnimation[j][i].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
			}
		}
	}
	
	public Image getAttackImage(){
		attackFrames++;
		if (attackFrames <= 30){ //hitbox is out
			if (attackHelper()) //if attack hit
				attackHit = true;
			if (attackHit)
				return attackAnimation[attackDirection][3];
			return attackAnimation[attackDirection][0];
		}
		else if (attackFrames == 31){ //attack has ended
			movable = true;
			hitbox.changeOffset(10000, 10000);
			attackHit = false;
		}
		else if (attackFrames > 60){ //can attack again
			attackFrames = -1;
		}
		return getWalkImage();
	}
	
	public Image getWalkImage(){
		if (curState == 0)
			return idleAnimation[curDirection];
		int curFrame = 0;
		if (frameCounter % 60 < 15)
			curFrame = 0;
		else if (frameCounter % 60 < 30)
			curFrame = 1;
		else if (frameCounter % 60 < 45)
			curFrame = 2;					
		else
			curFrame = 3;
		frameCounter++;
		
		if (frameCounter == 600) //max 10 seconds per animation
			frameCounter = 0;
		
		return walkAnimation[curDirection][curFrame];		
	}
	
	public Image getCurImage(){
		if (dead){
			return null;
		}
		if (invulnFrames != -1){
			invulnFrames++;
			if (invulnFrames >= 30){
				vulnerable = true;
				invulnFrames = -1;
			}
			return damageAnimation[curDirection];
		}
		vulnerable = true;
		if (isAttacking()){
			return getAttackImage();
		}
		else if (special != -1){
			return specialImages[special];
		}	
		else
			return getWalkImage();
	}
	
	public Box getHurtbox(){
		return hurtbox;
	}
	
	public Box getHitbox(){
		return hitbox;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getCurHP(){
		return curHP;
	}
	
	public int getMaxHP(){
		return maxHP;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setUnactable(){
		actable = false;
	}
	
	public void setActable(){
		actable = true;
	}	
	
	public boolean isVulnerable(){
		return vulnerable;
	}
	
	public void makeVulnerable(){
		vulnerable = true;
	}
	
	public void makeInvulnerable(){
		vulnerable = false;
	}
	
	public void increaseMaxHP(int increase){
		curHP += increase;
		maxHP += increase;
	}
	
	public void setMaxHP(int newMaxHP){
		maxHP = newMaxHP;
		curHP = maxHP;
	}
	
	public void takeDamage(int damage){
		//vulnerable = false; //DELETE
		if (vulnerable == false)
			return;
		if (attackFrames != -1){
			movable = true;
			hitbox.changeOffset(10000, 10000);
			attackHit = false;
			attackFrames = -1;
		}
		curHP = curHP - damage;	
		if (curHP <= 0){
			died();
			if (isPlayer()){
								String Message = "<html><center>Mystery Theater<BR>" + 
				"--------------------<BR>" +
						"You have been defeated!<BR>" +
				"--------------------<BR>" +
						"" + "<BR>" +
						"" +"<BR>" +
						"Better luck next time!" + "<BR>" +
						"</center></html>";
				//JOptionPane.showMessageDialog(null, Message);
				JFrame ex = new PauseMenu(Message, false, true);
				ex.setVisible(true);
			}
				
		}
		vulnerable = false;
		invulnFrames = 0;
	}
	
	public boolean isPlayer(){
		return false;
	}
	
	private void died(){
		hurtbox.makeInactive();
		hitbox.makeInactive();
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	protected boolean attackHelper(){ //returns true if hit else false
		if (attackFrames == 1){
			if (curDirection == UP){
				hitbox.changeOffset(x+17, y);
			}
			else if (curDirection == RIGHT){
				hitbox.changeOffset(x+50, y+30);
			}
			else if (curDirection == DOWN){
				hitbox.changeOffset(x+16, y+60);
			}
			else { //LEFT
				hitbox.changeOffset(x-18, y+30);
			}
		}
		return Room.getInstance().attackCollisionDetector(hitbox);			
	}
	
	protected void attack(){
		if (attackFrames != -1)
			return;
		
		movable = false;
		attackDirection = curDirection;
		attackFrames = 0;
	}
		
	public boolean isAttacking(){
		if (attackFrames == -1)
			return false;
		return true;
	}
		
	public void setLocation(int xLoc, int yLoc){
		x = xLoc;
		y = yLoc;
		hurtbox.changeOffset(x, y);
	}
	
	public void changeLocation(int xChange, int yChange){
		if (!movable)
			return;
		prevX = x;
		prevY = y;
		x += xChange;
		y += yChange;
		hurtbox.changeOffset(x, y);
	}
	
	public void moveToPrev(){
		x = prevX;
		y = prevY;
		hurtbox.changeOffset(x, y);
	}
}
