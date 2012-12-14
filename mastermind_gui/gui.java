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
  File filename;

  public void showGUI() {
		//Erstellt ein neues Fenster
		frame = new JFrame("MasterMind PP-1");
		//frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());
    //Make Menu
		genMenu();
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

  private void genMenu() {
		JMenuBar bar = new JMenuBar();
		{
			JMenu menu = new JMenu("File");
			{
			JMenuItem item00 = new JMenuItem("Speichern unter...");
      item00.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
            saveDialog();
          }
			});
      JMenuItem item01 = new JMenuItem("Speichern");
      item01.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
            saveFile();
          }
			});
			JMenuItem item02 = new JMenuItem("Laden");
      item02.addActionListener(new ActionListener () {
        public void actionPerformed (ActionEvent e) {
            loadDialog();
          }
			});
      menu.add(item00);
			menu.add(item01);
      menu.add(item02);
      }
		bar.add(menu);
		}
		frame.setJMenuBar(bar);
	}

  private void saveDialog() {
    JFileChooser fc = new JFileChooser();
    if(fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
      filename = fc.getSelectedFile();
      saveFile(filename);
    }
  }

  private void saveFile() {
    if(filename == null) {
      saveDialog();
    } else {
      saveFile(filename);
    }
  }

  private void saveFile(File filn) {
    save gamesave = new save(filn);
    gamesave.savefile(game.getCore());
  }

  private void loadDialog() {
    JFileChooser fc = new JFileChooser();
    if(fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
      load savegame = new load(fc.getSelectedFile());
      newGame(savegame.loadfile());
    }
  }

  private void newGame() {
    createGame(null);
  }

  private void newGame(Object[] o) {
    core co= new core(o);
    options.setColorRange(co.EnabledColorsSize());
    options.setcodeLength(co.codeLength());
    createGame(co);
  }

  private void createGame(core mm_core) {
    //Create JPanel
    if (jp !=null)
      frame.remove(jp);
    jp = new JPanel();
    jp.setLayout(new FlowLayout());
    //params CodeLength, EnabledColors, Tries
    if (mm_core == null) {
      mm_core = new core (options.getcodeLength(),options.getColorRange(),4);
    }
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
        jp.add(new JLabel("SIE HABEN VERLOREN"));
        break;
    }
    frame.repaint();
    frame.revalidate();
  }

  private void disableGame() {
    jp.remove(enabledColors);
    jb.setEnabled(false);
  }

}
