package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mastermind_gui.mastermind_templates.*;
import javax.swing.filechooser.*;
import java.io.*;
import javax.swing.SwingUtilities;
import mastermind_save_load.*;
import mastermind_core.core;

public class gui {
  gameInitialization game;
  JButton jb;
  options_gui options;
  JFrame frame;
  JPanel jp;
  JPanel enabledColors;

  public void showGUI() {
		//Erstellt ein neues Fenster
		frame = new JFrame("MasterMind PP-1");
		//frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());
    //Make Menu
		genMenu(frame);
    options = new options_gui();
    frame.add(options);
    JButton ak = new JButton ("Akzeptieren");
    ak.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent e) {
        newGame();
      }
    });
    frame.add(ak);
    newGame();
    frame.pack();
		frame.setVisible(true);
  }

  private void newGame() {
    //Create JPanel
    if (jp !=null)
      frame.remove(jp);
    jp = new JPanel();
    jp.setLayout(new FlowLayout());
    //params CodeLength, EnabledColors, Tries
    core mm_core = new core (options.getcodeLength(),options.getColorRange(),4);
    game = new gameInitialization(mm_core);
    JScrollPane scrollpane = new JScrollPane(game.initgameGround());
    jp.add(scrollpane);
    jb = new JButton ("OK");
    jb.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent e) {
        addTry();
      }
    });
    jp.add(jb);
    enabledColors = game.initenabledColors();
    jp.add(enabledColors);
    frame.add(jp);
    frame.repaint();
    frame.revalidate();
  }

  private void addTry() {
    switch(game.addTry()) {
      case WIN:
        disableGame();
        jp.add(new JLabel("SIE HABEN GEWONNEN"));
        break;
      case LOST:
        disableGame();
        jp.add(new JLabel("SIE HABEN DEN CODE NICHT GEKNACKT DIE WELT GEHT NUN UNTER IN 3 ... 2 ... 1 ... *SCHERZ*"));
        break;
    }
    frame.repaint();
    frame.revalidate();
  }

  private void disableGame() {
    jp.remove(enabledColors);
    jb.setEnabled(false);
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
