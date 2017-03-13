import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable, GameInfo{
	private int bWidth = INTERNAL_WIDTH;
	private int bHeight = INTERNAL_HEIGHT;
	private int initX = INTERNAL_WIDTH/2;	
	private int initY = INTERNAL_HEIGHT/2;	
	private int DELAY = 17;	
	private int framesSinceRoomEnter = 0;
	
	private Thread animator;
	private Player player;
	private Room room;
	
	public Board(){
		addKeyListener(new KeyInput());
		setFocusable(true);		
		initBoard();
	}
	
	private void initBoard(){	
		setBackground(Color.BLACK);
		bWidth = bWidth * Global.size; 
		bHeight = bHeight * Global.size;
		setPreferredSize(new Dimension(bWidth, bHeight));
		setDoubleBuffered(true);
		
		room = new Room();
		
		player = new Player("tw"); //temp
		player.setLocation(initX-(DEFAULT_WIDTH/2), initY-(DEFAULT_HEIGHT/2));
	}
	
	@Override
	public void addNotify(){
		super.addNotify();
		
		animator = new Thread(this);
		animator.start();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(room.getImage(), 0, 0, this);	
		drawCharacters(g);
		drawUI(g);	
	}	
	
	private void drawUI(Graphics g){
		int numHP = player.getCurHP();
		
		ImageIcon ii = new ImageIcon("hp.png");
		Image hpImage = ii.getImage();
		//hpImage = hpImage.getScaledInstance(8*Global.size, 8 * Global.size, Image.SCALE_DEFAULT);
		
		for (int i=2; i<=numHP+1; i++)
			g.drawImage(hpImage, (INTERNAL_WIDTH*Global.size-(i*32)), 40, this);
	}
	
	private void drawCharacters(Graphics g){	
		g.drawImage(player.getCurImage(), player.getX() *Global.size, player.getY() *Global.size, this);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void cycle(){
		framesSinceRoomEnter++;
		player.move();
		borderCollisionDetector();
	}
	
	private void borderCollisionDetector(){

		if (player.getX() + player.getWidth() > INTERNAL_WIDTH){
			if (framesSinceRoomEnter > 60){
				player.setLocation(0, player.getY());
				framesSinceRoomEnter = 0;
			}
			else{
				player.setLocation(INTERNAL_WIDTH - player.getWidth(), player.getY());
			}
		}
		else if (player.getX() < 0){
			if (framesSinceRoomEnter > 60){
				player.setLocation(INTERNAL_WIDTH-player.getWidth(), player.getY());
				framesSinceRoomEnter = 0;	
			}
			else{
				player.setLocation(0, player.getY());
			}			
		}
		else if (player.getY() + player.getHeight() > INTERNAL_HEIGHT){
			if (framesSinceRoomEnter > 60){
				player.setLocation(player.getX(), 0);		
				framesSinceRoomEnter = 0;			
			}
			else{
				player.setLocation(player.getX(), INTERNAL_HEIGHT - player.getHeight());
			}
		}
		else if (player.getY() < 0){
			if (framesSinceRoomEnter > 60){
			player.setLocation(player.getX(), INTERNAL_HEIGHT-player.getHeight());
			framesSinceRoomEnter = 0;			
			}
			else{
				player.setLocation(player.getX(), 0);
			}			
		}		
	}
	
	@Override
	public void run(){
		long beforeTime, timeDiff, sleep;
		
		beforeTime = System.currentTimeMillis();
		
		while (true){
			cycle();
			repaint();
			
			timeDiff = System.currentTimeMillis()-beforeTime;
			sleep = DELAY - timeDiff;
			
			if (sleep < 0){
				sleep = 2;
			}
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e){
				System.out.println("Interrupted: " + e.getMessage());
			}
			
			beforeTime = System.currentTimeMillis();
		}
	}
	
	private class KeyInput extends KeyAdapter{
		public void keyReleased(KeyEvent e){
			player.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e){
			player.keyPressed(e);
		}
	}
}

















