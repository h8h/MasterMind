package mastermind_gui;

import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.*;
import mastermind_core.core;
import mastermind_core.validator;
import mastermind_gui.gameArea.gameDialogSelector;
import mastermind_save_load.*;


/**
 * Global game gui manager, connect all gui classes
 */
public class gui {
  private gameInitialization game;
  private gameArea gA;
  private File filename;
  protected boolean gameRunningDialog;
  static final String GAMENAME="MasterMind PP-1";

  /**
   * Called by startMasterMind to show window
   */
  public void showGUI() {
    gA = new gameArea(this);
    gA.setTitle(GAMENAME + " Spiel: unbenannt");
    genMenu();
    newGame();
  }

  /**
   * Generate Menubar
   */
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
        	showDialog(gameDialogSelector.HELP);
          }
      });
     JMenuItem item22 = new JMenuItem("Info");
      item22.addActionListener(new ActionListener () {
        public void actionPerformed (ActionEvent e) {
        	showDialog(gameDialogSelector.INFO);
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
   * Show Info / About Dialog
   *
   * @see gameArea#showDialog(gameDialogSelector)
   */
  protected void showDialog(gameDialogSelector gDS) {
	  gA.showDialog(gDS);
  }

  /**
   * Check if gameDialog has focus
   *
   * @return <code>true</code> gameDialog is open
   * 		 <code>false</code> gameDialog is closed
   * @see gameArea#hasDialogFocus()
   */
  protected boolean hasDialogFocus() {
		return gA.hasDialogFocus();
  }

  /**
   * Close open dialog
   *
   * @see gameArea#closeDialog
   */
  protected void closeDialog() {
	  gA.closeDialog();
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
   *
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
   *
   * @param filn filename
   * @see mastermind_save_load.save
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
   *
   * @see mastermind_save_load.load
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
   * Add new try (next turn).<br>
   * If user wishes validating show help text and reject new try if validator is false
   *
   * @see gameInitialization#getEnabledValidate()
   * @see gameArea#isManualCode()
   * @see gameInitialization#validate()
   * @see gameInitialization#addTry()
   */
  protected void addTry() {
    game.doitBot();
    if(gA.isManualCode()) { //User gives secret code
      disableGame();
      setCodeAndNewGame();
      return;
    }
    if(game.getEnabledValidate()) { //User wishes help
      validator v = game.validate();
      setHapticFeedback(v.getText());
      if(!v.isValid()) { return; }
    } else {
        setHapticFeedback("Errate den geheimen Code ...");
    }
    int leftTries = game.leftTries();
    if(leftTries<0){
      leftTries = Math.abs(leftTries);
      setHapticFeedback(getHapticFeedback()+ " - Schon " + leftTries + " " + ((leftTries == 1) ? "Versuch" : "Versuche") + " benötigt");
    } else {
      setHapticFeedback(getHapticFeedback()+ " - Noch " + leftTries  + " " + ((leftTries<2) ? "Versuch" : "Versuche") + " möglich");
    }
    switch(game.addTry()) { // Check game status
      case WIN:
        disableGame();
        int sumTries = game.getTries()-game.leftTries();
        setHapticFeedback("SIE HABEN GEWONNEN :) - Mit " + sumTries + ((sumTries == 1) ? "em Versuch!" : " Versuchen!"));
        break;
      case LOST:
        disableGame();
        setHapticFeedback("SIE HABEN VERLOREN :(");
        break;
      case PLAYING:
        gA.setTitle(gA.getTitle().endsWith("*") ? gA.getTitle() : gA.getTitle() + "*");
    }
  }

  /**
   * Set text which gives user feedback, help and tips
   *
   * @param s text that helps user while playing
   * @see gameArea#setText(String)
   */
  protected void setHapticFeedback(String s) {
    gA.setText(s);
  }

  /**
   * Get text which you are set
   *
   * @return string with help text
   * @see gameArea#getText()
   */
  protected String getHapticFeedback() {
    return gA.getText();
  }

  /**
   * User's secret code is set, now create a new game
   *
   * @see #createGame()
   */
  protected void setCodeAndNewGame () {
    gA.removeManualCode();
    createGame();
    gA.setText("Geheimer Code wurde erstellt...Viel Spaß beim Erraten");
  }

  /**
   * If no game is running, create a new game
   *
   * @see #createGame(core)
   */
  protected void newGame() {
    if (gameRunning()) {return;}
    filename = null;
    gA.setTitle(GAMENAME+" Spiel: unbenannt");
    gA.initManualCode();
    if(gA.isManualCode()) {
      gA.setText("Setze den geheimen Code und klicke anschließend auf OK ...");
    } else {
      gA.setText("Neues Spiel ... neues Glück :)");
    }
    createGame(null);
  }

  /**
   * Create a new game from loaded file
   *
   * @param o with important core objects
   *
   * @see #createGame(core)
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
   * Create or recreate new gui with new/same game options and secret code given from user.<br>
   * Please use setCodeAndNewGame() to create a new game.
   *
   * @see #setCodeAndNewGame()
   */
  private void createGame() {
    core mm_core = new core (gA.options.getCodeSize(),gA.options.getColorRange(),gA.options.getNumberOfTries(),game.getColorArray());
    createGame(mm_core);
  }

  /**
   * Create or recreate new gui with new/same game options.<br>
   * Please use newGame() to create a new game.
   *
   * @param mm_core mastermind core class if game is loaded
   * @see #newGame()
   * @see #newGame(Object[])
   * @see mastermind_core.core
   */
  private void createGame(core mm_core) {
    gA.enableGame();
    if (mm_core == null) {
      mm_core = new core (gA.options.getCodeSize(),gA.options.getColorRange(),gA.options.getNumberOfTries());
    }
    game = new gameInitialization(mm_core);
    gA.setEnabledColors(game.initEnabledColors());
    gA.setGameInitialization(game.createGame());
    gA.validate();
    gA.repaint();
  }


  /**
   * Check if game is running
   *
   * @return <code>true</code> game is running<br>
   *         <code>false</code> game is not running
   */
  protected boolean gameRunning() {
    if(gA.getTitle().endsWith("*")) {
      Object [] options = {"Ja", "Nein", "Speichern"};
      gameRunningDialog = true;
      int ret = JOptionPane.showOptionDialog(null, "Es läuft zur Zeit ein aktives Spiel\n"
                  +"Wollen Sie das Spiel wirklich beenden?",
                  "Spiel beenden?",
                  JOptionPane.YES_NO_CANCEL_OPTION,
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  options,
                  options[2]);
      if(ret == 0) { //YES / OK
        gameRunningDialog = false;
        return false;
      }
      if(ret == 2) { // Save
        File tmp = filename;
        filename = null;
        saveFile();
        filename = tmp;
        gameRunningDialog = false;
        return false;
      }
      gameRunningDialog = false;
      return true;
    } else {
      gameRunningDialog = false;
      return false;
    }
  }

  /**
   * Check if game is finished (same as gameRunning, without a prompt)
   *
   * @return <code>true</code> game is finished<br>
   *         <code>false</code> game is running
   */
  protected boolean gameFinished() {
	  return !gA.nextTry.isEnabled();
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
   * Reset game, after WIN or LOST
   */
  private void disableGame() {
    gA.setTitle(GAMENAME + " Spiel: " + ((filename==null)  ? "unbenannt" : filename.getName()));
    gA.disableGame();
  }
}
