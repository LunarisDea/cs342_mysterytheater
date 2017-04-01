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
	
	private int frameCounter;
	private Image[] idleAnimation = new Image[4];
	private Image[][] walkAnimation = new Image[4][4];
	private Image[][] attackAnimation = new Image[4][4];
	
	private int width = 64;
	private int height = 64;
	private Image charImage;
	String name;
	
	public Character(){
		
	}
	
	public Character(String fName){
		initChar(fName);
	}
	
	public Character(String fName, int MaxHP){
		maxHP = MaxHP;
		initChar(fName);
	}
	
	private void initChar(String fName){
		hurtbox = new Box(13, 48, 40, 12);
		hurtbox.changeOffset(x, y);
		
		frameCounter = 0;
		curState = 0;
		curDirection = DOWN;
		name = fName;
		loadAnimations();
		vulnerable = true;		
	}
	
	public void loadAnimations(){
		for (int j = 0; j<4; j++){
			ImageIcon ii = new ImageIcon("Images/"+name+"/" + j + ".png");
			idleAnimation[j] = ii.getImage();
			idleAnimation[j] =  idleAnimation[j].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
			for (int i = 0; i<4; i++){		
				ii = new ImageIcon("Images/"+name+"/" + "w" + j + i + ".png");
				//walkAnimation[i] = ii.getImage();
				walkAnimation[j][i] = ii.getImage();
				walkAnimation[j][i] = walkAnimation[j][i].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
				
				ii = new ImageIcon("Images/"+name+"/" + "a" + j + i + ".png");
				attackAnimation[j][i] = ii.getImage();
				attackAnimation[j][i] = attackAnimation[j][i].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
			}
		}
	}
	
	public Image getCurImage(){
		if (curState == 0){
			return idleAnimation[curDirection];
		}
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
	
	public Box getHurtbox(){
		return hurtbox;
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
		curHP = curHP - damage;
		if (curHP <= 0){
			//character died
		}
	}
	
	public void setLocation(int xLoc, int yLoc){
		x = xLoc;
		y = yLoc;
		hurtbox.changeOffset(x, y);
	}
	
	public void changeLocation(int xChange, int yChange){
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
