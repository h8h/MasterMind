package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import java.util.*;

public class gui {
    public void createAndShowGUI() {
        //Create and set up the window.
        final 	JFrame frame = new JFrame("HelloWorldSwing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout (new FlowLayout());
		
		final String[] color_buttons =  {"Blau","Grün","Gelb","Schwarz","Lila","Seb","Chris","Timo","Stefan"};
		
		ActionListener al = new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				JButton[] coded_buttons = mixitbaby(color_buttons);
				for(int i=0; i < coded_buttons.length; i++) {
					System.out.println("Button wurde hinzugefügt");
					frame.getContentPane().add(coded_buttons[i]);
				}
			}
		};
		
		JButton mixer = new JButton ("ReMixIt");
		mixer.addActionListener( al );
        frame.getContentPane().add(mixer);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	private JButton[] mixitbaby(String[] s) {
		JButton[] coding_buttons = new JButton[s.length/2];
		Random r = new Random();
		int randomize_color = 0;
		for(int i=0; i < coding_buttons.length; i++) {
				randomize_color = r.nextInt(s.length);
				System.out.println("Ihre Zufallszahl lautet: " + randomize_color);
				coding_buttons[i] = new JButton(s[randomize_color]);
		}
		return coding_buttons;
	}
}