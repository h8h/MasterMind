package mastermind_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import mastermind_save_load.*;
import mastermind_core.core;
import mastermind_core.validator;
import  javax.swing.filechooser.FileFilter;

public class gui {
  private gameInitialization game;
  private JButton jb;
  private JLabel jl;
  private options_gui options;
  private JFrame frame;
  private Box jp;
  private JPanel enabledColors;
  private File filename;
  private keyboard keylist;
  private boolean makeValidate = false;
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
    // frame.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS);
    // KeyListener
    keylist = new keyboard(this);
    // Make Menu
		genMenu();
    options = new options_gui(this);
    frame.add(options,BorderLayout.LINE_START);
    jb = new JButton ("OK");
    jb.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent e) {
        addTry();
      }
    });
    jl = new JLabel("Errate den geheimen Code ... | ");
    jl.setBorder(BorderFactory.createTitledBorder(""));
    jl.setAlignmentX(Component.CENTER_ALIGNMENT);
    frame.add(jl,BorderLayout.NORTH);
    frame.add(jb,BorderLayout.LINE_END);
    newGame();
    frame.pack();
		frame.setVisible(true);
  }

  private void genMenu() {
		JMenuBar bar = new JMenuBar();
		{
			JMenu menu = new JMenu("File");
			{
			JMenuItem item00 = new JMenuItem("Öffnen");
      item00.addActionListener(new ActionListener () {
        public void actionPerformed (ActionEvent e) {
            loadDialog();
          }
			});
      JMenuItem item01 = new JMenuItem("Speichern");
      item01.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
            saveFile();
          }
			});
			JMenuItem item02 = new JMenuItem("Speichern unter...");
      item02.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
            saveDialog();
          }
			});
      JMenuItem item03 = new JMenuItem("Beenden");
      item03.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
            if(!gameRunning()) {
              frame.dispose();
            }
          }
			});
      menu.add(item00);
      menu.addSeparator();
			menu.add(item01);
      menu.add(item02);
      menu.addSeparator();
      menu.add(item03);
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
      jl.setText("Spiel "+filn.getName().substring(0,filn.getName().length()-3) +" wurde gespeichert ... |");
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

  protected void newGame() {
    if (gameRunning()) {return;}
    filename = null;
    frame.setTitle(GAMENAME+" Spiel: unbenannt");
    jl.setText("Neues Spiel ... neues Glück :)");
    createGame(null);
  }

  private void newGame(Object[] o) {
    if (gameRunning()) {return;}
    frame.setTitle(GAMENAME+" Spiel: " + filename.getName());
    jl.setText("Spiel "+filename.getName().substring(0,filename.getName().length()-3) +" wurde geladen ... |");
    core co= new core(o);
    options.setColorRange(co.EnabledColorsSize());
    options.setcodeLength(co.codeLength());
    options.setTriesLength(co.getTries());
    createGame(co);
  }

  private boolean gameRunning() {
    if(frame.getTitle().endsWith("*")) {
      Object [] options = {"Ja", "Nein", "Speichern"};
      int ret = JOptionPane.showOptionDialog(frame, "Es läuft zur Zeit ein aktives Spiel\n"
                  +"Wollen Sie das Spiel wirklich beenden?",
                  "Spiel beenden?",
                  JOptionPane.YES_NO_CANCEL_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  options,
                  options[2]);
      if(ret == 0) { //YES / OK
        return false;
      }
      if(ret == 2) {
        File tmp = filename;
        filename = null;
        saveFile();
        filename = tmp;
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  private void createGame(core mm_core) {
    //Create JPanel
    if (jp !=null) {
      frame.remove(jp);
      frame.remove(enabledColors);
      disableGame(); //Rest all settings
    }
    jp = Box.createVerticalBox();
    jp.setBorder(BorderFactory.createTitledBorder(" "));
    // Enable Save Menu
    frame.getJMenuBar().getMenu(0).getItem(2).setEnabled(true); // item01
    frame.getJMenuBar().getMenu(0).getItem(3).setEnabled(true); // item02
    if (mm_core == null) {
      //params CodeLength, EnabledColors, Tries
      mm_core = new core (options.getcodeLength(),options.getColorRange(),options.gettriesLength());
    }
    game = new gameInitialization(mm_core);
    Box vbox = Box.createVerticalBox();
    JScrollPane scrollpane = new JScrollPane(game.initgameGround());
    vbox.add(scrollpane);
    JCheckBox cb = new JCheckBox("Hilfsfunktion aktivieren");
    cb.setSelected(makeValidate);
    cb.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        makeValidate = (boolean) source.isSelected();
      }
    });
    cb.setAlignmentX(Component.RIGHT_ALIGNMENT);
    vbox.add(cb);
    jp.add(vbox);
    enabledColors = game.initenabledColors();
    enabledColors.setBorder(BorderFactory.createTitledBorder(""));
    //Validate Button
    jb.setEnabled(true);
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.addKeyEventDispatcher(keylist);
    frame.add(enabledColors,BorderLayout.SOUTH);
    frame.add(jp,BorderLayout.CENTER);
    frame.repaint();
    frame.revalidate();
  }

  protected void addTry() {
    game.doitBot(); //Fill empty Rows - setBestColors
    if(makeValidate) {
      validator v = game.validate();
      jl.setText(v.getText());
      if(!v.isValid()) { return; }
    } else {
        jl.setText("Errate den geheimen Code ... |");
    }
    switch(game.addTry()) {
      case WIN:
        disableGame();
        jl.setText("SIE HABEN GEWONNEN :)");
        break;
      case LOST:
        disableGame();
        jl.setText("SIE HABEN VERLOREN :(");
        break;
      case PLAYING:
        frame.setTitle(frame.getTitle().endsWith("*") ? frame.getTitle() : frame.getTitle() + "*");
    }
    frame.repaint();
    frame.revalidate();
  }

  protected void setColorAt(int color, int column) {
    game.setColorAt(color,column);
  }

  protected void removeColor(int column) {
    game.removeColor(column);
  }

  private void disableGame() {
    frame.setTitle(GAMENAME + " Spiel: " + ((filename==null)  ? "unbenannt" : filename.getName()));
    frame.getJMenuBar().getMenu(0).getItem(2).setEnabled(false); // item01
    frame.getJMenuBar().getMenu(0).getItem(3).setEnabled(false); // item02
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.removeKeyEventDispatcher(keylist);
    frame.remove(enabledColors);
    jb.setEnabled(false);
  }

}
