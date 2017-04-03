import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;

public class GameLauncher extends JFrame implements KeyListener{

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
	 * following are the defaults:
	private static final int MOVE_LEFT_MAPPING = KeyEvent.VK_LEFT;
	private static final int MOVE_RIGHT_MAPPING = KeyEvent.VK_RIGHT;
	private static final int MOVE_UP_MAPPING = KeyEvent.VK_UP;
	private static final int MOVE_DOWN_MAPPING = KeyEvent.VK_DOWN;
	private static final int ATTACK_ONE_MAPPING = "U";
	private static final int ATTACK_TWO_MAPPING = "O";
	
		if (key == KeyEvent.VK_LEFT) {
			keyPressedHelper(LEFT, -1, 0);
		}
		else if (key == KeyEvent.VK_RIGHT) {	
			keyPressedHelper(RIGHT, 1, 0);		
		}
		else if (key == KeyEvent.VK_UP) {
			keyPressedHelper(UP, 0, -1);			
		}
		else if (key == KeyEvent.VK_DOWN) {	
			keyPressedHelper(DOWN, 0, 1);
		}

	*/
	private int resolution = RESOLUTION_640_x_480;  // set resolution to default of 640 x 480
//	private JTextField textMoveLeft;
//	private JTextField textMoveRight;
//	private JTextField textMoveUp;
//	private JTextField textMoveDown;
//	private JTextField textAttackOne;
//	private JTextField textAttackTwo;

	private JButton btnMoveLeft;
	private JButton btnMoveRight;
	private JButton btnMoveUp;
	private JButton btnMoveDown;
	private JButton btnAttackOne;
	private JButton btnAttackTwo;
	
	private int left;
	private int right;
	private int down;
	private int up;
	private int attack;
	private int action;
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
		
		JLabel lblMoveLeft = new JLabel("Move Left Key");
		panel_1b.add(lblMoveLeft);
		
		btnMoveLeft = new JButton("Left");
		panel_1b.add(btnMoveLeft);
		btnMoveLeft.setText("A");
		
		JLabel lblMoveRight = new JLabel("Move Right Key");
		panel_1b.add(lblMoveRight);
		
		btnMoveLeft.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				left = e.getKeyCode();
				btnMoveLeft.setText(getKeyInfo(e));
			}

			private String getKeyChar() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		btnMoveRight = new JButton("Right");
		panel_1b.add(btnMoveRight);
		btnMoveRight.setText("D");
		
		btnMoveRight.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				right = e.getKeyCode();
				btnMoveRight.setText(getKeyInfo(e));
			}

			private String getKeyChar() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		JLabel lblMoveUp = new JLabel("Move Up Key");
		panel_1b.add(lblMoveUp);
		
		btnMoveUp = new JButton("Up");
		panel_1b.add(btnMoveUp);
		btnMoveUp.setText("W");
		
		btnMoveUp.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				up = e.getKeyCode();
				btnMoveUp.setText(getKeyInfo(e));
			}

			private String getKeyChar() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		JLabel lblMoveDown = new JLabel("Move Down Key");
		panel_1b.add(lblMoveDown);
		
		btnMoveDown = new JButton("Down");
		panel_1b.add(btnMoveDown);
		btnMoveDown.setText("S");
		
		btnMoveDown.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				down = e.getKeyCode();
				btnMoveDown.setText(getKeyInfo(e));
			}

			private String getKeyChar() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		JLabel lblAttackOne = new JLabel("Attack One Key Mapping");
		panel_1b.add(lblAttackOne);
		
		btnAttackOne = new JButton("Attack1");
		panel_1b.add(btnAttackOne);
		btnAttackOne.setText("Q");
		
		btnAttackOne.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				attack = e.getKeyCode();
				btnAttackOne.setText(getKeyInfo(e));
			}

			private String getKeyChar() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		JLabel lblAttackTwo = new JLabel("Action Key");
		panel_1b.add(lblAttackTwo);
		
		btnAttackTwo = new JButton("Attack2");
		panel_1b.add(btnAttackTwo);
		btnAttackTwo.setText("E");
		
		btnAttackTwo.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				action = e.getKeyCode();
				btnAttackTwo.setText(getKeyInfo(e));
			}

			private String getKeyChar() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
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
					writer.write(left);
					writer.write(right);
					writer.write(up);
					writer.write(down);
					writer.write(attack);
					writer.write(action);
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
//Source: https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
private String getKeyInfo(KeyEvent e){
        
        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "'" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = KeyEvent.getKeyText(keyCode);
        }
        return keyString;
}
}
