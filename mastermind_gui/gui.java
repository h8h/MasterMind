package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mastermind_gui.mastermind_templates.*;
import javax.swing.filechooser.*;
import java.io.*;
import javax.swing.SwingUtilities;

public class gui {
  public void showGUI() {
		//Erstellt ein neues Fenster
		JFrame frame = new JFrame("MasterMind PP-1");
		//frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Make Menu
		genMenu(frame);
		//params CodeLength, EnabledColors, Tries
		// gameInitialization game = new gameInitialization(2,15,4);
		// frame.add(game.getContainer());
		gameGround gg = new gameGround();
    frame.add(gg);

    frame.pack();
		frame.setVisible(true);
	}

	private void genMenu(Container container) {
		JMenuBar bar = new JMenuBar();
		{
			JMenu menu = new JMenu("File");
			{
			JMenuItem item = new JMenuItem("Speichern unter...");
      final Container frame = container;
      final JFileChooser fc = new JFileChooser();
      item.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						fc.showSaveDialog(frame);
					}
			});
			menu.add(item);
			}
			bar.add(menu);
		}
		((JFrame) container).setJMenuBar(bar);
	 }
}
