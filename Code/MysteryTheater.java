import java.awt.EventQueue;
import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MysteryTheater extends JFrame implements GameInfo{
	
	public MysteryTheater(){
		initUI();
	}
	
	private void initUI(){
		add(new Board());
		
		setResizable(false);
		pack();
		
		setTitle("ThreadTest");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void readFile(){
		File file = new File("GameSettings.txt");
		try {
			FileReader reader = new FileReader(file);
			int resolution = reader.read();
			Global.size = resolution;
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//GameLauncher gl = new GameLauncher();
			System.exit(-1);
		}		
	}
	
	public static void initGame(){
		readFile();		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame ex = new MysteryTheater();
				ex.setVisible(true);	
			}
		});		
	}
	
	public static void main(String[] args){	
		initGame();
	}
}