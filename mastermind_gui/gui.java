package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import java.util.*;

public class gui {
  public void showGUI() {
		//Erstellt ein neues Fenster		
		final JFrame frame = new JFrame("MasterMind PP-1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout (new FlowLayout());
		//Spielbare Farben und Namen ;)
		final Color[] color_buttons = {Color.GREEN,Color.RED,Color.BLACK,Color.BLUE,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.WHITE,Color.YELLOW,Color.CYAN};
		//Action fuer den ReMixIt - Button
		JButton mixer = new JButton ("ReMixIt");
		ActionListener al = new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				//Neue Zufallsbutton erstellen
				JButton[] coded_buttons = shufflecolor(color_buttons);
				//Buttons zum Panel hinzufuegen
				for(int i=0; i < coded_buttons.length; i++) {
					frame.getContentPane().add(coded_buttons[i]);
				}
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
	
	private JButton[] shufflecolor(Color[] c) {
		//Neues Button Array = geheimer Code, also haelfte vom Farben-Array
		JButton[] coding_buttons = new JButton[c.length/2];
		Random r = new Random();
		int randomize_color = 0;
		for(int i=0; i < coding_buttons.length; i++) {
				randomize_color = r.nextInt(c.length);
				JButton random_button = new JButton("              ");
				random_button.setBackground(c[randomize_color]);
				coding_buttons[i] = random_button;
		}
		return coding_buttons;
	}
}
