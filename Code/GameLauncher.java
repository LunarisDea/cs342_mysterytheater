import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;

public class GameLauncher extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1180818370680586732L;

	private JPanel contentPane;
	
	private static final int RESOLUTION_640_x_480 = 1;
	private static final int RESOLUTION_1280_x_960 = 2;
	private static final int RESOLUTION_1920_x_1080 = 3;
	private static final int RESOLUTION_2560_x_1440 = 4;
	/*
	private static final String MOVE_LEFT_MAPPING = "J";
	private static final String MOVE_RIGHT_MAPPING = "L";
	private static final String MOVE_UP_MAPPING = "I";
	private static final String MOVE_DOWN_MAPPING = "K";
	private static final String ATTACK_ONE_MAPPING = "U";
	private static final String ATTACK_TWO_MAPPING = "O";
	*/
	private int resolution = RESOLUTION_640_x_480;  // set resolution to default of 640 x 480
	private JTextField textMoveLeft;
	private JTextField textMoveRight;
	private JTextField textMoveUp;
	private JTextField textMoveDown;
	private JTextField textAttackOne;
	private JTextField textAttackTwo;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameLauncher frame = new GameLauncher();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameLauncher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_0 = new JPanel();
		contentPane.add(panel_0, BorderLayout.NORTH);
		
		JLabel lblMessage = new JLabel("Please choose a screen resolution. Default is 640 x 480.");
		panel_0.add(lblMessage);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1a = new JPanel();
		panel_1.add(panel_1a, BorderLayout.NORTH);
		panel_1a.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton button_1 = new JButton("640 x 480");
		panel_1a.add(button_1);
		
		JButton button_2 = new JButton("1280 x 960");
		panel_1a.add(button_2);
		
		JButton button_3 = new JButton("1920 x 1080");
		panel_1a.add(button_3);
		
		JButton button_4 = new JButton("2560 x 1440");
		panel_1a.add(button_4);
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = RESOLUTION_2560_x_1440;
			}
		});
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = RESOLUTION_1920_x_1080;
			}
		});
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = RESOLUTION_1280_x_960;
			}
		});
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = RESOLUTION_640_x_480;
			}
		});
		
		JPanel panel_1b = new JPanel();
		panel_1.add(panel_1b);
		panel_1b.setLayout(new GridLayout(6, 2, 0, 0));
		
		JLabel lblMoveLeft = new JLabel("Move Left Key Mapping");
		panel_1b.add(lblMoveLeft);
		
		textMoveLeft = new JTextField();
		panel_1b.add(textMoveLeft);
		textMoveLeft.setColumns(10);
		
		JLabel lblMoveRight = new JLabel("Move Right Key Mapping");
		panel_1b.add(lblMoveRight);
		
		textMoveRight = new JTextField();
		panel_1b.add(textMoveRight);
		textMoveRight.setColumns(10);
		
		JLabel lblMoveUp = new JLabel("Move Up Key Mapping");
		panel_1b.add(lblMoveUp);
		
		textMoveUp = new JTextField();
		panel_1b.add(textMoveUp);
		textMoveUp.setColumns(10);
		
		JLabel lblMoveDown = new JLabel("Move Down Key Mapping");
		panel_1b.add(lblMoveDown);
		
		textMoveDown = new JTextField();
		panel_1b.add(textMoveDown);
		textMoveDown.setColumns(10);
		
		JLabel lblAttackOne = new JLabel("Attack One Key Mapping");
		panel_1b.add(lblAttackOne);
		
		textAttackOne = new JTextField();
		panel_1b.add(textAttackOne);
		textAttackOne.setColumns(10);
		
		JLabel lblAttackTwo = new JLabel("AttackTwo Key Mapping");
		panel_1b.add(lblAttackTwo);
		
		textAttackTwo = new JTextField();
		panel_1b.add(textAttackTwo);
		textAttackTwo.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// write the resolution to a file...
				System.out.println(" write the resolution " + resolution + " to a file...");
				File file = new File("GameSettings.txt");
				try {
					FileWriter writer = new FileWriter(file);
					writer.write(resolution);
					writer.write(textMoveLeft.getText().toUpperCase());
					writer.write(textMoveRight.getText().toUpperCase());
					writer.write(textMoveUp.getText().toUpperCase());
					writer.write(textMoveDown.getText().toUpperCase());
					writer.write(textAttackOne.getText().toUpperCase());
					writer.write(textAttackTwo.getText().toUpperCase());
					writer.close();
					MysteryTheater.main(new String[0]);
					setVisible(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		panel_2.add(startButton);
}

}
