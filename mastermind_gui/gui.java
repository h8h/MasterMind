package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import mastermind_core.core;

public class gui {
	JButton[] code;
	core mastermindCore;	

  public void showGUI() {
		//Erstellt ein neues Fenster		
		JFrame frame = new JFrame("MasterMind PP-1");
		//frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(Box.createHorizontalBox());
		//Spielbare Farben und Namen ;)
		int codeSize = 4;
		mastermindCore = new core(codeSize);
		code = new JButton[codeSize];

		for(int i=0; i < code.length; i++) {
			code[i] = new JButton("              ");
			frame.add(code[i]);
		}	
		//Action fuer den ReMixIt - Button
		JButton mixer = new JButton ("ReMixIt");
		setBackgroundColor();
		//ReMixIt mit Click-Action verbinden
		mixer.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				setBackgroundColor();
			}
		});
		//ReMixIt-Button zum Panel hinzufuegen
		frame.add(Box.createHorizontalStrut(50));
		frame.add(mixer);
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void setBackgroundColor() {
		String [] colorCode = mastermindCore.generateCode();
		for(int i=0; i < code.length; i++) {
			code[i].setBackground(Color.decode(colorCode[i]));
		}
	}
}
