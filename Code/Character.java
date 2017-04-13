import java.awt.Image;
import javax.swing.ImageIcon;

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
	
	protected int attackFrames;
	protected int attackDirection;
	protected boolean attackHit;
	protected Box hitbox;
	
	private int frameCounter;
	private Image[] idleAnimation = new Image[4];
	private Image[] damageAnimation = new Image[4];
	private Image[][] walkAnimation = new Image[4][4];
	private Image[][] attackAnimation = new Image[4][4];
	private Image[] specialImages = new Image[1];
	protected int special = -1;
	
	private int width = 64;
	private int height = 64;
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
	}
	
	public void loadAnimations(){
		String fName = "Images/"+name+"/";
		ImageIcon ii = new ImageIcon(fName + "s0.png");
		specialImages[0] = ii.getImage();
		specialImages[0] = specialImages[0].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
		
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
		if (isAttacking()){
			return getAttackImage();
		}
		else if (special != -1){
			return specialImages[special];
		}	
		else if (invulnFrames != -1){
			invulnFrames++;
			if (invulnFrames >= 50){
				vulnerable = true;
				invulnFrames = -1;
			}
			return damageAnimation[curDirection];
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
		if (vulnerable == false)
			return;
		curHP = curHP - damage;
		if (curHP <= 0){
			changeLocation(5000, 5000); //temp
		}
		vulnerable = false;
		invulnFrames = 0;
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
