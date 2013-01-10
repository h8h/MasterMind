package mastermind_gui;

import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;

import javax.swing.*;

import mastermind_core.core;
import mastermind_core.validator;
import mastermind_save_load.*;


/**
 * Global game gui manager, connect all gui classes
 */
public class gui {
  private gameInitialization game;
  private gameArea gA;
  private File filename;
  static final String GAMENAME="MasterMind PP-1";

  /**
   * Call by main to show window
   */
  public void showGUI() {
    gA = new gameArea(this);
    gA.setTitle(GAMENAME + " Spiel: unbenannt");
    genMenu();
    newGame();
  }

  private void genMenu() {
		JMenuBar bar = new JMenuBar();
		{
			JMenu menu = new JMenu("Datei");
			{
			JMenuItem item00 = new JMenuItem("Öffnen");
      item00.setAccelerator(KeyStroke.getKeyStroke(
      KeyEvent.VK_O, ActionEvent.CTRL_MASK));
      item00.addActionListener(new ActionListener () {
        public void actionPerformed (ActionEvent e) {
            loadDialog();
          }
			});
      JMenuItem item01 = new JMenuItem("Speichern");
      item01.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
      item01.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
            saveFile();
          }
			});
			JMenuItem item02 = new JMenuItem("Speichern unter...");
      item02.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_S, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
      item02.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
            saveDialog();
          }
			});
      JMenuItem item03 = new JMenuItem("Beenden");
      item03.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
      item03.addActionListener(new ActionListener () {
					public void actionPerformed (ActionEvent e) {
            if(!gameRunning()) {
              gA.dispose();
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
      JMenu menu02 = new JMenu("Hilfe");
      {
      JMenuItem item21 = new JMenuItem("Anleitung");
      item21.addActionListener(new ActionListener () {
        public void actionPerformed (ActionEvent e) {
            JDialog jD = new gameHelpDialog();
            jD.setVisible(true);
          }
      });
     JMenuItem item22 = new JMenuItem("Info");
      item22.addActionListener(new ActionListener () {
        public void actionPerformed (ActionEvent e) {
            JDialog jA = new gameAboutDialog();
            jA.setVisible(true);
          }
      });
      menu02.add(item21);
      menu02.add(item22);
      }
      bar.add(menu);
      bar.add(menu02);
	  }
		gA.setJMenuBar(bar);
	}

  /**
   * Show save dialog to select save file
   */
  private void saveDialog() {
    JFileChooser fc = new JFileChooser();
    fc.setAcceptAllFileFilterUsed(false);
    //Make save filter, only save *.mm files
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
    //Show Dialog
    if(fc.showSaveDialog(gA) == JFileChooser.APPROVE_OPTION) {
      if(fc.getSelectedFile().exists()) {
        int ret = JOptionPane.showConfirmDialog(null, "Die Datei " + fc.getSelectedFile().getName() + " existiert bereits"
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
      gA.setTitle(GAMENAME+" Spiel: " + filename.getName());
      saveFile(filename);
    }
  }

  /**
   * Save file, if filename was set (by saveDialog)
   * otherwise show save Dialog
   * @see #saveDialog()
   */
  private void saveFile() {
    if(filename == null) {
      saveDialog();
    } else {
      gA.setTitle(GAMENAME+" Spiel: " + filename.getName());
      saveFile(filename);
    }
  }

  /**
   * Save file to given filename
   * @param filn filename
   */
  private void saveFile(File filn) {
    save gamesave = new save(filn);
    try {
      gamesave.savefile(game.getCore());
      gA.setText("Spiel "+filn.getName().substring(0,filn.getName().length()-3) +" wurde gespeichert ... |");
    } catch (Exception exc) {
        JOptionPane.showMessageDialog(null,
        ":/ Speichern nicht möglich!",
        "Datei " + filn.getName() +  " wurde nicht gespeichert!",
        JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Show load dialog to load mastermind from file
   */
  private void loadDialog() {
    JFileChooser fc = new JFileChooser();
    fc.setAcceptAllFileFilterUsed(false);
    //only open *.mm files
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
    //open file dialog and select file
    if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      try {
        filename = fc.getSelectedFile();
        load savegame = new load(filename);
        newGame(savegame.loadfile());
      } catch(Exception exc) {
        System.out.println(exc.toString());
         JOptionPane.showMessageDialog(null,
          ":/ Der Spielstand ist defekt oder nicht lesbar!",
          "Datei " + fc.getSelectedFile().getName() +  " ist beschädigt!",
          JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * If no game is running, create a new game
   */
  protected void newGame() {
    if (gameRunning()) {return;}
    filename = null;
    gA.setTitle(GAMENAME+" Spiel: unbenannt");
    gA.setText("Neues Spiel ... neues Glück :)");
    createGame(null);
  }

  /**
   * Create a new game from loaded file
   *
   * @param o with important core objects
   * @see mastermind_core.core#makePKG()
   */
  protected void newGame(Object[] o) {
    if (gameRunning()) {return;}
    gA.setTitle(GAMENAME+" Spiel: " + filename.getName());
    gA.setText("Spiel "+filename.getName().substring(0,filename.getName().length()-3) +" wurde geladen ... |");
    core co= new core(o);
    gA.options.setColorRange(co.getEnabledColorsSize());
    gA.options.setCodeSize(co.getCodeSize());
    gA.options.setNumberOfTries(co.getTries());
    createGame(co);
  }

  /**
   * Check if game is running
   *
   * @return <code>true</code> game is running<br>
   *         <code>false</code> game is not running
   */
  public boolean gameRunning() {
    if(gA.getTitle().endsWith("*")) {
      Object [] options = {"Ja", "Nein", "Speichern"};
      int ret = JOptionPane.showOptionDialog(null, "Es läuft zur Zeit ein aktives Spiel\n"
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

  /**
   * Create or recreate new gui with new/same game options<br>
   * Please use newGame to call it
   *
   * @param mm_core core class if game is loaded
   * @see #newGame()
   * @see #newGame(Object[])
   */
  private void createGame(core mm_core) {
    gA.enableGame();
    if (mm_core == null) {
      mm_core = new core (gA.options.getCodeSize(),gA.options.getColorRange(),gA.options.getNumberOfTries());
    }
    game = new gameInitialization(mm_core);
    gA.setEnabledColors(game.initEnabledColors());
    gA.setGameInitialization(game.createGame());
    gA.revalidate();
    gA.repaint();
  }

  /**
   * Add new try (next turn)<br>
   * if user wishes validating show help text and reject new try if validator is false
   * @see gameInitialization#getEnabledValidate()
   */
  protected void addTry() {
    game.doitBot();
    if(game.getEnabledValidate()) {
      validator v = game.validate();
      gA.setText(v.getText());
      if(!v.isValid()) { return; }
    } else {
        gA.setText("Errate den geheimen Code ...");
    }
    int leftTries = game.leftTries();
    if(leftTries<0){
      leftTries = Math.abs(leftTries);
      gA.setText(gA.getText()+ " - Schon " + leftTries + " " + ((leftTries == 1) ? "Versuch" : "Versuche") + " benötigt");
    } else {
      gA.setText(gA.getText()+ " - Noch " + leftTries  + " " + ((leftTries<2) ? "Versuch" : "Versuche") + " möglich");
    }
    switch(game.addTry()) {
      case WIN:
        disableGame();
        int sumTries = game.getTries()-game.leftTries();
        gA.setText("SIE HABEN GEWONNEN :) - Mit " + sumTries + ((sumTries == 1) ? "em Versuch!" : " Versuchen!"));
        break;
      case LOST:
        disableGame();
        gA.setText("SIE HABEN VERLOREN :(");
        break;
      case PLAYING:
        gA.setTitle(gA.getTitle().endsWith("*") ? gA.getTitle() : gA.getTitle() + "*");
    }
  }

  /**
   * Set color at position
   *
   * @see gameInitialization#setColorAt(int,int)
   */
  protected void setColorAt(int color, int column) {
    game.setColorAt(color,column);
  }

  /**
   * Remove color at given column
   *
   * @see gameInitialization#removeColor(int)
   */
  protected void removeColor(int column) {
    game.removeColor(column);
  }

  /**
   * Reset game settings
   */
  private void disableGame() {
    gA.setTitle(GAMENAME + " Spiel: " + ((filename==null)  ? "unbenannt" : filename.getName()));
    gA.disableGame();
  }

}
