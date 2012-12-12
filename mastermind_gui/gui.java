package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mastermind_gui.mastermind_templates.*;
import javax.swing.filechooser.*;
import java.io.*;
import javax.swing.SwingUtilities;

public class gui {
  gameInitialization game;

  public void showGUI() {
		//Erstellt ein neues Fenster
		JFrame frame = new JFrame("MasterMind PP-1");
		//frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());
		//Make Menu
		genMenu(frame);
		//params CodeLength, EnabledColors, Tries
		game = new gameInitialization(4,8,4);
    frame.add(game.initgameGround());
    JButton jb = new JButton ("OK");
    jb.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent e) {
        addTry();
      }
    });
    frame.add(jb);
    frame.add(game.initenabledColors());
    frame.pack();
		frame.setVisible(true);
	}

	private void addTry() {
    game.addTry();
  }

  private void genMenu(Container container) {
		JMenuBar bar = new JMenuBar();
		{
			JMenu menu = new JMenu("File");
			{
			JMenuItem item01 = new JMenuItem("Speichern unter...");
      final Container frame = container;
      final JFileChooser fc = new JFileChooser();
      item01.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						fc.showSaveDialog(frame);
					}
			});
			menu.add(item01);
      }
		bar.add(menu);
		}
		((JFrame) container).setJMenuBar(bar);
	 }
}
