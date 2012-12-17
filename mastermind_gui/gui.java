package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import mastermind_save_load.*;
import mastermind_core.core;
import  javax.swing.filechooser.FileFilter;

public class gui {
  private gameInitialization game;
  private JButton jb;
  private options_gui options;
  private JFrame frame;
  private JPanel jp;
  private JPanel enabledColors;
  private File filename;
  static final String GAMENAME="MasterMind PP-1";

  public void showGUI() {
		//Displays a new Window
		frame = new JFrame(GAMENAME+" Spiel: unbenannt");
		//frame.setPreferredSize(new Dimension(640, 480));
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowListener() {
            public void windowClosed(WindowEvent arg0) { }
            public void windowActivated(WindowEvent arg0) { }
            public void windowClosing(WindowEvent arg0) {
              if(!gameRunning()) {
                  frame.dispose();
              }
            }
            public void windowDeactivated(WindowEvent arg0) { }
            public void windowDeiconified(WindowEvent arg0) { }
            public void windowIconified(WindowEvent arg0) { }
            public void windowOpened(WindowEvent arg0) { }
        });
    frame.setLayout(new FlowLayout());
    //KeyListener
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.addKeyEventDispatcher(new keyboard(this));
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
    fc.setAcceptAllFileFilterUsed(false);
    fc.setFileFilter( new FileFilter() {
      public boolean accept( File f )
      {
        return f.isDirectory() ||
          f.getName().toLowerCase().endsWith( ".mm" );
      }
      public String getDescription()
      {
        return "MasterMind Savefile";
      }
    });
    if(fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
      if(fc.getSelectedFile().exists()) {
        int ret = JOptionPane.showConfirmDialog(frame, "Die Datei " + fc.getSelectedFile().getName() + " existiert bereits"
           +"wollen Sie die Datei wirklich überschreiben?"
           +"Alle Spielstände gehen dadurch verloren","Datei überschreiben?",
           JOptionPane.YES_NO_OPTION);
        if(ret != 0) { //YES / OK
          return;
        }
      }
      filename = fc.getSelectedFile();
      if(!filename.getAbsolutePath().endsWith(".mm")) {
        filename = new File(filename.getAbsolutePath()+".mm");
      }
      frame.setTitle(GAMENAME+" Spiel: " + filename.getName());
      saveFile(filename);
    }
  }

  private void saveFile() {
    if(filename == null) {
      saveDialog();
    } else {
      frame.setTitle(GAMENAME+" Spiel: " + filename.getName());
      saveFile(filename);
    }
  }

  private void saveFile(File filn) {
    save gamesave = new save(filn);
    try {
      gamesave.savefile(game.getCore());
    } catch (Exception exc) {
        JOptionPane.showMessageDialog(frame,
        ":/ Speichern nicht möglich!",
        "Datei " + filn.getName() +  " wurde nicht gespeichert!",
        JOptionPane.ERROR_MESSAGE);
    }
  }

  private void loadDialog() {
    JFileChooser fc = new JFileChooser();
    fc.setAcceptAllFileFilterUsed(false);
    fc.setFileFilter( new FileFilter() {
      public boolean accept( File f )
      {
        return f.isDirectory() ||
          f.getName().toLowerCase().endsWith( ".mm" );
      }
      public String getDescription()
      {
        return "MasterMind Savefile";
      }
    });
    if(fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
      try {
        filename = fc.getSelectedFile();
        load savegame = new load(filename);
        newGame(savegame.loadfile());
      } catch(Exception exc) {
        System.out.println(exc.toString());
         JOptionPane.showMessageDialog(frame,
          ":/ Der Spielstand ist defekt oder nicht lesbar!",
          "Datei " + fc.getSelectedFile().getName() +  " ist beschädigt!",
          JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void newGame() {
    if (gameRunning()) {return;}
    filename = null;
    frame.setTitle(GAMENAME+" Spiel: unbenannt");
    createGame(null);
  }

  private void newGame(Object[] o) {
    if (gameRunning()) {return;}
    frame.setTitle(GAMENAME+" Spiel: " + filename.getName());
    core co= new core(o);
    options.setColorRange(co.EnabledColorsSize());
    options.setcodeLength(co.codeLength());
    options.setTriesLength(co.getTries());
    createGame(co);
  }

  private boolean gameRunning() {
    if(frame.getTitle().endsWith("*")) {
      int ret = JOptionPane.showConfirmDialog(frame, "Es läuft zur Zeit ein aktives Spiel\n"
                  +"Wollen Sie das Spiel wirklich beenden?",
                  "Spiel beenden?",
                  JOptionPane.YES_NO_OPTION);
      if(ret == 0) { //YES / OK
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  private void createGame(core mm_core) {
    //Create JPanel
    if (jp !=null)
      frame.remove(jp);
    jp = new JPanel();
    jp.setLayout(new FlowLayout());
    // Enable Save Menu
    frame.getJMenuBar().getMenu(0).getItem(0).setEnabled(true); // item00
    frame.getJMenuBar().getMenu(0).getItem(1).setEnabled(true); // item01
    if (mm_core == null) {
      //params CodeLength, EnabledColors, Tries
      mm_core = new core (options.getcodeLength(),options.getColorRange(),options.gettriesLength());
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
      case PLAYING:
        frame.setTitle(frame.getTitle().endsWith("*") ? frame.getTitle() : frame.getTitle() + "*");
    }
    frame.repaint();
    frame.revalidate();
  }

  private void disableGame() {
    frame.setTitle(GAMENAME + " Spiel: " + ((filename==null)  ? "unbeannt" : filename.getName()));
    frame.getJMenuBar().getMenu(0).getItem(0).setEnabled(false); // item00
    frame.getJMenuBar().getMenu(0).getItem(1).setEnabled(false); // item01

    jp.remove(enabledColors);
    jb.setEnabled(false);
  }

}
