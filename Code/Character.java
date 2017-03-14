import java.awt.Image;
import javax.swing.ImageIcon;

public class Character implements GameInfo{	
	private int x;
	private int y;
	private int curHP;
	private int maxHP;
	private Box hurtbox;
	protected int curState;
	protected int curDirection;
	protected boolean vulnerable;
	
	private int frameCounter;
	private Image[] idleAnimation = new Image[4];
	private Image[][] walkAnimation = new Image[4][2];
	private Image[][] attackAnimation = new Image[4][2];
	
	private int width = 64;
	private int height = 64;
	private Image charImage;
	String name;
	
	public Character(){
		
	}
	
	public Character(String fName){
		frameCounter = 0;
		curState = 0;
		curDirection = 0;
		name = fName;
		loadAnimations();
		vulnerable = true;
	}
	
	public Character(String fName, int MaxHP){
		frameCounter = 0;
		curState = 0;
		maxHP = MaxHP;
		curHP = maxHP;
		name = fName;
		loadAnimations();
	}
	
	public void loadAnimations(){
		for (int j = 0; j<4; j++){
			ImageIcon ii = new ImageIcon(name + j + ".png");
			idleAnimation[j] = ii.getImage();
			idleAnimation[j] =  idleAnimation[j].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
			for (int i = 0; i<2; i++){		
				ii = new ImageIcon(name + "w" + j + i + ".png");
				//walkAnimation[i] = ii.getImage();
				walkAnimation[j][i] = ii.getImage();
				walkAnimation[j][i] = walkAnimation[j][i].getScaledInstance(width * Global.size, height * Global.size, Image.SCALE_DEFAULT);
				
				ii = new ImageIcon(name + "a" + j + i + ".png");
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
		if (frameCounter % 60 < 30)
			curFrame = 0;
		else if (frameCounter % 60 < 60)
			curFrame = 1;
		else
			curFrame = 1;
		frameCounter++;
		
		if (frameCounter == 600) //max 10 seconds per animation
			frameCounter = 0;
		
		return walkAnimation[curDirection][curFrame];
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
	}
	
	public void changeLocation(int xChange, int yChange){
		x += xChange;
		y += yChange;
	}
}