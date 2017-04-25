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

public class PauseMenu extends JFrame {
	
	private static final long serialVersionUID = 1180818370680586732L;

	private JPanel contentPane;
	private JButton closeButton;
	private JButton exitButton;
	private JPanel barPane;
	private JLabel textLabel;
	private static Clip clip;
	
	public PauseMenu(String msg, boolean resume, boolean exit) {

		textLabel = new JLabel(msg);
		if (resume == true){		
		closeButton = new JButton("Resume");
		closeButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
					 Player.getInstance().unPause();
		       dispose();
		    }
		});
		}
		if (exit == true){
		exitButton = new JButton("Exit Game");
		exitButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		       System.exit(0);
		    }
		});
		}
		barPane = new JPanel();
		if (exit){
		barPane.add(exitButton);
		}
		if (resume){
		barPane.add(closeButton);
		}
		JPanel textPanel = new JPanel();
		textPanel.add(textLabel);
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(textPanel, BorderLayout.CENTER);
		contentPane.add(barPane, BorderLayout.SOUTH);
		add(contentPane);
		setSize(400, 300);
		setVisible(true);
	}
	
	public void setMessage(String msg) {
		
	}
}
