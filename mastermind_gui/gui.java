package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import java.util.*;

public class gui {
	JButton[] code;
	
  public void showGUI() {
		//Erstellt ein neues Fenster		
		final JFrame frame = new JFrame("MasterMind PP-1");
		frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout (new FlowLayout());
		//Spielbare Farben und Namen ;)
		final Color[] color_buttons = {Color.GREEN,Color.RED,Color.BLACK,Color.BLUE,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.WHITE,Color.YELLOW,Color.CYAN};
		init(frame, color_buttons);
		//Action fuer den ReMixIt - Button
		JButton mixer = new JButton ("ReMixIt");
		shufflecolor(color_buttons);
		ActionListener al = new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				shufflecolor(color_buttons);
				frame.revalidate();
			}
		};
		//ReMixIt mit Click-Action verbinden
		mixer.addActionListener( al );
		//ReMixIt-Button zum Panel hinzufuegen
		frame.getContentPane().add(mixer);
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	private void init(JFrame frame, Color[] c) {
		//Neues Button Array = geheimer Code, also haelfte vom Farben-Array
		code = new JButton[c.length/2];
		for(int i=0; i < code.length; i++) {
			code[i] = new JButton("              ");
			frame.getContentPane().add(code[i]);
		}		
	}
	
	private void shufflecolor(Color[] c) {
		Random r = new Random();
		int randomize_color = 0;
		for(int i=0; i < code.length; i++) {
				randomize_color = r.nextInt(c.length);
				code[i].setBackground(c[randomize_color]);
		}
	}
}
