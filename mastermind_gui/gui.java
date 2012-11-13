package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 


public class gui {

  public void showGUI() {
		//Erstellt ein neues Fenster		
		JFrame frame = new JFrame("MasterMind PP-1");
		//frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Make Menu
		//genMenu(frame);
																	//params CodeLength, EnabledColors, Tries
		gameInitialization game = new gameInitialization(2,15,4);
		frame.add(game.getContainer());
		frame.pack();
		frame.setVisible(true);
	}
	
/*	private void genMenu(Container container) {
		JMenuBar bar = new JMenuBar();
		{
			JMenu menu = new JMenu("File");
			{
			JMenuItem item = new JMenuItem("Neues Spiel - mit Code");

			item.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						resetGame();
					}
			});
			menu.add(item);
			}
			bar.add(menu);
		}	
		((JFrame) container).setJMenuBar(bar);
	}
	*/
}
