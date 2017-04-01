import java.awt.EventQueue;
import javax.swing.JFrame;

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MysteryTheater extends JFrame implements GameInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -952184984365619687L;

	public MysteryTheater(){
		initUI();
	}
	
	private void initUI(){
		add(new Board());
		
		setResizable(false);
		pack();
		
		setTitle("Mystery Theater");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void readFile(){
		File file = new File("GameSettings.txt");
		try {
			FileReader reader = new FileReader(file);
			int resolution = reader.read();
			char moveLeftMapping = (char) reader.read();
			char moveRightMapping = (char) reader.read();
			char moveUpMapping = (char) reader.read();
			char moveDownMapping = (char) reader.read();
			char attackOneMapping = (char) reader.read();
			char attackTwoMapping = (char) reader.read();
			System.out.println(" move left mapping = " + moveLeftMapping);
			System.out.println(" move right mapping = " + moveRightMapping);
			System.out.println(" move up mapping = " + moveUpMapping);
			System.out.println(" move right mapping = " + moveDownMapping);
			System.out.println(" attack one mapping = " + attackOneMapping);
			System.out.println(" attack two mapping = " + attackTwoMapping);
			//could let player choose chars for mapping
			//by updating GameLauncher by adding to GUI
			//hitting any key needs to go to event handler
			//doing mapping: "if key pressed

			Global.size = resolution;
			reader.close();
		} catch (IOException e) {
			//TODO Auto-generated catch block
			//e.printStackTrace();
			//GameLauncher gl = new GameLauncher();
			GameLauncher.main(new String[0]);			
			//System.exit(-1);
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
	
	public static void main(String[] args) {
		for (String s: args){
			if (s.equals("-h"))
				Global.showHitboxes = true;
		}
		initGame();
	}
}
