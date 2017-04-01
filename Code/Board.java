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
	
	private Thread animator;
	private Player player;
	private Room room;
	
	private char moveLeftMapping = 0;
	private char moveRightMapping = 0;
	private char moveUpMapping = 0;
	private char moveDownMapping = 0;
	private char attackOneMapping = 0;
	private char attackTwoMapping = 0;
	
	private Image hpImage;
	
	public Board() {
		addKeyListener(new KeyInput());
		setFocusable(true);
		initBoard();
	}
	
	private void initBoard() {	
		ImageIcon ii = new ImageIcon("UI/hp.png");
		Image hpImage = ii.getImage();
		
		setBackground(Color.BLACK);
		bWidth = bWidth * Global.size; 
		bHeight = bHeight * Global.size;
		setPreferredSize(new Dimension(bWidth, bHeight));
		setDoubleBuffered(true);
		
		room = new Room();
		
		player = new Player();
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
		g.drawImage(room.getBgImage(), 0, 0, this);	
		drawCharacters(g);
		g.drawImage(room.getFgImage(), 0, 0, this);
		drawUI(g);
	}	
	
	private void drawUI(Graphics g){
		int numHP = player.getCurHP();
		
		for (int i=2; i<=numHP+1; i++)
			g.drawImage(hpImage, (INTERNAL_WIDTH*Global.size-(i*32)), 40, this);
		
		if (Global.showHitboxes){
			//player hurtbox
			Box curBox = player.getHurtbox();
			g.setColor(Color.BLUE);
			g.drawRect(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight()); 
			//enemy hurtboxes
			//env boxes	
			g.setColor(Color.GREEN);
			int numObjects = room.getNumObjects();
			for (int i = 0; i<numObjects; i++){
				curBox = room.getCollisionBox(i);
				g.drawRect(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight()); 
			}
			//transition boxes
			g.setColor(Color.YELLOW);
			int numTrans = room.getNumTransitions();
			for (int i=0; i<numTrans; i++){
				curBox = room.getTransitionBox(i);
				g.drawRect(curBox.getX(), curBox.getY(), curBox.getWidth(), curBox.getHeight());
			}
		}
	}
	
	private void drawCharacters(Graphics g){	
		g.drawImage(player.getCurImage(), player.getX() *Global.size, player.getY() *Global.size, this);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private void cycle(){
		player.move();
		room.envCollisionDetector(player);
		room.borderCollisionDetector(player);
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
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
			
			beforeTime = System.currentTimeMillis();
		}
	}
	
	private class KeyInput extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
	}
}
