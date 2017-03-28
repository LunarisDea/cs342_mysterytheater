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
	
	public Board() {
//	public Board(char moveLeftMapping,
//	  char moveRightMapping,
//	  char moveUpMapping,
//	  char moveDownMapping,
//	  char attackOneMapping,
//	  char attackTwoMapping) {
//		this.moveLeftMapping = moveLeftMapping;
//		this.moveRightMapping = moveRightMapping;
//		this.moveUpMapping = moveUpMapping;
//		this.moveDownMapping = moveDownMapping;
//		this.attackOneMapping = attackOneMapping;
//		this.attackTwoMapping = attackTwoMapping;
		addKeyListener(new KeyInput());
		setFocusable(true);
		initBoard();
	}
	
	private void initBoard() {	
		setBackground(Color.BLACK);
		bWidth = bWidth * Global.size; 
		bHeight = bHeight * Global.size;
		setPreferredSize(new Dimension(bWidth, bHeight));
		setDoubleBuffered(true);
		
		room = new Room();
		
		player = new Player();
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
		
		ImageIcon ii = new ImageIcon("UI/hp.png");
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
		player.move();
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
