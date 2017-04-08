import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.sound.sampled.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class GameLauncher extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1180818370680586732L;

	private JPanel contentPane;
	
	private static final int RESOLUTION_640_x_480 = 1;
	private static final int RESOLUTION_1280_x_960 = 2;
	private static final int RESOLUTION_1920_x_1080 = 3;
	private static final int RESOLUTION_2560_x_1440 = 4;
	private static Clip clip;
	
	private int resolution = RESOLUTION_640_x_480;  // set resolution to default of 640 x 480

	private JButton btnMoveLeft;
	private JButton btnMoveRight;
	private JButton btnMoveUp;
	private JButton btnMoveDown;
	private JButton btnAttackOne;
	private JButton btnAttackTwo;
	
	private int left = 65;
	private int right = 68;
	private int down = 83;
	private int up = 87;
	private int attack = 81;
	private int action = 69;
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
		button_1.setBackground(Color.GRAY);
		panel_1a.add(button_1);
		
		JButton button_2 = new JButton("1280 x 960");
		button_2.setBackground(Color.GRAY);
		panel_1a.add(button_2);
		
		JButton button_3 = new JButton("1920 x 1080");
		button_3.setBackground(Color.GRAY);
		panel_1a.add(button_3);
		
		JButton button_4 = new JButton("2560 x 1440");
		panel_1a.add(button_4);
		button_4.setBackground(Color.GRAY);
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = RESOLUTION_640_x_480;
			    button_1.setForeground(Color.GREEN);
			    button_2.setForeground(Color.BLACK);
			    button_3.setForeground(Color.BLACK);
			    button_4.setForeground(Color.BLACK);
			}
		});
		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = RESOLUTION_1280_x_960;
			    button_1.setForeground(Color.BLACK);
			    button_2.setForeground(Color.GREEN);
			    button_3.setForeground(Color.BLACK);
			    button_4.setForeground(Color.BLACK);
			}
		});
		
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = RESOLUTION_1920_x_1080;
			    button_1.setForeground(Color.BLACK);
			    button_2.setForeground(Color.BLACK);
			    button_3.setForeground(Color.GREEN);
			    button_4.setForeground(Color.BLACK);
			}
		});
		
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resolution = RESOLUTION_2560_x_1440;
			    button_1.setForeground(Color.BLACK);
			    button_2.setForeground(Color.BLACK);
			    button_3.setForeground(Color.BLACK);
			    button_4.setForeground(Color.GREEN);
			}
		});
		
		JPanel panel_1b = new JPanel();
		panel_1.add(panel_1b);
		panel_1b.setLayout(new GridLayout(6, 2, 0, 0));
		
		//.add(slider);
		
		JLabel lblMoveLeft = new JLabel("Move Left Key");
		panel_1b.add(lblMoveLeft);
		
		btnMoveLeft = new JButton("Left");
		panel_1b.add(btnMoveLeft);
		btnMoveLeft.setText("A");
		
		JLabel lblMoveRight = new JLabel("Move Right Key");
		panel_1b.add(lblMoveRight);
		
		//add repeat function for music
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream (
			     new File("BkgrndMusic.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		}
	   catch (LineUnavailableException  | IOException | UnsupportedAudioFileException  e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);

		JSlider slider;
		JLabel label;

		slider = new JSlider(JSlider.HORIZONTAL, -20, 6, 2);
		slider.setMajorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setForeground(Color.RED);

		label = new JLabel("Volume");
		panel_3.add(label);
		panel_3.add(slider);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//label.setText("Volume);
				FloatControl gainControl =
						(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(slider.getValue());
				clip.start();
			}
		});


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
		panel_3.add(startButton);
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
